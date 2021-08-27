package com.example.demo.src.product;


import com.example.demo.config.BaseException;
import com.example.demo.src.product.model.*;
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
//        try {
            int newProduct = productDao.createProduct(postProductReq);
            return new PostProductRes(newProduct);
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
    }

    @Transactional
    public void deleteProduct(int productIdx) throws BaseException {
        try {
            int result = productDao.deleteProduct(productIdx);
            if (result == 0) {
                throw new BaseException(DELETE_FAIL_PRODUCT);
            }
//        return result;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void modifyProductInfo(PatchProductReq patchProductReq) throws BaseException {
        try {
            int result = productDao.modifyProductInfo(patchProductReq);
            if (result == 0) {
                throw new BaseException(MODIFY_FAIL_PRODUCT);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void updateViewCount(int productIdx) throws BaseException {
        try {
            int result = productDao.updateViewCount(productIdx);
            if (result == 0) {
                throw new BaseException(MODIFY_FAIL_VIEW_COUNT);
            }
//        return result;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void modifyProductPull(PatchPullReq patchPullReq) throws BaseException {
        try {
            int result = productDao.modifyProductPull(patchPullReq);
            if (result == 0) {
                throw new BaseException(MODIFY_FAIL_PULL_PRODUCT);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void createWish(PostWishReq postWishReq) throws BaseException {
        try {
            productDao.createWish(postWishReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void updateWish(PostWishReq postWishReq) throws BaseException {
        try {
            productDao.updateWish(postWishReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional
    public void updateProductStatus(PatchStatusReq patchStatusReq) throws BaseException {
        try {
            productDao.updateProductStatus(patchStatusReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //POST
    @Transactional
    public void createKeyword(PostKeywordReq postKeywordReq) throws BaseException {
        try {
            productDao.createKeyword(postKeywordReq);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
