package fh.bswe.statusserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StatusserverApplication {

    public static void main(String[] args) {
        //  AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        SpringApplication.run(StatusserverApplication.class, args);
    }


}
