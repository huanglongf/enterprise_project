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
package com.jumbo.web.action.wmsdataimport;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.wmsdataimport.WMSDataImportManager;
import com.jumbo.wms.model.dataimport.ShopDataSource;

/**
 * wms数据导出
 * 
 */
public class WMSDataImportAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = -4725171113538112202L;

    @Autowired
    private WMSDataImportManager wmsDataImportManager;

    @Autowired
    private WareHouseManager manager;


    private Long zdhId;

    private String code;

    /**
     * 创建时间
     */
    private String createTime;
    /**
     * 结束时间
     */
    private String endCreateTime;
    /**
     * 所选店铺的code集合
     */
    private String postshopInput;
    /**
     * 平台单据号集合
     */
    private String slipcodes;
    /**
     * 下载的文件名
     */
    private String fileName;
    /**
     * 数据来源
     */
    // @Element(ShopDataSource.class)
    private List<ShopDataSource> addList;

    /**
     * 初始化跳转页面，跳转到对应的jsp页面
     * 
     * @return
     */
    public String wareHouseDataImport() {
        return SUCCESS;
    }

    /**
     * 初始化页面生成和下载按钮状态
     * 
     * @return
     * @throws JSONException
     */
    public String getFileCreateStatus() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            String res = wmsDataImportManager.getFileCreateStatus();
            result.put("result", SUCCESS);
            result.put("btnStatus", res);
        } catch (BusinessException e) {
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e2) {
            result.put("msg", e2.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 生成历史SN、效期流水
     * 
     * @return
     * @throws JSONException
     */
    public String snAndValidDateSkuFlowCreate() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wmsDataImportManager.generateSnAndValidDateSkuFlow(FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime), postshopInput, slipcodes);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e2) {
            result.put("msg", e2.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 尚未完成品牌入库指令数据文件生成
     * 
     * @return
     * @throws JSONException
     */
    public String brandUnFinishedInstructions() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wmsDataImportManager.generateBrandUnFinishedInstructions(postshopInput, addList);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e2) {
            result.put("msg", e2.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 下载文件
     * 
     * @return
     * @throws Exception
     */
    public String downloadExportFile() throws Exception {
        // 解决乱码
        fileName = new String(this.fileName.getBytes("GBK"), "ISO-8859-1");
        try {
            log.info("fileName {} ", fileName);
            String filepath = wmsDataImportManager.backupFile(fileName);
            // setFileName(fileName + Constants.FILE_EXTENSION_TXT);
            InputStream in = new FileInputStream(new File(filepath));
            setInputStream(in);
        } catch (Exception e) {
            log.error("", e);
        }
        return SUCCESS;
    }


    public String downloadBackUpInv() throws IOException {
        String fileName = code + Constants.FILE_EXTENSION_TXT;
        InputStream in = null;
        try {
            String filePath = manager.downloadBackUpInv(zdhId);
            in = new FileInputStream(new File(filePath));
            setInputStream(in);
            setFileName(fileName);
        } catch (FileNotFoundException e) {
            log.error("downloadBackUpInv", e);
        }
        return SUCCESS;
    }

    // @Override
    // public String execute() throws Exception {
    // return SUCCESS;
    // }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(String endCreateTime) {
        this.endCreateTime = endCreateTime;
    }

    public String getPostshopInput() {
        return postshopInput;
    }

    public void setPostshopInput(String postshopInput) {
        this.postshopInput = postshopInput;
    }

    public String getSlipcodes() {
        return slipcodes;
    }

    public void setSlipcodes(String slipcodes) {
        this.slipcodes = slipcodes;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<ShopDataSource> getAddList() {
        return addList;
    }

    public void setAddList(List<ShopDataSource> addList) {
        this.addList = addList;
    }

    public Long getZdhId() {
        return zdhId;
    }

    public void setZdhId(Long zdhId) {
        this.zdhId = zdhId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
