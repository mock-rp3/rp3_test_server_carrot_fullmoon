package com.example.demo.src.community;

import com.example.demo.config.BaseException;
import com.example.demo.src.community.model.*;
import com.example.demo.src.product.ProductDao;
import com.example.demo.src.product.model.GetDetailRes;
import com.example.demo.src.product.model.GetProductRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

@Service
public class CommunityProvider {

    @Autowired
    private final CommunityDao communityDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CommunityProvider(CommunityDao communityDao) {
        this.communityDao = communityDao;
    }

    @Transactional
    public List<GetCommunityRes> getCommunities() throws BaseException {
        try {
            List<GetCommunityRes> getCommunityResList = communityDao.getCommunities();
            return getCommunityResList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public List<GetCommunityRes> getCommunitiesByDescription(String keyword) throws BaseException {
        try {
            List<GetCommunityRes> getCommunityResList = communityDao.getCommunitiesByDescription(keyword);
            return getCommunityResList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public List<GetCommunityDetailRes> getCommunity(int communityIdx) throws BaseException {
//        try {
            List<GetCommunityDetailRes> getCommunityDetailResList = communityDao.getCommunity(communityIdx);
            return getCommunityDetailResList;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
    }
}
