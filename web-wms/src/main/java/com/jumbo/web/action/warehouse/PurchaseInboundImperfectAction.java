package com.jumbo.web.action.warehouse;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import loxia.support.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.DropDownListManager;
import com.jumbo.wms.manager.channel.ChannelManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.GiftLineCommand;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.BiChannelImperfectCommand;
import com.jumbo.wms.model.warehouse.BiChannelImperfectLineCommand;
import com.jumbo.wms.model.warehouse.ImperfectStv;
import com.jumbo.wms.model.warehouse.ImperfectStvCommand;
import com.jumbo.wms.model.warehouse.SkuImperfect;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransVoucher;

public class PurchaseInboundImperfectAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = -4124212070402060613L;
    @Autowired
    private DropDownListManager dropDownManager;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    @Autowired
    private ChannelManager channelManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private ExcelExportManager excelExportManager;

    private StockTransVoucher stv;
    @Autowired
    private PrintManager printManager;
    private Sku sku;

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    /**
     * 保修卡
     */
    private List<GiftLineCommand> giftLineList;
    private Integer snType;


    public Integer getSnType() {
        return snType;
    }

    public void setSnType(Integer snType) {
        this.snType = snType;
    }

    public List<GiftLineCommand> getGiftLineList() {
        return giftLineList;
    }

    public void setGiftLineList(List<GiftLineCommand> giftLineList) {
        this.giftLineList = giftLineList;
    }

    /**
     * 收货确认记录
     */
    private ImperfectStv imperfectStv;
    /**
     * 作业单信息
     */
    private StockTransApplication sta;
    /**
     * 批次上架模式
     */
    private List<ChooseOption> inMode;
    private File file;
    private SkuImperfect skuImperfect;
    private String createDate;
    private String endDate;


    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    private String stvIds;

    /**
     * 渠道名称
     */
    private String owner;
    /**
     * 渠道ID
     */
    private Long channelId;
    /**
     * 渠道原因ID
     */
    private Long imperfectId;
    /**
     * staId
     */
    private Long staId;
    private String arriveStartTime;
    private String arriveEndTime;
    private String startTime;
    private String endTime;
    private String slipCode1;
    private String slipCode2;
    private Integer isNeedInvoice;
    private String lpcode;
    private String trackingNo;



    public SkuImperfect getSkuImperfect() {
        return skuImperfect;
    }

    public void setSkuImperfect(SkuImperfect skuImperfect) {
        this.skuImperfect = skuImperfect;
    }

    public StockTransVoucher getStv() {
        return stv;
    }

    public void setStv(StockTransVoucher stv) {
        this.stv = stv;
    }

    public String getStvIds() {
        return stvIds;
    }

    public void setStvIds(String stvIds) {
        this.stvIds = stvIds;
    }

    public String getArriveStartTime() {
        return arriveStartTime;
    }

    public void setArriveStartTime(String arriveStartTime) {
        this.arriveStartTime = arriveStartTime;
    }

    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSlipCode1() {
        return slipCode1;
    }

    public void setSlipCode1(String slipCode1) {
        this.slipCode1 = slipCode1;
    }

    public String getSlipCode2() {
        return slipCode2;
    }

    public void setSlipCode2(String slipCode2) {
        this.slipCode2 = slipCode2;
    }

    public Integer getIsNeedInvoice() {
        return isNeedInvoice;
    }

    public void setIsNeedInvoice(Integer isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }

    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    public String getTrackingNo() {
        return trackingNo;
    }

    public void setTrackingNo(String trackingNo) {
        this.trackingNo = trackingNo;
    }

    public String getArriveEndTime() {
        return arriveEndTime;
    }

    public void setArriveEndTime(String arriveEndTime) {
        this.arriveEndTime = arriveEndTime;
    }

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    public ImperfectStv getImperfectStv() {
        return imperfectStv;
    }

    public void setImperfectStv(ImperfectStv imperfectStv) {
        this.imperfectStv = imperfectStv;
    }

    public Long getImperfectId() {
        return imperfectId;
    }

    public void setImperfectId(Long imperfectId) {
        this.imperfectId = imperfectId;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<ChooseOption> getInMode() {
        return inMode;
    }

    public void setInMode(List<ChooseOption> inMode) {
        this.inMode = inMode;
    }


    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    /**
     * 采购经销
     * 
     * @return
     */
    public String purchaseEntry() {
        inMode = dropDownManager.findStaInboundStoreModeChooseOptionList();
        return SUCCESS;
    }

    /**
     * 批量入库上架
     * 
     * @return
     */
    public String shelvesimperfect() {
        return SUCCESS;
    }

    public String skuImperfect() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findSkuImperfect(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu(), skuImperfect, sta, sku, createDate, endDate, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * @return 分页操作
     */
    public String shelvesStaListJson() {
        if (sta != null) {
            setTableConfig();
            request.put(JSON, toJson(wareHouseManager.findStaNotFinishedImperfectListByType(sta, userDetails.getCurrentOu(), FormatUtil.getDate(startTime), FormatUtil.getDate(endTime), tableConfig.getStart(), tableConfig.getPageSize(), slipCode1,
                    slipCode2, tableConfig.getSorts())));
        }
        return JSON;
    }

    /**
     * 模版导出
     * 
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    public void exportInboundShelvesImperfect() throws UnsupportedEncodingException, IOException {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=批量数据_SHELVES" + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            String[] stv = stvIds.split(",");
            List<Long> stvId = new ArrayList<Long>();
            if (stv.length > 0) {
                for (int i = 0; i < stv.length; i++) {
                    stvId.add(Long.parseLong(stv[i]));
                }
                excelExportManager.exportInboundShelvesImperfect(outs, stvId);
            }


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
     * 收货
     * 
     * @return
     * @throws Exception
     */
    public String purchaseReceiveStepImperfect() throws Exception {
        if (stv != null && stv.getStvLines() != null) {
            try {
                JSONObject obj = new JSONObject();
                Long staId = stv.getSta().getId();
                if (staId != null) {
                    stv = wareHouseManager.purchaseReceiveStepImperfect(stv.getSta().getId(), stv.getStvLines(), giftLineList, userDetails.getUser(), stv.getSta().getMemo(), stv.getIsPda(), false, snType);
                    obj.put("stvId", stv.getId());
                    obj.put("isPda", stv.getIsPda());
                    request.put(JSON, obj);
                } else {
                    request.put(JSON, new JSONObject().put("SNmsg", "收货失败"));
                }
            } catch (BusinessException e) {
                catchBusinessException(e);
            } catch (Exception e) {
                // e.printStackTrace();
                // log.error("", e);
                if (log.isErrorEnabled()) {
                    log.error("purchaseReceiveStepImperfect error:", e);
                };
                if (e instanceof DataIntegrityViolationException) {
                    request.put(JSON, new JSONObject().put("SNmsg", "您输入的SN号有重复,请重新核对"));
                } else {
                    request.put(JSON, new JSONObject().put("SNmsg", "数据可能有错误"));
                }
                // catchException(e);
            }
        }
        return JSON;
    }

    /**
     * 入库上架导入
     * 
     * @return
     * @throws Exception
     */
    public String importInboundShelvesImperfect() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        if (sta == null || file == null) {
            msg = (sta == null ? "sta.id must not be null" : "") + (file == null ? " The upload file must not be null" : "");
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = wareHouseManagerExe.purchaseSetImperfect(file, userDetails.getUser());
                // ReadStatus rs = whExecute.importInboundShelves(sta.getStvId(), file,
                // userDetails.getUser());
                if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
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
            } catch (BusinessException e) {
                request.put("msg", "error");
                log.error("", e);
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                log.error(e.getMessage());
                request.put("msg", sb.toString());
            } catch (Exception e) {
                request.put("msg", "error");
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    public String printSkuImperfectStv() {
        JasperPrint jp;
        try {
            jp = printManager.skuImperfectStv(imperfectId);
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

    private void catchBusinessException(BusinessException e) throws Exception {
        String error = businessExceptionPost(e);
        log.error(error);
        errorPost(error);
    }

    private void errorPost(String key, String error) throws Exception {
        request.put(JSON, new JSONObject().put(key, error));
    }

    private void errorPost(String error) throws Exception {
        errorPost("msg", error);
    }

    private String businessExceptionPost(BusinessException e) {
        if (ErrorCode.ERROR_NOT_SPECIFIED == e.getErrorCode()) {
            BusinessException[] errors = (BusinessException[]) e.getArgs();
            StringBuilder sb = new StringBuilder();
            for (BusinessException be : errors) {
                sb.append(getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()) + Constants.HTML_LINE_BREAK);
            }
            return sb.toString();
        } else {
            return getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
        }
    }


    public String getByName() {
        BiChannelCommand command = channelManager.getBiChannelByCode(owner);
        request.put(JSON, JsonUtil.obj2json(command));
        return JSON;
    }

    public String getByImperfect() {
        List<BiChannelImperfectCommand> biChannelImperfectCommands = channelManager.findImperfect(channelId);
        request.put(JSON, JsonUtil.collection2json(biChannelImperfectCommands));
        return JSON;
    }

    public String getByImperfectLine() {
        List<BiChannelImperfectLineCommand> biChannelImperfectCommands = channelManager.findImperfectLine(imperfectId);
        request.put(JSON, JsonUtil.collection2json(biChannelImperfectCommands));
        return JSON;
    }

    public String addImperfectStv() {
        JSONObject result = new JSONObject();
        try {
            ImperfectStv stv = channelManager.addImperfectStv(imperfectStv);
            result.put("msg", SUCCESS);
            result.put("id", stv.getId());
        } catch (Exception e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String imperfectStvLine() {
        List<ImperfectStvCommand> commands = channelManager.findImperfectStvLine(staId);
        request.put(JSON, JsonUtil.collection2json(commands));
        return JSON;
    }

}
