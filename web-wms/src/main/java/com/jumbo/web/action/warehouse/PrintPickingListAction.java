package com.jumbo.web.action.warehouse;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.PickingListPrintManager;
import com.jumbo.wms.manager.warehouse.StaCancelManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.command.StaDeliverCommand;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.WhPlDiffLine;
import com.jumbo.wms.model.warehouse.WhPlDiffLineCommand;

import loxia.dao.Pagination;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

/**
 * 配货单信息打印控制逻辑
 * 
 * @author jinlong.ke
 *
 */
public class PrintPickingListAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = -7054238943957410415L;
    @Autowired
    private PickingListPrintManager pickingListPrintManager;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private StaCancelManager staCancelManager;
    @Autowired
    private ExcelExportManager excelExportManager;
    /**
     * 作为查询条件的pickingList
     */
    private PickingList pickingList;
    /**
     * PickingList ID
     */
    private Long pickingListId;

    private List<WhPlDiffLine> whPlDiffLineList;
    /**
     * 物流面单号维护信息
     */
    private List<StaDeliverCommand> std;
    /**
     * 导入文件
     */
    private File file;

    private PickingListCommand pickingListCommand;

    private int pickingStarus;
    private String priority;

    private String biginCreateDate; // 创建时间开始
    private String endCreateDate; // 创建时间结束
    private String biginPickDate; // 配货时间开始
    private String endPickDate; // 配货时间结束
    /**
     * 批量
     */
    private String pickingListIds;

    /**
     * 作业单
     */
    private String staCode;

    private String plCode;

    private String areaCode;

    public String getPlCode() {
        return plCode;
    }

    public void setPlCode(String plCode) {
        this.plCode = plCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    /**
     * 是否澳门件
     */
    private Boolean isMacaoOrder;
    /**
     * 是否打印海关单
     */
    private Boolean isPrintMaCaoHGD;

    /**
     * 页面跳转 跳转到配货单信息打印--秒杀页面
     * 
     * @return
     */
    public String printPickingListSecKill() {
        return SUCCESS;
    }

    /**
     * 页面跳转 跳转到作业单
     * 
     * @return
     */
    public String staRestorePageRedirect() {
        return SUCCESS;
    }


    /**
     * 返回所有配货清单
     * 
     * @return
     */
    public String getAllPickingList() {
        setTableConfig();
        Pagination<PickingList> pickinglist = pickingListPrintManager.getAllPickingList(tableConfig.getStart(), tableConfig.getPageSize(), pickingList, isMacaoOrder, isPrintMaCaoHGD, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(pickinglist));
        return JSON;
    }

    /**
     * 返回所有取消配货清单
     * 
     * @return
     */
    public String getAllPickingQuery() {
        setTableConfig();
        Pagination<PickingList> pickinglist = pickingListPrintManager.getAllPickingQuery(tableConfig.getStart(), tableConfig.getPageSize(), pickingList, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(pickinglist));
        return JSON;
    }

    /**
     * 返回根据配货清单状态的数据
     * 
     * @return
     */
    public String getAllPickingListStatus() {
        setTableConfig();
        Pagination<PickingList> pickinglist = pickingListPrintManager.getAllPickingListStatus(tableConfig.getStart(), tableConfig.getPageSize(), pickingList, userDetails.getCurrentOu().getId(), tableConfig.getSorts(), getCityList(priority));
        request.put(JSON, toJson(pickinglist));
        return JSON;
    }




    /**
     * 快捷进入时根据code查询pickinglist信息
     * 
     * @return
     */
    public String getPickingListByCode() {
        JSONObject result = new JSONObject();
        try {
            PickingListCommand plc = pickingListPrintManager.getPickingListByCode(pickingList.getCode(), pickingList.getCheckMode().getValue() == 1 ? null : pickingList.getCheckMode().getValue(), pickingStarus, userDetails.getCurrentOu().getId());
            if (plc != null) {
                result.put("pickingList", JsonUtil.obj2json(plc));
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /***
     * 所有取消配货清单
     * 
     * @return
     */
    public String findAllPickingList() {
        setTableConfig();
        Pagination<PickingList> pickinglist = pickingListPrintManager.findAllPickingList(tableConfig.getStart(), tableConfig.getPageSize(), pickingList, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(pickinglist));
        return JSON;
    }

    /**
     * 根据配货清单返回作业单列表需求分页
     * 
     * @return
     */
    public String detailStaList() {
        setTableConfig();
        Pagination<StockTransApplicationCommand> stas = pickingListPrintManager.getAllStaByPickingListId(tableConfig.getStart(), tableConfig.getPageSize(), pickingListId, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(stas));
        return JSON;
    }

    /**
     * 确认数量模版导出
     * 
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    public void exportStaDeliver() throws UnsupportedEncodingException, IOException {

        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + pickingList.getCode() + "_STATRANSNO" + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.exportStaDeliver(outs, pickingList);
        } catch (Exception e) {
            log.error("", e);
        } finally {
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        }
    }

    /**
     * 根据slipcode和pickingList id查找Sta
     * 
     * @return
     */
    public String findStaBySlipCodeAndPid() {
        JSONObject result = new JSONObject();
        try {
            StockTransApplication sta = staCancelManager.findStaBySlipCodeAndPid(pickingList.getId(), pickingList.getCode());
            if (sta != null) {
                result.put("sta", JsonUtil.obj2json(sta));
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 更新物流单号 根据slipCode
     * 
     * @return
     * @throws JSONException
     */
    public String updateTransNoBySlipCode() {
        JSONObject result = new JSONObject();
        try {
            pickingListPrintManager.updateTransNoBySlipCode(std);
            result.put("rs", "success");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 文件导入维护物流面单
     * 
     * @return
     */
    public String importStaDeliveryInfo() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        } else {
            try {
                ReadStatus rs = pickingListPrintManager.importStaDeliveryInfo(file, pickingListId);
                if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                    request.put("result", SUCCESS);
                } else {
                    errorMsg.addAll(getExcelImportErrorMsg(rs));
                }
            } catch (BusinessException e) {
                request.put("result", ERROR);
                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
                errorMsg.add(msg);
            }
            request.put("message", JsonUtil.collection2json(errorMsg));
        }
        return SUCCESS;
    }

    /**
     * 更新配货单状态
     * 
     * @return
     */
    public String updatePickListWhStatus() {
        JSONObject result = new JSONObject();
        try {
            pickingListPrintManager.updatePickListWhStatus(pickingListId, this.userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
            result.put("msg", "SUCCESS");
        } catch (Exception e) {
            log.error("", e);
            try {
                result.put("msg", "ERROR");
            } catch (JSONException e1) {
                log.error("", e1);
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 拣货&分拣有差异，插入t_wh_pl_diff_line表
     * 
     * @return
     */
    public String addWhPlDiffLine() {
        JSONObject result = new JSONObject();
        try {
            pickingListPrintManager.addWhPlDiffLine(pickingListId, whPlDiffLineList);
            result.put("msg", "SUCCESS");
        } catch (Exception e) {
            log.error("", e);
            try {
                result.put("msg", "ERROR");
            } catch (JSONException e1) {
                log.error("", e1);
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查询配货清单差异表明细 拣货
     * 
     * @return
     */
    public String findWhPlDillLineByPid() {
        setTableConfig();
        request.put(JSON, toJson(pickingListPrintManager.findWhPlDillLineByPid(pickingListId, pickingStarus)));
        return JSON;
    }


    /**
     * 查询配货清单差异表明细 分拣
     * 
     * @return
     */
    public String findWhPlDillLineByPidS() {
        setTableConfig();
        request.put(JSON, toJson(pickingListPrintManager.findWhPlDillLineByPidS(pickingListId)));
        return JSON;
    }

    /**
     * 查询配货清单拣货和分拣的差异
     * 
     * @return
     */
    public String findWhPlDillLineByPidSD() {
        setTableConfig();
        request.put(JSON, toJson(pickingListPrintManager.findWhPlDillLineByPidSD(pickingListId)));
        return JSON;
    }

    public String checkWhPlDillLineByPid() {
        JSONObject result = new JSONObject();
        try {
            List<WhPlDiffLineCommand> whList = pickingListPrintManager.findWhPlDillLineByPid(pickingListId, pickingStarus);
            if (whList.size() > 0) {
                result.put("msg", "DATA");
            } else {
                result.put("msg", "NODATA");
            }
        } catch (Exception e) {
            log.error("", e);
            try {
                result.put("msg", "ERROR");
            } catch (JSONException e1) {
                log.error("", e1);
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 删除拣货差异明细 plid
     * 
     * @return
     */
    public String deleteWhPlDillLineByPid() {
        JSONObject result = new JSONObject();
        try {
            pickingListPrintManager.deleteWhPlDillLineByPid(pickingListId);
            result.put("msg", "SUCCESS");
        } catch (Exception e) {
            log.error("", e);
            try {
                result.put("msg", "ERROR");
            } catch (JSONException e1) {
                log.error("", e1);
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 差异明细确认完成
     * 
     * @return
     */
    public String donePickListWhDieking() {
        JSONObject result = new JSONObject();
        try {
            pickingListPrintManager.donePickListWhDieking(pickingListId, this.userDetails.getCurrentOu().getId(), userDetails.getUser().getId(), whPlDiffLineList);
            result.put("msg", "SUCCESS");
        } catch (Exception e) {
            log.error("", e);
            try {
                result.put("msg", "ERROR");
            } catch (JSONException e1) {
                log.error("", e1);
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 配货批次分配 分页查询
     * 
     * @return
     */
    public String findBatchAllocation() {
        setTableConfig();
        Pagination<PickingListCommand> list =
                pickingListPrintManager.findBatchAllocation(tableConfig.getStart(), tableConfig.getPageSize(), FormatUtil.getDate(biginCreateDate), FormatUtil.getDate(endCreateDate), FormatUtil.getDate(biginPickDate), FormatUtil.getDate(endPickDate),
                        pickingListCommand, tableConfig.getSorts(), userDetails.getCurrentOu().getId());
        request.put(JSON, toJson(list));
        return JSON;
    }


    public String startPrintPicklist() {
        JSONObject result = new JSONObject();
        try {
            String[] ids = pickingListIds.split(",");
            if (null != ids && ids.length > 0) {
                for (int i = 0; i < ids.length; i++) {
                    Long pickingListId = Long.parseLong(ids[i]);
                    pickingListPrintManager.updatPickListById(pickingListId);
                }
                result.put("msg", "SUCCESS");
            } else {
                log.error("更新配货单状态(或批量): id数组为空数据");
                result.put("msg", "ERROR");
            }
        } catch (Exception e) {
            log.error("", e);
            try {
                result.put("msg", "ERROR");
            } catch (JSONException e1) {
                log.error("", e1);
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 批量 更新配货单状态
     * 
     * @return
     */
    public String batchUpdatePickListWhStatus() {
        JSONObject result = new JSONObject();
        try {
            String[] ids = pickingListIds.split(",");
            if (null != ids && ids.length > 0) {
                for (int i = 0; i < ids.length; i++) {
                    Long pickingListId = Long.parseLong(ids[i]);
                    pickingListPrintManager.updatePickListWhStatus(pickingListId, this.userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
                }
                result.put("msg", "SUCCESS");
            } else {
                log.error("更新配货单状态(或批量): id数组为空数据");
                result.put("msg", "ERROR");
            }
        } catch (Exception e) {
            log.error("", e);
            try {
                result.put("msg", "ERROR");
            } catch (JSONException e1) {
                log.error("", e1);
            }
        }
        request.put(JSON, result);
        return JSON;
    }


    /**
     * 重置作业单为新建
     * 
     * @return
     */
    public String updateStaStatusToCreate() {
        JSONObject result = new JSONObject();
        try {
            String msg = staCancelManager.updateStaStatusToCreate(staCode, getCurrentOu().getId());
            if (msg == null || "".equals(msg)) {
                result.put("msg", "SUCCESS");
            } else {
                result.put("msg", msg);
            }
        } catch (Exception e) {
            log.error("", e);
            try {
                result.put("msg", "ERROR");
            } catch (JSONException e1) {
                // e1.printStackTrace();
                if (log.isErrorEnabled()) {
                    log.error("updateStaStatusToCreate error:" + staCode, e);
                };
            }

        }
        request.put(JSON, result);
        return JSON;
    }
    /**
     * 手动占用
     * @return
     * @throws JSONException 
     */
    
    public String shouPick() throws JSONException {
        JSONObject result = new JSONObject();
        try {
             wareHouseManagerProxy.newOccupiedInventoryByStaShou(staCode, getCurrentOu().getId());
             result.put("msg", "SUCCESS");
        }
        catch (BusinessException e){
            result.put("msg", ERROR);
            if (e.getErrorCode() == -1) {
                result.put("msg", e.getMessage());
            }  else {
                result.put("msg", ERROR);
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                result.put("msg", sb.toString());
                }
        }
            catch (Exception e) {
                log.error("shouPick", e);
                result.put("msg", "ERROR");
                }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 作业单备份还原
     * 
     * @return
     * @throws JSONException
     */

    public String updateStaRestore() throws JSONException {
        JSONObject result = new JSONObject();
        String msg = null;
        try {
            msg = staCancelManager.updateStaRestore(staCode, getCurrentOu().getId());
            result.put("msg", msg);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("updateStaRestore", e);
            result.put("msg", "0");
        }
        request.put(JSON, result);
        return JSON;
    }



    public PickingList getPickingList() {
        return pickingList;
    }

    public void setPickingList(PickingList pickingList) {
        this.pickingList = pickingList;
    }

    public Long getPickingListId() {
        return pickingListId;
    }

    public void setPickingListId(Long pickingListId) {
        this.pickingListId = pickingListId;
    }

    public List<StaDeliverCommand> getStd() {
        return std;
    }

    public void setStd(List<StaDeliverCommand> std) {
        this.std = std;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getPickingStarus() {
        return pickingStarus;
    }

    public void setPickingStarus(int pickingStarus) {
        this.pickingStarus = pickingStarus;
    }

    public List<WhPlDiffLine> getWhPlDiffLineList() {
        return whPlDiffLineList;
    }

    public void setWhPlDiffLineList(List<WhPlDiffLine> whPlDiffLineList) {
        this.whPlDiffLineList = whPlDiffLineList;
    }

    public PickingListCommand getPickingListCommand() {
        return pickingListCommand;
    }

    public void setPickingListCommand(PickingListCommand pickingListCommand) {
        this.pickingListCommand = pickingListCommand;
    }

    public String getBiginCreateDate() {
        return biginCreateDate;
    }

    public void setBiginCreateDate(String biginCreateDate) {
        this.biginCreateDate = biginCreateDate;
    }

    public String getEndCreateDate() {
        return endCreateDate;
    }

    public void setEndCreateDate(String endCreateDate) {
        this.endCreateDate = endCreateDate;
    }

    public String getBiginPickDate() {
        return biginPickDate;
    }

    public void setBiginPickDate(String biginPickDate) {
        this.biginPickDate = biginPickDate;
    }

    public String getEndPickDate() {
        return endPickDate;
    }

    public void setEndPickDate(String endPickDate) {
        this.endPickDate = endPickDate;
    }

    public String getPickingListIds() {
        return pickingListIds;
    }

    public void setPickingListIds(String pickingListIds) {
        this.pickingListIds = pickingListIds;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public Boolean getIsMacaoOrder() {
        return isMacaoOrder;
    }

    public void setIsMacaoOrder(Boolean isMacaoOrder) {
        this.isMacaoOrder = isMacaoOrder;
    }

    public Boolean getIsPrintMaCaoHGD() {
        return isPrintMaCaoHGD;
    }

    public void setIsPrintMaCaoHGD(Boolean isPrintMaCaoHGD) {
        this.isPrintMaCaoHGD = isPrintMaCaoHGD;
    }

     public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    private List<String> getCityList(String str) {
        if (!StringUtils.hasText(str)) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        if (str != null && StringUtils.hasText(str)) {
            for (String s : str.split(",")) {
                list.add(s);
            }
        }
        return list;
    }
}
