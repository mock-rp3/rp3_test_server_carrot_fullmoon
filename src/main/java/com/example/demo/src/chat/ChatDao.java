package com.example.demo.src.chat;

import com.example.demo.src.chat.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ChatDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetChatRes> getChatRooms() {
        String getChatRoomsQuery = "select chatRoomIdx,\n" +
                "       U.userInfoIdx,\n" +
                "       ChatRoom.createdAt,\n" +
                "       ChatRoom.updatedAt,\n" +
                "       ChatRoom.productId,\n" +
                "       PI.imageUrl,\n" +
                "       ChatRoom.userInfoId,\n" +
                "       U.profileImageUrl,\n" +
                "       regionNameTown,\n" +
                "       U.nickname,\n" +
                "       chatMessage\n" +
                "from ChatRoom\n" +
                "         left join UserInfo UI on ChatRoom.userInfoId = UI.userInfoIdx\n" +
                "         join Product P on ChatRoom.productId = P.productIdx\n" +
                "         join ProductImage PI on P.productIdx = PI.productId\n" +
                "         left join ChatMessage CM on ChatRoom.chatRoomIdx = CM.chatRoomId\n" +
                "         join UserInfo U on CM.senderId = U.userInfoIdx\n" +
                "         join Region R on P.regionId = R.regionIdx\n" +
                "group by U.userInfoIdx";
        return this.jdbcTemplate.query(getChatRoomsQuery,
                (rs, rowNum) -> new GetChatRes(
                        rs.getInt("chatRoomIdx"),
                        rs.getInt("userInfoIdx"),
                        rs.getString("createdAt"),
                        rs.getString("updatedAt"),
                        rs.getInt("productId"),
                        rs.getString("imageUrl"),
                        rs.getInt("userInfoId"),
                        rs.getString("profileImageUrl"),
                        rs.getString("regionNameTown"),
                        rs.getString("nickname"),
                        rs.getString("chatMessage"))
        );
    }


    public List<GetChatDetailRes> getChatRoom(int chatIdx) {
        String getChatRoomQuery = "select chatRoomIdx,\n" +
                "       U.nickname,\n" +
                "       UV.mannerGrade,\n" +
                "       ChatRoom.productId,\n" +
                "       PI.imageUrl,\n" +
                "       P.status,\n" +
                "       P.price,\n" +
                "       P.title,\n" +
                "       P.canProposal,\n" +
                "       ChatRoom.userInfoId,\n" +
                "       U.profileImageUrl,\n" +
                "       CM.createdAt,\n" +
                "       CM.chatMessage\n" +
                "from ChatRoom\n" +
                "         left join UserInfo UI on ChatRoom.userInfoId = UI.userInfoIdx\n" +
                "         join Product P on ChatRoom.productId = P.productIdx\n" +
                "         join ProductImage PI on P.productIdx = PI.productId\n" +
                "         left join ChatMessage CM on ChatRoom.chatRoomIdx = CM.chatRoomId\n" +
                "         join UserInfo U on CM.senderId = U.userInfoIdx\n" +
                "         join Region R on P.regionId = R.regionIdx\n" +
                "         left join UserVar UV on U.userInfoIdx = UV.userInfoId\n" +
                "group by chatRoomIdx";
        int getChatRoomParams = chatIdx;
        return this.jdbcTemplate.query(getChatRoomQuery,
                (rs, rowNum) -> new GetChatDetailRes(
                        rs.getInt("chatRoomIdx"),
                        rs.getString("nickname"),
                        rs.getInt("mannerGrade"),
                        rs.getInt("productId"),
                        rs.getString("imageUrl"),
                        rs.getString("status"),
                        rs.getInt("price"),
                        rs.getString("title"),
                        rs.getString("canProposal"),
                        rs.getInt("userInfoId"),
                        rs.getString("profileImageUrl"),
                        rs.getString("createdAt"),
                        rs.getString("chatMessage")),
                getChatRoomParams
        );
    }

//    public int createChat(PostChatReq postChatReq) {
//        String createChatQuery = "insert into Community (description, categoryId, regionId, userInfoId) VALUES (?,?,?,?)";
//        Object[] createChatParams = new Object[]{postChatReq.getDescription()};
//        this.jdbcTemplate.update(createChatQuery, createChatParams);
//
//        String lastInsertIdQuery = "select last_insert_id()";
//        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
//    }

}
