package com.bt.lmis.tools.compareData.thread;

import com.bt.lmis.base.spring.SpringUtils;
import com.bt.lmis.datasource.DataSourceContextHolder;
import com.bt.lmis.tools.compareData.dao.CompareDataMapper;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class UseFuture<T> implements Callable<List<Map<String,Object>>>{


	private List<Object> ancodes;

    public UseFuture( List<Object> ancodes){
        this.ancodes = ancodes;
    }
    @SuppressWarnings("unchecked")
	@Override
    public List<Map<String,Object>> call()  {
        CompareDataMapper<T> compareDataMapper = SpringUtils.getBean("compareDataMapper");
        DataSourceContextHolder.setDbType("ds_2");
        List<Map<String, Object>> maps = compareDataMapper.selectFromWms(ancodes);
        return maps;
    }

}
