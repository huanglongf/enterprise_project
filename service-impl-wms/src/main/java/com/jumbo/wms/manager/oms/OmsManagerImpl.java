/**
 * EventObserver * Copyright (c) 2010 Jumbomart All Rights Reserved.
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.support.excel.ExcelReader;
import loxia.support.excel.ReadStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.oms.EtamOmsInvRuleDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.oms.EtamOmsInvRule;
import com.jumbo.wms.model.oms.EtamOmsInvRuleType;

@Transactional
@Service("omsManager")
public class OmsManagerImpl extends BaseManagerImpl implements OmsManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6740075902171107666L;

    @Autowired
    private EtamOmsInvRuleDao etamOmsInvRuleDao;

    @Resource(name = "etamSizeRuleReader")
    private ExcelReader etamSizeRuleReader;
    @Resource(name = "etamBar9RuleReader")
    private ExcelReader etamBar9RuleReader;

    @SuppressWarnings("unchecked")
    public ReadStatus importEtamSizeRule(File file) {
        if (file == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR_IMPORT_FILE_IS_NULL);
        }
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        try {
            rs = etamSizeRuleReader.readSheet(new FileInputStream(file), 0, beans);
        } catch (FileNotFoundException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {"FILE RREAD ERROR"});
        }
        List<EtamOmsInvRule> sscList = (List<EtamOmsInvRule>) beans.get("data");
        // 处理数据
        for (EtamOmsInvRule cmd : sscList) {
            EtamOmsInvRule r = etamOmsInvRuleDao.findByBrandSize(cmd.getSizes(), cmd.getBrand());
            if (r == null) {
                cmd.setRuleType(1);
                etamOmsInvRuleDao.save(cmd);
            } else {
                r.setQty(cmd.getQty());
                etamOmsInvRuleDao.save(r);
            }
        }
        return rs;
    }

    @SuppressWarnings("unchecked")
    public ReadStatus importEtamBar9Rule(File file) {
        if (file == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR_IMPORT_FILE_IS_NULL);
        }
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = null;
        try {
            rs = etamBar9RuleReader.readSheet(new FileInputStream(file), 0, beans);
        } catch (FileNotFoundException e) {
            log.error("", e);
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, new Object[] {"FILE RREAD ERROR"});
        }
        List<EtamOmsInvRule> sscList = (List<EtamOmsInvRule>) beans.get("data");
        // 处理数据
        for (EtamOmsInvRule cmd : sscList) {
            EtamOmsInvRule r = etamOmsInvRuleDao.findByBar9Color(cmd.getBar9(), cmd.getColor());
            if (r == null) {
                cmd.setRuleType(2);
                etamOmsInvRuleDao.save(cmd);
            } else {
                r.setQty(cmd.getQty());
                etamOmsInvRuleDao.save(r);
            }
        }
        return rs;
    }

    public Pagination<EtamOmsInvRule> findCurrentEtamOmsInvRuleByPage(int start, int pageSize, EtamOmsInvRule inv, EtamOmsInvRuleType type, Sort[] sorts) {
        if (type == EtamOmsInvRuleType.BRANDSIZE) {
            if (inv != null) {
                if (StringUtils.hasText(inv.getBrand())) {
                    inv.setBrand(inv.getBrand() + "%");
                } else {
                    inv.setBrand(null);
                }
                if (StringUtils.hasText(inv.getSizes())) {
                    inv.setSizes(inv.getSizes() + "%");
                } else {
                    inv.setSizes(null);
                }
            } else {
                inv = new EtamOmsInvRule();
            }
            return etamOmsInvRuleDao.findEtamOmsInvRule1ByPage(start, pageSize, inv.getBrand(), inv.getSizes(), new BeanPropertyRowMapper<EtamOmsInvRule>(EtamOmsInvRule.class), sorts);
        } else {
            if (inv != null) {
                if (StringUtils.hasText(inv.getBar9())) {
                    inv.setBar9(inv.getBar9() + "%");
                } else {
                    inv.setBar9(null);
                }
                if (StringUtils.hasText(inv.getColor())) {
                    inv.setColor(inv.getColor() + "%");
                } else {
                    inv.setColor(null);
                }
            } else {
                inv = new EtamOmsInvRule();
            }
            return etamOmsInvRuleDao.findEtamOmsInvRule2ByPage(start, pageSize, inv.getBar9(), inv.getColor(), new BeanPropertyRowMapper<EtamOmsInvRule>(EtamOmsInvRule.class), sorts);
        }
    }

    public void cancelEtam(List<Long> ids) {
        if (null == ids || 0 >= ids.size()) {
            return;
        }
        for (int i = 0; i < ids.size(); i++) {
            etamOmsInvRuleDao.cancelEtam(ids.get(i));
        }
    }

}
