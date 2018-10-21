package com.bt.workOrder.dao;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.page.QueryParameter;
import com.bt.lmis.page.QueryResult;
import com.bt.workOrder.controller.form.GenerationRuleQueryParam;
import com.bt.workOrder.model.GenerationRule;

/**
* @ClassName: GenerationRuleMapper
* @Description: TODO(GenerationRuleMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface GenerationRuleMapper<T> extends BaseMapper<T> {
	
	public  List<GenerationRule> findAll(QueryParameter qr);
		
	public  List<T> findAllData(QueryParameter qr);
	public  List<Map<String, Object>> findAllData2(QueryParameter qr);

	public  List<GenerationRule> findGenerationRule(QueryParameter qr);
	
	int selectCount(QueryParameter qr);
  
	int CanAddData(QueryParameter qr);
	
    List<Map<String,Object>>	CanAddData_se(QueryParameter qr);

	public void deletRule(GenerationRuleQueryParam queryParam);
	
	List<Map<String,Object>>checkRuleIsInUse(QueryParameter qr);
	
	int CanAddData_th(QueryParameter qr);

	public List<T>  findAllDataUpdate(GenerationRuleQueryParam q1);

	public int selectCount2(QueryParameter qr);
}
