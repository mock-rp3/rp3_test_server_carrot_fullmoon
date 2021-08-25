package com.example.demo.src.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProductStatusRes {
    private String imageUrl;
    private String title;
    private String regionNameGu;
    private String regionNameTown;
    private String createdAt;
    private String updatedAt;
    private int price;
    private String status;
    private int sellerId;
private int productIdx;
}
