package com.example.demo.src.product;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.product.model.*;
import com.example.demo.src.user.model.DeleteUserReq;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/products")
public class ProductController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final ProductProvider productProvider;
    private final ProductService productService;

    public ProductController(ProductProvider productProvider, ProductService productService, JwtService jwtService) {
        this.productProvider = productProvider;
        this.productService = productService;
    }

    /**
     * 상품 전체 조회 API [메인화면]
     * [GET] /app/products
     */
    //Query String
    @ResponseBody
    @GetMapping("") // (GET) 127.0.0.1:9000/app/products
    public BaseResponse<List<GetProductRes>> getProducts(@RequestParam(required = false) String title) {
        try {
            if (title == null) {
                List<GetProductRes> getProductRes = productProvider.getProducts();
                return new BaseResponse<>(getProductRes);
            }
            // Get products
            List<GetProductRes> getProductRes = productProvider.getProductsByTitle(title);
            return new BaseResponse<>(getProductRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 상품 디테일 조회 API
     * [GET] /app/products/:productIdx
     *
     * @return BaseResponse<GetDetailRes, GetDetailImageRes>
     */
    // Path-variable
    @ResponseBody
    @GetMapping("/{productIdx}") // (GET) 127.0.0.1:9000/app/products/:productIdx
    public BaseResponse<List<String>> getAllDetail(@PathVariable("productIdx") int productIdx) {
        // Get Image
        try {
            List<String> resultDetailList = productProvider.getAllDetail(productIdx);
            return new BaseResponse<>(resultDetailList);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    /**
     * 상품 등록 API
     * [POST] /app/products
     *
     * @return BaseResponse<PostProductRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostProductRes> createProduct(@RequestBody PostProductReq postProductReq) {
        try {
            PostProductRes postProductRes = productService.createProduct(postProductReq);
            return new BaseResponse<>(postProductRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 상품 삭제 API
     */
    @PatchMapping("/delete/{productIdx}")
    public BaseResponse<String> deleteProduct(@PathVariable("productIdx") int productIdx) {
        try {
            productService.deleteProduct(productIdx);

            return new BaseResponse<>(SUCCESS_DELETE_PRODUCT);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 상품 정보 변경
     */
    @PatchMapping("/{productIdx}")
    public BaseResponse<String> modifyProductInfo(@PathVariable("productIdx") int productIdx, @RequestBody PatchProductReq patchProductReq) {
        try {
            patchProductReq = new PatchProductReq(productIdx,
                    patchProductReq.getTitle(),
                    patchProductReq.getDescription(),
                    patchProductReq.getPrice(),
                    patchProductReq.getCanProposal(),
                    patchProductReq.getCategoryId());
            productService.modifyProductInfo(patchProductReq);

            return new BaseResponse<>(SUCCESS_UPDATE_PRODUCT);
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    /**
     * 게시자(유저)로 상품 검색
     * [GET] /app/products?seller={seller}
     */
    //Query String
    @ResponseBody
    @GetMapping("/search") // (GET) 127.0.0.1:9000/app/products
    public BaseResponse<List<GetProductSellerRes>> getProductsBySeller(@RequestParam(required = false) String seller) {
        try {
            List<GetProductSellerRes> getProductSellerRes = productProvider.getProductsBySeller(seller);
            return new BaseResponse<>(getProductSellerRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 조회수 증가
     * [PATCH} /app/products/view/:productIdx
     */
    @PatchMapping("/view/{productIdx}")
    public BaseResponse<String> updateViewCount(@PathVariable("productIdx") int productIdx) {
        try {
            productService.updateViewCount(productIdx);

            return new BaseResponse<>(SUCCESS_UPDATE_VIEW);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 상품 끌어올리기
     */
    @PatchMapping("/pull/{productIdx}")
    public BaseResponse<String> modifyProductPull(@PathVariable("productIdx") int productIdx, @RequestBody PatchPullReq patchPullReq) {
        int dbSellerId;
        try {
            dbSellerId = productProvider.getSellerIdByProductId(productIdx);
            if (patchPullReq.getSellerId() != dbSellerId) {
                return new BaseResponse<>(PATCH_NOT_MATCH_USER);
            }
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
        try {
            patchPullReq = new PatchPullReq(productIdx,
                    patchPullReq.getPrice(),
                    patchPullReq.getSellerId());
            productService.modifyProductPull(patchPullReq);

            return new BaseResponse<>(SUCCESS_PULL_PRODUCT);
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    /**
     * 위시 설정
     */
    @PostMapping("/wish")
    public BaseResponse<String> createWish(@RequestBody PostWishReq postWishReq) throws BaseException {
        try {
            productService.createWish(postWishReq);
            return new BaseResponse<>(SUCCESS_WISH_PRODUCT);
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }

    /**
     * 위시 해제
     */
    @DeleteMapping("/wish")
    public BaseResponse<String> updateWish(@RequestBody PostWishReq postWishReq) throws BaseException {
        try {
            productService.updateWish(postWishReq);
            return new BaseResponse<>(SUCCESS_REVOKE_WISH_PRODUCT);
        } catch (BaseException baseException) {
            return new BaseResponse<>(baseException.getStatus());
        }
    }
}


