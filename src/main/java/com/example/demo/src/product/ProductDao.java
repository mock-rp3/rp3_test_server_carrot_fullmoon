package com.example.demo.src.product;


import com.example.demo.src.product.model.*;
import com.example.demo.src.user.model.DeleteUserReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class ProductDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetProductRes> getProducts() {
        String getProductsQuery = "select productIdx,\n" +
                "       case\n" +
                "           when timestampdiff(MINUTE, Product.createdAt, CURRENT_TIMESTAMP()) < 60\n" +
                "               then concat(timestampdiff(MINUTE, Product.createdAt, CURRENT_TIMESTAMP()), '분 전')\n" +
                "           when timestampdiff(HOUR, Product.createdAt, CURRENT_TIMESTAMP()) < 24\n" +
                "               then concat(timestampdiff(HOUR, Product.createdAt, CURRENT_TIMESTAMP()), '시간 전')\n" +
                "           when timestampdiff(DAY, Product.createdAt, CURRENT_TIMESTAMP()) < 30\n" +
                "               then concat(timestampdiff(DAY, Product.createdAt, CURRENT_TIMESTAMP()), '일 전')\n" +
                "           else date_format(Product.createdAt, '%Y년-%m월-%d일')\n" +
                "           end as createdAt,\n" +
                "       title,\n" +
                "       price,\n" +
                "       case\n" +
                "           when timestampdiff(MINUTE, Product.pulledAt, CURRENT_TIMESTAMP()) < 60\n" +
                "               then concat('끌올 ',timestampdiff(MINUTE, Product.pulledAt, CURRENT_TIMESTAMP()), '분 전')\n" +
                "           when timestampdiff(HOUR, Product.pulledAt, CURRENT_TIMESTAMP()) < 24\n" +
                "               then concat('끌올 ',timestampdiff(HOUR, Product.pulledAt, CURRENT_TIMESTAMP()), '시간 전')\n" +
                "           when timestampdiff(DAY, Product.pulledAt, CURRENT_TIMESTAMP()) < 30\n" +
                "               then concat('끌올 ',timestampdiff(DAY, Product.pulledAt, CURRENT_TIMESTAMP()), '일 전')\n" +
                "           else date_format(Product.pulledAt, '%Y년-%m월-%d일')\n" +
                "           end as pulledAt,\n" +
                "       imageUrl,\n" +
                "       regionNameGu,\n" +
                "       regionNameTown,\n" +
                "       count(W.wishIdx)\n" +
                "from Product\n" +
                "         join UserInfo UI on UI.userInfoIdx = Product.sellerId\n" +
                "         left join ProductImage PI on Product.productIdx = PI.productId\n" +
                "         left join Region R on R.regionIdx = Product.regionId\n" +
                "         left join Wish W on Product.productIdx = W.productId\n" +
                "where UI.status = 'normal'\n" +
                "  and Product.status = 'normal'\n" +
                "group by productIdx order by date(Product.createdAt) asc";
        return this.jdbcTemplate.query(getProductsQuery,
                (rs, rowNum) -> new GetProductRes(
                        rs.getInt("productIdx"),
                        rs.getString("createdAt"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getString("pulledAt"),
                        rs.getString("imageUrl"),
                        rs.getString("regionNameGu"),
                        rs.getString("regionNameTown"),
                        rs.getInt("count(W.wishIdx)"))
        );
    }

    public List<GetProductRes> getProductsByTitle(String title) {
        String getProductsByTitleQuery = "select productIdx,\n" +
                "       case\n" +
                "           when timestampdiff(MINUTE, Product.createdAt, CURRENT_TIMESTAMP()) < 60\n" +
                "               then concat(timestampdiff(MINUTE, Product.createdAt, CURRENT_TIMESTAMP()), '분 전')\n" +
                "           when timestampdiff(HOUR, Product.createdAt, CURRENT_TIMESTAMP()) < 24\n" +
                "               then concat(timestampdiff(HOUR, Product.createdAt, CURRENT_TIMESTAMP()), '시간 전')\n" +
                "           when timestampdiff(DAY, Product.createdAt, CURRENT_TIMESTAMP()) < 30\n" +
                "               then concat(timestampdiff(DAY, Product.createdAt, CURRENT_TIMESTAMP()), '일 전')\n" +
                "           else date_format(Product.createdAt, '%Y년-%m월-%d일')\n" +
                "           end as createdAt,\n" +
                "       title,\n" +
                "       price,\n" +
                "       case\n" +
                "           when timestampdiff(MINUTE, Product.pulledAt, CURRENT_TIMESTAMP()) < 60\n" +
                "               then concat('끌올 ',timestampdiff(MINUTE, Product.pulledAt, CURRENT_TIMESTAMP()), '분 전')\n" +
                "           when timestampdiff(HOUR, Product.pulledAt, CURRENT_TIMESTAMP()) < 24\n" +
                "               then concat('끌올 ',timestampdiff(HOUR, Product.pulledAt, CURRENT_TIMESTAMP()), '시간 전')\n" +
                "           when timestampdiff(DAY, Product.pulledAt, CURRENT_TIMESTAMP()) < 30\n" +
                "               then concat('끌올 ',timestampdiff(DAY, Product.pulledAt, CURRENT_TIMESTAMP()), '일 전')\n" +
                "           else date_format(Product.pulledAt, '%Y년-%m월-%d일')\n" +
                "           end as pulledAt,\n" +
                "       imageUrl,\n" +
                "       regionNameGu,\n" +
                "       regionNameTown,\n" +
                "       count(W.wishIdx)\n" +
                "from Product\n" +
                "         join ProductImage PI on Product.productIdx = PI.productId\n" +
                "         join UserInfo UI on UI.userInfoIdx = Product.sellerId\n" +
                "         join Region R on R.regionIdx = Product.regionId\n" +
                "         left join Wish W on Product.productIdx = W.productId\n" +
                "where (UI.status = 'normal' and Product.status = 'normal')\n" +
                "  AND title LIKE concat('%', ?, '%')\n" +
                "group by productIdx order by date(Product.createdAt) desc";
        String getProductsByTitleParams = title;
        return this.jdbcTemplate.query(getProductsByTitleQuery,
                (rs, rowNum) -> new GetProductRes(
                        rs.getInt("productIdx"),
                        rs.getString("createdAt"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getString("pulledAt"),
                        rs.getString("imageUrl"),
                        rs.getString("regionNameGu"),
                        rs.getString("regionNameTown"),
                        rs.getInt("count(W.wishIdx)")),
                getProductsByTitleParams);
    }

    public List<GetDetailRes> getDetail(int productIdx) {
        String getDetailQuery = "select productIdx,\n" +
                "       UI.profileImageUrl,\n" +
                "       UI.nickname,\n" +
                "       regionNameGu,\n" +
                "       regionNameTown,\n" +
                "       UV.mannerGrade,\n" +
                "       title,\n" +
                "       C.name,\n" +
                "       case\n" +
                "           when timestampdiff(MINUTE, P.createdAt, CURRENT_TIMESTAMP()) < 60\n" +
                "               then concat(timestampdiff(MINUTE, P.createdAt, CURRENT_TIMESTAMP()), '분 전')\n" +
                "           when timestampdiff(HOUR, P.createdAt, CURRENT_TIMESTAMP()) < 24\n" +
                "               then concat(timestampdiff(HOUR, P.createdAt, CURRENT_TIMESTAMP()), '시간 전')\n" +
                "           when timestampdiff(DAY, P.createdAt, CURRENT_TIMESTAMP()) < 30\n" +
                "               then concat(timestampdiff(DAY, P.createdAt, CURRENT_TIMESTAMP()), '일 전')\n" +
                "           else date_format(P.createdAt, '%Y년-%m월-%d일')\n" +
                "           end as createdAt,\n" +
                "       case\n" +
                "           when timestampdiff(MINUTE, P.pulledAt, CURRENT_TIMESTAMP()) < 60\n" +
                "               then concat('끌올 ', timestampdiff(MINUTE, P.pulledAt, CURRENT_TIMESTAMP()), '분 전')\n" +
                "           when timestampdiff(HOUR, P.pulledAt, CURRENT_TIMESTAMP()) < 24\n" +
                "               then concat('끌올 ', timestampdiff(HOUR, P.pulledAt, CURRENT_TIMESTAMP()), '시간 전')\n" +
                "           when timestampdiff(DAY, P.pulledAt, CURRENT_TIMESTAMP()) < 30\n" +
                "               then concat('끌올 ', timestampdiff(DAY, P.pulledAt, CURRENT_TIMESTAMP()), '일 전')\n" +
                "           else date_format(P.pulledAt, '%Y년-%m월-%d일')\n" +
                "           end as pulledAt,\n" +
                "       description,\n" +
                "       count(W.wishIdx),\n" +
                "       viewCount,\n" +
                "       price,\n" +
                "       canProposal,\n" +
                "       UI.userInfoIdx\n" +
                "from Product P\n" +
                "         join ProductImage PI on P.productIdx = PI.productId\n" +
                "         join UserInfo UI on UI.userInfoIdx = P.sellerId\n" +
                "         join UserVar UV on UI.userInfoIdx = UV.userInfoId\n" +
                "         join Region R on R.regionIdx = P.regionId\n" +
                "         left join Wish W on P.productIdx = W.productId\n" +
                "         join Category C on C.categoryIdx = P.categoryId\n" +
                "where (UI.status = 'normal' and P.status = 'normal')\n" +
                "  AND productIdx = ?";
        int getDetailParams = productIdx;
        return this.jdbcTemplate.query(getDetailQuery,
                (rs, rowNum) -> new GetDetailRes(
                        rs.getInt("productIdx"),
                        rs.getString("profileImageUrl"),
                        rs.getString("nickname"),
                        rs.getString("regionNameGu"),
                        rs.getString("regionNameTown"),
                        rs.getInt("mannerGrade"),
                        rs.getString("title"),
                        rs.getString("name"),
                        rs.getString("createdAt"),
                        rs.getString("pulledAt"),
                        rs.getString("description"),
                        rs.getInt("count(W.wishIdx)"),
                        rs.getInt("viewCount"),
                        rs.getInt("price"),
                        rs.getString("canProposal"),
                        rs.getInt("userInfoIdx")),
                getDetailParams);
    }

    // 그냥 Map 안 쓰고 리스트로만 가져와도 됨
    public List<Map<String, Object>> getDetailImage(int productId) {
        String getProductImageQuery = "select imageUrl from ProductImage where productId = ?";
        int getProductImageParams = productId;
        List<Map<String, Object>> images = jdbcTemplate.queryForList(getProductImageQuery, getProductImageParams);
        return images;
    }

    public int createProduct(PostProductReq postProductReq) {
        try {
            String getRegionIdQuery = "select regionIdx from Region where userInfoId = ?";
            int getSellerId = postProductReq.getSellerId();
            int regionId = this.jdbcTemplate.queryForObject(getRegionIdQuery, int.class, getSellerId);

            String createProductQuery = "insert into Product (title" +
                    ", description, price, canProposal, categoryId, sellerId, regionId)\n" +
                    "values (?,?,?,?,?,?,?)";
            Object[] createProductParams = new Object[]{postProductReq.getTitle()
                    , postProductReq.getDescription()
                    , postProductReq.getPrice()
                    , postProductReq.getCanProposal()
                    , postProductReq.getCategoryId()
                    , postProductReq.getSellerId()
                    , regionId};
            this.jdbcTemplate.update(createProductQuery, createProductParams);

            String lastProductIdQuery = "select last_insert_id()";
            int lastProductId = this.jdbcTemplate.queryForObject(lastProductIdQuery, int.class);

            String createProductImageQuery = "insert into ProductImage (imageUrl, productId) VALUES (?,?)";
            Object[] createProductImageParams = new Object[]{postProductReq.getImageUrl(), lastProductId};
            this.jdbcTemplate.update(createProductImageQuery, createProductImageParams);

            String lastInsertImageIdQuery = "select last_insert_id()";
            return this.jdbcTemplate.queryForObject(lastInsertImageIdQuery, int.class);

        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("EmptyResult");

        } catch (IncorrectResultSizeDataAccessException e) {

            String lastInsertImageIdQuery = "select last_insert_id()";
            return this.jdbcTemplate.queryForObject(lastInsertImageIdQuery, int.class);

//            String lastInsertImageIdQuery = "select last_insert_id()";
//            int lastInsertImageId = this.jdbcTemplate.queryForObject(lastInsertImageIdQuery, int.class);
//            String stringLastInsertImageId = Integer.toString(lastInsertImageId);
//            String findProductIdQuery = "select productId from ProductImage where pdImageIdx = ?";
//            return this.jdbcTemplate.query(findProductIdQuery,stringLastInsertImageId);

        }
    }

    public int deleteProduct(int productIdx) {
        String deleteProductQuery = "update Product set status = 'deleted' where productIdx = ? ";
        int deleteProductParams = productIdx;

        return this.jdbcTemplate.update(deleteProductQuery, deleteProductParams);
    }

//    public int checkID(String ID){
//        String checkIDQuery = "select exists(select userID from UserInfo where userID = ?)";
//        String checkIDParams = ID;
//        return this.jdbcTemplate.queryForObject(checkIDQuery,
//                int.class,
//                checkIDParams);
//
//    }

    public int modifyProductInfo(PatchProductReq patchProductReq) {
        String modifyUserNameQuery = "update Product set title = ?, description = ?, price = ?, canProposal = ?, categoryId = ? where productIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{
                patchProductReq.getTitle()
                , patchProductReq.getDescription()
                , patchProductReq.getPrice()
                , patchProductReq.getCanProposal()
                , patchProductReq.getCategoryId()
                , patchProductReq.getProductIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery, modifyUserNameParams);
    }

    public List<GetProductSellerRes> getProductsBySeller(String seller) {
        String getProductsBySellerQuery = "select productIdx,\n" +
                "       title,\n" +
                "       price,\n" +
                "       imageUrl\n" +
                "from Product\n" +
                "         join ProductImage PI on Product.productIdx = PI.productId\n" +
                "         join UserInfo UI on UI.userInfoIdx = Product.sellerId\n" +
                "where (UI.status = 'normal' and Product.status = 'normal')\n" +
                "  AND sellerId = ?\n" +
                "group by productIdx limit 4";
        String getProductsBySellerParams = seller;
        return this.jdbcTemplate.query(getProductsBySellerQuery,
                (rs, rowNum) -> new GetProductSellerRes(
                        rs.getInt("productIdx"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getString("imageUrl")),
                getProductsBySellerParams);
    }

    public int updateViewCount(int productIdx) {
        String updateViewCountQuery = "update Product set viewCount = viewCount + 1 where productIdx = ? ";
        int updateViewCountParams = productIdx;

        return this.jdbcTemplate.update(updateViewCountQuery, updateViewCountParams);
    }

    public int modifyProductPull(PatchPullReq patchPullReq) {
        String modifyProductPullQuery = "update Product set price = ? where sellerId =? AND productIdx = ? ";
        Object[] modifyProductPullParams = new Object[]{
                patchPullReq.getPrice()
                , patchPullReq.getSellerId()
                , patchPullReq.getProductIdx()};

        return this.jdbcTemplate.update(modifyProductPullQuery, modifyProductPullParams);
    }

    public int getSellerIdByProductId(int productIdx) {
        String getSellerIdByProductIdQuery = "select sellerId from Product where productIdx = ?";
        int getSellerIdByProductIdParams = productIdx;
        return this.jdbcTemplate.queryForObject(getSellerIdByProductIdQuery, int.class, getSellerIdByProductIdParams);
    }

    public int createWish(PostWishReq postWishReq) {
        String createWishQuery = "insert into Wish (productId, userInfoId) values (?, ?)";
        Object[] createWishParams = new Object[]{
                postWishReq.getProductId()
                , postWishReq.getUserInfoId()
        };
        return this.jdbcTemplate.update(createWishQuery, createWishParams);
    }

    public int updateWish(PostWishReq postWishReq) {
        String updateWishQuery = "delete from Wish where productId =? and userInfoId = ?";
        Object[] updateWishParams = new Object[]{
                postWishReq.getProductId()
                , postWishReq.getUserInfoId()
        };
        return this.jdbcTemplate.update(updateWishQuery, updateWishParams);
    }

    public int updateProductStatus(PatchStatusReq patchStatusReq) {
        String updateProductStatusQuery = "update Product set status = ? where productIdx = ?";
        Object[] updateProductStatusParams = new Object[]{
                patchStatusReq.getStatus()
                , patchStatusReq.getProductIdx()
        };
        return this.jdbcTemplate.update(updateProductStatusQuery, updateProductStatusParams);
    }

    // as는 select에서만 되는 것 같다 안 됨
    public List<GetProductStatusRes> getProductStatus(String status, int sellerId) {
        String getProductStatusQuery = "select PI.imageUrl,\n" +
                "       P.title,\n" +
                "       R.regionNameGu,\n" +
                "       R.regionNameTown,\n" +
                "       case\n" +
                "           when timestampdiff(MINUTE, P.createdAt, CURRENT_TIMESTAMP()) < 60\n" +
                "               then concat(timestampdiff(MINUTE, P.createdAt, CURRENT_TIMESTAMP()), '분 전')\n" +
                "           when timestampdiff(HOUR, P.createdAt, CURRENT_TIMESTAMP()) < 24\n" +
                "               then concat(timestampdiff(HOUR, P.createdAt, CURRENT_TIMESTAMP()), '시간 전')\n" +
                "           when timestampdiff(DAY, P.createdAt, CURRENT_TIMESTAMP()) < 30\n" +
                "               then concat(timestampdiff(DAY, P.createdAt, CURRENT_TIMESTAMP()), '일 전')\n" +
                "           else date_format(P.createdAt, '%Y년-%m월-%d일')\n" +
                "           end  as createdAt,\n" +
                "       case\n" +
                "           when timestampdiff(MINUTE, P.pulledAt, CURRENT_TIMESTAMP()) < 60\n" +
                "               then concat('끌올 ', timestampdiff(MINUTE, P.pulledAt, CURRENT_TIMESTAMP()), '분 전')\n" +
                "           when timestampdiff(HOUR, P.pulledAt, CURRENT_TIMESTAMP()) < 24\n" +
                "               then concat('끌올 ', timestampdiff(HOUR, P.pulledAt, CURRENT_TIMESTAMP()), '시간 전')\n" +
                "           when timestampdiff(DAY, P.pulledAt, CURRENT_TIMESTAMP()) < 30\n" +
                "               then concat('끌올 ', timestampdiff(DAY, P.pulledAt, CURRENT_TIMESTAMP()), '일 전')\n" +
                "           else date_format(P.pulledAt, '%Y년-%m월-%d일')\n" +
                "           end  as pulledAt,\n" +
                "       P.price,\n" +
                "       P.status as status,\n" +
                "       P.sellerId,\n" +
                "       P.productIdx\n" +
                "from Product P\n" +
                "         join ProductImage PI on P.productIdx = PI.productId\n" +
                "         join Region R on P.regionId = R.regionIdx\n" +
                "GROUP BY productIdx\n" +
                "having status = ?\n" +
                "   and sellerId = ?";
        Object[] getProductStatusParams = new Object[]{status, sellerId};
        return this.jdbcTemplate.query(getProductStatusQuery,
                (rs, rowNum) -> new GetProductStatusRes(
                        rs.getString("imageUrl"),
                        rs.getString("title"),
                        rs.getString("regionNameGu"),
                        rs.getString("regionNameTown"),
                        rs.getString("createdAt"),
                        rs.getString("pulledAt"),
                        rs.getInt("price"),
                        rs.getString("status"),
                        rs.getInt("sellerId"),
                        rs.getInt("productIdx")),
                getProductStatusParams);

    }

    public List<GetProductRes> getProductsByCategory(int categoryId) {
        String getProductsByCategoryQuery = "select productIdx,\n" +
                "       case\n" +
                "           when timestampdiff(MINUTE, Product.createdAt, CURRENT_TIMESTAMP()) < 60\n" +
                "               then concat(timestampdiff(MINUTE, Product.createdAt, CURRENT_TIMESTAMP()), '분 전')\n" +
                "           when timestampdiff(HOUR, Product.createdAt, CURRENT_TIMESTAMP()) < 24\n" +
                "               then concat(timestampdiff(HOUR, Product.createdAt, CURRENT_TIMESTAMP()), '시간 전')\n" +
                "           when timestampdiff(DAY, Product.createdAt, CURRENT_TIMESTAMP()) < 30\n" +
                "               then concat(timestampdiff(DAY, Product.createdAt, CURRENT_TIMESTAMP()), '일 전')\n" +
                "           else date_format(Product.createdAt, '%Y년-%m월-%d일')\n" +
                "           end as createdAt,\n" +
                "       title,\n" +
                "       price,\n" +
                "       case\n" +
                "           when timestampdiff(MINUTE, Product.pulledAt, CURRENT_TIMESTAMP()) < 60\n" +
                "               then concat('끌올 ', timestampdiff(MINUTE, Product.pulledAt, CURRENT_TIMESTAMP()), '분 전')\n" +
                "           when timestampdiff(HOUR, Product.pulledAt, CURRENT_TIMESTAMP()) < 24\n" +
                "               then concat('끌올 ', timestampdiff(HOUR, Product.pulledAt, CURRENT_TIMESTAMP()), '시간 전')\n" +
                "           when timestampdiff(DAY, Product.pulledAt, CURRENT_TIMESTAMP()) < 30\n" +
                "               then concat('끌올 ', timestampdiff(DAY, Product.pulledAt, CURRENT_TIMESTAMP()), '일 전')\n" +
                "           else date_format(Product.pulledAt, '%Y년-%m월-%d일')\n" +
                "           end as pulledAt,\n" +
                "       imageUrl,\n" +
                "       regionNameGu,\n" +
                "       regionNameTown,\n" +
                "       count(W.wishIdx)\n" +
                "from Product\n" +
                "         join ProductImage PI on Product.productIdx = PI.productId\n" +
                "         join UserInfo UI on UI.userInfoIdx = Product.sellerId\n" +
                "         join Region R on R.regionIdx = Product.regionId\n" +
                "         left join Wish W on Product.productIdx = W.productId\n" +
                "where (UI.status = 'normal' and Product.status = 'normal')\n" +
                "  AND categoryId = ?\n" +
                "group by productIdx order by date(Product.createdAt) desc";
        int getProductsByCategoryParams = categoryId;
        return this.jdbcTemplate.query(getProductsByCategoryQuery,
                (rs, rowNum) -> new GetProductRes(
                        rs.getInt("productIdx"),
                        rs.getString("createdAt"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getString("pulledAt"),
                        rs.getString("imageUrl"),
                        rs.getString("regionNameGu"),
                        rs.getString("regionNameTown"),
                        rs.getInt("count(W.wishIdx)")),
                getProductsByCategoryParams);
    }

    public List<GetProductSearchRes> getPopularProducts() {
        String getPopularProductsQuery = "select productIdx,\n" +
                "       title,\n" +
                "       price,\n" +
                "       imageUrl,\n" +
                "       regionNameGu,\n" +
                "       regionNameTown,\n" +
                "       Product.status\n" +
                "from Product\n" +
                "         join ProductImage PI on Product.productIdx = PI.productId\n" +
                "         join UserInfo UI on UI.userInfoIdx = Product.sellerId\n" +
                "         join Region R on R.regionIdx = Product.regionId\n" +
                "         left join Wish W on Product.productIdx = W.productId\n" +
                "where UI.status = 'normal' and Product.status = 'normal'\n" +
                "group by productIdx order by viewCount + count(W.wishIdx) desc";
        return this.jdbcTemplate.query(getPopularProductsQuery,
                (rs, rowNum) -> new GetProductSearchRes(
                        rs.getInt("productIdx"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getString("imageUrl"),
                        rs.getString("regionNameGu"),
                        rs.getString("regionNameTown"),
                        rs.getString("status"))
        );
    }

    public List<GetProductSearchRes> getRandomProducts() {
        String getPopularProductsQuery = "select productIdx,\n" +
                "       title,\n" +
                "       price,\n" +
                "       imageUrl,\n" +
                "       regionNameGu,\n" +
                "       regionNameTown,\n" +
                "       Product.status\n" +
                "from Product\n" +
                "         join ProductImage PI on Product.productIdx = PI.productId\n" +
                "         join UserInfo UI on UI.userInfoIdx = Product.sellerId\n" +
                "         join Region R on R.regionIdx = Product.regionId\n" +
                "         left join Wish W on Product.productIdx = W.productId\n" +
                "where UI.status = 'normal' and Product.status = 'normal'\n" +
                "group by productIdx order by rand() limit 10";
        return this.jdbcTemplate.query(getPopularProductsQuery,
                (rs, rowNum) -> new GetProductSearchRes(
                        rs.getInt("productIdx"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getString("imageUrl"),
                        rs.getString("regionNameGu"),
                        rs.getString("regionNameTown"),
                        rs.getString("status"))
        );
    }

    public List<GetProductWish> getWishProducts(int userInfoId) {
        String getWishProductsQuery = "select productIdx,\n" +
                "       case\n" +
                "           when timestampdiff(MINUTE, Product.createdAt, CURRENT_TIMESTAMP()) < 60\n" +
                "               then concat(timestampdiff(MINUTE, Product.createdAt, CURRENT_TIMESTAMP()), '분 전')\n" +
                "           when timestampdiff(HOUR, Product.createdAt, CURRENT_TIMESTAMP()) < 24\n" +
                "               then concat(timestampdiff(HOUR, Product.createdAt, CURRENT_TIMESTAMP()), '시간 전')\n" +
                "           when timestampdiff(DAY, Product.createdAt, CURRENT_TIMESTAMP()) < 30\n" +
                "               then concat(timestampdiff(DAY, Product.createdAt, CURRENT_TIMESTAMP()), '일 전')\n" +
                "           else date_format(Product.createdAt, '%Y년-%m월-%d일')\n" +
                "           end as createdAt,\n" +
                "       title,\n" +
                "       price,\n" +
                "       case\n" +
                "           when timestampdiff(MINUTE, Product.pulledAt, CURRENT_TIMESTAMP()) < 60\n" +
                "               then concat('끌올 ', timestampdiff(MINUTE, Product.pulledAt, CURRENT_TIMESTAMP()), '분 전')\n" +
                "           when timestampdiff(HOUR, Product.pulledAt, CURRENT_TIMESTAMP()) < 24\n" +
                "               then concat('끌올 ', timestampdiff(HOUR, Product.pulledAt, CURRENT_TIMESTAMP()), '시간 전')\n" +
                "           when timestampdiff(DAY, Product.pulledAt, CURRENT_TIMESTAMP()) < 30\n" +
                "               then concat('끌올 ', timestampdiff(DAY, Product.pulledAt, CURRENT_TIMESTAMP()), '일 전')\n" +
                "           else date_format(Product.pulledAt, '%Y년-%m월-%d일')\n" +
                "           end as pulledAt,\n" +
                "       imageUrl,\n" +
                "       regionNameGu,\n" +
                "       regionNameTown,\n" +
                "       count(W.wishIdx),\n" +
                "       W.userInfoId as userInfoId\n" +
                "from Product\n" +
                "         join ProductImage PI on Product.productIdx = PI.productId\n" +
                "         join UserInfo UI on UI.userInfoIdx = Product.sellerId\n" +
                "         join Region R on R.regionIdx = Product.regionId\n" +
                "         left join Wish W on Product.productIdx = W.productId\n" +
                "where UI.status = 'normal'\n" +
                "  and Product.status = 'normal'\n" +
                "and W.status = 'normal'\n" +
                "group by wishIdx\n" +
                "having userInfoId = ?\n" +
                "order by date(Product.createdAt) desc";
        int getWishProductsParams = userInfoId;
        return this.jdbcTemplate.query(getWishProductsQuery,
                (rs, rowNum) -> new GetProductWish(
                        rs.getInt("productIdx"),
                        rs.getString("createdAt"),
                        rs.getString("title"),
                        rs.getInt("price"),
                        rs.getString("pulledAt"),
                        rs.getString("imageUrl"),
                        rs.getString("regionNameGu"),
                        rs.getString("regionNameTown"),
                        rs.getInt("count(W.wishIdx)"),
                        rs.getInt("userInfoId")),
                getWishProductsParams);
    }

    public int createKeyword(PostKeywordReq postKeywordReq) {
        String createKeywordQuery = "insert into Keyword (userInfoId, keyword) values (?, ?)";
        Object[] createKeywordParams = new Object[]{
                postKeywordReq.getUserInfoId(),
                postKeywordReq.getKeyword()
        };
        return this.jdbcTemplate.update(createKeywordQuery, createKeywordParams);
    }
}
