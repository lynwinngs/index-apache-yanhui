# IoC 容器扩展学习

# bean 生命周期

## AnnotationConfigApplicationContext

- new
    + 加载类 ContextClosedEvent
    + new AnnotatedBeanDefinitionReader
        + getOrCreateEnvironment(BeanDefinitionRegistry)
        + new ConditionEvaluator
            + deduceBeanFactory （beanFactory：自身，applicationContext：组合的 beanFactory）
            + deduceEnvironment
            + deduceResourceLoader (容器自身)
            + deduceClassLoader
        + AnnotationConfigUtils.registerAnnotationConfigProcessors
            + 注册内建 BeanDefinition
                + ConfigurationClassPostProcessor
                + AutowiredAnnotationBeanPostProcessor
                + CommonAnnotationBeanPostProcessor
                + PersistenceAnnotationBeanPostProcessor
                + EventListenerMethodProcessor
                + DefaultEventListenerFactory
            + 调用 DefaultListableBeanFactory.registerBeanDefinition 注册这些内建 BeanDefinition
    + new ClassPathBeanDefinitionScanner
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
        

## 注册 BeanDefinition 过程

DefaultListableBeanFactory.registerBeanDefinition 方法

- validate() 校验
- 判断 BeanDefinition 是否存在
    + 已存在，则进行一些校验，通过则 put 到 beanDefinitionMap
    + 不存在，判断是否已经开始创建
        + 已经开始
            + 需要加锁，保证线程安全
            + 使用 CopyOnWrite 方式进行新增
        + 未开始
            + put(beanName, beanDefinition) to beanDefinitionMap
            + add(beanName) to beanDefinitionNames
- 判断是否需要 resetBeanDefinition

相关属性说明 
- beanDefinitionMap 保存 beanName 与 beanDefinition 的 ConcurrentHashMap
- beanDefinitionNames 用来控制 beanDefinition 的顺序
- frozenBeanDefinitionNames 冻结 beanDefinition 注册的标志


    
