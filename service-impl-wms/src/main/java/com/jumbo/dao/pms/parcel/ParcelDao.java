package com.jumbo.dao.pms.parcel;

import java.util.Map;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.pms.model.Parcel;

@Transactional
public interface ParcelDao extends GenericEntityDao<Parcel, Long> {
	
    /**
     * 根据运单号&物流商查询包裹
     * 
     * @param mailNo
     * @param transType
     * @return
     */
    @NativeQuery(model = Parcel.class)
    Parcel findByLpcodeAndMailNo(@QueryParam("mailNo") String mailNo, @QueryParam("lpcode") String lpcode, @QueryParam("outerOrderCode") String outerOrderCode);
    
    @NamedQuery
    Parcel findByCode(@QueryParam("code") String code);
    
    @NamedQuery
    Parcel findByOuterOrderCode(@QueryParam("outerOrderCode") String outerOrderCode);
    
    @NativeUpdate
    int updateByParams(@QueryParam Map<String, Object> params);
    
    @NativeUpdate
    int updateMailByLpcodeAndMailNo(@QueryParam("slipMailNo") String slipMailNo, @QueryParam("slipLpcode") String slipLpcode,@QueryParam("mailNo") String mailNo,  @QueryParam("lpcode") String lpcode, @QueryParam("remark") String remark);
    
}