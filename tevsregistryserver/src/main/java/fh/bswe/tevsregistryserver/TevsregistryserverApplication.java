package fh.bswe.tevsregistryserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class TevsregistryserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(TevsregistryserverApplication.class, args);
    }

}
