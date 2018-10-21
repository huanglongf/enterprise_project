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
package com.jumbo.dao.baseinfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.jumbo.wms.model.baseinfo.MenuItem;
import loxia.dao.support.BaseRowMapper;

/**
 * 
 * @author wanghua
 * 
 */
@Deprecated
public class MenuItemRowMapper extends BaseRowMapper<MenuItem> {

    public MenuItem mapRow(ResultSet rs, int arg1) throws SQLException {
        MenuItem menuItem = new MenuItem();
        menuItem.setAcl(getResultFromRs(rs, "ACL", String.class));
        menuItem.setEntryURL(getResultFromRs(rs, "ENTRY_URL", String.class));
        menuItem.setIsGroup(getResultFromRs(rs, "IS_GROUP", Boolean.class));
        menuItem.setIsSeperator(getResultFromRs(rs, "IS_SEPERATOR", Boolean.class));
        menuItem.setMenuId(getResultFromRs(rs, "MENU_ID", String.class));
        menuItem.setName(getResultFromRs(rs, "NAME", String.class));
        menuItem.setSortNo(getResultFromRs(rs, "SORT_NO", Integer.class));
        String parentId = getResultFromRs(rs, "PARENT_MENU_ID", String.class);
        if (parentId != null && parentId.length() > 0) {
            MenuItem parent = new MenuItem();
            parent.setMenuId(parentId);
            menuItem.setParent(parent);
        }
        return menuItem;
    }

}
