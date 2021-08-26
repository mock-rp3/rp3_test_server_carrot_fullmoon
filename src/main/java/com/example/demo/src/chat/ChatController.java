package com.example.demo.src.chat;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.chat.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/chat")
public class ChatController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ChatProvider chatProvider;
    private final ChatService chatService;
    private final JwtService jwtService;

    public ChatController(ChatProvider chatProvider, ChatService chatService, JwtService jwtService) {
        this.chatProvider = chatProvider;
        this.chatService = chatService;
        this.jwtService = jwtService;
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

    /**
     * 채팅 등록 API
     * [POST]
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<String> createChat(@RequestBody PostChatReq postChatReq) throws BaseException {
        int userIdxByJwt = jwtService.getUserIdx();
        //userIdx와 접근한 유저가 같은지 확인
        if (postChatReq.getSenderId() != userIdxByJwt) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }
        try {
            chatService.createChat(postChatReq);
            return new BaseResponse<>(SUCCESS_SEND_CHAT);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
