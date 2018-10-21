package com.jumbo.dao.vmi.etamData;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.etamData.EtamRtnData;


@Transactional
public interface EtamRtnDataDao extends GenericEntityDao<EtamRtnData, Long> {

    /*
     * @NativeUpdate void createEtamDataRtnSql(
     * 
     * @QueryParam(value="billCode") String billCode,
     * 
     * @QueryParam(value="whCode") Integer whCode,
     * 
     * @QueryParam(value="shopCode") Integer shopCode,
     * 
     * @QueryParam(value="boxNo") String boxNo,
     * 
     * @QueryParam(value="outBoundTime") String outBoundTime,
     * 
     * @QueryParam(value="skuCode") String skuCode,
     * 
     * @QueryParam(value="quantity") Long quantity,
     * 
     * @QueryParam(value="invStatus") String invStatus,
     * 
     * @QueryParam(value="userDef1") String userDef1,
     * 
     * @QueryParam(value="userDef2") String userDef2,
     * 
     * @QueryParam(value="userDef3") String userDef3,
     * 
     * @QueryParam(value="todoStatus") Integer todoStatus);
     */
    @NativeUpdate
    void createEtamDataRtnSql(@QueryParam(value = "billCode") String billCode, @QueryParam(value = "whCode") Integer whCode, @QueryParam(value = "shopCode") Integer shopCode, @QueryParam(value = "boxNo") String boxNo,
            @QueryParam(value = "outBoundTime") String outBoundTime, @QueryParam(value = "invStatus") String invStatus, @QueryParam(value = "todoStatus") Integer todoStatus);

    @NativeUpdate
    void deleteEtamRtnByTodoStatus(@QueryParam(value = "todoStatus") Integer todoStatus);

    @NativeUpdate
    void updateEtamRtnByTodoStatus(@QueryParam(value = "createStatus") Integer createStatus, @QueryParam(value = "todoStatus") Integer todoStatus);

    @NativeQuery
    List<EtamRtnData> findEtamRtnByCreateStatus(@QueryParam(value = "createStatus") Integer createStatus, BeanPropertyRowMapperExt<EtamRtnData> beanPropertyRowMapper);

    @NamedQuery
    List<EtamRtnData> findAllEtamRtnDatas();

    @NativeUpdate
    void updateFinishById(@QueryParam(value = "id") Long id);

    @NativeQuery
    Integer findRtnByBoxNoAndBillCodeAndSku(@QueryParam(value = "billCode") String billCode, @QueryParam(value = "boxNo") String boxNo, @QueryParam(value = "skuCode") String skuCode, SingleColumnRowMapper<Integer> beanPropertyRowMapper);
}
