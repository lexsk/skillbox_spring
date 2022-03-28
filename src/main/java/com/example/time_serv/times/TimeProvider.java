package com.example.time_serv.times;

import com.example.external.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TimeProvider implements CommandLineRunner {

    private final TimeService timeService;

    @Autowired
    public TimeProvider(TimeService timeService) {
        this.timeService = timeService;
    }

    @Override
    public void run(String... args) throws Exception {
        this.timeService.printCurrentTime();
    }
}
