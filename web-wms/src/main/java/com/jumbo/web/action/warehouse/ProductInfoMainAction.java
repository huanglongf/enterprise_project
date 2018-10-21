package com.jumbo.web.action.warehouse;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONArray;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.security.UserDetails;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.baseinfo.Brand;
import com.jumbo.wms.model.baseinfo.Customer;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.SkuCommand;

/**
 * 商品信息维护(体积/重量)
 * 
 * @author jinlong.ke
 * 
 */
public class ProductInfoMainAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6400512656170722969L;
    @Autowired
    private WareHouseManagerProxy wareHouseManager;
    @Autowired
    private WareHouseManager whManager;
    @Autowired
    private WareHouseManagerExe wareHouseManagerExe;
    private SkuCommand proCmd;
    private File file;
    private Boolean nikeFlag;
    private String brandName;



    /**
     * 初始跳转页面，只进行页面跳转，不进行其他 操作。
     * 
     * @return
     */
    public String initProductInfo() {
        return SUCCESS;
    }

    /**
     * 初始跳转页面，只进行页面跳转，不进行其他 操作。
     * 
     * @return
     */
    public String initProductInfo2() {
        return SUCCESS;
    }

    /**
     * 查找商品
     * 
     * @return
     */
    public String findProduct() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findAllProduct(tableConfig.getStart(), tableConfig.getPageSize(), proCmd, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 查找所有商品
     */
    public String findProduct2() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findAllProduct2(tableConfig.getStart(), tableConfig.getPageSize(), proCmd, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 导入商品体积重量
     * 
     * @return
     */
    public String updateProductInfo() {
        List<String> errorMsg = new ArrayList<String>();
        if (file == null) {
            return ERROR;
        } else {
            try {
                ReadStatus rs = null;
                if (true == (null == nikeFlag ? false : nikeFlag)) {
                    rs = wareHouseManager.nikeSkuInfoImport(file, brandName);
                } else {
                    rs = wareHouseManager.pdaPurchaseSnImport(file);
                }
                request.put("result", ERROR);
                if (rs.getStatus() == ReadStatus.STATUS_SUCCESS) {
                    request.put("result", SUCCESS);
                }
                errorMsg.addAll(getExcelImportErrorMsg(rs));
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
     * 查询所有品牌名称
     * 
     * @return
     */
    public String findAllBrand() {
        JSONObject json = new JSONObject();
        JSONArray ja = new JSONArray();
        try {
            List<Brand> bList = wareHouseManager.selectAllBrandName();

            for (int i = 0; i < bList.size(); i++) {
                Brand b = bList.get(i);
                JSONObject jo = new JSONObject();
                jo.put("name", b.getName());
                ja.put(jo);
            }

            json.put("brandlist", ja);
        } catch (Exception e) {
            log.debug("ProductInfoMainAction.findAllBrand.exception");
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 查询所有分类名称
     * 
     * @return
     */
    public String findAllCategories() {
        JSONObject json = new JSONObject();
        try {
            json.put("categorieslist", wareHouseManager.selectAllCategoriesName());
        } catch (Exception e) {
            log.debug("ProductInfoMainAction.findAllCategories.exception");
        }
        request.put(JSON, json);
        return JSON;
    }



    /**
     * 分页查询所有品牌名称
     * 
     * @return
     */
    public String findBrandByPage() {
        setTableConfig();
        try {
            request.put(JSON, toJson(wareHouseManager.findBrandByPage(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts())));
        } catch (Exception e) {
            log.debug("ProductInfoMainAction.findBrandByPage.exception");
        }
        return JSON;
    }

    /**
     * 修改商品信息
     * 
     * @return
     * @throws JSONException
     */
    public String editSkuByCode() throws JSONException {
        JSONObject result = new JSONObject();
        try {
        	wareHouseManagerExe.editSkuByCode(proCmd);
            result.put("result", "success");
        } catch (BusinessException e) {
            String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            result.put("result", "error");
            result.put("msg", msg);
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 修改商品所有信息
     * 
     * @return
     * @throws JSONException
     */
    public String editAllSkuByCode() throws JSONException {
        JSONObject result = new JSONObject();
        try {
        	wareHouseManagerExe.editAllSkuByCode(proCmd, userDetails.getUser().getId());
            result.put("result", "success");
        } catch (BusinessException e) {
            String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs());
            result.put("result", "error");
            result.put("msg", msg);
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 判断客户是否阿迪达斯
     * 
     * @return
     */

    public String getCustomerIsAdidas() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            Customer c = whManager.getCustomerIsAdidas(proCmd);
            if ("adidas".equals(c.getCode())) {
                result.put("brand", "1");// 是ad客户
            } else {
                result.put("brand", "0");
            }
            result.put("result", "success");
        } catch (BusinessException e) {
            result.put("result", "error");
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 判断sku barcode
     * 
     * @return
     */

    public String judgeSkuBarCode() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            Sku s = whManager.judgeSkuBarCode(proCmd);
            if (s == null) {
                result.put("brand", "1");// barcode 唯一性
            } else {
                result.put("brand", "0");//
            }

            result.put("result", "success");
        } catch (BusinessException e) {
            result.put("result", "error");
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /*
     * 判断 sku code和CustomerSkuCode唯一性
     */

    public String judgeSkuCodeOrCustomerSkuCode() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            Map<String, String> map = whManager.judgeSkuCodeOrCustomerSkuCode(proCmd);
            String brand = map.get("brand");
            result.put("brand", brand);
            result.put("result", "success");
        } catch (BusinessException e) {
            result.put("result", "error");
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    public SkuCommand getProCmd() {
        return proCmd;
    }

    public void setProCmd(SkuCommand proCmd) {
        this.proCmd = proCmd;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Boolean getNikeFlag() {
        return nikeFlag;
    }

    public void setNikeFlag(Boolean nikeFlag) {
        this.nikeFlag = nikeFlag;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }


    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }
}
