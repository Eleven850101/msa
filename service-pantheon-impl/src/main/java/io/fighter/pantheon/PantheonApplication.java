package io.fighter.pantheon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author xiasx
 * @create 2019-03-07 13:55
 **/

@SpringBootApplication(scanBasePackages = {"io.fighter"})
@EnableDiscoveryClient
public class PantheonApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(PantheonApplication.class, args);
        System.out.println(applicationContext.getEnvironment().getProperty("my.name"));
        System.out.println(applicationContext.getEnvironment().getProperty("my.motto"));
    }
}
