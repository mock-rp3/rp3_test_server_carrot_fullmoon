package com.example.demo.src.community;

import com.example.demo.config.BaseException;
import com.example.demo.src.community.model.*;
import com.example.demo.src.product.ProductDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommunityProvider {

    @Autowired
    private final CommunityDao communityDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CommunityProvider(CommunityDao communityDao) {
        this.communityDao = communityDao;
    }
}
