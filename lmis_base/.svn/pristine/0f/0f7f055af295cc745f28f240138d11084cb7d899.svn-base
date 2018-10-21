package com.lmis.sys.functionButton.service.impl;

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
import com.lmis.setup.pageElement.dao.SetupPageElementMapper;
import com.lmis.setup.pageElement.model.SetupPageElement;
import com.lmis.sys.functionButton.dao.SysFunctionButtonMapper;
import com.lmis.sys.functionButton.model.SysFunctionButton;
import com.lmis.sys.functionButton.service.SysFunctionButtonServiceInterface;

/** 
 * @ClassName: SysFunctionButtonServiceImpl
 * @Description: TODO(业务层实现类)
 * @author codeGenerator
 * @date 2018-01-09 13:09:08
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class SysFunctionButtonServiceImpl<T extends SysFunctionButton> implements SysFunctionButtonServiceInterface<T>{

	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(SysFunctionButtonServiceImpl.class);
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<Map<String, Object>> dynamicSqlService;
	@Autowired
	private SetupPageElementMapper<SetupPageElement> setupPageElementMapper;
	
	@Autowired
	private SysFunctionButtonMapper<T> sysFunctionButtonMapper;
	
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

	@Override
	public LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
			return dynamicSqlService.executeInsert(dynamicSqlParam);
	}

	@Override
	public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
			// TODO(业务校验)
			// 更新操作
			return dynamicSqlService.executeUpdate(dynamicSqlParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> deleteSysFunctionButton(T t) throws Exception  {
		
		
		List<SysFunctionButton> sysFbList = new ArrayList<>();
		
		SysFunctionButton fb =new SysFunctionButton();
		fb.setIsDeleted(false);
		fb.setIsDisabled(false);
		List<T> fbList = sysFunctionButtonMapper.retrieve((T) fb);
		fb.setId(t.getId());
		List<T> fbs = sysFunctionButtonMapper.retrieve((T) fb);
		if(fbs.size()!=1) throw new Exception(LBASEConstant.EBASE000104);
		findChildren(fbs.get(0), fbList,sysFbList);
		
		LmisResult<T> lmisResult = new LmisResult<T>();
		// TODO(业务校验)
		// 删除操作
		for (SysFunctionButton t2 : sysFbList) {
			t2.setUpdateBy(session.getAttribute("lmisUserId").toString());
			sysFunctionButtonMapper.logicalDelete((T) t2);
		}
		//更新人
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		if(sysFunctionButtonMapper.logicalDelete(t) == 1) lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		
		return lmisResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysFunctionButton>  findFunctionButton(SysFunctionButton sysFunctionButton) throws Exception  {
		
		List<SysFunctionButton>  fbList=new ArrayList<>();
		if(ObjectUtils.isNull(sysFunctionButton.getFbId())&&ObjectUtils.isNull(sysFunctionButton.getFbName())){
			sysFunctionButton.setFbPid("0");
			sysFunctionButton.setIsDeleted(false);
			List<SysFunctionButton> SysfbList = (List<SysFunctionButton>)sysFunctionButtonMapper.sortQuery((T) sysFunctionButton);
			for (SysFunctionButton fb : SysfbList) {
				fbList.add(FBRecursive(fb));
			}
		}else {
			List<SysFunctionButton> SysfbList = (List<SysFunctionButton>)sysFunctionButtonMapper.sortQuery((T) sysFunctionButton);
			if(SysfbList.size()>0) {
				SysFunctionButton t = SysfbList.get(0);
				fbList.add(FBRecursive(t));
			}
		}
		return fbList;
		
	}
	
	@SuppressWarnings("unchecked")
	private SysFunctionButton FBRecursive(SysFunctionButton sysFunctionButton) {
		List<SysFunctionButton> childList=new ArrayList<SysFunctionButton>();
		
		//查询所有子结构
		SysFunctionButton paramFB = new SysFunctionButton();
		paramFB.setIsDeleted(false);
		paramFB.setFbPid(sysFunctionButton.getFbId());
		List<SysFunctionButton> sysFBList = (List<SysFunctionButton>)sysFunctionButtonMapper.sortQuery((T) paramFB);
		
		//遍历子结构继续查询做递归
		if(sysFBList.size()>0) {
			for (SysFunctionButton child : sysFBList) {
				SysFunctionButton fB = FBRecursive(child);
				childList.add(fB);
			}
			sysFunctionButton.setChildList(childList);
		}
		return sysFunctionButton;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> addOrUpdateSysFunctionButton(SysFunctionButton functionButton)  throws Exception {
		
		String fbId = functionButton.getFbId();
		
		LmisResult<T> lmisResult = new LmisResult<T>();
		//查询页面元素参数
		String pageId = functionButton.getFbPage();
		
		List<SysFunctionButton> insetList=new ArrayList<>();
		
		String id = functionButton.getId();
		//id为空则新增，不为空则修改
		if(ObjectUtils.isNull(id)) {
			
			//创建人
			functionButton.setCreateBy(session.getAttribute("lmisUserId").toString());
			//更新人
			functionButton.setUpdateBy(session.getAttribute("lmisUserId").toString());
			//所属机构
			functionButton.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
			//类型
			functionButton.setFbType("fb_function");
			
			// 唯一校验
			SysFunctionButton param = new SysFunctionButton();
			param.setIsDeleted(false);
			param.setFbId(fbId);
			if(!ObjectUtils.isNull(sysFunctionButtonMapper.retrieve((T) param)))throw new Exception(LBASEConstant.EBASE000105);
			insetList.add(functionButton);
			//判断pageId是否为空，不为空则添加按钮
			if(!ObjectUtils.isNull(pageId)) {
				List<SetupPageElement> pageList=setupPageElementMapper.findPageElementByPageId(pageId);
				for (SetupPageElement pe : pageList) {
					SysFunctionButton fb=new SysFunctionButton();
					//创建人
					fb.setCreateBy(session.getAttribute("lmisUserId").toString());
					//更新人
					fb.setUpdateBy(session.getAttribute("lmisUserId").toString());
					//所属机构
					fb.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
					fb.setFbType("fb_button");
					fb.setFbId(fbId+pe.getElementId());
					fb.setFbPid(fbId);
					fb.setFbName(pe.getElementName());
					fb.setFbSeq(pe.getElementSeq());
					insetList.add(fb);
				}
			}
			// 插入操作
			sysFunctionButtonMapper.createBatch((List<T>) insetList);
		}else {
			//更新人
			functionButton.setUpdateBy(session.getAttribute("lmisUserId").toString());
			
			//判断如果pageId为空，则清空当前菜单下的所有按钮 如果不为空，则查看是否刷新按钮
			if(ObjectUtils.isNull(pageId)) {
				SysFunctionButton param = new SysFunctionButton();
				param.setIsDeleted(false);
				param.setFbPid(fbId);
				param.setFbType("fb_button");
				List<T> deleteList = sysFunctionButtonMapper.retrieve((T) param);
				for (T t : deleteList) {
					sysFunctionButtonMapper.logicalDelete(t);
				}
			}else {
				SysFunctionButton param = new SysFunctionButton();
				param.setIsDeleted(false);
				param.setId(id);
				param.setFbPage(pageId);
				List<T> list = sysFunctionButtonMapper.retrieve((T) param);
				//判断pageId修改了，则重新插入按钮
				if(list.size()==0) {
					SysFunctionButton deleteparam = new SysFunctionButton();
					deleteparam.setIsDeleted(false);
					deleteparam.setFbPid(fbId);
					deleteparam.setFbType("fb_button");
					List<T> deleteList = sysFunctionButtonMapper.retrieve((T) deleteparam);
					for (T t : deleteList) {
						sysFunctionButtonMapper.logicalDelete(t);
					}
					
					List<SetupPageElement> pageList=setupPageElementMapper.findPageElementByPageId(pageId);
					for (SetupPageElement pe : pageList) {
						SysFunctionButton fb=new SysFunctionButton();
						//创建人
						fb.setCreateBy(session.getAttribute("lmisUserId").toString());
						//更新人
						fb.setUpdateBy(session.getAttribute("lmisUserId").toString());
						//所属机构
						fb.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
						fb.setFbType("fb_button");
						fb.setFbId(fbId+pe.getElementId());
						fb.setFbPid(fbId);
						fb.setFbName(pe.getElementName());
						fb.setFbSeq(pe.getElementSeq());
						insetList.add(fb);
					}
				}
			}
			sysFunctionButtonMapper.updateRecord((T) functionButton);
			if(insetList.size()>0) {
				sysFunctionButtonMapper.createBatch((List<T>) insetList);
			}
		}
		
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}


	/** 
     * 递归查找子节点 
     * @param fbList 
     * @return 
     */  
    public void findChildren(SysFunctionButton treeNode,List<T> fbList,List<SysFunctionButton> sysFbList) {  
        for (SysFunctionButton it : fbList) {  
            if(treeNode.getFbId().equals(it.getFbPid())) { 
            	sysFbList.add(it);
                findChildren(it,fbList,sysFbList);  
            }  
        }  
    }
}
