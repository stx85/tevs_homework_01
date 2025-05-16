package fh.bswe.statusserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class StatusserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatusserverApplication.class, args);
    }


}
