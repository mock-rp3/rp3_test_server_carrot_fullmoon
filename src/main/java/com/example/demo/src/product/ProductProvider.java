package com.example.demo.src.product;


import com.example.demo.config.BaseException;
import com.example.demo.src.product.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
            System.out.println(title+title.getClass().getName());
            return getProductRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public List<GetDetailRes> getProduct(int productIdx) throws BaseException {
        try {
            List<GetDetailRes> getDetailRes = productDao.getDetail(productIdx);
            return getDetailRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

//    @Transactional
//    public List<GetDetailImageRes> getDetailImage(int productIdx) throws BaseException {
//        try {
//            List<GetDetailImageRes> getDetailImageRes = productDao.getDetailImage(productIdx);
//            return getDetailImageRes;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }

    @Transactional
    public List getAllDetail(int productIdx) throws BaseException {
        try {
            List detailInfo = productDao.getDetail(productIdx);
            List imageUrl = productDao.getDetailImage(productIdx);
            List resultDetailList = new ArrayList<>(Arrays.asList(detailInfo,imageUrl));
            return resultDetailList;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public List<GetProductSellerRes> getProductsBySeller(String seller) throws BaseException {
        try {
            List<GetProductSellerRes> getProductSellerRes = productDao.getProductsBySeller(seller);
            return getProductSellerRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public int getSellerIdByProductId(int productIdx) throws BaseException {
        try {
            return productDao.getSellerIdByProductId(productIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public List<GetProductStatusRes> getProductStatus(String status, int sellerId) throws BaseException {
        try {
            List<GetProductStatusRes> getProductStatusRes = productDao.getProductStatus(status, sellerId);
            return getProductStatusRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
