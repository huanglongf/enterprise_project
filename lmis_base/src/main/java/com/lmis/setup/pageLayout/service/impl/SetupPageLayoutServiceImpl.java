package com.lmis.setup.pageLayout.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.model.Element;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.common.util.BaseUtils;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.setup.page.dao.SetupPageMapper;
import com.lmis.setup.page.model.SetupPage;
import com.lmis.setup.pageElement.dao.SetupPageElementMapper;
import com.lmis.setup.pageElement.model.SetupPageElement;
import com.lmis.setup.pageElement.model.ViewSetupPageElement;
import com.lmis.setup.pageLayout.dao.SetupPageLayoutMapper;
import com.lmis.setup.pageLayout.model.SetupPageLayout;
import com.lmis.setup.pageLayout.model.ViewSetupPageLayout;
import com.lmis.setup.pageLayout.service.SetupPageLayoutServiceInterface;
import com.lmis.setup.pageTable.dao.SetupPageTableMapper;
import com.lmis.setup.pageTable.model.SetupPageTable;
import com.lmis.setup.pageTable.model.ViewSetupPageTable;

/** 
 * @ClassName: SetupPageLayoutServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年12月8日 下午8:24:54 
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class SetupPageLayoutServiceImpl<T extends SetupPageLayout> implements SetupPageLayoutServiceInterface<T> {

	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<SetupPageLayout> dynamicSqlService;
	
	@Autowired
	private SetupPageMapper<SetupPage> setupPageMapper;

	@Autowired
	private SetupPageLayoutMapper<T> setupPageLayoutMapper;

	@Autowired
	private SetupPageElementMapper<SetupPageElement> setupPageElementMapper;

	@Autowired
	private RedisTemplate<?, ?> redisTemplate;

	@Value("${redis.key.user.fb}")
	String redisKeyUserFb;

	@Autowired
	private HttpSession session;
	 
	private static final String FB_BUTTON = "h_e_button";	//页面元素过来type类型
	
	@Autowired
	private SetupPageTableMapper<SetupPageTable> setupPageTableMapper;
	
	@Autowired
	private BaseUtils baseUtils;

	
	@Override
	public LmisResult<?> queryPage(ViewSetupPageLayout viewSetupPageLayout, LmisPageObject pageObject) {
		Page<ViewSetupPageLayout> page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
		
		// PageHelper会自动拦截到下面这查询sql
		setupPageLayoutMapper.listSetupPageLayoutBySeq(viewSetupPageLayout);
	
		// 封装返回页面参数对象
		LmisResult<ViewSetupPageLayout> lmisResult = new LmisResult<ViewSetupPageLayout>();
		lmisResult.setMetaAndData(page.toPageInfo());
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
        return lmisResult;
	}
	
	/**
	 * @Title: checkSetupPageLayout
	 * @Description: TODO(校验页面布局数据)
	 * @param t
	 * @throws Exception
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2018年1月2日 下午8:46:58
	 */
	private void checkSetupPageLayout(T t) throws Exception {
		
		// 是否提交了上级布局
		if(!ObjectUtils.isNull(t.getParentLayoutId())) {
			
			// 提交了上级布局，则当前布局必须为元素布局（暂时只支持两层）
			if(!"layout_a".equals(t.getLayoutType())) throw new Exception("提交了上级布局，则当前布局必须为元素布局");
			
			// 查询提交了的上级布局
			SetupPageLayout param = new SetupPageLayout();
			param.setIsDeleted(false);
			param.setLayoutId(t.getParentLayoutId());
			@SuppressWarnings("unchecked")
			List<T> parentLayout = setupPageLayoutMapper.retrieve((T) param);
			
			// 提交的上级容器必须存在
			if(ObjectUtils.isNull(parentLayout)) throw new Exception("提交的上级布局必须存在");
			
			// 提交的上级布局只可能存在一条记录
			if(parentLayout.size() != 1) throw new Exception("提交的上级布局只可能存在一条记录");
			
			// 提交的上级布局必须为容器布局
			if(!"layout_b".equals(parentLayout.get(0).getLayoutType())) throw new Exception("提交的上级布局必须为容器布局");
			
			// 提交了的上级布局的上级布局必须为空（暂时只支持两层）
			if(!ObjectUtils.isNull((parentLayout.get(0).getParentLayoutId()))) throw new Exception("提交的上级布局的上级布局必须为空");
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<T> addSetupPageLayout(T t) throws Exception {
		
		// 唯一校验
		if(!ObjectUtils.isNull(setupPageLayoutMapper.retrieve((T) new SetupPageLayout(t.getId(), false, t.getLayoutId())))) throw new Exception("布局ID已经存在，不能新增");
		
		// 字段page_id需要校验表sys_function_button中存在
		
		// 数据业务校验
		checkSetupPageLayout(t);
		
		t.setCreateBy(session.getAttribute("lmisUserId").toString());
		t.setPwrOrg(session.getAttribute("lmisUserOrg").toString());
		
		// 新增操作
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, setupPageLayoutMapper.create(t));
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<T> updateSetupPageLayout(T t) throws Exception {
			
		// 存在校验
		if(ObjectUtils.isNull(setupPageLayoutMapper.retrieve((T) new SetupPageLayout(t.getId(), false, t.getLayoutId())))) throw new Exception("布局ID不存在，不能更新");
		
		// 字段page_id需要校验表sys_function_button中存在
	
		// 数据业务校验
		checkSetupPageLayout(t);
		
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		
		// 修改操作
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, setupPageLayoutMapper.updateRecord(t));
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<T> preDeleteSetupPageLayout(T t) throws Exception {
		JSONObject data = new JSONObject();
		
		// 判断是否存在下级布局
		SetupPageLayout param = new SetupPageLayout();
		param.setParentLayoutId(t.getLayoutId());
		// true-下级布局存在；false-下级布局不存在；
		data.put("childrenLayout", !ObjectUtils.isNull(setupPageLayoutMapper.retrieve((T) param)));
		
		// 判断布局页面元素是否存在
		SetupPageElement param1 = new SetupPageElement();
		param1.setLayoutId(t.getLayoutId());
		// true-布局页面元素存在；false-布局页面元素不存在；
		data.put("element", !ObjectUtils.isNull(setupPageElementMapper.retrieve(param1)));
		
		// 判断查询结果是否存在
		SetupPageTable param2 = new SetupPageTable();
		param2.setLayoutId(t.getLayoutId());
		// true-布局查询结果存在；false-布局查询结果不存在；
		data.put("table", !ObjectUtils.isNull(setupPageTableMapper.retrieve(param2)));
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, data);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<T> deleteSetupPageLayout(T t) throws Exception {
			
		// 存在校验
		if(ObjectUtils.isNull(setupPageLayoutMapper.retrieve((T) new SetupPageLayout(t.getId(), false, t.getLayoutId())))) throw new Exception("布局ID不存在，不能删除");
		
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		
		// 删除操作
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, setupPageLayoutMapper.logicalDelete(t));
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<T> previewSetupPageLayout(T t) throws Exception {
		if(ObjectUtils.isNull(t.getPageId())) throw new Exception("页面ID不存在");
		SetupPage param = new SetupPage();
		param.setIsDeleted(false);
		param.setPageId(t.getPageId());
		//调试sql  20180510
		//SqlCombineHelper.startSqlCombine(true);
		List<SetupPage> tmp = setupPageMapper.retrieve(param);
		if(ObjectUtils.isNull(tmp)) throw new Exception("页面ID" + t.getPageId() + "对应数据不存在，数据异常");
		if(tmp.size() != 1) throw new Exception("页面ID" + t.getPageId() + "对应多条数据，数据异常");
		List<SetupPage> setupPages = new ArrayList<SetupPage>();
		setupPages.add(tmp.get(0));

		// 从redis中获取该用户所能操作的菜单栏及按钮权限
		List<String> fbList = ((ValueOperations<String, List<String>>) redisTemplate.opsForValue()).get(redisKeyUserFb + session.getAttribute("lmisUserId"));
		String fbString = null;
		if(CollectionUtils.isNotEmpty(fbList)) fbString = String.join(",", fbList);
		
		// 查询所有子页面
		param.setPageId(null);
		param.setParentPageId(t.getPageId());
		//调试sql  20180510
		//SqlCombineHelper.startSqlCombine(true);
		List<SetupPage> _tmp = setupPageMapper.retrieve(param);
		if(!ObjectUtils.isNull(_tmp)) setupPages.addAll(_tmp);
		JSONArray data = new JSONArray();
		if(!ObjectUtils.isNull(setupPages)) {
			for(int i = 0; i < setupPages.size(); i++) {
				JSONObject page = (JSONObject) JSONObject.toJSON(setupPages.get(i));
				page.put("layout", structureLayoutPlus(setupPages.get(i), fbString, t.getOperationType()));
				data.add(page);
			}
		}
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, data);
	}
	
	private JSONArray structureLayout(SetupPage setupPage, String fbString, String operationType) throws Exception {
		JSONArray data = new JSONArray();
		
		// 查询pageId下所有布局
		ViewSetupPageLayout param = new ViewSetupPageLayout();
		param.setIsDeleted(false);
		param.setPageId(setupPage.getPageId());
		param.setParentLayoutId("");
		List<ViewSetupPageLayout> _layout = setupPageLayoutMapper.listSetupPageLayoutBySeq(param);
		//判断页面按钮元素是否需要权限过滤
		boolean filter = (!ObjectUtils.isNull(operationType) && "preview".equals(operationType)) ? false : setupPage.getPageType().equals("page_default") ? true : false;

		if(!ObjectUtils.isNull(_layout)) {
			// 布局存在
			JSONObject layout = null;
			for(int i = 0; i < _layout.size(); i++) {
				layout = (JSONObject) JSONObject.toJSON(_layout.get(i));
				if("layout_a".equals(_layout.get(i).getLayoutType())) {
					// 元素布局
					structureElementLayout(layout, _layout.get(i), fbString, filter);
				} else if("layout_b".equals(_layout.get(i).getLayoutType())) {
					// 容器布局，查询所有下级元素布局
					param.setParentLayoutId(_layout.get(i).getLayoutId());
					List<ViewSetupPageLayout> _childrenLayout = setupPageLayoutMapper.listSetupPageLayoutBySeq(param);
					if(ObjectUtils.isNull(_childrenLayout)) {
						layout.put("children", new JSONArray());
					} else {
						JSONArray children = new JSONArray();
						JSONObject childrenLayout = null;
						for(int j = 0; j < _childrenLayout.size(); j++) {
							if(!"layout_a".equals(_childrenLayout.get(j).getLayoutType())) throw new Exception("下级页面布局只可能为元素布局，布局ID" + _childrenLayout.get(j).getLayoutId() + "数据异常，不能预览");
							childrenLayout = (JSONObject) JSONObject.toJSON(_childrenLayout.get(j));
							structureElementLayout(childrenLayout, _childrenLayout.get(j), fbString, filter);
							children.add(childrenLayout);
						}
						layout.put("children", children);
					}
				} else {
					throw new Exception("预览的页面布局类型不存在，数据异常，不能预览");
				}
				data.add(layout);
			}
		}
		return data;
	}
	
	/**
	 * 用于将结果查询进行拆分，减少不必要查询
	 * @param setupPage
	 * @param fbString
	 * @param operationType
	 * @return
	 * @throws Exception
	 */
	private JSONArray structureLayoutPlus(SetupPage setupPage, String fbString, String operationType) throws Exception {
		JSONArray data = new JSONArray();
		
		// 查询pageId下所有布局
		ViewSetupPageLayout param = new ViewSetupPageLayout();
		param.setIsDeleted(false);
		param.setPageId(setupPage.getPageId());
		param.setParentLayoutId("");
		List<ViewSetupPageLayout> _layout = setupPageLayoutMapper.listSetupPageLayoutSimpleBySeq(param);
		//判断页面按钮元素是否需要权限过滤
		boolean filter = (!ObjectUtils.isNull(operationType) && "preview".equals(operationType)) ? false : setupPage.getPageType().equals("page_default") ? true : false;

		if(!ObjectUtils.isNull(_layout)) {
			// 布局存在
			JSONObject layout = null;
			for(int i = 0; i < _layout.size(); i++) {
				layout = (JSONObject) JSONObject.toJSON(_layout.get(i));
				if("layout_a".equals(_layout.get(i).getLayoutType())) {
					// 元素布局
					structureElementLayoutPlus(layout, _layout.get(i), fbString, filter);
				} else if("layout_b".equals(_layout.get(i).getLayoutType())) {
					// 容器布局，查询所有下级元素布局
					param.setParentLayoutId(_layout.get(i).getLayoutId());
					List<ViewSetupPageLayout> _childrenLayout = setupPageLayoutMapper.listSetupPageLayoutSimpleBySeq(param);
					if(ObjectUtils.isNull(_childrenLayout)) {
						layout.put("children", new JSONArray());
					} else {
						JSONArray children = new JSONArray();
						JSONObject childrenLayout = null;
						for(int j = 0; j < _childrenLayout.size(); j++) {
							if(!"layout_a".equals(_childrenLayout.get(j).getLayoutType())) throw new Exception("下级页面布局只可能为元素布局，布局ID" + _childrenLayout.get(j).getLayoutId() + "数据异常，不能预览");
							childrenLayout = (JSONObject) JSONObject.toJSON(_childrenLayout.get(j));
							structureElementLayoutPlus(childrenLayout, _childrenLayout.get(j), fbString, filter);
							children.add(childrenLayout);
						}
						layout.put("children", children);
					}
				} else {
					throw new Exception("预览的页面布局类型不存在，数据异常，不能预览");
				}
				data.add(layout);
			}
		}
		return data;
	}
	
	private void structureElementLayout(JSONObject json, ViewSetupPageLayout viewSetupPageLayout, String fbString, boolean filter) {
		ViewSetupPageElement param = new ViewSetupPageElement();
		param.setIsDeleted(false);
		param.setLayoutId(viewSetupPageLayout.getLayoutId());
		List<ViewSetupPageElement> setupPageElement = setupPageElementMapper.listSetupPageElementBySeq(param);
		if(ObjectUtils.isNull(setupPageElement)) {
			json.put("element", new JSONArray());
		} else {
			json.put("element", filter ? JSONArray.toJSON(controllerFbButton(setupPageElement, fbString)) : JSONArray.toJSON(setupPageElement));
		}
		ViewSetupPageTable _param = new ViewSetupPageTable();
		_param.setIsDeleted(false);
		_param.setLayoutId(viewSetupPageLayout.getLayoutId());
		List<ViewSetupPageTable> setupPageTable = setupPageTableMapper.listSetupPageTableBySeq(_param);
		if(ObjectUtils.isNull(setupPageTable)) {
			json.put("table", new JSONArray());
		} else {
			json.put("table", JSONArray.toJSON(setupPageTable));
		}
	}
	
	/**
	 * 对元素获取进行视图区分，减少不必要查询
	 * @param json
	 * @param viewSetupPageLayout
	 * @param fbString
	 * @param filter
	 */
	private void structureElementLayoutPlus(JSONObject json, ViewSetupPageLayout viewSetupPageLayout, String fbString, boolean filter) {
		ViewSetupPageElement param = new ViewSetupPageElement();
		param.setIsDeleted(false);
		param.setLayoutId(viewSetupPageLayout.getLayoutId());
		List<ViewSetupPageElement> setupPageElement = setupPageElementMapper.listSetupPageElementSimpleBySeq(param);
		if(ObjectUtils.isNull(setupPageElement)) {
			json.put("element", new JSONArray());
		} else {
			json.put("element", filter ? JSONArray.toJSON(controllerFbButton(setupPageElement, fbString)) : JSONArray.toJSON(setupPageElement));
		}
		ViewSetupPageTable _param = new ViewSetupPageTable();
		_param.setIsDeleted(false);
		_param.setLayoutId(viewSetupPageLayout.getLayoutId());
		List<ViewSetupPageTable> setupPageTable = setupPageTableMapper.listSetupPageTableSimpleBySeq(_param);
		if(ObjectUtils.isNull(setupPageTable)) {
			json.put("table", new JSONArray());
		} else {
			json.put("table", JSONArray.toJSON(setupPageTable));
		}
	}

	//页面按钮权限过滤
	private List<ViewSetupPageElement> controllerFbButton(List<ViewSetupPageElement> setupPageElement, String fbString) {
		List<ViewSetupPageElement> controls = new ArrayList<ViewSetupPageElement>();
		if(CollectionUtils.isNotEmpty(setupPageElement) && !ObjectUtils.isNull(fbString)) {
			for(ViewSetupPageElement element: setupPageElement) {
				if(element.getElementType().equals(FB_BUTTON)){
					if(fbString.indexOf(element.getElementId()) > 0){
						controls.add(element);
					}
				}else{
					controls.add(element);
				}
			}
		}
		return controls;
	}

	@Override
	public LmisResult<T> getSetupPageLayout(T t) throws Exception {
		List<T> result = setupPageLayoutMapper.retrieve(t);
		if(ObjectUtils.isNull(result)) throw new Exception("查无记录，数据异常");
		if(result.size() != 1) throw new Exception("记录存在多条，数据异常");
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result.get(0));
	}

	@Override
	public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam, LmisPageObject pageObject) throws Exception {
		return dynamicSqlService.executeSelect(dynamicSqlParam, pageObject);
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		
	    // 用于校验是否已经配置
		checkSetupPageElementValid(dynamicSqlParam);
		
		// 根据页面传递的参数动态构造当前对象
		SetupPageLayout setupPageLayout = dynamicSqlService.generateTableModel((DynamicSqlParam<SetupPageLayout>) dynamicSqlParam, new SetupPageLayout()).getTableModel();
		
		// 数据业务校验
		checkSetupPageLayout((T) setupPageLayout);
	
		// 不允许前端传递页面布局 ID
		notAllowElementId(dynamicSqlParam,"P_YMBJ2_ADD_P01_E01");
		
		// 使用配置的 id 生成策略
		String generateId = baseUtils.GetCodeRule("PAGE_NUM");
		setupPageLayout.setLayoutId(generateId);
		// 唯一校验
		if(!ObjectUtils.isNull(setupPageLayoutMapper.retrieve((T) new SetupPageLayout(null, false, setupPageLayout.getLayoutId())))) throw new Exception("布局ID已经存在，不能新增");
		
		// 为布局 ID 自动赋值
		Element element = new Element();
		element.setElementId("P_YMBJ2_ADD_P01_E01");
		element.setValue(generateId);
		dynamicSqlParam.getElements().add(element);
	
		return dynamicSqlService.executeInsert(dynamicSqlParam);
	}

	/**
	 * 不允许前端传递布局 ID 值，由后端统一赋值
	 * @param dynamicSqlParam
	 * @throws Exception 
	 */
	private void notAllowElementId(DynamicSqlParam<T> dynamicSqlParam, String elementId) throws Exception {
		List<Element> elements = dynamicSqlParam.getElements();
		if (ObjectUtils.isNull(elements)) throw new Exception("参数 elements 不能为空，数据异常");
		for (Element element : elements) {
			if (element.getElementId().equals(elementId)) {
				throw new Exception("页面布局ID由后端统一生成，不允许手动指定!");
			}
		}
	}

	private void checkSetupPageElementValid(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		SetupPageElement param = new SetupPageElement();
		param.setIsDeleted(false);
		param.setLayoutId(dynamicSqlParam.getLayoutId());
		param.setTableId("setup_page_layout");
		param.setColumnId("page_id");
		List<SetupPageElement> tmp = setupPageElementMapper.retrieve(param);
		if(ObjectUtils.isNull(tmp)) throw new Exception("无字段page_id对应记录，数据异常");
		if(tmp.size() != 1) throw new Exception("字段page_id存在多条记录，数据异常");
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		
		// 用于校验是否已经配置
		checkSetupPageElementValid(dynamicSqlParam);
				
		// 根据页面传递的参数动态构造当前对象
		SetupPageLayout setupPageLayout = dynamicSqlService.generateTableModel((DynamicSqlParam<SetupPageLayout>) dynamicSqlParam, new SetupPageLayout()).getTableModel();
		
		// 存在校验
		if(ObjectUtils.isNull(setupPageLayoutMapper.retrieve((T) new SetupPageLayout(setupPageLayout.getId(), false, setupPageLayout.getLayoutId())))) throw new Exception("布局ID不存在，不能更新");
				
		// 数据业务校验
		checkSetupPageLayout((T) setupPageLayout);		
		return dynamicSqlService.executeUpdate(dynamicSqlParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeSelect(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		LmisResult<?> _lmisResult = dynamicSqlService.executeSelect(dynamicSqlParam);
		if(LmisConstant.RESULT_CODE_ERROR.equals(_lmisResult.getCode())) return _lmisResult;
		List<Map<String, Object>> result =  (List<Map<String, Object>>) _lmisResult.getData();
		if(ObjectUtils.isNull(result)) throw new Exception("查无记录，数据异常");
		if(result.size() != 1) throw new Exception("记录存在多条，数据异常");
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result.get(0));
	}

}
