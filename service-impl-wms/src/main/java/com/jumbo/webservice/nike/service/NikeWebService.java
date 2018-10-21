package com.jumbo.webservice.nike.service;

import java.util.List;

import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.util.StringUtil;
import com.jumbo.webservice.nike.command.InventoryObj;
import com.jumbo.webservice.nike.command.NikeOrderObj;
import com.jumbo.webservice.nike.command.NikeOrderResultObj;
import com.jumbo.webservice.nike.manager.NikeOrderManager;

/**
 * Please modify this class to meet your needs This class is not complete
 */

@WebService(serviceName = "NikeService", targetNamespace = "http://nike.webservice.service.erry.com/")
public class NikeWebService {

    private static final Logger log = LoggerFactory.getLogger(NikeWebService.class);

    @Autowired
    private NikeOrderManager nikeOrderManager;

    /**
     * 接收NIKE单据
     * 
     * @param unionId
     * @param warehouseCode
     * @param nikeOrderObj
     * @return
     */
    public Integer receiveNikeOrder(String unionId, String warehouseCode, NikeOrderObj nikeOrderObj) {
        log.debug("---nike order code---" + nikeOrderObj.getCode());
        Integer status = 5; // 成功
        if (!Constants.UNION_ID_FOR_NIKE.equals(unionId)) {
            status = 99; // unionId错误
            return status;
        }
        if (!warehouseCode.equals("SHVMI01")) {
            status = 31;
            return status;
        }
        try {
            // status=nikeOrderManager.receiveNikeOrder(nikeOrderObj,Constants.NIKE_SHOP_ID,warehouseCode);
            status = nikeOrderManager.saveNikeOrder(nikeOrderObj);
        } catch (BusinessException e) {
            log.error("=============BusinessException  error :{}=================", e.getErrorCode());
            status = e.getErrorCode() - 19900;
        } catch (Exception e) {
            status = 100;
            log.error("=============Exception  error =================");
            log.error("", e);
        }
        return status;
    }

    /**
     * 反馈处理结果
     * 
     * @param unionId
     * @param code 前置单据号
     * @param type
     * @param warehouseCode
     * @return
     */
    public NikeOrderResultObj getOrderResultByCode(String unionId, String code, String type, String warehouseCode) {
        if (!Constants.UNION_ID_FOR_NIKE.equals(unionId)) {
            return null;
        }
        try {
            NikeOrderResultObj obj = nikeOrderManager.getNikeOrderResultByCode(warehouseCode, code, type);
            return obj;
        } catch (Exception e) {
            if (e instanceof BusinessException) {
                log.error("BusinessException : " + ((BusinessException) e).getErrorCode() + "");
            } else {
                log.error("", e);
            }
            return null;
        }
    }

    /**
     * 反馈库存信息
     * 
     * @param unionId
     * @param warehouseCode
     * @return
     */
    public List<InventoryObj> getNikeInventory(String unionId, String warehouseCode) {
        if (!Constants.UNION_ID_FOR_NIKE.equals(unionId)) {
            return null;
        }
        return nikeOrderManager.getAvailableInventoryByWarehouse(warehouseCode);
    }

    /**
     * 根据商品信息 反馈商品库存
     * 
     * @param unionId
     * @param supplierCode
     * @return
     */
    public List<InventoryObj> getNikeInventoryBySku(String unionId, String supplierCode) {
        if (!Constants.UNION_ID_FOR_NIKE.equals(unionId) || StringUtil.isEmpty(supplierCode)) {
            return null;
        }
        return nikeOrderManager.getAvailableInventoryBySku(supplierCode);
    }

    /**
     * 单据取消接口 1.取消作业单,单据类型：外部订单销售出库、换货出库(限类型外部订单)、换货入库(限类型外部订单) 情况 (1) 单据没有接收-反馈未找到单据 (2)
     * 单据接收未创建-删除队列中对应单据反馈成功 (3) 单据创建状态正常-取消反馈成功 (4) 单据已经取消-不处理反馈已经取消
     */
    public Integer cancelOrder(String unionId, String orderCode, Integer type) {
        Integer status = 99;
        try {
            return nikeOrderManager.cancelOrder(unionId, orderCode, type);
        } catch (Exception e) {
            return status;
        }
    }

}
