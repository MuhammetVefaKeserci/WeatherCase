package durumu.hava;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {
    private RestTemplate restTemplate;
    @Bean
public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}