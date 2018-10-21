package com.jumbo.web.action.warehouse;

import java.io.File;
import java.util.List;

import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;

/**
 * 导入打印
 * 
 * @author jinlong.ke
 * 
 */
public class ImportPrintAction extends BaseJQGridProfileAction {
    /**
     * 
     */
    private static final long serialVersionUID = 2593086973899681858L;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private PrintManager printManager;
    private File file;

    public String redirectIndex() {
        return SUCCESS;
    }

    public String importThenPrint() {
        try {
            ReadStatus rs = excelReadManager.importStaDeliveryInfo(file);
            if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                request.put("msg", "success");
                // request.put("printInfo", printInfo);
            } else if (rs.getStatus() > 0) {
                String msg = "";
                List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                for (String s : list) {
                    msg += s;
                }
                request.put("msg", msg);
            }
        } catch (BusinessException e) {
            request.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e) {
            log.error("", e);
            request.put("msg", "error");
        }
        return SUCCESS;

        // JasperPrint jp;
        // try {
        // jp = staPrintManager.printExpressBillBySta(sta.getId(), null);
        // return printObject(jp);
        // } catch (JasperPrintFailureException e) {
        // log.error("", e);
        // } catch (JRException e) {
        // log.error("", e);
        // } catch (JasperReportNotFoundException e) {
        // log.error("", e);
        // } catch (Exception e) {
        // log.error("", e);
        // }
        // return null;
    }

    public String afterImportPrint() {
        JasperPrint jp;
        jp = printManager.printImportInfo();
        try {
            return printObject(jp);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
