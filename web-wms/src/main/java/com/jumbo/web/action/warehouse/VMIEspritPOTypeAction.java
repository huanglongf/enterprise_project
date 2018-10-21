package com.jumbo.web.action.warehouse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.vmi.espData.ESPOrderManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.vmi.espData.ESPPoTypeCommand;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

public class VMIEspritPOTypeAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -622451033826253507L;

    private File file;
    private ESPPoTypeCommand ptCommand;
    @Autowired
    private ESPOrderManager ordManage;
    @Autowired
    private ExcelReadManager excelReadManager;

    private String po;


    public String vmiEspritPOTypeEntry() {
        return SUCCESS;
    }

    public String importVMIPOType() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        }
        try {
            ReadStatus rs = excelReadManager.importEspritPoType(file);
            request.put("result", ERROR);
            if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                request.put("result", SUCCESS);
            } else if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
                List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                for (String s : list) {
                    errorMsg.add(s);
                }
            } else if (rs.getStatus() < ReadStatus.STATUS_SUCCESS) {
                for (Exception ex : rs.getExceptions()) {
                    if (ex instanceof BusinessException) {
                        BusinessException bex = (BusinessException) ex;
                        String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                        errorMsg.add(msg);
                    }
                }
            }
        } catch (BusinessException e) {
            request.put("result", ERROR);
            String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            errorMsg.add(msg);
        } catch (Exception e) {
            log.error("",e);
            request.put("result", ERROR);
            errorMsg.add(e.getCause() + " " + e.getMessage());
        }
        request.put("message", JsonUtil.collection2json(errorMsg));
        return SUCCESS;
    }

    public String findPotypeList() {
        setTableConfig();
        if (ptCommand != null) {
            ptCommand.setStartTime(FormatUtil.getDate(ptCommand.getStartTime1()));
            ptCommand.setEndTime(FormatUtil.getDate(ptCommand.getEndTime1()));
        }
        Pagination<ESPPoTypeCommand> poTypeList = ordManage.findESPPoTypeList(tableConfig.getStart(), tableConfig.getPageSize(), ptCommand, tableConfig.getSorts());
        request.put(JSON, toJson(poTypeList));
        return JSON;
    }

    public String findPoTypeByPo() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            result = ordManage.findPoTypeByPo(result, po);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            result.put("result", ERROR);
            result.put("msg", msg);
        } catch (Exception e) {
            log.error("",e);
            result.put("result", ERROR);
            result.put("msg", "发票号获取失败！");
        }
        request.put(JSON, result);
        return JSON;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public ESPPoTypeCommand getPtCommand() {
        return ptCommand;
    }

    public void setPtCommand(ESPPoTypeCommand ptCommand) {
        this.ptCommand = ptCommand;
    }

    public String getPo() {
        return po;
    }

    public void setPo(String po) {
        this.po = po;
    }

}
