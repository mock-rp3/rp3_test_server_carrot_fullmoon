package com.example.demo.src.user;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    @Transactional
    public List<GetUserRes> getUsers() throws BaseException{
        try{
            List<GetUserRes> getUserRes = userDao.getUsers();
            return getUserRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public List<GetUserRes> getUsersByName(String name) throws BaseException{
        try{
            List<GetUserRes> getUsersRes = userDao.getUsersByName(name);
            return getUsersRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public GetUserRes getUser(int userInfoIdx) throws BaseException {
        try {
            GetUserRes getUserRes = userDao.getUser(userInfoIdx);
            return getUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public int checkPhoneNumber(String phoneNumber) throws BaseException{
        try{
            return userDao.checkPhoneNumber(phoneNumber);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException {
        User user = userDao.getPhoneNumber(postLoginReq);

        int userInfoIdx = userDao.getPhoneNumber(postLoginReq).getUserInfoIdx();
        String jwt = jwtService.createJwt(userInfoIdx);
        return new PostLoginRes(userInfoIdx, jwt);
    }

}
