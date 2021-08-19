package com.example.demo.src.product;


import com.example.demo.src.product.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetProductRes> getProducts(){
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
                (rs,rowNum) -> new GetProductRes(
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

    public List<GetProductRes> getProductsByTitle(String title){
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
                "where (UI.status & Product.status = 'normal') AND title = ?\n" +
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

//    public GetProductRes getProduct(int productIdx){
//        String getProductQuery = "select * from Product where productIdx = ?";
//        int getProductParams = productIdx;
//        return this.jdbcTemplate.queryForObject(getProductQuery,
//                (rs, rowNum) -> new GetProductRes(
//                        rs.getInt("productIdx"),
//                        rs.getString("status"),
//                        rs.getString("createdAt"),
//                        rs.getString("title"),
//                        rs.getString("description"),
//                        rs.getInt("price"),
//                        rs.getInt("viewCount"),
//                        rs.getString("pulledAt"),
//                        rs.getString("category"),
//                        rs.getString("canProposal"),
//                        rs.getInt("sellerId"),
//                        rs.getInt("regionId")),
//                getProductParams);
//    }
    

//    public int createUser(PostUserReq postUserReq){
//        String createUserQuery = "insert into UserInfo (userID, password, nickname, profileImageUrl) VALUES (?,?,?,?)";
//        Object[] createUserParams = new Object[]{postUserReq.getUserID(), postUserReq.getPassword(), postUserReq.getNickname(), postUserReq.getProfileImageUrl()};
//        this.jdbcTemplate.update(createUserQuery, createUserParams);
//
//        String lastInsertIdQuery = "select last_insert_id()";
//        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
//    }
//
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
