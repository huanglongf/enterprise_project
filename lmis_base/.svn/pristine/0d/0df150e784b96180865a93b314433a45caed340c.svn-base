package com.lmis.basis.staff.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.lmis.basis.grade.dao.BasisGradeMapper;
import com.lmis.basis.grade.model.BasisGrade;
import com.lmis.sys.org.dao.SysOrgMapper;
import com.lmis.sys.org.model.SysOrg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmis.basis.orgStaff.dao.BasisOrgStaffMapper;
import com.lmis.basis.orgStaff.model.BasisOrgStaff;
import com.lmis.basis.staff.dao.BasisStaffMapper;
import com.lmis.basis.staff.model.BasisStaff;
import com.lmis.basis.staff.service.BasisStaffServiceInterface;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.constant.LBASEConstant;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;

/** 
 * @ClassName: BasisStaffServiceImpl
 * @Description: TODO(员工业务层实现类)
 * @author codeGenerator
 * @date 2018-01-19 16:11:56
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class BasisStaffServiceImpl<T extends BasisStaff> implements BasisStaffServiceInterface<T> {
	
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<BasisStaff> dynamicSqlService;
	@Autowired
	private BasisStaffMapper<T> basisStaffMapper;
	
	
	@Autowired
	private BasisOrgStaffMapper<BasisOrgStaff> basisOrgStaffMapper;

	@Autowired
	private BasisGradeMapper<BasisGrade> basisGradeMapper;

	@Autowired
	private SysOrgMapper<SysOrg> sysOrgMapper;

	@Autowired
	private HttpSession session;
	
	@Override
	public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception {
		return dynamicSqlService.executeSelect(dynamicSqlParam, pageObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		LmisResult<?> _lmisResult = dynamicSqlService.executeSelect(dynamicSqlParam);
		if(LmisConstant.RESULT_CODE_ERROR.equals(_lmisResult.getCode())) return _lmisResult;
		List<Map<String, Object>> result =  (List<Map<String, Object>>) _lmisResult.getData();
		if(ObjectUtils.isNull(result)) throw new Exception(LBASEConstant.EBASE000007);
		if(result.size() != 1) throw new Exception(LBASEConstant.EBASE000008);
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result.get(0));
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		// TODO(业务校验)

		BasisStaff staff = dynamicSqlService.generateTableModel((DynamicSqlParam<BasisStaff>) dynamicSqlParam, new BasisStaff()).getTableModel();
		//导入校验
		BasisGrade basisGrade = new BasisGrade();
		basisGrade.setIsDeleted(false);
		basisGrade.setIsDisabled(false);
		if(ObjectUtils.isNull(staff.getGrade())) throw new Exception(LBASEConstant.EBASE000080);
		basisGrade.setGradeCode(staff.getGrade());
		if (basisGradeMapper.retrieve(basisGrade).size()!=1) throw new Exception(LBASEConstant.EBASE000081);

		if(!ObjectUtils.isNull(staff.getOrgId())) {
			SysOrg sysOrg = new SysOrg();
			sysOrg.setIsDeleted(false);
			sysOrg.setIsDisabled(false);
			sysOrg.setOrgId(staff.getOrgId());
			if (sysOrgMapper.retrieve(sysOrg).size()!=1) throw new Exception(LBASEConstant.EBASE000028);
		}


		/*if(!ObjectUtils.isNull(staff.getSuperior())) {
			BasisStaff basisStaff = new BasisStaff();
			basisStaff.setIsDeleted(false);
			basisStaff.setIsDisabled(false);
			basisStaff.setStaffCode(staff.getSuperior());
			if(basisStaffMapper.retrieve((T) basisStaff).size()!=1) throw new Exception("上级领导不存在");
		}*/


		//唯一校验
		if(ObjectUtils.isNull(staff.getStaffCode()))throw new Exception(LBASEConstant.EBASE000082);
		
		BasisStaff checkStaff=new BasisStaff();
		checkStaff.setIsDeleted(false);
		checkStaff.setStaffCode(staff.getStaffCode());
		if(basisStaffMapper.retrieve((T) checkStaff).size() > 0) throw new Exception(LBASEConstant.EBASE000083);
		
		
		if(!ObjectUtils.isNull(staff.getAssociatedUser())) {
			//校验绑定员工
			BasisStaff checkStaffUser=new BasisStaff();
			checkStaffUser.setIsDeleted(false);
			checkStaffUser.setAssociatedUser(staff.getAssociatedUser());
			if(basisStaffMapper.retrieve((T) checkStaffUser).size() > 0) throw new Exception(LBASEConstant.EBASE000084);
		}
		
		if(!ObjectUtils.isNull(staff.getOrgId())) {
			//新增组织机构员工关系表
			BasisOrgStaff orgStaff=new BasisOrgStaff();
			//创建人
			orgStaff.setCreateBy(session.getAttribute("lmisUserId").toString());
			//更新人
			orgStaff.setUpdateBy(session.getAttribute("lmisUserId").toString());
			//所属机构
			orgStaff.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
			
			orgStaff.setStaffCode(staff.getStaffCode());
			orgStaff.setOrgId(staff.getOrgId());
			basisOrgStaffMapper.create(orgStaff);
		}
		
		//创建人
		dynamicSqlParam.setCreateBy(session.getAttribute("lmisUserId").toString());
		//更新人
		dynamicSqlParam.setUpdateBy(session.getAttribute("lmisUserId").toString());
		//所属机构
		dynamicSqlParam.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
		// 插入操作
		return dynamicSqlService.executeInsert(dynamicSqlParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		String orgId="";
		// TODO(业务校验)
		
		//存在校验
		BasisStaff staff = dynamicSqlService.generateTableModel((DynamicSqlParam<BasisStaff>) dynamicSqlParam, new BasisStaff()).getTableModel();
		BasisStaff checkStaff=new BasisStaff();
		checkStaff.setIsDeleted(false);
		checkStaff.setStaffCode(staff.getStaffCode());
		List<T> staffList = basisStaffMapper.retrieve((T) checkStaff);
		if(staffList.size()==0) throw new Exception(LBASEConstant.EBASE000085);
		if(!ObjectUtils.isNull(staff.getAssociatedUser())) {
			//校验绑定员工
			BasisStaff checkStaffUser=new BasisStaff();
			checkStaffUser.setIsDeleted(false);
			checkStaffUser.setAssociatedUser(staff.getAssociatedUser());
			checkStaffUser.setId(staff.getId());
			if(basisStaffMapper.checkStaffUser((T) checkStaffUser).size() > 0) throw new Exception(LBASEConstant.EBASE000084);
		}
		
		BasisOrgStaff checkOrgStaff=new BasisOrgStaff();
		checkOrgStaff.setIsDeleted(false);
		checkOrgStaff.setStaffCode(staff.getStaffCode());
		List<BasisOrgStaff> list = basisOrgStaffMapper.retrieve(checkOrgStaff);
		if(list.size()>0) {
			orgId=list.get(0).getOrgId();
			if(!orgId.equals(staff.getOrgId())) {
				basisOrgStaffMapper.logicalDelete(list.get(0));
			}
		}
		if(!ObjectUtils.isNull(staff.getOrgId())&&(list.size()==0||!orgId.equals(staff.getOrgId()))) {
			//新增组织机构员工关系表
			BasisOrgStaff orgStaff=new BasisOrgStaff();
			//创建人
			orgStaff.setCreateBy(session.getAttribute("lmisUserId").toString());
			//更新人
			orgStaff.setUpdateBy(session.getAttribute("lmisUserId").toString());
			//所属机构
			orgStaff.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
			
			orgStaff.setStaffCode(staff.getStaffCode());
			orgStaff.setOrgId(staff.getOrgId());
			basisOrgStaffMapper.create(orgStaff);
		}
		
		//更新人
		dynamicSqlParam.setUpdateBy(session.getAttribute("lmisUserId").toString());
		
		// 更新操作
		return dynamicSqlService.executeUpdate(dynamicSqlParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> deleteBasisStaff(T t) throws Exception {
			
		// TODO(业务校验)
		//存在校验
		BasisStaff checkStaff=new BasisStaff();
		checkStaff.setIsDeleted(false);
		checkStaff.setStaffCode(t.getStaffCode());
		if(basisStaffMapper.retrieve((T) checkStaff).size()==0) throw new Exception(LBASEConstant.EBASE000085);
		
		//更新人
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		
		// 删除操作
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, basisStaffMapper.logicalDelete(t));
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> switchBasisStaff(T t) throws Exception {
		// TODO Auto-generated method stub
		//存在校验
		BasisStaff checkStaff=new BasisStaff();
		checkStaff.setIsDeleted(false);
		checkStaff.setStaffCode(t.getStaffCode());
		if(basisStaffMapper.retrieve((T) checkStaff).size()==0) throw new Exception(LBASEConstant.EBASE000085);
		
		//更新人
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, basisStaffMapper.shiftValidity(t));
	}

	@Override
	public LmisResult<?> filterBasisStaff(DynamicSqlParam<BasisStaff> dynamicSqlParam,LmisPageObject pageObject) throws Exception {
		
		/*LmisResult<ViewBasisStaff> lmisResult = new LmisResult<ViewBasisStaff>();
		
		BasisOrgStaff orgStaff=new BasisOrgStaff();
		orgStaff.setIsDeleted(false);
		List<BasisOrgStaff> orgStaffList = basisOrgStaffMapper.retrieve(orgStaff);
		basisStaffMapper.selectStaffNotInStaffCodes(orgStaffList);
		
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		lmisResult.setData(staffList);
		return lmisResult;*/
		
		BasisStaff staff = dynamicSqlService.generateTableModel(dynamicSqlParam, new BasisStaff()).getTableModel();
		BasisOrgStaff orgStaff=new BasisOrgStaff();
		orgStaff.setIsDeleted(false);
		List<BasisOrgStaff> orgStaffList = basisOrgStaffMapper.retrieve(orgStaff);
		Map<String,Object> map=new HashMap<>();
		map.put("list", orgStaffList);
		if(!ObjectUtils.isNull(staff)) {
			if(!ObjectUtils.isNull(staff.getStaffCode())) map.put("staffCode", staff.getStaffCode());
			if(!ObjectUtils.isNull(staff.getStaffName())) map.put("staffName", staff.getStaffName());
		}
		Page<Map<String, Object>> page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
		basisStaffMapper.selectStaffNotInStaffCodes(map);
		LmisResult<Map<String, Object>> lmisResult = new LmisResult<Map<String, Object>>();
		lmisResult.setMetaAndData(page.toPageInfo());
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
		
		/*LmisResult<Map<String, Object>> lmisResult = new LmisResult<Map<String, Object>>();
		String executeSql = getDynamicSql(dynamicSqlParam);
		if(!ObjectUtils.isNull(executeSql)){
			Page<Map<String, Object>> page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
			this.dynamicSqlMapper.executeSelect(executeSql);
			lmisResult.setMetaAndData(page.toPageInfo());
			lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		}else {
			lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
			lmisResult.setMessage("数据异常");
		}
		return lmisResult;*/
		
		
	}
	
	/*private String getDynamicSql(DynamicSqlParam<T> dynamicSqlParam) throws Exception{
		StringBuffer sql = new StringBuffer("");
		LmisResult<?> result3 = dynamicSqlService.getColumnsAndOrderby(dynamicSqlParam);
		if (!"E3001".equals(result3.getCode())) {
			JSONObject data1 = (JSONObject)result3.getData();
			sql.append(data1.get("selectSql"));
			LmisResult<?> result2 = dynamicSqlService.getWhereSql(dynamicSqlParam);
			if(!"E3001".equals(result2.getCode())){
				sql.append(result2.getData().toString());
				sql.append(" AND is_disabled = FALSE");
			}
			
			if(!ObjectUtils.isNull(dynamicSqlParam) && !ObjectUtils.isNull(dynamicSqlParam.getId())){
				sql.append(" AND staff_code NOT IN ( select staff_code from view_basis_org_staff where is_deleted = FALSE AND staff_code = '"  + dynamicSqlParam.getId()).append("') ");
			}
			sql.append(data1.get("orderbySql"));
		}
		return sql.toString();
	}*/
	

}
