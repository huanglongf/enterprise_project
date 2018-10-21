package com.jumbo.web.action.warehouse;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.PackageSkuManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.util.FormatUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * 套装组合商品维护控制逻辑
 * 
 * @author jinlong.ke
 * 
 */
public class PackageSkuMaintainAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = -8193787918251085013L;
    @Autowired
    private PackageSkuManager packageSkuManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    private Long staTotalSkuQty;
    private List<String> titleList;
    private List<Long> idList;
    private String expireDate;
    private Long skuQty;
    private Long packageSkuId;

    /**
     * 初始化跳转页面
     * 
     * @return
     */
    public String packageSkuMaintain() {
        return SUCCESS;
    }

    /**
     * 添加新的套装组合商品
     * 
     * @return
     * @throws JSONException
     */
    public String addPackageSku() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            result.put("rs", "error");
            if (FormatUtil.getDate(expireDate) != null) {
                if (FormatUtil.getDate(expireDate).before(new Date())) {
                    // 过期时间不能小于创建时间
                    throw new BusinessException(ErrorCode.PACKAGESKU_EXPIRETIMEBIG);
                }
            }
            Boolean b = wareHouseManager.getByOuId(userDetails.getCurrentOu().getId()).getIsSupportPackageSku();
            if(b==null||!b){
                String errorMsg = getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.NOT_SUPPORT_PACKAGESKU), new Object[] {""});
                result.put("errorMsg", errorMsg);
            }else{
                packageSkuManager.addNewPackageSku(userDetails.getCurrentOu(), staTotalSkuQty, skuQty, idList, titleList, FormatUtil.getDate(expireDate));
                result.put("rs", "success");
            }
        } catch (BusinessException e) {
            String errorMsg = getMessage((ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode()), new Object[] {""});
            result.put("errorMsg", errorMsg);
            log.debug("PackageSkuMaintainAction.addPackageSku------------------------>BusinessException");
            log.error("", e);
        } catch (Exception e) {
            // 创建套装组合商品失败
            String errorMsg = getMessage((ErrorCode.BUSINESS_EXCEPTION + ErrorCode.PACKAGESKU_FAILED), new Object[] {""});
            result.put("errorMsg", errorMsg);
            log.debug("PackageSkuMaintainAction.addPackageSku------------------------>Exception");
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 删除套装组合商品
     * 
     * @return
     */
    public String deletePackageSkuById() {
        JSONObject result = new JSONObject();
        try{
            result.put("rs", "error");
            packageSkuManager.deletePackageSkuById(packageSkuId,userDetails.getCurrentOu().getId());
            result.put("rs", "success");
        }catch(Exception e){
            log.debug("PackageSkuMaintainAction.deletePackageSkuById---------------------->Exception");
            log.error("",e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查询所有的套装组合商品
     * 
     * @return
     */
    public String selectAllPackageSku() {
        setTableConfig();
        request.put(JSON, toJson(packageSkuManager.selectAllPackageSkuByOu(userDetails.getCurrentOu().getId(), tableConfig.getSorts())));
        return JSON;
    }

    public Long getStaTotalSkuQty() {
        return staTotalSkuQty;
    }

    public void setStaTotalSkuQty(Long staTotalSkuQty) {
        this.staTotalSkuQty = staTotalSkuQty;
    }

    public List<String> getTitleList() {
        return titleList;
    }

    public void setTitleList(List<String> titleList) {
        this.titleList = titleList;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        if (expireDate == null || expireDate.equals("")) {
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DATE, 1);
            expireDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(c.getTime());
        }
        this.expireDate = expireDate;
    }

    public Long getSkuQty() {
        return skuQty;
    }

    public void setSkuQty(Long skuQty) {
        this.skuQty = skuQty;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }

    public Long getPackageSkuId() {
        return packageSkuId;
    }

    public void setPackageSkuId(Long packageSkuId) {
        this.packageSkuId = packageSkuId;
    }
}
