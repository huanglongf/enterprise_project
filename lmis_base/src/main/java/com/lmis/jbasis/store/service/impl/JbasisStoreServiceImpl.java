package com.lmis.jbasis.store.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.jbasis.store.dao.JbasisStoreMapper;
import com.lmis.jbasis.store.model.JbasisStore;
import com.lmis.jbasis.store.service.JbasisStoreServiceInterface;

/**
 * @ClassName: JbasisStoreServiceImpl
 * @Description: TODO(店铺信息（基础数据）业务层实现类)
 * @author codeGenerator
 * @date 2018-04-13 10:22:24
 * 
 * @param <T>
 */
@Transactional(rollbackFor = Exception.class)
@Service
public class JbasisStoreServiceImpl<T extends JbasisStore> implements JbasisStoreServiceInterface<T> {

	@Resource(name = "dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<JbasisStore> dynamicSqlService;
	@Autowired
	private JbasisStoreMapper<T> jbasisStoreMapper;
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
		if (LmisConstant.RESULT_CODE_ERROR.equals(_lmisResult.getCode()))
			return _lmisResult;
		List<Map<String, Object>> result = (List<Map<String, Object>>) _lmisResult.getData();
		if (ObjectUtils.isNull(result))
			throw new Exception("查无记录，数据异常");
		if (result.size() != 1)
			throw new Exception("记录存在多条，数据异常");
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result.get(0));
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeInsert(DynamicSqlParam<T> dynamicSqlParam) throws Exception {
		LmisResult<?> lmisResult = new LmisResult<>();
		JbasisStore store = dynamicSqlService.generateTableModel((DynamicSqlParam<JbasisStore>) dynamicSqlParam, new JbasisStore()).getTableModel();
		// 店铺编码唯一校验
		if (ObjectUtils.isNull(store.getStoreCode())) {
			lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
			lmisResult.setMessage("店铺编码不能为空");
			return lmisResult;
		} else {
			JbasisStore checkStore = new JbasisStore();
			checkStore.setIsDeleted(false);
			checkStore.setStoreCode(store.getStoreCode());
			if (jbasisStoreMapper.retrieve((T) checkStore).size() > 0) {
				lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
				lmisResult.setMessage("当前店铺编码已存在");
				return lmisResult;
			}
		}
		// 店铺名称唯一校验
		if (ObjectUtils.isNull(store.getStoreName())) {
			lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
			lmisResult.setMessage("店铺名称不能为空");
			return lmisResult;
		} else {
			JbasisStore checkStore = new JbasisStore();
			checkStore.setIsDeleted(false);
			checkStore.setStoreName(store.getStoreName());
			if (jbasisStoreMapper.retrieve((T) checkStore).size() > 0) {
				lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
				lmisResult.setMessage("当前店铺名称已存在");
				return lmisResult;
			}
		}
		// 插入操作
		return dynamicSqlService.executeInsert(dynamicSqlParam);
	}

	@SuppressWarnings("unchecked")
	@Override
	public LmisResult<?> executeUpdate(DynamicSqlParam<T> dynamicSqlParam) throws Exception {

		LmisResult<?> lmisResult = new LmisResult<>();
		JbasisStore store = dynamicSqlService.generateTableModel((DynamicSqlParam<JbasisStore>) dynamicSqlParam, new JbasisStore()).getTableModel();
		// 店铺名称唯一校验
		if (ObjectUtils.isNull(store.getStoreName())) {
			lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
			lmisResult.setMessage("店铺名称不能为空");
			return lmisResult;
		} else {
			JbasisStore checkStore = new JbasisStore();
			checkStore.setIsDeleted(false);
			checkStore.setStoreName(store.getStoreName());
			List<T> list= jbasisStoreMapper.retrieve((T) checkStore);
			if (list.size() >0 && !list.get(0).getStoreCode().equals(store.getStoreCode())) {
				lmisResult.setCode(LmisConstant.RESULT_CODE_ERROR);
				lmisResult.setMessage("当前店铺名称已存在");
				return lmisResult;
			}
		}

		return dynamicSqlService.executeUpdate(dynamicSqlParam);
	}

	@Override
	public LmisResult<?> deleteJbasisStore(T t) throws Exception {

		// 更新人
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());
		
		// 删除操作
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, jbasisStoreMapper.logicalDelete(t));
	}

	@Override
	public LmisResult<?> switchJbasisStore(T t) throws Exception {

		// 更新人
		t.setUpdateBy(session.getAttribute("lmisUserId").toString());

		// 启用店铺/禁用店铺
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, jbasisStoreMapper.shiftValidity(t));
	}

}
