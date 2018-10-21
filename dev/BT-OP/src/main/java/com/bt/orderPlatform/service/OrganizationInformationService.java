package com.bt.orderPlatform.service;

import java.util.List;

import com.bt.orderPlatform.model.OrganizationInformation;

public interface OrganizationInformationService<T>  {

	OrganizationInformation selectById(String orgid);

	List queryAll();

	List<OrganizationInformation> findOrgName(String org_name);

	List queryAllBypid(String id);

	List queryAll(String string);

}
