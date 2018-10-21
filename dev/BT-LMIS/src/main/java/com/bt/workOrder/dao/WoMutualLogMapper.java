package com.bt.workOrder.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.bt.workOrder.bean.WoMutualLog;

public interface WoMutualLogMapper {

    int insert(WoMutualLog record);

	List<Map<String, String>> selectAllByWoNo(@Param("woNo")String woNo);
	
	List<String> selectNewAccessory(@Param("woNo")String woNo);

}