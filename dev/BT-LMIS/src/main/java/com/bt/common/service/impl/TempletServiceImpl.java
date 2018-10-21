package com.bt.common.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bt.common.controller.param.Parameter;
import com.bt.common.controller.result.Result;
import com.bt.common.dao.TempletMapper;
import com.bt.common.model.TableColumnConfig;
import com.bt.common.service.TempletService;
import com.bt.lmis.page.QueryResult;
import com.bt.utils.CommonUtils;

/** 
 * @ClassName: TempletServiceImpl
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年8月17日 上午11:04:22 
 * 
 * @param <T>
 */
@Service
public class TempletServiceImpl<T> implements TempletService<T> {
	
	private static final Logger logger = Logger.getLogger(TempletServiceImpl.class);
	@Autowired
	private TempletMapper<T> templetMapper;

	@Override
	public List<Map<String, Object>> listTableColumnConfig(Parameter parameter) {
		return templetMapper.listTableColumnConfig(parameter);
	}
	
	@Override
	public List<Map<String, Object>> listTableColumn(Parameter parameter) {
		return templetMapper.listTableColumn(parameter);
	}

	@Override
	@Transactional
	public Result saveTableColumnConfig(Parameter parameter) {
		try {
			// 删除历史记录
			templetMapper.delTableColumnConfig(parameter);
			// 新增当前配置
			String[] temp = (String[]) parameter.getParam().get("tableColumnConfig[]");
			if(CommonUtils.checkExistOrNot(temp)) {
				List<TableColumnConfig> tableColumnConfig = new ArrayList<TableColumnConfig>();
				for(int i = 0; i < temp.length; i++) {
					tableColumnConfig.add(new TableColumnConfig(
							parameter.getCurrentAccount().getId().toString(),
							parameter.getCurrentAccount().getId().toString(),
							parameter.getParam().get("tableName").toString(),
							temp[i],
							i+1
							));
				}
				templetMapper.saveTableColumnConfig(tableColumnConfig);
				
			}
			return new Result(true, "操作成功");
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			return new Result(false, "操作失败，失败原因："+CommonUtils.getExceptionStack(e));
			
		}
		
	}
	
	@Override
	public QueryResult<Map<String, Object>> searchTempletTest(Parameter parameter) {
		return new QueryResult<Map<String,Object>>(templetMapper.searchTempletTest(parameter), templetMapper.countTempletTest(parameter));
	}

	@Override
	public List<Map<String, Object>> loadingTableColumnConfig(Parameter parameter) {
		List<Map<String, Object>> tableColumnConfig = templetMapper.listTableColumnConfig(parameter);
		if(!CommonUtils.checkExistOrNot(tableColumnConfig)) {
			tableColumnConfig = templetMapper.listTableColumn(parameter);
		}
		return tableColumnConfig;
	}

}