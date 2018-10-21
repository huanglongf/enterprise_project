package com.bt.lmis.service.impl;
import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.bt.lmis.dao.DifferenceMatchingMapper;
import com.bt.lmis.service.DifferenceMatchingService;
import com.bt.lmis.utils.DifferenceMatchingUtils;
import com.bt.utils.CommonUtils;

@Service
public class DifferenceMatchingServiceImpl implements DifferenceMatchingService {
	
	@Autowired
	private DifferenceMatchingMapper<T> differenceMatchingMapper;
	
	@Override
	public JSONObject differentMatching(HttpServletRequest request, JSONObject result) throws Exception {
		result= new JSONObject();
		result.put("result_code", "SUCCESS");
		result.put("path", CommonUtils.getAllMessage("config", "COMMON_DOWNLOAD_MAP")+ DifferenceMatchingUtils.generateDifferenceMatching(differenceMatchingMapper.selectById(Integer.parseInt(request.getParameter("id")))));
		return result;
		
	}

	@Override
	public Integer judgeFileNameDuplicate(String fileName) throws Exception {
		return differenceMatchingMapper.judgeFileNameDuplicate(fileName);
		
	}
	
}