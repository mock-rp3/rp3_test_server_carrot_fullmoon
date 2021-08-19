package com.example.demo.src.product.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProductRes {
    private int productIdx;
    private String createdAt;
    private String title;
    private int price;
    private String pulledAt;
    private String imageUrl;
    private String regionNameGu;
    private String regionNameTown;
    private int wishCount;
}
