/**
 * EventObserver * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import loxia.support.excel.ExcelReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.util.StringUtil;
import com.jumbo.webservice.nike.manager.NikeOrderManager;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.NikeVMIInboundCommand;

@Service("whmanagerNotTransactional")
public class WhmanagerNotTransactionalImpl extends BaseManagerImpl implements WhmanagerNotTransactional {

    /**
     * 
     */
    private static final long serialVersionUID = 7008017413265820117L;

    @Resource(name = "nikeVmiinboundReader")
    private ExcelReader nikeVmiinboundReader;// 收货暂存区导入
    @Autowired
    private BiChannelDao companyShopDao;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private NikeOrderManager nikeOrderManager;
    @Autowired
    private WareHouseManager wareHouseManager;

    @SuppressWarnings({"unchecked"})
    public void importCreateNikeVMIInbound(File staFile, Long ouid, User creator, Long shopId, Map<String, List<NikeVMIInboundCommand>> errorMap, Map<String, List<NikeVMIInboundCommand>> successMap) {
        // 店铺
        BiChannel companyShop = companyShopDao.getByPrimaryKey(shopId);
        if (companyShop == null) {
            throw new BusinessException(ErrorCode.NIKE_SHOP_NOT_FOUND);
        }
        // 仓库
        OperationUnit whOu = ouDao.getByPrimaryKey(ouid);
        if (whOu == null) {
            throw new BusinessException(ErrorCode.NIKE_WRAEHOUSE_NOT_FOUND);
        }
        // 保存文件数据
        Map<String, Object> beans = new HashMap<String, Object>();
        // 保存SKU信息
        Map<String, Sku> skuMap = new HashMap<String, Sku>();
        try {
            nikeVmiinboundReader.readAll(new FileInputStream(staFile), beans);
        } catch (FileNotFoundException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.NIKE_VMI_INBOUND_READER);
        }
        List<NikeVMIInboundCommand> list = (List<NikeVMIInboundCommand>) beans.get("nike");
        boolean isNoSkuError = false;
        for (int i = 0; i < list.size(); i++) {
            boolean isError = false;
            NikeVMIInboundCommand com = list.get(i);
            com.setNo(i + 2);
            if (StringUtil.isEmpty(com.getExternOrderKey())) {
                isError = true;
                com.getErrors().put(ErrorCode.NIKE_VMI_INBOUND_ORDERKEY_ERROR, new Object[] {i + 2});
            }
            if (StringUtil.isEmpty(com.getUpc())) {
                isError = true;
                com.getErrors().put(ErrorCode.NIKE_VMI_INBOUND_UPC_NULL, new Object[] {i + 2});
            } else {
                if (skuMap.containsKey(com.getUpc())) {
                    com.setSku(skuMap.get(com.getUpc()));
                } else {
                    // 查询对应UPC的商品
                    String upc = "00" + com.getUpc();
                    Sku sku = wareHouseManager.getByExtCode2AndCustomerAndShopId(upc, companyShop.getCustomer().getId(), companyShop.getId());
                    if (sku == null) {
                        baseinfoManager.sendMsgToOmsCreateSku(upc, companyShop.getVmiCode());
                        isNoSkuError = true;
                        continue;
                    }
                    if (sku != null) {
                        skuMap.put(com.getUpc(), sku);
                        com.setSku(sku);
                    }
                }
            }
            if (StringUtil.isEmpty(com.getLabelNo())) {
                isError = true;
                com.getErrors().put(ErrorCode.NIKE_VMI_INBOUND_LABELNO_ERROR, new Object[] {i + 2});
            }
            if (StringUtil.isEmpty(com.getQtyShipped())) {
                isError = true;
                com.getErrors().put(ErrorCode.NIKE_VMI_INBOUND_QTY_ERROR, new Object[] {i + 2});
            } else {
                try {
                    Long.valueOf(com.getQtyShipped().trim());
                } catch (Exception e) {
                    isError = true;
                    com.getErrors().put(ErrorCode.NIKE_VMI_INBOUND_QTY_INFO_ERROR, new Object[] {i + 2, com.getQtyShipped()});
                }
            }
            String labelNo = com.getLabelNo();
            if (isError) {// 保存错误数据
                if (StringUtil.isEmpty(com.getLabelNo())) {
                    labelNo = "";
                }
                if (errorMap.containsKey(labelNo)) {
                    errorMap.get(labelNo).add(com);
                } else {
                    List<NikeVMIInboundCommand> labelNoErrorList = new ArrayList<NikeVMIInboundCommand>();
                    labelNoErrorList.add(com);
                    errorMap.put(labelNo, labelNoErrorList);
                }
                // 如果一个单里面存在错误的数据 此单将不会执行
                if (successMap.containsKey(labelNo)) {
                    successMap.remove(labelNo);
                }
            } else if (!errorMap.containsKey(labelNo)) {// 记录数据正确
                                                        // (判断在错误数据单据当中是否存在此单
                                                        // 如果存在就不做保存)
                if (successMap.containsKey(labelNo)) {
                    successMap.get(labelNo).add(com);
                } else {
                    List<NikeVMIInboundCommand> labelNoSuccessList = new ArrayList<NikeVMIInboundCommand>();
                    labelNoSuccessList.add(com);
                    successMap.put(labelNo, labelNoSuccessList);
                }
            }
        }
        if (isNoSkuError) {
            throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
        }
        // 判断是否存在成功的单据
        if (successMap.size() > 0) {
            Map<String, List<NikeVMIInboundCommand>> tempMap = new HashMap<String, List<NikeVMIInboundCommand>>(successMap);
            for (Entry<String, List<NikeVMIInboundCommand>> entry : tempMap.entrySet()) {
                try {
                    nikeOrderManager.createNikeVMIInbound(entry.getKey(), entry.getValue(), companyShop, whOu, creator);
                } catch (Exception e) {
                    log.error("", e);
                    errorMap.put(entry.getKey(), entry.getValue());
                    successMap.remove(entry.getKey());
                }
            }
        }
    }
}
