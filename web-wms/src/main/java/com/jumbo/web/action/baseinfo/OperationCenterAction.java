/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
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
package com.jumbo.web.action.baseinfo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import loxia.dao.Pagination;
import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import loxia.support.json.JSONArray;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.Constants;
import com.jumbo.manager.ExcelExportManager;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.util.zip.ZipUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.print.PrintManager;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.warehouse.CheckOutBoundManager;
import com.jumbo.wms.manager.warehouse.PackageSkuManager;
import com.jumbo.wms.manager.warehouse.PickingListPrintManager;
import com.jumbo.wms.manager.warehouse.SecKillSkuManager;
import com.jumbo.wms.manager.warehouse.SkuSizeConfigManager;
import com.jumbo.wms.manager.warehouse.TransportServiceManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.manager.warehouse.WareHouseOutBoundManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.baseinfo.OperationCenter;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.TransEmsInfo;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCheckMode;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.SkuSizeConfig;
import com.jumbo.wms.model.warehouse.SkuSn;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;

public class OperationCenterAction extends BaseJQGridProfileAction implements ServletResponseAware {

    /**
	 * 
	 */
    private static final long serialVersionUID = 8298086647488634100L;
    protected Map<String, Object> maps;
    @Autowired
    private CheckOutBoundManager checkOutBoundManager;
    @Autowired
    private SecKillSkuManager secKillSkuManager;
    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private AuthorizationManager authorizationManager;
    @Autowired
    private WareHouseOutBoundManager warehouseOutBoundManager;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private PickingListPrintManager pickingListPrintManager;
    @Autowired
    private PrintManager printManager;
    @Autowired
    private PackageSkuManager packageSkuManager;
    @Autowired
    private SkuSizeConfigManager skuSizeConfigManager;
    @Autowired
    private TransportServiceManager transportServiceManager;
    @Autowired
    private ExcelExportManager excelExportManager;

    private SkuSn skuSn;
    /**
     * 平台订单开始时间
     */
    private String orderCreateTime;
    /**
     * 平台订单结束时间
     */
    private String toOrderCreateTime;
    /**
     * 是否需要使用后置打印模板
     */
    private Boolean isPostPrint = false;

    public SkuSn getSkuSn() {
        return skuSn;
    }

    public void setSkuSn(SkuSn skuSn) {
        this.skuSn = skuSn;
    }

    private Sku sku;

    /**
     * 运单时限类型
     */
    private Integer transTimeType;

    public Sku getSku() {
        return sku;
    }

    public void setSku(Sku sku) {
        this.sku = sku;
    }

    private OperationUnit ou;

    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    private PickingListCommand cmd;

    public PickingListCommand getCmd() {
        return cmd;
    }

    public void setCmd(PickingListCommand cmd) {
        this.cmd = cmd;
    }

    private String fileName;


    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * 自动生成批货批次数量
     */
    private Integer plCount;

    public Integer getPlCount() {
        return plCount;
    }

    public void setPlCount(Integer plCount) {
        this.plCount = plCount;
    }

    private Long pickinglistId;

    public Long getPickinglistId() {
        return pickinglistId;
    }

    public void setPickinglistId(Long pickinglistId) {
        this.pickinglistId = pickinglistId;
    }

    private Long staId;

    public Long getStaId() {
        return staId;
    }

    public void setStaId(Long staId) {
        this.staId = staId;
    }

    private Long test;

    public Long getTest() {
        return test;
    }

    public void setTest(Long test) {
        this.test = test;
    }

    /**
     * 每批作业单数
     */
    private Integer limit;

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private Long pickingListId;

    public Long getPickingListId() {
        return pickingListId;
    }

    public void setPickingListId(Long pickingListId) {
        this.pickingListId = pickingListId;
    }

    private OperationCenter operationCenter;

    public OperationCenter getOperationCenter() {
        return operationCenter;
    }

    public void setOperationCenter(OperationCenter operationCenter) {
        this.operationCenter = operationCenter;
    }

    /**
     * 结束时间
     */
    private String toDate;

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    /**
     * 优先发货城市
     */
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /**
     * 商品大小件分类配置列表
     */
    private List<SkuSizeConfig> ssList;

    public List<SkuSizeConfig> getSsList() {
        return ssList;
    }

    public void setSsList(List<SkuSizeConfig> ssList) {
        this.ssList = ssList;
    }

    /**
     * 创建时间 下限
     */
    private String fromTime;

    public String getFromTime() {
        return fromTime;
    }

    public void setFromTime(String fromTime) {
        this.fromTime = fromTime;
    }

    /**
     * 创建时间 上限
     */
    private String endTime;

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * STA
     */
    private StockTransApplication sta;

    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    /**
     * 开始时间
     */
    private String fromDate;

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    /**
     * 是否需要发票
     */
    private Integer isNeedInvoice;
    /**
     * 是否包含sn商品
     */
    private Integer isSn;

    public Integer getIsNeedInvoice() {
        return isNeedInvoice;
    }

    public void setIsNeedInvoice(Integer isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }

    /**
     * 店铺列表
     */
    private String shoplist;

    public String getShoplist() {
        return shoplist;
    }

    public void setShoplist(String shoplist) {
        this.shoplist = shoplist;
    }

    /**
     * 商品分类
     */
    private Long categoryId;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    /**
     * 查询条件配货清单实体
     */
    private PickingList pickingList;

    public PickingList getPickingList() {
        return pickingList;
    }

    public void setPickingList(PickingList pickingList) {
        this.pickingList = pickingList;
    }

    private String pickcode;

    public String getPickcode() {
        return pickcode;
    }

    public void setPickcode(String pickcode) {
        this.pickcode = pickcode;
    }

    /**
     * 需要配货作业单id集合
     */
    private List<Long> staIdList;

    public List<Long> getStaIdList() {
        return staIdList;
    }

    public void setStaIdList(List<Long> staIdList) {
        this.staIdList = staIdList;
    }

    private Boolean isOnlyParent = false;
    private Integer pickZoneId;


    public Integer getPickZoneId() {
        return pickZoneId;
    }

    public void setPickZoneId(Integer pickZoneId) {
        this.pickZoneId = pickZoneId;
    }

    /**
     * 根据登录用户查找相应组织(运营中心)信息
     * 
     * @return
     */
    @Override
    public String execute() {
        /**
         * 取得当前登录用户的组织
         */
        ou = getCurrentOu();
        /**
         * 根据组织id查找 相应的运营中心附加信息
         */
        operationCenter = baseinfoManager.findOperationCenterByOuId(getCurrentOu().getId());
        return SUCCESS;
    }

    /**
     * 转到秒杀页面
     */
    public String pickinglistseckillopc() {
        return SUCCESS;
    }

    /**
     * 转到团购页面
     */
    public String creategrouppurchaselist() {
        return SUCCESS;
    }

    /**
     * 编辑修改登录用户的组织(运营中心)信息
     * 
     * @return
     */
    public String updateOperCenterInfo() {
        ou.setId(this.getCurrentOu().getId());
        baseinfoManager.updateOperationCenter(operationCenter, ou);
        return JSON;
    }

    /**
     * 查询仓库关联的店铺打印装箱清单是否按店铺定制
     * 
     * @return
     * @throws JSONException
     */
    public String warehosueIsRelateShopForPrint() throws JSONException {
        String flag = SUCCESS;
        JSONObject result = new JSONObject();
        List<String> trankNames = wareHouseManager.warehosueIsRelateShopForPrint(ou.getId());
        if (trankNames == null || trankNames.isEmpty()) {
            result.put("result", flag);
        } else {
            for (String s : trankNames) {
                if (StringUtils.hasLength(s)) {
                    flag = ERROR;
                    break;
                } else {
                    continue;
                }
            }
            result.put("result", flag);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据仓库id返回所有配货清单
     * 
     * @return
     */
    public String getAllPickingListBywid() {
        setTableConfig();
        Pagination<PickingListCommand> pickinglist;
        if (ou == null || ou.getId() == null) {
            List<Long> lists = new ArrayList<Long>();
            List<OperationUnit> listopc = authorizationManager.selectWarehouseByCenid(userDetails.getCurrentOu().getId());
            for (int i = 0; i < listopc.size(); i++) {
                OperationUnit opc = listopc.get(i);
                lists.add(opc.getId());
            }
            pickinglist = pickingListPrintManager.getAllPickingListsigleopc(tableConfig.getStart(), tableConfig.getPageSize(), pickingList, null, lists, tableConfig.getSorts());
        } else {
            pickinglist = pickingListPrintManager.getAllPickingListsigleopc(tableConfig.getStart(), tableConfig.getPageSize(), pickingList, ou.getId(), null, tableConfig.getSorts());
        }
        request.put(JSON, toJson(pickinglist));
        return JSON;
    }

    /**
     * 配货清单明细
     * 
     * @return
     */
    public String detialList() {
        setTableConfig();
        List<Long> lists = new ArrayList<Long>();
        List<OperationUnit> listopc = authorizationManager.selectWarehouseByCenid(userDetails.getCurrentOu().getId());
        for (int i = 0; i < listopc.size(); i++) {
            OperationUnit opc = listopc.get(i);
            lists.add(opc.getId());
        }
        List<StockTransApplicationCommand> stas = wareHouseManager.findStaListByPickingListopc(pickingListId, lists, null);
        request.put(JSON, toJson(stas));
        return JSON;
    }

    /**
     * 根据用户运营中心id获得仓库
     * 
     * @return
     */
    public String selectByopc() {
        JSONObject result = new JSONObject();
        JSONArray ja = new JSONArray();
        try {
            List<OperationUnit> wareList = authorizationManager.selectWarehouseByCenId(userDetails.getCurrentOu().getId());
            for (int i = 0; i < wareList.size(); i++) {
                OperationUnit ou = wareList.get(i);
                JSONObject jo = new JSONObject();
                jo.put("name", ou.getName());
                jo.put("id", ou.getId());
                ja.put(jo);
            }
            result.put("warelist", ja);
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据拣货模式查找待配货作业单列表
     * 
     * @return
     */
    public String findStaForPickingByModelopc() {
        setTableConfig();
        Long cid = ou.getId();
        Pagination<StockTransApplicationCommand> stas =
                warehouseOutBoundManager.findStaForPickingByModel(null, null, null, null, null, ssList, shoplist, null, getCityList(city), tableConfig.getStart(), tableConfig.getPageSize(), FormatUtil.getDate(fromDate), FormatUtil.getDate(toDate),
                        FormatUtil.getDate(orderCreateTime), FormatUtil.getDate(toOrderCreateTime), sta, categoryId, transTimeType, null, isSn, isNeedInvoice, cid, tableConfig.getSorts(), null, null, false);
        List<StockTransApplicationCommand> sta = stas.getItems();
        for (StockTransApplicationCommand stocks : sta) {
            if (stocks.getIsNeedInvoice() == null) {
                stocks.setIsNeedInvoicecn("否");
            } else {
                stocks.setIsNeedInvoicecn("是");
            }
        }
        request.put(JSON, toJson(stas));
        return JSON;
    }

    /**
     * 转换城市字符串为list
     * 
     * @param str
     * @return
     */
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

    // /**
    // * 查询当前仓库关联的所有店铺（运营中心）
    // *
    // * @return
    // */
    // public String queryShopListMultiCheckopc() {
    // setTableConfig();
    // Long cid = ou.getId();
    // request.put(JSON, toJson(baseinfoManager.queryShopListMultiCheck(cid,
    // tableConfig.getSorts())));
    // return JSON;
    //
    // }

    // 根据当前用户选择的仓库，查询其中所有的定制打印模版的店铺
    public String customiztationTemplShop() throws JSONException {
        JSONObject result = new JSONObject();
        List<String> innerShopCoders = wareHouseManager.customiztationTemplShop(ou.getId());
        /*
         * String rtn = ""; for (String s:innerShopCoders){ rtn += s + "|"; } result.put("result",
         * rtn);
         */
        if (innerShopCoders != null && !innerShopCoders.isEmpty()) {
            if (innerShopCoders != null && !innerShopCoders.isEmpty()) {
                result.put("returnVal", JsonUtil.collection2json(innerShopCoders));
            }
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 根据仓库编号，获取秒杀商品列表
     * 
     * @return
     */
    public String selectAllSecKillSkuByOuId() {
        setTableConfig();
        request.put(JSON, toJson(secKillSkuManager.selectAllSecKillSkuByOu(ou.getId(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 根据仓库编号，获取秒杀商品列表
     * 
     * @return
     */
    public String selectAllPackageSkuByOuId() {
        setTableConfig();
        request.put(JSON, toJson(packageSkuManager.selectAllPackageSkuByOu(ou.getId(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 根据仓库编号，获取配货清单列表
     * 
     * @return
     */
    public String searchCheckListBycenid() throws JSONException {
        setTableConfig();
        List<Long> lists = new ArrayList<Long>();
        List<OperationUnit> listopc = authorizationManager.selectWarehouseByCenid(userDetails.getCurrentOu().getId());
        for (int i = 0; i < listopc.size(); i++) {
            OperationUnit opc = listopc.get(i);
            lists.add(opc.getId());
        }
        if (cmd == null) {
            cmd = new PickingListCommand();
        }
        if (cmd != null) {
            cmd.setPickingTime(FormatUtil.getDate(cmd.getPickingTime1()));
            cmd.setExecutedTime(FormatUtil.getDate(cmd.getExecutedTime1()));
        }
        request.put(JSON, toJson(wareHouseManager.findPickingListForVerifyByCmd1opc(tableConfig.getStart(), tableConfig.getPageSize(), cmd, tableConfig.getSorts(), lists)));
        return JSON;
    }

    /**
     * 根据仓库编号，获取配货清单列表
     * 
     * @return
     */
    public String searchCheckList() throws JSONException {
        setTableConfig();
        List<Long> lists = new ArrayList<Long>();
        if (cmd == null) {
            cmd = new PickingListCommand();
        }
        if (cmd != null) {
            cmd.setPickingTime(FormatUtil.getDate(cmd.getPickingTime1()));
            cmd.setExecutedTime(FormatUtil.getDate(cmd.getExecutedTime1()));
        }
        if (ou.getId() != null) {
            test = null;
            cmd.setWarehouse(authorizationManager.findOperationUnitWarehouseopc(ou.getId()));
        } else {
            List<OperationUnit> listopc = authorizationManager.selectWarehouseByCenid(userDetails.getCurrentOu().getId());
            for (int i = 0; i < listopc.size(); i++) {
                OperationUnit opc = listopc.get(i);
                lists.add(opc.getId());
            }
        }
        request.put(JSON, toJson(wareHouseManager.findPickingListForVerifyByCmd1B(tableConfig.getStart(), tableConfig.getPageSize(), cmd, tableConfig.getSorts(), lists)));
        return JSON;
    }

    /*************************************** 二次分拣意见 *************************************************/

    /**
     * 配货中批次列表
     * 
     * @return
     */
    public String findPickingListForPickOut() {
        setTableConfig();
        List<Long> lists = new ArrayList<Long>();
        List<OperationUnit> listopc = authorizationManager.selectWarehouseByCenid(userDetails.getCurrentOu().getId());
        for (int i = 0; i < listopc.size(); i++) {
            OperationUnit opc = listopc.get(i);
            lists.add(opc.getId());
        }
        if (cmd != null) {
            cmd.setCheckedTime(FormatUtil.getDate(cmd.getCreateTime1()));
            cmd.setExecutedTime(FormatUtil.getDate(cmd.getExecutedTime1()));
        }
        request.put(JSON, toJson(wareHouseManager.findPickingListInfoopc(tableConfig.getStart(), tableConfig.getPageSize(), cmd, lists, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 配货中批次列表 根据仓库id查找
     * 
     * @return
     */
    public String findPickingListForPickOutbywid() {
        setTableConfig();
        List<Long> lists = new ArrayList<Long>();
        if (cmd != null) {
            cmd.setCheckedTime(FormatUtil.getDate(cmd.getCreateTime1()));
            cmd.setExecutedTime(FormatUtil.getDate(cmd.getExecutedTime1()));
        }
        if (ou.getId() != null) {
            request.put(JSON, toJson(wareHouseManager.findPickingListInfo(tableConfig.getStart(), tableConfig.getPageSize(), cmd, ou.getId(), tableConfig.getSorts())));
        } else {
            List<OperationUnit> listopc = authorizationManager.selectWarehouseByCenid(userDetails.getCurrentOu().getId());
            for (int i = 0; i < listopc.size(); i++) {
                OperationUnit opc = listopc.get(i);
                lists.add(opc.getId());
            }
            request.put(JSON, toJson(wareHouseManager.findPickingListInfoopc(tableConfig.getStart(), tableConfig.getPageSize(), cmd, lists, tableConfig.getSorts())));
        }
        // request.put(JSON,
        // toJson(wareHouseManager.findPickingListInfoB(tableConfig.getStart(),
        // tableConfig.getPageSize(), cmd, ou.getId(), lists, test,
        // tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 配货批，涉及的作业单明细 运营中心
     * 
     * @return
     */
    public String findStaLineByPickingId() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManagerQuery.findStaLineByPickingId(pickinglistId, ou.getId(), tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 配货批，涉及的作业单明细 运营中心 无仓库
     * 
     * @return
     */
    public String findStaLineByPickingIdFast() {
        setTableConfig();
        request.put(JSON, toJson(wareHouseManager.findStaLineByPickingIdFast(pickinglistId, tableConfig.getSorts())));
        return JSON;
    }

    /**
     * 运营中心
     * 
     * @return
     * @throws JSONException
     */
    public String getPickCheckList() throws JSONException {
        setTableConfig();
        String[] statu = status.split(",");
        Integer[] statusInteger = new Integer[statu.length];
        for (int i = 0; i < statu.length; i++) {
            statusInteger[i] = Integer.valueOf(statu[i]);
        }
        List<StockTransApplicationCommand> list = wareHouseManager.getPickCheckList(cmd.getId(), ou.getId(), statusInteger);
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 二次分拣意见，配货批核对
     * 
     * @return
     */
    public String checkPinkingListByCode() throws Exception {
        JSONObject result = new JSONObject();
        List<PickingListCommand> list = wareHouseManager.findPickingListInfoFast(cmd, null);
        if (list != null && list.size() == 1) {
            result.put("result", SUCCESS);
            result.put("data", JsonUtil.obj2json(list.get(0)));
        }
        request.put(JSON, result);
        return JSON;
    }

    // 查询当前仓库基本信息，应用是否需要包材 是否允许手动称重
    public String findWarehouseBaseInfo() {
        Warehouse warehouse = baseinfoManager.findWarehouseByOuId(ou.getId());
        if (warehouse != null) {
            JSONObject result = new JSONObject();
            try {
                result.put("result", SUCCESS);
                result.put("warehouse", JsonUtil.obj2json(warehouse));
            } catch (JSONException e) {
                log.error("", e);
            }
            request.put(JSON, result);
        }
        return JSON;
    }

    public String findwarehousebypickid() throws Exception {
        Long cenid = pickingListPrintManager.getWhidBypickid(pickcode);
        Warehouse warehouse = baseinfoManager.findWarehouseByOuId(cenid);
        if (warehouse != null) {
            JSONObject result = new JSONObject();
            try {
                result.put("result", SUCCESS);
                result.put("warehouse", JsonUtil.obj2json(warehouse));
            } catch (JSONException e) {
                log.error("", e);
            }
            request.put(JSON, result);
        }
        return JSON;
    }

    /**
     * 根据查询条件，查询秒杀配货清单，渲染配后清单列表
     * 
     * @return
     */
    public String getAllSecKillPickingListByStatus() {
        setTableConfig();
        List<Long> lists = new ArrayList<Long>();
        List<OperationUnit> listopc = authorizationManager.selectWarehouseByCenid(userDetails.getCurrentOu().getId());
        for (int i = 0; i < listopc.size(); i++) {
            OperationUnit opc = listopc.get(i);
            lists.add(opc.getId());
        }
        Pagination<PickingListCommand> list =
                checkOutBoundManager.getAllSecKillPickingListByStatusopc(tableConfig.getStart(), tableConfig.getPageSize(), FormatUtil.getDate(fromTime), FormatUtil.getDate(endTime), pickingList, ou.getId(), lists, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    // 拣货单打印-new 包含大中小团秒
    public String printPickingListNewMode1() {
        JasperPrint jpJasperPrint = null;
        String psizes = "";
        try {
            Long pcheckmode = pickingListPrintManager.findPickingListByPickId(pickingListId);
            if (pcheckmode == PickingListCheckMode.PICKING_SECKILL.getValue()) {
                psizes = "秒";
            } else if (pcheckmode == PickingListCheckMode.PICKING_GROUP.getValue()) {
                psizes = "团";
            } else if (pcheckmode == PickingListCheckMode.PICKING_PACKAGE.getValue()) {
                psizes = "套";
            } else {
                psizes = skuSizeConfigManager.findBypicklistId(pickingListId);
            }
            jpJasperPrint = printManager.printPickingListNewMode1(pickingListId, pickZoneId, ou.getId(), psizes, userDetails.getUser().getId(), null);
            return printObject(jpJasperPrint);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    // / 单张物流面单的打印
    public String printSingleDeliveryMode1Out() {
        JasperPrint jp;
        try {
            // jp =
            // warehouseOutBoundManager.printSingleDeliveryMode1(sta.getId(),
            // ou.getId());
            jp = printManager.printExpressBillBySta(sta.getId(), isOnlyParent, null, userDetails.getUser().getId());
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

    public void pickingListMode1Export() throws IOException {
        response.setContentType("application/octet-stream;charset=ISO8859-1");
        PickingList pl = wareHouseManager.getPickingListByid(cmd.getId());
        String fileName = "配货清单_" + pl.getCode();
        fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + Constants.EXCEL_XLS);
        ServletOutputStream os = response.getOutputStream();
        try {
            String psizes = "";
            Long pcheckmode = pickingListPrintManager.findPickingListByPickId(pickingListId);
            if (pcheckmode == PickingListCheckMode.PICKING_SECKILL.getValue()) {
                psizes = "秒";
            } else if (pcheckmode == PickingListCheckMode.PICKING_GROUP.getValue()) {
                psizes = "团";
            } else if (pcheckmode == PickingListCheckMode.PICKING_PACKAGE.getValue()) {
                psizes = "套";
            } else {
                psizes = skuSizeConfigManager.findBypicklistId(pickingListId);
            }
            excelExportManager.pickingListMode1Export(cmd, ou.getId(), os, psizes);
            os.flush();
            os.close();
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 打印配货清单-old
     * 
     * @return
     * @throws Exception
     */
    public String printPickingListMode1() {
        JasperPrint jpJasperPrint = null;
        try {
            jpJasperPrint = printManager.printPickingListMode1(cmd, ou.getId(), userDetails.getUser().getId(), null);
            return printObject(jpJasperPrint);
        } catch (Exception e) {
            log.error("", e);
            return null;
        }
    }

    /**
     * 打印装箱清单列表
     * 
     * @return
     * @throws Exception
     */
    public String printTrunkPackingListMode1Out() {
        JasperPrint jp;
        try {
            jp = printManager.printPackingPage(cmd.getId(), cmd.getStaId(), userDetails.getUser().getId(), isPostPrint, null);
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

    /**
     * 物流面单导出
     * 
     * @throws Exception
     */
    public String exportDeliveryInfo() {
        String exepName = wareHouseManager.findexpNameByPlid(pickingListId);
        PickingList pl = wareHouseManager.getPickingListByid(pickingListId);
        String fileName = exepName + "物流面单_" + pl.getCode() + Constants.EXCEL_XLS;
        try {
            fileName = new String(fileName.getBytes("GBK"), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            log.error("", e);
        }
        this.setFileName(fileName);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        excelExportManager.exportDeliveryInfo(out, pickingListId, this.ou.getId());
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        setInputStream(in);
        return SUCCESS;
    }

    /**
     * 物流面单导出
     * 
     * @throws IOException
     * @throws UnsupportedEncodingException
     * @throws Exception
     */
    public String exportDeliveryInfoZip() throws UnsupportedEncodingException, IOException {
        PickingList pl = wareHouseManager.getPickingListByid(pickingListId);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        excelExportManager.exportDeliveryInfo(out, pickingListId, this.ou.getId());
        String fileName = pl.getCode() + Constants.ZIP;
        setInputStream(ZipUtil.zipFileDownloadForExcel(fileName, out));
        setFileName(fileName);
        return SUCCESS;
    }

    /**
     * 物流快递面单套打
     * 
     * @return
     * @throws Exception
     */
    public String printDeliveryInfoMode1() {
        try {
            PickingList pl = pickingListPrintManager.getPickingListById(cmd.getId());
            if (pl.getLpcode() != null && !pl.getLpcode().equals("")) {
                JasperPrint jp = printManager.printExpressBillByPickingListLpCode(cmd.getId(), null, userDetails.getUser().getId());
                return printObject(jp);
            } else {
                List<JasperPrint> jp = new ArrayList<JasperPrint>();
                jp = printManager.printExpressBillByPickingList(cmd.getId(), null, userDetails.getUser().getId());
                return printObject(jp);
            }
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        } catch (JRException e) {
            log.error("", e);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    public String findEmsTrandInfo() throws JSONException {
        JSONObject result = new JSONObject();
        boolean isCod = wareHouseManagerQuery.isCodPickingList(pickingListId);
        TransEmsInfo emsInof = transportServiceManager.findByCmp(isCod);
        if (emsInof == null) {
            result.put(RESULT, ERROR);
        } else {
            result.put(RESULT, SUCCESS);
            result.put("ems", JsonUtil.obj2json(emsInof));
        }
        request.put(JSON, result);
        return JSON;
    }

    public String checkoperationunit() throws JSONException {
        List<Long> lists = new ArrayList<Long>();
        if (userDetails.getCurrentOu().getOuType().getName().equals(OperationUnitType.OUTYPE_OPERATION_CENTER)) {
            List<OperationUnit> listopc = authorizationManager.selectWarehouseByCenid(userDetails.getCurrentOu().getId());
            for (int i = 0; i < listopc.size(); i++) {
                OperationUnit opc = listopc.get(i);
                lists.add(opc.getId());
            }
        }
        request.put(JSON, JsonUtil.collection2json(lists));
        return JSON;
    }

    /**
     * 打印报关清单
     * 
     * @return
     */
    public String printImportEntryList() {
        try {
            List<JasperPrint> jp = new ArrayList<JasperPrint>();
            jp = printManager.printImportEntryList(pickinglistId);
            return printObject(jp);
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        } catch (JRException e) {
            log.error("", e);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 打印澳门报关清单
     * 
     * @return
     */
    public String printImportMacaoEntryList() {
        try {
            List<JasperPrint> jp = new ArrayList<JasperPrint>();
            jp = printManager.printImportEntryLists(pickinglistId);
            return printObject(jp);
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        } catch (JRException e) {
            log.error("", e);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 打印澳门件海关单
     * 
     * @return
     */
    public String printImportMacaoHGDEntryList() {
        try {
            List<JasperPrint> jp = new ArrayList<JasperPrint>();
            jp = printManager.printImportHGDEntryLists(pickinglistId, staId);
            return printObject(jp);
        } catch (JasperReportNotFoundException e) {
            log.error("", e);
        } catch (JasperPrintFailureException e) {
            log.error("", e);
        } catch (JRException e) {
            log.error("", e);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    /**
     * 判断是否允许打印澳门件海关单
     * 
     * @return
     * @throws JSONException
     */
    public String checkIsAllowPrintMacaoHGD() throws JSONException {
        JSONObject result = new JSONObject();
        boolean res = wareHouseManagerQuery.checkIsAllowPrintMacaoHGD(staId, pickinglistId);
        result.put(RESULT, res);
        request.put(JSON, result);
        return JSON;
    }


    public Integer getIsSn() {
        return isSn;
    }

    public void setIsSn(Integer isSn) {
        this.isSn = isSn;
    }

    public Integer getTransTimeType() {
        return transTimeType;
    }

    public void setTransTimeType(Integer transTimeType) {
        this.transTimeType = transTimeType;
    }

    public String getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(String orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public String getToOrderCreateTime() {
        return toOrderCreateTime;
    }

    public void setToOrderCreateTime(String toOrderCreateTime) {
        this.toOrderCreateTime = toOrderCreateTime;
    }

    public Boolean getIsPostPrint() {
        return isPostPrint;
    }

    public void setIsPostPrint(Boolean isPostPrint) {
        this.isPostPrint = isPostPrint;
    }

    public Boolean getIsOnlyParent() {
        return isOnlyParent;
    }

    public void setIsOnlyParent(Boolean isOnlyParent) {
        this.isOnlyParent = isOnlyParent;
    }

}
