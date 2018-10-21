package com.bt.orderPlatform.dao;

import org.apache.ibatis.annotations.Param;

import com.bt.orderPlatform.model.PcodeMappering;

/**
* @ClassName: PcodeMapperingMapper
* @Description: TODO(PcodeMapperingMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface PcodeMapperingMapper<T>  {

	PcodeMappering queryBySfopcode(@Param("sf_opcode")String sf_opcode);
	
	
}
