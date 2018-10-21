package com.lmis.pos.skuMaster.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.framework.baseInfo.LmisResult;

@Transactional(rollbackFor=Exception.class)
@Service
public interface PosSkuMasterServiceInterface {

	LmisResult<?> getPosSkuMasterInfo(String prodCd, LmisPageObject pageObject) throws Exception;
	
	LmisResult<?> deleteAllInfo() throws Exception;
	
	LmisResult<?> savePosSkuMasterInfo(List<Map<String, Object>> list, String name, String fileName) throws Exception;
}
