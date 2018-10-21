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
 */
package com.jumbo.web.action.warehouse;

import java.util.List;

import loxia.dao.Pagination;
import loxia.support.json.JSONException;
import loxia.support.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.warehouse.ChannelPostPrintConfManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.ChannelWhRef;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.ChannelWhRefCommand;

/**
 * @author lichuan
 * 
 */
public class ChannelPostPrintConfAction extends BaseJQGridProfileAction {
    private static final long serialVersionUID = 1L;
    private OperationUnit ou;
    private List<ChannelWhRef> channelWhRefList;
    @Autowired
    private ChannelPostPrintConfManager channelPostPrintConfManager;

    public String channelPostPrintConf() {
        return SUCCESS;
    }

    public String findAllChannelRefByOuId() {
        ou = getCurrentOu();
        setTableConfig();
        Pagination<BiChannelCommand> channelList = channelPostPrintConfManager.findAllChannelRefByOuId(tableConfig.getStart(), tableConfig.getPageSize(), ou.getId(), tableConfig.getSorts());
        request.put(JSON, toJson(channelList));
        return JSON;
    }

    public String updatePostPrintConf() {
        ou = getCurrentOu();
        JSONObject result = new JSONObject();
        Long userId = null;
        try {
            try {
                userId = this.userDetails.getUser().getId();
                List<ChannelWhRefCommand> channelList = channelPostPrintConfManager.findAllPostPrintConfByOuId(ou.getId());
                for (ChannelWhRef channelRefList : channelWhRefList) {
                    for (int i = 0; i < channelList.size(); i++) {
                        boolean isPostPage = false;
                        boolean isPostBill = false;
                        if (channelRefList.getShop().getId().equals(channelList.get(i).getChannelId())) {
                            if (1 == channelList.get(i).getIsPostPage()) {
                                isPostPage = true;
                            }
                            if (1 == channelList.get(i).getIsPostBill()) {
                                isPostBill = true;
                            }
                            if (channelRefList.getIsPostExpressBill().equals(isPostBill) && channelRefList.getIsPostPackingPage().equals(isPostPage)) {
                                channelList.remove(i);
                                i--;
                            }
                        }
                    }
                }
                channelPostPrintConfManager.updatePostPrintConf(channelWhRefList, ou.getId(), userId, channelList);
                result.put("msg", "success");
            } catch (BusinessException be) {
                String message = this.getMessage(ErrorCode.BUSINESS_EXCEPTION + be.getErrorCode(), be.getArgs());
                result.put("msg", message);
            }
        } catch (JSONException e) {
            log.error("", e);
        }
        request.put(JSON, result);
        return JSON;
    }

    public String findAllPostPrintConf() {
        ou = getCurrentOu();
        List<ChannelWhRefCommand> channelList = channelPostPrintConfManager.findAllPostPrintConfByOuId(ou.getId());
        request.put(JSON, JsonUtil.collection2json(channelList));
        return JSON;
    }

    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    public List<ChannelWhRef> getChannelWhRefList() {
        return channelWhRefList;
    }

    public void setChannelWhRefList(List<ChannelWhRef> channelWhRefList) {
        this.channelWhRefList = channelWhRefList;
    }

}
