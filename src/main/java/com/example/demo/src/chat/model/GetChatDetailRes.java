package com.example.demo.src.chat.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetChatDetailRes {
    private int chatRoomIdx;
    private String senderNickname;
    private int senderMannerGrade;
    private int productId;
    private String productImageUrl;
    private String productStatus;
    private int productPrice;
    private String productTitle;
    private String productCanProposal;
    private int senderUserIdx;
    private String senderProfileImage;
    private String chatCreatedAt;
    private String chatMessage;
}
