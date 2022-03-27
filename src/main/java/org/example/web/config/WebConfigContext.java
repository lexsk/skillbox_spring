package org.example.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

// конфигурация через аннотации. Аналог файла web-config.xml который будет удален или переименован, так как не используется

@Configuration
@ComponentScan(basePackages = "org.example.web") // <context:component-scan base-package="org.example.web"/>
@EnableWebMvc // <mvc:annotation-driven/>
public class WebConfigContext implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("classpath:images"); // <mvc:resources mapping="/**" location="classpath:images"/>
    }

    @Bean
    // <bean id="templateResolver" class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">...</bean>
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setPrefix("/WEB-INF/views/"); // <property name="prefix" value="/WEB-INF/views/"/>
        templateResolver.setSuffix(".html"); // <property name="suffix" value=".html"/>
        templateResolver.setTemplateMode("HTML5"); // <property name="templateMode" value="HTML"/>
        templateResolver.setCharacterEncoding("UTF-8"); // <property name="characterEncoding" value="UTF-8"/>
        return templateResolver;
    }

    @Bean // <bean id="templateEngine" class="org.thymeleaf.spring5.SpringTemplateEngine">...</bean>
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver()); // <property name="templateResolver" ref="templateResolver"/>
        return templateEngine;
    }

    @Bean // <bean class="org.thymeleaf.spring5.view.ThymeleafViewResolver">...</bean>
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine()); // <property name="templateEngine" ref="templateEngine"/>
        viewResolver.setOrder(1); // <property name="order" value="1"/>
        viewResolver.setCharacterEncoding("UTF-8"); // <property name="characterEncoding" value="UTF-8"/>
        return viewResolver;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(5000000); //5mb
        return multipartResolver;
    }

}
