package com.jumbo.wms.manager.warehouse;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.ImperfectCartonLineDao;
import com.jumbo.dao.warehouse.SkuImperfectDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.ImperfectCartonLineCommand;
import com.jumbo.wms.model.warehouse.SkuImperfectCommand;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;

import loxia.dao.support.BeanPropertyRowMapperExt;

@Service("warehouseReturnManager")
public class WarehouseReturnManagerImpl extends BaseManagerImpl implements WarehouseReturnManager {

    /**
     * 
     */
    private static final long serialVersionUID = -3086515444398954146L;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;
    @Autowired
    private SkuImperfectDao imperfectDao;
    @Autowired
    private ImperfectCartonLineDao cartonLineDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private StockTransApplicationDao staDao;

    @Override
    public void validateSkuIsPlanOrNot(String staCode, String barCode, Long qty, Long whouid) {
        Long customerId = wareHouseManagerQuery.getCustomerByWhouid(whouid);
        Sku sku = skuDao.getByBarcode(barCode, customerId);
        if (sku == null) {
            throw new BusinessException(ErrorCode.SKU_NOT_FIND_BY_BARCODE);
        } else {
            StockTransApplication sta = staDao.getByCode(staCode);
            BiChannel bc = biChannelDao.getByCode(sta.getOwner());
            // 当前条码对应商品不在计划范围之内!
            StaLine staLine = null;
            if (bc.getIsPartOutbound() != null && bc.getIsPartOutbound()) {
                staLine = staLineDao.getPartStaLineBySkuIdAndStaCode(staCode, sku.getId(), new BeanPropertyRowMapper<StaLine>(StaLine.class));
            } else {
                staLine = staLineDao.getPlanStaLineBySkuIdAndStaCode(staCode, sku.getId(), new BeanPropertyRowMapper<StaLine>(StaLine.class));
            }
            if (staLine == null) {
                throw new BusinessException(ErrorCode.SKU_NOT_IN_PLAN);
            } else {
                if (staLine.getQuantity().compareTo(qty) < 0) {
                    // 错误操作，执行量不能大于计划量。
                    throw new BusinessException(ErrorCode.SKU_QTY_BIGTHAN_PLAN);
                }
            }
        }

    }

    @Override
    public StaLineCommand staLineStatus(String staCode) {
        List<StaLineCommand> staLineCommands = staLineDao.findstaLineStatus(staCode, new BeanPropertyRowMapperExt<StaLineCommand>(StaLineCommand.class));
        if (staLineCommands == null) {
            throw new BusinessException(ErrorCode.NOT_FIND_STA);
        } else {
            if (staLineCommands.size() != 1) {
                throw new BusinessException(ErrorCode.DOUBLE_INVSTATUS_ERROR);
            } else {
                StaLineCommand command = staLineCommands.get(0);
                return command;
            }
        }
    }

    @Override
    public SkuImperfectCommand validateSkuImperfect(String defectCode, String barCode) {
        ImperfectCartonLineCommand cartonLineCommand = cartonLineDao.findImperfectCartonLineCode(defectCode, new BeanPropertyRowMapperExt<ImperfectCartonLineCommand>(ImperfectCartonLineCommand.class));
        if (cartonLineCommand != null) {
            throw new BusinessException(ErrorCode.IMPERFECT_CARTON_CODE_ERROR, new Object[] {defectCode});
        }
        SkuImperfectCommand command = imperfectDao.findSkuImperfectBarCode(defectCode, barCode, new BeanPropertyRowMapperExt<SkuImperfectCommand>(SkuImperfectCommand.class));
        if (command == null) {
            throw new BusinessException(ErrorCode.IMPERFECT_CODE_ERROR, new Object[] {defectCode, barCode});
        }
        return command;
    }


}
