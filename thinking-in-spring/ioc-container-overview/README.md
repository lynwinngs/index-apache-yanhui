# IoC 容器总览

### 依赖查找

- 根据 Bean 名称查找
    + 实施查找 
    
        BeanFactory#getBean(String name)
    + 延迟查找 
    
        ObjectFactory#getObject()
- 根据 Bean 类型查找
    + 单个 Bean 对象 
    
        BeanFactory#getBean(Class<T> requiredType)
    + 集合 Bean 对象
    
        ListableBeanFactory#getBeansOfType(Class<T> type)
- 根据 Bean 名称 + 类型查找 

    BeanFactory#getBean(String name, Class<T> requiredType)
- 根据 Java 注解查找

    ListableBeanFactory#getBeansWithAnnotation(Class<? extends Annotation> annotationType)
    + 单个 Bean 对象
    + 集合 Bean 对象
    

### 依赖注入

- 根据 Bean 名称注入
- 根据 Bean 类型注入
    + 单个 Bean 对象
    + 集合 Bean 对象
- 注入容器内建 Bean 对象
- 注入非 Bean 对象
- 注入类型
    + 实施注入
    + 延迟注入
    
### 依赖来源

- 自定义 Bean
- 容器内建 Bean 对象
- 容器内建非 Bean 依赖

### 配置元信息

- Bean 定义配置
    + 基于 XML 文件
    + 基于 Properties 文件
    + 基于 Java 注解
    + 基于 Java API
    + 通过 Groovy 进行 DSL 配置
- Ioc 容器配置
    + 基于 XML 文件
    + 基于 Java 注解
    + 基于 Java API
- 外部化属性配置
    + 基于 Java 注解