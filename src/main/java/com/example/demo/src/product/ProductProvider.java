package com.example.demo.src.product;


import com.example.demo.config.BaseException;
import com.example.demo.src.product.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

//Provider : Read의 비즈니스 로직 처리
@Service
public class ProductProvider {

    @Autowired
    private final ProductDao productDao;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public ProductProvider(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public List<GetProductRes> getProducts() throws BaseException {
        try {
            List<GetProductRes> getProductRes = productDao.getProducts();
            return getProductRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public List<GetProductRes> getProductsByTitle(String title) throws BaseException {
        try {
            List<GetProductRes> getProductRes = productDao.getProductsByTitle(title);
            return getProductRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public GetDetailRes getDetail(int productIdx) throws BaseException {
        try {
            GetDetailRes getDetailRes = productDao.getDetail(productIdx);
            return getDetailRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

//    public int checkID(String ID) throws BaseException{
//        try{
//            return userDao.checkID(ID);
//        } catch (Exception exception){
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }


}
