package com.lmis.sys.userRoleOrg.service.impl;

import java.util.ArrayList;
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
import com.lmis.sys.userRoleOrg.dao.SysUserRoleOrgMapper;
import com.lmis.sys.userRoleOrg.model.SysUserRoleOrg;
import com.lmis.sys.userRoleOrg.service.SysUserRoleOrgServiceInterface;

/** 
 * @ClassName: SysUserRoleOrgServiceImpl
 * @Description: TODO(业务层实现类)
 * @author codeGenerator
 * @date 2018-01-09 17:10:23
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class SysUserRoleOrgServiceImpl<T extends SysUserRoleOrg> implements SysUserRoleOrgServiceInterface<T>{

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(SysUserRoleOrgServiceImpl.class);
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<Map<String, Object>> dynamicSqlService;
	@Autowired
	private SysUserRoleOrgMapper<T> sysUserRoleOrgMapper;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception {
		dynamicSqlParam.setIsDeleted(false);
		return dynamicSqlService.executeSelect(dynamicSqlParam, pageObject);
	}

	@Override
	public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		LmisResult<?> lmisResult = new LmisResult<T>();
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

	@Override
	public LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		// TODO(业务校验)
		
		// 插入操作
		return dynamicSqlService.executeInsert(dynamicSqlParam);
	}

	@Override
	public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		// TODO(业务校验)
		
		// 更新操作
		return dynamicSqlService.executeUpdate(dynamicSqlParam);
	}

	@Override
	public LmisResult<?> deleteSysUserRoleOrg(T t) throws Exception {
		LmisResult<T> lmisResult = new LmisResult<T>();
		// TODO(业务校验)
		
		// 删除操作
		if(sysUserRoleOrgMapper.logicalDelete(t) == 1) lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> addUserRoleOrg(List<SysUserRoleOrg> userRoleOrgList)  throws Exception {
		//返回参数
		LmisResult<T> lmisResult = new LmisResult<T>();
		//插入LISt
		List<SysUserRoleOrg> insertList=new ArrayList<>();
		
		SysUserRoleOrg param=new SysUserRoleOrg();
		for (SysUserRoleOrg sysUserRoleOrg : userRoleOrgList) {
			param.setOrgId(sysUserRoleOrg.getOrgId());
			param.setUserId(sysUserRoleOrg.getUserId());
			param.setIsDeleted(false);
			List<T> list = sysUserRoleOrgMapper.retrieve((T)param);
			if(list.size()==0) {
				
				SysUserRoleOrg userRoleOrg =new SysUserRoleOrg();
				
				//创建人
				userRoleOrg.setCreateBy(session.getAttribute("lmisUserId").toString());
				//更新人
				userRoleOrg.setUpdateBy(session.getAttribute("lmisUserId").toString());
				//所属机构
				userRoleOrg.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
				
				//机构ID
				userRoleOrg.setOrgId(sysUserRoleOrg.getOrgId());
				//用户ID
				userRoleOrg.setUserId(sysUserRoleOrg.getUserId());
				insertList.add(userRoleOrg);
			}
		}
		if(insertList.size()>0) {
			sysUserRoleOrgMapper.createBatch( (List<T>) insertList);
		}
		
		//查询要逻辑删除的数据
		List<SysUserRoleOrg> updateList=sysUserRoleOrgMapper.getUserRoleOrgByOrgId(userRoleOrgList);
		for (SysUserRoleOrg sysUserRoleOrg : updateList) {
			sysUserRoleOrgMapper.logicalDelete((T) sysUserRoleOrg);
		}
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> selectUserRoleOrg(SysUserRoleOrg sysUserRoleOrg) {
		// TODO Auto-generated method stub
		sysUserRoleOrg.setIsDeleted(false);
		return sysUserRoleOrgMapper.retrieve((T) sysUserRoleOrg);
	}


}
