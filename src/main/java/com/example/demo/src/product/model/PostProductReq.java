package com.example.demo.src.product.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostProductReq {
    private String title;
    private String description;
    private int price;
    private String canProposal;
    private int categoryId;
    private int sellerId;
    private String imageUrl;

}
