package com.lmis.jbasis.area.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.lmis.framework.baseDao.BaseMapper;
import com.lmis.jbasis.area.model.JbasisArea;
import com.lmis.jbasis.area.model.ViewJbasisArea;
import com.lmis.jbasis.warehouse.model.JbasisWarehouse;

/** 
 * @ClassName: JbasisAreaMapper
 * @Description: TODO(地址库表（基础数据）DAO映射接口)
 * @author codeGenerator
 * @date 2018-04-11 14:23:33
 * 
 * @param <T>
 */
@Mapper
@Repository
public interface JbasisAreaMapper<T extends JbasisArea> extends BaseMapper<T> {
    
    /**
     * @Title: retrieveViewJbasisArea
     * @Description: TODO(查询view_jbasis_area)
     * @param viewJbasisArea
     * @return: List<ViewJbasisArea>
     * @author: codeGenerator
     * @date: 2018-04-11 14:23:33
     */
    List<JbasisArea> retrieveJbasisArea(T viewJbasisArea);
    
    List<JbasisArea> retrieveByCode(JbasisArea JbasisArea);
    
    String getChildLst(T j);

    int updateByPid(T area);
    
    /**
     * 根据省市区名称查询名称是否合理
     * @param JbasisWarehouse
     * @return
     */
    List<JbasisArea> retrieveByNames(JbasisWarehouse jbasisWarehouse);
}
