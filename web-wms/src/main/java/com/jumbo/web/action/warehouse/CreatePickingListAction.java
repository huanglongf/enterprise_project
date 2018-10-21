package com.jumbo.web.action.warehouse;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.Constants;
import com.jumbo.manager.channel.packingPage.PackingPageFactory;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.MsgToWcsThread;
import com.jumbo.web.manager.print.PrintManager;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.CreatePickingListManagerProxy;
import com.jumbo.wms.manager.warehouse.SkuSizeConfigManager;
import com.jumbo.wms.manager.warehouse.WareHouseOutBoundManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.automaticEquipment.WcsInterfaceType;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.WhPickingBatchCommand2;
import com.jumbo.wms.model.warehouse.CreatePickingListSql;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.SkuSizeConfig;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;

import loxia.dao.Pagination;
import loxia.support.jasperreport.JasperPrintFailureException;
import loxia.support.jasperreport.JasperReportNotFoundException;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * 新配货逻辑控制
 * 
 * @author jinlong.ke
 * 
 */
public class CreatePickingListAction extends BaseJQGridProfileAction {

    /**
     * 
     */
    private static final long serialVersionUID = -1947536429061445144L;
    @Autowired
    private SkuSizeConfigManager skuSizeConfigManager;
    @Autowired
    private WareHouseOutBoundManager warehouseOutBoundManager;
    @Autowired
    private PrintManager printManager;
    @Autowired
    private CreatePickingListManagerProxy createPickingListManager;


    private String id;
    /**
     * STA
     */
    private StockTransApplication sta;
    /**
     * 是否COD订单
     */
    private Integer isCod;
    /**
     * 查询条件配货清单实体
     */
    private PickingList pickingList;
    /**
     * 开始时间
     */
    private String fromDate;
    /**
     * 结束时间
     */
    private String toDate;
    /**
     * 平台订单开始时间
     */
    private String orderCreateTime;
    /**
     * 平台订单结束时间
     */
    private String toOrderCreateTime;
    /**
     * 店铺列表
     */
    private String shoplist;
    /**
     * 店铺列表
     */
    private String shoplist1;
    /**
     * 优先发货城市
     */
    private String city;
    /**
     * 是否需要发票
     */
    private Integer isNeedInvoice;
    /**
     * 是否包含sn号
     */
    private Integer isSn;
    /**
     * 商品分类
     */
    private Long categoryId;
    /**
     * 需要配货作业单id集合
     */
    private List<Long> staIdList;
    /**
     * 每批作业单数
     */
    private Integer limit;
    /**
     * 每批最少作业单数
     */
    private Integer minAutoSize;

    private String ssList1;
    /**
     * 自动生成批货批次数量
     */
    private Integer plCount;
    /**
     * 每单商品总数
     */
    private Integer sumCount;
    /**
     * 打印拣货单模式一封装对象
     */
    private PickingListCommand plCmd;
    /**
     * 配货清单id
     */
    private Long pickingListId;
    /**
     * 配货清单单据号列表
     */
    private List<Long> plList;
    /**
     * 物流商编码
     */
    private String lpcode;
    /**
     * 文件名
     */
    private String fileName;

    /**
     * 运单时限类型
     */
    private Integer transTimeType;

    /**
     * 套装商品 ID和数量。 格式 skuId;quantity|skuId;quantity|...
     */
    private String skusIdAndQty;

    /**
     * 商品大小件分类配置列表
     */
    private List<SkuSizeConfig> ssList;
    private OperationUnit ou;
    /**
     * 工号
     */
    private String jubNumber1;
    /**
     * 配货类型
     */
    private Long pickType;
    /**
     * 批次号
     */
    private String batchCode;
    /**
     * 新增运单号
     */
    private String trankCode;
    /**
     * 是否需要使用后置打印模板
     */
    private Boolean isPostPrint = false;

    /**
     * 是否QS
     */
    private String isQs;

    /**
     * 分配状态
     */
    private Integer pickStatus;

    /**
     * 分配优先级
     */
    private Long pickSort;

    private String againSort;

    private String whZoneList1;

    /**
     * 重新分配类型。 1： 全部分配，2 查询结果再分配
     */
    private String againType;

    /**
     * 拣货区域列表
     */
    private String areaList;

    private String AeraList1;

    /**
     * 合并拣货区域
     */
    private Integer mergePickZoon;
    /**
     * 合并仓库区域
     */
    private Integer mergeWhZoon;

    /**
     * 仓库区域列表
     */
    private String whAreaList;

    /**
     * 合并仓库区域
     */
    private Boolean isMargeWhZoon;

    private String postshopInputName;


    private String postshopInputName1;
    // private Date createTime;
    //
    // private Date createTime2;
    //
    // private Date startTime;
    //
    // private Date startTime2;


    private String createTime;

    private String createTime2;

    private String startTime;

    private String startTime2;

    private String pCode;
    private String userName;

    private String modeName;

    private String ruleCode;

    private String ruleName;

    private String priority;

    private String isPreSale;

    private WhPickingBatchCommand2 whPickingBatchCommand;

    private String district;

    private String wh_district;

    private String otoAll;

    private Boolean isOtoPicking;

    /**
     * 混合拣货区域（包含所有排列组合）
     */
    private Boolean countAreasCp;

    public String getOtoAll() {
        return otoAll;
    }

    public void setOtoAll(String otoAll) {
        this.otoAll = otoAll;
    }

    public Boolean getIsOtoPicking() {
        return isOtoPicking;
    }

    public void setIsOtoPicking(Boolean isOtoPicking) {
        this.isOtoPicking = isOtoPicking;
    }


    public String getPostshopInputName() {
        return postshopInputName;
    }

    public void setPostshopInputName(String postshopInputName) {
        this.postshopInputName = postshopInputName;
    }

    public String getAeraList1() {
        return AeraList1;
    }

    public void setAeraList1(String aeraList1) {
        AeraList1 = aeraList1;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public WhPickingBatchCommand2 getWhPickingBatchCommand() {
        return whPickingBatchCommand;
    }

    public void setWhPickingBatchCommand(WhPickingBatchCommand2 whPickingBatchCommand) {
        this.whPickingBatchCommand = whPickingBatchCommand;
    }

    public String getpCode() {
        return pCode;
    }

    public void setpCode(String pCode) {
        this.pCode = pCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getCountAreasCp() {
        return countAreasCp;
    }

    public void setCountAreasCp(Boolean countAreasCp) {
        this.countAreasCp = countAreasCp;
    }

    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    private Boolean isOnlyParent = false;

    /**
     * 创建配货清单(单件/多件/团购/秒杀/套装组合商品 打印配货清单页面跳转)
     * 
     * @return
     */
    public String pageRedirect() {
        return SUCCESS;
    }

    public String pickingListLogQuery() {// pda拣货日志 查询
        return SUCCESS;
    }

    /**
     * 配货批次分配
     * 
     * @return
     */
    public String batchDistribution() {
        return SUCCESS;
    }


    public String createPickinglistBySingOrder() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            String msg = warehouseOutBoundManager.createPickinglistBySingOrder(userDetails.getCurrentOu().getId(), district, wh_district, userDetails.getUser().getId());
            result.put("msg", msg);
        } catch (Exception e) {
            result.put("msg", "创批失败！" + e.getMessage());
            log.error("createPickinglistBySingOrder errir", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String addRultName() {
        JSONObject result = new JSONObject();
        String flag =
                warehouseOutBoundManager.addRultNameByOuId(postshopInputName1, postshopInputName, isMargeWhZoon, mergePickZoon, mergeWhZoon, areaList, AeraList1, whAreaList, whZoneList1, ssList1, shoplist, shoplist1, city, fromDate, toDate,
                        orderCreateTime, toOrderCreateTime, sta, categoryId, transTimeType, isSn, isNeedInvoice, isCod, priority, userDetails.getCurrentOu().getId(), userDetails.getUser().getId(), modeName, ruleCode, ruleName);
        try {
            result.put("result", flag);
        } catch (JSONException e) {

        }
        request.put(JSON, result);
        return JSON;
    }

    public String findRulaNameById() {
        JSONObject result = new JSONObject();
        CreatePickingListSql createPickingListSql = warehouseOutBoundManager.findRulaNameById(id);
        try {
            result.put("result", JsonUtil.obj2jsonIncludeAll(createPickingListSql));
        } catch (JSONException e) {

        }
        request.put(JSON, result);
        return JSON;
    }

    public String deleteRultName() {
        JSONObject result = new JSONObject();
        warehouseOutBoundManager.deleteRultName(id);
        try {
            result.put("result", "true");
        } catch (JSONException e) {}
        request.put(JSON, result);
        return JSON;
    }

    /**
     * pda拣货查询
     */
    public String pickingListLogQueryDo() {
        setTableConfig();
        Pagination<WhPickingBatchCommand2> list =
                warehouseOutBoundManager.pickingListLogQueryDo(tableConfig.getStart(), tableConfig.getPageSize(), whPickingBatchCommand, FormatUtil.getDate(startTime), FormatUtil.getDate(startTime2), FormatUtil.getDate(createTime),
                        FormatUtil.getDate(createTime2), null, userDetails.getCurrentOu().getId(), pCode, userName, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime2() {
        return createTime2;
    }

    public void setCreateTime2(String createTime2) {
        this.createTime2 = createTime2;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getStartTime2() {
        return startTime2;
    }

    public void setStartTime2(String startTime2) {
        this.startTime2 = startTime2;
    }

    /**
     * 根据拣货模式查找待配货作业单列表
     * 
     * @return
     */
    public String findStaForPickingByModel() {
        setTableConfig();
        Long ouId = userDetails.getCurrentOu().getId();
        if (ou != null) {
            ouId = ou.getId();
        }
        Pagination<StockTransApplicationCommand> stas =
                warehouseOutBoundManager.findStaForPickingByModel(isMargeWhZoon, mergePickZoon, mergeWhZoon, areaList, whAreaList, ssList, shoplist, shoplist1, getCityList(city), tableConfig.getStart(), tableConfig.getPageSize(),
                        FormatUtil.getDate(fromDate), FormatUtil.getDate(toDate), FormatUtil.getDate(orderCreateTime), FormatUtil.getDate(toOrderCreateTime), sta, categoryId, transTimeType, isSn, isNeedInvoice, isCod, ouId, tableConfig.getSorts(),
                        getCityList(priority), otoAll, countAreasCp);
        request.put(JSON, toJson(stas));
        return JSON;
    }

    /**
     * 根据条件查询相应条件下的可配货的作业单数量
     * 
     * @return
     */
    public String findStaForPickingCount() {
        JSONObject result = new JSONObject();
        Long ouId = userDetails.getCurrentOu().getId();
        if (ou != null) {
            ouId = ou.getId();
        }
        try {
            List<StockTransApplicationCommand> stas =
                    warehouseOutBoundManager.findStaForPickingCount(ssList, shoplist, shoplist1, getCityList(city), FormatUtil.getDate(fromDate), FormatUtil.getDate(toDate), FormatUtil.getDate(orderCreateTime), FormatUtil.getDate(toOrderCreateTime), sta,
                            categoryId, isSn, transTimeType, isNeedInvoice, isCod, ouId);
            result.put("result", stas.size());
        } catch (JSONException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("findStaForPickingCount error:", e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查看作业单明细
     * 
     * @return
     */
    public String findPickingStaLine() {
        setTableConfig();
        Pagination<StaLineCommand> stals = warehouseOutBoundManager.findPickingStaLine(tableConfig.getStart(), tableConfig.getPageSize(), sta.getId(), tableConfig.getSorts());
        request.put(JSON, toJson(stals));
        return JSON;
    }

    /**
     * 手动生成配货清单并占用库存 本次操作分三步进行：1、选择作业单加入配货批 2、判断快递是否可以送达 3、占用库存（最终数据更新）
     * 
     * @return
     * @throws JSONException
     */
    public String generPickingListBySta() throws JSONException {
        JSONObject result = new JSONObject();
        Long ouId = userDetails.getCurrentOu().getId();
        if (ou != null) {
            ouId = ou.getId(); // isCod
        }
        try {
            Long msgId =
                    createPickingListManager.createPickingListBySta(null, staIdList, ouId, userDetails.getUser().getId(), pickingList.getCheckMode(), pickingList.getIsSpecialPackaging(), null, null, getCityList(city), null, null, isCod, isQs,
                            pickingList.getIsOTwoo(), true, false, null, false);
            if (msgId != null) {
                // 多件播种
                MsgToWcsThread wcs = new MsgToWcsThread();
                wcs.setMsgId(msgId);
                wcs.setType(WcsInterfaceType.SBoZhong.getValue());
                Thread thread = new Thread(wcs);
                thread.start();
            }
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            result.put("message", errorMsg);
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 自动生成配货清单
     * 
     * @return
     * @throws JSONException
     */
    public String autoGenerDispatchList() throws JSONException {
        JSONObject result = new JSONObject();
        Long ouId = userDetails.getCurrentOu().getId();
        if (ou != null) {
            ouId = ou.getId();
        }
        try {

            createPickingListManager.autoCreatePickingListBySta(mergePickZoon, mergeWhZoon, isMargeWhZoon, whAreaList, areaList, skusIdAndQty, ssList, shoplist, shoplist1, getCityList(city), sta, transTimeType, minAutoSize, limit, plCount,
                    FormatUtil.getDate(fromDate), FormatUtil.getDate(toDate), FormatUtil.getDate(orderCreateTime), FormatUtil.getDate(toOrderCreateTime), categoryId, isCod, isSn, isNeedInvoice, ouId, userDetails.getUser().getId(),
                    pickingList.getCheckMode(), pickingList.getIsSpecialPackaging(), isQs, sumCount, otoAll, isOtoPicking, countAreasCp);

            result.put("result", SUCCESS);

        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            result.put("message", errorMsg);
            result.put("result", SUCCESS);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 自动生成配货清单不传递PICKINGTYPE
     * 
     * @return
     * @throws JSONException
     */
    public String autoGenerDispatchListByNoPT() throws JSONException {
        JSONObject result = new JSONObject();
        Long ouId = userDetails.getCurrentOu().getId();
        if (ou != null) {
            ouId = ou.getId();
        }
        try {
            createPickingListManager.autoCreatePickingListByStaByNoPT(areaList, skusIdAndQty, ssList, shoplist, shoplist1, getCityList(city), sta, transTimeType, minAutoSize, limit, plCount, FormatUtil.getDate(fromDate), FormatUtil.getDate(toDate),
                    FormatUtil.getDate(orderCreateTime), FormatUtil.getDate(toOrderCreateTime), categoryId, isCod, isSn, isNeedInvoice, ouId, userDetails.getUser().getId(), pickingList.getCheckMode(), pickingList.getIsSpecialPackaging(), isQs, sumCount);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            String errorMsg = "";
            errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
            BusinessException current = e;
            while (current.getLinkedException() != null) {
                current = current.getLinkedException();
                errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
            }
            result.put("message", errorMsg);
            result.put("result", SUCCESS);
        }
        request.put(JSON, result);
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

    /**
     * 查看仓库对应的团购商品
     * 
     * @return
     */
    public String queryGroupBuyingSku() {
        setTableConfig();
        Pagination<Sku> skus = warehouseOutBoundManager.queryGroupBuyingSku(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(skus));
        return JSON;
    }

    /**
     * 失败sta从新占用库存
     * 
     * @return
     */
    public String reOccupiedInvOut() {
        occupiedInv();
        return JSON;
    }

    /**
     * pickinglist中指定状态的sta占用库存
     * 
     * @param status
     */
    private void occupiedInv() {
        String resultStr = SUCCESS;
        String errorMsg = "";
        if (plList != null) {
            for (Long plId : plList) {
                // 如果顺风调用接口
                try {
                    warehouseOutBoundManager.matchingTransNoByPlId(plId);
                    // transOlManagerProxy.matchingTransNo(plId);
                } catch (BusinessException e) {
                    resultStr = ERROR;
                    errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
                    BusinessException current = e;
                    while (current.getLinkedException() != null) {
                        current = current.getLinkedException();
                        errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + current.getErrorCode(), current.getArgs()) + Constants.HTML_LINE_BREAK;
                    }
                    continue;
                }
                try {
                    Long msgId = warehouseOutBoundManager.confirmPickingList(plId, userDetails.getUser().getId(), userDetails.getCurrentOu().getId());
                    if (msgId != null) {
                        // 多件播种
                        MsgToWcsThread wcs = new MsgToWcsThread();
                        wcs.setMsgId(msgId);
                        wcs.setType(WcsInterfaceType.SBoZhong.getValue());
                        Thread thread = new Thread(wcs);
                        thread.start();
                    }
                } catch (BusinessException e) {
                    resultStr = ERROR;
                    errorMsg += this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()) + Constants.HTML_LINE_BREAK;
                }


            }
        }
        JSONObject result = new JSONObject();
        try {
            result.put("result", resultStr);
            result.put("msg", errorMsg);
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
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
            jp = printManager.printPackingPage(plCmd.getId(), plCmd.getStaId(), userDetails.getUser().getId(), isPostPrint, null);
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
     * 打印O2OQS装箱清单列表
     * 
     * @return
     * @throws Exception
     */
    public String printO2OQSPackingListMode1Out() {
        JasperPrint jp;
        try {
            jp = printManager.printPackingPage(plCmd.getId(), plCmd.getPickingListPackageId(), plCmd.getStaId(), userDetails.getUser().getId(), isPostPrint, true, PackingPageFactory.PACKING_PAGE_NIKE_O2OQS);
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

    // / 单张物流面单的打印
    public String printSingleDeliveryMode1Out() {
        JasperPrint jp;
        try {
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

    // 打印新增运单号面单
    public String printSingleDeliveryMode2Out() {
        JasperPrint jp;
        try {
            jp = printManager.printExpressBillByTrankNo(sta.getId(), null, trankCode, userDetails.getUser().getId());
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
     * 查询商品大小件分类
     * 
     * @return
     * @throws Exception
     */
    public String findSkuSize() throws Exception {
        request.put(JSON, JsonUtil.collection2json(skuSizeConfigManager.selectAllConfig()));
        return JSON;
    }

    /**
     * 配货批次分配记录 与账号关联
     * 
     * @return
     */
    public String savePickNode() throws JSONException {
        List<String> errorMsg = new ArrayList<String>();
        JSONObject result = new JSONObject();
        try {
            createPickingListManager.savePickNode(jubNumber1, pickType, batchCode, userDetails.getCurrentOu().getId());
            result.put("msg", SUCCESS);
        } catch (BusinessException e) {
            BusinessException bex = e;
            do {
                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                log.error(msg);
                errorMsg.add(msg);
                bex = bex.getLinkedException();
            } while (bex != null);
            result.put("msg", ERROR);
            result.put("errMsg", JsonUtil.collection2json(errorMsg));
        } catch (Exception e) {
            log.error("", e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查找配货失败的作业单列表
     * 
     * @return
     */
    public String findPickFailedByWhId() {
        setTableConfig();
        Long ouId = userDetails.getCurrentOu().getId();
        if (ou != null) {
            ouId = ou.getId();
        }
        Pagination<StockTransApplicationCommand> stas =
                warehouseOutBoundManager.findPickFailedByWhId(shoplist, transTimeType, lpcode, getCityList(city), isCod, pickStatus, pickSort, categoryId, isNeedInvoice, tableConfig.getStart(), tableConfig.getPageSize(), FormatUtil.getDate(fromDate),
                        FormatUtil.getDate(toDate), FormatUtil.getDate(orderCreateTime), FormatUtil.getDate(toOrderCreateTime), sta, ouId, isPreSale, tableConfig.getSorts());
        request.put(JSON, toJson(stas));
        return JSON;
    }

    public String failedReusltAgainPick() throws JSONException {
        Long ouId = userDetails.getCurrentOu().getId();
        if (ou != null) {
            ouId = ou.getId();
        }
        List<String> errorMsg = new ArrayList<String>();
        JSONObject result = new JSONObject();
        try {
            warehouseOutBoundManager.failedReusltAgainPick(againSort, againType, shoplist, transTimeType, lpcode, getCityList(city), isCod, pickStatus, pickSort, categoryId, isNeedInvoice, FormatUtil.getDate(fromDate), FormatUtil.getDate(toDate),
                    FormatUtil.getDate(orderCreateTime), FormatUtil.getDate(toOrderCreateTime), sta, isPreSale, ouId);
            result.put("msg", SUCCESS);
        } catch (BusinessException e) {
            BusinessException bex = e;
            do {
                String msg = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + bex.getErrorCode(), bex.getArgs());
                log.error(msg);
                errorMsg.add(msg);
                bex = bex.getLinkedException();
            } while (bex != null);
            result.put("msg", ERROR);
            result.put("errMsg", JsonUtil.collection2json(errorMsg));
        } catch (Exception e) {
            log.error("", e);
            result.put("msg", e.getCause() + " " + e.getMessage());
        }
        request.put(JSON, result);
        return JSON;
    }

    public StockTransApplication getSta() {
        return sta;
    }

    public void setSta(StockTransApplication sta) {
        this.sta = sta;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getShoplist() {
        return shoplist;
    }

    public void setShoplist(String shoplist) {
        this.shoplist = shoplist;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getIsNeedInvoice() {
        return isNeedInvoice;
    }

    public void setIsNeedInvoice(Integer isNeedInvoice) {
        this.isNeedInvoice = isNeedInvoice;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public List<Long> getStaIdList() {
        return staIdList;
    }

    public void setStaIdList(List<Long> staIdList) {
        this.staIdList = staIdList;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getPlCount() {
        return plCount;
    }

    public void setPlCount(Integer plCount) {
        this.plCount = plCount;
    }

    public PickingList getPickingList() {
        return pickingList;
    }

    public void setPickingList(PickingList pickingList) {
        this.pickingList = pickingList;
    }

    public PickingListCommand getPlCmd() {
        return plCmd;
    }

    public void setPlCmd(PickingListCommand plCmd) {
        this.plCmd = plCmd;
    }

    public Long getPickingListId() {
        return pickingListId;
    }

    public void setPickingListId(Long pickingListId) {
        this.pickingListId = pickingListId;
    }

    public List<Long> getPlList() {
        return plList;
    }

    public void setPlList(List<Long> plList) {
        this.plList = plList;
    }

    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<SkuSizeConfig> getSsList() {
        return ssList;
    }

    public void setSsList(List<SkuSizeConfig> ssList) {
        this.ssList = ssList;
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

    public String getTrankCode() {
        return trankCode;
    }

    public void setTrankCode(String trankCode) {
        this.trankCode = trankCode;
    }

    public String getJubNumber1() {
        return jubNumber1;
    }

    public void setJubNumber1(String jubNumber1) {
        this.jubNumber1 = jubNumber1;
    }

    public Long getPickType() {
        return pickType;
    }

    public void setPickType(Long pickType) {
        this.pickType = pickType;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getSkusIdAndQty() {
        return skusIdAndQty;
    }

    public void setSkusIdAndQty(String skusIdAndQty) {
        this.skusIdAndQty = skusIdAndQty;
    }

    public Boolean getIsPostPrint() {
        return isPostPrint;
    }

    public void setIsPostPrint(Boolean isPostPrint) {
        this.isPostPrint = isPostPrint;
    }

    public String getShoplist1() {
        return shoplist1;
    }

    public void setShoplist1(String shoplist1) {
        this.shoplist1 = shoplist1;
    }

    public Integer getSumCount() {
        return sumCount;
    }

    public void setSumCount(Integer sumCount) {
        this.sumCount = sumCount;
    }

    public Integer getIsCod() {
        return isCod;
    }

    public void setIsCod(Integer isCod) {
        this.isCod = isCod;
    }

    public String getIsQs() {
        return isQs;
    }

    public void setIsQs(String isQs) {
        this.isQs = isQs;
    }

    public Boolean getIsOnlyParent() {
        return isOnlyParent;
    }

    public void setIsOnlyParent(Boolean isOnlyParent) {
        this.isOnlyParent = isOnlyParent;
    }

    public Integer getPickStatus() {
        return pickStatus;
    }

    public void setPickStatus(Integer pickStatus) {
        this.pickStatus = pickStatus;
    }

    public Long getPickSort() {
        return pickSort;
    }

    public void setPickSort(Long pickSort) {
        this.pickSort = pickSort;
    }

    public String getAgainSort() {
        return againSort;
    }

    public void setAgainSort(String againSort) {
        this.againSort = againSort;
    }

    public String getAgainType() {
        return againType;
    }

    public void setAgainType(String againType) {
        this.againType = againType;
    }

    public String getAreaList() {
        return areaList;
    }

    public void setAreaList(String areaList) {
        this.areaList = areaList;
    }

    public Integer getMinAutoSize() {
        return minAutoSize;
    }

    public void setMinAutoSize(Integer minAutoSize) {
        this.minAutoSize = minAutoSize;
    }

    public Integer getMergePickZoon() {
        return mergePickZoon;
    }

    public void setMergePickZoon(Integer mergePickZoon) {
        this.mergePickZoon = mergePickZoon;
    }

    public Integer getMergeWhZoon() {
        return mergeWhZoon;
    }

    public void setMergeWhZoon(Integer mergeWhZoon) {
        this.mergeWhZoon = mergeWhZoon;
    }

    public String getWhAreaList() {
        return whAreaList;
    }

    public void setWhAreaList(String whAreaList) {
        this.whAreaList = whAreaList;
    }

    public Boolean getIsMargeWhZoon() {
        return isMargeWhZoon;
    }

    public void setIsMargeWhZoon(Boolean isMargeWhZoon) {
        this.isMargeWhZoon = isMargeWhZoon;
    }

    public String getModeName() {
        return modeName;
    }

    public void setModeName(String modeName) {
        this.modeName = modeName;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWhZoneList1() {
        return whZoneList1;
    }

    public void setWhZoneList1(String whZoneList1) {
        this.whZoneList1 = whZoneList1;
    }

    public String getPostshopInputName1() {
        return postshopInputName1;
    }

    public void setPostshopInputName1(String postshopInputName1) {
        this.postshopInputName1 = postshopInputName1;
    }

    public String getSsList1() {
        return ssList1;
    }

    public void setSsList1(String ssList1) {
        this.ssList1 = ssList1;
    }

    public String getIsPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(String isPreSale) {
        this.isPreSale = isPreSale;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getWh_district() {
        return wh_district;
    }

    public void setWh_district(String wh_district) {
        this.wh_district = wh_district;
    }

}
