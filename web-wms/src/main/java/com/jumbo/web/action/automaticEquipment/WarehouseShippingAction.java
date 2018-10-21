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
package com.jumbo.web.action.automaticEquipment;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.util.JsonUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.web.manager.MsgToWcsThread;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.automaticEquipment.AutoBaseInfoManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.automaticEquipment.GoodsCollection;
import com.jumbo.wms.model.automaticEquipment.ShippingPoint;
import com.jumbo.wms.model.automaticEquipment.WcsInterfaceType;
import com.jumbo.wms.model.command.automaticEquipment.CheckingSpaceRoleCommand;
import com.jumbo.wms.model.command.automaticEquipment.ShippingPointCommand;
import com.jumbo.wms.model.command.automaticEquipment.ShippingPointRoleLineCommand;
import com.jumbo.wms.model.odo.OdoCommand;
import com.jumbo.wms.model.odo.OdoLineCommand;
import com.jumbo.wms.model.warehouse.PhysicalWarehouse;
import com.jumbo.wms.web.commond.GoodsCollectionCommand;

import loxia.dao.Pagination;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

/**
 * @author xiaolong.fei
 * 
 */
public class WarehouseShippingAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = -6561923848716612530L;
    @Autowired
    private AutoBaseInfoManager autoBaseInfoManager;

    /**
     * 集货口id
     */
    private String id;

    private int status;

    /**
     * 集货规则
     */
    private String code;
    /**
     * 集货编码
     */
    private String name;
    /**
     * wsc编码
     */
    private String wcsCode;

    /**
     * 集货口类型
     */
    private String pointType;

    /**
     * 单次循环最大负载量
     */
    private Long maxAssumeNumber;

    /**
     * 地区类型
     */
    private Long type;

    /**
     * 地区父节点ID
     */
    private Long parentId;

    /**
     * 规则ID
     */
    private Long roleId;

    /**
     * 规则明细
     */
    private ShippingPointRoleLineCommand roleLine;


    private GoodsCollectionCommand goodsCollection;

    /**
     * 文件导入
     */
    private File file;

    /**
     * 集货口ID
     */
    private Long pointId;
    /**
     * 店铺
     */
    private String owner;

    /**
     * oto门店
     */
    private String toLocation;

    /**
     * 是否QS
     */
    private Long isQs;
    /**
     * 是否特殊处理
     */
    private Long isSpaice;

    /**
     * 优先级
     */
    private Integer sort;

    private String collectionCode;

    private String popUpCode;

    /**
     * 工作台编码
     */
    private String checkCode;

    /**
     * 1： 工作台 2: 播种墙
     */
    private Integer menuType;

    /**
     * 负载均衡集货口的关联id
     */
    private Long refShippingPointId;
    /**
     * 负载均衡集货口list
     */
    private List<ShippingPoint> addList;

    /**
     * 操作类型
     */
    private String oper;

    /**
     * 优先发货城市
     */
    private String city;

    /**
     * 物流商
     */
    private String lpcode;
    /**
     * 集货口及关联IDs
     */
    private String arrIds;
    /**
     * 集货规则IDS
     */
    private String batchIds;

    /**
     * 运单时限类型
     * 
     * @return
     */
    private Integer transTimeType;

    /**
     * 指定商品
     * 
     * @return
     */
    private String skuCodes;

    private Long physicalId;
    
    private String isPreSale;

    private String passWay;// 通道

    private String pickModel;// 拣货模式

    private int fullTime;// 集满超时预警时效

    private int notFullTime;// 未集满超时预警时效

    private int ouId;

    private OdoCommand odoCommand;


    public int getOuId() {
        return ouId;
    }

    public void setOuId(int ouId) {
        this.ouId = ouId;
    }

    public Long getPhysicalId() {
        return physicalId;
    }

    public void setPhysicalId(Long physicalId) {
        this.physicalId = physicalId;
    }

    public String getSkuCodes() {
        return skuCodes;
    }

    public void setSkuCodes(String skuCodes) {
        this.skuCodes = skuCodes;
    }

    public Integer getTransTimeType() {
        return transTimeType;
    }

    public void setTransTimeType(Integer transTimeType) {
        this.transTimeType = transTimeType;
    }

    public String getBatchIds() {
        return batchIds;
    }

    public void setBatchIds(String batchIds) {
        this.batchIds = batchIds;
    }

    public String getArrIds() {
        return arrIds;
    }

    public void setArrIds(String arrIds) {
        this.arrIds = arrIds;
    }


    public String shippingCollection() {
        return SUCCESS;
    }

    /**
     * 页面访问
     * 
     * @return
     */
    public String shippingrolePoint() {
        return SUCCESS;
    }

    /**
     * 工作台核对规则页面访问
     * 
     * @return
     */
    public String initcheckSpaceRole() {
        return SUCCESS;
    }

    /**
     * 查询集货点
     * 
     * @return
     */
    public String shippingPointQuery() {
        setTableConfig();
        Pagination<ShippingPointCommand> group = autoBaseInfoManager.findShippingPointList(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), userDetails.getCurrentOu().getId(), code, name, wcsCode);
        request.put(JSON, toJson(group));
        return JSON;
    }


    public String findOdOAllQuery() {
        setTableConfig();
        Pagination<OdoCommand> odoCommandList = autoBaseInfoManager.findOdOAllQuery(tableConfig.getStart(), tableConfig.getPageSize(), odoCommand, tableConfig.getSorts());
        // Pagination<ShippingPointCommand> group =
        // autoBaseInfoManager.findShippingPointList(tableConfig.getStart(),
        // tableConfig.getPageSize(), tableConfig.getSorts(), userDetails.getCurrentOu().getId(),
        // code, name, wcsCode);
        request.put(JSON, toJson(odoCommandList));
        return JSON;
    }

    public String odoOutBoundDetail() {
        setTableConfig();
        Pagination<OdoLineCommand> odoCommandList = autoBaseInfoManager.odoOutBoundDetail(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), id, status);
        request.put(JSON, toJson(odoCommandList));
        return JSON;
    }


    public String odoOutBoundDetailList() {
        JSONObject result = new JSONObject();
        Boolean flag = autoBaseInfoManager.odoOutBoundDetailList(id, status);
        try {
            result.put("msg", flag);
        } catch (JSONException e) {
            log.error("odoOutBoundDetailList:", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String odoOuIdList() {
        List<OperationUnit> list = autoBaseInfoManager.odoOuIdList(id);
        request.put(JSON, JsonUtil.collection2json(list));
        return JSON;
    }

    public String findOdoLineByOdoId() {
        JSONObject result = new JSONObject();
        Boolean flag = autoBaseInfoManager.findOdoLineByOdoId(id, ouId);
        try {
            result.put("msg", flag);
        } catch (JSONException e) {
            log.error("findOdoLineByOdoId:", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查询集货区域配置
     * 
     * @return
     */
    public String shippingPointCollectionQuery() {
        setTableConfig();
        Pagination<GoodsCollectionCommand> group =
                autoBaseInfoManager.findShippingPointCollectionList(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), goodsCollection.getPhysicalId().getId(), goodsCollection.getCollectionCode(), goodsCollection.getSort(),
                        goodsCollection.getPopUpCode(), goodsCollection.getPlCode(), goodsCollection.getContainer(), goodsCollection.getPassWay(), goodsCollection.getPickModel());
        request.put(JSON, toJson(group));
        return JSON;
    }


    public String checkPopupArea() {
        JSONObject result = new JSONObject();
        boolean msg = autoBaseInfoManager.checkPopupArea(popUpCode);
        try {
            result.put("msg", msg);
        } catch (JSONException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("checkPopupArea JSONException:", e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    public String findPhysicalWarehouse() {
        List<PhysicalWarehouse> list = autoBaseInfoManager.findPhysicalWarehouse();
        request.put(JSON, JsonUtil.collection2json(list));
        return JSON;
    }

    // 导入数据
    public String importCollection() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = autoBaseInfoManager.importShippingCollect(file, id, userDetails.getUser());
                if (rs != null) {
                    if (rs.getExceptions().size() > 0) request.put("msg", rs.getExceptions().get(0).getMessage());
                }
                if (rs == null) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                } else if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
                    request.put("msg", "");
                    List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                    request.put("msg", list);
                } else if (rs.getStatus() < ReadStatus.STATUS_SUCCESS) {
                    request.put("msg", "");
                    List<String> list = new ArrayList<String>();
                    for (Exception ex : rs.getExceptions()) {
                        if (ex instanceof BusinessException) {
                            BusinessException be = (BusinessException) ex;
                            list.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()) + Constants.HTML_LINE_BREAK);
                        }
                    }
                    request.put("msg", list);
                }
            } catch (BusinessException e) {
                request.put("msg", ERROR);
                log.error("", e);
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                log.error(e.getMessage());
                request.put("msg", sb.toString());
            } catch (Exception e) {
                request.put("msg", ERROR);
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    // 下载模板


    public String saveShippingPointCollection() {
        JSONObject result = new JSONObject();
        String msg = autoBaseInfoManager.saveCollection(collectionCode, physicalId, sort, userDetails.getUser(), popUpCode, passWay, pickModel);
        try {
            result.put("msg", msg);
        } catch (JSONException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("saveShippingPointCollection JSONException:", e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    public String updateShippingPointCollection() {
        JSONObject result = new JSONObject();
        String msg = autoBaseInfoManager.updateShippingPointCollection(collectionCode, sort, userDetails.getUser(), popUpCode, id, passWay, pickModel);
        try {
            result.put("msg", msg);
        } catch (JSONException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("updateShippingPointCollection JSONException:", e);
            };
        }
        request.put(JSON, result);
        return JSON;

    }


    public String deleteShippingPointCollection() {
        JSONObject result = new JSONObject();
        if (!StringUtil.isEmpty(arrIds)) {
            String[] arrStr = arrIds.split(",");
            try {
                autoBaseInfoManager.deleteShippingPointCollection(arrStr);
                result.put("result", SUCCESS);
            } catch (Exception e) {
                log.error("deleteShippingPointCollection" + e);
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    public String findshippingpointStatus() {
        GoodsCollection goodsCollection = autoBaseInfoManager.findshippingpointStatus(id);
        request.put(JSON, JsonUtil.obj2jsonIncludeAll(goodsCollection));
        return JSON;
    }

    /**
     * 查询负载均衡集货点
     * 
     * @return
     */
    public String assumeShippingPointQuery() {
        setTableConfig();
        Pagination<ShippingPoint> group = autoBaseInfoManager.findAssumeShippingPointList(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), userDetails.getCurrentOu().getId(), refShippingPointId);
        request.put(JSON, toJson(group));
        return JSON;
    }

    /**
     * 保存或修改集货点
     * 
     * @return
     * @throws JSONException
     */
    public String saveOrUpdatePoint() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            String result2 = autoBaseInfoManager.saveShippingPoint(id, code, name, wcsCode, pointType, maxAssumeNumber, refShippingPointId, oper, pointId, userDetails.getCurrentOu().getId(), userDetails.getUser().getId());
            result.put("result", SUCCESS);
            result.put("result2", result2);
        } catch (BusinessException e) {
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 保存或修改负载均衡集货点
     * 
     * @return
     * @throws JSONException
     */
    public String saveOrUpdateAssumePoint() throws JSONException {
        @SuppressWarnings("unused")
        List<ShippingPoint> list = new ArrayList<ShippingPoint>();
        list = addList;
        JSONObject result = new JSONObject();
        try {
            String result2 = autoBaseInfoManager.saveAssumeShippingPoint(addList);
            result.put("result", SUCCESS);
            result.put("result2", result2);
        } catch (BusinessException e) {
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 删除集货点
     * 
     * @return
     * @throws JSONException
     */
    public String deleteShippingPoint() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            autoBaseInfoManager.deleteShippingById(id);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 保存或修改工作台核对规则
     * 
     * @return
     * @throws JSONException
     */
    public String saveOrUpdateCheckRoleLine() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            String result2 = autoBaseInfoManager.saveCheckSpace(transTimeType, skuCodes, city, lpcode, roleId, owner, toLocation, isQs, isSpaice, sort, userDetails.getCurrentOu().getId(), checkCode, menuType, isPreSale);
            result.put("result", SUCCESS);
            result.put("result2", result2);
        } catch (BusinessException e) {
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查询地区
     * 
     * @return
     */
    public String findAreaByPaream() {
        JSONObject json = new JSONObject();
        try {
            if (type == null) {
                // 查找省市区集合
                json.put("provinceList", autoBaseInfoManager.findAreaByPaream(1l, null));
                json.put("cityList", autoBaseInfoManager.findAreaByPaream(2l, null));
                json.put("districtList", autoBaseInfoManager.findAreaByPaream(3l, null));
            } else {
                // 根据类型父节点查找集合
                json.put("areaList", autoBaseInfoManager.findAreaByPaream(type, parentId));
            }
        } catch (Exception e) {
            log.debug("ProductInfoMainAction.findAllCategories.exception");
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 查询弹出口
     * 
     * @return
     */
    public String findpopAreaList() {
        JSONObject json = new JSONObject();
        try {
            json.put("areaList", autoBaseInfoManager.findpopAreaList());
        } catch (Exception e) {
            log.debug("findpopAreaList.exception");
        }
        request.put(JSON, json);
        return JSON;
    }


    /**
     * 查询集货口
     * 
     * @return
     */
    public String findPointList() {
        JSONObject json = new JSONObject();
        try {
            json.put("pointList", autoBaseInfoManager.findPointList(userDetails.getCurrentOu().getId()));
        } catch (Exception e) {
            log.debug("findPointList.exception");
        }
        request.put(JSON, json);
        return JSON;
    }

    /**
     * 保存或修改集货规则明细
     * 
     * @return
     * @throws JSONException
     */
    public String saveOrUpdateRoleLine() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            String result2 = autoBaseInfoManager.saveShippingRoleLine(roleLine, pointId, userDetails.getCurrentOu().getId());
            result.put("result", SUCCESS);
            result.put("result2", result2);
        } catch (BusinessException e) {
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 根据父ID查询规则明细
     * 
     * @return
     */
    public String findRoleLineByRoleId() {
        setTableConfig();
        Pagination<ShippingPointRoleLineCommand> serv = autoBaseInfoManager.findRoleLineByRoleId(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), userDetails.getCurrentOu().getId(), code);
        request.put(JSON, toJson(serv));
        return JSON;
    }

    /**
     * 查询所有的核对工作台
     * 
     * @return
     */
    public String findlAllCheckSpace() {
        setTableConfig();
        Pagination<CheckingSpaceRoleCommand> serv = autoBaseInfoManager.findlAllCheckSpace(tableConfig.getStart(), tableConfig.getPageSize(), tableConfig.getSorts(), userDetails.getCurrentOu().getId(), owner, menuType);
        request.put(JSON, toJson(serv));
        return JSON;
    }

    /**
     * 删除规则明细
     * 
     * @return
     * @throws JSONException
     */
    public String deleteRoleLineById() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            autoBaseInfoManager.deleteShippingRoleLine(roleId);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 删除工作台规则明细
     * 
     * @return
     * @throws JSONException
     */
    public String deleteCheckLineById() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            autoBaseInfoManager.deleteCheckRoleLine(roleId);
            result.put("result", SUCCESS);
        } catch (BusinessException e) {
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }


    /**
     * 集货规则批量导入
     * 
     * @return
     */
    public String importShippingRole() throws Exception {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = autoBaseInfoManager.importShippingRole(file, userDetails.getCurrentOu().getId());
                if (rs != null) {
                    if (rs.getExceptions().size() > 0) request.put("msg", rs.getExceptions().get(0).getMessage());
                }
                if (rs == null) {
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                } else if (rs.getStatus() > ReadStatus.STATUS_SUCCESS) {
                    request.put("msg", "");
                    List<String> list = ExcelKit.getInstance().getReadStatusMessages(rs, this.getLocale());
                    request.put("msg", list);
                } else if (rs.getStatus() < ReadStatus.STATUS_SUCCESS) {
                    request.put("msg", "");
                    List<String> list = new ArrayList<String>();
                    for (Exception ex : rs.getExceptions()) {
                        if (ex instanceof BusinessException) {
                            BusinessException be = (BusinessException) ex;
                            list.add(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()) + Constants.HTML_LINE_BREAK);
                        }
                    }
                    request.put("msg", list);
                }
            } catch (BusinessException e) {
                request.put("msg", ERROR);
                log.error("", e);
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                log.error(e.getMessage());
                request.put("msg", sb.toString());
            } catch (Exception e) {
                request.put("msg", ERROR);
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    /**
     * 保存集货弹出扣绑定
     * 
     * @return
     * @throws JSONException
     */
    public String saveContainPop() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            String results = autoBaseInfoManager.saveContainPop(code, name, userDetails.getCurrentOu().getId());
            String rs = ""; // 返回js判断
            String msgId = "";// 消息ID
            if (results.contains("_")) {
                rs = results.substring(0, results.indexOf("_"));
                msgId = results.substring(results.indexOf("_") + 1, results.length());
                if (!msgId.equals("")) {
                    MsgToWcsThread wt = new MsgToWcsThread();
                    wt.setType(WcsInterfaceType.SShouRongQi.getValue());
                    wt.setMsgId(Long.parseLong(msgId));
                    Thread th = new Thread(wt);
                    th.start();
                }
            } else {
                rs = results;
            }
            result.put("result", rs);
        } catch (BusinessException e) {
            result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }



    /**
     * 方法说明：查询级联关系的集货口(通过负载均衡的关联字段)
     * 
     * @author LuYingMing
     * @return
     * @throws JSONException
     */
    public String findShippingPointForCascadeByRefId() throws JSONException {
        JSONObject result = new JSONObject();
        List<Long> list = autoBaseInfoManager.findShippingPointForCascadeByRefId(refShippingPointId);
        result.put("shippingPointId", list);
        request.put(JSON, result);
        return JSON;
    }


    /**
     * 方法说明：批量删除集货口
     * 
     * @author LuYingMing
     * @return
     * @throws JSONException
     */
    public String batchRemove() throws JSONException {
        JSONObject result = new JSONObject();
        if (!StringUtil.isEmpty(arrIds)) {
            String[] arrStr = arrIds.split(",");
            try {
                autoBaseInfoManager.batchRemove(arrStr);
                result.put("result", SUCCESS);
            } catch (Exception e) {
                log.error("deleteShippingById function is error!");
                result.put("msg", getMessage(ErrorCode.BUSINESS_EXCEPTION + ErrorCode.SHIPPING_POINT_ALREADY_RELATION));
            }
        }
        request.put(JSON, result);
        return JSON;
    }


    /**
     * 方法说明：批量删除集货规则
     * 
     * @author LuYingMing
     * @return
     * @throws JSONException
     */
    public String batchRemoveByRule() throws JSONException {
        JSONObject result = new JSONObject();
        if (!StringUtil.isEmpty(batchIds)) {
            String[] roleIds = batchIds.split(",");
            try {
                for (int i = 0; i < roleIds.length; i++) {
                    Long id = Long.parseLong(roleIds[i]);
                    autoBaseInfoManager.deleteShippingRoleLine(id);
                }
                result.put("result", SUCCESS);
            } catch (Exception e) {
                log.error("deleteShippingById function is error!");
            }
        }
        request.put(JSON, result);
        return JSON;
    }

    public String findshippingCollectionBoard() {
        JSONObject json = new JSONObject();
        StringBuilder sb = new StringBuilder();
        List<GoodsCollection> collectionList = autoBaseInfoManager.findShippingPointCollection(userDetails.getCurrentOu().getId());
        for (int i = 1; i < 12; i++) {
            for (GoodsCollection goodsCollection : collectionList) {
                Date date = new Date();
                Long differ = 0L;
                if (goodsCollection.getStartTime() != null) {
                    differ = (date.getTime() - goodsCollection.getStartTime().getTime()) / (3600 * 1000);
                }
                // 拼接第i通道的集货库位(目前预留11个通道)
                if (goodsCollection.getPassWay().equals(i + "")) {
                    if (goodsCollection.getStatus() == 1) {
                        sb.append(goodsCollection.getCollectionCode() + "-白色");
                    } else if (goodsCollection.getStatus() == 2 && differ < notFullTime) {
                        sb.append(goodsCollection.getCollectionCode() + "-蓝色");
                    } else if (goodsCollection.getStatus() == 3 && differ < fullTime) {
                        sb.append(goodsCollection.getCollectionCode() + "-绿色");
                        // 集满超时预警
                    } else if (goodsCollection.getStatus() == 3 && differ >=fullTime) {
                        sb.append(goodsCollection.getCollectionCode() + "-黄色");
                        // 未集满超时预警
                    } else if (goodsCollection.getStatus() == 2 && differ >= notFullTime) {
                        sb.append(goodsCollection.getCollectionCode() + "-红色");
                    }
                    sb.append(",");
                }

            }
            sb.append(";");
        }
        try {
            json.put("value", sb.toString());
            request.put(JSON, json);
           // System.out.println(sb);
        } catch (JSONException e) {
          log.error("集货库位状态看板查询失败",e);
        }
        return JSON;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public ShippingPointRoleLineCommand getRoleLine() {
        return roleLine;
    }

    public void setRoleLine(ShippingPointRoleLineCommand roleLine) {
        this.roleLine = roleLine;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Long getPointId() {
        return pointId;
    }

    public void setPointId(Long pointId) {
        this.pointId = pointId;
    }

    public String getWcsCode() {
        return wcsCode;
    }

    public void setWcsCode(String wcsCode) {
        this.wcsCode = wcsCode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public Long getIsQs() {
        return isQs;
    }

    public void setIsQs(Long isQs) {
        this.isQs = isQs;
    }

    public Long getIsSpaice() {
        return isSpaice;
    }

    public void setIsSpaice(Long isSpaice) {
        this.isSpaice = isSpaice;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }

    public Integer getMenuType() {
        return menuType;
    }

    public void setMenuType(Integer menuType) {
        this.menuType = menuType;
    }

    public Long getRefShippingPointId() {
        return refShippingPointId;
    }

    public void setRefShippingPointId(Long refShippingPointId) {
        this.refShippingPointId = refShippingPointId;
    }

    public List<ShippingPoint> getAddList() {
        return addList;
    }

    public void setAddList(List<ShippingPoint> addList) {
        this.addList = addList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPointType() {
        return pointType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    public void setPointType(String pointType) {
        this.pointType = pointType;
    }

    public Long getMaxAssumeNumber() {
        return maxAssumeNumber;
    }

    public void setMaxAssumeNumber(Long maxAssumeNumber) {
        this.maxAssumeNumber = maxAssumeNumber;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    public GoodsCollectionCommand getGoodsCollection() {
        return goodsCollection;
    }

    public void setGoodsCollection(GoodsCollectionCommand goodsCollection) {
        this.goodsCollection = goodsCollection;
    }

    public String getCollectionCode() {
        return collectionCode;
    }

    public void setCollectionCode(String collectionCode) {
        this.collectionCode = collectionCode;
    }

    public String getPopUpCode() {
        return popUpCode;
    }

    public void setPopUpCode(String popUpCode) {
        this.popUpCode = popUpCode;
    }

    public String getIsPreSale() {
        return isPreSale;
    }

    public void setIsPreSale(String isPreSale) {
        this.isPreSale = isPreSale;
    }

    public String getPassWay() {
        return passWay;
    }

    public void setPassWay(String passWay) {
        this.passWay = passWay;
    }

    public String getPickModel() {
        return pickModel;
    }

    public void setPickModel(String pickModel) {
        this.pickModel = pickModel;
    }

    public int getFullTime() {
        return fullTime;
    }

    public void setFullTime(int fullTime) {
        this.fullTime = fullTime;
    }

    public int getNotFullTime() {
        return notFullTime;
    }

    public void setNotFullTime(int notFullTime) {
        this.notFullTime = notFullTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public OdoCommand getOdoCommand() {
        return odoCommand;
    }

    public void setOdoCommand(OdoCommand odoCommand) {
        this.odoCommand = odoCommand;
    }


}
