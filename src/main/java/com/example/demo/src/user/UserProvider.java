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

    public List<GetUserRes> getUsers() throws BaseException{
        try{
            List<GetUserRes> getUserRes = userDao.getUsers();
            return getUserRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetUserRes> getUsersByEmail(String email) throws BaseException{
        try{
            List<GetUserRes> getUsersRes = userDao.getUsersByEmail(email);
            return getUsersRes;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
                    }


    public GetUserRes getUser(int userIdx) throws BaseException {
        try {
            GetUserRes getUserRes = userDao.getUser(userIdx);
            return getUserRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkID(String ID) throws BaseException{
        try{
            return userDao.checkID(ID);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public PostLoginRes logIn(PostLoginReq postLoginReq) throws BaseException {
        User user = userDao.getPwd(postLoginReq);
        String password;
        int no = userDao.getPwd(postLoginReq).getUserInfoIdx();
        String jwt = jwtService.createJwt(no);

        try {
            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(user.getPassword());
            return new PostLoginRes(no, jwt);

        } catch (Exception ignored) {
            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
        }
    }

}
