package se.magnus.springcloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;


@SpringBootApplication
public class GatewayApplication {

    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        final WebClient.Builder builder = WebClient.builder();
        return builder;
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}