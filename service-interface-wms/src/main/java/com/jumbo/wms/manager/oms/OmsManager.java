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
package com.jumbo.wms.manager.oms;

import java.io.File;
import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.support.excel.ReadStatus;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.oms.EtamOmsInvRule;
import com.jumbo.wms.model.oms.EtamOmsInvRuleType;

public interface OmsManager extends BaseManager {

    int SHEET_0 = 1;

    /**
     * 分页查询当前Etam安全库存设定
     * 
     * @param start
     * @param pageSize
     * @param inv
     * @param whOuId
     * @param srots
     * @return
     */
    Pagination<EtamOmsInvRule> findCurrentEtamOmsInvRuleByPage(int start, int pageSize, EtamOmsInvRule inv, EtamOmsInvRuleType type, Sort[] sorts);

    /**
     * 取消Etam安全库存设定
     * 
     * @param id
     */
    void cancelEtam(List<Long> id);

    /**
     * ETAM尺码品牌设定
     * 
     * @param file
     * @return
     */
    ReadStatus importEtamSizeRule(File file);

    /**
     * ETAM款号颜色设定
     * 
     * @param file
     * @return
     */
    ReadStatus importEtamBar9Rule(File file);
}
