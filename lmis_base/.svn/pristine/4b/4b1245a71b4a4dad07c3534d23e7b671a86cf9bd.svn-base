package com.lmis.basis.grade.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.basis.grade.model.BasisGrade;
import com.lmis.basis.grade.model.ViewBasisGrade;

/** 
 * @ClassName: BasisGradeMapper
 * @Description: TODO(职级DAO映射接口)
 * @author codeGenerator
 * @date 2018-01-19 16:11:26
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface BasisGradeMapper<T extends BasisGrade> extends BaseMapper<T> {
	
	/**
	 * @Title: retrieveViewBasisGrade
	 * @Description: TODO(查询view_basis_grade)
	 * @param viewBasisGrade
	 * @return: List<ViewBasisGrade>
	 * @author: codeGenerator
	 * @date: 2018-01-19 16:11:26
	 */
	List<ViewBasisGrade> retrieveViewBasisGrade(ViewBasisGrade viewBasisGrade);
	
}
