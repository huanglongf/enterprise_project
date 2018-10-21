package com.jumbo.web.action.warehouse;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.warehouse.ReturnPackage;
import com.jumbo.wms.model.warehouse.ReturnPackageCommand;

/**
 * 商品标签
 * 
 * @author xiaolong.fei
 * 
 */
public class ReturnPackageAction extends BaseJQGridProfileAction {
    /**
     * 
     */
    private static final long serialVersionUID = -1895241672688248124L;
    @Autowired
    private WareHouseManagerExe whExe;
    @Autowired
    private WareHouseManagerQuery whQuery;
    @Autowired
    private PrintManager printManager;

    @Autowired
    private ExcelReadManager excelReadManager;


    private List<ReturnPackageCommand> rpcommList;
    private ReturnPackageCommand comm;

    private String batchCode;

    private File file;

    private static List<ReturnPackage> returnPackage;

    private ReturnPackageCommand packages;

    public String returnPackage() {
        return SUCCESS;
    }


    public String returnPackageHand() throws Exception {
        JSONObject result = new JSONObject();
        try {
            String batchCode = whExe.returnPackageRegister(rpcommList, userDetails.getCurrentOu(), userDetails.getUser());
            if (batchCode == null) {
                result.put("result", ERROR);
                result.put("message", "Package list is null!");
            } else {
                result.put("result", SUCCESS);
                result.put("batchCode", batchCode);
            }
        } catch (BusinessException e) {
            result.put("result", ERROR);
            StringBuffer sb = new StringBuffer();
            if (e.getErrorCode() > 0) {
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
            }
            BusinessException be = e;
            while ((be = be.getLinkedException()) != null) {
                sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
            }
            log.error(e.getMessage());
            result.put("message", sb.toString());
        }
        request.put(JSON, result);
        return JSON;
    }


    public String queryReturnPackage() throws JSONException {
        return SUCCESS;
    }

    public String queryReturnPackageHand() throws JSONException {
        setTableConfig();
        Pagination<ReturnPackageCommand> list = whQuery.findReturnPackage(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), comm, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String queryWarehouse() throws JSONException {
        request.put(JSON, JsonUtil.collection2json(whQuery.fandOperationUnitsByType(OperationUnitType.OUTYPE_WAREHOUSE)));
        return JSON;
    }

    /**
     * 物流交接清单打印
     * 
     * @return
     */
    public String printReturnPackage() {
        try {
            JasperPrint jasperPrintList = printManager.printReturnPackage(batchCode);
            if (jasperPrintList == null) jasperPrintList = new JasperPrint();
            return printObject(jasperPrintList);
        } catch (Exception e1) {
            // e1.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("printReturnPackage error:" + batchCode, e1);
            };
            return null;
        }
    }

    /**
     * 批量导入退换货快递登记信息
     * 
     * @return
     * @throws JSONException
     */
    public String importReturnPackage() throws JSONException {
        JSONObject result = new JSONObject();
        if (file != null) {
            returnPackage = excelReadManager.importReturnPackage(file);
            result.put("message", "success");
        }
        request.put(JSON, result);
        return SUCCESS;
    }


    public String returnPackageInfo() throws JSONException {
        setTableConfig();
        importReturnPackage();
        if (returnPackage != null) {
            List<ReturnPackageCommand> ReturnPackageCommand = new ArrayList<ReturnPackageCommand>();
            for (ReturnPackage returnPackage2 : returnPackage) {
                ReturnPackageCommand RPcommand = new ReturnPackageCommand();
                RPcommand.setType(packages.getType());
                RPcommand.setLpcode(returnPackage2.getLpcode());
                RPcommand.setPhyWhId(packages.getPhyWhId());
                RPcommand.setRemarksb(packages.getRemarksb());
                RPcommand.setTrackingNo(returnPackage2.getTrackingNo());
                RPcommand.setRejectionReasons(packages.getRejectionReasons());
                RPcommand.setWhName(packages.getWhName());
                RPcommand.setWeight(returnPackage2.getWeight());
                ReturnPackageCommand.add(RPcommand);
            }
            request.put(JSON, toJson(ReturnPackageCommand));
        }
        return JSON;
    }

    /**
     * 批量导入退换货登记信息校验
     * 
     * @return
     * @throws JSONException
     */
    public String checkImportPackage() throws JSONException {
        JSONObject result = new JSONObject();
        importReturnPackage();
        if (returnPackage != null) {
            if (returnPackage.size() > 200) {
                result.put("message", "error5");
                returnPackage = null;
                request.put(JSON, result);
                return JSON;
            }
            for (ReturnPackage returnPackage2 : returnPackage) {
                int i = 0;
                if (returnPackage2.getTrackingNo() == null || returnPackage2.getLpcode() == null) {
                    result.put("message", "error1");
                    returnPackage = null;
                    request.put(JSON, result);
                    return JSON;
                } else if (returnPackage2.getTrackingNo().toString().length() < 10) {
                    result.put("message", "error2");
                    returnPackage = null;
                    request.put(JSON, result);
                    return JSON;
                } else if (!whExe.lpCodeWhetherValid(returnPackage2.getLpcode())) {
                    result.put("message", "error3");
                    returnPackage = null;
                    request.put(JSON, result);
                    return JSON;
                } else if (returnPackage2.getWeight() != null) {
                    if (returnPackage2.getWeight().compareTo(BigDecimal.ZERO) <= 0) {
                        result.put("message", "error6");
                        returnPackage = null;
                        request.put(JSON, result);
                        return JSON;
                    }

                } else {
                    // 检验是否存在重复信息
                    for (ReturnPackage returnPackage3 : returnPackage) {
                        if (returnPackage2.getTrackingNo().equals(returnPackage3.getTrackingNo()) && returnPackage2.getLpcode().equals(returnPackage3.getLpcode())) {
                            i++;
                        }
                    }
                    if (i >= 2) {
                        result.put("message", "error4");
                        returnPackage = null;
                        request.put(JSON, result);
                        return JSON;
                    }
                }
            }
        }
        result.put("message", "success");
        request.put(JSON, result);
        return JSON;
    }

    public WareHouseManagerExe getWhExe() {
        return whExe;
    }

    public void setWhExe(WareHouseManagerExe whExe) {
        this.whExe = whExe;
    }


    public List<ReturnPackageCommand> getRpcommList() {
        return rpcommList;
    }


    public void setRpcommList(List<ReturnPackageCommand> rpcommList) {
        this.rpcommList = rpcommList;
    }


    public ReturnPackageCommand getComm() {
        return comm;
    }


    public void setComm(ReturnPackageCommand comm) {
        this.comm = comm;
    }


    public String getBatchCode() {
        return batchCode;
    }


    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }


    public void setWhQuery(WareHouseManagerQuery whQuery) {
        this.whQuery = whQuery;
    }


    public void setPrintManager(PrintManager printManager) {
        this.printManager = printManager;
    }


    public File getFile() {
        return file;
    }


    public void setFile(File file) {
        this.file = file;
    }


    public ReturnPackageCommand getPackages() {
        return packages;
    }


    public void setPackages(ReturnPackageCommand packages) {
        this.packages = packages;
    }

}
