package com.lmis.pos.accReport.service.impl;
import java.text.DateFormat;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lmis.common.dynamicSql.model.DynamicSqlParam;
import com.lmis.common.dynamicSql.service.DynamicSqlServiceInterface;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.accReport.dao.PosPlanWareAnalyMapper;
import com.lmis.pos.accReport.model.PosPlanWareAnalyAreaScale;
import com.lmis.pos.accReport.service.PosPlanWareAnalyServiceInterface;
import com.lmis.pos.common.model.PosDataFileTemplete;

import com.lmis.pos.common.service.PosDataFileTempleteService;

/**
 * 
 *@author jinggong.li
 *@date 2018年6月22日  
 * @param <T>
 */
@Service
public class PosPlanWareAnalyServiceImpl<T extends PosPlanWareAnalyAreaScale> implements PosPlanWareAnalyServiceInterface<T> {
	
	@Resource
	private PosPlanWareAnalyMapper posPlanWareAnalyAreaScaleMapper;
	
	@Resource(name="dynamicSqlServiceImpl")
	DynamicSqlServiceInterface<PosPlanWareAnalyAreaScale> dynamicSqlService;
	
	@Value("${lmis_pos.podata.excel_import_file_download_path}")
	private String excelImportFileDownloadPath; 
	
	@Value("${lmis_pos.podata.excel_import_file_upload_path}")
	private String excelImportFileUploadPath;

	
	@Resource(name="redisTemplate")
    private RedisTemplate<String, ?> redisTemplate;

	@Resource(name="posDataFileTempleteServiceImpl")
	private PosDataFileTempleteService<PosDataFileTemplete> posDataFileTempleteService;

	
	@Override
	public LmisResult<?> selectPosPlanWareAnalyAreaScale(DynamicSqlParam<PosPlanWareAnalyAreaScale> dynamicSqlParam,
			LmisPageObject pageObject, String beginDateTime, String endDateTime,String bu,String whsName) throws Exception {
		 LmisResult<Map<String, Object>> lmisResult = new LmisResult<Map<String, Object>>();
		 dynamicSqlParam.setIsDeleted(false);
		 //获取到公共传参参数
		 PosPlanWareAnalyAreaScale posPlanWareAnalyAreaScale = getPosPlanWareAnalyAreaScale(dynamicSqlParam, beginDateTime, endDateTime, bu, whsName);
		 Page<Map<String, Object>> page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
		 posPlanWareAnalyAreaScaleMapper.selectPosPlanWareAnalyAreaScale(posPlanWareAnalyAreaScale);
		 lmisResult.setMetaAndData(page.toPageInfo());
		 lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		 return lmisResult;
	}
	
	
	private PosPlanWareAnalyAreaScale getPosPlanWareAnalyAreaScale(DynamicSqlParam<PosPlanWareAnalyAreaScale> dynamicSqlParam,String beginDateTime, String endDateTime,String bu,String whsName) throws Exception {
		PosPlanWareAnalyAreaScale posPlanWareAnalyAreaScale = dynamicSqlService.generateTableModel(dynamicSqlParam,new PosPlanWareAnalyAreaScale()).getTableModel()==null ? new PosPlanWareAnalyAreaScale(): dynamicSqlService.generateTableModel(dynamicSqlParam,new PosPlanWareAnalyAreaScale()).getTableModel();
		 if(!judgementDate(beginDateTime,endDateTime)){
			 throw new Exception("结束时间与开始时间间隔不能超过90天");
		 }else{
			 if(null != beginDateTime && !"".equals(beginDateTime)) {
				 posPlanWareAnalyAreaScale.setBeginDateTime(beginDateTime);
			 }
			 if(null != endDateTime && !"".equals(endDateTime)) {
				posPlanWareAnalyAreaScale.setEndDateTime(endDateTime);
			 }
		 };
		 if(null != bu && !"".equals(bu)) {
			 posPlanWareAnalyAreaScale.setBu(bu);
		 }
		 if(null != whsName && !"".equals(whsName)) {
			 posPlanWareAnalyAreaScale.setWhsName(whsName);
		 }
		return posPlanWareAnalyAreaScale;
		
	}
	
	//判断日期
    private Boolean judgementDate(String beginDateTime, String endDateTime) throws Exception {
        boolean flag = true;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        long m = 0;
    	if((null != beginDateTime && !"".equals(beginDateTime)) && ( null != endDateTime && !"".equals(endDateTime)) ) {
    		 Date beginDate = df.parse(beginDateTime);
    		 Date endDate = df.parse(endDateTime);
    		 long diff = (endDate.getTime() - beginDate.getTime())/(1000 * 60 * 60 * 24);
    		 if((diff - 90) >= 0) {
    			 flag = false;
    		 }
    		 
    	}
		return flag;
    }


	/**
	 * 查询仓库名称
	 */
	@Override
	public LmisResult<?> selectWhsallocate(DynamicSqlParam<PosPlanWareAnalyAreaScale> dynamicSqlParam)
			throws Exception {
		LmisResult<Map<String, Object>> result = new LmisResult<Map<String, Object>>();
		result.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		result.setData(posPlanWareAnalyAreaScaleMapper.selectWhsallocate());
		return result ;
	}
	
	
	
	public LmisResult<?> exportPosPlanWareAnalyAreaScale(String templeteId,String suffixName, String fileName, DynamicSqlParam<PosPlanWareAnalyAreaScale> dynamicSqlParam, 
                                       String beginDateTime, String endDateTime, String bu, String whsName)throws Exception {
		LmisResult<Map<String, Object>> result = new LmisResult<Map<String, Object>>();
		
			if(null == fileName || "".equals(fileName)) {
				throw new Exception("文件名不能为空!!");
			}
			if(null == templeteId || "".equals(templeteId)) {
				throw new Exception("模版id不能为空");
			}
			dynamicSqlParam.setIsDeleted(false);
			PosPlanWareAnalyAreaScale posPlanWareAnalyAreaScale = getPosPlanWareAnalyAreaScale(dynamicSqlParam, beginDateTime, endDateTime, bu, whsName);
		    List<Map<String,Object>> lMap = posPlanWareAnalyAreaScaleMapper.exportPosPlanWareAnalyAreaScale(posPlanWareAnalyAreaScale);
		    if(lMap.size() <= 0) {
		    	throw new Exception("查询的数据条目不能小于0");
		    }
		    String filterJsonStr = JSON.toJSONString(lMap);
		    return result;
	}
	


}
