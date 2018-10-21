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

import com.jumbo.wms.manager.warehouse.ChannelGroupManager;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.BiChannelGroup;
import com.jumbo.wms.model.warehouse.BiChannelGroupCommand;
import com.jumbo.util.JsonUtil;
import com.jumbo.web.action.BaseJQGridProfileAction;

/**
 * @author lichuan
 * 
 */
public class ChannelGroupAction extends BaseJQGridProfileAction {
    private static final long serialVersionUID = 1L;
    @Autowired
    private ChannelGroupManager channelGroupManager;
    private OperationUnit ou;
    private BiChannelGroup group;
    private Integer status;
    private List<BiChannel> channelRef;
    private Long channelId;
    private Long groupId;

    public String channelgroupmaintain() {
        return SUCCESS;
    }

    public String findAllChannelGroupByOuId() {
        ou = getCurrentOu();
        setTableConfig();
        Pagination<BiChannelGroupCommand> channelGroupList = channelGroupManager.findAllChannelGroupByOuId(tableConfig.getStart(), tableConfig.getPageSize(), ou.getId(), tableConfig.getSorts());
        request.put(JSON, toJson(channelGroupList));
        return JSON;
    }

    /**
     * 根据仓库查找渠道组，不分页
     * 
     * @return
     */
    public String findChannelGroupByOuId() {
        ou = getCurrentOu();
        setTableConfig();
        List<BiChannelGroupCommand> channelGroupList = channelGroupManager.findChannelGroupByOuId(ou.getId(), tableConfig.getSorts());
        request.put(JSON, toJson(channelGroupList));
        return JSON;
    }

    public String findChannelGroupExistByCode() {
        try {
            String code = null;
            if (null != group) {
                code = group.getCode();
            }
            boolean result = channelGroupManager.findChannelGroupExistByCode(code);
            JSONObject ret = new JSONObject();
            ret.put("result", result);
            request.put(JSON, ret);
        } catch (JSONException e) {
            log.error("", e);
        }
        return JSON;
    }

    public String findChannelGroupExistByName() {
        try {
            String name = null;
            if (null != group) {
                name = group.getName();
            }
            boolean result = channelGroupManager.findChannelGroupExistByName(name);
            JSONObject ret = new JSONObject();
            ret.put("result", result);
            request.put(JSON, ret);
        } catch (JSONException e) {
            log.error("", e);
        }
        return JSON;
    }

    public String saveChannelGroup() throws JSONException {
        JSONObject ret = new JSONObject();
        ou = getCurrentOu();
        try {
            channelGroupManager.saveChannelGroup(group, status, ou);
            ret.put("msg", "success");
        } catch (Exception e) {
            ret.put("msg", "error");
            log.error("", e);
        }
        request.put(JSON, ret);
        return JSON;
    }

    public String deleteChannelGroup() throws JSONException {
        JSONObject ret = new JSONObject();
        ou = getCurrentOu();
        try {
            channelGroupManager.deleteChannelGroup(group);
            ret.put("msg", "success");
        } catch (Exception e) {
            ret.put("msg", "error");
            log.error("", e);
        }
        request.put(JSON, ret);
        return JSON;
    }

    public String findChannelGroupByCode() {
        String code = group.getCode();
        BiChannelGroup cg = channelGroupManager.findChannelGroupByCode(code);
        request.put(JSON, JsonUtil.obj2json(cg));
        return JSON;
    }

    public String saveChannelRef() throws JSONException {
        JSONObject ret = new JSONObject();
        ou = getCurrentOu();
        try {
            channelGroupManager.saveChannelRef(group, status, ou, channelRef);
            ret.put("msg", "success");
        } catch (Exception e) {
            ret.put("msg", "error");
            log.error("", e);
        }
        request.put(JSON, ret);
        return JSON;
    }

    public String findAllChannelRefByGroupId() {
        Long groupId = group.getId();
        List<BiChannelCommand> channelList = channelGroupManager.findAllChannelRefByGroupId(groupId);
        request.put(JSON, JsonUtil.collection2json(channelList));
        return JSON;
    }

    public String findAllChannelGroupByCIdAndOuId() {
        ou = getCurrentOu();
        List<BiChannelGroupCommand> channelGroupList = channelGroupManager.findAllChannelGroupByCIdAndOuId(channelId, groupId, ou);
        request.put(JSON, JsonUtil.collection2json(channelGroupList));
        return JSON;
    }


    public BiChannelGroup getGroup() {
        return group;
    }

    public void setGroup(BiChannelGroup group) {
        this.group = group;
    }

    public OperationUnit getOu() {
        return ou;
    }

    public void setOu(OperationUnit ou) {
        this.ou = ou;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<BiChannel> getChannelRef() {
        return channelRef;
    }

    public void setChannelRef(List<BiChannel> channelRef) {
        this.channelRef = channelRef;
    }

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

}
