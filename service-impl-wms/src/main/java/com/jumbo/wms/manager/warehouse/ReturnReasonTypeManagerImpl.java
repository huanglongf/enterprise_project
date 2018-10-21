package com.jumbo.wms.manager.warehouse;

import java.util.Date;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.warehouse.ReturnReasonTypeDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;

@Transactional
@Service("reasonTypeManager")
public class ReturnReasonTypeManagerImpl extends BaseManagerImpl implements ReturnReasonTypeManager {

    private static final long serialVersionUID = 1L;
    @Autowired
    private ReturnReasonTypeDao returnReasonTypeDao;


    /**
     * 退货原因查询
     */
    @Override
    public Pagination<StockTransApplicationCommand> findAllReturnGoods(int start, int pageSize, Long ouId, Date createTime, Date endCreateTime, Integer returnReasonType, StockTransApplicationCommand sta, Sort[] sorts) {
        String code = null;
        String refSlipCode = null;
        String trackingNo = null;
        String slipCode1 = null;
        String slipCode2 = null;
        if (sta != null) {
            if (StringUtils.hasText(sta.getCode())) {
                code = sta.getCode();
            }
            if (StringUtils.hasText(sta.getTrackingNo())) {
                trackingNo = sta.getTrackingNo();
            }
            if (StringUtils.hasText(sta.getRefSlipCode())) {
                refSlipCode = sta.getRefSlipCode();
            }
            if (StringUtils.hasText(sta.getSlipCode1())) {
                slipCode1 = sta.getSlipCode1();
            }
            if (StringUtils.hasText(sta.getSlipCode2())) {
                slipCode2 = sta.getSlipCode2();
            }
        }

        return returnReasonTypeDao.findAllReturnGoods(start, pageSize, ouId, createTime, endCreateTime, trackingNo, code, returnReasonType, refSlipCode, slipCode1, slipCode2, sorts, new BeanPropertyRowMapper<StockTransApplicationCommand>(
                StockTransApplicationCommand.class));
    }

}
