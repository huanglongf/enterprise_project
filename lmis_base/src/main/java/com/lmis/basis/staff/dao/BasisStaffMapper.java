package com.lmis.basis.staff.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.basis.staff.model.BasisStaff;
import com.lmis.basis.staff.model.ViewBasisStaff;
import com.lmis.framework.baseDao.BaseMapper;

/** 
 * @ClassName: BasisStaffMapper
 * @Description: TODO(员工DAO映射接口)
 * @author codeGenerator
 * @date 2018-01-19 16:11:56
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface BasisStaffMapper<T extends BasisStaff> extends BaseMapper<T> {
	
	/**
	 * @Title: retrieveViewBasisStaff
	 * @Description: TODO(查询view_basis_staff)
	 * @param viewBasisStaff
	 * @return: List<ViewBasisStaff>
	 * @author: codeGenerator
	 * @date: 2018-01-19 16:11:56
	 */
	List<ViewBasisStaff> retrieveViewBasisStaff(ViewBasisStaff viewBasisStaff);

	List<Map<String,Object>> selectStaffNotInStaffCodes(Map<String,Object> map);

	List<T> checkStaffUser(T checkStaffUser);
	
}
