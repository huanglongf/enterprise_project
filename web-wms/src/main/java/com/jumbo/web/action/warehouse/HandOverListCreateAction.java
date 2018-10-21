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
package com.jumbo.web.action.warehouse;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.authorization.AuthorizationManager;
import com.jumbo.wms.manager.automaticEquipment.AutoBaseInfoManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;
import com.jumbo.wms.manager.warehouse.excel.ExcelReadManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.command.automaticEquipment.CountPackageCommand;
import com.jumbo.wms.model.warehouse.HandOverList;
import com.jumbo.wms.model.warehouse.PackageInfoCommand;

public class HandOverListCreateAction extends BaseJQGridProfileAction {

    /**
	 * 
	 */
    private static final long serialVersionUID = -955802839683249934L;

    @Autowired
    private WareHouseManager manager;
    @Autowired
    private ExcelReadManager excelReadManager;
    @Autowired
    private WareHouseManagerExecute wareHouseManagerExecute;
    @Autowired
    private AuthorizationManager authorizationManager;

    @Autowired
    private AutoBaseInfoManager autoBaseInfoManager;

    private List<Long> packageIds;

    private File trackingNoFile;
    /**
     * 物流简称
     */
    private String lpcode;

    /**
     * 物流商编码
     */
    private String lpCodeS;


    private List<String> transNo;

    public String importEntry() {
        return SUCCESS;
    }

    /**
     * 导入物流单
     * 
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String importTrackingNo() throws Exception {
        if (trackingNoFile == null && !StringUtils.hasText(lpcode)) {
            return ERROR;
        } else {
            List<PackageInfoCommand> pgList = null;
            List<String> list = excelReadManager.hoListImport(trackingNoFile);
            Map<String, Object> map = new HashMap<String, Object>();
            if (userDetails.getCurrentOu().getOuType().getName().equals("OperationCenter")) {
                List<OperationUnit> ouList = authorizationManager.findOperationUnitList(userDetails.getCurrentOu().getId());
                List<Long> idList = new ArrayList<Long>();
                for (OperationUnit ou : ouList) {
                    idList.add(ou.getId());
                }
                map = manager.hoListImportCheckTrackingNo2(list, null, idList);
            } else {
                map = manager.hoListImportCheckTrackingNo2(list, userDetails.getCurrentOu().getId(), null);
            }
            pgList = (List<PackageInfoCommand>) map.get("package");
            Map<String, Object> map1 = manager.hoListImportCheckLpcode(pgList, lpcode);
            pgList = ((List<PackageInfoCommand>) map1.get("package"));
            // Map<String, Object> map2 = manager.hoListImportCheckByStaStatus(pgList);
            // pgList = (List<PackageInfoCommand>) map2.get("package");
            Map<String, Object> map2 = manager.hoListImportCheckPre(pgList, lpcode);
            pgList = ((List<PackageInfoCommand>) map2.get("package"));

            request.put("hoList", JsonUtil.collection2json(pgList).toString());
            if (map.get("removeTrackingNo") != null) {
                request.put("removeByTrackingNo", JsonUtil.collection2json((List<String>) map.get("removeTrackingNo")).toString());
            }
            if (map1.get("removeTrackingNo") != null) {
                request.put("removeBylpcode", JsonUtil.collection2json((List<String>) map1.get("removeTrackingNo")).toString());
            }
            if (map2.get("removeTrackingNo") != null) {
                request.put("removeBylpcodePre", JsonUtil.collection2json((List<String>) map2.get("removeTrackingNo")).toString());
            }
            // if (map2.get("removeTrackingNo") != null) {
            // request.put("removeBySta", JsonUtil.collection2json((List<String>)
            // map2.get("removeTrackingNo")).toString());
            // }
            BigDecimal totalWeigh = new BigDecimal(0);
            for (PackageInfoCommand pg : pgList) {
                totalWeigh = totalWeigh.add(pg.getWeight());
            }
            request.put("packageCount", pgList.size());
            request.put("totalWeight", totalWeigh);

        }
        return SUCCESS;
    }

    /**
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public String generateHandoverListByHand() throws Exception {
        JSONObject result = new JSONObject();
        if (transNo == null || transNo.isEmpty() || !StringUtils.hasText(lpcode)) {
            log.error("===========[error]: lpcode is null or transNo is null================");
            throw new BusinessException("transport lpcode is null  or transNo. is null.");
        }
        Map<String, Object> resultMap = null;
        if (userDetails.getCurrentOu().getOuType().getName().equals("OperationCenter")) {
            List<OperationUnit> ouList = authorizationManager.findOperationUnitList(userDetails.getCurrentOu().getId());
            List<Long> idList = new ArrayList<Long>();
            for (OperationUnit ou : ouList) {
                idList.add(ou.getId());
            }
            resultMap = manager.hoListCreateByHandStep1(transNo, lpcode, null, idList);
        } else {
            resultMap = manager.hoListCreateByHandStep1(transNo, lpcode, userDetails.getCurrentOu().getId(), null);
        }
        List<PackageInfoCommand> pgList = null;
        if (resultMap != null && !resultMap.isEmpty()) {
            pgList = (List<PackageInfoCommand>) resultMap.get("pclist");
            List<String> t1 = (List<String>) resultMap.get("removeByTrackingNo");
            if (t1 != null) {
                result.put("removeByTrackingNo", JsonUtil.collection2json(t1));
            }
            List<String> t2 = (List<String>) resultMap.get("removeBylpcode");
            if (t2 != null) {
                result.put("removeBylpcode", JsonUtil.collection2json(t2));
            }
            List<String> t3 = (List<String>) resultMap.get("removeBySta");
            if (t3 != null) {
                result.put("removeBySta", JsonUtil.collection2json(t3));
            }
            List<String> t4 = (List<String>) resultMap.get("removeBylpcodePre");
            if (t4 != null) {
                result.put("removeBylpcodePre", JsonUtil.collection2json(t4));
            }
            result.put("hoList", JsonUtil.collection2json(pgList));
            BigDecimal totalWeigh = new BigDecimal(0);
            for (PackageInfoCommand pg : pgList) {
                totalWeigh = totalWeigh.add(pg.getWeight());
            }
            result.put("packageCount", pgList.size());
            result.put("totalWeight", totalWeigh);
            result.put("result", SUCCESS);
        } else {
            result.put("result", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 创建交货清单
     * 
     * @return
     * @throws JSONException
     */
    public String createHandOverListDelete() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            HandOverList ho = wareHouseManagerExecute.createHandOverListDelete(lpcode, packageIds, userDetails.getCurrentOu().getId(), userDetails.getUser().getId(), false);
            result.put("result", SUCCESS);
            result.put("ho", JsonUtil.obj2json(ho));
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * createOneHandOverList 一键创建交接清单
     */
    public String createOneHandOverList() throws JSONException {
        JSONObject result = new JSONObject();
        Map<String, Object> map = null;
        try {
            map = wareHouseManagerExecute.getOneHandOutPack(userDetails.getUser().getId(), userDetails.getCurrentOu().getId(), lpcode);
            result.put("result", SUCCESS);
            result.put("brand", map.get("brand"));
            result.put("hoIds", map.get("hoIds"));
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e) {
            result.put("result", ERROR);
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("createOneHandOverList error:", e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 创建交接清单
     * 
     * @return
     * @throws JSONException
     */
    public String createHandOverList() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            HandOverList ho = wareHouseManagerExecute.createHandOverList(lpcode, packageIds, userDetails.getCurrentOu().getId(), userDetails.getUser().getId(), false);
            result.put("result", SUCCESS);
            result.put("ho", JsonUtil.obj2json(ho));
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        }
        request.put(JSON, result);
        return JSON;
    }

    public String findCountPackageByOutbound() {
        setTableConfig();
        String[] lp = lpCodeS.split("，");
        String s = "";
        for (int i = 0; i < lp.length; i++) {
            if (i == 0) {
                s = "'" + lp[i] + "'";
            } else {
                s = s + ",'" + lp[i] + "'";
            }
        }
        Pagination<CountPackageCommand> zoonList = autoBaseInfoManager.findCountPackageByOutbound(tableConfig.getStart(), tableConfig.getPageSize(), s);
        request.put(JSON, toJson(zoonList));
        return JSON;
    }

    /**
     * 自动化仓交接
     * 
     * @return
     * @throws JSONException
     */
    public String autoHandOverList() throws JSONException {
        JSONObject result = new JSONObject();
        Map<String, Object> map = null;
        try {
            map = wareHouseManagerExecute.getAutoOneHandOutPack(userDetails.getUser().getId(), lpcode);
            result.put("result", SUCCESS);
            result.put("hoIds", map.get("hoIds"));
        } catch (BusinessException e) {
            result.put("result", ERROR);
            result.put("message", this.getMessage(ErrorCode.BUSINESS_EXCEPTION + e.getErrorCode(), e.getArgs()));
        } catch (Exception e) {
            result.put("result", ERROR);
            // e.printStackTrace();
            if (log.isErrorEnabled()) {
                log.error("autoHandOverList error:", e);
            };
        }
        request.put(JSON, result);
        return JSON;
    }

    public File getTrackingNoFile() {
        return trackingNoFile;
    }

    public void setTrackingNoFile(File trackingNoFile) {
        this.trackingNoFile = trackingNoFile;
    }

    public String getLpcode() {
        return lpcode;
    }

    public void setLpcode(String lpcode) {
        this.lpcode = lpcode;
    }

    public List<Long> getPackageIds() {
        return packageIds;
    }

    public void setPackageIds(List<Long> packageIds) {
        this.packageIds = packageIds;
    }

    public List<String> getTransNo() {
        return transNo;
    }

    public void setTransNo(List<String> transNo) {
        this.transNo = transNo;
    }

    public String getLpCodeS() {
        return lpCodeS;
    }

    public void setLpCodeS(String lpCodeS) {
        this.lpCodeS = lpCodeS;
    }


}
