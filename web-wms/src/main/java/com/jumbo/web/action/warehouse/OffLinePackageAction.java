/**
 * F * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */
package com.jumbo.web.action.warehouse;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import loxia.dao.Pagination;
import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;
import com.jumbo.web.security.UserDetails;
import com.jumbo.wms.manager.expressDelivery.ChannelInsuranceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuSpType;
import com.jumbo.wms.model.warehouse.BiChannelSpecialActionCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.TransOrder;
import com.jumbo.wms.model.warehouse.TransPackage;
import com.jumbo.wms.model.warehouse.TransPackageCommand;
import com.jumbo.wms.model.warehouse.TransStaRecord;

/**
 * 
 * @author lzb//
 * 
 */
public class OffLinePackageAction extends BaseJQGridProfileAction {
    private static final long serialVersionUID = 4012186321121611461L;
    @Autowired
    private PrintManager printManager;

    @Autowired
    private WareHouseManager wareHouseManager;

    @Autowired
    private ChannelInsuranceManager channelInsuranceManager;
    private TransOrder transOrder;// 线下快递订单
    private TransPackage transPackage;// 线下包裹实体

    private String staTypes;// 指令类型

    private String staIds;// 作业指令

    private String brand;// 作业指令信息数量

    protected UserDetails userDetails;

    private String owner;// 店铺code

    private long id;// 包裹id

    private String transNo;// 包裹面单号

    private long orderId;// 快递订单id

    private String skuId;// 耗材号

    private String skuId2;// sku barcode


    public String getSkuId2() {
        return skuId2;
    }

    public void setSkuId2(String skuId2) {
        this.skuId2 = skuId2;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    private TransPackageCommand packageCommand;// 包裹command

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTransNo() {
        return transNo;
    }

    public void setTransNo(String transNo) {
        this.transNo = transNo;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public TransPackageCommand getPackageCommand() {
        return packageCommand;
    }

    public void setPackageCommand(TransPackageCommand packageCommand) {
        this.packageCommand = packageCommand;
    }

    public TransPackage getTransPackage() {
        return transPackage;
    }

    public void setTransPackage(TransPackage transPackage) {
        this.transPackage = transPackage;
    }

    public TransOrder getTransOrder() {
        return transOrder;
    }

    public void setTransOrder(TransOrder transOrder) {
        this.transOrder = transOrder;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStaTypes() {
        return staTypes;
    }

    public void setStaTypes(String staTypes) {
        this.staTypes = staTypes;
    }

    public String getStaIds() {
        return staIds;
    }

    public void setStaIds(String staIds) {
        this.staIds = staIds;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    /**
     * 初始页面跳转(线下包裹管理)
     * 
     * @return
     */
    public String pageRedirect() {
        return SUCCESS;
    }

    public String zdhPiciManager() {
        return SUCCESS;
    }

    public String zdhPiciImport() {
        return SUCCESS;
    }



    /**
     * 初始页面跳转(线下包裹信息查询)
     * 
     * @return
     */
    public String pageRedirectQuery() {
        return SUCCESS;
    }

    /**
     * 验证作业类型
     * 
     * @return
     * @throws Exception
     */
    public String checkSta() throws Exception {
        int status = 0;
        JSONObject result = new JSONObject();
        String[] staIdArr = staIds.split(",");
        String[] staTypeArr = staTypes.split(",");
        boolean a = Arrays.asList(staIdArr).contains("");
        boolean b = Arrays.asList(staTypeArr).contains("0");
        Map<String, String> map = new HashMap<String, String>();
        List<String> codeList = new ArrayList<String>();
        List<String> codeList2 = new ArrayList<String>();
        List<String> codeList3 = new ArrayList<String>();
        Set<String> set = new ConcurrentSkipListSet<String>();
        if (!"0".equals(brand)) {
            if (a || b) {
                result.put("msg", "errorNull");
                request.put(JSON, result);
                return JSON;
            }
            for (int i = 0; i < staIdArr.length; i++) {// 作业指令遍历
                StockTransApplicationCommand stockTrans = wareHouseManager.findStaByOffLine(staIdArr[i], userDetails.getCurrentOu().getId());
                TransStaRecord record = wareHouseManager.getOneStaRecord(staIdArr[i]);
                if (stockTrans == null) {// 验证该作业指令是否存在
                    result.put("msg", "errorCode");
                    result.put("staCode", staIdArr[i]);
                    request.put(JSON, result);
                    return JSON;
                } else if (!stockTrans.getType().equals(StockTransApplicationType.valueOf(Integer.valueOf(staTypeArr[i])))) {
                    result.put("msg", "errorType");
                    result.put("staCode", staIdArr[i]);
                    request.put(JSON, result);
                    return JSON;
                } else {
                    map.put(stockTrans.getChName(), stockTrans.getCode());
                    if (set.contains(stockTrans.getCode())) {
                        result.put("msg", "errorDouble");
                        result.put("staCode", staIdArr[i]);
                        request.put(JSON, result);
                        return JSON;
                    }
                    set.add(stockTrans.getCode());
                }

                if (stockTrans.getStatus().equals(StockTransApplicationStatus.valueOf(17))) {// 已取消
                    status = 1;
                    codeList.add(staIdArr[i]);
                }
                if (record != null) {
                    status = 1;
                    codeList2.add(staIdArr[i]);
                }
                if (stockTrans.getStatus().equals(StockTransApplicationStatus.valueOf(15))) {// 取消未处理
                    status = 1;
                    codeList3.add(staIdArr[i]);
                }
            }
            if (map.size() != 1) {// 判断是否为同一个店铺
                result.put("msg", "errorChName");
                request.put(JSON, result);
                return JSON;
            }
            result.put("msg2", "1");
            if (status == 1) {
                result.put("msg2", "tipSuccess");
                result.put("codeList", codeList);
                result.put("codeList2", codeList2);
                result.put("codeList3", codeList3);
            }
            StockTransApplicationCommand stockTrans = wareHouseManager.findStaByOffLine(staIdArr[0], userDetails.getCurrentOu().getId());
            result.put("stockTrans", JsonUtil.obj2json(stockTrans));
        }
        result.put("msg", "success");
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 获得仓库相关信息
     * 
     * @return
     * @throws Exception
     */
    public String getWarehouse() throws JSONException {
        JSONObject result = new JSONObject();
        result.put("id", userDetails.getCurrentOu().getId());
        result.put("name", userDetails.getCurrentOu().getName());
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据商品code获得保价金额
     * 
     * @return
     * @throws JSONException
     * @throws Exception
     */
    public String getPriceRange() throws JSONException {
        JSONObject result = new JSONObject();
        BiChannelSpecialActionCommand info = channelInsuranceManager.getInsuranceEn(owner);
        if (null != info) {
            if (info.getMinInsurance() != null && info.getMaxInsurance() != null) {
                result.put("msg", "success");
                result.put("BiChannel", JsonUtil.obj2json(info));
            } else {
                result.put("msg", "error");
            }
        } else {
            result.put("msg", "error");
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * insertTransOrder 保存快递订单与包裹订单
     */
    public String insertTransOrder() throws JSONException {
        String msg = "success";
        Date date = new Date();
        JSONObject result = new JSONObject();
        transOrder.setCreateTime(date);
        transOrder.setLastModifyTime(date);
        transOrder.setStatus(1);
        // transOrder.setOuId(userDetails.getCurrentOu().getId());
        transOrder.setOpUnit(userDetails.getCurrentOu());
        // transOrder.setCreateUserId(userDetails.getUser().getId());
        transOrder.setCreateUser(userDetails.getUser());
        Map<String, Object> map = null;
        try {
            map = wareHouseManager.saveTransOrder(transOrder, staIds, userDetails.getCurrentOu().getId());
        } catch (Exception e) {
            msg = "error";
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("insertTransOrder error:" + staIds, e);
            };
            request.put(JSON, result);
            return JSON;
        }
        result.put("order", JsonUtil.obj2json(map.get("order")));
        result.put("msg", msg);
        result.put("trans", map.get("trans"));
        request.put(JSON, result);
        return JSON;
    }

    /*
     * insertTransPackage 跟新包裹订单
     */
    public String insertTransPackage() throws JSONException {
        String msg = "success";
        JSONObject result = new JSONObject();
        result.put("brand", "0");
        transPackage.setOpUser(userDetails.getUser());
        TransPackage pack = null;
        if ("0".equals(brand)) {// 保存验证验证面单号是否已经存在
            TransPackageCommand trans = wareHouseManager.getOneTransPackage2(transPackage);
            if (trans != null) {// 面单号已存在不允许插入
                result.put("msg", "error");
                result.put("brand", "1");// 面单号已存在
                request.put(JSON, result);
                return JSON;
            }
        }
        try {
            pack = wareHouseManager.saveOffLineTransPackage(transPackage, brand, skuId2);
            result.put("pack", JsonUtil.obj2json(pack));
        } catch (Exception e) {
            msg = "error";
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("insertTransPackage error:" + skuId2, e);
            };

        }
        result.put("msg", msg);
        request.put(JSON, result);
        return JSON;
    }

    /*
     * 分页查询线下包裹信息
     */
    public String getTransPackagePage() {
        setTableConfig();
        Pagination<TransPackageCommand> list = wareHouseManager.getTransPackagePage(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), packageCommand, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 获取staList queryStas
     * 
     * @throws Exception
     */
    public String queryStas() throws Exception {
        setTableConfig();
        List<StockTransApplicationCommand> list = wareHouseManager.queryStas(id, orderId, transNo, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 获得getOneTransPackage
     */
    public String getOneTransPackage() throws Exception {
        JSONObject result = new JSONObject();
        TransPackageCommand pack = wareHouseManager.getOneTransPackageDetail(id, transNo);
        result.put("pack", JsonUtil.obj2json(pack));
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 获得getOneTransPackage根据耗材号和运单号
     */
    public String getOneTransPackage2() throws Exception {
        TransPackage pg = new TransPackage();
        pg.setTransNo(transNo);
        JSONObject result = new JSONObject();
        TransPackageCommand pack = wareHouseManager.getOneTransPackage2(pg);
        if (pack == null) {
            result.put("brand", "yes");
        } else {
            result.put("brand", "no");
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 包裹重启重update
     * 
     * */
    public String updateTransPackage() throws JSONException {
        String msg = "success";
        JSONObject result = new JSONObject();
        try {
            wareHouseManager.updateTransPackage(transPackage, skuId2);
        } catch (Exception e) {
            msg = "error";
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("updateTransPackage error:" + skuId2, e);
            };

        }
        result.put("msg", msg);
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 线下包裹打印电子面单
     */
    public String printSingleOrderDetailOutMode1() {
        TransPackageCommand pack = wareHouseManager.getOneTransPackage(id, transNo);
        JasperPrint jp;
        try {
            jp = printManager.printOffLineExpressBillBySta(pack, false, true, userDetails.getUser().getId(), userDetails.getCurrentOu().getId());
            return printObject(jp);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        } catch (JRException e) {
            log.error("", e);
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (Exception e) {
            // log.error("", e);
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("printSingleOrderDetailOutMode1 error:", e);
            };
        }
        return null;
    }

    /**
     * 根据耗材barCode来获取体积（长*宽*高）
     * 
     * @throws JSONException
     */
    public String getVolumnByBarCode() throws JSONException {
        JSONObject result = new JSONObject();
        Sku sku = wareHouseManager.getSkuByBarcode1(skuId);
        if (sku == null) {
            result.put("brand", "1");
            result.put("tip", "库存没有此耗材");
        } else if (sku.getSpType() == null || sku.getSpType() != SkuSpType.CONSUMPTIVE_MATERIAL) {
            result.put("brand", "1");
            result.put("tip", "该商品不为耗材");
        } else {
            BigDecimal length = sku.getLength();
            BigDecimal width = sku.getWidth();
            BigDecimal heigth = sku.getHeight();
            if (length == null) {
                length = new BigDecimal(0.0);
            }
            if (width == null) {
                width = new BigDecimal(0.0);
            }
            if (heigth == null) {
                heigth = new BigDecimal(0.0);
            }
            Double volumn = length.multiply(width).multiply(heigth).divide(new BigDecimal(1000000000)).doubleValue();

            if (new BigDecimal(0.01).compareTo(new BigDecimal(volumn)) >= 0) {
                volumn = 0.0;
            }
            DecimalFormat df = new DecimalFormat("#.##");
            // get_double = Double.ParseDouble(df.format(result_value));
            result.put("brand", "2");
            result.put("volumn", df.format(volumn));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据code查询店铺信息
     */
    public String getChannelByCode() throws JSONException {
        JSONObject result = new JSONObject();
        BiChannel channel = wareHouseManager.getChannelByCode(skuId);
        if (channel != null) {
            result.put("brand", "1");
            result.put("channel", JsonUtil.obj2json(channel));
        } else {
            result.put("brand", "2");
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * getNum 获取未交接运单数与交接上限
     */
    public String getNum() throws JSONException {
        JSONObject result = new JSONObject();
        Map<String, Object> map = wareHouseManager.getNum(userDetails.getUser().getId(), userDetails.getCurrentOu().getId());
        result.put("maxNum", map.get("maxNum"));
        result.put("num", map.get("num"));
        request.put(JSON, result);
        return JSON;
    }
}
