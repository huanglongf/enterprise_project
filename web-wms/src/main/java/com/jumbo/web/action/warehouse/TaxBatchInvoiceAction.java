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



import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;

import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.FillInInvoiceManagerProxy;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelWriterManager;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.WmsInvoiceOrder;

/**
 * 补寄发票相关功能控制逻辑
 * 
 * @author jinlong.ke
 * 
 */
public class TaxBatchInvoiceAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 5029420869862210452L;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Resource
    private ApplicationContext applicationContext;
    @Autowired
    private ExcelExportManager excelExportManager;
    @Autowired
    private ExcelWriterManager excelWriterManager;
    @Autowired
    private PrintManager printManager;
    @Autowired
    private FillInInvoiceManagerProxy fillInInvoiceManagerProxy;
    private String createTime;
    private String endCreateTime;
    private String finishTime;
    private String endFinishTime;
    private Integer intStatus;
    private Long staId;
    private List<Long> wioIdlist;
    private String staIdlist;
    private String swioIdlist;
    private String batchNo;
    private String fileName;

    private StockTransApplicationCommand staCmd;

    private WmsInvoiceOrder wioCmd;

    // 批量导出发票和电子面单
    public String whBatchInvoiceExpress() {
        return SUCCESS;
    }

    /**
     * 补开发票查询
     */
    public String whFillopenInvoiceSearch() {
        return SUCCESS;
    }

    /**
     * 批量导出发票和电子面单列表查询
     * 
     * @return
     */
    public String queryInvoiceOrderExport() {
        setTableConfig();
        if (wioCmd == null) {
            wioCmd = new WmsInvoiceOrder();
        }
        request.put(JSON, toJson(wareHouseManager.queryInvoiceOrderExport(tableConfig.getStart(), tableConfig.getPageSize(), FormatUtil.getDate(createTime), FormatUtil.getDate(endCreateTime), FormatUtil.getDate(finishTime),
                FormatUtil.getDate(endFinishTime), wioCmd, tableConfig.getSorts())));
        return JSON;
    }

    public String openPrintAndExportPage() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            String batchCode = fillInInvoiceManagerProxy.preExportInvoice(wioIdlist);
            obj.put("result", SUCCESS);
            obj.put("batchCode", batchCode);
        } catch (BusinessException e) {
            obj.put("result", ERROR);
            String errorMsg = "";
            if (e.getErrorCode() != ErrorCode.ERROR_NOT_SPECIFIED) {
                errorMsg += applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs(), Locale.SIMPLIFIED_CHINESE);
            }
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += applicationContext.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs(), Locale.SIMPLIFIED_CHINESE);
            }
            obj.put("msg", errorMsg);
        }
        request.put(JSON, obj);
        return JSON;
    }

    // 校验补开发票生成按钮
    // public String openPrintAndExportPage() throws JSONException {
    // JSONObject obj = new JSONObject();
    // try {
    // Map<String, String> retMap = wareHouseManager.checkAllWmsInvoiceOrder(wioIdlist,
    // userDetails.getCurrentOu().getId());
    // if ("true".equals(retMap.get("flag"))) {
    // obj.put("result", SUCCESS);
    // } else if ("trueExist".equals(retMap.get("flag"))) {
    // obj.put("result", retMap.get("flag"));
    // } else if ("false".equals(retMap.get("flag"))) {
    // obj.put("result", retMap.get("flag"));
    // }
    // obj.put("batchNo", retMap.get("batchNo"));
    // } catch (BusinessException e) {
    // obj.put("result", ERROR);
    // }
    // request.put(JSON, obj);
    // return JSON;
    // }

    // 更新发票单据状态
    public String updateWmsInvoiceOrderStatus() throws JSONException {
        JSONObject obj = new JSONObject();
        try {
            wareHouseManager.updateWmsInvoiceOrderStatus(wioIdlist, userDetails.getCurrentOu().getId());
            obj.put("result", SUCCESS);
        } catch (BusinessException e) {
            obj.put("result", ERROR);
        }
        request.put(JSON, obj);
        return JSON;
    }


    // 单张物流面单的打印
    public String printSingleInvoiceOrderDeliveryOut() {
        JasperPrint jp;
        try {
            jp = printManager.getJpByInvoiceOrder(wioCmd.getId());
            // printManager.printExpressBillByInvoiceOrder(wioCmd.getId(), null, null,
            // userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
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
     * 物流快递面单套打
     * 
     * @return
     * @throws Exception
     */
    public String printSelectedWmsInvoiceOrderTrans() {
        try {
            List<JasperPrint> jp = new ArrayList<JasperPrint>();
            List<Long> list = null;
            if (swioIdlist != null) {
                String[] wioIds = swioIdlist.split(",");
                Long[] longs = convertionToLong(wioIds);
                list = Arrays.asList(longs);
            }
            jp = printManager.printExpressBillForInvoiceOrder(list);
            // jp = printManager.printExpressBillByWioListId(list, batchNo,
            // userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
            return printObject(jp);
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        } catch (JRException e) {
            log.error("", e);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }


    /**
     * 导出税控发票
     * 
     * @throws UnsupportedEncodingException
     * 
     * @throws Exception
     */
    public String exportWmsInvoiceOrder() {
        List<Long> list = null;
        if (swioIdlist != null) {
            String[] wioIds = swioIdlist.split(",");
            Long[] longs = convertionToLong(wioIds);
            list = Arrays.asList(longs);
        }
        String fileName = excelWriterManager.getExportFileName(batchNo, list);
        setFileName(fileName + Constants.EXCEL_XLS);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        excelExportManager.exportSoInvoiceByBatchNo(out, batchNo, list);
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        setInputStream(in);
        return SUCCESS;
    }


    // 将String数组转换为Long类型数组
    public static Long[] convertionToLong(String[] strs) {
        Long[] longs = new Long[strs.length]; // 声明long类型的数组
        for (int i = 0; i < strs.length; i++) {
            String str = strs[i]; // 将strs字符串数组中的第i个值赋值给str
            long thelong = Long.valueOf(str);// 将str转换为long类型，并赋值给thelong
            longs[i] = thelong;// 将thelong赋值给 longs数组中对应的地方
        }
        return longs;
    }


    public StockTransApplicationCommand getStaCmd() {
        return staCmd;
    }

    public void setStaCmd(StockTransApplicationCommand staCmd) {
        this.staCmd = staCmd;
    }

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

    public Integer getIntStatus() {
        return intStatus;
    }

    public void setIntStatus(Integer intStatus) {
        this.intStatus = intStatus;
    }

    public WmsInvoiceOrder getWioCmd() {
        return wioCmd;
    }

    public void setWioCmd(WmsInvoiceOrder wioCmd) {
        this.wioCmd = wioCmd;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public List<Long> getWioIdlist() {
        return wioIdlist;
    }

    public void setWioIdlist(List<Long> wioIdlist) {
        this.wioIdlist = wioIdlist;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(String batchNo) {
        this.batchNo = batchNo;
    }

    public String getStaIdlist() {
        return staIdlist;
    }

    public void setStaIdlist(String staIdlist) {
        this.staIdlist = staIdlist;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSwioIdlist() {
        return swioIdlist;
    }

    public void setSwioIdlist(String swioIdlist) {
        this.swioIdlist = swioIdlist;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getEndFinishTime() {
        return endFinishTime;
    }

    public void setEndFinishTime(String endFinishTime) {
        this.endFinishTime = endFinishTime;
    }

}
