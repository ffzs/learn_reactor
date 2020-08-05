# Reactor 3 （1）:  走进响应式编程

随着zuul不再维护，spring官方有意推Spring Cloud Gateway为下一代网关，由于Spring Cloud Gateway是通过Webflux实现的，完全使用了响应式web框架，同时Webflux又是通过Reactor实现的，因此为了更好是使用Spring Cloud有必要学习一下响应式编程，Webflux，Reactor

## 响应式编程

开发人员一般都会写一些监控的脚本，通过定时的访问，来确定一写事件是否发生，发生的话将内容通过邮件发送。如果对时间粒度要求宽泛还好，如果要求比较严格，就要一直访问，造成不必要的资源浪费。为了解决这种命令式编程的痛点，响应式编程应运而生，通过设置消息的订阅者和消息的发送者来解决这个问题，通过事件驱动，一旦事件发生，发送者将消息推送给订阅者。

举个收外卖的例子：

你今天点了一个外卖，但是你不知道何时会送达，小区外卖进不来，你只能到小区门口去拿：

1. 命令式：由于不知到几点到，你就每隔5分钟去小区门口看一下，如果外卖到了就拿回来，没到进入下个循环
2. 响应式：小哥到了小区门口，打电话通知到小区门口去外卖

根据上面的例子明白响应式编程这种事件驱动的好处。

而且响应式将发和收拆开将同步过程拆成异步的过程，对合理利用资源提升效率更有帮助

## Reactor 3介绍

Reactor 3框架是Pivotal（Spring 母公司）基于Reactive Programming思想实现的。Spring Cloud 使用的响应式web框架 Webflux由Reactor实现。

reactor3 优势特点：

+ 可以将处理的数据视为steam，这也使得reactor的用法和java8的stream很像，流式处理数据更加函数式减少副作用，更加方便用于多线程处理

+ 可读性强，流式的编写，每一环节逻辑更清晰，避免了回调地狱

+ 组合性强，可使用先前任务的结果将输入反馈给后续任务

+ 事件的生产者和消费者拆成 **Publisher**和**Publisher**， **Publisher**只管准备数据，在**Publisher**订阅之前不会发生消费情况

+ 背压，Reactor比较重要的特征，事件下游可以通过反馈调节上游的生产速度

+ cold & hot ：只有在观察者订阅的时候才会使用的数据源为cold，就是说数据源是Lazy的；hot一直在变，例如数据流，你不观察数据流也不会停止等你来观察

  

## 学习准备

+ 官网：https://projectreactor.io/docs/core/release/reference/

通过idea创建新项目，使用gradle管理项目依赖：

```groovy
dependencies {
    implementation 'junit:junit:4.12'
    implementation 'org.junit.jupiter:junit-jupiter-api:5.2.0'
    implementation 'org.slf4j:slf4j-api:1.7.25'
    implementation 'org.projectlombok:lombok:1.18.12'
    implementation 'ch.qos.logback:logback-classic:1.2.3'
    implementation platform('io.projectreactor:reactor-bom:Bismuth-RELEASE')
    implementation 'io.projectreactor:reactor-core'
    implementation 'io.projectreactor:reactor-test'
    implementation 'io.projectreactor.addons:reactor-extra'
}
```

