package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetUserRes> getUsers() {
        String getUsersQuery = "select * from UserInfo";
        return this.jdbcTemplate.query(getUsersQuery,
                (rs, rowNum) -> new GetUserRes(
                        rs.getInt("userInfoIdx"),
                        rs.getString("status"),
                        rs.getString("createdAt"),
                        rs.getString("phoneNumber"),
                        rs.getString("nickname"),
                        rs.getString("profileImageUrl"))
        );
    }

    public List<GetUserRes> getUsersByName(String name) {
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

    public GetUserRes getUser(int userInfoIdx) {
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


    public int createUser(PostUserReq postUserReq) {
        try {
            String createUserQuery = "insert into UserInfo (phoneNumber, password, nickname, profileImageUrl) VALUES (?,?,?,?)";
            Object[] createUserParams = new Object[]{postUserReq.getPhoneNumber(), postUserReq.getPassword(), postUserReq.getNickname(), postUserReq.getProfileImageUrl()};
            this.jdbcTemplate.update(createUserQuery, createUserParams);

            String lastInsertIdQuery = "select last_insert_id()";
            int lastId = this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
            String createRegionIdQuery = "insert into Region (userInfoId) values (?)";
            return this.jdbcTemplate.queryForObject(createRegionIdQuery, int.class, lastId);
//        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
        } catch (EmptyResultDataAccessException e) {
            throw new RuntimeException("EmptyResult");

        } catch (IncorrectResultSizeDataAccessException e) {
            String lastInsertImageIdQuery = "select last_insert_id()";
            return this.jdbcTemplate.queryForObject(lastInsertImageIdQuery, int.class);
        }
    }

    public int checkPhoneNumber(String phoneNumber) {
        String checkPhoneNumberQuery = "select exists(select phoneNumber from UserInfo where phoneNumber = ?)";
        String checkPhoneNumberParams = phoneNumber;
        return this.jdbcTemplate.queryForObject(checkPhoneNumberQuery,
                int.class,
                checkPhoneNumberParams);

    }

    public User getUserByPhoneNumber(PostLoginReq postLoginReq) {
        String getUserByPhoneNumberQuery = "select userInfoIdx, phoneNumber, password, nickname, profileImageUrl from UserInfo where phoneNumber = ?";
        String getUserByPhoneNumberParams = postLoginReq.getPhoneNumber();

        return this.jdbcTemplate.queryForObject(getUserByPhoneNumberQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("userInfoIdx"),
                        rs.getString("phoneNumber"),
                        rs.getString("password"),
                        rs.getString("nickname"),
                        rs.getString("profileImageUrl")),
                getUserByPhoneNumberParams
        );

    }

    public int modifyUserInfo(PatchUserReq patchUserReq) {
        String modifyUserInfoQuery = "update UserInfo set nickname = ?, profileImageUrl = ? where userInfoIdx = ? ";
        Object[] modifyUserInfoParams = new Object[]{patchUserReq.getNickname(), patchUserReq.getProfileImageUrl(), patchUserReq.getUserInfoIdx()};

        return this.jdbcTemplate.update(modifyUserInfoQuery, modifyUserInfoParams);
    }

    public int deleteUserInfo(DeleteUserReq deleteUserReq) {
        String deleteUserInfoQuery = "update UserInfo set status = 'deleted', closingReason = ? where userInfoIdx = ? ";
        Object[] deleteUserInfoParams = new Object[]{deleteUserReq.getClosingReason(), deleteUserReq.getUserInfoIdx()};

        return this.jdbcTemplate.update(deleteUserInfoQuery, deleteUserInfoParams);
    }

    public User userJoin(PostUserLoginReq postUserLoginReq) {
        String createUserQuery = "insert into UserInfo (phoneNumber, password, nickname, profileImageUrl) VALUES (?,?,?,?)";
        Object[] createUserParams = new Object[]{postUserLoginReq.getPhoneNumber(), postUserLoginReq.getPassword(), postUserLoginReq.getNickname(), postUserLoginReq.getProfileImageUrl()};
        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertUserIdQuery = "select last_insert_id()";
        int lastInsertUserId = this.jdbcTemplate.queryForObject(lastInsertUserIdQuery, int.class);

        String createUserRegionQuery = "insert into Region (userInfoId, regionNameCity, regionNameGu, regionNameTown) VALUES (?,?,?,?)";
        Object[] createUserRegionParams = new Object[]{lastInsertUserId, postUserLoginReq.getRegionNameCity(), postUserLoginReq.getRegionNameGu(), postUserLoginReq.getRegionNameTown()};
        this.jdbcTemplate.update(createUserRegionQuery,createUserRegionParams);

        // String lastInsertIdQuery = "select last_insert_id()";
        // return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
        // return lastInsertUserId;
        String getUserByPhoneNumberQuery = "select userInfoIdx, phoneNumber, password, nickname, profileImageUrl from UserInfo where phoneNumber = ?";
        String getUserByPhoneNumberParams = postUserLoginReq.getPhoneNumber();
        return this.jdbcTemplate.queryForObject(getUserByPhoneNumberQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("userInfoIdx"),
                        rs.getString("phoneNumber"),
                        rs.getString("password"),
                        rs.getString("nickname"),
                        rs.getString("profileImageUrl")),
                getUserByPhoneNumberParams
        );
    }

    public User userLogin(PostUserLoginReq postUserLoginReq) {
        String getUserByPhoneNumberQuery = "select userInfoIdx, phoneNumber, password, nickname, profileImageUrl from UserInfo where phoneNumber = ?";
        String getUserByPhoneNumberParams = postUserLoginReq.getPhoneNumber();

        return this.jdbcTemplate.queryForObject(getUserByPhoneNumberQuery,
                (rs, rowNum) -> new User(
                        rs.getInt("userInfoIdx"),
                        rs.getString("phoneNumber"),
                        rs.getString("password"),
                        rs.getString("nickname"),
                        rs.getString("profileImageUrl")),
                getUserByPhoneNumberParams
        );

    }

}
