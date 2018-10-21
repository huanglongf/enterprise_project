package com.jumbo.web.action.warehouse;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.MsgToWcsThread;
import com.jumbo.web.manager.print.PrintManager;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.AutoOutboundTurnboxManager;
import com.jumbo.wms.model.automaticEquipment.WcsInterfaceType;
import com.jumbo.wms.model.automaticEquipment.WhContainerCommand;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.mongodb.MongDbNoThreeDimensional;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * 自动化对接周转箱操作相关控制
 * 
 * @author jinlong.ke
 * @date 2016年2月25日下午2:47:43
 */
public class AutoOutboundTurnoverBoxAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = -4245317002128029363L;
    /**
     * 单件小批次中的任意一个作业单前置单据号
     */
    private String slipCode;
    /**
     * 周转箱编码
     */
    private String code;
    /**
     * 配货清单编码
     */
    private String plCode;
    /**
     * 配货清单id
     */
    private Long pickId;
    /**
     * 周转箱ID
     */
    private Long tnId;

    /**
     * 仓储区域编码
     */
    private String zoonCode;

    /**
     * 周转箱编码
     */
    private List<String> boxCode;

    /**
     * 仓库ID
     */
    private Long ouId;

    /**
     * 占用状态
     */
    private Integer status;



    private Long pinkingListId;

    private String pinkingCode;

    private Date startCreateTime2;

    private Date endCreateTime2;

    private String owner2;

    private Long plId;

    @Autowired
    private AutoOutboundTurnboxManager autoOutboundTurnboxManager;
    @Autowired
    private PrintManager printManager;


    private StockTransApplicationCommand staCommand;

    private Long staId;
    @Autowired
    private ExcelExportManager Excexport;

    /**
     * 初始页面跳转
     * 
     * @return
     */
    public String pageRedirect() {
        return SUCCESS;
    }

    /**
     * 核对当前单据是否可以绑定周转箱，并返回操作类型5 单件、1 多件
     * 
     * @return
     * @throws JSONException
     */
    public String checkBindPickingList() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            result.put("result", ERROR);
            PickingList p = autoOutboundTurnboxManager.checkBindPickingList(plCode, getCurrentOu().getId());
            result.put("result", SUCCESS);
            result.put("pId", p.getId());
            result.put("pType", p.getCheckMode().getValue());
        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            result.put("msg", errorMsg);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据任意扫描的装箱清单，绑定周转箱和小批次
     * 
     * @return
     * @throws JSONException
     */
    public String bindBatchAndTurnbox() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            result.put("result", ERROR);
            String rv = autoOutboundTurnboxManager.bindBatchAndTurnbox(slipCode, code, pickId, getCurrentOu().getId(), userDetails.getUser().getId());
            try {
                if (Long.valueOf(rv.split(",")[0]).compareTo(0L) != 0) {
                    MsgToWcsThread mt = new MsgToWcsThread();
                    mt.setMsgId(Long.valueOf(rv.split(",")[0]));
                    mt.setType(WcsInterfaceType.OShouRongQi.getValue());
                    Thread t = new Thread(mt);
                    t.start();
                }
            } catch (Exception e) {
                log.error("Interface OShouRongQi container Code: " + code + " error");
                log.error("", e);
            }
            result.put("flag", rv.split(",")[1]);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            result.put("msg", errorMsg);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据扫描的多件波次号，释放周转箱
     * 
     * @return
     * @throws JSONException
     */
    public String releaseTurnBoxByPc() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            result.put("result", SUCCESS);
            autoOutboundTurnboxManager.releaseTurnBoxByPc(plCode, userDetails.getUser().getId());
        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            result.put("result", ERROR);
            result.put("msg", errorMsg);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 新建周转箱
     * 
     * @return
     * @throws JSONException
     */
    public String createNewTurnbox() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            result.put("result", ERROR);
            autoOutboundTurnboxManager.createNewTurnboxByName(code, getCurrentOu().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            result.put("msg", errorMsg);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据条件查询周转箱
     * 
     * @return
     */
    public String getAllTurnoverBox() {
        setTableConfig();
        if ("".equals(code)) {
            code = null;
        }
        if ("".equals(plCode)) {
            plCode = null;
        }
        Pagination<WhContainerCommand> tl = autoOutboundTurnboxManager.getAllTurnoverBox(tableConfig.getStart(), tableConfig.getPageSize(), code, plCode, status, ouId, tableConfig.getSorts());
        request.put(JSON, toJson(tl));
        return JSON;
    }

    /**
     * 打印周装箱条码
     * 
     * @return
     */
    public String printTurnoverBoxBarCode() {
        try {
            List<JasperPrint> jasperPrintList = printManager.printTurnoverBoxBarCode(tnId);
            return printObject(jasperPrintList);
        } catch (Exception e1) {
            log.error("", e1);
            return null;
        }
    }

    /**
     * 重置周装箱状态
     * 
     * @return
     * @throws JSONException
     */
    public String resetTurnoverBoxStatus() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            result.put("result", ERROR);
            autoOutboundTurnboxManager.resetTurnoverBoxStatus(tnId, userDetails.getUser().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            if (e.getMessage() != null) {
                result.put("msg", e.getMessage());
            } else {
                result.put("msg", errorMsg);
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据条件查询配货批与仓储区域的关系
     * 
     * @return
     */
    public String findPickingListZoneByParams() {
        setTableConfig();

        HashMap<String, Object> m = new HashMap<String, Object>();
        m.put("pickingListCode", slipCode);
        request.put(JSON, toJson(autoOutboundTurnboxManager.findPickingListZoneByParams(tableConfig.getStart(), tableConfig.getPageSize(), m)));
        return JSON;
    }

    /**
     * 根据扫描的捡货清单，绑定周转箱
     * 
     * @return
     * @throws JSONException
     */
    public String bindManyBatchAndTurnbox() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            autoOutboundTurnboxManager.bindManyBatchAndTurnbox(slipCode, zoonCode, code, getCurrentOu().getId());
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            result.put("msg", errorMsg);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String pickingListAndZoneOver() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            List<Long> msgId = autoOutboundTurnboxManager.pickingListAndZoneOverForWeb(pickId, zoonCode, boxCode, getCurrentOu().getId(), userDetails.getUser().getId());
            if (msgId != null && msgId.size() > 1) {

                for (int i = 1; i < msgId.size(); i++) {
                    try {

                        MsgToWcsThread wt = new MsgToWcsThread();
                        wt.setMsgId(msgId.get(i));
                        wt.setType(msgId.get(0).intValue());
                        new Thread(wt).start();
                    } catch (Exception e) {
                        result.put("result", ERROR);
                        // e.printStackTrace();
                        if (log.isErrorEnabled()) {
                            log.error("sShouRongQi error:" + msgId, e);
                        };
                        // log.error("sShouRongQi error:" + msgId);
                    }
                }
            } else if (msgId != null && msgId.size() == 1) {
                if (msgId.get(0) == -2L) {
                    result.put("over", "批次拣货完成！\r\n 无法推荐，请自行分拣和复核！");
                }
            }
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            result.put("msg", errorMsg);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String findOccupyOu() {
        request.put(JSON, JsonUtil.collection2json(autoOutboundTurnboxManager.findOccupyOu()));
        return JSON;
    }

    /**
     * 强制重置周转箱状态
     * 
     * @return
     * @throws JSONException
     */
    public String resetBoxStatus() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            autoOutboundTurnboxManager.resetBoxStatus(pickId);
            result.put("msg", SUCCESS);
        } catch (Exception e) {
            result.put("msg", ERROR);
            log.error("resetBoxStatus", e);
        }
        request.put(JSON, result);
        return JSON;
    }


    /**
     * 查询缺失三维信息的单子
     * 
     * @return
     */
    public String findThreeDimensional() {
        setTableConfig();

        Pagination<StockTransApplicationCommand> staList = null;
        try {
            if (staCommand == null) {
                staCommand = new StockTransApplicationCommand();
            }
            staCommand.setOuId(getCurrentOu().getId());
            staList = autoOutboundTurnboxManager.findThreeDimensional(tableConfig.getStart(), tableConfig.getPageSize(), staCommand);
        } catch (Exception e) {
            log.error("", e);
        }
        request.put(JSON, toJson(staList));

        return JSON;
    }


    /**
     * 查询缺失三维信息的单子
     * 
     * @return
     */
    public String findNoThreeDimensional() {
        setTableConfig();
        Pagination<MongDbNoThreeDimensional> skuList = null;
        try {
            skuList = autoOutboundTurnboxManager.findNoThreeDimensional(tableConfig.getStart(), tableConfig.getPageSize(), pinkingCode, startCreateTime2, endCreateTime2, owner2, getCurrentOu().getId());
        } catch (Exception e) {
            log.error("", e);
        }
        request.put(JSON, toJson(skuList));
        return JSON;
    }

    /**
     * 查询缺失三维信息商品
     * 
     * @return
     */
    public String findThreeDimensionalSkuInfo() {
        setTableConfig();
        request.put(JSON, toJson(autoOutboundTurnboxManager.findThreeDimensionalSkuInfo(tableConfig.getStart(), tableConfig.getPageSize(), staId)));
        return JSON;
    }

    /**
     * 查询缺失三维信息商品
     * 
     * @return
     */
    public String findNoThreeDimensionalSkuInfo() {
        setTableConfig();
        request.put(JSON, toJson(autoOutboundTurnboxManager.findNoThreeDimensionalSkuInfo(tableConfig.getStart(), tableConfig.getPageSize(), plId)));
        return JSON;
    }

    public void exportThreeDimensionalSkuInfo() throws Exception {
        if (staCommand == null) {
            staCommand = new StockTransApplicationCommand();
        }
        staCommand.setOuId(getCurrentOu().getId());
        List<SkuCommand> skuList = autoOutboundTurnboxManager.findThreeDimensionalSkuInfoList(staCommand);

        //
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        String fileName = "缺失三维数据的商品";
        fileName = fileName.replace(" ", "");
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        ServletOutputStream os = response.getOutputStream();
        try {
            Excexport.exportThreeDimensionalSkuInfo(os, skuList);
            os.flush();
            os.close();
        } catch (Exception e) {
            log.error("", e);
        }
    }


    public void exportNoThreeDimensional() throws Exception {
        List<SkuCommand> skuList = autoOutboundTurnboxManager.findNoThreeDimensionalSkuInfoList(getCurrentOu().getId());

        response.setContentType("application/octet-stream;charset=ISO8859-1");
        String fileName = "缺失三维数据的商品";
        fileName = fileName.replace(" ", "");
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        ServletOutputStream os = response.getOutputStream();
        try {
            Excexport.exportNoThreeDimensional(os, skuList);
            os.flush();
            os.close();
        } catch (Exception e) {
            log.error("", e);
        }
    }


    public String getSlipCode() {
        return slipCode;
    }

    public void setSlipCode(String slipCode) {
        this.slipCode = slipCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }



    public String getZoonCode() {
        return zoonCode;
    }

    public void setZoonCode(String zoonCode) {
        this.zoonCode = zoonCode;
    }

    public List<String> getBoxCode() {
        return boxCode;
    }

    public void setBoxCode(List<String> boxCode) {
        this.boxCode = boxCode;
    }

    public String getPlCode() {
        return plCode;
    }

    public void setPlCode(String plCode) {
        this.plCode = plCode;
    }

    public Long getPickId() {
        return pickId;
    }

    public void setPickId(Long pickId) {
        this.pickId = pickId;
    }

    public Long getTnId() {
        return tnId;
    }

    public void setTnId(Long tnId) {
        this.tnId = tnId;
    }

    public Long getOuId() {
        return ouId;
    }

    public void setOuId(Long ouId) {
        this.ouId = ouId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public StockTransApplicationCommand getStaCommand() {
        return staCommand;
    }

    public void setStaCommand(StockTransApplicationCommand staCommand) {
        this.staCommand = staCommand;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public Long getPinkingListId() {
        return pinkingListId;
    }

    public void setPinkingListId(Long pinkingListId) {
        this.pinkingListId = pinkingListId;
    }

    public Date getStartCreateTime2() {
        return startCreateTime2;
    }

    public void setStartCreateTime2(Date startCreateTime2) {
        this.startCreateTime2 = startCreateTime2;
    }

    public Date getEndCreateTime2() {
        return endCreateTime2;
    }

    public void setEndCreateTime2(Date endCreateTime2) {
        this.endCreateTime2 = endCreateTime2;
    }

    public String getOwner2() {
        return owner2;
    }

    public void setOwner2(String owner2) {
        this.owner2 = owner2;
    }

    public Long getPlId() {
        return plId;
    }

    public void setPlId(Long plId) {
        this.plId = plId;
    }

    public String getPinkingCode() {
        return pinkingCode;
    }

    public void setPinkingCode(String pinkingCode) {
        this.pinkingCode = pinkingCode;
    }



}
