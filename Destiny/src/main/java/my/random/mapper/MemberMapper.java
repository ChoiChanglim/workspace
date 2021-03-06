package my.random.mapper;

import java.util.List;
import my.random.bean.Member;
import my.random.bean.MemberExample;
import my.random.bean.MemberKey;
import org.apache.ibatis.annotations.Param;

public interface MemberMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Fri Jul 24 02:20:09 KST 2020
     */
    int countByExample(MemberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Fri Jul 24 02:20:09 KST 2020
     */
    int deleteByExample(MemberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Fri Jul 24 02:20:09 KST 2020
     */
    int deleteByPrimaryKey(MemberKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Fri Jul 24 02:20:09 KST 2020
     */
    int insert(Member record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Fri Jul 24 02:20:09 KST 2020
     */
    int insertSelective(Member record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Fri Jul 24 02:20:09 KST 2020
     */
    List<Member> selectByExample(MemberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Fri Jul 24 02:20:09 KST 2020
     */
    Member selectByPrimaryKey(MemberKey key);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Fri Jul 24 02:20:09 KST 2020
     */
    int updateByExampleSelective(@Param("record") Member record, @Param("example") MemberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Fri Jul 24 02:20:09 KST 2020
     */
    int updateByExample(@Param("record") Member record, @Param("example") MemberExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Fri Jul 24 02:20:09 KST 2020
     */
    int updateByPrimaryKeySelective(Member record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table member
     *
     * @mbggenerated Fri Jul 24 02:20:09 KST 2020
     */
    int updateByPrimaryKey(Member record);
}