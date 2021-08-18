package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers(){
        String getUsersQuery = "select * from UserInfo";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs,rowNum) -> new GetUserRes(
                        rs.getInt("userInfoIdx"),
                        rs.getString("status"),
                        rs.getString("createdAt"),
                        rs.getString("phoneNumber"),
                        rs.getString("nickname"),
                        rs.getString("profileImageUrl"))
                );
    }

    public List<GetUserRes> getUsersByName(String name){
        String getUsersByNameQuery = "select * from UserInfo where nickname =?";
        String getUsersByNameParams = name;
        return this.jdbcTemplate.query(getUsersByNameQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userInfoIdx"),
                        rs.getString("status"),
                        rs.getString("createdAt"),
                        rs.getString("phoneNumber"),
                        rs.getString("nickname"),
                        rs.getString("profileImageUrl")),
        getUsersByNameParams);
    }

    public GetUserRes getUser(int userInfoIdx){
        String getUserQuery = "select * from UserInfo where userInfoIdx = ?";
        int getUserParams = userInfoIdx;
        return this.jdbcTemplate.queryForObject(getUserQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userInfoIdx"),
                        rs.getString("status"),
                        rs.getString("createdAt"),
                        rs.getString("phoneNumber"),
                        rs.getString("nickname"),
                        rs.getString("profileImageUrl")),
                getUserParams);
    }
    

    public int createUser(PostUserReq postUserReq){
        String createUserQuery = "insert into UserInfo (userID, password, nickname, profileImageUrl) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserReq.getPhoneNumber(), postUserReq.getPassword(), postUserReq.getNickname(), postUserReq.getProfileImageUrl()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";
        return this.jdbcTemplate.queryForObject(lastInsertIdQuery,int.class);
    }

    public int checkPhoneNumber(String phoneNumber){
        String checkPhoneNumberQuery = "select exists(select phoneNumber from UserInfo where phoneNumber = ?)";
        String checkPhoneNumberParams = phoneNumber;
        return this.jdbcTemplate.queryForObject(checkPhoneNumberQuery,
                int.class,
                checkPhoneNumberParams);

    }

    public int modifyUserName(PatchUserReq patchUserReq){
        String modifyUserNameQuery = "update UserInfo set userName = ? where userIdx = ? ";
        Object[] modifyUserNameParams = new Object[]{patchUserReq.getUserName(), patchUserReq.getUserIdx()};

        return this.jdbcTemplate.update(modifyUserNameQuery,modifyUserNameParams);
    }

    public User getPhoneNumber(PostLoginReq postLoginReq){
        String getPhoneNumberQuery = "select userInfoIdx, phoneNumber, password, nickname, profileImageUrl from UserInfo where phoneNumber = ?";
        String getPhoneNumberParams = postLoginReq.getPhoneNumber();

        return this.jdbcTemplate.queryForObject(getPhoneNumberQuery,
                (rs,rowNum)-> new User(
                        rs.getInt("userInfoIdx"),
                        rs.getString("phoneNumber"),
                        rs.getString("password"),
                        rs.getString("nickname"),
                        rs.getString("profileImageUrl")),
                getPhoneNumberParams
                );

    }



}
