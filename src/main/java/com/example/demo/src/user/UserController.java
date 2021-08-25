package com.example.demo.src.user;

import com.example.demo.utils.ValidationRegex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexPhoneNumber;


@RestController
@RequestMapping("/app")
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;


    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * 회원 전체 조회 API
     * [GET] /app/users
     * 회원 닉네임 검색 조회 API
     * [GET] /users?name=
     *
     * @return BaseResponse<List < GetUsersRes>>
     * 성공!
     */
    //Query String
    @ResponseBody
    @GetMapping("/users") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetUserRes>> getUsers(@RequestParam(required = false) String name) {
        try {
            if (name == null) {
                List<GetUserRes> getUsersRes = userProvider.getUsers();
                return new BaseResponse<>(getUsersRes);
            }
            // Get Users By name
            List<GetUserRes> getUsersRes = userProvider.getUsersByName(name);
            return new BaseResponse<>(getUsersRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 회원 1명 조회 API
     * [GET] /users/:userInfoIdx
     *
     * @return BaseResponse<GetUserRes>
     * 성공!
     * @PathVariable userInfoIdx
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/users/{userInfoIdx}") // (GET) 127.0.0.1:9000/app/users/:userInfoIdx
    public BaseResponse<GetUserRes> getUser(@PathVariable("userInfoIdx") int userInfoIdx) {
        // Get Users
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (userInfoIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            GetUserRes getUserRes = userProvider.getUser(userInfoIdx);
            return new BaseResponse<>(getUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 회원가입 API
     * [POST] /app/users
     *
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("/users")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        if (postUserReq.getPhoneNumber() == null || postUserReq.getPhoneNumber().length() == 0) {
            return new BaseResponse<>(POST_USERS_EMPTY_PHONE_NUMBER);
        }
        if (postUserReq.getNickname() == null || postUserReq.getNickname().length() == 0) {
            return new BaseResponse<>(POST_USERS_EMPTY_NICKNAME);
        }

        if (!isRegexPhoneNumber(postUserReq.getPhoneNumber())) {
            return new BaseResponse<>(INVALID_PHONE_NUMBER);
        }

        try {
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 로그인 API
     * [POST] /app/logIn
     *
     * @return PostLoginRes
     * @return BaseResponse<PostLoginRes>
     * @RequestBody PostLoginReq
     */
    @ResponseBody
    @PostMapping("/logIn")
    public BaseResponse<PostLoginRes> logIn(@RequestBody PostLoginReq postLoginReq) {
        if (postLoginReq.getPhoneNumber() == null || postLoginReq.getPhoneNumber().length() == 0) {
            return new BaseResponse<>(LOGIN_USERS_EMPTY_PHONE_NUMBER);
        }
        try {
            PostLoginRes postLoginRes = userProvider.logIn(postLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 로그인 API
     * [POST] /app/joinAndLogIn
     *
     * @return PostLoginRes
     * @return BaseResponse<PostLoginRes>
     * @RequestBody PostLoginReq
     */
    @ResponseBody
    @PostMapping("/join-login")
    public BaseResponse<PostLoginRes> joinAndLogIn(@RequestBody PostUserLoginReq postUserLoginReq) {
        if (postUserLoginReq.getPhoneNumber() == null || postUserLoginReq.getPhoneNumber().length() == 0) {
            return new BaseResponse<>(LOGIN_USERS_EMPTY_PHONE_NUMBER);
        }
        if (postUserLoginReq.getRegionNameCity() == null || postUserLoginReq.getRegionNameCity().length() == 0) {
            return new BaseResponse<>(POST_USERS_EMPTY_CITY);
        }
        if (postUserLoginReq.getRegionNameGu() == null || postUserLoginReq.getRegionNameGu().length() == 0) {
            return new BaseResponse<>(POST_USERS_EMPTY_GU);
        }
        if (postUserLoginReq.getRegionNameTown() == null || postUserLoginReq.getRegionNameTown().length() == 0) {
            return new BaseResponse<>(POST_USERS_EMPTY_TOWN);
        }
        if (!isRegexPhoneNumber(postUserLoginReq.getPhoneNumber())) {
            return new BaseResponse<>(INVALID_PHONE_NUMBER);
        }
        // service 단으로 보내야함
        // 이미 존재하는 회원이라면 그냥 로그인
        try {
            int numberIsExist = userProvider.checkPhoneNumber(postUserLoginReq.getPhoneNumber());
            if (numberIsExist == 1) {
                PostLoginRes postLoginRes = userService.userLogin(postUserLoginReq);
                return new BaseResponse<>(postLoginRes);
            }
        } catch (BaseException exception){
            return new BaseResponse<>(exception.getStatus());
        }
        // 존재하지 않는다면 회원가입
        try {
            PostLoginRes postLoginRes = userService.userJoin(postUserLoginReq);
            return new BaseResponse<>(postLoginRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 유저정보변경 API
     * [PATCH] /users/:userInfoIdx
     *
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/users/{userInfoIdx}")
    public BaseResponse<String> modifyUserName(@PathVariable("userInfoIdx") int userInfoIdx, @RequestBody PatchUserReq patchUserReq) {
        if (patchUserReq.getNickname() == null) {
            return new BaseResponse<>(PATCH_USERS_EMPTY_NICKNAME);
        }
        if (patchUserReq.getProfileImageUrl() == null) {
            return new BaseResponse<>(PATCH_USERS_EMPTY_PROFILE_URL);
        }
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (userInfoIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 유저네임 변경

            patchUserReq = new PatchUserReq(userInfoIdx
                    , patchUserReq.getNickname()
                    , patchUserReq.getProfileImageUrl());
            userService.modifyUserInfo(patchUserReq);

            return new BaseResponse<>(SUCCESS_UPDATE_USER);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 유저 탈퇴 API
     * [PATCH] /users/:userInfoIdx/delete
     *
     * @return BaseResponse<String>
     */
    @PatchMapping("/users/delete/{userInfoIdx}")
    public BaseResponse<String> deleteUserInfo(@PathVariable("userInfoIdx") int userInfoIdx, @RequestBody DeleteUserReq deleteUserReq) {
        if (deleteUserReq.getClosingReason() == null) {
            return new BaseResponse<>(EMPTY_CLOSING_ACCOUNT_REASON);
        }
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (userInfoIdx != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }
            //같다면 탈퇴로 status 변경
            deleteUserReq = new DeleteUserReq(userInfoIdx
                    , deleteUserReq.getClosingReason());
            userService.deleteUserInfo(deleteUserReq);

            return new BaseResponse<>(SUCCESS_DELETE_USER);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
