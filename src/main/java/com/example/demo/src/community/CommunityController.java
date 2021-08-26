package com.example.demo.src.community;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.product.model.*;
import com.example.demo.utils.JwtService;
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
    private final JwtService jwtService;

    public CommunityController(CommunityProvider communityProvider, CommunityService communityService, JwtService jwtService) {
        this.communityProvider = communityProvider;
        this.communityService = communityService;
        this.jwtService = jwtService;
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

    /**
     * 동네생활 디테일 조회 API
     * [GET] /app/community/:communityIdx
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{communityIdx}") // (GET) 127.0.0.1:9000/app/products/:productIdx
    public BaseResponse<List<GetCommunityDetailRes>> getCommunity(@PathVariable("communityIdx") int communityIdx) {
        // Get Image
        try {
            List<GetCommunityDetailRes> getCommunityDetailResList = communityProvider.getCommunity(communityIdx);
            return new BaseResponse<>(getCommunityDetailResList);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 동네생활 삭제 API
     */
    @PatchMapping("/delete/{communityIdx}")
    public BaseResponse<String> deleteCommunity(@PathVariable("communityIdx") int communityIdx) {
        try {
            communityService.deleteCommunity(communityIdx);

            return new BaseResponse<>(SUCCESS_DELETE_COMMUNITY);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 동네생활 등록 API
     * [POST]
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostCommunityRes> createCommunity(@RequestBody PostCommunityReq postCommunityReq) {
        try {
            PostCommunityRes postCommunityRes = communityService.createCommunity(postCommunityReq);
            return new BaseResponse<>(postCommunityRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 동네생활 게시글 변경
     */
    @PatchMapping("/{communityIdx}")
    public BaseResponse<String> updateCommunity(@PathVariable("communityIdx") int communityIdx, @RequestBody PatchCommunityReq patchCommunityReq) {
        try {
            patchCommunityReq = new PatchCommunityReq(communityIdx,
                    patchCommunityReq.getCategoryId(),
                    patchCommunityReq.getDescription());
            communityService.updateCommunity(patchCommunityReq);

            return new BaseResponse<>(SUCCESS_UPDATE_COMMUNITY);
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    /**
     * 카테고리 검색
     */
    /**
     * 상품 카테고리 검색
     */
    //Query String
    @ResponseBody
    @GetMapping("/category") // (GET) 127.0.0.1:9000/app/products
    public BaseResponse<List<GetCommunityCategory>> getCommunityByCategory(@RequestParam(required = false) int category) {
        try {
            List<GetCommunityCategory> getCommunityResList = communityProvider.getCommunityByCategory(category);
            return new BaseResponse<>(getCommunityResList);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 리액션 설정
     */
    @PostMapping("/reaction")
    public BaseResponse<String> createReaction(@RequestBody PostReactionReq postReactionReq) throws BaseException {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (postReactionReq.getUserInfoId() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            communityService.createReaction(postReactionReq);
            return new BaseResponse<>(SUCCESS_KEYWORD_REACTION);
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    /**
     * 리액션 설정
     */
    @PatchMapping("/reaction")
    public BaseResponse<String> updateReaction(@RequestBody PostReactionReq postReactionReq) throws BaseException {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (postReactionReq.getUserInfoId() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            communityService.updateReaction(postReactionReq);
            return new BaseResponse<>(SUCCESS_UPDATE_REACTION);
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    /**
     * 리액션 설정
     */
    @PatchMapping("/delete/reaction")
    public BaseResponse<String> deleteReaction(@RequestBody DeleteReactionReq deleteReactionReq) throws BaseException {
        try {
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (deleteReactionReq.getUserInfoId() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            communityService.deleteReaction(deleteReactionReq);
            return new BaseResponse<>(SUCCESS_DELETE_REACTION);
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }
}
