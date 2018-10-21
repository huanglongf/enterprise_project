package com.jumbo.dao.warehouse;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.pda.PdaOrderLineSn;
import com.jumbo.wms.model.pda.PdaOrderLineSnCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

@Transactional
public interface PdaOrderLineSnDao extends GenericEntityDao<PdaOrderLineSn, Long> {

    /**
     * 根据明细查寻SN
     * 
     * @param pdaOrderLineId
     * @return
     */
    @NamedQuery
    List<PdaOrderLineSn> getByPdaOrderLine(@QueryParam(value = "pdaOrderLineId") Long pdaOrderLineId);

    /**
     * 
     * @param start
     * @param pageSize
     * @param id
     * @param sorts
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<PdaOrderLineSnCommand> findEchoSn(@QueryParam("pdaOrderid") Long pdaOrderid, RowMapper<PdaOrderLineSnCommand> row);

    /**
     * PDA 获取上传的sn号 KJL
     * 
     * @param id
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<PdaOrderLineSn> findSnByPdaOrderLineId(@QueryParam("plId") Long id, BeanPropertyRowMapper<PdaOrderLineSn> beanPropertyRowMapper);

    /**
     * PDA 根据orderId获取上传的sn
     * 
     * @param id2
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<String> findSnByPdaOrderId(@QueryParam("plId") Long id2, SingleColumnRowMapper<String> singleColumnRowMapper);


    @NativeQuery
    List<PdaOrderLineSn> findPdaLineSnListById(@QueryParam("plId") Long id, BeanPropertyRowMapper<PdaOrderLineSn> beanPropertyRowMapper);

}
