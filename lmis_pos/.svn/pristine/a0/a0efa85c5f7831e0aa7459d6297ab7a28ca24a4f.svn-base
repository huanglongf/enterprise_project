package com.lmis.pos.skuMaster.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Lists;
import com.lmis.common.dataFormat.GetUuidForJava;
import com.lmis.framework.baseInfo.LmisConstant;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;
import com.lmis.pos.common.dao.ServiceDataFileLogMapper;
import com.lmis.pos.common.model.ServiceDataFileLog;
import com.lmis.pos.skuMaster.dao.PosSkuMasterMapper;
import com.lmis.pos.skuMaster.service.PosSkuMasterServiceInterface;

//@Transactional(rollbackFor = Exception.class)
@Service
public class PosSkuMasterServiceImpl implements PosSkuMasterServiceInterface {
	private static Log logger = LogFactory.getLog(PosSkuMasterServiceImpl.class);
	@Autowired
	private PosSkuMasterMapper mapper;
	
	@Resource(name="redisTemplate")
    private RedisTemplate<String, ?> redisTemplate;
	
	@Autowired
	private HttpSession session;
	
	@Autowired
	private ServiceDataFileLogMapper<ServiceDataFileLog> serviceDataFileLogMapper;

	@Override
	public LmisResult<?> getPosSkuMasterInfo(String prodCd, LmisPageObject pageObject) throws Exception {
		Page<Map<String, Object>> page = PageHelper.startPage(pageObject.getPageNum(), pageObject.getPageSize());
		mapper.getPosSkuMasterInfo(prodCd);
		LmisResult<Map<String, Object>> lmisResult = new LmisResult<Map<String, Object>>();
		lmisResult.setMetaAndData(page.toPageInfo());
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		return lmisResult;
	}

	@Override
	public LmisResult<?> deleteAllInfo() throws Exception {
		Integer resultCode = mapper.deleteAll();
		LmisResult<Map<String, Object>> lmisResult = new LmisResult<Map<String, Object>>();
		lmisResult.setData(resultCode);
		lmisResult.setCode(LmisConstant.RESULT_CODE_SUCCESS);
		lmisResult.setMessage("删除" + resultCode + "条数据");
		return lmisResult;
	}

	@Override
	public LmisResult<?> savePosSkuMasterInfo(List<Map<String, Object>> list, String path, String fileName)
			throws Exception {
		if(list == null || list.size() <= 0){
			LmisResult<?> result = new LmisResult<>(LmisConstant.RESULT_CODE_ERROR, "读取数据为0，请填写数据");
			return result;
		}
		
		// 生成文件任务记录
		String fileId = GetUuidForJava.getUuidForJava();
		final String updateBy = session.getAttribute("lmisUserId").toString();
		ServiceDataFileLog serviceDataFileLog = new ServiceDataFileLog();
		serviceDataFileLog.setId(fileId);
		serviceDataFileLog.setFileName(fileName);
		serviceDataFileLog.setFilePath(path);
		serviceDataFileLog.setDataCount((long) list.size());
		serviceDataFileLog.setOpsCount(0l);
		serviceDataFileLog.setOpsStatus(0);
		serviceDataFileLog.setCreateBy(updateBy);
		serviceDataFileLog.setUpdateBy(updateBy);
		int r = serviceDataFileLogMapper.create(serviceDataFileLog);
		
		@SuppressWarnings("unchecked")
		final ValueOperations<String, Object> opsForValue = 
				(ValueOperations<String, Object>) redisTemplate.opsForValue();
		
		// 设置默认进度
    	opsForValue.set(fileId,0l);
        // 清除表中数据
		mapper.deleteAll();

		List<Map<String, Object>> dataList = null;
		int realIndex = 0;
		int saveNum = 0;
		if (list != null && list.size() > 0) {
			int size = list.size();
			int limitSize = 3000;
			
			int resultCode = 0;
			if (limitSize < size) {
				int part = size / limitSize;
				logger.error("共有" + size + "条数据，分" + part + "批次插入");
				for (int pageIndex = 0; pageIndex < part; pageIndex++) {
					dataList = new ArrayList<>();
					realIndex = 0;
					for (int rowIndex = 0; rowIndex < limitSize; rowIndex++) {
						realIndex = rowIndex + pageIndex*limitSize;
						if(realIndex < list.size()){
							dataList.add(list.get(realIndex));
						}
					}
					logger.error("第" + (pageIndex + 1) + "次插入数据");
					opsForValue.set(fileId,(pageIndex + 1));
					resultCode = insertBatchData(dataList);
					saveNum += resultCode;
					dataList.clear();
				}
				if (!list.isEmpty()) {
					resultCode = insertBatchData(list);
					saveNum += resultCode;
				}
			} else {
				resultCode = insertBatchData(list);
				saveNum += resultCode;
			}
		}
		ServiceDataFileLog dataFileLog = new ServiceDataFileLog();
		dataFileLog.setId(fileId);
		dataFileLog.setOpsStatus(2);
		dataFileLog.setOpsCount((long) list.size());
		serviceDataFileLogMapper.update(dataFileLog);
		
		LmisResult<?> result = new LmisResult<>(LmisConstant.RESULT_CODE_SUCCESS, "");
		result.setData(fileId);
		return result;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public int insertBatchData(List<Map<String, Object>> dataList) {
		Integer r = 0;
		List<List<Map<String, Object>>> partition = Lists.partition(dataList, 100);
		for (List<Map<String, Object>> subList : partition) {
			r += mapper.savePosSkuMasterInfo(subList);
		}
		return r;
	}
	

}
