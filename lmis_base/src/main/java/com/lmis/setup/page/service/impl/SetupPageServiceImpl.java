package com.lmis.setup.page.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.common.util.BaseUtils;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.setup.page.dao.SetupPageMapper;
import com.lmis.setup.page.model.SetupPage;
import com.lmis.setup.page.service.SetupPageServiceInterface;
import com.lmis.setup.pageElement.dao.SetupPageElementMapper;
import com.lmis.setup.pageElement.model.SetupPageElement;
import com.lmis.setup.pageLayout.dao.SetupPageLayoutMapper;
import com.lmis.setup.pageLayout.model.SetupPageLayout;
import com.lmis.setup.pageTable.dao.SetupPageTableMapper;
import com.lmis.setup.pageTable.model.SetupPageTable;

/** 
 * @ClassName: SetupPageServiceImpl
 * @Description: TODO(配置页面业务实现类)
 * @author Ian.Huang
 * @date 2017年12月28日 下午4:06:32 
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class SetupPageServiceImpl<T extends SetupPage> implements SetupPageServiceInterface<T> {

	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<SetupPage> dynamicSqlService;
	@Autowired
	private SetupPageMapper<T> setupPageMapper;
	@Autowired
	private SetupPageElementMapper<SetupPageElement> setupPageElementMapper;
	@Autowired
	private SetupPageLayoutMapper<SetupPageLayout> setupPageLayoutMapper;
	@Autowired
	private SetupPageTableMapper<SetupPageTable> setupPageTableMapper;
	@Autowired
	private BaseUtils baseUtils;
	private static Log log = LogFactory.getLog(SetupPageServiceImpl.class);

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
		if(ObjectUtils.isNull(result)) throw new Exception("查无记录，数据异常");
		if(result.size() != 1) throw new Exception("记录存在多条，数据异常");
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result.get(0));
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		SetupPageElement param = new SetupPageElement();
		param.setIsDeleted(false);
		param.setLayoutId(dynamicSqlParam.getLayoutId());
		param.setTableId("setup_page");
		param.setColumnId("page_id");
		List<SetupPageElement> tmp = setupPageElementMapper.retrieve(param);
		if(ObjectUtils.isNull(tmp)) throw new Exception("无字段page_id对应记录，数据异常");
		if(tmp.size() != 1) throw new Exception("字段page_id存在多条记录，数据异常");
		
		// 唯一校验
		if(!ObjectUtils.isNull(setupPageMapper.retrieve((T) new SetupPage(null, false, dynamicSqlParam.getElementValue(tmp.get(0).getElementId()))))) throw new Exception("页面ID已经存在，不能新增");
		return dynamicSqlService.executeInsert(dynamicSqlParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		SetupPageElement param = new SetupPageElement();
		param.setIsDeleted(false);
		param.setLayoutId(dynamicSqlParam.getLayoutId());
		param.setTableId("setup_page");
		param.setColumnId("page_id");
		List<SetupPageElement> tmp = setupPageElementMapper.retrieve(param);
		if(ObjectUtils.isNull(tmp)) throw new Exception("无字段page_id对应记录，数据异常");
		if(tmp.size() != 1) throw new Exception("字段page_id存在多条记录，数据异常");
		
		// 存在校验
		if(ObjectUtils.isNull(setupPageMapper.retrieve((T) new SetupPage(null, false, dynamicSqlParam.getElementValue(tmp.get(0).getElementId()))))) throw new Exception("页面ID不存在，不能更新");
		return dynamicSqlService.executeUpdate(dynamicSqlParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> deleteSetupPage(T t) throws Exception {
			
		// 存在校验
		if(ObjectUtils.isNull(setupPageMapper.retrieve((T) new SetupPage(null, false, t.getPageId())))) throw new Exception("页面ID不存在，不能删除");
		
		// 删除操作
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, setupPageMapper.logicalDelete(t));
	}

	
	@Override
	public LmisResult<?> copySetupPage(String currentPageId, String orignPageId) throws Exception {
		// 参数校验
		List<SetupPage> currentPage = checkValid(currentPageId, orignPageId);

		// 通过传入的当前页面 ID 逻辑删除相关表中对应的数据
		SetupPageLayout setupPageLayout = new SetupPageLayout();
		setupPageLayout.setIsDeleted(false);
		setupPageLayout.setPageId(currentPage.get(0).getPageId());
		List<SetupPageLayout> pageLayouts = setupPageLayoutMapper.retrieve(setupPageLayout);
		List<String> deleteLayoutIds = new ArrayList<>();
		long deleteStart = System.currentTimeMillis();
		if (!ObjectUtils.isNull(pageLayouts)) {
			for(SetupPageLayout layout : pageLayouts) {
				deleteLayoutIds.add(layout.getLayoutId());
			}
			// 通过 layoutId 批量逻辑删除页面元素
			setupPageElementMapper.updateBatch(deleteLayoutIds);
			// 通过 layoutId 批量逻辑删除查询列表
			setupPageTableMapper.updateBatch(deleteLayoutIds);
			// 通过 layoutId 批量逻辑删除页面布局
			setupPageLayoutMapper.updateBatch(deleteLayoutIds);
		}
		long deleteEnd = System.currentTimeMillis();
		log.info("批量逻辑删除耗时(毫秒): " + (deleteEnd - deleteStart));
		
		// 将来源页面的数据为当前页面进行重新生成数据并绑定
		SetupPageLayout originParam = new SetupPageLayout();
		originParam.setIsDeleted(false);
		originParam.setPageId(orignPageId);
		List<SetupPageLayout> oldPageLayouts = setupPageLayoutMapper.retrieve(originParam);
		
		// 测试大批量提交耗时
		List<SetupPageElement> nweSetupPageElements = new ArrayList<>();
		List<SetupPageTable> nweSetupPageTables = new ArrayList<>();

		if (!ObjectUtils.isNull(oldPageLayouts)) {
			long generateStart = System.currentTimeMillis();
			for (SetupPageLayout orignSetupPageLayout : oldPageLayouts) {
				// 新生成的 layoutId
				String newLayoutId = baseUtils.GetCodeRule("PAGE_NUM");
				
				SetupPageLayout oldSetupPageLayout = orignSetupPageLayout;
				// 生成该布局下的新页面元素
				createNewElementByOldLayoutId(newLayoutId, oldSetupPageLayout,nweSetupPageElements);
				// 生成该布局下的新查询列表
				createNewSetupPageTableByOldLayoutId(newLayoutId, oldSetupPageLayout,nweSetupPageTables);
				
				// 指定为当前页面的ID
				orignSetupPageLayout.setPageId(currentPage.get(0).getPageId());
				// 生成新的 layoutId
				orignSetupPageLayout.setLayoutId(newLayoutId);
			}
			long generateEnd = System.currentTimeMillis();
			log.info("调用生成新的 ID 总耗时： " + (generateEnd - generateStart));
			
			// 统一提交测试耗时
			long begin = System.currentTimeMillis();
			log.info("批量插入开始:" + new Date());
			setupPageElementMapper.createBatch(nweSetupPageElements);
			setupPageTableMapper.createBatch(nweSetupPageTables);
			setupPageLayoutMapper.createBatch(oldPageLayouts);
			long end = System.currentTimeMillis();
			log.info("批量插入结束:" + new Date());
			log.info("批量插入耗时(毫秒):" + (end - begin));
		}
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, "复制页面布局成功！");
	}

	@SuppressWarnings("unchecked")
	private List<SetupPage> checkValid(String currentPageId, String orignPageId) throws Exception {
		// 非空校验
		if (ObjectUtils.isNull(currentPageId)) {
			throw new Exception("当前页面ID不能为空");
		}
		if (ObjectUtils.isNull(orignPageId)) {
			throw new Exception("来源页面ID不能为空");
		}
		if (currentPageId.equals(orignPageId)) {
			throw new Exception("来源页面ID和当前页面ID不能相同");
		}
		
		// 存在校验
		List<SetupPage> currentPage =  (List<SetupPage>) setupPageMapper.retrieve((T) new SetupPage(null, false, currentPageId));
		if(ObjectUtils.isNull(currentPage)) throw new Exception("当前页面不存在，不能复制");
		
		List<?> orignPage =  setupPageMapper.retrieve((T) new SetupPage(null, false, orignPageId));
		if(ObjectUtils.isNull(orignPage)) throw new Exception("来源页面不存在，不能复制");
		return currentPage;
	}

	private void createNewSetupPageTableByOldLayoutId(String newLayoutId, SetupPageLayout oldSetupPageLayout,List<SetupPageTable> newSetupPageTables) {
		SetupPageTable oldSetupPageTableParam = new SetupPageTable();
		oldSetupPageTableParam.setIsDeleted(false);
		oldSetupPageTableParam.setLayoutId(oldSetupPageLayout.getLayoutId());
		List<SetupPageTable> oldSetupPageTables = setupPageTableMapper.retrieve(oldSetupPageTableParam);
		if (!ObjectUtils.isNull(oldSetupPageTables)) {
			for (SetupPageTable newSetupPageTable: oldSetupPageTables) {
				newSetupPageTable.setLayoutId(newLayoutId);
				newSetupPageTable.setColumnId(baseUtils.GetCodeRule("PAGE_NUM"));
				newSetupPageTables.add(newSetupPageTable);
			}
		}
	}

	private void createNewElementByOldLayoutId(String newLayoutId, SetupPageLayout oldSetupPageLayout,List<SetupPageElement> newSetupPageElements) {
		SetupPageElement oldParam =  new SetupPageElement();
		oldParam.setIsDeleted(false);
		oldParam.setLayoutId(oldSetupPageLayout.getLayoutId());
		List<SetupPageElement> oldElements = setupPageElementMapper.retrieve(oldParam);
		if (!ObjectUtils.isNull(oldElements)) {
			for (SetupPageElement newSetupPageElement : oldElements) {
				// 为此元素生成新的 ID
				String newElementId = baseUtils.GetCodeRule("PAGE_NUM");
				newSetupPageElement.setElementId(newElementId);
				// 为此元素绑定新的 layoutId
				newSetupPageElement.setLayoutId(newLayoutId);
				newSetupPageElements.add(newSetupPageElement);
			}
		}
	}

}
