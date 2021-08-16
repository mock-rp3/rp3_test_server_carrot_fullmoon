package com.example.demo.src.product;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.example.demo.config.BaseResponseStatus.*;

// Service Create, Update, Delete 의 로직 처리
@Service
public class ProductService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ProductDao productDao;
    private final ProductProvider productProvider;


    @Autowired
    public ProductService(ProductDao productDao, ProductProvider productProvider) {
        this.productDao = productDao;
        this.productProvider = productProvider;

    }

//    //POST
//    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
//        //중복
//        if(userProvider.checkID(postUserReq.getUserID()) ==1){
//            throw new BaseException(POST_USERS_EXISTS_ID);
//        }
//
//        String pwd;
//        try{
//            //암호화
//            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword());
//            postUserReq.setPassword(pwd);
//        } catch (Exception ignored) {
//            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
//        }
////        try{
//            int userInfoIdx = userDao.createUser(postUserReq);
//            //jwt 발급.
//            String jwt = jwtService.createJwt(userInfoIdx);
//            return new PostUserRes(jwt,userInfoIdx);
////        } catch (Exception exception) {
////            throw new BaseException(DATABASE_ERROR);
////        }
//    }
//
//    public void modifyUserName(PatchUserReq patchUserReq) throws BaseException {
//        try{
//            int result = userDao.modifyUserName(patchUserReq);
//            if(result == 0){
//                throw new BaseException(MODIFY_FAIL_USERNAME);
//            }
//        } catch(Exception exception){
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
}
