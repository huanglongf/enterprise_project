/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
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
package com.jumbo.wms.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.authorization.OperationUnitTypeDao;
import com.jumbo.dao.authorization.UserGroupDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.rfid.SkuRfidDao;
import com.jumbo.dao.rfid.SkuRfidLogDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaCheckDetialDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.authorization.UserGroup;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuRfid;
import com.jumbo.wms.model.baseinfo.SkuRfidLog;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.PickingListStatus;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.TransactionDirection;

import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;

/**
 * 构造下拉列表
 * 
 * @author wanghua
 * 
 */
@Service("dropDownListManager")
public class DropDownListManagerImpl implements DropDownListManager {
    private static final long serialVersionUID = -8781882796680205718L;

    protected static final org.slf4j.Logger logger = LoggerFactory.getLogger(BaseQueryThreadPoolManagerImpl.class);
    @Autowired
    private OperationUnitTypeDao operationUnitTypeDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private UserGroupDao userGroupDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private StaCheckDetialDao staCheckDetialDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private SkuRfidDao skuRfidDao;
    @Autowired
    private SkuRfidLogDao skuRfidLogDao;

    /**
     * 查找系统中的可用组织类型列表
     * 
     * @param containsRoot 组织类型是否包括集团,包括true,不包括null/false
     * @return
     */
    public List<OperationUnitType> findOperationUnitTypeList(Boolean containsRoot) {
        return operationUnitTypeDao.findOperationUnitTypeList(containsRoot, new BeanPropertyRowMapperExt<OperationUnitType>(OperationUnitType.class));
    }

    /**
     * UserGroup下拉菜单
     */
    @Transactional(readOnly = true)
    public List<UserGroup> findUserGroupList() {
        return userGroupDao.findUserGroupList();
    }

    /**
     * 使用中=true,已禁用=false下拉列表
     * 
     * @return
     */
    @Transactional(readOnly = true)
    public List<ChooseOption> findStatusChooseOptionList() {
        return chooseOptionDao.findOptionListByCategoryCode(Constants.CHOOSEOPTION_CATEGORY_CODE_STATUS);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.jumbo.wms.manager.DropDownListManager#findPickingListStatusForVerify()
     */
    public List<ChooseOption> findPickingListStatusForVerify() {
        List<ChooseOption> list = chooseOptionDao.findOptionListByCategoryCode(Constants.CHOOSEOPTION_CATEGORY_CODE_PICKINGLIST_STATUS), result = new ArrayList<ChooseOption>();
        for (ChooseOption each : list) {
            if (PickingListStatus.PACKING.getValue() == Integer.valueOf(each.getOptionKey().toString()) || PickingListStatus.PARTLY_RETURNED.getValue() == Integer.valueOf(each.getOptionKey().toString())) {
                result.add(each);
            }
        }
        return result;
    }

    public List<InventoryStatus> findInvStatusListByCompany(Long whId, Sort[] sorts) {
        return inventoryStatusDao.findInvStatusListByCompany(whId, sorts);
    }

    public List<OperationUnit> findWarehouseOuListByComOuId(Long comOuId, Sort[] sorts) {
        return operationUnitDao.findWarehouseOuListByComOuId(comOuId, new BeanPropertyRowMapperExt<OperationUnit>(OperationUnit.class), sorts);
    }

    public List<ChooseOption> findStaStatusChooseOptionList() {
        return chooseOptionDao.findOptionListByCategoryCode(Constants.CHOOSEOPTION_CATEGORY_CODE_STA_STATUS);
    }

    public List<ChooseOption> findStaInboundStoreModeChooseOptionList() {
        return chooseOptionDao.findOptionListByCategoryCode(Constants.CHOOSEOPTION_CATEGORY_CODE_WAREHOUSE_INMODE);
    }

    public Boolean nikeRFIDByCode(Long staId, String barCode, String nikeRFIDCode) {
        Boolean flag = true;
        Sku sku = skuDao.getSkuByBarcode(barCode);
        String[] code = nikeRFIDCode.split("⊥");
        if (sku != null && sku.getRfidNumber() != null) {
            if (code.length != sku.getRfidNumber()) {
                flag = false;
            }
        }
        List<String> staCheckList = staCheckDetialDao.findRfidAllBySkuIdStaId(staId, barCode, new SingleColumnRowMapper<String>(String.class));

        List<String> staCheckCode = new ArrayList<String>();
        for (String staCheck : staCheckList) {
            String[] splitcode = staCheck.split("⊥");
            for (String code1 : splitcode) {
                staCheckCode.add(code1);
            }
        }
        if (staCheckList != null && staCheckList.size() > 0) {
            for (String code1 : code) {
                if (!staCheckCode.contains(code1)) {
                    flag = false;
                    break;
                }
            }
        } else {
            flag = false;
        }

        if (flag) {
            StockTransApplication sta = staDao.getByPrimaryKey(staId);
            UUID uuid = UUID.randomUUID();
            String str = uuid.toString();
            String rfidBatch = str.replace("-", "");
            for (String rfid : code) {
                SkuRfid skuRfid = new SkuRfid();
                skuRfid.setRfidCode(rfid);
                skuRfid.setLastModifyTime(new Date());
                skuRfid.setOuId(sta.getMainWarehouse().getId());
                skuRfid.setSkuId(sku.getId());
                skuRfid.setStaId(null);
                skuRfid.setVersion(0);
                skuRfid.setRfidBatch(rfidBatch);
                skuRfidDao.save(skuRfid);
            }
            // 日志记录
            SkuRfidLog skuRfidLog = new SkuRfidLog();
            skuRfidLog.setDirection(TransactionDirection.valueOf(1));
            skuRfidLog.setSkuId(sku.getId());
            skuRfidLog.setOuId(sta.getMainWarehouse().getId());
            skuRfidLog.setRfidCode(nikeRFIDCode.toString());
            skuRfidLog.setStaId(sta.getId());
            skuRfidLog.setTransactionTime(new Date());
            skuRfidLogDao.save(skuRfidLog);
        }
        return flag;
    }


    public List<ChooseOption> findPickingListStatusOptionList() {
        return chooseOptionDao.findOptionListByCategoryCode(Constants.CHOOSEOPTION_CATEGORY_CODE_PICKINGLIST_STATUS);
    }

    public List<OperationUnitType> findChildOUPList(Long parentId) {
        return operationUnitTypeDao.findChildOUPList(parentId, new BeanPropertyRowMapperExt<OperationUnitType>(OperationUnitType.class));
    }

    public List<ChooseOption> findMsgOutboundOrderStatusOptionList() {
        return chooseOptionDao.findOptionListByCategoryCode(Constants.CHOOSEOPTION_CATEGORY_CODE_MSGOUTBOUNDORDER_STATUS);
    }

    public List<ChooseOption> findMsgInboundOrderStatusOptionList() {
        return chooseOptionDao.findOptionListByCategoryCode(Constants.CHOOSEOPTION_CATEGORY_CODE_MSGINBOUNDORDER_STATUS);
    }

    public static void main(String[] args) {
        List<String> l = new ArrayList<String>();
        l.add("E28068940000400430E2313D,E28068940000400430E2493D,0100");
        if (l.get(0).contains("0100")) {
            System.out.println("1");
        } else {
            System.out.println("2");
        }
    }

}
