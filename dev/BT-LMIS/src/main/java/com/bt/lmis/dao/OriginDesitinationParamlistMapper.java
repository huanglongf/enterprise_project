package com.bt.lmis.dao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;

/**
* @ClassName: OriginDesitinationParamlistMapper
* @Description: TODO(OriginDesitinationParamlistMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface OriginDesitinationParamlistMapper<T> extends BaseMapper<T> {

  public	Map selectCounts(Map param);
	
  public   Map getInfoByNm(Map param);	
 
  public   Map getInfoAndTypeIsNull(Map param);	
  
  public  Map  findTableIdByTBNm(Map tbnm);
  
  public  Integer isExisitRecord(Map param);
  
  public  Map  findContract(Map tbnm);
  
  public  Map  findClient(Map tbnm);
  
  public  Map  findStore(Map tbnm);
  
  public Map TRUNCATE(Map tbnm);
  
  public void  insertCheck(Map param);
  
  public ArrayList<?> getSame();
  
  public List<Map<String,Object>> checkCTF(Map param);
  
  public List<Map<String,Object>> checkWLCTF(Map param);
}
