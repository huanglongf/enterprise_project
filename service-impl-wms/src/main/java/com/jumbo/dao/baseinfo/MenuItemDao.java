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

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.MenuItem;

/**
 * Get MenuItem method
 * 
 * @author wanghua
 * 
 */
@Transactional
public interface MenuItemDao extends GenericEntityDao<MenuItem, String> {

    /**
     * @param userId
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<MenuItem> findMenuItemListByRoleIdSql(@QueryParam("roleId") Long roleId, @QueryParam("systemCode") String systemCode, RowMapper<MenuItem> rowMapper);

    @NativeQuery
    List<MenuItem> findMenuItemListByOuTypeSql(@QueryParam("ouType") Long ouType, @QueryParam("systemCode") String systemCode, RowMapper<MenuItem> rowMapper);

    @NamedQuery
    MenuItem findMenuItemByAcl(@QueryParam("acl") String acl);

    @NativeQuery
    List<MenuItem> findRolePrivByRoleIdSql(@QueryParam("roleId") Long roleId, @QueryParam("systemCode") String systemCode, RowMapper<MenuItem> rowMapper);


}
