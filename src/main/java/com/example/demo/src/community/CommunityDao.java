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
}
