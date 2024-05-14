package com.productcatalogservice.dtos;

import com.productcatalogservice.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private Long id;

    private String name;

    private String description;

    private String imageUrl;

    private Double price;

    private CategoryDto category;
}
