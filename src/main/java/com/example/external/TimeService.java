package com.example.external;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

@Service
public class TimeService {
    public void printCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        Logger.getLogger(TimeService.class.getName()).info("current time: " + dateFormat.format(new Date()));
    }
}
