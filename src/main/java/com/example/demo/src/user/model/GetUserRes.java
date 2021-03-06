package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {
    private int userInfoIdx;
    private String status;
    private String createdAt;
    private String phoneNumber;
    private String nickname;
    private String profileImageUrl;
}
