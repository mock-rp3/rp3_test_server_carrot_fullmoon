package com.example.demo.src.product;


import com.example.demo.src.product.model.*;
import com.example.demo.src.user.model.DeleteUserReq;
import org.springframework.beans.factory.annotation.Autowired;
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
                "       Product.createdAt,\n" +
                "       title,\n" +
                "       price,\n" +
                "       pulledAt,\n" +
                "       imageUrl,\n" +
                "       regionNameGu,\n" +
                "       regionNameTown,\n" +
                "       count(W.wishIdx)\n" +
                "from Product\n" +
                "         join ProductImage PI on Product.productIdx = PI.productId\n" +
                "         join UserInfo UI on UI.userInfoIdx = Product.sellerId\n" +
                "         join Region R on R.regionIdx = Product.regionId\n" +
                "         join Wish W on Product.productIdx = W.productId\n" +
                "where UI.status & Product.status = 'normal'\n" +
                "group by productIdx";
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
                "       Product.createdAt,\n" +
                "       title,\n" +
                "       price,\n" +
                "       pulledAt,\n" +
                "       imageUrl,\n" +
                "       regionNameGu,\n" +
                "       regionNameTown,\n" +
                "       count(W.wishIdx)\n" +
                "from Product\n" +
                "         join ProductImage PI on Product.productIdx = PI.productId\n" +
                "         join UserInfo UI on UI.userInfoIdx = Product.sellerId\n" +
                "         join Region R on R.regionIdx = Product.regionId\n" +
                "         join Wish W on Product.productIdx = W.productId\n" +
                "where (UI.status & Product.status = 'normal') AND title LIKE concat('%',?,'%')\n" +
                "group by productIdx";
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
                "       P.createdAt,\n" +
                "       pulledAt,\n" +
                "       description,\n" +
                "       count(W.wishIdx),\n" +
                "       price,\n" +
                "       canProposal,\n" +
                "       UI.userInfoIdx\n" +
                "from Product P\n" +
                "         join ProductImage PI on P.productIdx = PI.productId\n" +
                "         join UserInfo UI on UI.userInfoIdx = P.sellerId\n" +
                "         join UserVar UV on UI.userInfoIdx = UV.userInfoId\n" +
                "         join Region R on R.regionIdx = P.regionId\n" +
                "         join Wish W on P.productIdx = W.productId\n" +
                "         join Category C on C.categoryIdx = P.categoryId\n" +
                "where (UI.status & P.status = 'normal')\n" +
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

//    public List<GetDetailAllRes> getAllDetail(int productIdx) {
//        String getDetailAllQuery = "select imageUrl from ProductImage where productIdx = ?";
//        int getDetailAllParams = productIdx;
//        return this.jdbcTemplate.query(getDetailAllQuery,
//                (rs,rowNum) -> new GetDetailImageRes(
//                        rs.getString("imageUrl")),
//                getDetailAllParams);
//    }


    public int createProduct(PostProductReq postProductReq) {
        int sellerId = postProductReq.getSellerId();
        System.out.println("셀러아이디 체크" + sellerId);
        String createProductQuery = "insert into Product (title" +
                ", description, price, canProposal, categoryId, sellerId, regionId)\n" +
                "values (?,?,?,?,?,?,(select regionIdx from Region where userInfoId = sellerId))";
        Object[] createProductParams = new Object[]{postProductReq.getTitle()
                , postProductReq.getDescription()
                , postProductReq.getPrice()
                , postProductReq.getCanProposal()
                , postProductReq.getCategoryId()
                , sellerId};
        this.jdbcTemplate.update(createProductQuery, createProductParams);

        String createProductImageQuery = "insert into ProductImage (imageUrl, productId) " +
                "VALUES (?,(select last_insert_id() from Product))" +
                ",(?,(select last_insert_id() from Product))";
        Object[] createProductImageParams = new Object[]{postProductReq.getImageUrl()};
        this.jdbcTemplate.update(createProductImageQuery, createProductImageParams);

        String lastInsertIdQuery = "select last_insert_id()";

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);

    }

    public int deleteProduct(int productIdx){
        String deleteProductQuery = "update Product set status = 'deleted' where productIdx = ? ";
        int deleteProductParams = productIdx;

        return this.jdbcTemplate.update(deleteProductQuery,deleteProductParams);
    }

//    public int checkID(String ID){
//        String checkIDQuery = "select exists(select userID from UserInfo where userID = ?)";
//        String checkIDParams = ID;
//        return this.jdbcTemplate.queryForObject(checkIDQuery,
//                int.class,
//                checkIDParams);
//
//    }

//    public int modifyUserName(PatchUserReq patchUserReq){
//        String modifyUserNameQuery = "update UserInfo set userName = ? where userIdx = ? ";
//        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getUserIdx()};
//
//        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
//    }

}
