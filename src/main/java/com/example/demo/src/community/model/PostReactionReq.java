package com.example.demo.src.community.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostReactionReq {
    private String reactionChoice;
    private int userInfoId;
    private int communityId;
}
