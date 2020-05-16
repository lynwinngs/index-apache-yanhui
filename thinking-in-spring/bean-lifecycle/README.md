# bean 生命周期

### 配置

配置主要分三种方式

- 资源文件的配置，包括 xml 方式， properties 方式
- 注解方式，包括 @Component 及其派生注解， @Configuration 内使用的 @Bean 注解
- API 方式，主要是通过实例化 BeanDefinition 对象，或者使用 BeanDefinitionBuilder构建


### 解析

解析过程主要针对两种方式

- 配置文件配置的 bean definition
- 注解方式配置的 bean definition

通过 API 方式配置的 bean 不需要再进行解析，因为 BeanDefinition 已经被实例化