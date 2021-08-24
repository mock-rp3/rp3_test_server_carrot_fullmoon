package com.example.demo.src.community.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostCommunityReq {
    public String description;
    public int categoryId;
    public int regionId;
    public int userInfoId;
}
