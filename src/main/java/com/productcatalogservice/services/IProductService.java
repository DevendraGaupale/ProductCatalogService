package com.productcatalogservice.services;

import com.productcatalogservice.dtos.ProductDto;
import com.productcatalogservice.models.Product;

import java.util.List;

public interface IProductService {

    List<Product> getAllProducts();

    Product getProduct(Long productId);

    Product createProduct(Product productDto);

    Product replaceProduct(Long id, Product product);

    Product deleteProduct(Long productId);
}
