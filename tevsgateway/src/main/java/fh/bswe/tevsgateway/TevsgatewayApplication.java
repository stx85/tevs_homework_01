package fh.bswe.tevsgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TevsgatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(TevsgatewayApplication.class, args);
	}

}
