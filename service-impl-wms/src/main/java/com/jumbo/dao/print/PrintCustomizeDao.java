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
package com.jumbo.dao.print;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.PrintCustomizeCommand;
import com.jumbo.wms.model.print.PrintCustomize;

@Transactional
public interface PrintCustomizeDao extends GenericEntityDao<PrintCustomize, Long> {

    @NativeQuery
    PrintCustomizeCommand getPrintCustomizeByOwnerAndType(@QueryParam(value = "owner") String owner, @QueryParam(value = "printType") Integer printType, RowMapper<PrintCustomizeCommand> rowMapper);

    @NativeQuery(pagable = true)
    Pagination<PrintCustomize> findPrintCustomizeByOwner(int start, int size, Sort sort[], @QueryParam("owner") String owner, RowMapper<PrintCustomize> r);

    @NamedQuery
    PrintCustomize findPcBytempletCode(@QueryParam("templetCode") String templetCode);

    /**
     * 获取所有打印编码code
     */
    @NativeQuery
    List<PrintCustomize> findPcCode(RowMapper<PrintCustomize> r);

}
