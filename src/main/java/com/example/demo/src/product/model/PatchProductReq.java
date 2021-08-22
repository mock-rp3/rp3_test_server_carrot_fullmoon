package com.example.demo.src.product.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchProductReq {
    private int productIdx;
    private String title;
    private String description;
    private int price;
    private String canProposal;
    private int categoryId;
}
