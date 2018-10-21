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
package com.jumbo.wms.manager.operationcenter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.operationcenter.ScanSkuRecordDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.ScanSkuRecordCommand;
import com.jumbo.wms.model.operationcenter.ScanSkuRecord;

/**
 * @author lichuan
 * 
 */
@Transactional
@Service("scanSkuRecordManager")
public class ScanSkuRecordManagerImpl extends BaseManagerImpl implements ScanSkuRecordManager {

    private static final long serialVersionUID = 6686656335959847867L;
    @Autowired
    private ScanSkuRecordDao scanSkuRecordDao;
    @Autowired
    private SkuDao skuDao;

    /**
     * 保存记录
     */
    @Override
    public void saveScanRecords(List<ScanSkuRecordCommand> list, Long operatorId) {
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        // String batchCode = sdf.format(new Date());
        Sku sku = null;
        for (ScanSkuRecordCommand cmd : list) {
            ScanSkuRecord ssr = new ScanSkuRecord();
            String batchCode = cmd.getBatchCode();
            if (StringUtil.isEmpty(batchCode)) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR);
            }
            ssr.setBatchCode(batchCode);
            String locationCode = cmd.getLocationCode();
            if (StringUtils.isEmpty(locationCode)) {
                throw new BusinessException(ErrorCode.LOCATION_NOT_FOUND);
            }
            ssr.setLocationCode(locationCode);
            Long skuId = cmd.getSkuId();
            if (null == skuId) {
                if (cmd.getSkuBarcode() == null) {
                    throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
                } else {
                    sku = skuDao.getByBarcode1(cmd.getSkuBarcode());
                }
            }
            if (null != skuId) {
                sku = skuDao.getByPrimaryKey(skuId);
            }
            if (null == sku) {
                throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
            }
            ssr.setSkuId(sku.getId());
            ssr.setQty(cmd.getQty());
            ssr.setOperatorId(operatorId);
            ssr.setCreateTime(new Date());
            scanSkuRecordDao.save(ssr);
        }
    }

    /**
     * 查找记录列表
     */
    @Override
    public Pagination<ScanSkuRecordCommand> findScanRecordsByPage(int start, int pageSize, ScanSkuRecordCommand ssrCmd, Sort[] sort) {
        if (null == ssrCmd) ssrCmd = new ScanSkuRecordCommand();
        Date createTime = ssrCmd.getCreateTime();
        Date finishTime = ssrCmd.getFinishTime();
        String batchCode = StringUtils.isEmpty(ssrCmd.getBatchCode()) ? null : ssrCmd.getBatchCode();
        String skuBarcode = StringUtils.isEmpty(ssrCmd.getSkuBarcode()) ? null : ssrCmd.getSkuBarcode();
        String skuCode = StringUtils.isEmpty(ssrCmd.getSkuCode()) ? null : ssrCmd.getSkuCode();
        String skuName = StringUtils.isEmpty(ssrCmd.getSkuName()) ? null : ssrCmd.getSkuName();
        String locationCode = StringUtils.isEmpty(ssrCmd.getLocationCode()) ? null : ssrCmd.getLocationCode();
        String operator = StringUtils.isEmpty(ssrCmd.getOperatorName()) ? null : ssrCmd.getOperatorName();
        return scanSkuRecordDao.findScanRecordsByPage(start, pageSize, createTime, finishTime, batchCode, skuBarcode, skuCode, skuName, locationCode, operator, new BeanPropertyRowMapper<ScanSkuRecordCommand>(ScanSkuRecordCommand.class), sort);
    }

    /**
     * 获取批次号
     */
    @Override
    public synchronized String getScanRecordBatchCode() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String batchCode = sdf.format(new Date());
        int r = new Random().nextInt(10);
        return batchCode + r;
    }

}
