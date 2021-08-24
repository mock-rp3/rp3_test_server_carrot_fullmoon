package com.example.demo.src.community;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.product.model.GetProductRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.src.community.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 커뮤니티 전체 조회 API [메인화면]
     * [GET] /app/community
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/products
    public BaseResponse<List<GetCommunityRes>> getCommunities(@RequestParam(required = false) String keyword) {
        try {
            if (keyword == null) {
                List<GetCommunityRes> getCommunityResList = communityProvider.getCommunities();
                return new BaseResponse<>(getCommunityResList);
            }
            // Get products by Title
            List<GetCommunityRes> getCommunityResList = communityProvider.getCommunitiesByDescription(keyword);
            return new BaseResponse<>(getCommunityResList);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
