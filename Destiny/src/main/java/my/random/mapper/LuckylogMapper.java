package my.random.mapper;

import java.util.HashMap;
import java.util.List;

import my.random.bean.Luckylog;
import my.random.bean.LuckylogExample;
import my.random.bean.LuckylogExtends;

import org.apache.ibatis.annotations.Param;

public interface LuckylogMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lucky_log
     *
     * @mbggenerated Tue Feb 23 18:23:42 KST 2021
     */
    int countByExample(LuckylogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lucky_log
     *
     * @mbggenerated Tue Feb 23 18:23:42 KST 2021
     */
    int deleteByExample(LuckylogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lucky_log
     *
     * @mbggenerated Tue Feb 23 18:23:42 KST 2021
     */
    int deleteByPrimaryKey(Long seq);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lucky_log
     *
     * @mbggenerated Tue Feb 23 18:23:42 KST 2021
     */
    int insert(Luckylog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lucky_log
     *
     * @mbggenerated Tue Feb 23 18:23:42 KST 2021
     */
    int insertSelective(Luckylog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lucky_log
     *
     * @mbggenerated Tue Feb 23 18:23:42 KST 2021
     */
    List<Luckylog> selectByExample(LuckylogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lucky_log
     *
     * @mbggenerated Tue Feb 23 18:23:42 KST 2021
     */
    Luckylog selectByPrimaryKey(Long seq);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lucky_log
     *
     * @mbggenerated Tue Feb 23 18:23:42 KST 2021
     */
    int updateByExampleSelective(@Param("record") Luckylog record, @Param("example") LuckylogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lucky_log
     *
     * @mbggenerated Tue Feb 23 18:23:42 KST 2021
     */
    int updateByExample(@Param("record") Luckylog record, @Param("example") LuckylogExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lucky_log
     *
     * @mbggenerated Tue Feb 23 18:23:42 KST 2021
     */
    int updateByPrimaryKeySelective(Luckylog record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lucky_log
     *
     * @mbggenerated Tue Feb 23 18:23:42 KST 2021
     */
    int updateByPrimaryKey(Luckylog record);

	List<LuckylogExtends> selectLastWinner(HashMap<String, Object> param);
}