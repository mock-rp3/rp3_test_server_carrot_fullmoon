package com.example.demo.src.chat;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.chat.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.SUCCESS_DELETE_COMMUNITY;
import static com.example.demo.config.BaseResponseStatus.SUCCESS_UPDATE_COMMUNITY;

@RestController
@RequestMapping("/app/chat")
public class ChatController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ChatProvider chatProvider;
    private final ChatService chatService;

    public ChatController(ChatProvider chatProvider, ChatService chatService) {
        this.chatProvider = chatProvider;
        this.chatService = chatService;
    }
    /**
     * 채팅방 전체 조회 API [메인화면]
     * [GET] /app/chat
     */
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/products
    public BaseResponse<List<GetChatRes>> getChatRooms() {
        try {
            List<GetChatRes> getChatResList = chatProvider.getChatRooms();
            return new BaseResponse<>(getChatResList);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 채팅방 입장 API
     * [GET] /app/chat/:chatIdx
     */
    @ResponseBody
    @GetMapping("/{chatIdx}") // (GET) 127.0.0.1:9000/app/products/:productIdx
    public BaseResponse<List<GetChatDetailRes>> getChatRoom(@PathVariable("chatIdx") int chatIdx) {
        try {
            List<GetChatDetailRes> getChatDetailResList = chatProvider.getChatRoom(chatIdx);
            return new BaseResponse<>(getChatDetailResList);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

//    /**
//     * 동네생활 등록 API
//     * [POST]
//     */
//    // Body
//    @ResponseBody
//    @PostMapping("")
//    public BaseResponse<PostChatRes> createChat(@RequestBody PostChatReq postChatReq) {
//        try {
//            PostChatRes postChatRes = chatService.createChat(postChatReq);
//            return new BaseResponse<>(postChatRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
//    }
}
