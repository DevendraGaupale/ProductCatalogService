package com.productcatalogservice.controllers;

import com.productcatalogservice.dtos.ProductDto;
import com.productcatalogservice.models.Category;
import com.productcatalogservice.models.Product;
import com.productcatalogservice.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    IProductService productService;

    @GetMapping
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("{id}")
    public Product getProduct(@PathVariable("id") Long productId){
        if(productId < 1)
            throw new IllegalArgumentException("Invalid productId, please pass valid id");
        return productService.getProduct(productId);
    }

    @PostMapping
    public Product createProduct(@RequestBody ProductDto productDto){
        return productService.createProduct(getProduct(productDto));
    }

    @PutMapping("{id}")
    public Product replaceProduct(@PathVariable("id") Long productId, @RequestBody ProductDto productDto){
        if(productId < 1)
            throw new IllegalArgumentException("Invalid productId, please pass valid id");
        return productService.replaceProduct(productId, getProduct(productDto));
    }

    @DeleteMapping("{id}")
    public Product deleteProduct(@PathVariable("id") Long productId){
        if(productId < 1)
            throw new IllegalArgumentException("Invalid productId, please pass valid id");
        return productService.deleteProduct(productId);
    }

    private Product getProduct(ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        Category category = new Category();
        category.setName(productDto.getCategory().getName());
        product.setCategory(category);
        return product;
    }
}
