package com.example.external;

import com.example.time_serv.times.TimeProviderProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

@Service
@ConditionalOnProperty(value = "spring.profiles.active", havingValue = "prod", matchIfMissing = true)
public class TimeService implements TimeServiceInterface {

    private final TimeProviderProperties timeProviderProperties;

    @Autowired
    public TimeService(TimeProviderProperties timeProviderProperties) {
        this.timeProviderProperties = timeProviderProperties;
    }

    public void printCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(timeProviderProperties.getFormat());
        Logger.getLogger(TimeService.class.getName()).info(timeProviderProperties.getDescription());
        Logger.getLogger(TimeService.class.getName()).info("current time: " + dateFormat.format(new Date()));
    }
}
