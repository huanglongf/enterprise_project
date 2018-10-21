package com.jumbo.wms.manager.pda;

import java.text.ParseException;
import java.util.Map;

import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;

public interface PdaShelvesManager {

    Map<String, Object> initMongodbWhShelvesInfo(String code, OperationUnit op, User user);

    /**
     * 初始化残次品
     * 
     * @param barCode
     * @param code
     * @param op
     * @param user
     * @return
     */
    void initMongodbWhShelvesInfoIncomplete(String code, OperationUnit op, User user);



    public String checkLocIsInv(Long ouId, String locationCode);

    Map<String, Object> verifyAndRecommend(String barCode, String code, OperationUnit op, User user);

    Map<String, Object> scanSku(String barCode, String code, String locationCode, OperationUnit op, User user) throws Exception;// 扫描商品

    /**
     * 扫描残次品
     */
    Map<String, Object> scanSkuIncomplete(String barCode, String code, String locationCode, OperationUnit op, User user) throws Exception;// 扫描商品

    /**
     * 取消商品还原
     */
    Map<String, Object> cancelSku(String barCode, String code, String locationCode, OperationUnit op, User user);

    /**
     * 取消商品还原(残次品)
     */
    Map<String, Object> cancelSkuIncomplete(String barCode, String code, String locationCode, OperationUnit op, User user);

    /**
     * 库位上架
     */
    Map<String, Object> locationShelves(String barCode, String code, String locationCode, OperationUnit op, User user, String type) throws Exception;

    /**
     * 库位上架(残次品)
     */
    Map<String, Object> locationShelvesDmgCode(String barCode, String code, String locationCode, OperationUnit op, User user, String type) throws Exception;


    /**
     * 扫描商品插入操作明细(良品批量)
     * 
     * @param barCode
     * @param code
     * @param locationCode
     * @param op
     * @param user
     * @return
     * @throws Exception
     */
    Map<String, Object> scanSku2(String barCode, String code, String locationCode, OperationUnit op, User user, Long num) throws Exception;

    /**
     * 验证库位
     */
    Map<String, Object> verifyLocation(String locationCode, OperationUnit op, User user);

    String initMongodbWhShelvesInfoShou(String code, OperationUnit currentOu, User user);

    // 验证商品手动上架
    String checkSkuShou(String code, String skuBarcode, Long num, String eDate, String sDate, OperationUnit currentOu, User user) throws ParseException;

    // 手动上架
    Map<String, Object> locationShelvesShou(Long num, String skuBarcode, String code, String locationCode, String eDate, OperationUnit currentOu, User user, Object object) throws Exception;

    // 验证商品属性
    String checkSkuShouPro(Long num, String code, String skuBarcode, OperationUnit currentOu, User user);
    
    /**
     * 验证作业单状态是否完成
     * @param slipCode
     * @param ouId
     * @return
     */
    String checkStaStatus(String slipCode,Long ouId);


}
