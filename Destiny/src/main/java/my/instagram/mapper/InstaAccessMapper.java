package my.instagram.mapper;

import java.util.List;
import my.instagram.bean.InstaAccess;
import my.instagram.bean.InstaAccessExample;
import org.apache.ibatis.annotations.Param;

public interface InstaAccessMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table insta_access
     *
     * @mbggenerated Thu Nov 16 17:15:53 KST 2017
     */
    int countByExample(InstaAccessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table insta_access
     *
     * @mbggenerated Thu Nov 16 17:15:53 KST 2017
     */
    int deleteByExample(InstaAccessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table insta_access
     *
     * @mbggenerated Thu Nov 16 17:15:53 KST 2017
     */
    int deleteByPrimaryKey(String userid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table insta_access
     *
     * @mbggenerated Thu Nov 16 17:15:53 KST 2017
     */
    int insert(InstaAccess record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table insta_access
     *
     * @mbggenerated Thu Nov 16 17:15:53 KST 2017
     */
    int insertSelective(InstaAccess record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table insta_access
     *
     * @mbggenerated Thu Nov 16 17:15:53 KST 2017
     */
    List<InstaAccess> selectByExample(InstaAccessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table insta_access
     *
     * @mbggenerated Thu Nov 16 17:15:53 KST 2017
     */
    InstaAccess selectByPrimaryKey(String userid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table insta_access
     *
     * @mbggenerated Thu Nov 16 17:15:53 KST 2017
     */
    int updateByExampleSelective(@Param("record") InstaAccess record, @Param("example") InstaAccessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table insta_access
     *
     * @mbggenerated Thu Nov 16 17:15:53 KST 2017
     */
    int updateByExample(@Param("record") InstaAccess record, @Param("example") InstaAccessExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table insta_access
     *
     * @mbggenerated Thu Nov 16 17:15:53 KST 2017
     */
    int updateByPrimaryKeySelective(InstaAccess record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table insta_access
     *
     * @mbggenerated Thu Nov 16 17:15:53 KST 2017
     */
    int updateByPrimaryKey(InstaAccess record);
}