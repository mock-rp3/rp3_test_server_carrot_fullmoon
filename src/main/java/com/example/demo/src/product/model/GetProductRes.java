package com.example.demo.src.product.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetProductRes {
    private int productIdx;
    private String status;
    private String createdAt;
    private String title;
    private String description;
    private int price;
    private int viewCount;
    private String pulledAt;
    private String category;
    private String canProposal;
    private int sellerId;
    private int regionId;
}
