package pt.joja.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ScheduledService {

    @Scheduled(cron = "0,5,10,25-40/3 * * * * MON-SAT")
    public void hello() {
        System.out.println("hello!" + new Date());
    }

}
