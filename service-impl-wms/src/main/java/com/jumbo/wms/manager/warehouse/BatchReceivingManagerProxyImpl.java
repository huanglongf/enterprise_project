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
 */
package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import loxia.support.excel.ExcelReader;
import loxia.support.excel.ReadStatus;
import loxia.support.excel.impl.DefaultReadStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.warehouse.StvLineCommand;

/**
 * @author lichuan
 * 
 */
@Service("bachBatchReceivingManagerProxy")
public class BatchReceivingManagerProxyImpl extends BaseManagerImpl implements BatchReceivingManagerProxy {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Resource(name = "skuImportForBatchReceiving")
    private ExcelReader skuImportForBatchReceiving;
    @Resource(name = "importInboundPutaway")
    private ExcelReader importInboundPutaway;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Resource(name = "skuImportShelfLifeForBatchReceiving")
    private ExcelReader skuImportShelfLifeForBatchReceiving;

    /**
     * @throws Exception
     * 
     */
    public Map<String, Object> readAllDataFromExcel(File staFile, Map<String, Object> beans) throws Exception {
        Map<String, Object> readStatus = new HashMap<String, Object>();
        ReadStatus readStatus1 = null;
        try {
            readStatus1 = null;
            readStatus1 = skuImportForBatchReceiving.readAll(new FileInputStream(staFile), beans);
        } catch (Exception e) {
            log.error("", e);
            log.error(e.getMessage());
            log.error("throw exception");
            throw e;
        }
        readStatus.put("readStatus", readStatus1);
        readStatus.put("beans", beans);
        return readStatus;
    }

    /**
     * 批量收货
     */
    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus batchReceiving(Map<String, Object> beans, User creator) throws Exception {
        ReadStatus rs = new DefaultReadStatus();
        List<StvLineCommand> stvLines = (List<StvLineCommand>) beans.get("stvLines");
        if (!(stvLines == null || stvLines.isEmpty())) {
            // 合并重复
            Map<String, StvLineCommand> tempLine = new HashMap<String, StvLineCommand>();
            List<StvLineCommand> stvLineList = new ArrayList<StvLineCommand>();
            Iterator<StvLineCommand> it = stvLines.iterator();
            while (it.hasNext()) {
                StvLineCommand slc = it.next();
                if (null == slc) continue;
                if (null == slc.getStaCode() || null == slc.getStaCode().trim() || "".equals(slc.getStaCode().trim())) continue;
                String key = slc.getStaCode().trim() + "_" + slc.getSku().getBarCode().trim() + "_" + slc.getLocation().getCode().trim();
                if (tempLine.containsKey(key)) {
                    StvLineCommand temp = tempLine.get(key);
                    temp.setQuantity(temp.getQuantity() + slc.getQuantity());
                } else {
                    stvLineList.add(slc);
                    tempLine.put(key, slc);
                }

            }
            // 作业单分组
            Map<String, List<StvLineCommand>> group = new HashMap<String, List<StvLineCommand>>();
            Iterator<StvLineCommand> itr = stvLineList.iterator();
            while (itr.hasNext()) {
                StvLineCommand slc = itr.next();
                String staCode = slc.getStaCode();
                staCode = staCode.trim();
                Set<String> keys = group.keySet();
                if (keys.contains(staCode)) {
                    List<StvLineCommand> slcList = group.get(staCode);
                    slcList.add(slc);
                } else {
                    List<StvLineCommand> tempList = new ArrayList<StvLineCommand>();
                    tempList.add(slc);
                    group.put(staCode, tempList);
                }
            }
            // 批量收货
            Set<String> sets = group.keySet();
            for (String staCode : sets) {
                List<StvLineCommand> lists = group.get(staCode);
                ReadStatus current = new DefaultReadStatus();
                try {
                    current = excelReadManager.importForBatchReceiving(staCode, lists, creator);
                    if (current.getStatus() != ReadStatus.STATUS_SUCCESS) {
                        rs.setStatus(current.getStatus());
                        rs.getExceptions().addAll(current.getExceptions());
                    }
                } catch (Exception e) {
                    rs.setStatus(ReadStatus.STATUS_SUCCESS - 1);
                    BusinessException be = new BusinessException(ErrorCode.ERROR_STA_IMPORT_ERROR, new Object[] {staCode});
                    rs.getExceptions().add(be);
                }

            }
        }
        return rs;
    }

    @Override
    public Map<String, Object> readPutawayDataFromExcel(File staFile, Map<String, Object> beans) throws Exception {
        Map<String, Object> readStatus = new HashMap<String, Object>();
        ReadStatus readStatus1 = null;
        try {
            readStatus1 = null;
            readStatus1 = importInboundPutaway.readAll(new FileInputStream(staFile), beans);
        } catch (Exception e) {
            log.error("", e);
            log.error(e.getMessage());
            log.error("throw exception");
            throw e;
        }
        readStatus.put("readStatus", readStatus1);
        readStatus.put("beans", beans);
        return readStatus;
    }

    @SuppressWarnings("all")
    @Override
    public ReadStatus procurementBatchPutaway(Map<String, Object> beans, User creator, Long ouId) throws Exception {
        ReadStatus rs = new DefaultReadStatus();
        List<StvLineCommand> stvLines = (List<StvLineCommand>) beans.get("stvLines");
        // 合并重复
        Map<String, StvLineCommand> tempLine = new HashMap<String, StvLineCommand>();
        List<StvLineCommand> stvLineList = new ArrayList<StvLineCommand>();
        Iterator<StvLineCommand> it = stvLines.iterator();
        while (it.hasNext()) {
            StvLineCommand slc = it.next();
            if (null == slc) continue;
            if (null == slc.getStaCode() || null == slc.getStaCode().trim() || "".equals(slc.getStaCode().trim())) continue;
            String key = slc.getStaCode().trim() + "_" + slc.getBarCode().trim() + "_" + slc.getLocationCode().trim();
            if (tempLine.containsKey(key)) {
                StvLineCommand temp = tempLine.get(key);
                temp.setQuantity(temp.getQuantity() + slc.getQuantity());
            } else {
                stvLineList.add(slc);
                tempLine.put(key, slc);
            }

        }
        // 作业单分组
        Map<String, List<StvLineCommand>> group = new HashMap<String, List<StvLineCommand>>();
        Iterator<StvLineCommand> itr = stvLineList.iterator();
        while (itr.hasNext()) {
            StvLineCommand slc = itr.next();
            String staCode = slc.getStaCode();
            staCode = staCode.trim();
            Set<String> keys = group.keySet();
            if (keys.contains(staCode)) {
                List<StvLineCommand> slcList = group.get(staCode);
                slcList.add(slc);
            } else {
                List<StvLineCommand> tempList = new ArrayList<StvLineCommand>();
                tempList.add(slc);
                group.put(staCode, tempList);
            }
        }
        // 上架
        Set<String> sets = group.keySet();
        for (String staCode : sets) {
            List<StvLineCommand> lists = group.get(staCode);
            ReadStatus current = new DefaultReadStatus();
            try {
                current = excelReadManager.importBatchProcucrementInbound(staCode, lists, creator, ouId);
                if (current.getStatus() != ReadStatus.STATUS_SUCCESS) {
                    rs.setStatus(current.getStatus());
                    rs.getExceptions().addAll(current.getExceptions());
                } else {
                    rs.getExceptions().add(new BusinessException(staCode));
                }
            } catch (BusinessException e) {
                rs.setStatus(ReadStatus.STATUS_SUCCESS - 1);
                String errerCode = "";
                if (null != e.getArgs()) {
                    errerCode = e.getArgs()[0].toString();
                }
                BusinessException be = new BusinessException(e.getErrorCode(), new Object[] {staCode + errerCode});
                rs.getExceptions().add(be);
            } catch (Exception ex) {
                rs.setStatus(ReadStatus.STATUS_SUCCESS - 1);
                BusinessException be = new BusinessException(ErrorCode.ERROR_STA_IMPORT_ERROR, new Object[] {staCode});
                rs.getExceptions().add(be);
            }

        }


        return rs;
    }

    /**
     * @throws Exception
     * 
     */
    @Override
    public Map<String, Object> readAllShelfLifeDataFromExcel(File staFile, Map<String, Object> beans) throws Exception {
        Map<String, Object> readStatus = new HashMap<String, Object>();
        ReadStatus readStatus1 = null;
        try {
            readStatus1 = null;
            readStatus1 = skuImportShelfLifeForBatchReceiving.readAll(new FileInputStream(staFile), beans);
        } catch (Exception e) {
            log.error("", e);
            log.error(e.getMessage());
            log.error("throw exception");
            throw e;
        }
        readStatus.put("readStatus", readStatus1);
        readStatus.put("beans", beans);
        return readStatus;
    }

    /**
     * 保质期商品批量收货
     */
    @SuppressWarnings("unchecked")
    @Override
    public ReadStatus batchShelfLifeReceiving(Map<String, Object> beans, User creator) throws Exception {
        ReadStatus rs = new DefaultReadStatus();
        List<StvLineCommand> stvLines = (List<StvLineCommand>) beans.get("stvLines");
        if (!(stvLines == null || stvLines.isEmpty())) {
            // 合并重复
            Map<String, StvLineCommand> tempLine = new HashMap<String, StvLineCommand>();
            List<StvLineCommand> stvLineList = new ArrayList<StvLineCommand>();
            Iterator<StvLineCommand> it = stvLines.iterator();
            while (it.hasNext()) {
                StvLineCommand slc = it.next();
                if (null == slc) continue;
                if (null == slc.getStaCode() || null == slc.getStaCode().trim() || "".equals(slc.getStaCode().trim())) continue;
                String key = slc.getStaCode().trim() + "_" + slc.getSku().getBarCode().trim() + "_" + slc.getLocation().getCode().trim();
                if (tempLine.containsKey(key)) {
                    StvLineCommand temp = tempLine.get(key);
                    temp.setQuantity(temp.getQuantity() + slc.getQuantity());
                } else {
                    stvLineList.add(slc);
                    tempLine.put(key, slc);
                }

            }
            // 作业单分组
            Map<String, List<StvLineCommand>> group = new HashMap<String, List<StvLineCommand>>();
            Iterator<StvLineCommand> itr = stvLineList.iterator();
            while (itr.hasNext()) {
                StvLineCommand slc = itr.next();
                String staCode = slc.getStaCode();
                staCode = staCode.trim();
                Set<String> keys = group.keySet();
                if (keys.contains(staCode)) {
                    List<StvLineCommand> slcList = group.get(staCode);
                    slcList.add(slc);
                } else {
                    List<StvLineCommand> tempList = new ArrayList<StvLineCommand>();
                    tempList.add(slc);
                    group.put(staCode, tempList);
                }
            }
            // 批量收货
            Set<String> sets = group.keySet();
            for (String staCode : sets) {
                List<StvLineCommand> lists = group.get(staCode);
                ReadStatus current = new DefaultReadStatus();
                try {
                    current = excelReadManager.findShelfLifeSkuCountByStaCode(staCode, lists, creator);
                    if (current.getStatus() != ReadStatus.STATUS_SUCCESS) {
                        rs.setStatus(current.getStatus());
                        rs.getExceptions().addAll(current.getExceptions());
                    }
                } catch (Exception e) {
                    rs.setStatus(ReadStatus.STATUS_SUCCESS - 1);
                    BusinessException be = new BusinessException(ErrorCode.ERROR_STA_IMPORT_ERROR, new Object[] {staCode});
                    rs.getExceptions().add(be);
                }

            }
        }
        return rs;
    }

}
