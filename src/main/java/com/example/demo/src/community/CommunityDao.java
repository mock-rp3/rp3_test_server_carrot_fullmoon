package com.example.demo.src.community;

import com.example.demo.src.community.model.*;
import com.example.demo.src.product.model.GetProductRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CommunityDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<GetCommunityRes> getCommunities() {
        String getCommunitiesQuery = "select communityIdx,\n" +
                "       Community.createdAt,\n" +
                "       Community.description,\n" +
                "       CC.name,\n" +
                "       R.regionNameTown,\n" +
                "       count(R2.userInfoId),\n" +
                "       count(C.userInfoId)\n" +
                "from Community\n" +
                "         join CmCategory CC on CC.cmCategoryIdx = Community.categoryId\n" +
                "         join UserInfo UI on UI.userInfoIdx = Community.userInfoId\n" +
                "         join Region R on UI.userInfoIdx = R.userInfoId\n" +
                "         left join Comment C on Community.communityIdx = C.communityId\n" +
                "         left join Reaction R2 on Community.communityIdx = R2.communityId\n" +
                "where (UI.status & Community.status = 'normal')\n" +
                "group by communityIdx";
        return this.jdbcTemplate.query(getCommunitiesQuery,
                (rs, rowNum) -> new GetCommunityRes(
                        rs.getInt("communityIdx"),
                        rs.getString("createdAt"),
                        rs.getString("description"),
                        rs.getString("name"),
                        rs.getString("regionNameTown"),
                        rs.getInt("count(R2.userInfoId)"),
                        rs.getInt("count(C.userInfoId)"))
        );
    }

    public List<GetCommunityRes> getCommunitiesByDescription(String keyword) {
        String getCommunitiesByDescriptionQuery = "select communityIdx,\n" +
                "       Community.createdAt,\n" +
                "       Community.description as keyword,\n" +
                "       CC.name,\n" +
                "       R.regionNameTown,\n" +
                "       count(R2.userInfoId),\n" +
                "       count(C.userInfoId)\n" +
                "from Community\n" +
                "         join CmCategory CC on CC.cmCategoryIdx = Community.categoryId\n" +
                "         join UserInfo UI on UI.userInfoIdx = Community.userInfoId\n" +
                "         join Region R on UI.userInfoIdx = R.userInfoId\n" +
                "         left join Comment C on Community.communityIdx = C.communityId\n" +
                "         left join Reaction R2 on Community.communityIdx = R2.communityId\n" +
                "where (UI.status & Community.status = 'normal')\n" +
                "group by communityIdx\n" +
                "having keyword LIKE concat('%', ?, '%')";
        String getCommunitiesByDescriptionParams = keyword;
        return this.jdbcTemplate.query(getCommunitiesByDescriptionQuery,
                (rs, rowNum) -> new GetCommunityRes(
                        rs.getInt("communityIdx"),
                        rs.getString("createdAt"),
                        rs.getString("keyword"),
                        rs.getString("name"),
                        rs.getString("regionNameTown"),
                        rs.getInt("count(R2.userInfoId)"),
                        rs.getInt("count(C.userInfoId)")),
                getCommunitiesByDescriptionParams
        );
    }

    public List<GetCommunityDetailRes> getCommunity(int communityIdx) {
        String getCommunityQuery = "select communityIdx,\n" +
                "       CC.name,\n" +
                "       UI.userInfoIdx,\n" +
                "       UI.profileImageUrl,\n" +
                "       UI.nickname,\n" +
                "       R.regionNameGu,\n" +
                "       R.regionNameTown,\n" +
                "       R.authCount,\n" +
                "       Community.createdAt,\n" +
                "       Community.description,\n" +
                "       count(R2.userInfoId),\n" +
                "       count(C.userInfoId),\n" +
                "       U.userInfoIdx,\n" +
                "       U.profileImageUrl,\n" +
                "       U.nickname,\n" +
                "       C.createdAt,\n" +
                "       C.description\n" +
                "from Community\n" +
                "         join CmCategory CC on CC.cmCategoryIdx = Community.categoryId\n" +
                "         join UserInfo UI on UI.userInfoIdx = Community.userInfoId\n" +
                "         join Region R on UI.userInfoIdx = R.userInfoId\n" +
                "         left join Comment C on Community.communityIdx = C.communityId\n" +
                "         left join Reaction R2 on Community.communityIdx = R2.communityId\n" +
                "         left join Comment C2 on UI.userInfoIdx = C2.commentIdx\n" +
                "         left join UserInfo U on U.userInfoIdx = C2.commentIdx\n" +
                "where (UI.status & Community.status = 'normal')\n" +
                "  and communityIdx = ?\n" +
                "group by communityIdx";
        int getCommunityParams = communityIdx;
        return this.jdbcTemplate.query(getCommunityQuery,
                (rs, rowNum) -> new GetCommunityDetailRes(
                        rs.getInt("communityIdx"),
                        rs.getString("name"),
                        rs.getInt("UI.userInfoIdx"),
                        rs.getString("UI.profileImageUrl"),
                        rs.getString("UI.nickname"),
                        rs.getString("R.regionNameGu"),
                        rs.getString("regionNameTown"),
                        rs.getInt("authCount"),
                        rs.getString("Community.createdAt"),
                        rs.getString("Community.description"),
                        rs.getInt("count(R2.userInfoId)"),
                        rs.getInt("count(C.userInfoId)"),
                        rs.getInt("U.userInfoIdx"),
                        rs.getString("U.profileImageUrl"),
                        rs.getString("U.nickname"),
                        rs.getString("C.createdAt"),
                        rs.getString("C.description")
                ),
                getCommunityParams
        );
    }
}
