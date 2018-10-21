package com.jumbo.dao.vmi.warehouse;



import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.warehouse.MsgRtnReturnLine;

@Transactional
public interface MsgRtnReturnLineDao extends GenericEntityDao<MsgRtnReturnLine, Long> {


    @NativeUpdate
    void saveByIDSSHPLine(@QueryParam(value = "status") Integer status);


    @NamedQuery
    List<MsgRtnReturnLine> queryByReturnId(@QueryParam(value = "rtnId") Long rtnId);

    @NativeUpdate
    void saveForEtamLine(@QueryParam(value = "skuId") Long skuId, @QueryParam(value = "invStatusId") Long invStatusId, @QueryParam(value = "quantity") Long quantity, @QueryParam(value = "staCode") String staCode);

    @NamedQuery
    MsgRtnReturnLine findLineBySlipCodeAndSkuBarCode(@QueryParam(value = "slipCode") String slipCode, @QueryParam(value = "skuId") Long skuId);

    @NamedQuery
    List<MsgRtnReturnLine> findReturnLinesByReturnId(@QueryParam(value = "returnId") Long returnId);

}
