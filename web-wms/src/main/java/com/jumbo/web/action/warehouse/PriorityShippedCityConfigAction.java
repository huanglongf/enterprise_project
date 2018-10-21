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

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.warehouse.PriorityShippedCityConfigManager;
import com.jumbo.wms.model.warehouse.HightProvinceConfig;
import com.jumbo.wms.model.warehouse.PriorityShippedCityConfigCommand;


/**
 * 
 * 接口/类说明：优先发货城市配置 Action
 * 
 * @ClassName: PriorityShippedCityConfigAction
 * @author LuYingMing
 * @date 2016年8月25日 下午1:43:56
 */
public class PriorityShippedCityConfigAction extends BaseJQGridProfileAction {


    private static final long serialVersionUID = -4404910897545028614L;


    protected static final Logger logger = LoggerFactory.getLogger(PriorityShippedCityConfigAction.class);

    @Autowired
    private PriorityShippedCityConfigManager psccManager;



    private PriorityShippedCityConfigCommand psccCommand;

    private String ids;

    private HightProvinceConfig hightProvinceConfig;


    public HightProvinceConfig getHightProvinceConfig() {
        return hightProvinceConfig;
    }

    public void setHightProvinceConfig(HightProvinceConfig hightProvinceConfig) {
        this.hightProvinceConfig = hightProvinceConfig;
    }

    /**
     * 方法说明：进入优先城市配置页面
     * 
     * @author LuYingMing
     * @date 2016年8月25日 下午1:49:51
     * @return
     */
    public String initConfigPage() {
        return SUCCESS;
    }

    /**
     * 方法说明：分页查询
     * 
     * @author LuYingMing
     * @date 2016年8月25日 下午7:49:43
     * @return
     */
    public String queryEntityParams() {
        setTableConfig();
        Pagination<PriorityShippedCityConfigCommand> entityList = psccManager.queryPriorityCityConfig(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), userDetails.getCurrentOu().getOuType().getId());
        request.put(JSON, toJson(entityList));
        return JSON;
    }


    public String queryHightProvinceConfig() {
        setTableConfig();
        Pagination<HightProvinceConfig> paginationList = psccManager.queryHightProvinceConfig(tableConfig.getStart(), tableConfig.getPageSize(), userDetails.getCurrentOu().getId(), userDetails.getCurrentOu().getOuType().getId(), hightProvinceConfig);
        request.put(JSON, toJson(paginationList));
        return JSON;
    }

    /**
     * 方法说明：保存
     * 
     * @author LuYingMing
     * @date 2016年8月25日 下午3:28:01
     * @return
     * @throws JSONException
     */
    public String saveEntity() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            psccCommand.setOuId(userDetails.getCurrentOu().getId());
            psccCommand.setOuTypeId(userDetails.getCurrentOu().getOuType().getId());
            psccManager.saveEntity(psccCommand);
            result.put("msg", SUCCESS);
        } catch (BusinessException e) {
            log.error("", e);
            result.put("msg", ERROR);
        }

        request.put(JSON, result);
        return JSON;

    }



    public String saveHightProvinceConfig() {
        JSONObject result = new JSONObject();
        try {
            hightProvinceConfig.setOuId(userDetails.getCurrentOu().getId());
            hightProvinceConfig.setOuTypeId(userDetails.getCurrentOu().getOuType().getId());
            hightProvinceConfig.setPriorityName(hightProvinceConfig.getPriorityName());
            psccManager.saveHightProvinceConfig(hightProvinceConfig);
            result.put("msg", SUCCESS);
        } catch (BusinessException e) {
            log.error("saveHightProvinceConfigBusinessException", e);
            try {
                result.put("msg", ERROR);
            } catch (JSONException e1) {
                logger.error("saveHightProvinceConfig" + e1.toString());
            }

        } catch (Exception e) {
            logger.error("saveHightProvinceConfigException" + e.toString());
        }
        request.put(JSON, result);
        return JSON;
    }

    public String deleteHightProvinceConfig() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            psccManager.deleteHightProvinceConfig(ids);
            result.put("msg", SUCCESS);
        } catch (Exception e) {
            log.error("", e);
            result.put("msg", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 方法说明：删除
     * 
     * @author LuYingMing
     * @date 2016年8月25日 下午3:29:10
     * @return
     * @throws JSONException
     */
    public String delEntity() throws JSONException {
        JSONObject result = new JSONObject();
        try {
            psccManager.deleteEntity(ids);
            result.put("msg", SUCCESS);
        } catch (Exception e) {
            log.error("", e);
            result.put("msg", ERROR);
        }
        request.put(JSON, result);
        return JSON;
    }

    /**
     * 
     * 方法说明：控件查询优先发货城市 (先查仓库,后集团)
     * 
     * @author LuYingMing
     * @date 2016年8月26日 下午6:02:47
     * @return
     */
    public String findPriorityCityList() {
        request.put(JSON, JsonUtil.collection2json(psccManager.findPriorityCityList(userDetails.getCurrentOu().getId(), userDetails.getCurrentOu().getOuType().getId())));
        return JSON;
    }

    public String findPriorityList() {
        request.put(JSON, JsonUtil.collection2json(psccManager.findPriorityList(userDetails.getCurrentOu().getId(), userDetails.getCurrentOu().getOuType().getId())));
        return JSON;
    }

    public PriorityShippedCityConfigCommand getPsccCommand() {
        return psccCommand;
    }

    public void setPsccCommand(PriorityShippedCityConfigCommand psccCommand) {
        this.psccCommand = psccCommand;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

}
