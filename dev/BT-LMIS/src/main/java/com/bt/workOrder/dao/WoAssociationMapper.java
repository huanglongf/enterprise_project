package com.bt.workOrder.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.workOrder.model.WoAssociation;

public interface WoAssociationMapper {

    int insert(WoAssociation record);

    List<String> selectByWoNoP(@Param("woNoP")String woNoP);


}