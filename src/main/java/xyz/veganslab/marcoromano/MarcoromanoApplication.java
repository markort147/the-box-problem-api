package xyz.veganslab.marcoromano;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class MarcoromanoApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarcoromanoApplication.class, args);
    }

}
