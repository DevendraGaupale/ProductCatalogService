package com.productcatalogservice.clients;

import com.productcatalogservice.dtos.FakeStoreProductDto;
import com.productcatalogservice.models.Category;
import com.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class FakeStoreApiClient {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    public FakeStoreProductDto[] getAllProducts(){
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreProductDto[].class).getBody();
        return fakeStoreProductDtos;
    }

    public FakeStoreProductDto getProduct(Long productId){
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class,productId).getBody();
        return fakeStoreProductDto;
    }

    public FakeStoreProductDto createProduct(Product product){
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDtoReq = getFakeStoreProductDto(product);
        FakeStoreProductDto fakeStoreProductDto = restTemplate.postForEntity("https://fakestoreapi.com/products", fakeStoreProductDtoReq, FakeStoreProductDto.class).getBody();
        return fakeStoreProductDto;
    }

    public FakeStoreProductDto replaceProduct(Long id, Product product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDtoReq = getFakeStoreProductDto(product);
        FakeStoreProductDto fakeStoreProductDto = putForEntity("https://fakestoreapi.com/products/{id}", fakeStoreProductDtoReq, FakeStoreProductDto.class, id).getBody();
        return fakeStoreProductDto;
    }

    public FakeStoreProductDto deleteProduct(Long productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto = deleteForEntity("https://fakestoreapi.com/products/{id}", FakeStoreProductDto.class, productId).getBody();
        return fakeStoreProductDto;
    }

    private <T> ResponseEntity<T> putForEntity(String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables);
    }

    private <T> ResponseEntity<T> deleteForEntity(String url, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.acceptHeaderRequestCallback(responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, HttpMethod.DELETE, requestCallback, responseExtractor, uriVariables);
    }

    private FakeStoreProductDto getFakeStoreProductDto(Product product){
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setId(product.getId());
        fakeStoreProductDto.setTitle(product.getName());
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setCategory(product.getCategory().getName());
        return fakeStoreProductDto;
    }
}
