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
package com.jumbo.web.action.warehouse;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuBarcode;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;

public class CommodityAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -2204980356252296470L;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private PrintManager printManager;

    private SkuCommand skuCom;

    private String barCode;

    private List<SkuBarcode> skuBarcode;

    private Long skuId;
    /**
     * 打印数量
     */
    private Long count;

    private File file;

    public String commodityQuery() {
        return SUCCESS;
    }

    // 查询所有的sku
    public String findSku() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findSkuAll(tableConfig.getStart(), tableConfig.getPageSize(), skuCom, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 打印商品条码
     * 
     * @return
     * @throws Exception
     */
    public String printSkuBarcode() {
        JasperPrint jp;
        // List<JasperPrint> jps = null;
        try {
            jp = printManager.printSkuBarcode(skuId);
            // jps = wareHouseManager.printSkuBarcode(skuId,count);
            /*
             * for(int i=0;i<count;i++){ printObject(jp); }
             */
            return printObject(jp);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        } catch (JRException e) {
            log.error("", e);
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 查询sku 以及 barcode
     * 
     * @return
     * @throws JSONException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public String findSkuAndSkuBarcodeById() throws JSONException, IllegalAccessException, InvocationTargetException {
        JSONObject obj = new JSONObject();
        Sku sku = wareHouseManager.findSkuBySkuId(skuId);
        obj.put("sku", JsonUtil.obj2json(sku));
        obj.put("barcodelist", JsonUtil.collection2json(wareHouseManager.findSkuBarcodeBySkuId(skuId)));
        request.put(JSON, obj);
        return JSON;
    }

    // 修改sku 主条码
    public String updateSkuBarCode() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.updateSkuBarCode(skuId, barCode);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            log.error("", e);
            result.put("result", ERROR);
            result.put("message", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    // 修改sku 子条码
    public String updateBarCode() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.updateSkuBarCodes(skuId, skuBarcode);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            log.error("", e);
            result.put("result", ERROR);
            result.put("message", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 保质期商品导入 bin.hu
     */
    public String importSkuShelfLife() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = excelReadManager.createSkuForShelfLife(file);
                if (rs != null) if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
                    request.put("msg", "");
                    List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                    request.put("msg", list);
                } else if (rs.getStatus() < ReadStatus.STATUS_SUCCESS) {
                    request.put("msg", "");
                    List<String> list = new ArrayList<String>();
                    for (Exception ex : rs.getExceptions()) {
                        if (ex instanceof BusinessException) {
                            BusinessException be = (BusinessException) ex;
                            list.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()) + Constants.HTML_LINE_BREAK);
                        }
                    }
                    request.put("msg", list);
                }
            } catch (Exception e) {
                request.put("msg", "error");
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    public SkuCommand getSkuCom() {
        return skuCom;
    }

    public void setSkuCom(SkuCommand skuCom) {
        this.skuCom = skuCom;
    }


    public Long getSkuId() {
        return skuId;
    }


    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public List<SkuBarcode> getSkuBarcode() {
        return skuBarcode;
    }

    public void setSkuBarcode(List<SkuBarcode> skuBarcode) {
        this.skuBarcode = skuBarcode;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }


}
