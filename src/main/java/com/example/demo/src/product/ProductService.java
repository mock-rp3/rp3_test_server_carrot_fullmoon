package com.example.demo.src.product;


import com.example.demo.config.BaseException;
import com.example.demo.src.product.model.PostProductReq;
import com.example.demo.src.product.model.PostProductRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    //POST
    @Transactional
    public PostProductRes createProduct(PostProductReq postProductReq) throws BaseException {
//        try{
            int newProduct = productDao.createProduct(postProductReq);
            return new PostProductRes(newProduct);
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
    }

    @Transactional
    public void deleteProduct(int productIdx) throws BaseException {
//        try{
        int result = productDao.deleteProduct(productIdx);
        if (result == 0){
            throw new BaseException(DELETE_FAIL_PRODUCT);
        }
//        return result;
//        } catch(Exception exception){
//            throw new BaseException(DATABASE_ERROR);
        }
}
