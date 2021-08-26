package com.example.demo.src.community.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCommunityCategory {
    private int communityIdx;
    private String createdAt;
    private String description;
    private String name;
    private String regionNameTown;
    private int reactionCount;
    private int commentCount;
    private int categoryId;
}
