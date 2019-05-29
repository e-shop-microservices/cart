package ojles.coursework.cart.configuration;

import ojles.coursework.cart.service.CatalogueService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CatalogueConfiguration {
    private String catalogueUrl = "http://localhost:8081";

    @Bean
    public CatalogueService catalogueService() {
        return new CatalogueService(catalogueUrl, catalogueRestTemplate());
    }

    @Bean
    public RestTemplate catalogueRestTemplate() {
        return new RestTemplate();
    }
}
