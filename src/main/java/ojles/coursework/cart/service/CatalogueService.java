package ojles.coursework.cart.service;

import lombok.RequiredArgsConstructor;
import ojles.coursework.cart.dto.ProductDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RequiredArgsConstructor
public class CatalogueService {
    private final String catalogueUrl;
    private final RestTemplate restTemplate;

    public List<ProductDto> getProductsByIds(List<Long> productIds) {
        String uri = UriComponentsBuilder.newInstance()
                .host(catalogueUrl)
                .path("/products")
                .queryParam("ids", productIds.toArray())
                .build()
                .encode()
                .toUriString();
        return restTemplate.exchange(uri, HttpMethod.GET, null, new ParameterizedTypeReference<List<ProductDto>>() {
        })
                .getBody();
    }
}
