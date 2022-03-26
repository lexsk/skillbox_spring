package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/*
Жизненный цикл bean
- Пре-инициализация - вызывается код, выполняемый
перед основной инициализацией.
- Инициализация бина - выполняется основная работа
по инициализации.
- Пост-инициализация выполняется перед тем, как Bean
будет задействован в работе.
- Деинициализация - этап жизненного цикла бина, в ходе которого
происходит уничтожение экземпляра бина и освобождение ресурсов

BeanFactory предоставляет различные callback-методы, которые
широко могут быть разделены на две группы: Post-Initialization
и Pre-destruction callback-методы

Aware интерфейсы
Интерфейсы — осведомители, которые позволяют бинам оповещать
контейнер о том, что им для работы требуются некоторые зависимости,
которые не относятся к бинам самого приложения

BeanPostProcessor
Интерфейс, который определяет два метода:
postProcessBeforeInitialization(), postProcessAfterInitialization()

@PostConstruct метод будет вызван после того, как бин был создан
при помощи конструктора по умолчанию

Init методы
Можно определять локальные для каждого бина, а также общие
для всех, глобальные, init и методы в конфигурационном файле

Интерфейс InitializingBean позволяет бину выполнять некоторую работу
на этапе инициализации после того, как все необходимое свойства
и зависимости были установлены

Когда бин выполнил свою работу и больше не нужен, или контейнер
завершает свою работу, начинается этап деинициализации,
в ходе которого происходит уничтожение экземпляра бина
и освобождение ресурсов

Помимо жизненного цикла у бинов есть ещё такая
характеристика, как scope — область видимости Spring bean
• @Scope(“singleton”) — один экземпляр на один контейнер;
• @Scope(“prototype”) — новый экземпляр при каждом вызове в коде;
• @Scope(“request”) — один экземпляр на каждый http-запрос;
• @Scope(“session”) — один экземпляр на http-сессию; ПО УМОЛЧАНИЮ
• @Scope(“application”) — один экземпляр на каждый контекст приложения;
• @Scope(“websocket”) — один экземпляр на websocket-соединение.
*/

public class IdProvider implements InitializingBean, DisposableBean, BeanPostProcessor {

    Logger logger = Logger.getLogger(IdProvider.class);

    public String provideId(Book book) {
        return this.hashCode() + "_" + book.hashCode();
    }

    private void initIdProvider() {
        logger.info("IdProvider init");
    }

    private void destroyIdProvider() {
        logger.info("IdProvider destroy");
    }

    private void defaultInit() {
        logger.info("Default destroy in IdProvider");
    }

    private void defaultDestroy() {
        logger.info("Default destroy in IdProvider");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("IdProvider afterPropertiesSet invoked");
    }

    @Override
    public void destroy() throws Exception {
        logger.info("IdProvider. DisposableBean destroy invoked");
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        logger.info("IdProvider. postProcessBeforeInitialization");
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        logger.info("IdProvider. postProcessAfterInitialization");
        return null;
    }

    @PostConstruct
    public void postConstructIdProvider() {
        logger.info("IdProvider. postConstructIdProvider");
    }

    @PreDestroy
    public void preDestroyIdProvider() {
        logger.info("IdProvider. preDestroyIdProvider");
    }
}
