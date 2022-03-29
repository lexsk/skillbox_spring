package com.example.time_serv.times;

import com.example.external.TimeService;
import com.example.external.TimeServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
//@Profile("prod")
public class TimeProvider implements CommandLineRunner {

    private final TimeServiceInterface timeService;
    private final TimeProviderProperties timeProviderProperties;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${custom.welcomeMessage}")
    private String welcomeMessage;

    @Autowired
    public TimeProvider(TimeServiceInterface timeService, TimeProviderProperties timeProviderProperties) {
        this.timeService = timeService;
        this.timeProviderProperties = timeProviderProperties;
    }

    @Override
    public void run(String... args) throws Exception {
        Logger.getLogger(TimeProvider.class.getName()).info("running " + appName + " with profile " + timeProviderProperties.getProfile());
        Logger.getLogger(TimeProvider.class.getName()).info(welcomeMessage);
        this.timeService.printCurrentTime();
    }
}
