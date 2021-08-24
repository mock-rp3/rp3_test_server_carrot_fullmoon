package com.example.demo.src.community.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCommunityDetailRes {
    private int communityIdx;
    private String categoryName;
    private int writerIdx;
    private String writerProfileImage;
    private String writerName;
    private String writerGu;
    private String writerTown;
    private int writerTownAuthCount;
    private String contentCreatedAt;
    private String contentDescription;
    private int reactionCount;
    private int commentCount;
    private int commenterIdx;
    private String commenterProfileImage;
    private String commenterName;
    private String commentCreateAt;
    private String commentDescription;
}
