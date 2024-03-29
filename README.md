<h1 style="text-align: center">BambooCloud基于SpringCloud的微服务架构</h1>

## 一、框架项目简介
#### 致力于提供高性能，高可用，高并发的服务器设计方案。构建企业级微服务生态系统。

## 二、框架模块简介
### bamboo-business（具体业务架构应用层）
    1.business-admin（后台管理系统业务服务）
    2.business-official（技术官网系统业务服务）
### bamboo-microservices（微服务架构服务提供层）
    1.microservices-auth（服务认证鉴权中心服务）
    2.microservices-notice（通知路由服务）
    3.microservices-payment（支付网关服务）
    4.microservices-document（文档解析转换服务）
### bamboo-support（微服务治理框架支撑层）
    1.support-cloud-core（微服务架构核心模块-服务注册发现统一配置管理）
    2.support-cloud-gateway（微服务架构统一网关路由服务）
    3.support-cloud-seata（微服务架构-分布式事务支持）
    4.support-cloud-rocketmq（微服务架构-消息队列RocketMQ支持）
### bamboo-technology（技术框架基础支撑层）
    1.bamboo-common（通用模块-为本技术框架提供支持【不可对外依赖】）
    2.bamboo-core（核心模块-为上层框架或服务提供核心支持）
    3.bamboo-data（数据模块-为本技术框架提供数据相关支持【不可对外依赖】）
    4.bamboo-data-jpa（jpa支持模块-为上层服务提供jpa访问框架支持）
    5.bamboo-data-lucene（lucene支持模块-为上层服务提供大文本全文搜索支持）
    6.bamboo-data-mybatis（mybatis支持模块-为上层服务提供mybatis访问框架支持）
    7.bamboo-data-redis（redis支持模块-为上层服务提供Redis访问使用支持）
    8.bamboo-data-mongo（mongodb支持模块-为上层服务提供Mongodb访问使用支持）
    9.bamboo-security（服务安全支持模块-为上层服务提供鉴权标准）
    10.bamboo-mq-rocketmq（消息队列模块-为上层服务提供RocketMQ使用准则）
    11.bamboo-mq-rabbitmq（消息队列模块-为上层服务提供RabbitMQ使用准则）

## 三、框架能力展示
#### 请移步本技术框架最佳实践，BAMBOO后台管理系统。
- 演示地址：http://47.116.33.28/#/dashboard
- 演示账号：demo 密码：123456（部分功能受限）
- 超管账号：admin 密码：Bamboo123456/（可体验完整功能）
- 前端项目地址：[admin-vue](https://github.com/jiefangen/frontend-vue/tree/main/admin-vue)
- 分布式框架项目地址：[bamboo](https://github.com/jiefangen/bamboo)
- 微服务框架项目地址：[bamboo-cloud](https://github.com/jiefangen/bamboo-cloud)

## 四、架构设计指导
### 架构设计风格
    1.分层架构：将软件系统分为多个层次，每个层次负责不同的功能，层与层之间通过接口进行通信。这种架构设计风格能够使得软件系统的结构更加清晰，同时也更加易于维护和扩展。
    2.微服务架构：将软件系统拆分为多个小型的、独立的服务，每个服务负责一个特定的功能，不同的服务之间通过接口进行通信。这种架构设计风格能够提高软件系统的可伸缩性和可维护性，同时也更加容错。
    3.事件驱动架构：通过事件机制进行消息传递和通信，将软件系统分为多个松散耦合的组件，每个组件都可以根据事件进行操作。这种架构设计风格能够提高软件系统的灵活性和可扩展性，同时也可以支持异步消息处理。
    4.领域驱动设计：将软件系统设计为由多个领域模型组成的整体，每个领域模型都负责处理一个特定的业务领域。这种架构设计风格能够提高软件系统的可维护性和可扩展性，同时也更加符合业务需求。
    5.数据驱动架构：将软件系统设计为由多个数据流组成的整体，每个数据流负责处理一个特定的数据源。这种架构设计风格能够提高软件系统的数据处理能力和效率，同时也更加容错和稳定。

### 微服务架构设计
    1.单一职责原则：每个微服务都应该专注于一个特定的业务领域或功能。这样可以使微服务更加模块化，易于维护和扩展。
    2.服务自治：每个微服务都应该独立运行，没有共享的状态或数据。这样可以减少服务之间的依赖性，提高可靠性和可伸缩性。
    3.轻量级通信：微服务之间的通信应该采用轻量级的协议，例如RESTful API或消息队列。这样可以提高通信效率和可靠性。
    4.容错性：每个微服务都应该具有容错机制，例如重试失败的操作或在服务不可用时使用备用服务。
    5.事件驱动架构：微服务可以采用事件驱动的架构，通过事件触发不同的服务进行操作，以实现松耦合和可扩展性。
    6.分布式数据管理：微服务可以采用分布式数据管理方案，例如使用数据库分片或NoSQL数据库。这样可以提高性能和可扩展性。
    7.安全性：每个微服务都应该具有适当的安全措施，例如身份验证和授权，以保护敏感数据和业务逻辑。
    8.监控和日志记录：每个微服务都应该具有监控和日志记录机制，以便快速发现和修复问题。这可以通过使用工具如Prometheus、ELK等实现。

### 五、反馈交流
- 邮箱：jiefangen@gmail.com
