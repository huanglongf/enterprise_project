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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.Constants;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.channel.ChannelManager;
import com.jumbo.wms.manager.print.PrintCustomizeManager;
import com.jumbo.wms.model.authorization.OperationUnitType;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.BiChannelSpecialAction;
import com.jumbo.wms.model.baseinfo.BiChannelSpecialActionType;
import com.jumbo.wms.model.baseinfo.ChannelWhRef;
import com.jumbo.wms.model.print.PrintCustomize;
import com.jumbo.wms.model.warehouse.BetweenLabaryMoveCommand;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.BiChannelImperfect;
import com.jumbo.wms.model.warehouse.BiChannelImperfectCommand;
import com.jumbo.wms.model.warehouse.BiChannelImperfectLine;
import com.jumbo.wms.model.warehouse.BiChannelImperfectLineCommand;
import com.jumbo.wms.model.warehouse.BiChannelRef;
import com.jumbo.wms.model.warehouse.BiChannelSpecialActionCommand;
import com.jumbo.wms.model.warehouse.ChannelWhRefCommand;
import com.jumbo.wms.model.warehouse.SfCloudWarehouseConfigCommand;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.support.excel.ExcelKit;
import loxia.support.excel.ReadStatus;
import loxia.support.json.JSONArray;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

public class BiChannelInfoAction extends BaseJQGridProfileAction {

    private static final long serialVersionUID = 5379294127020151127L;

    private String shopName;// 店铺名称
    private Long whouid;// 仓库组织ID
    private BiChannel biChannel;
    private BiChannelImperfect biChannelImperfect;
    private BiChannelImperfectLine biChannelImperfectLine;
    private Long channelId;
    private Long imperfectId;
    private BiChannelSpecialAction bcsap;
    private BiChannelSpecialAction bcsae;
    private Long customerId;
    private Long icId;
    private List<ChannelWhRef> channelWhRefList;
    private BetweenLabaryMoveCommand blmcmd;
    private int deleteBCWR;
    private File file;
    private File filewhy;
    private String skuName;
    /**
     * 作业单编码
     */
    private String staCode;
    /**
     * 商品条码
     */
    private String barCode;

    @Autowired
    private ChannelManager channelManager;

    @Autowired
    private BaseinfoManager baseinfoManager;
    @Autowired
    private PrintCustomizeManager printCustomizeManager;



    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String skuName) {
        this.skuName = skuName;
    }

    public Long getImperfectId() {
        return imperfectId;
    }

    public void setImperfectId(Long imperfectId) {
        this.imperfectId = imperfectId;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFilewhy() {
        return filewhy;
    }

    public void setFilewhy(File filewhy) {
        this.filewhy = filewhy;
    }

    public String getBiChannelInfoByIc() {
        request.put(JSON, JsonUtil.obj2json(channelManager.findBichannelByInvCheck(icId)));
        return JSON;
    }


    public String manager() {
        return SUCCESS;
    }

    /**
     * 
     * SF时效类型查询
     */
    public String findSfTimeList() {
        setTableConfig();
        Pagination<SfCloudWarehouseConfigCommand> list = channelManager.findSfTimeList(tableConfig.getStart(), tableConfig.getPageSize(), whouid, channelId, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    /**
     * 获取所有的仓库
     */
    public String findAllWarehouse() {
        request.put(JSON, JsonUtil.collection2json(channelManager.findAllWarehouse()));
        return JSON;
    }

    /**
     * 根据组织查询对应渠道信息
     * 
     * @return
     */
    public String getBiChannelInfo() {
        setTableConfig();
        if (whouid != null) {
            Pagination<BiChannel> list = channelManager.findBiChannelByOuid(tableConfig.getStart(), tableConfig.getPageSize(), OperationUnitType.OUTYPE_WAREHOUSE, shopName, whouid, tableConfig.getSorts());
            request.put(JSON, toJson(list));
        } else {
            Pagination<BiChannel> list = channelManager.findBiChannelByOuid(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getOuType().getName(), shopName, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
            request.put(JSON, toJson(list));
        }
        return JSON;
    }

    /**
     * 根据组织查询对应按箱收货渠道信息
     * 
     * @return
     */
    public String getBiChannelInfoOfCartonSta() {
        JSONObject result = new JSONObject();
        JSONArray ja = new JSONArray();
        try {
            List<BiChannel> list = channelManager.findBiChannelByOuidAndCartonStaShop(userDetails.getCurrentOu().getId(), true);
            for (BiChannel biChannel : list) {
                JSONObject jo = new JSONObject();
                jo.put("code", biChannel.getCode());
                jo.put("name", biChannel.getName());
                ja.put(jo);
            }
            result.put("cartonStaShoplist", ja);
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查询渠道对应信息
     * 
     * @return
     */
    public String findShopInfo() {
        List<BiChannel> channelList = new ArrayList<BiChannel>();
        if (userDetails.getCurrentOu().getOuType().getName().equals(OperationUnitType.OUTYPE_COMPANY)) {
            // ouid = userDetails.getCurrentOu().getId();
            // request.put(JSON,
            // JsonUtil.collection2json(baseinfoManager.findCompanyShopsByCompanyOuId(ouid)));
        } else if (userDetails.getCurrentOu().getOuType().getName().equals(OperationUnitType.OUTYPE_OPERATION_CENTER)) {
            // OperationUnit ou =
            // authorizationManager.getOUByPrimaryKey(userDetails.getCurrentOu().getId());
            // ouid = ou.getParentUnit().getId();
            // request.put(JSON,
            // JsonUtil.collection2json(baseinfoManager.findCompanyShopsByCompanyOuId(ouid)));
        } else if (userDetails.getCurrentOu().getOuType().getName().equals(OperationUnitType.OUTYPE_WAREHOUSE)) {
            // 当前组织为仓库，查询所有仓库关联店铺信息
            channelList = baseinfoManager.queryShopListMultiCheck(userDetails.getCurrentOu().getId());
            request.put(JSON, JsonUtil.collection2json(channelList));
        }
        return JSON;
    }

    /**
     * 查询所有渠道信息
     * 
     * @return
     */
    public String findShopInfoAll() {
        List<BiChannel> channelList = new ArrayList<BiChannel>();
        channelList = baseinfoManager.queryAllShopList();
        request.put(JSON, JsonUtil.collection2json(channelList));
        return JSON;
    }

    /**
     * 查询所有渠道信息（分页）
     * 
     * @return
     */
    public String findShopInfoAllPage() {
        setTableConfig();
        Pagination<BiChannel> list = channelManager.findBiChannelByPage(tableConfig.getStart(), tableConfig.getPageSize(), shopName, tableConfig.getSorts());
        request.put(JSON, toJson(list));
        return JSON;
    }

    public String findShopInfoByDefaultOuId() {
        List<BiChannel> channelList = new ArrayList<BiChannel>();
        if (userDetails.getCurrentOu().getOuType().getName().equals(OperationUnitType.OUTYPE_WAREHOUSE)) {
            // 当前组织为仓库，查询绑定当前仓库为默认仓库的所有关联店铺信息
            channelList = baseinfoManager.queryShopListByDefaultOuId(userDetails.getCurrentOu().getId());
            request.put(JSON, JsonUtil.collection2json(channelList));
        }
        return JSON;
    }

    /**
     * 通过仓库组织ID获取仓库所属客户下所有渠道
     * 
     * @return
     */
    public String findAllShopListByCustomer() {
        request.put(JSON, JsonUtil.collection2json(channelManager.findtAllChannelByWhouid(userDetails.getCurrentOu().getId())));
        return JSON;
    }

    /**
     * 通过一个或多个仓库组织ID获取仓库所属客户下所有渠道
     * 
     * @return
     */
    public String findAllShopListByCustomers() {
        request.put(JSON, JsonUtil.collection2json(channelManager.findAllChannelByWhouids(blmcmd.getStartWarehouseId(), blmcmd.getEndWarehouseId())));
        return JSON;
    }

    /**
     * 获取所有客户信息
     * 
     * @return
     */
    public String findBiCustomer() {
        request.put(JSON, JsonUtil.collection2json(baseinfoManager.findAllCustomer()));
        return JSON;
    }

    /**
     * 获取渠道信息
     * 
     * @return
     */
    public String findBiChannelList() {
        setTableConfig();
        Pagination<BiChannelCommand> biChannelList = channelManager.getBiChannelList(tableConfig.getStart(), tableConfig.getPageSize(), biChannel, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(biChannelList));
        return JSON;
    }

    /**
     * 获取渠道残次原因信息
     * 
     * @return
     */
    public String findBiChanneImperfectlList() {
        setTableConfig();
        Pagination<BiChannelImperfectCommand> biChannelList = channelManager.findBiChanneImperfectlList(tableConfig.getStart(), tableConfig.getPageSize(), channelId, userDetails.getCurrentOu().getId(), new Sort[] {new Sort("ct.id asc")});
        request.put(JSON, toJson(biChannelList));
        return JSON;
    }

    /**
     * 获取渠道残次原因信息
     * 
     * @return
     */
    public String findBiChanneImperfectlLineList() {
        setTableConfig();
        Pagination<BiChannelImperfectLineCommand> biChannelList = channelManager.findBiChanneImperfectlLineList(tableConfig.getStart(), tableConfig.getPageSize(), imperfectId, userDetails.getCurrentOu().getId(), new Sort[] {new Sort("ct.id asc")});
        request.put(JSON, toJson(biChannelList));
        return JSON;
    }

    /**
     * 获取公司下拉列表
     * 
     * @return
     */
    public String findInvoiceCompanyList() {
        List<BiChannelCommand> companyList = channelManager.findInvoiceCompany();
        request.put(JSON, JsonUtil.collection2json(companyList));
        return JSON;
    }

    /**
     * 获取非虚拟渠道信息
     * 
     * @return
     */
    public String findBiChannelListByType() {
        setTableConfig();
        Pagination<BiChannelCommand> biChannelList = channelManager.getBiChannelListByType(tableConfig.getStart(), tableConfig.getPageSize(), biChannel, whouid, tableConfig.getSorts());
        request.put(JSON, toJson(biChannelList));
        return JSON;
    }

    /**
     * 获取非虚拟渠道信息
     * 
     * @return
     */
    public String findBiChannelListByTypeAndExpT() {
        setTableConfig();
        Pagination<BiChannelCommand> biChannelList = channelManager.findBiChannelListByTypeAndExpT(tableConfig.getStart(), tableConfig.getPageSize(), biChannel, userDetails.getCurrentOu().getId(), tableConfig.getSorts());
        request.put(JSON, toJson(biChannelList));
        return JSON;
    }

    /**
     * 通过渠道ID获取对应信息
     * 
     * @return
     */
    public String getBiChannelById() {
        request.put(JSON, JsonUtil.obj2json(channelManager.getBiChannelById(channelId)));
        return JSON;
    }

    /**
     * 通过渠道编码获取渠道信息
     * 
     * @return String
     * @throws
     */
    public String getBiChannelByCode() {
        request.put(JSON, JsonUtil.obj2json(channelManager.getBiChannelByCode(biChannel.getCode())));
        return JSON;
    }

    /**
     * 通过渠道ID获取渠道特殊行为类型为装箱清单打印定制的数据
     * 
     * @return
     */
    public String getBiChannelSpecialActionByCidTypePackingPage() {
        try {
            JSONObject result = new JSONObject();
            BiChannelSpecialActionCommand channelSpacialAction = channelManager.getBiChannelSpecialActionByCidType(channelId, BiChannelSpecialActionType.PRINT_PACKING_PAGE.getValue());
            if (null != channelSpacialAction)
                request.put(JSON, JsonUtil.obj2json(channelSpacialAction));
            else {
                result.put("msg", NONE);
                request.put(JSON, result);
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        return JSON;
    }

    public String findIsSpecial() {
        JSONObject result = new JSONObject();
        String s = channelManager.getIsSpecialByStaAndSku(staCode, barCode, userDetails.getCurrentOu().getId());
        try {
            result.put("flag", s);
        } catch (JSONException e) {
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("findIsSpecial JSONException:", e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    public String findIsSpecialBySku() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            Boolean msg = channelManager.checkIsSpecialByStaAndSku(staCode, barCode);
            result.put("flag", msg);
        } catch (Exception e) {
            log.error("findIsSpecialBySku JSONException:", e);
            result.put("flag", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 通过渠道ID获取渠道特殊行为类型为运单定制的数据
     * 
     * @return
     */
    public String getBiChannelSpecialActionByCidTypeExpressBill() {
        try {
            JSONObject result = new JSONObject();
            BiChannelSpecialActionCommand channelSpacialAction = channelManager.getBiChannelSpecialActionByCidType(channelId, BiChannelSpecialActionType.PRINT_EXPRESS_BILL.getValue());
            if (null != channelSpacialAction)
                request.put(JSON, JsonUtil.obj2json(channelSpacialAction));
            else {
                result.put("msg", NONE);
                request.put(JSON, result);
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        return JSON;
    }

    /**
     * 修改渠道等相关信息
     * 
     * @return
     */
    public String updateBiChannel() {
        JSONObject result = new JSONObject();
        Long userId = null;
        try {
            userId = this.userDetails.getUser().getId();
            channelManager.updateBiChannel(biChannel, bcsap, bcsae, deleteBCWR, userId);
            result.put("msg", SUCCESS);
        } catch (Exception e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 新增渠道等相关信息
     * 
     * @return
     */
    public String addBiChannel() {
        JSONObject result = new JSONObject();
        Long userId = null;
        try {
            userId = this.userDetails.getUser().getId();
            channelManager.addBiChannel(biChannel, bcsap, bcsae, userId);
            result.put("msg", SUCCESS);
        } catch (Exception e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String importImperfect() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (file == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = channelManager.addSkuImperfect(file, icId);
                if (rs != null) {
                    if (rs.getExceptions().size() > 0) {
                        request.put("msg", rs.getExceptions().get(0).getMessage());
                        request.put("id", icId);
                    }
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

                log.error("", e);
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                    request.put("msg", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                }
                BusinessException be = e;
                while ((be = be.getLinkedException()) != null) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs()));
                }
                log.error(e.getMessage());
                request.put("msg", sb.toString());
            } catch (Exception e) {
                request.put("result", ERROR);
                request.put("msg", "");
                log.error("", e);
                log.error(e.getMessage());
                request.put("msg", e.getMessage());
            }
        }
        return SUCCESS;
    }

    public String addImperfectLine() {
        JSONObject result = new JSONObject();
        try {
            channelManager.addImperfectLine(biChannelImperfectLine);
            result.put("msg", SUCCESS);
        } catch (Exception e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 验证渠道编码和名称是否存在
     * 
     * @return
     */
    public String checkBiChannelByCodeOrName() {
        JSONObject result = new JSONObject();
        try {
            if (biChannel.getId() == null) {
                // 新增验证，需要验证编码和名称
                BiChannel bic = channelManager.checkBiChannelByCodeOrName(null, biChannel.getName());
                BiChannel bin = channelManager.checkBiChannelByCodeOrName(biChannel.getName(), null);
                if (bic == null && bin == null) {
                    result.put("msg", "NODATA");
                } else {
                    result.put("msg", "DATA");
                }
            }
            if (biChannel.getId() != null) {
                // 修改，只需要验证名称
                BiChannel name = channelManager.getBiChannel(biChannel.getId());
                if (name.getName().equals(biChannel.getName())) {
                    // 如果修改的值和原来值相同，不用查询
                    result.put("msg", "NODATA");
                } else {
                    // 如果修改的值和原来值不相同，需要查询
                    BiChannel bi = channelManager.checkBiChannelByCodeOrName(null, biChannel.getName());
                    if (bi == null) {
                        result.put("msg", "NODATA");
                    } else {
                        result.put("msg", "DATA");
                    }
                }
            }
        } catch (Exception e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 通过客户ID获取下面对应的仓库
     * 
     * @return
     */
    public String findBiChannelWhRefList() {
        setTableConfig();
        request.put(JSON, toJson(channelManager.findBiChannelWhRefList(customerId)));
        return JSON;
    }

    /**
     * 通过channel_id获取渠道仓库绑定信息
     * 
     * @return
     */
    public String findChannelWhRefListByChannelId() {
        JSONObject result = new JSONObject();
        try {
            String whRefValue = channelManager.findChannelWhRefListByChannelId(channelId);
            result.put("msg", whRefValue);
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 保存渠道仓库绑定关系
     * 
     * @return
     */
    public String updateChannelWhRef() {
        JSONObject result = new JSONObject();
        try {
            Long userId = this.userDetails.getUser().getId();
            List<ChannelWhRefCommand> channelList = channelManager.findChannelListByChannelId(channelId);
            for (ChannelWhRef list : channelWhRefList) {
                for (int i = 0; i < channelList.size(); i++) {
                    /*
                     * boolean isPostPage = false; boolean isPostBill = false;
                     */
                    if (list.getWh().getId().equals(channelList.get(i).getOuId())) {
                        /*
                         * if (1 == channelList.get(i).getIsPostPage()) { isPostPage = true; } if (1
                         * == channelList.get(i).getIsPostBill()) { isPostBill = true; }
                         */
                        channelList.remove(i);
                        i--;
                        /*
                         * if (list.getIsPostExpressBill().equals(isPostBill) &&
                         * list.getIsPostPackingPage().equals(isPostPage)) {
                         * 
                         * }
                         */
                    }
                }
            }

            String str = channelManager.verifySfJcustid(channelWhRefList);
            if ("".equals(str)) {
                channelManager.updateChannelWhRef(channelWhRefList, channelId, userId, channelList);
                result.put("msg", "SUCCESS");
            } else {
                result.put("str", str);
                result.put("msg", "ERROR");
            }

        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 查询渠道是否和渠道组有绑定关系bin.hu
     */
    public String checkBiChannelRefByBiChannel() {
        JSONObject result = new JSONObject();
        try {
            BiChannelRef br = channelManager.checkBiChannelRefByBiChannel(biChannel.getCode());
            if (br != null) {
                result.put("msg", "DATA");
            } else {
                result.put("msg", "NODATA");
            }
        } catch (Exception e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String findAllChannel() {
        JSONObject json = new JSONObject();
        JSONArray ja = new JSONArray();
        try {
            List<BiChannel> bList = channelManager.selectAllBiChannel();

            for (int i = 0; i < bList.size(); i++) {
                BiChannel b = bList.get(i);
                JSONObject jo = new JSONObject();
                jo.put("name", b.getName());
                jo.put("code", b.getCode());
                ja.put(jo);
            }

            json.put("channelList", ja);
        } catch (Exception e) {
            log.debug("BiChannelInfoAction.findAllChannel.exception");
        }
        request.put(JSON, json);
        return JSON;
    }

    public String importImperfectWhy() {
        String msg = SUCCESS;
        request.put("msg", msg);
        request.put("result", SUCCESS);
        if (filewhy == null) {
            msg = "The upload file can not be null...";
            log.error(msg);
            request.put("msg", msg);
        } else {
            try {
                ReadStatus rs = channelManager.addSkuImperfectWhy(filewhy, imperfectId);
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
                request.put("result", ERROR);

                log.error("", e);
                StringBuffer sb = new StringBuffer();
                if (e.getErrorCode() > 0) {
                    sb.append(this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
                    request.put("msg", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
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

    public String getPrintCode() {
    List<PrintCustomize>  pcList=  printCustomizeManager.getAllPrintCode();
    request.put(JSON, JsonUtil.collection2json(pcList));
        return JSON;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public Long getWhouid() {
        return whouid;
    }

    public void setWhouid(Long whouid) {
        this.whouid = whouid;
    }

    public BiChannel getBiChannel() {
        return biChannel;
    }

    public void setBiChannel(BiChannel biChannel) {
        this.biChannel = biChannel;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public BiChannelSpecialAction getBcsap() {
        return bcsap;
    }

    public void setBcsap(BiChannelSpecialAction bcsap) {
        this.bcsap = bcsap;
    }

    public BiChannelSpecialAction getBcsae() {
        return bcsae;
    }

    public void setBcsae(BiChannelSpecialAction bcsae) {
        this.bcsae = bcsae;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<ChannelWhRef> getChannelWhRefList() {
        return channelWhRefList;
    }

    public void setChannelWhRefList(List<ChannelWhRef> channelWhRefList) {
        this.channelWhRefList = channelWhRefList;
    }

    public BetweenLabaryMoveCommand getBlmcmd() {
        return blmcmd;
    }

    public void setBlmcmd(BetweenLabaryMoveCommand blmcmd) {
        this.blmcmd = blmcmd;
    }

    public int getDeleteBCWR() {
        return deleteBCWR;
    }

    public void setDeleteBCWR(int deleteBCWR) {
        this.deleteBCWR = deleteBCWR;
    }

    public Long getIcId() {
        return icId;
    }

    public void setIcId(Long icId) {
        this.icId = icId;
    }

    public String getStaCode() {
        return staCode;
    }

    public void setStaCode(String staCode) {
        this.staCode = staCode;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public BiChannelImperfect getBiChannelImperfect() {
        return biChannelImperfect;
    }

    public void setBiChannelImperfect(BiChannelImperfect biChannelImperfect) {
        this.biChannelImperfect = biChannelImperfect;
    }

    public BiChannelImperfectLine getBiChannelImperfectLine() {
        return biChannelImperfectLine;
    }

    public void setBiChannelImperfectLine(BiChannelImperfectLine biChannelImperfectLine) {
        this.biChannelImperfectLine = biChannelImperfectLine;
    }

}
