package com.example.demo.src.chat;

import com.example.demo.config.BaseException;
import com.example.demo.src.chat.model.*;
import com.example.demo.src.community.model.GetCommunityRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class ChatProvider {

    @Autowired
    private final ChatDao chatDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ChatProvider(ChatDao chatDao) {
        this.chatDao = chatDao;
    }

    @Transactional
    public List<GetChatRes> getChatRooms() throws BaseException {
        try {
            List<GetChatRes> getChatResList = chatDao.getChatRooms();
            return getChatResList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public List<GetChatDetailRes> getChatRoom(int chatIdx) throws BaseException {
//        try {
            List<GetChatDetailRes> getChatDetailResList = chatDao.getChatRoom(chatIdx);
            return getChatDetailResList;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
    }
}
