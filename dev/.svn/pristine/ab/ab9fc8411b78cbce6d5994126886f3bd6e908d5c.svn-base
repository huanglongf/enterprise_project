package com.bt.lmis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.lmis.model.JipaoConditionEx;

public interface JipaoConditionExMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(JipaoConditionEx record);

    int insertSelective(JipaoConditionEx record);

    JipaoConditionEx selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(JipaoConditionEx record);

    public Integer judgeUnique(JipaoConditionEx jbpc);
    
    public List<JipaoConditionEx> selectAll(@Param("con_id")Integer con_id, @Param("belong_to")String belong_to);
    
    public Integer delJBPConditon(@Param("id")int id);
	
}