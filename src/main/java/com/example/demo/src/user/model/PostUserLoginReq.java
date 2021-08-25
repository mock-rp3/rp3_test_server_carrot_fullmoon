package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostUserLoginReq {
    private String regionNameCity;
    private String regionNameGu;
    private String regionNameTown;
    private String phoneNumber;
    private String password;
    private String nickname;
    private String profileImageUrl;
}
