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

package com.jumbo.wms.manager.baseinfo;

import java.util.ArrayList;
import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.json.JSONArray;
import loxia.support.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.authorization.PrivilegeDao;
import com.jumbo.dao.baseinfo.MenuItemDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.MenuItem;

/**
 * 
 * @author wanghua
 * 
 */
@Transactional
@Service("menuManager")
public class MenuManagerImpl extends BaseManagerImpl implements MenuManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1025666996137548808L;
    private static final Logger log = LoggerFactory.getLogger(MenuManagerImpl.class);
    @Autowired
    private MenuItemDao menuItemDao;
    @Autowired
    private PrivilegeDao priDao;

    @Transactional(readOnly = true)
    public List<MenuItem> getMenuListByRoleId(Long roleId, String systemCode) {
        if (log.isDebugEnabled()) {
            log.debug("----------MenuManagerImpl.getMenuList({})", roleId);
        }
        List<MenuItem> result = null;
        result = menuItemDao.findMenuItemListByRoleIdSql(roleId, systemCode, new BeanPropertyRowMapperExt<MenuItem>(MenuItem.class));
        result = rebuild(result);
        return result;
    }

    private List<MenuItem> rebuild(List<MenuItem> list) {
        List<MenuItem> root = new ArrayList<MenuItem>();
        if (list == null || list.isEmpty()) {
            return list;
        }
        MenuItem[] level = new MenuItem[3];
        for (int i = 0; i < list.size(); i++) {
            MenuItem _this = list.get(i);
            if (i == 0 || _this.getParent() == null) {
                root.add(_this);
            } else {
                addToRoot(level, _this);
            }
            setLevel(level, _this);
        }
        // Iterator<MenuItem> it=root.iterator();
        // while(it.hasNext()) {
        // MenuItem _this=it.next();
        // if(_this.getChildren()==null||_this.getChildren().isEmpty()) {
        // it.remove();
        // }
        // }
        return root;
    }

    private List<MenuItem> rebuild(List<MenuItem> list, List<String> privileges) {
        List<MenuItem> root = new ArrayList<MenuItem>();
        if (list == null || list.isEmpty()) {
            return list;
        }
        MenuItem[] level = new MenuItem[3];
        for (int i = 0; i < list.size(); i++) {
            MenuItem _this = list.get(i);
            if (_this.getName().equals("特殊处理商品维护")) {
                // log.error("dddd");
            }
            if (!_this.getIsGroup() && (_this.getAcl() == null || (StringUtils.hasLength(_this.getAcl()) && privileges.contains(_this.getAcl())))) {
                _this.setChecked(true);
            } else {
                _this.setChecked(false);
            }
            if (i == 0 || _this.getParent() == null) {
                root.add(_this);
            } else {
                addToRoot(level, _this);
            }
            setLevel(level, _this);
        }
        return root;
    }

    private void setLevel(MenuItem[] level, MenuItem _this) {
        if (_this.getIsGroup()) {
            if (_this.getParent() == null) {
                level[0] = _this;
            } else {
                if (_this.getParent().getMenuId().equals(level[0].getMenuId())) {
                    level[1] = _this;
                } else {
                    level[2] = _this;
                }
            }
        }
    }

    private void addToRoot(MenuItem[] level, MenuItem _this) {
        if (level == null || level[0] == null) {
            throw new IllegalArgumentException("The first level node must not be null");
        }
        MenuItem parent = level[0];// [level[1] == null ||
                                   // !level[1].getMenuId().equals(_this.getParent().getMenuId()) ?
                                   // 0 : 1];
        if (level[1] == null) {
            parent = level[0];
        } else {
            if (level[1].getMenuId().equals(_this.getParent().getMenuId())) {
                parent = level[1];
            } else {
                if (level[2] == null) {

                } else if (level[2].getMenuId().equals(_this.getParent().getMenuId())) {
                    parent = level[2];
                } else {

                }
            }
        }
        (parent.getChildren() == null ? new ArrayList<MenuItem>() : parent.getChildren()).add(_this);
    }

    public JSONArray getMenuListByOuTypeId(Long ouTypeId, Long roleId, String systemCode) {
        List<MenuItem> result = null;
        result = menuItemDao.findMenuItemListByOuTypeSql(ouTypeId, systemCode, new BeanPropertyRowMapperExt<MenuItem>(MenuItem.class));
        result = rebuild(result, priDao.findPrivilegeAclByRoleId(roleId, new SingleColumnRowMapper<String>(String.class)));
        try {
            return rebuildRoleTree(result);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }

    private JSONArray rebuildRoleTree(List<MenuItem> list) throws Exception {
        List<JSONObject> listJson = new ArrayList<JSONObject>();
        if (list == null || list.isEmpty()) {
            return new JSONArray(listJson);
        }
        for (MenuItem each : list) {
            if (each.getIsGroup() && each.getChildren().isEmpty()) continue;
            listJson.add(ouToJson(each));
        }
        return new JSONArray(listJson);
    }

    private JSONObject ouToJson(MenuItem obj) throws Exception {
        JSONObject json = new JSONObject();
        json.put("id", obj.getMenuId() + ":" + obj.getAcl());
        json.put("text", obj.getName());
        JSONObject attributes = new JSONObject();
        attributes.put("checked", obj.getChecked());
        json.put("attributes", attributes);
        if (obj.getChildren() != null && obj.getChildren().size() > 0) {
            json.put("children", rebuildRoleTree(obj.getChildren()));
        }
        return json;
    }

    public JSONArray findRolePrivilegeList(Long roleId, String systemCode) {
        List<MenuItem> result = null;
        result = menuItemDao.findMenuItemListByRoleIdSql(roleId, systemCode, new BeanPropertyRowMapperExt<MenuItem>(MenuItem.class));
        result = rebuild(result);
        try {
            return rebuildRoleTree(result);
        } catch (Exception e) {
            log.error("", e);
        }
        return null;
    }
}
