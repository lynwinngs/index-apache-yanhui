# IoC 容器扩展学习

# bean 生命周期

## AnnotationConfigApplicationContext

- new
    + AbstractApplicationContext 类加载时，通过 static 块加载类 ContextClosedEvent，目的是让这个类尽快加载
    + new AnnotatedBeanDefinitionReader：注解的 BeanDefinition 读取器（详细见 《目录 AnnotatedBeanDefinitionReader》）
    + new ClassPathBeanDefinitionScanner：ClassPath 内的 BeanDefinition 扫描器
        + registerDefaultFilters
            + 添加 Component 注解的过滤
                + 考虑元注解
                + 不考虑子类继承
                + 不考虑接口
            + 如果存在 ManagerBean 注解（JSR-250）存在，添加 ManagedBean 注解的过滤
                + 不考虑元注解
                + 不考虑子类继承
                + 不考虑接口
            + 如果存在 Named 注解（JSR-330）存在，添加 Named 注解的过滤
                + 不考虑元注解
                + 不考虑子类继承
                + 不考虑接口
        + setEnvironment
        + setResourceLoader
            + CandidateComponentsIndexLoader.loadIndex
                + 加载 META-INF/spring.components 中的组件到 CandidateComponentsIndex componentsIndex **？？？**
- register
    + AnnotatedBeanDefinitionReader.register(Class ...) 注册组件（详细见 《目录 AnnotatedBeanDefinitionReader》）
        
                
                
## AnnotatedBeanDefinitionReader：注解的 BeanDefinition 读取器

- new 构造器参数 BeanDefinitionRegistry
    + new AnnotatedBeanDefinitionReader：注解的 BeanDefinition 读取器
        + getOrCreateEnvironment(BeanDefinitionRegistry)
        + new ConditionEvaluator：条件评估器（用于 @Conditional 的 Condition.matches）
            + deduceBeanFactory （beanFactory：自身，applicationContext：组合的 beanFactory）
            + deduceEnvironment
            + deduceResourceLoader (容器自身)
            + deduceClassLoader
        + AnnotationConfigUtils.registerAnnotationConfigProcessors 注册注解配置处理器
            + 注册内建 BeanDefinition
                + ConfigurationClassPostProcessor：处理 @Configuration 
                + AutowiredAnnotationBeanPostProcessor：处理 @Autowired @Value @Inject 等
                + CommonAnnotationBeanPostProcessor：处理 @Resource @PostConstructor @PreDestroy 等
                + PersistenceAnnotationBeanPostProcessor：处理 jpa
                + EventListenerMethodProcessor：处理 @EventListener
                + DefaultEventListenerFactory
            + 调用 DefaultListableBeanFactory.registerBeanDefinition 注册这些内建 BeanDefinition

- register(Class ...) 注册组件
    + registerBean(Class beanClass) 注册 bean
        + doRegisterBean 注册 bean
            + new AnnotatedGenericBeanDefinition
                + set beanClass
                + AnnotationMetadata.introspect(beanClass) 通过自省获得元数据
                    + AnnotationsScanner.getDeclaredAnnotations 缓存注解的元信息
            + 判断是否要跳过注册 ConditionEvaluator.shouldSkip
                + 通过 Conditional 注解内的 Condition 接口实现的逻辑判断
            + ScopeMetadataResolver.resolveScopeMetadata 解析 @Scope 信息，无则为 singleton
                + 使用 AnnotationScopeMetadataResolver 的实现
            + 如果 beanName 为空，则使用 BeanNameGenerator.generateBeanName 生成 beanName
                + 使用 AnnotationBeanNameGenerator 的实现
                + 判断是不是 Spring bean（@Component 及其派生、@ManagedBean、@Named）
                    + 如果是且 value 不为空，则使用 value 值作为 beanName
                    + 否则，按类名首字母小写来生成 beanName
            + AnnotationConfigUtils.processCommonDefinitionAnnotations 处理普通定义的注解
                + @Lazy
                + @Primary
                + @DependsOn
                + @Role
                + @Description
            + 处理参数中是否传递了限定注解
                + @Lazy
                + @Primary
                + @Qualifier
            + BeanDefinitionCustomizer.customize: BeanDefinition 定制器执行
            + new BeanDefinitionHolder：持有 BeanDefinition 实例和 beanName
            + AnnotationConfigUtils.applyScopedProxyMode 处理代理，如果需要的话
                + ScopedProxyMode.INTERFACE：jdk 动态代理
                + ScopedProxyMode.TARGET_CLASS：cglib 动态代理
            + BeanDefinitionReaderUtils.registerBeanDefinition 注册 bean
                + 使用 DefaultListableBeanFactory.registerBeanDefinition 注册
                + 注册别名
        
## XmlBeanDefinitionReader：Xml 的 BeanDefinition 读取器

extends AbstractBeanDefinitionReader

- new
    + 构造器参数 BeanDefinitionRegistry，传递上下文的 ResourceLoader、Environment
    
- AbstractBeanDefinitionReader.loadBeanDefinitions ①
    + 判断当前 ResourceLoader 是 ResourcePatternResolver 么（ApplicationContext 实现了 ResourcePatternResolver）
        + 是，则取到 Resource[]
        + 否，则取到 Resource
    + XmlBeanDefinitionReader.loadBeanDefinitions(EncodedResource) 包装 Resource 到 EncodeResource, 开始读取配置
    + ThreadLocal 保存当前线程 EncodedResource
    + doLoadDocument 把资源加载成文档
    + registerBeanDefinitions 注册 BeanDefinition
        + 创建 BeanDefinitionDocumentReader： BeanDefinition 文档读取器, DefaultBeanDefinitionDocumentReader
        + BeanDefinitionDocumentReader.registerBeanDefinitions 读取器注册 BeanDefinition
            + 创建 XmlReaderContext：xml 读取上下文
                + 创建 NamespaceHandlerResolver
                    + namespaceHandlerResolver == null 则创建 DefaultNamespaceHandlerResolver
                        + 关联 META-INF/spring.handlers **？？？**
            + DefaultBeanDefinitionDocumentReader.doRegisterBeanDefinitions
                + 创建 BeanDefinitionParserDelegate 代理
                + 判断命名空间的 profile，与当前不符则 return
                + preProcessXml：xml 前处理，默认无实现，可继承 DefaultBeanDefinitionDocumentReader 进行实现
                + parseBeanDefinitions：解析 BeanDefinition
                    + 遍历节点元素（按照文件中元素的声明顺序）
                        + parseDefaultElement：解析元素
                            + 处理 import
                                + 处理 spring expression 获取真实路径
                                + 判断绝对路径 or 相对路径
                                    + 相对路径要转换路径资源
                                + goto ① 解析 import 的 xml 文件
                            + 处理 alias
                            + 处理 bean
                                + processBeanDefinition 处理 BeanDefinition
                                    + BeanDefinitionParserDelegate.parseBeanDefinitionElement：解析 BeanDefinition 元素，此时没有 beanName
                                        + 获取 id 赋值 beanName, 处理 alias, beanName 查重
                                        + parseBeanDefinitionElement：解析 BeanDefinition 元素，此时有 beanName
                                            + 解析 class, parent
                                            + createBeanDefinition：通过 class 和 parent，创建 BeanDefinition
                                                + new GenericBeanDefinition
                                                + 设置 parent class
                                                + 如果 classLoader 不为空，则设置 beanClass，否则设置 beanClassName
                                            + parseBeanDefinitionAttributes：解析 BeanDefinition 的属性
                                                + scope, abstract, lazy-init, autowire, depend-on, autowire-candidate, 
                                                primary, init-method, destroy-method, factory-method, factory-bean
                                            + 根据 child node 解析 description
                                            + parseMetaElements：解析 meta 元素
                                            + parseLookupOverrideSubElements：解析 lookup-method 方法查找依赖
                                            + parseReplacedMethodSubElements：解析 replaced-method **？？？**
                                            + parseConstructorArgElements：解析 constructor-arg 构造器参数
                                            + parsePropertyElements：解析 property 参数
                                            + parseQualifierElements：解析 qualifier 限定
                                            + 设置 Resource、Source
                                        + 如果 beanName 为空，生成 beanName
                                        + alias to array
                                        + return BeanDefinitionHolder
                                     + BeanDefinitionParserDelegate.decorateBeanDefinitionIfRequired：装饰存在的属性和子节点属性 **？？？**
                                     + BeanDefinitionReaderUtils.registerBeanDefinition 注册 BeanDefinition
                            + 处理 beans
                + postProcessXml：xml 后处理，默认无实现，可继承 DefaultBeanDefinitionDocumentReader 进行实现
            
            
## 注册 BeanDefinition 过程

DefaultListableBeanFactory.registerBeanDefinition 方法

- validate() 校验
- 判断 BeanDefinition 是否存在
    + 已存在，则进行一些校验，通过则 put 到 beanDefinitionMap
    + 不存在，判断是否已经开始创建
        + 已经开始 bean 创建
            + 需要对 beanDefinitionMap 加锁，保证线程安全
            + 使用 CopyOnWrite 方式进行新增
            + 手动移除 singleton beanName
        + 未开始
            + put(beanName, beanDefinition) to beanDefinitionMap
            + add(beanName) to beanDefinitionNames
- 判断是否需要 resetBeanDefinition

相关属性说明 
- beanDefinitionMap 保存 beanName 与 beanDefinition 的 ConcurrentHashMap
- beanDefinitionNames 用来控制 beanDefinition 的顺序
- frozenBeanDefinitionNames 冻结 beanDefinition 注册的标志


    
