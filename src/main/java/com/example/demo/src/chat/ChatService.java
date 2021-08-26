package com.example.demo.src.chat;

import com.example.demo.config.BaseException;
import com.example.demo.src.chat.model.PostChatReq;
import com.example.demo.src.chat.model.PostChatRes;
import com.example.demo.src.community.model.PatchCommunityReq;
import com.example.demo.src.community.model.PostCommunityReq;
import com.example.demo.src.community.model.PostCommunityRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class ChatService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ChatDao communityDao;
    private final ChatProvider communityProvider;


    @Autowired
    public ChatService(ChatDao communityDao, ChatProvider communityProvider) {
        this.communityDao = communityDao;
        this.communityProvider = communityProvider;

    }

    //POST
    @Transactional
    public void createChat(PostChatReq postChatReq) throws BaseException {
//        try{
            communityDao.createChat(postChatReq);
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
    }

}
