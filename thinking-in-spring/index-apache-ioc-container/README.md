# IoC 容器扩展学习

# bean 生命周期

## 图例

> **???**: 疑惑点  
> _goto class.method_: 跳转条目  
> _goto ①_: 跳转条目内锚点
> 自定义扩展: 可以对框架进行的扩展点  

### AnnotationConfigApplicationContext: 注解配置的应用上下文(核心容器)

- new
    + `AbstractApplicationContext` 类加载时, 通过 static 块加载类 `ContextClosedEvent`, 目的是让这个类尽快加载
    + `super new GenericApplicationContext`
        + `new DefaultListableBeanFactory`
    + `new AnnotatedBeanDefinitionReader`: 注解的 `BeanDefinition` 读取器(_goto `AnnotatedBeanDefinitionReader.new`_)
    + `new ClassPathBeanDefinitionScanner`: ClassPath 内的 BeanDefinition 扫描器
        + (_goto `ClassPathBeanDefinitionScanner.new`_) 
- `register`
    + `AnnotatedBeanDefinitionReader.register(Class ...)` 注册组件(_goto `AnnotatedBeanDefinitionReader.register`_)

- `refresh` (_goto `AbstractApplicationContext.refresh`_)

                
                
         
### AbstractApplicationContext

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
                    + 设置 应用上下文 和 spring el expression 处理器(_goto ①_)
        + 忽略注入接口 `ignoreDependencyInterface`
            + `EnvironmentAware`, `EmbeddedValueResolverAware`, `ResourceLoaderAware`, 
            `ApplicationEventPublisherAware`, `MessageSourceAware`, `ApplicationContextAware`
        + `ConfigurableListableBeanFactory.registerResolvableDependency` 注册 ResolvableDependency(无生命周期管理; 无法实现延迟初始化 bean; 无法通过依赖查找 )
            + (_goto `DefaultListableBeanFactory.resolvableDependencies`_)
                + `BeanFactory` -> beanFactory
                + `ResourceLoader` -> applicationContext
                + `ApplicationEventPublisher` -> applicationContext
                + `ApplicationContext` -> applicationContext 
        + `addBeanPostProcessor`
            + `new ApplicationListenerDetector` ApplicationContext 检测器, 添加到 `BeanPostProcessor` 列表
        + `loadTimeWeaver`(类加载期织入) 判断 "loadTimeWeaver" bean 是否存在
            + `new LoadTimeWeaverAwareProcessor` 具有 类加载期织入 意识的处理器, 添加到 `BeanPostProcessor` 列表
            + `new ContextTypeMatchClassLoader` 用于织入的类加载器
        + `SingletonBeanRegistry.registerSingleton` 注册 Singleton(无生命周期管理; 无法实现延迟初始化 bean)
            + (_goto `DefaultListableBeanFactory.registerSingleton`_)
                + `ConfigurableEnvironment environment`
                + `Map<String, Object> systemProperties`
                + `Map<String, Object> systemEnvironment`
    + try 块
        + `postProcessBeanFactory` beanFactory 后处理. 默认为空, 可对子类进行自定义扩展
            + spring 对 一些 web context 提供了扩展实现
                + `AbstractRefreshableWebApplicationContext`
                + `GenericWebApplicationContext`
                + `AnnotationConfigServletWebApplicationContext`
                + `AnnotationConfigServletWebServerApplicationContext`
                + `AnnotationConfigReactiveWebServerApplicationContext`
                + `ServletWebServerApplicationContext`
                + `StaticWebApplicationContext` 
                + `ResourceAdapterApplicationContext`
        + `invokeBeanFactoryPostProcessors` 调用 beanFactory 后处理
            + (_goto `PostProcessorRegistrationDelegate.invokeBeanFactoryPostProcessors`_)
            + `loadTimeWeaver`(类加载期织入) 判断 "loadTimeWeaver" bean 是否存在
                + `new LoadTimeWeaverAwareProcessor` 具有 类加载期织入 意识的处理器, 添加到 `BeanPostProcessor` 列表
                + `new ContextTypeMatchClassLoader` 用于织入的类加载器
        + `registerBeanPostProcessors` 注册 BeanPostProcessor
            + (_goto `PostProcessorRegistrationDelegate.registerBeanPostProcessors`_)
        + `initMessageSource` 国际化
            + TODO
        + `initApplicationEventMulticaster`
            + 本地 BeanFactory 包含 `applicationEventMulticaster` bean(`ApplicationEventMulticaster.class`)
                + 获取 bean 赋值 `applicationEventMulticaster` (_goto AbstractApplicationContext.getBean_)
            + otherwise 
                + `new SimpleApplicationEventMulticaster` 并赋值 `applicationEventMulticaster`
                + 注册到共享单例 bean (_goto `DefaultListableBeanFactory.registerSingleton`_)
        + `onRefresh` refresh 事件回调. 默认为空, 可对子类进行自定义扩展
            + spring 对 一些 web context 提供了扩展实现
                + `AbstractRefreshableWebApplicationContext`
                + `GenericWebApplicationContext`
                + `ReactiveWebServerApplicationContext`
                + `ServletWebServerApplicationContext`
                + `StaticWebApplicationContext` 
        + `registerListeners`
        + `finishBeanFactoryInitialization`
        + `finishRefresh`
    + catch 块
        + `destroyBeans`
        + `cancelRefresh`
    + finally 块
        + `resetCommonCaches`
        
        
- `finishBeanFactoryInitialization` 完成 BeanFactory 初始化

### AbstractBeanFactory

- `getBean`
    + TODO

- `getMergedLocalBeanDefinition`
    + 根据 `beanName` 获取 `mergedBeanDefinitions` 中 `RootBeanDefinition` 缓存
    + 缓存为 null, 则根据 `beanName` 获取 `BeanDefinition`, 再进行 merge
    + `synchronized(mergedBeanDefinitions)`
        + RootBeanDefinition mbd = 取 `mergedBeanDefinitions` 中 `RootBeanDefinition` 缓存
        (再次检查缓存, 与上面结合相当于 double check, 原因: 该方法可能会被单独调用)
        + 如果没有缓存 或 缓存陈旧 `mbd == null || mbd.stale`
            + 判断: 无 parent
                + 参数 BeanDefinition bd is a `RootBeanDefinition`
                    + clone 当前 `RootBeanDefinition` 赋值给 mbd
                + otherwise
                    + 对当前 `BeanDefinition` 新建副本 mbd = `new RootBeanDefinition(bd)`
            + 有 parent
                + 标准化 parentBeanName
                + 当前 beanName 不等于 parentBeanName
                    + 通过 parentBeanName 获取 `BeanDefinition` (_goto `getMergedBeanDefinition`_)
                + otherwise 什么情况下会等于呢 **???**
                    + 获取父 `BeanFactory`
                    + 如果父 `BeanFactory` is a `ConfigurableBeanFactory`, 
                    通过 parentBeanName 获取 `BeanDefinition` (_goto `getMergedBeanDefinition`_)
                    + otherwise `throw new NoSuchBeanDefinitionException` 无法再获取该 BeanDefinition
                + 对获得的父 `BeanDefinition` 新建副本 mbd = `new RootBeanDefinition(pbd)`
                + 将当前 `BeanDefinition` 的属性覆盖到 父 `BeanDefinition` mbd
                + 设置 scope
                + 保存缓存
        + 之前存在陈旧的缓存, 则覆盖原缓存
    + 返回 RootBeanDefinition mbd
    + 注: 整个过程获取的结果都使用数据源的副本, 对数据源并不做变更

- `getMergedBeanDefinition`①
    + 标准化 beanName
    + 如果当前 `BeanFactory` 不包含 beanName, 且父 `BeanFactory` is a `ConfigurableBeanFactory`
        + 通过 beanName 获取 `BeanDefinition` (_goto ①_)
    + otherwise:
        + (_goto `getMergedLocalBeanDefinition`_) 在本地 beanFactory 获取 mbd

### DefaultListableBeanFactory: BeanFactory 的默认实现

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


- `resolvableDependencies` 
    + 校验
    + put Class -> object

    
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
    
- `getBeanNamesForType`
    + 判断条件,如果满足则调用 (_goto `doGetBeanNamesForType`_) 并返回结果
        + 当前是否已`配置冻结` configurationFrozen(上下文完成初始化时会调用 `freezeConfiguration`)
        (_goto `freezeConfiguration`_)
        (_goto `AbstractApplicationContext.finishBeanFactoryInitialization`_)
        + 传入的参数 type 值为 null
        + allowEagerInit 值为 false, 不允许饥饿初始化
    + otherwise: 根据参数 includeNonSingletons 选择是否包含非单例的 beanNameByType
    + 取出对应 type 的 beanName 数组, 不等于 null 则返回
    + otherwise: 调用 (_goto `doGetBeanNamesForType`_), 此时必然允许饥饿初始化, 即 allowEagerInit 传值 true
    + 验证,保存缓存,返回结果
    
- 🔒`doGetBeanNamesForType`
    + 遍历 `beanDefinitionNames`(此时已注册的所有 BeanDefinition 名称)
        + 处理非别名名称
            + 获取已合并的本地 BeanDefinition (_goto `AbstractBeanFactory.getMergedLocalBeanDefinition`_)
            + 进行 bean definition 完成验证, 成功则加入返回列表
    + 遍历 `manualSingletonNames` (手动加入的 Singleton Bean)
        + 进行一些验证, 成功则加入返回列表

- `freezeConfiguration` 停止注册配置
    + configurationFrozen = true
    + copy `beanDefinitionNames` 赋值给 `frozenBeanDefinitionNames`, 
    冻结后注册的 beanDefinition 无法进入 `frozenBeanDefinitionNames`

### BeanDefinitionReaderUtils: BeanDefinition读取工具

- `registerBeanDefinition` 注册 bean
    + 使用 `DefaultListableBeanFactory.registerBeanDefinition` 注册(_goto `DefaultListableBeanFactory.registerBeanDefinition`_)
    + 注册别名(_goto `SimpleAliasRegistry.registerAlias`_)
    
### SimpleAliasRegistry: 简单别名注册表

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

### ReaderEventListener: 读取事件监听器

    默认为空, 可自定义扩展
- 方法:   
    + 在 `new XmlReaderContext` 时, 传入 `ReaderEventListener` 的实现
    + 使用 `setEventListener` 方法设置 
    
    
### PostProcessorRegistrationDelegate: 后处理注册代理

- `invokeBeanFactoryPostProcessors`
    + 声明 `Set<String> processedBeans`, 意为优先执行的 postProcessor, 后面执行其他 postProcessor 时,就会跳过这些
    + beanFactory is a `BeanDefinitionRegistry`
        + 声明 `List<BeanDefinitionRegistryPostProcessor> registryProcessors` 保存 `BeanDefinitionRegistryPostProcessor` 的实现实例
        + 声明 `List<BeanFactoryPostProcessor> regularPostProcessors` 保存 `BeanFactoryPostProcessor` 的实现
        + 循环入参的 `BeanFactoryPostProcessor` 列表, 
        这个步骤的意思大概是:可以自定义一些 BeanDefinitionRegistryPostProcessor 的实现, 
        在 ConfigurationClassPostProcessor 到达前即可以执行 postProcessBeanDefinitionRegistry 方法 **???**
            + 元素实例 is a `BeanDefinitionRegistryPostProcessor`
                + 调用每个元素的 `postProcessBeanDefinitionRegistry` 方法, BeanDefinition 注册后处理
                + add to registryProcessors
            + otherwise
                + add to regularPostProcessors
        + 声明 `List<BeanDefinitionRegistryPostProcessor> currentRegistryProcessors` 保存当前注册的  `BeanDefinitionRegistryPostProcessor` 的实现实例
        + beanFactory `getBeanNamesForType` 根据 `BeanDefinitionRegistryPostProcessor` 类型获取 postProcessorNames
        (取到的本质上是 beanNames, 实际就是取到目标类及其子类所有的 postProcessor 实例)
            + (_goto `DefaultListableBeanFactory.getBeanNamesForType`_)
            + 这里可以获得的默认实现
                + ConfigurationClassPostProcessor
        + 遍历优先级的 postProcessorNames (类实现了 `PriorityOrdered.class` 接口的)
            + postProcessorName 添加到 `processedBeans` 
            + 按顺序执行各实例的 `postProcessBeanDefinitionRegistry` 方法, BeanDefinition 注册后处理
        + 遍历非优先级的, 有序的 postProcessorNames (类没有实现 `PriorityOrdered.class` 接口,实现了 `Ordered.class` 接口的)
            + 步骤与上相同
        + 遍历剩余的 postProcessorNames
            + 步骤与上相同
        + 循环调用各个 `BeanFactoryPostProcessor` 的 `postProcessBeanFactory` 实现
    + otherwise
        + 循环调用各个 `BeanFactoryPostProcessor` 的 `postProcessBeanFactory` 实现
    + beanFactory `getBeanNamesForType` 根据 `BeanFactoryPostProcessor` 类型获取 postProcessorNames
        + (_goto `DefaultListableBeanFactory.getBeanNamesForType`_)
        + 这里可以获得的默认实现
            + ConfigurationClassPostProcessor
            + EventListenerMethodProcessor
    + 通过 `processedBeans` 保存的记录, 跳过已执行的, 继续按 优先级, 有序, 剩余的顺序, 
    对其余 `BeanFactoryPostProcessor` 实例调用 `postProcessBeanFactory` 实现
    + 清除缓存
        + 对 mergedBeanDefinition 做陈旧处理
        + 清除一些勿用缓存

- `registerBeanPostProcessors`
    + beanFactory `getBeanNamesForType` 根据 `BeanPostProcessor` 类型获取 postProcessorNames
        + (_goto `DefaultListableBeanFactory.getBeanNamesForType`_) 
        + 这里可以获得的默认实现
            + AutowiredAnnotationBeanPostProcessor
            + CommonAnnotationBeanPostProcessor
    + `addBeanPostProcessor` BeanPostProcessorChecker, 添加到 `BeanPostProcessor` 列表
        + `new BeanPostProcessorChecker` 用于检查 BeanPostProcessor 实例化期间被创建的 bean, 当 bean 不能被所有 bpp 处理 **???**
    + 遍历 BeanPostProcessor
        + 如果实现 `PriorityOrdered.class`
            + 获取 `BeanPostProcessor` 实例 (_goto `AbstractBeanFactory.getBean`_)
            + 添加到 优先级列表
            + 如果实现 `MergedBeanDefinitionPostProcessor.class`
                + 添加到 内部列表
        + 如果实现 `Ordered.class`
            + 添加到 有序(仅名称)列表
        + otherwise
            + 添加到 无序(仅名称)列表
    + 排序 优先级列表, 并注册 `BeanPostProcessor` 到 beanFactory (`registerBeanPostProcessors`)
    + 对 有序列表, 无序列表 重复上一步操作
    + 重新添加内部列表中的 `BeanPostProcessor` 
    (内部列表的 `BeanPostProcessor` 将被移除后重新添加 beanFactory beanPostProcessor 列表末尾)
    + 重新添加 ApplicationListenerDetector, 将其移动到处理器链的末端(用于拾取代理等) **???**


### ConfigurationClassUtils 配置类工具

- `checkConfigurationClassCandidate` 检查配置类候选者
    + 获取非内建 BeanDefinition 等的元数据
    + 包含@Configuration 标记 full
    + 包含@Component, @ComponentScan, @Import, @ImportResource 或包含 @Bean标记 lite
    + 其他非候选者

### ConfigurationClassParser 配置类解析

- `parse` 解析 ConfigurationClass
    + 遍历参数(配置候选者)
        + 获取候选者的 BeanDefinition, 根据 BeanDefinition 的 metaData 进行解析 (_goto `processConfigurationClass`_)
    + (_goto inner class `DeferredImportSelectorHandler.process`_)
    
- `processConfigurationClass` 处理 ConfigurationClass
    + 通过 ConditionEvaluate 进行条件判断, 是否跳过
    + 真正的开始解析 配置类 (_goto `doProcessConfigurationClass`_)
- `validate` 校验
    + TODO
    
- `process` 处理
    + TODO
    
- `doProcessConfigurationClass` (解析 Spring 组件的关键步骤. 
@Component, @ComponentScan, @Import, @ImportResource, @Bean 全部加载至 ConfigurationClass 实例内, 
再在 ConfigurationClassBeanDefinitionReader 中注册成 BeanDefinition )
    + 处理有 @Component 注解的 
        + 如果内部类也有, 则继续递归, 最后全部添加 `configurationClasses` 缓存中
    + 处理有 @PropertySource 注解的 
        + 获取该类标注的 @PropertySource 的属性值, 将属性转换成 AnnotationAttributes 对象, 
        将 name 保存至 `propertySourceNames` 缓存中
    + 处理有 @ComponentScan 注解的 
        + 获取该类标注的 @ComponentScan 的属性值, 将属性转换成 AnnotationAttributes 对象
        + 解析属性, 获取扫描包下所有 BeanDefinitionHolder 集合 (_goto `ComponentScanAnnotationParser.parse`_)
        + 遍历集合检查是否为配置类, 如果是则进行解析 (_goto `ConfigurationClassParser.processConfigurationClass`_).
        本质上就是递归解析 ConfigurationClass
    + 处理有 @Import 注解的
        + 获取 @Import value值对应的类信息, 反射出对应类
        + 如果当前配置类已在 importStack 缓存中, 说明存在循环依赖问题
    + 处理有 @ImportResource 注解的 
    + 处理有 @Bean 注解的 
    + 处理默认接口
    + 处理 super class

    
- class DeferredImportSelectorHandler
    + `process`
        + TODO

### ComponentScanAnnotationParser: @ComponentScan 注解解析器

- `parse` 解析注解属性信息 AnnotationAttributes
    + `new ClassPathBeanDefinitionScanner` 扫描器 (_goto `ClassPathBeanDefinitionScanner.new`_)
    + 进行一系列验证, 属性转换操作 (TODO)
    + 执行扫描 (_goto `ClassPathBeanDefinitionScanner.doScan`_)


### ClassPathBeanDefinitionScanner

extends ClassPathScanningCandidateComponentProvider

- `new`
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
            + 加载 `META-INF/spring.components` 中的组件到 `CandidateComponentsIndex componentsIndex` 
                + 如果在配置文件配置了要解析的类, 其他没有配置的类, 即使标注了 `@Component`, 包扫描时也不会把他们放在容器管理.
                因为设置了这个配置, 在构建时就已经设定好了扫描目标, 其他类根本就不会被考虑.
                (放在缓存中, 扫描时, 如果缓存有数据则只处理缓存中的类, 如果没有再对设定的包进行全包遍历)
                + 删库跑路什么的是会进监狱的, 想要坑你的坏东家, 就用这招吧, 他们死也查不出来 `@Component` 为什么没被容器加载
            + 如果没有配置文件, 则会扫描所有被模式注解标注的类
            
- `doScan` 执行扫描逻辑
    + 循环所有目标包
        + 查找包下候选组件 `findCandidateComponents`
            + 如果 `componentsIndex` 缓存不为空(参考 `META-INF/spring.components` 的说明), 则处理配置的类
                + new AnnotatedGenericBeanDefinition 放入缓存, 等待后续操作进行注册
            + 如果 `componentsIndex` 缓存为空, 则对包下类进行遍历分析
                + 如果属于模式注解标注的类, 则 new ScannedGenericBeanDefinition 放入缓存,等待后续操作进行注册
        + 补充一些 BeanDefinition 配置信息
        + `BeanDefinitionReaderUtils` 注册 `BeanDefinition` (_goto `BeanDefinitionReaderUtils.registerBeanDefinition`_)
        + 返回 BeanDefinitionHolder 集合

## 相关 BeanFactoryPostProcessor 接口

### ConfigurationClassPostProcessor 实现 BeanDefinitionRegistryPostProcessor -> BeanFactoryPostProcessor

解析(或者注册 **???**)阶段注册 BeanDefinition

refresh - invokeBeanFactoryPostProcessors 实例化并调用

#### 核心方法
- `postProcessBeanDefinitionRegistry` BeanDefinition 注册后处理
    + `System.identityHashCode(registry)` 获取唯一注册码
    + 判断是否已执行过(包括正在执行) `BeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry`, 已执行抛异常
    + 判断是否已执行过(包括正在执行) `BeanFactoryPostProcessor.postProcessBeanFactory`, 已执行抛异常
    + 标记开始执行 postProcessBeanDefinitionRegistry 方法
    + (_goto `processConfigBeanDefinitions`_) 处理配置类的 BeanDefinition(Configuration Class)

- `postProcessBeanFactory` BeanFactory 后处理
    + `System.identityHashCode(registry)` 获取唯一注册码
    + 判断是否已执行过(包括正在执行) `BeanFactoryPostProcessor.postProcessBeanFactory`, 已执行抛异常
    + 标记开始执行 postProcessBeanFactory 方法
    + 判断是否已执行过(包括正在执行) `BeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry`, 
    未执行则先处理配置 BeanDefinition (_goto `processConfigBeanDefinitions`_) 
    + 对 Configuration Class 类进行提升 (_goto `enhanceConfigurationClasses`_)
    + 添加 BeanPostProcessor `new ImportAwareBeanPostProcessor` (_goto `ConfigurationClassPostProcessor$ImportAwareBeanPostProcessor`_)
 
- `processConfigBeanDefinitions` 处理配置类的 BeanDefinition(Configuration Class)
    + 检查 bean 是否为配置类,是则加入到 配置类候选. 检查原则: (_goto `ConfigurationClassUtils.checkConfigurationClassCandidate`_)
    + 无候选则返回
    + 排序
    + `new ConfigurationClassParser` 用于解析 @Configuration class
    + 遍历候选
        + 解析 (_goto `ConfigurationClassParser.parse`_)
        + 校验 (_goto `ConfigurationClassParser.validate`_)
        + 根据解析结果, 加载 BeanDefinition (_goto `ConfigurationClassBeanDefinitionReader.loadBeanDefinitions`_)
        + 再次检查其他 beanDefinition 是否可作为候选
        + 注册 SingletonBean `ImportStack.class` (_goto `DefaultListableBeanFactory.registerSingleton`_)
        + 清缓存
        
- `enhanceConfigurationClasses` 提升 Configuration Class
    + TODO
        
### EventListenerMethodProcessor

解析(或者注册 **???**)阶段注册 BeanDefinition

refresh - invokeBeanFactoryPostProcessors 实例化并调用
    
    
## 相关 BeanPostProcessor 接口

- AutowiredAnnotationBeanPostProcessor
    + 解析(或者注册 **???**)阶段注册 BeanDefinition
    + refresh - registerBeanPostProcessors 实例化
- CommonAnnotationBeanPostProcessor
    + 解析(或者注册 **???**)阶段注册 BeanDefinition
    + refresh - registerBeanPostProcessors 实例化
- ApplicationContextAwareProcessor 
    + refresh - prepareBeanFactory 阶段添加
- ApplicationListenerDetector
    + refresh - prepareBeanFactory 阶段添加
    + refresh - registerBeanPostProcessor 阶段添加
- LoadTimeWeaverAwareProcessor
    + 如果存在 loadTimeWeaver bean
    + refresh - prepareBeanFactory 阶段添加
    + refresh - invokeBeanFactoryPostProcessor 阶段添加
- ConfigurationClassPostProcessor$ImportAwareBeanPostProcessor
    + refresh - invokeBeanFactoryPostProcessors 阶段添加
- PostProcessorRegistrationDelegate$BeanPostProcessorChecker
    + refresh - registerBeanPostProcessors 阶段添加

## 相关 BeanDefinitionReader:BeanDefinition读取器

### AnnotatedBeanDefinitionReader: 注解的 BeanDefinition 读取器

- new 构造器参数 `BeanDefinitionRegistry`
    + `new AnnotatedBeanDefinitionReader`: 注解的 `BeanDefinition` 读取器
        + `getOrCreateEnvironment(BeanDefinitionRegistry)`
        + `new ConditionEvaluator`: 条件评估器(用于 `@Conditional` 的 `Condition.matches`)
            + `deduceBeanFactory` (beanFactory: 自身, applicationContext: 组合的 beanFactory)
            + `deduceEnvironment`
            + `deduceResourceLoader` (容器自身)
            + `deduceClassLoader`
        + `AnnotationConfigUtils.registerAnnotationConfigProcessors` 注册注解配置处理器
            + 设置依赖比较器 `beanFactory.setDependencyComparator(AnnotationAwareOrderComparator.INSTANCE)`
            + 设置自动写入候选解析器 `beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver())`
            + 注册内建 `BeanDefinition`(_goto `DefaultListableBeanFactory.registerBeanDefinition`_)
                + `ConfigurationClassPostProcessor`: 处理 `@Configuration` 
                + `AutowiredAnnotationBeanPostProcessor`: 处理 `@Autowired` `@Value` `@Inject` 等
                + `CommonAnnotationBeanPostProcessor`: 处理 `@Resource` `@PostConstructor` `@PreDestroy` 等
                + `PersistenceAnnotationBeanPostProcessor`: 处理 jpa
                + `EventListenerMethodProcessor`: 处理 `@EventListener`
                + `DefaultEventListenerFactory`
- `register(Class ...)` 注册组件
    + `registerBean(Class beanClass)` 注册 bean
        + `doRegisterBean` 注册 bean
            + `new AnnotatedGenericBeanDefinition` 通过这种方式注册的 bean 都会被认为是 @Component 或其派生
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
        
### XmlBeanDefinitionReader: Xml 的 BeanDefinition 读取器

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
                (`XmlBeanDefinitionReader` 使用了组合模式, 组合了 `BeanDefinitionDocumentReader` 的实现, 
                可以继承 `DefaultBeanDefinitionDocumentReader` 后, 使用 `setDocumentReaderClass` 方法进行覆盖)
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
   
### ConfigurationClassBeanDefinitionReader 配置类的 BeanDefinition 读取器

- `loadBeanDefinitions` 将 ConfigurationClass 实例的属性加载成 BeanDefinition
    + TODO