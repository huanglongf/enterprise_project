package com.bt.lmis.dao;
import java.util.List;
import java.util.Map;

import com.bt.lmis.code.BaseMapper;
import com.bt.lmis.model.PerationSettlementFixed;

/**
* @ClassName: PerationSettlementFixedMapper
* @Description: TODO(PerationSettlementFixedMapper)
* @author Yuriy.Jiang
*
* @param <T>
*/ 
public interface PerationSettlementFixedMapper<T> extends BaseMapper<T> {

	List<Map<String, Object>> findByEntity(PerationSettlementFixed entity);
}
