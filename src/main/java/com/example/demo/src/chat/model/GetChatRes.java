package com.example.demo.src.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetChatRes {
    private int chatRoomIdx;
    private int senderUserInfoId;
    private String createdAt;
    private String updatedAt;
    private int productId;
    private String productImageUrl;
    private int receiverUserInfoId;
    private String profileImageUrl;
    private String senderRegionNameTown;
    private String senderNickname;
    private String senderChatMessage;
}
