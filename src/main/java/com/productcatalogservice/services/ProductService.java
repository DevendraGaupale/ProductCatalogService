package com.productcatalogservice.services;

import com.productcatalogservice.clients.FakeStoreApiClient;
import com.productcatalogservice.dtos.CategoryDto;
import com.productcatalogservice.dtos.FakeStoreProductDto;
import com.productcatalogservice.dtos.ProductDto;
import com.productcatalogservice.models.Category;
import com.productcatalogservice.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    RestTemplateBuilder restTemplateBuilder;

    @Autowired
    FakeStoreApiClient fakeStoreApiClient;

    @Override
    public List<Product> getAllProducts(){
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto[] fakeStoreProductDtos = fakeStoreApiClient.getAllProducts();
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos){
            products.add(getProduct(fakeStoreProductDto));
        }

        return products;
    }

    @Override
    public Product getProduct(Long productId){
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.getProduct(productId);
        return getProduct(fakeStoreProductDto);
    }

    @Override
    public Product createProduct(Product product){
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.createProduct(product);
        return getProduct(fakeStoreProductDto);
    }

    @Override
    public Product replaceProduct(Long id, Product product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.replaceProduct(id, product);
        return getProduct(fakeStoreProductDto);
    }

    @Override
    public Product deleteProduct(Long productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto = fakeStoreApiClient.deleteProduct(productId);
        return getProduct(fakeStoreProductDto);
    }

    private Product getProduct(FakeStoreProductDto fakeStoreProductDto){
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setImageUrl(fakeStoreProductDto.getImage());

        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());

        product.setCategory(category);

        return product;
    }

}
