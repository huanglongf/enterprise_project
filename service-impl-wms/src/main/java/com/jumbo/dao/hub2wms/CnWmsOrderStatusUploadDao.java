package com.jumbo.dao.hub2wms;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.hub2wms.threepl.CnWmsOrderStatusUpload;

@Transactional
public interface CnWmsOrderStatusUploadDao extends GenericEntityDao<CnWmsOrderStatusUpload, Long> {

    /**
     * 查询未上传订单的状态信息（排除状态为iglore_upload的）
     * 
     * @param uploadStatus
     * @return
     */
    @NamedQuery
    List<CnWmsOrderStatusUpload> getByUploadStatus(@QueryParam(value = "uploadStatus") String uploadStatus);

    /**
     * 通过订单号查询未上传订单状态
     * 
     * @param uploadStatus
     * @return
     */
    @NamedQuery
    List<CnWmsOrderStatusUpload> getByOrderCodeAndUploadStatus(@QueryParam(value = "orderCode") String orderCode, @QueryParam(value = "uploadStatus") String uploadStatus);
}
