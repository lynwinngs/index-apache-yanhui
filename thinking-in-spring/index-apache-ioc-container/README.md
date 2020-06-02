# IoC 容器扩展学习

# bean 生命周期

## 图例

> **???**: 疑惑点  
> _goto class.method_: 跳转条目  
> _goto ①_: 跳转条目内锚点  
> _link ①_: 指向条目内锚点
> 自定义扩展: 可以对框架进行的扩展点  

## AnnotationConfigApplicationContext

- new
    + `AbstractApplicationContext` 类加载时, 通过 static 块加载类 `ContextClosedEvent`, 目的是让这个类尽快加载
    + `super new GenericApplicationContext`
        + `new DefaultListableBeanFactory`
    + `new AnnotatedBeanDefinitionReader`: 注解的 `BeanDefinition` 读取器(_goto `AnnotatedBeanDefinitionReader.new`_)
    + `new ClassPathBeanDefinitionScanner`: ClassPath 内的 BeanDefinition 扫描器
        + `registerDefaultFilters`
            + 添加 `@Component` 注解的过滤
                + 考虑元注解
                + 不考虑子类继承
                + 不考虑接口
            + 如果存在 `@ManagerBean` 注解(JSR-250)存在, 添加 `@ManagedBean` 注解的过滤
                + 不考虑元注解
                + 不考虑子类继承
                + 不考虑接口
            + 如果存在 `@Named` 注解(JSR-330)存在, 添加 `@Named` 注解的过滤
                + 不考虑元注解
                + 不考虑子类继承
                + 不考虑接口
        + `setEnvironment`
        + `setResourceLoader`
            + `CandidateComponentsIndexLoader.loadIndex`
                + 加载 META-INF/spring.components 中的组件到 `CandidateComponentsIndex componentsIndex` **???**
- `register`
    + `AnnotatedBeanDefinitionReader.register(Class ...)` 注册组件(_goto `AnnotatedBeanDefinitionReader.register`_)
    
- `refresh`
    + `prepareRefresh`
        + 初始化一些状态: startupDate=now, closed=false, active=true
        + `initPropertySources`: 提供给子类的自定义扩展点
            + spring 提供了 3 个关于 web 容器的实现, 非 web 项目无官方实现
            + `AbstractRefreshableWebApplicationContext`
            + `GenericWebApplicationContext`
            + `StaticWebApplicationContext`
        + `validateRequiredProperties` 校验必须参数
        + `earlyApplicationListeners` 就绪. 早期的 `ApplicationListeners` 何时会提前注入进来 **???**
        + `earlyApplicationEvents` 就绪
    + `obtainFreshBeanFactory` 获取 `BeanFactory`
        + `refreshBeanFactory` 设置 id
        + `getBeanFactory` 返回实例化是创建的 DefaultListableBeanFactory
    + `prepareBeanFactory`
        + `setBeanClassLoader` 设置 classLoader
        + `setBeanExpressionResolver` 设置 spring el expression 处理器 `new StandardBeanExpressionResolver` ①
        + `addPropertyEditorRegistrar` 添加 `new ResourceEditorRegistrar` 作用是什么 **???**
        + `addBeanPostProcessor`
            + `new ApplicationContextAwareProcessor` 具有 ApplicationContext 意识的处理器,添加到 `BeanPostProcessor` 列表
                + `new EmbeddedValueResolver` 嵌入式 Value 处理器
                    + 设置 应用上下文 和 spring el expression 处理器(_link ①_)
        + 忽略注入接口
            + `EnvironmentAware`, `EmbeddedValueResolverAware`, `ResourceLoaderAware`, 
            `ApplicationEventPublisherAware`, `MessageSourceAware`, `ApplicationContextAware`
        + `ConfigurableListableBeanFactory.registerResolvableDependency` 注册 ResolvableDependency(无生命周期管理; 无法实现延迟初始化 bean; 无法通过依赖查找 )
            + (_goto `DefaultListableBeanFactory.resolvableDependencies`_)
        + `addBeanPostProcessor`
            + `new ApplicationListenerDetector` ApplicationContext 检测器, 添加到 `BeanPostProcessor` 列表
        + `loadTimeWeaver`(类加载期织入) bean 是否存在
            + `new LoadTimeWeaverAwareProcessor` 具有 类加载期织入 意识的处理器, 添加到 `BeanPostProcessor` 列表
            + `new ContextTypeMatchClassLoader` 用于织入的类加载器
        + `SingletonBeanRegistry.registerSingleton` 注册 Singleton(无生命周期管理; 无法实现延迟初始化 bean)
            + (_goto `DefaultListableBeanFactory.registerSingleton`_)


    + try 块
        + `postProcessBeanFactory`
        + `invokeBeanFactoryPostProcessors`
        + `registerBeanPostProcessors`
        + `initMessageSource`
        + `initApplicationEventMulticaster`
        + `onRefresh`
        + `registerListeners`
        + `finishBeanFactoryInitialization`
        + `finishRefresh`
    + catch 块
        + `destroyBeans`
        + `cancelRefresh`
    + finally 块
        + `resetCommonCaches`
                
                
## AnnotatedBeanDefinitionReader: 注解的 BeanDefinition 读取器

- new 构造器参数 `BeanDefinitionRegistry`
    + `new AnnotatedBeanDefinitionReader`: 注解的 `BeanDefinition` 读取器
        + `getOrCreateEnvironment(BeanDefinitionRegistry)`
        + `new ConditionEvaluator`: 条件评估器(用于 `@Conditional` 的 `Condition.matches`)
            + `deduceBeanFactory` (beanFactory: 自身, applicationContext: 组合的 beanFactory)
            + `deduceEnvironment`
            + `deduceResourceLoader` (容器自身)
            + `deduceClassLoader`
        + `AnnotationConfigUtils.registerAnnotationConfigProcessors` 注册注解配置处理器
            + 注册内建 `BeanDefinition`
                + `ConfigurationClassPostProcessor`: 处理 `@Configuration` 
                + `AutowiredAnnotationBeanPostProcessor`: 处理 `@Autowired` `@Value` `@Inject` 等
                + `CommonAnnotationBeanPostProcessor`: 处理 `@Resource` `@PostConstructor` `@PreDestroy` 等
                + `PersistenceAnnotationBeanPostProcessor`: 处理 jpa
                + `EventListenerMethodProcessor`: 处理 `@EventListener`
                + `DefaultEventListenerFactory`
            + 调用 `DefaultListableBeanFactory.registerBeanDefinition` 注册这些内建 `BeanDefinition`

- `register(Class ...)` 注册组件
    + `registerBean(Class beanClass)` 注册 bean
        + `doRegisterBean` 注册 bean
            + `new AnnotatedGenericBeanDefinition`
                + set beanClass
                + `AnnotationMetadata.introspect(beanClass)` 通过自省获得元数据
                    + `AnnotationsScanner.getDeclaredAnnotations` 缓存注解的元信息
            + 判断是否要跳过注册 `ConditionEvaluator.shouldSkip`
                + 通过 `@Conditional` 注解内的 `Condition` 接口实现的逻辑判断
            + `ScopeMetadataResolver.resolveScopeMetadata` 解析 `@Scope` 信息, 无则为 singleton
                + 使用 `AnnotationScopeMetadataResolver` 的实现
            + 如果 beanName 为空, 则使用 `BeanNameGenerator.generateBeanName` 生成 beanName
                + 使用 `AnnotationBeanNameGenerator` 的实现
                + 判断是不是 Spring bean(`@Component` 及其派生、`@ManagedBean`、`@Named`)
                    + 如果是且 value 不为空, 则使用 value 值作为 beanName
                    + 否则, 按类名首字母小写来生成 beanName
            + `AnnotationConfigUtils.processCommonDefinitionAnnotations` 处理普通定义的注解
                + `@Lazy`
                + `@Primary`
                + `@DependsOn`
                + `@Role`
                + `@Description`
            + 处理参数中是否传递了限定注解
                + `@Lazy`
                + `@Primary`
                + `@Qualifier`
            + `BeanDefinitionCustomizer.customize`: `BeanDefinition` 定制器执行(无实现, 可进行自定义扩展)
            + `new BeanDefinitionHolder`: 持有 `BeanDefinition` 实例和 beanName
            + `AnnotationConfigUtils.applyScopedProxyMode` 处理代理, 如果需要的话
                + `ScopedProxyMode.INTERFACE`: jdk 动态代理
                + `ScopedProxyMode.TARGET_CLASS`: cglib 动态代理
            + `BeanDefinitionReaderUtils` 注册 bean (_goto `BeanDefinitionReaderUtils.registerBeanDefinition`_)
        
## XmlBeanDefinitionReader: Xml 的 BeanDefinition 读取器

`extends AbstractBeanDefinitionReader`

- new
    + 构造器参数 `BeanDefinitionRegistry`, 传递上下文的 `ResourceLoader`、`Environment`
    
- `AbstractBeanDefinitionReader.loadBeanDefinitions` ①
    + 判断当前 `ResourceLoader` 是 `ResourcePatternResolver` 么(`ApplicationContext` 实现了 `ResourcePatternResolver`)
        + 是, 则取到 `Resource[]`
        + 否, 则取到 `Resource`
    + `XmlBeanDefinitionReader.loadBeanDefinitions(EncodedResource)` 包装 `Resource` 到 `EncodeResource`, 开始读取配置
    + `ThreadLocal` 保存当前线程 `EncodedResource`
    + `doLoadDocument` 把资源加载成文档
    + `registerBeanDefinitions` 注册 `BeanDefinition`
        + 创建 `BeanDefinitionDocumentReader`:  `BeanDefinition` 文档读取器, `DefaultBeanDefinitionDocumentReader`
        + `BeanDefinitionDocumentReader.registerBeanDefinitions` 读取器注册 `BeanDefinition`
            + 创建 `XmlReaderContext`: xml 读取上下文
                + 创建 `NamespaceHandlerResolver`
                    + `namespaceHandlerResolver == null` 则创建 `DefaultNamespaceHandlerResolver`
                        + 关联 META-INF/spring.handlers **???**
            + `DefaultBeanDefinitionDocumentReader.doRegisterBeanDefinitions` ②
                + 创建 `BeanDefinitionParserDelegate` 代理
                + 判断命名空间的 `profile`, 与当前不符则 return
                + `preProcessXml`: xml 前处理, 默认无实现, 可继承 `DefaultBeanDefinitionDocumentReader` 进行自定义扩展
                + `parseBeanDefinitions`: 解析 `BeanDefinition`
                    + 遍历节点元素(按照文件中元素的声明顺序)
                        + `parseDefaultElement`: 解析元素
                            + 处理 import
                                + 处理 spring expression 获取真实路径
                                + 判断绝对路径 or 相对路径
                                    + 相对路径要转换路径资源
                                + (_goto ①_) 解析 import 的 xml 文件
                                + `ReaderContext.fireImportProcessed` 执行 Import 后事件 `ReaderEventListener` (_goto `ReaderEventListener`_) 
                            + 处理 alias
                                + 一些校验
                                + 成功后注册别名(_goto `SimpleAliasRegistry.registerAlias`_)
                                + `ReaderContext.fireAliasRegistered` 执行别名注册后事件 `ReaderEventListener` (_goto `ReaderEventListener`_)
                            + 处理 bean
                                + `processBeanDefinition` 处理 `BeanDefinition`
                                    + `BeanDefinitionParserDelegate.parseBeanDefinitionElement`: 解析 `BeanDefinition` 元素, 此时没有 beanName
                                        + 获取 id 赋值 beanName, 处理 alias, beanName 查重
                                        + `parseBeanDefinitionElement`: 解析 `BeanDefinition` 元素, 此时有 beanName
                                            + 解析 class, parent
                                            + `createBeanDefinition`: 通过 class 和 parent, 创建 `BeanDefinition`
                                                + `new GenericBeanDefinition`
                                                + 设置 parent class
                                                + 如果 classLoader 不为空, 则设置 beanClass, 否则设置 beanClassName
                                            + `parseBeanDefinitionAttributes`: 解析 `BeanDefinition` 的属性
                                                + scope, abstract, lazy-init, autowire, depend-on, autowire-candidate, 
                                                primary, init-method, destroy-method, factory-method, factory-bean
                                            + 根据 child node 解析 description
                                            + `parseMetaElements`: 解析 meta 元素
                                            + `parseLookupOverrideSubElements`: 解析 lookup-method 方法查找依赖
                                            + `parseReplacedMethodSubElements`: 解析 replaced-method **???**
                                            + `parseConstructorArgElements`: 解析 constructor-arg 构造器参数
                                            + `parsePropertyElements`: 解析 property 参数
                                            + `parseQualifierElements`: 解析 qualifier 限定
                                            + 设置 `Resource`、`Source`
                                        + 如果 beanName 为空, 生成 beanName
                                        + alias to array
                                        + return BeanDefinitionHolder
                                    + `BeanDefinitionParserDelegate.decorateBeanDefinitionIfRequired`: 装饰存在的属性和子节点属性 **???**
                                    + `BeanDefinitionReaderUtils` 注册 `BeanDefinition` (_goto `BeanDefinitionReaderUtils.registerBeanDefinition`_)
                                    + `ReaderContext.fireComponentRegistered` 执行组件注册后事件 `ReaderEventListener` (_goto `ReaderEventListener`_)
                            + 处理 beans
                                + (_goto ②_)
                + `postProcessXml`: xml 后处理, 默认无实现, 可继承 `DefaultBeanDefinitionDocumentReader` 进行自定义扩展
                + 代理置空
    + finally 块
        + 移除当前 `EncodedResource`
        + 如果`Set<EncodedResource>`空了, 则清空当前线程 `ThreadLocal` 数据, 避免内存泄漏
            
            
## DefaultListableBeanFactory 

- `registerBeanDefinition`: 注册 `BeanDefinition` 过程
    + `validate`() 校验
    + 判断 `BeanDefinition` 是否存在
        + 已存在, 则进行一些校验, 通过则 put 到 beanDefinitionMap
        + 不存在, 判断是否已经开始创建
            + 已经开始 bean 创建
                + 需要对 beanDefinitionMap 加锁, 保证线程安全
                + 使用 CopyOnWrite 方式进行新增
                + 手动移除 singleton beanName
            + 未开始
                + put(beanName, beanDefinition) to beanDefinitionMap
                + add(beanName) to beanDefinitionNames
    + 判断是否需要 resetBeanDefinition

> 相关属性说明  
> - beanDefinitionMap 保存 beanName 与 beanDefinition 的 ConcurrentHashMap  
> - beanDefinitionNames 用来控制 beanDefinition 的顺序  
> - frozenBeanDefinitionNames 冻结 beanDefinition 注册的标志  


- `resolvableDependencies` put Class -> object
    + `BeanFactory` -> beanFactory
    + `ResourceLoader` -> applicationContext
    + `ApplicationEventPublisher` -> applicationContext
    + `ApplicationContext` -> applicationContext 
    
- `registerSingleton`
    + super `DefaultSingletonBeanRegistry.registerSingleton`
        + synchronized `singletonObjects`
        + 判断不存在,则调用 `addSingleton`
            + synchronized `singletonObjects`
            + `singletonObjects` put beanName -> bean instance
            + `singletonFactories` remove beanName
            + `earlySingletonObjects` remove beanName
            + `registeredSingletons` remove beanName
    + updateManualSingletonNames 更新手动的 Singleton
        + manualSingletonNames add beanName
    + clearByTypeCache 清除所有假定的 byType mappings


## BeanDefinitionReaderUtils 

- `registerBeanDefinition` 注册 bean
    + 使用 `DefaultListableBeanFactory.registerBeanDefinition` 注册(_goto `DefaultListableBeanFactory.registerBeanDefinition`_)
    + 注册别名(_goto `SimpleAliasRegistry.registerAlias`_)
    
## SimpleAliasRegistry

- Field `aliasMap` 用来保存 别名 与 bean 名称的对应

- `registerAlias`
    + `aliasMap` 加锁
    + 判断 参数名称 与别名是否相同
        + 相同则移除别名
        + 不同
            + 获取 `aliasMap` 中别名对应的 beanName
            + 如果 value 不等于空且与 参数名称相同, 则返回
            + 如果不允许覆写, 则抛出异常(Spring 默认允许, Spring boot 默认不允许 **???**)
            + `checkForAliasCircle` 检查别名循环
            + `aliasMap` 保存 alias - beanName

## ReaderEventListener

    默认为空, 可自定义扩展
- 方法:   
    + 在 `new XmlReaderContext` 时, 传入 `ReaderEventListener` 的实现
    + 使用 `setEventListener` 方法设置 