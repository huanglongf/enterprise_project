package com.jumbo.web.action.warehouse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;

import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WhReplenishManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.warehouse.WarehouseDistrict;
import com.jumbo.wms.model.warehouse.WarehouseLocation;
import com.jumbo.wms.model.warehouse.WhReplenish;
import com.jumbo.wms.model.warehouse.WhReplenishCommand;

/**
 * 仓库库内补货
 * 
 * @author wudan
 * 
 */
public class WarehouseReplenishStockAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = -1779756437419761163L;

    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private WhReplenishManager whReplenishManager;
    private WhReplenish wr;
    @Autowired
    private ExcelExportManager excelExportManager;
    /**
     * 库区List
     */
    private List<WarehouseDistrict> districtList;

    private String LocationCode;

    private String DistrictCode;

    private File file;
    /**
     * 补货报表Id
     */
    private Long wrId;



    /**
     * 指定库区的所有库位信息
     * 
     * @return
     */
    public String findLocationsJson() {
        setTableConfig();

        Pagination<WarehouseLocation> o = wareHouseManager.findLocationsListByOuId(tableConfig.getStart(), tableConfig.getPageSize(), this.userDetails.getCurrentOu().getId(), LocationCode, DistrictCode, tableConfig.getSorts());
        request.put(JSON, toJson(o));
        return JSON;
    }


    /**
     * 库存安全警告数导入设置
     * 
     * @return
     */
    public String importwhReplenishStock() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        } else {
            try {
                ReadStatus rs = excelReadManager.importwhReplenishStock(file, userDetails.getCurrentOu().getId());
                request.put("result", ERROR);
                if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                    request.put("result", SUCCESS);
                } else if (rs.getStatus() > 0) {
                    List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                    for (String s : list) {
                        errorMsg.add(s);
                    }
                } else if (rs.getStatus() < 0) {
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
                request.put("result", ERROR);
                log.error("", e);
                if (e instanceof DataIntegrityViolationException) {
                    String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + ErrorCode.SN_INSERT_UQ_ERROR);
                    errorMsg.add(msg);
                }
            }

            request.put("message", JsonUtil.collection2json(errorMsg));
        }
        return SUCCESS;
    }

    /**
     * 跳转到库内补货报表界面
     * 
     * @return
     */
    public String innerReplenishReport() {
        return SUCCESS;
    }

    /**
     * 申请新的补货报表 KJL
     * 
     * @return
     */
    public String newWhReplenish() {
        JSONObject jo = new JSONObject();
        try {
            jo.put("rs", "error");
            Integer flag = whReplenishManager.createNewWhReplenish(wr, userDetails.getCurrentOu());
            jo.put("rs", "success");
            jo.put("flag", flag);
        } catch (Exception e) {
            log.error(e.getClass().getSimpleName());
        }
        request.put(JSON, jo);
        return JSON;
    }

    /**
     * 查询当前仓库的所有补货报表
     * 
     * @return
     */
    public String findAllWhReplenish() {
        setTableConfig();
        Pagination<WhReplenishCommand> wrList = whReplenishManager.findAllReplenishOrder(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(wrList));
        return JSON;
    }

    /**
     * 取消补货申请
     * 
     * @return
     */
    public String cancelReplenishOrder() {
        JSONObject result = new JSONObject();
        try {
            result.put("rs", "error");
            whReplenishManager.cancelReplenishOrderById(wrId);
            result.put("rs", "success");
        } catch (BusinessException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("cancelReplenishOrder Exception:" + wrId, e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 导出报表
     * 
     * @throws IOException
     */
    public void exportReplenishOrder() throws IOException {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=WAREHOUSE_REPLENISH" + wrId + "" + Constants.EXCEL_XLS);
        ServletOutputStream outs = null;
        try {
            outs = response.getOutputStream();
            excelExportManager.exportreplenishorder(outs, wrId);
        } catch (Exception e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("exportReplenishOrder Exception:" + wrId, e);
            };
        } finally {
            if (outs != null) {
                outs.flush();
                outs.close();
            }
        }
    }

    public List<WarehouseDistrict> getDistrictList() {
        return districtList;
    }

    public void setDistrictList(List<WarehouseDistrict> districtList) {
        this.districtList = districtList;
    }

    public String getLocationCode() {
        return LocationCode;
    }

    public void setLocationCode(String locationCode) {
        LocationCode = locationCode;
    }

    public String getDistrictCode() {
        return DistrictCode;
    }

    public void setDistrictCode(String districtCode) {
        DistrictCode = districtCode;
    }


    public File getFile() {
        return file;
    }


    public void setFile(File file) {
        this.file = file;
    }


    public WhReplenish getWr() {
        return wr;
    }


    public void setWr(WhReplenish wr) {
        this.wr = wr;
    }


    public Long getWrId() {
        return wrId;
    }


    public void setWrId(Long wrId) {
        this.wrId = wrId;
    }



}
