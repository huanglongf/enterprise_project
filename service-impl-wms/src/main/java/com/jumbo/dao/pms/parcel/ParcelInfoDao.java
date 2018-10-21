package com.jumbo.dao.pms.parcel;

import java.util.Date;
import java.util.List;
import java.util.Map;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.pms.model.command.ParcelInfoQueryCommand;
import com.jumbo.pms.model.parcel.ParcelInfo;

@Transactional
public interface ParcelInfoDao extends GenericEntityDao<ParcelInfo, Long> {

    /**
     * 根据运单号&物流商查询包裹
     * 
     * @param mailNo 运单号
     * @param lpCode 物流商编码
     * @param platformOrderCode 平台单号
     * @return
     */
    @NativeQuery(model = ParcelInfo.class)
    ParcelInfo findByLpcodeAndMailNoAndPFCode(@QueryParam("mailNo") String mailNo, @QueryParam("lpcode") String lpcode, @QueryParam("platformOrderCode") String platformOrderCode);
    
    @NativeQuery(model = ParcelInfoQueryCommand.class)
    ParcelInfoQueryCommand findByCode(@QueryParam("code") String code);
    
    @NativeQuery(model = ParcelInfoQueryCommand.class)
    ParcelInfoQueryCommand findByParams(@QueryParam Map<String, Object> params);
    
    @NamedQuery
    List<ParcelInfo> findByShipmentId(@QueryParam("shipmentId") Long shipmentId);
    
    @NativeUpdate
    int updateByCode(@QueryParam("code") String code, @QueryParam("signedTime") Date signedTime, @QueryParam("finishedTime") Date finishedTime, @QueryParam("status") Integer status);
    
    @NativeUpdate
    int updateMailNoByCode(@QueryParam("code") String code, @QueryParam("mailNo") String mailNo, @QueryParam("lpcode") String lpcode);
    
}