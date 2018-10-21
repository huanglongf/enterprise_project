package com.lmis.sys.org.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import com.lmis.basis.staff.dao.BasisStaffMapper;
import com.lmis.basis.staff.model.BasisStaff;
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
import com.lmis.sys.org.dao.SysOrgMapper;
import com.lmis.sys.org.model.SysOrg;
import com.lmis.sys.org.model.ViewSysOrg;
import com.lmis.sys.org.service.SysOrgServiceInterface;
import com.lmis.sys.userRoleOrg.dao.SysUserRoleOrgMapper;
import com.lmis.sys.userRoleOrg.model.SysUserRoleOrg;

/** 
 * @ClassName: SysOrgServiceImpl
 * @Description: TODO(业务层实现类)
 * @author codeGenerator
 * @date 2018-01-09 13:08:38
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class SysOrgServiceImpl<T extends SysOrg> implements SysOrgServiceInterface<T>{

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(SysOrgServiceImpl.class);
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<SysOrg> dynamicSqlService;
	@Autowired
	private SysOrgMapper<T> sysOrgMapper;
	
	@Autowired
	private SysUserRoleOrgMapper<SysUserRoleOrg> sysUserRoleOrgMapper;

	@Autowired
	private BasisStaffMapper<BasisStaff> basisStaffMapper;
	
	@Autowired
	private HttpSession session;
	
	@Override
	public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception {
		dynamicSqlParam.setIsDeleted(false);
		return dynamicSqlService.executeSelect(dynamicSqlParam, pageObject);
	}

	@Override
	public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception{
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

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception{
		//唯一校验
		SysOrg org = dynamicSqlService.generateTableModel((DynamicSqlParam<SysOrg>) dynamicSqlParam, new SysOrg()).getTableModel();
		//
		if(ObjectUtils.isNull(org.getOrgId())) throw new Exception(LBASEConstant.EBASE000107);
		//导入校验
		SysOrg sysOrg = new SysOrg();
		sysOrg.setIsDeleted(false);
		sysOrg.setIsDisabled(false);
		if(ObjectUtils.isNull(org.getPid())) throw new Exception(LBASEConstant.EBASE000108);
		sysOrg.setOrgId(org.getPid());
		if (!org.getPid().equals("0")&&sysOrgMapper.retrieve((T) sysOrg).size()!=1) throw new Exception(LBASEConstant.EBASE000109);
		
		if(!ObjectUtils.isNull(org.getOrgBy())) {
			BasisStaff basisStaff = new BasisStaff();
			basisStaff.setIsDeleted(false);
			basisStaff.setIsDisabled(false);
			basisStaff.setStaffCode(org.getOrgBy());
			if(basisStaffMapper.retrieve( basisStaff).size()!=1) throw new Exception(LBASEConstant.EBASE000110);
		}

		SysOrg checkOrg=new SysOrg();
		checkOrg.setIsDeleted(false);
		checkOrg.setOrgId(org.getOrgId());
		if(sysOrgMapper.retrieve((T) checkOrg).size()>0) throw new Exception(LBASEConstant.EBASE000111);

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
	public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception{
		// TODO(业务校验)
		
		// 更新操作
		return dynamicSqlService.executeUpdate(dynamicSqlParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> deleteSysOrg(T t) throws Exception{
		LmisResult<T> lmisResult = new LmisResult<T>();
			
		// TODO(业务校验)
		//无子节点才可删除
		SysOrg sysOrg = new SysOrg();
		sysOrg.setIsDeleted(false);
		sysOrg.setPid(t.getOrgId());
		List<T> list = sysOrgMapper.retrieve((T) sysOrg);
		if(list.size()>0)throw new Exception(LBASEConstant.EBASE000023);
		
		//逻辑删除用户角色机构关系表
		SysUserRoleOrg userRoleOrg = new SysUserRoleOrg();
		userRoleOrg.setOrgId(t.getOrgId());
		List<SysUserRoleOrg> deleteUserRoleOrg = sysUserRoleOrgMapper.retrieve(userRoleOrg);
		for (SysUserRoleOrg sysUserRoleOrg : deleteUserRoleOrg) {
			sysUserRoleOrgMapper.logicalDelete(sysUserRoleOrg);
		}
		//更新人
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		// 删除操作
		if(sysOrgMapper.logicalDelete(t) == 1) lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysOrg> findOrg(SysOrg sysOrg) throws Exception{
		List<SysOrg>  orgList=new ArrayList<>();
		if(ObjectUtils.isNull(sysOrg.getOrgId())&&ObjectUtils.isNull(sysOrg.getOrgBy())&&ObjectUtils.isNull(sysOrg.getOrgName())){
			sysOrg.setPid("0");
			sysOrg.setIsDeleted(false);
			List<SysOrg> org = (List<SysOrg>)sysOrgMapper.retrieve((T) sysOrg);
			for (SysOrg sysorg : org) {
				orgList.add(OrgRecursive(sysorg));
			}
		}else {
			List<SysOrg> org = (List<SysOrg>)sysOrgMapper.retrieve((T) sysOrg);
			if(org.size()>0) {
				SysOrg t = org.get(0);
				orgList.add(OrgRecursive(t));
			}
		}
		return orgList;
	}

	@SuppressWarnings("unchecked")
	private SysOrg OrgRecursive(SysOrg sysOrg) { 
		List<SysOrg> childList=new ArrayList<SysOrg>();
		
		//查询所有子结构
		SysOrg paramOrg = new SysOrg();
		paramOrg.setIsDeleted(false);
		paramOrg.setPid(sysOrg.getOrgId());
		List<SysOrg> sysOrgList = (List<SysOrg>)sysOrgMapper.retrieve((T) paramOrg);
		
		//遍历子结构继续查询做递归
		if(sysOrgList.size()>0) {
			for (SysOrg child : sysOrgList) {
				SysOrg org = OrgRecursive(child);
				childList.add(org);
			}
			sysOrg.setChildList(childList);
		}
		return sysOrg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> addOrUpdateSysOrg(SysOrg org) throws Exception{
			LmisResult<T> lmisResult = new LmisResult<T>();
			String id = org.getId();
			
		if(ObjectUtils.isNull(org.getOrgId())) throw new Exception(LBASEConstant.EBASE000107);
		//导入校验
		SysOrg sysOrg = new SysOrg();
		sysOrg.setIsDeleted(false);
		sysOrg.setIsDisabled(false);
		if(ObjectUtils.isNull(org.getPid())) throw new Exception(LBASEConstant.EBASE000108);
		sysOrg.setOrgId(org.getPid());
		if (!org.getPid().equals("0")&&sysOrgMapper.retrieve((T) sysOrg).size()!=1) throw new Exception(LBASEConstant.EBASE000109);

			//id为空则新增，不为空则修改
			if(ObjectUtils.isNull(id)) {
				// 唯一校验
				SysOrg param = new SysOrg();
				param.setIsDeleted(false);
				param.setOrgId(org.getOrgId());
				if(!ObjectUtils.isNull(sysOrgMapper.retrieve((T) param)))throw new Exception(LBASEConstant.EBASE000111);
				
				//创建人
				org.setCreateBy(session.getAttribute("lmisUserId").toString());
				//更新人
				org.setUpdateBy(session.getAttribute("lmisUserId").toString());
				//所属机构
				org.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
				
				if(sysOrgMapper.create((T) org)==1) lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
				// 插入操作
			}else {
				// TODO(业务校验)
				//更新人
				org.setUpdateBy(session.getAttribute("lmisUserId").toString());
				// 更新操作
				if(sysOrgMapper.updateRecord((T) org)==1) lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
			}
			return lmisResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> switchSysOrg(T t) throws Exception{
		SysOrg checkOrg=new SysOrg();
		checkOrg.setIsDeleted(false);
		checkOrg.setOrgId(t.getOrgId());
		if(sysOrgMapper.retrieve((T) checkOrg).size()==0) throw new Exception(LBASEConstant.EBASE000112);
		
		if(t.getIsDisabled()) {
			SysOrg org=new SysOrg();
			org.setIsDeleted(false);
			org.setPid(t.getOrgId());
			org.setIsDisabled(false);
			if(sysOrgMapper.retrieve((T) org).size()>0) throw new Exception(LBASEConstant.EBASE000024);
		}else {
			SysOrg org=new SysOrg();
			org.setIsDeleted(false);
			org.setOrgId(t.getPid());
			org.setIsDisabled(true);
			if(sysOrgMapper.retrieve((T) org).size()>0) throw new Exception(LBASEConstant.EBASE000025);
		}
		
		//更新人
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, sysOrgMapper.shiftValidity(t));
	}

	@Override
	public List<ViewSysOrg> findOrg(ViewSysOrg sysOrg) throws Exception {
		List<ViewSysOrg>  orgList=new ArrayList<>();
		if(ObjectUtils.isNull(sysOrg.getOrgId())&&ObjectUtils.isNull(sysOrg.getOrgBy())
				&&ObjectUtils.isNull(sysOrg.getOrgName())&&ObjectUtils.isNull(sysOrg.getPid())){
			//sysOrg.setPid("0");
			sysOrg.setIsDeleted(false);
			List<ViewSysOrg> org = sysOrgMapper.retrieveViewSysOrg(sysOrg);
			orgList = buildByRecursiveView(org);
			/*for (ViewSysOrg sysorg : org) {
				orgList.add(ViewOrgRecursive(sysorg));
			}*/
		}else {
			ViewSysOrg org1=new ViewSysOrg();
			org1.setIsDeleted(false);
			List<ViewSysOrg> retrieveViewSysOrg = sysOrgMapper.retrieveViewSysOrg(org1);
			List<ViewSysOrg> org = sysOrgMapper.retrieveViewSysOrg(sysOrg);
			for (ViewSysOrg viewSysOrg : org) {
				ViewSysOrg _sysOrg = findChildren(viewSysOrg, retrieveViewSysOrg);
				orgList.add(_sysOrg);
			}
		}
		return orgList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SysOrg> findSubordinateSysOrg(T t) throws Exception {
		List<SysOrg> orgList=new ArrayList<>();
		t.setIsDeleted(false);
		t.setIsDisabled(false);
		List<T> orgs = sysOrgMapper.retrieve(t);
		if(orgs.size()==0) {
			throw new Exception(LBASEConstant.EBASE000113);
		}else {
			T org = orgs.get(0);
			SysOrg paramOrg=new SysOrg();
			paramOrg.setIsDeleted(false);
			paramOrg.setIsDisabled(false);
			paramOrg.setPid(org.getOrgId());
			
			List<SysOrg> sysOrgList = (List<SysOrg>) sysOrgMapper.retrieve((T) paramOrg);
			org.setChildList(sysOrgList);
			orgList.add(org);
		}
		return orgList;
	}

	@Override
	public LmisResult<List<ViewSysOrg>> exportOrg(int pageSize) {
		
		//返回参数
		LmisResult<List<ViewSysOrg>> lmisResult=new LmisResult<>();
		List<ViewSysOrg> exportList=new ArrayList<>();
		ViewSysOrg org=new ViewSysOrg();
		org.setIsDeleted(false);
		org.setIsDisabled(false);
		org.setPid("0");
		List<ViewSysOrg> orgList = sysOrgMapper.retrieveViewSysOrg( org);
		for (ViewSysOrg sysOrg : orgList) {
			exportList.add(sysOrg);
			if(exportList.size()==pageSize) break;
			exportRecursive(sysOrg,exportList,pageSize);
		}
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		lmisResult.setData(exportList);
		
		return lmisResult;
	}

	private void exportRecursive(ViewSysOrg viewSysOrg, List<ViewSysOrg> exportList,int pageSize) {
		//查询所有子结构
		ViewSysOrg paramOrg = new ViewSysOrg();
		paramOrg.setIsDeleted(false);
		paramOrg.setPid(viewSysOrg.getOrgId());
		List<ViewSysOrg> sysOrgList = sysOrgMapper.retrieveViewSysOrg( paramOrg);
		
		//遍历子结构继续查询做递归
		if(sysOrgList.size()>0) {
			for (ViewSysOrg child : sysOrgList) {
				if(exportList.size()==pageSize) return;
				exportList.add(child);
				exportRecursive(child,exportList,pageSize);
			}
		}
	}
	
	/** 
     * 使用递归方法建树 
     * @param treeNodes 
     * @return 
     */  
    public static List<ViewSysOrg> buildByRecursiveView(List<ViewSysOrg> treeNodes) {  
        List<ViewSysOrg> trees = new ArrayList<ViewSysOrg>();  
        for (ViewSysOrg treeNode : treeNodes) {  
            if ("0".equals(treeNode.getPid())) {  
                trees.add(findChildren(treeNode,treeNodes));  
            }  
        }  
        return trees;  
    }
    /** 
     * 递归查找子节点 
     * @param treeNodes 
     * @return 
     */  
    public static ViewSysOrg findChildren(ViewSysOrg treeNode,List<ViewSysOrg> treeNodes) {  
        for (ViewSysOrg it : treeNodes) {  
            if(treeNode.getOrgId().equals(it.getPid())) {  
                if (treeNode.getChildList() == null) {  
                    treeNode.setChildList(new ArrayList<ViewSysOrg>());  
                }  
                treeNode.getChildList().add(findChildren(it,treeNodes));  
            }  
        }  
        return treeNode;  
    }

	@Override
	public LmisResult<?> getSysOrg(T t) throws Exception {
		if(ObjectUtils.isNull(t.getOrgId())) throw new Exception(LBASEConstant.EBASE000066);
		t.setIsDeleted(false);
		t.setIsDisabled(false);
		List<T> sysOrgList = sysOrgMapper.retrieve(t);
		if(sysOrgList.size()!=1) throw new Exception(LBASEConstant.EBASE000113);
		return new LmisResult<>(LmisConstant.RESULT_CODE_SUCCESS, sysOrgList.get(0));
	}
	
}
