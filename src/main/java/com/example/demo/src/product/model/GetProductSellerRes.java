package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProductSellerRes {
    private int productIdx;
    private String title;
    private int price;
    private String imageUrl;
}
