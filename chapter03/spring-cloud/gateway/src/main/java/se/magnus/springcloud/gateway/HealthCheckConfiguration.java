package se.magnus.springcloud.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.CompositeReactiveHealthContributor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class HealthCheckConfiguration {

    private static final Logger LOG = LoggerFactory.getLogger(HealthCheckConfiguration.class);



    private final WebClient.Builder webClientBuilder;

    private WebClient webClient;

    @Autowired
    public HealthCheckConfiguration(
            WebClient.Builder webClientBuilder
    ) {
        this.webClientBuilder = webClientBuilder;
            }

    @Bean
    ReactiveHealthIndicator healthcheckMicroservices() {
         Map<String, ReactiveHealthIndicator> indicators = new HashMap<>();
        indicators.put("auth-server",           () -> getHealth("http://auth-server"));
        indicators.put("product",           () -> getHealth("http://product"));
        indicators.put("recommendation",    () -> getHealth("http://recommendation"));
        indicators.put("review",            () -> getHealth("http://review"));
        indicators.put("product-composite", () -> getHealth("http://product-composite"));

        ReactiveHealthIndicator registry1 = (ReactiveHealthIndicator) CompositeReactiveHealthContributor.fromMap(indicators);
        return registry1;
    }

    private Mono<Health> getHealth(String url) {
        url += "/actuator/health";
        LOG.debug("Will call the Health API on URL: {}", url);
        return getWebClient().get().uri(url).retrieve().bodyToMono(String.class)
                .map(s -> new Health.Builder().up().build())
                .onErrorResume(ex -> Mono.just(new Health.Builder().down(ex).build()))
                .log();
    }

    private WebClient getWebClient() {
        if (webClient == null) {
            webClient = webClientBuilder.build();
        }
        return webClient;
    }
}