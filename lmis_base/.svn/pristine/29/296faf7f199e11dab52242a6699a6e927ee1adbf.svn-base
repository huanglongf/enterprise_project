package com.lmis.sys.user.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.basis.staff.dao.BasisStaffMapper;
import com.lmis.basis.staff.model.BasisStaff;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.common.util.Base64Utils;
import com.lmis.constant.LBASEConstant;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.sys.user.dao.SysUserMapper;
import com.lmis.sys.user.model.SysUser;
import com.lmis.sys.user.service.SysUserServiceInterface;
import com.lmis.sys.userRole.dao.SysUserRoleMapper;
import com.lmis.sys.userRole.model.SysUserRole;
import com.lmis.sys.userRoleOrg.dao.SysUserRoleOrgMapper;
import com.lmis.sys.userRoleOrg.model.SysUserRoleOrg;

/** 
 * @ClassName: SysUserServiceImpl
 * @Description: TODO(业务层实现类)
 * @author codeGenerator
 * @date 2018-01-09 12:51:12
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class SysUserServiceImpl<T extends SysUser> implements SysUserServiceInterface<T>{

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(SysUserServiceImpl.class);
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<Map<String, Object>> dynamicSqlService;
	@Autowired
	private SysUserMapper<T> sysUserMapper;
	@Autowired
	private SysUserRoleMapper<SysUserRole> sysUserRoleMapper;
	@Autowired
	private SysUserRoleOrgMapper<SysUserRoleOrg> sysUserRoleOrgMapper;

	@Autowired
	private BasisStaffMapper<BasisStaff> basisStaffMapper;
	
	@Autowired
	private HttpSession session;
	@Override
	public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception {
	    //需要数据权限引入
	    //SqlCombineHelper.startSqlCombine(true);
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
	public LmisResult<?> executeInsert(T t) throws Exception {
		LmisResult<?> lmisResult = new LmisResult<T>();
		//非空校验
		if(ObjectUtils.isNull(t.getUserCode())) throw new Exception(LBASEConstant.EBASE000116);
		
		// 唯一校验
		SysUser param = new SysUser();
		param.setIsDeleted(false);
		param.setUserCode(t.getUserCode());
		if(!ObjectUtils.isNull(sysUserMapper.retrieve((T) param)))throw new Exception(LBASEConstant.EBASE000117);
		// 插入操作
		//创建人
		t.setCreateBy(session.getAttribute("lmisUserId").toString());
		//更新人
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		//所属机构
		t.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
		// 插入操作
		if(sysUserMapper.create(t) == 1) lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

	@Override
	public LmisResult<?> executeUpdate(T t) throws Exception {
		LmisResult<?> lmisResult = new LmisResult<T>();
		// TODO(业务校验)
		// 更新操作
		//更新人
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		if(sysUserMapper.update(t) == 1) lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

	@Override
	public LmisResult<?> deleteSysUser(T t) throws Exception {
		LmisResult<T> lmisResult = new LmisResult<T>();
		if(ObjectUtils.isNull(t.getUserCode())) throw new Exception(LBASEConstant.EBASE000066);
		//逻辑删除用户角色关系表
		SysUserRole userRole = new SysUserRole();
		userRole.setUserId(t.getUserCode());
		userRole.setIsDeleted(false);
		// TODO(业务校验)
		List<SysUserRole> deleteUserRole = sysUserRoleMapper.retrieve(userRole);
		for (SysUserRole sysUserRole : deleteUserRole) {
			sysUserRole.setUpdateBy(session.getAttribute("lmisUserId").toString());
			sysUserRoleMapper.logicalDelete(sysUserRole);
		}
		//逻辑删除用户角色机构关系表
		SysUserRoleOrg userRoleOrg = new SysUserRoleOrg();
		userRoleOrg.setUserId(t.getUserCode());
		userRoleOrg.setIsDeleted(false);
		
		// TODO(业务校验)
		List<SysUserRoleOrg> deleteUserRoleOrg = sysUserRoleOrgMapper.retrieve(userRoleOrg);
		for (SysUserRoleOrg sysUserRoleOrg : deleteUserRoleOrg) {
			sysUserRoleOrg.setUpdateBy(session.getAttribute("lmisUserId").toString());
			sysUserRoleOrgMapper.logicalDelete(sysUserRoleOrg);
		}
		//清空与之关联的basis_staff表中的associatedUser字段的值
		BasisStaff basisStaff = new BasisStaff();
		basisStaff.setAssociatedUser(t.getUserCode());
        List<BasisStaff> retrieveList = basisStaffMapper.retrieve(basisStaff);
        if(retrieveList.size()>0){
            BasisStaff basisStaffUpdate =  retrieveList.get(0);
            basisStaffUpdate.setAssociatedUser(null);
            basisStaffUpdate.setUpdateBy(session.getAttribute("lmisUserId").toString());
            basisStaffMapper.updateRecord(basisStaffUpdate);
        }

		//更新人
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		// 删除操作
		if(sysUserMapper.logicalDelete(t) == 1) lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> updatePwd(T t) throws Exception {
		
		LmisResult<T> lmisResult = new LmisResult<T>();
		
		//解密旧密码，并校验与数据库是否对应
		String oldPwd = Base64Utils.decoderBase64(t.getOldUserPwd());
		SysUser param=new SysUser();
		param.setUserCode(t.getUserCode());
		param.setIsDeleted(false);
		param.setUserPwd(oldPwd);
		
		List<T> list = sysUserMapper.retrieve((T) param);
		if(list.size()==0) throw new Exception(LBASEConstant.EBASE000118);
		
		//解密新密码
		String newPwd = Base64Utils.decoderBase64(t.getUserPwd());
		t.setUserPwd(newPwd);
		t.setVersion(list.get(0).getVersion());
		//更新人
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		if(sysUserMapper.update(t) == 1) lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		lmisResult.setMessage("密码修改成功");
		return lmisResult;
	}

}
