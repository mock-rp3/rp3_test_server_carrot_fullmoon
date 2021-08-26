package com.example.demo.src.community;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.community.model.*;
import com.example.demo.src.product.ProductDao;
import com.example.demo.src.product.ProductProvider;
import com.example.demo.src.product.model.PatchProductReq;
import com.example.demo.src.product.model.PostKeywordReq;
import com.example.demo.src.product.model.PostProductRes;
import com.example.demo.src.user.model.PostUserReq;
import com.example.demo.src.user.model.PostUserRes;
import com.example.demo.utils.AES128;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;

@Service
public class CommunityService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CommunityDao communityDao;
    private final CommunityProvider communityProvider;


    @Autowired
    public CommunityService(CommunityDao communityDao, CommunityProvider communityProvider) {
        this.communityDao = communityDao;
        this.communityProvider = communityProvider;

    }

    @Transactional
    public void deleteCommunity(int communityIdx) throws BaseException {
        try {
            int result = communityDao.deleteCommunity(communityIdx);
            if (result == 0) {
                throw new BaseException(DELETE_FAIL_PRODUCT);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //POST
    @Transactional
    public PostCommunityRes createCommunity(PostCommunityReq postCommunityReq) throws BaseException {
        try{
            int newCommunity = communityDao.createCommunity(postCommunityReq);
            return new PostCommunityRes(newCommunity);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //PATCH
    @Transactional
    public void updateCommunity(PatchCommunityReq patchCommunityReq) throws BaseException {
        try {
            int result = communityDao.updateCommunity(patchCommunityReq);
            if (result == 0) {
                throw new BaseException(MODIFY_FAIL_COMMUNITY);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void createReaction(PostReactionReq postReactionReq) throws BaseException {
        try {
            communityDao.createReaction(postReactionReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void updateReaction(PostReactionReq postReactionReq) throws BaseException {
        try {
            communityDao.updateReaction(postReactionReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void deleteReaction(DeleteReactionReq deleteReactionReq) throws BaseException {
        try {
            communityDao.deleteReaction(deleteReactionReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
