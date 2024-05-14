package com.productcatalogservice.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product extends BaseModel{

    private String name;

    private String description;

    private String imageUrl;

    private Double price;

    private Category category;
}
