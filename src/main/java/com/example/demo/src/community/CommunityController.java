package com.example.demo.src.community;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.src.community.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/community")
public class CommunityController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CommunityProvider communityProvider;
    private final CommunityService communityService;

    public CommunityController(CommunityProvider communityProvider, CommunityService communityService) {
        this.communityProvider = communityProvider;
        this.communityService = communityService;
    }


}
