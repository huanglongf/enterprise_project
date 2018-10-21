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

import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.utils.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.baseinfo.SkuModifyLogDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.command.SkuModifyLogCommand;

/**
 * 
 * @author hui.li
 * 
 */
@Transactional
@Service("skuModifyLogManager")
public class SkuModifyLogManagerImpl extends BaseManagerImpl implements SkuModifyLogManager {
    /**
     * 
     */
    private static final long serialVersionUID = -2244355092527798180L;

    @Autowired
    private SkuModifyLogDao skuModifyLogDao;

    @Override
    public Pagination<SkuModifyLogCommand> findSkuModifyLogAll(int start, int pageSize, Map<String, Object> m, Sort[] sorts) {
        String sDate = null;
        String eDate = null;
        Date startDate = null;
        Date endDate = null;
        if (m != null && !m.isEmpty()) {
            sDate = (String) m.get("bDate");
            eDate = (String) m.get("eDate");
        }
        if (sDate != null) {
            try {
                startDate = DateUtil.parse(sDate);
                m.put("beginDate", startDate);
            } catch (ParseException e) {
                if (log.isErrorEnabled()) {
                    log.error("findSkuModifyLogAll", e);
                }
            }
        }
        if (eDate != null) {
            try {
                endDate = DateUtil.parse(eDate);
                m.put("endDate", endDate);

            } catch (ParseException e) {
                if (log.isErrorEnabled()) {
                    log.error("findSkuModifyLogAll-ParseException", e);
                }
            }
        }

        // Pagination<SkuModifyLog> p=skuModifyLogDao.getSkuModifyLogAll(start, pageSize, sorts);

        Pagination<SkuModifyLogCommand> p = skuModifyLogDao.findSkuModifyLogAll(start, pageSize, m, new BeanPropertyRowMapper<SkuModifyLogCommand>(SkuModifyLogCommand.class), sorts);
        return p;

    }

}
