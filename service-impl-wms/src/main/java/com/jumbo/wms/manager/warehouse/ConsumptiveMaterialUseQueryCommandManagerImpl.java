package com.jumbo.wms.manager.warehouse;

import java.util.Date;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jumbo.dao.command.ConsumptiveMaterialUseQueryCommandDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.command.ConsumptiveMaterialUseQueryCommand;

/**
 * 耗材使用情况查询业务逻辑实现
 * 
 * @author jinlong.ke
 * 
 */
@Service("consumptiveMaterialUseQueryCommandManager")
public class ConsumptiveMaterialUseQueryCommandManagerImpl extends BaseManagerImpl implements ConsumptiveMaterialUseQueryCommandManager {

    /**
     * 
     */
    private static final long serialVersionUID = -198948661615574456L;
    @Autowired
    private ConsumptiveMaterialUseQueryCommandDao cmDao;

    @Override
    public Pagination<ConsumptiveMaterialUseQueryCommand> findCmUseList(int start, int pageSize, Date date, Date date2, ConsumptiveMaterialUseQueryCommand cm, Long id, Sort[] sorts) {
        String slipCode = null;
        String recommandSku = null;
        String usedSku = null;
        String isMatch = null;
        String checkUser = null;
        String outboundUser = null;
        if (cm != null) {
            if (StringUtils.hasText(cm.getSlipCode())) {
                slipCode = cm.getSlipCode();
            }
            if (StringUtils.hasText(cm.getRecommandSku())) {
                recommandSku = cm.getRecommandSku();
            }
            if (StringUtils.hasText(cm.getUsedSku())) {
                usedSku = cm.getUsedSku();
            }
            if (StringUtils.hasText(cm.getIsMatch())) {
                isMatch = cm.getIsMatch();
            }
            if (StringUtils.hasText(cm.getCheckUser())) {
                checkUser = cm.getCheckUser();
            }
            if (StringUtils.hasText(cm.getOutboundUser())) {
                outboundUser = cm.getOutboundUser();
            }
        }
        return cmDao.findCmUseList(start, pageSize, date, date2, slipCode, recommandSku, usedSku, isMatch, checkUser, outboundUser, id, sorts, new BeanPropertyRowMapper<ConsumptiveMaterialUseQueryCommand>(ConsumptiveMaterialUseQueryCommand.class));
    }
}
