package com.example.demo.src.community;

import com.example.demo.config.BaseException;
import com.example.demo.src.community.model.*;
import com.example.demo.src.product.ProductDao;
import com.example.demo.src.product.ProductProvider;
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
}
