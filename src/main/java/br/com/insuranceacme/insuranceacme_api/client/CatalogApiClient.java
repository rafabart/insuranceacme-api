package br.com.insuranceacme.insuranceacme_api.client;

import br.com.insuranceacme.insuranceacme_api.domain.response.OfferResponse;
import br.com.insuranceacme.insuranceacme_api.domain.response.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "catalog-api-client", url = "http://localhost:8090")
public interface CatalogApiClient {

    @GetMapping("/products/{id}")
    ProductResponse getProduct(@PathVariable("id") String id);

    @GetMapping("/offers/{id}")
    OfferResponse getOffer(@PathVariable("id") String id);

}
