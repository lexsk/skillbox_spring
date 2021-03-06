package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.web.dto.LoginForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

// HTTP-методы GET, POST, PUT, DELETE, HEAD, OPTIONS

@Controller
// благодаря анноции @Controller: dispatcherServlet, а до него контейнер инверсии контроля (DI контейнер) распознает этот класс как бин

/*@Controller — это специализированная версия базовой аннотации @Component, обозначающей, что аннотируемый ею класс (бин)
является компонентом Spring-приложения и, в том числе, кандидатом на распознавание в рамках component-scanning и включение
в IoC-контейнер*/

/*
Аннотация @RequestMapping используется для мэппинга
http-запросов на соответствующие методы в классах обработчиках.
Для более тонкой настройки мэппинга у этой аннотации есть несколько
параметров: value, name, consumes, produces, headers, method, params
 */

@RequestMapping(value = "/login")
public class LoginController {

    private final Logger logger = Logger.getLogger(LoginController.class);

    @GetMapping
    public String login(Model model) {
        logger.info("GET /login returns login_page.html");
        model.addAttribute("loginForm", new LoginForm());
        return "login_page";
    }

}
