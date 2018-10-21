package com.bt.orderPlatform.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bt.orderPlatform.model.OrganizationInformation;

/**
* @ClassName: OrganizationInformationMapper
* @Description: TODO(OrganizationInformationMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface OrganizationInformationMapper<T> {

	OrganizationInformation selectById(String orgid);

	List findAll();

	List<OrganizationInformation> findOrgName(String org_name);

	List queryAllBypid(String idvar);

	List queryAll(@Param("idvar")String idvar);
	
}
