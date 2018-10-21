package com.lmis.sys.role.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.constant.LBASEConstant;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.sys.role.dao.SysRoleMapper;
import com.lmis.sys.role.model.SysRole;
import com.lmis.sys.role.service.SysRoleServiceInterface;
import com.lmis.sys.roleFunctionButton.dao.SysRoleFunctionButtonMapper;
import com.lmis.sys.roleFunctionButton.model.SysRoleFunctionButton;
import com.lmis.sys.userRole.dao.SysUserRoleMapper;
import com.lmis.sys.userRole.model.SysUserRole;
import com.lmis.sys.userRoleOrg.dao.SysUserRoleOrgMapper;
import com.lmis.sys.userRoleOrg.model.SysUserRoleOrg;

/** 
 * @ClassName: SysRoleServiceImpl
 * @Description: TODO(业务层实现类)
 * @author codeGenerator
 * @date 2018-01-09 13:08:09
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class SysRoleServiceImpl<T extends SysRole> implements SysRoleServiceInterface<T>{

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(SysRoleServiceImpl.class);
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<SysRole> dynamicSqlService;
	@Autowired
	private SysRoleMapper<T> sysRoleMapper;
	@Autowired
	private SysRoleFunctionButtonMapper<SysRoleFunctionButton> sysRoleFunctionButtonMapper;
	@Autowired
	private SysUserRoleMapper<SysUserRole> sysUserRoleMapper;
	@Autowired
	private SysUserRoleOrgMapper<SysUserRoleOrg> sysUserRoleOrgMapper;
	
	@Autowired
	private HttpSession session;
	

	@Override
	public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception {
		return dynamicSqlService.executeSelect(dynamicSqlParam, pageObject);
	}

	@Override
	public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		LmisResult<?> lmisResult = new LmisResult<T>();
		dynamicSqlParam.setIsDeleted(false);
		LmisResult<?> _lmisResult = dynamicSqlService.executeSelect(dynamicSqlParam);
		if(LmisConstant.RESULT_CODE_ERROR.equals(_lmisResult.getCode())) {
			return _lmisResult;
		}
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> result =  (List<Map<String, Object>>) _lmisResult.getData();
		if(ObjectUtils.isNull(result)) throw new Exception(LBASEConstant.EBASE000007);
		if(result.size() != 1) throw new Exception(LBASEConstant.EBASE000008);
		lmisResult.setData(result.get(0));
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
			
		
			//唯一校验
			SysRole role = dynamicSqlService.generateTableModel((DynamicSqlParam<SysRole>) dynamicSqlParam, new SysRole()).getTableModel();
			
			if(ObjectUtils.isNull(role.getRoleId())) throw new Exception(LBASEConstant.EBASE000114);
			SysRole checkRole=new SysRole();
			checkRole.setIsDeleted(false);
			checkRole.setRoleId(role.getRoleId());
			if(sysRoleMapper.retrieve((T) checkRole).size()>0) throw new Exception(LBASEConstant.EBASE000115);
		
			// TODO(业务校验)
			//创建人
			dynamicSqlParam.setCreateBy(session.getAttribute("lmisUserId").toString());
			//更新人
			dynamicSqlParam.setUpdateBy(session.getAttribute("lmisUserId").toString());
			//所属机构
			dynamicSqlParam.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
			// 插入操作
			return dynamicSqlService.executeInsert(dynamicSqlParam);
	}

	@Override
	public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
			// TODO(业务校验)
			//更新人
			dynamicSqlParam.setUpdateBy(session.getAttribute("lmisUserId").toString());
			// 更新操作
			return dynamicSqlService.executeUpdate(dynamicSqlParam);
	}

	@Override
	public LmisResult<?> deleteSysRole(T t) throws Exception {
		LmisResult<T> lmisResult = new LmisResult<T>();
		//逻辑删除用户角色关系表
		if(ObjectUtils.isNull(t.getRoleId())) throw new Exception(LBASEConstant.EBASE000066);
		SysUserRole userRole = new SysUserRole();
		userRole.setIsDeleted(false);
		userRole.setRoleId(t.getRoleId());
		List<SysUserRole> deleteUserRole = sysUserRoleMapper.retrieve(userRole);
		for (SysUserRole sysUserRole : deleteUserRole) {
			sysUserRole.setUpdateBy(session.getAttribute("lmisUserId").toString());
			sysUserRoleMapper.logicalDelete(sysUserRole);
		}
		//逻辑删除角色菜单关系表
		SysRoleFunctionButton roleFb = new SysRoleFunctionButton();
		roleFb.setRoleId(t.getRoleId());
		roleFb.setIsDeleted(false);
		List<SysRoleFunctionButton> deleteRoleFb = sysRoleFunctionButtonMapper.retrieve(roleFb);
		for (SysRoleFunctionButton sysRoleFb : deleteRoleFb) {
			sysRoleFb.setUpdateBy(session.getAttribute("lmisUserId").toString());
			sysRoleFunctionButtonMapper.logicalDelete(sysRoleFb);
		}
		
		//逻辑删除用户角色机构关系表
		SysUserRoleOrg userRoleOrg = new SysUserRoleOrg();
		userRoleOrg.setRoleId(t.getRoleId());
		userRoleOrg.setIsDeleted(false);
		List<SysUserRoleOrg> deleteUserRoleOrg = sysUserRoleOrgMapper.retrieve(userRoleOrg);
		for (SysUserRoleOrg sysUserRoleOrg : deleteUserRoleOrg) {
			sysUserRoleOrg.setUpdateBy(session.getAttribute("lmisUserId").toString());
			sysUserRoleOrgMapper.logicalDelete(sysUserRoleOrg);
		}
		//更新人
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		// 删除操作
		if(sysRoleMapper.logicalDelete(t) == 1) lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findRole() throws Exception {
		SysRole sysRole = new SysRole();
		sysRole.setIsDeleted(false);
		return sysRoleMapper.retrieve((T) sysRole);
	}

}
