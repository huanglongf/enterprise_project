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

import java.util.List;

import loxia.support.json.JSONArray;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.baseinfo.MenuItem;

public interface MenuManager extends BaseManager {
    /**
     * 根据RoleId拉菜单
     * 
     * @param roleId
     * @param systemCode
     * @return
     */
    List<MenuItem> getMenuListByRoleId(Long roleId, String systemCode);

    JSONArray getMenuListByOuTypeId(Long ouTypeId, Long roleId, String systemCode);

    /**
     * 取角色的权限树
     * 
     * @param roleId
     * @return
     */
    JSONArray findRolePrivilegeList(Long roleId, String systemCode);
}
