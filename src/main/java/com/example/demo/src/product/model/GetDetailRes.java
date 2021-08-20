package com.example.demo.src.product.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDetailRes {
    private int productIdx;
    private String profileImageUrl;
    private String nickname;
    private String regionNameGu;
    private String regionNameTown;
    private int mannerGrade;
    private String title;
    private String name;
    private String createdAt;
    private String pulledAt;
    private String description;
    private int wishCount;
    private int price;
    private String canProposal;
    private int userInfoIdx;
}
