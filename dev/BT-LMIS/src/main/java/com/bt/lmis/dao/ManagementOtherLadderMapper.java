package com.bt.lmis.dao;

import java.util.List;

import com.bt.lmis.model.ManagementOtherLadder;

public interface ManagementOtherLadderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ManagementOtherLadder record);

    int insertSelective(ManagementOtherLadder record);

    ManagementOtherLadder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ManagementOtherLadder record);

    int updateByPrimaryKey(ManagementOtherLadder record);

	List<ManagementOtherLadder> findList(ManagementOtherLadder managementOtherLadder);
}