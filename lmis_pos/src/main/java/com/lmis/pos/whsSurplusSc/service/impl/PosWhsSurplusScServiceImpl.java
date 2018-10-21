package com.lmis.pos.whsSurplusSc.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmis.common.dataFormat.ObjectUtils;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.whsSurplusSc.dao.PosWhsSurplusScMapper;
import com.lmis.pos.whsSurplusSc.model.PosWhsSurplusSc;
import com.lmis.pos.whsSurplusSc.service.PosWhsSurplusScServiceInterface;

/** 
 * @ClassName: PosWhsSurplusScServiceImpl
 * @Description: TODO(仓库剩余入库能力分析业务层实现类)
 * @author codeGenerator
 * @date 2018-05-30 17:16:40
 * 
 * @param <T>
 */
@Transactional(rollbackFor=Exception.class)
@Service
public class PosWhsSurplusScServiceImpl<T extends PosWhsSurplusSc> implements PosWhsSurplusScServiceInterface<T> {
	
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<PosWhsSurplusSc> dynamicSqlService;
	
	@Autowired
	private PosWhsSurplusScMapper<T> posWhsSurplusScMapper;

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
		if(ObjectUtils.isNull(result)) throw new Exception("查无记录，数据异常");
		if(result.size() != 1) throw new Exception("记录存在多条，数据异常");
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, result.get(0));
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
	public LmisResult<?> deletePosWhsSurplusSc(T t) throws Exception {
			
		// TODO(业务校验)
		
		// 删除操作
		return new LmisResult<T>(LmisConstant.RESULT_CODE_SUCCESS, posWhsSurplusScMapper.logicalDelete(t));
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = false,propagation=Propagation.NESTED,rollbackFor = Exception.class)
	public LmisResult<?> updatePlannedInAbility(String whsCode, String bu, String crd, Integer qty) throws Exception {
		LmisResult<?> result = new LmisResult<>(LmisConstant.RESULT_CODE_ERROR,"");
		
		if(StringUtils.isBlank(whsCode)) {//whsCode为空的情况直接忽视，用于不占用库存能力操作
			result.setCode(LmisConstant.RESULT_CODE_SUCCESS);
			return result;
		}
		
		if(StringUtils.isBlank(bu) || 
				StringUtils.isBlank(crd)  || 
				qty == null ) {
			result.setMessage("参数存在空值，请传入正确参数");
			return result;
		}
		//检查是否crd是否为排班第一日
		PosWhsSurplusSc posWhsSurplusSc = new PosWhsSurplusSc();
		posWhsSurplusSc.setWhsCode(whsCode);
		posWhsSurplusSc.setSkuType(bu);
		posWhsSurplusSc.setScheduleDate(crd);
		posWhsSurplusSc.setIsDeleted(false);
		List<T> posWhsSurplusScList = posWhsSurplusScMapper.retrieve((T)posWhsSurplusSc);
		if(CollectionUtils.isEmpty(posWhsSurplusScList)) {
			result.setMessage("未查找到仓库剩余入库能力分析表记录，请确认参数正确");
			return result;
		}
		String updateBy = session.getAttribute("lmisUserId").toString();
		PosWhsSurplusSc targetPosWhsSurplusSc = posWhsSurplusScList.get(0);
		posWhsSurplusSc.setPlannedInPo(
				new BigDecimal(
						targetPosWhsSurplusSc.getPlannedInPo() == null ? 
								0 : targetPosWhsSurplusSc.getPlannedInPo()
				)
				.add(
					new BigDecimal(qty)
				).intValue());
		
		if(targetPosWhsSurplusSc.getDateId().equals(crd)) {
			//这里执行排班第一天逻辑
			result = updatePlannedInAbilityForFirstDay(posWhsSurplusSc,
					targetPosWhsSurplusSc,updateBy);
		} else {
			//这里执行非排班第一天逻辑
			result = updatePlannedInAbilityForOtherDay(posWhsSurplusSc,
					targetPosWhsSurplusSc,updateBy);
		}
		//这里因为同个仓库不同bu(鞋服配)间共享可用作业能力(inJobsEnable),这里需要将更新后的inJobsEnable值刷入其他bu中
		if(result != null && LmisConstant.RESULT_CODE_SUCCESS.equals(result.getCode())) {
			//计算可用作业能力
			BigDecimal inJobsEnable = new BigDecimal(targetPosWhsSurplusSc.getInJobsMax());
			
			PosWhsSurplusSc flushThisWhsOtherBuThisDayInJobsEnable = new PosWhsSurplusSc();
			flushThisWhsOtherBuThisDayInJobsEnable.setWhsCode(whsCode);
			flushThisWhsOtherBuThisDayInJobsEnable.setScheduleDate(crd);
			flushThisWhsOtherBuThisDayInJobsEnable.setIsDeleted(false);
			//设置影响排班批次为targetPosWhsSurplusSc相同批次
			flushThisWhsOtherBuThisDayInJobsEnable.setDateId(targetPosWhsSurplusSc.getDateId());
			//查询该仓库当日其他bu类型记录
			posWhsSurplusScList = posWhsSurplusScMapper
					.retrieve((T)flushThisWhsOtherBuThisDayInJobsEnable);

			if(CollectionUtils.isEmpty(posWhsSurplusScList)) {
				result.setMessage("未查找到该仓库其他bu类型当日入库能力记录，当前bu:" + bu);
				return result;
			}
			
			for (T t : posWhsSurplusScList) {
				inJobsEnable = inJobsEnable.subtract(new BigDecimal(t.getPlannedInPo()));
			}
			
			//设置刷入值
			flushThisWhsOtherBuThisDayInJobsEnable.setSkuType(bu);
			flushThisWhsOtherBuThisDayInJobsEnable.setInJobsEnable(inJobsEnable.intValue());
			//刷新在相同排班批次记录里,同仓库下其他bu类型在同一天的inJobsEnable(涉及inEnable、inEnablePlus变化)
			int r = posWhsSurplusScMapper.flushThisWhsOtherBuThisDayInJobsEnable(
					(T) flushThisWhsOtherBuThisDayInJobsEnable);
			if(r<=0) {
				result.setCode(LmisConstant.RESULT_CODE_ERROR);
				result.setMessage("刷新在相同排班批次记录失败");
			}
		}
		
		
		return result;
	}
	
	/**
	 * Title: updatePlannedInAbilityForFirstDay
	 * Description: 对排班第一天执行分析
	 * @param paramPosWhsSurplusSc		传入参数条件
	 * @param targetPosWhsSurplusSc     需操作记录
	 * @return
	 * @author lsh8044
	 * @date 2018年6月1日
	 */
	@SuppressWarnings("unchecked")
	private LmisResult<?> updatePlannedInAbilityForFirstDay(PosWhsSurplusSc paramPosWhsSurplusSc,
			PosWhsSurplusSc targetPosWhsSurplusSc,String updateBy) throws Exception{
		LmisResult<String> result = new LmisResult<>(LmisConstant.RESULT_CODE_ERROR,"");
		
		//保存旧Po计划入库数
		Integer oldPlannedInPo = targetPosWhsSurplusSc.getPlannedInPo();
		targetPosWhsSurplusSc.setPlannedInPo(paramPosWhsSurplusSc.getPlannedInPo());
		targetPosWhsSurplusSc.setUpdateBy(updateBy);
		targetPosWhsSurplusSc.setUpdateTime(new Date());
		//执行排班周期内第一天刷新记录逻辑
		int r = posWhsSurplusScMapper.updatePlannedInAbilityForFirstDay((T) targetPosWhsSurplusSc);
		if(r<=0) {
			result.setMessage("修改仓库剩余入库能力分析表记录失败");
			return result;
		}
		//刷新排班周期内ScheduleDate大于本crd日期的其他记录
		//计算增量
		PosWhsSurplusSc posWhsSurplusScOtherDayQuery = new PosWhsSurplusSc();
		posWhsSurplusScOtherDayQuery.setScheduleDate(paramPosWhsSurplusSc.getScheduleDate());
		posWhsSurplusScOtherDayQuery.setWhsCode(paramPosWhsSurplusSc.getWhsCode());
		posWhsSurplusScOtherDayQuery.setSkuType(paramPosWhsSurplusSc.getSkuType());
		posWhsSurplusScOtherDayQuery.setDateId(targetPosWhsSurplusSc.getDateId());
		posWhsSurplusScOtherDayQuery.setPlannedInPo(
				new BigDecimal(paramPosWhsSurplusSc.getPlannedInPo())
					.subtract(
						new BigDecimal(oldPlannedInPo)
					).intValue());
		posWhsSurplusScOtherDayQuery.setUpdateBy(updateBy);
		posWhsSurplusScOtherDayQuery.setUpdateTime(new Date());
		r = posWhsSurplusScMapper.updatePlannedInAbilityForDayRight((T) posWhsSurplusScOtherDayQuery);
		
		result.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return result;
	}
	
	/**
	 * Title: updatePlannedInAbilityForOtherDay
	 * Description: 对非排班第一天执行分析
	 * @param paramPosWhsSurplusSc		传入参数条件
	 * @param targetPosWhsSurplusSc     需操作记录
	 * @param updateBy     修改人
	 * @return
	 * @author lsh8044
	 * @date 2018年6月1日
	 */
	@SuppressWarnings("unchecked")
	private LmisResult<?> updatePlannedInAbilityForOtherDay(PosWhsSurplusSc paramPosWhsSurplusSc,
			PosWhsSurplusSc targetPosWhsSurplusSc,String updateBy) throws Exception{
		LmisResult<String> result = new LmisResult<>(LmisConstant.RESULT_CODE_ERROR,"");

		//保存旧Po计划入库数
		Integer oldPlannedInPo = targetPosWhsSurplusSc.getPlannedInPo();
		targetPosWhsSurplusSc.setPlannedInPo(paramPosWhsSurplusSc.getPlannedInPo());
		targetPosWhsSurplusSc.setUpdateBy(updateBy);
		targetPosWhsSurplusSc.setUpdateTime(new Date());
		//执行排班周期内第一天刷新记录逻辑
		int r = posWhsSurplusScMapper.updatePlannedInAbilityForOtherDay((T) targetPosWhsSurplusSc);
		if(r<=0) {
			result.setMessage("修改仓库剩余入库能力分析表记录失败");
			return result;
		}

		//刷新排班周期内ScheduleDate大于本crd日期的其他记录
		//计算增量
		PosWhsSurplusSc posWhsSurplusScOtherDayQuery = new PosWhsSurplusSc();
		posWhsSurplusScOtherDayQuery.setScheduleDate(paramPosWhsSurplusSc.getScheduleDate());
		posWhsSurplusScOtherDayQuery.setWhsCode(paramPosWhsSurplusSc.getWhsCode());
		posWhsSurplusScOtherDayQuery.setSkuType(paramPosWhsSurplusSc.getSkuType());
		posWhsSurplusScOtherDayQuery.setDateId(targetPosWhsSurplusSc.getDateId());
		posWhsSurplusScOtherDayQuery.setPlannedInPo(
				new BigDecimal(paramPosWhsSurplusSc.getPlannedInPo())
					.subtract(
						new BigDecimal(oldPlannedInPo)
					).intValue());
		posWhsSurplusScOtherDayQuery.setUpdateBy(updateBy);
		posWhsSurplusScOtherDayQuery.setUpdateTime(new Date());
		r = posWhsSurplusScMapper.updatePlannedInAbilityForDayRight((T) posWhsSurplusScOtherDayQuery);

		result.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return result;
	}

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public LmisResult<?> selectPosWhsSurplusScList(T t,LmisPageObject pageObject){
        // TODO Auto-generated method stub
        Page page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
        posWhsSurplusScMapper.selectPosWhsSurplusScList(t);
        LmisResult lmisResult = new LmisResult();
        lmisResult.setMetaAndData(page.toPageInfo());
        Map result = new HashMap();
        result.put("sum",  posWhsSurplusScMapper.selectPosWhsSurplusSumBySku(t));
        result.put("list", lmisResult.getData());
        lmisResult.setData(result);
        lmisResult.setCode("S1001");
        return lmisResult;
    }
	
	
	

}
