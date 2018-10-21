package com.lmis.jbasis.expenses.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.framework.baseInfo.LmisPageObject;
import com.lmis.jbasis.expenses.model.TbExpressReturnStorage;
import com.lmis.jbasis.expenses.model.TbOperationfeeData;
import com.lmis.jbasis.expenses.model.TbStorageExpendituresData;
import com.lmis.jbasis.expenses.model.ViewTbWarehouseExpressData;

@Mapper
@Repository
public interface TbExpensesMapper extends BaseMapper{

    List<TbStorageExpendituresData> executeSelect(@Param("sql") String arg0);


}
