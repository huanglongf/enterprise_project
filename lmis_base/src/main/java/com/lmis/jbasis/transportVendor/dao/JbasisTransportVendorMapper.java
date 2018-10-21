package com.lmis.jbasis.transportVendor.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.jbasis.transportVendor.model.JbasisTransportVendor;
import com.lmis.jbasis.transportVendor.model.ViewJbasisTransportVendor;

/** 
 * @ClassName: JbasisTransportVendorMapper
 * @Description: TODO(运输供应商(基础数据)DAO映射接口)
 * @author codeGenerator
 * @date 2018-04-19 11:14:05
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface JbasisTransportVendorMapper<T extends JbasisTransportVendor> extends BaseMapper<T> {
	
	/**
	 * @Title: retrieveViewJbasisTransportVendor
	 * @Description: TODO(查询view_jbasis_transport_vendor)
	 * @param viewJbasisTransportVendor
	 * @return: List<ViewJbasisTransportVendor>
	 * @author: codeGenerator
	 * @date: 2018-04-19 11:14:05
	 */
	List<ViewJbasisTransportVendor> retrieveViewJbasisTransportVendor(ViewJbasisTransportVendor viewJbasisTransportVendor);

    List<?> findVendorType(String code);

    String checkType(String transportType);
	
}
