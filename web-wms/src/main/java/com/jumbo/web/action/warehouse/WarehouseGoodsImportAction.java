package com.jumbo.web.action.warehouse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.baseinfo.SkuDeclarationCommand;

import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONObject;

public class WarehouseGoodsImportAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = 2737711797115310065L;

    @Autowired
    private BaseinfoManager baseinfoManager;

    private ExcelReadManager excelReadManager;

    private File file;

    private String owner;

    private String upc;

    private String hsCode;

    private String skuCode;

    private String style;

    private String color;

    private String skuSize;

    private int isDiscount;

    private int status;

    private String skuName;

    private int isService;

    private Long id;


    public String goodsImport() throws Exception {
        try {
            ReadStatus rs = excelReadManager.goodsImport(file);
            if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                request.put("result", SUCCESS);
                request.put("msg", "操作成功");
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
    }



    public String findGoodsList() {
        setTableConfig();
        Pagination<SkuDeclarationCommand> list = baseinfoManager.findGoodsList(tableConfig.getStart(), tableConfig.getPageSize(), owner, upc, hsCode, skuCode, style, color, skuSize, isDiscount, status, skuName, isService, tableConfig.getSorts());
        List<SkuDeclarationCommand> list1 = new ArrayList<SkuDeclarationCommand>();
        for (SkuDeclarationCommand skuDeclarationCommand : list.getItems()) {
            list1.add(skuDeclarationCommand);
        }
        list.setItems(list1);
        request.put(JSON, toJson(list));
        return JSON;
    }


    public String deletefindGoodsListById() {
        JSONObject ob = new JSONObject();
        try {
            baseinfoManager.deletefindGoodsListById(id);
            ob.put("msg", "success");
        } catch (Exception e) {
            log.error("deletefindGoodsListById is error :" + e);
        }
        request.put(JSON, ob);
        return JSON;
    }

    public String pushGoods() {
        JSONObject ob = new JSONObject();
        try {
            baseinfoManager.pushGoods(id);
            ob.put("msg", "success");
        } catch (Exception e) {
            log.error("pushGoods is error :" + e);
        }
        request.put(JSON, ob);
        return JSON;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }



    public ExcelReadManager getExcelReadManager() {
        return excelReadManager;
    }



    public void setExcelReadManager(ExcelReadManager excelReadManager) {
        this.excelReadManager = excelReadManager;
    }



    public String getOwner() {
        return owner;
    }



    public void setOwner(String owner) {
        this.owner = owner;
    }



    public String getUpc() {
        return upc;
    }



    public void setUpc(String upc) {
        this.upc = upc;
    }



    public String getHsCode() {
        return hsCode;
    }



    public void setHsCode(String hsCode) {
        this.hsCode = hsCode;
    }



    public String getSkuCode() {
        return skuCode;
    }



    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }



    public String getStyle() {
        return style;
    }



    public void setStyle(String style) {
        this.style = style;
    }



    public String getColor() {
        return color;
    }



    public void setColor(String color) {
        this.color = color;
    }



    public String getSkuSize() {
        return skuSize;
    }



    public void setSkuSize(String skuSize) {
        this.skuSize = skuSize;
    }



    public int getIsDiscount() {
        return isDiscount;
    }



    public void setIsDiscount(int isDiscount) {
        this.isDiscount = isDiscount;
    }



    public int getStatus() {
        return status;
    }



    public void setStatus(int status) {
        this.status = status;
    }



    public String getSkuName() {
        return skuName;
    }



    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }



    public int getIsService() {
        return isService;
    }



    public void setIsService(int isService) {
        this.isService = isService;
    }



    public Long getId() {
        return id;
    }



    public void setId(Long id) {
        this.id = id;
    }

}
