package org.example.app.config;


import org.apache.log4j.Logger;
import org.example.app.services.UserRepository;
import org.example.web.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity // активация spring security
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    Logger logger = Logger.getLogger(AppSecurityConfig.class);

    private final UserRepository userRepository;

    @Autowired
    public AppSecurityConfig(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.info("populate inmemory auth user");

        for (User user : userRepository.retrieveAll()) {
            auth
                    .inMemoryAuthentication()
                    .withUser(user.getUsername())
                    .password(passwordEncoder().encode(user.getPassword()))
                    .roles("USER");
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        logger.info("config http security");
        http.headers().frameOptions().disable();
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login*").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/user/auth")
                .defaultSuccessUrl("/books/shelf", true)
                .failureUrl("/login");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        logger.info("config web security");

        web
                .ignoring()
                .antMatchers("/images/**");
    }
}
