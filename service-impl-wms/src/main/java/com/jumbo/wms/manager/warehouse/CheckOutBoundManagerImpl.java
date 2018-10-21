package com.jumbo.wms.manager.warehouse;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhStaPickingListLogDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.warehouse.HandOverList;
import com.jumbo.wms.model.warehouse.PackageInfo;
import com.jumbo.wms.model.warehouse.PackageInfoCommand;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.StaAdditionalLine;
import com.jumbo.wms.model.warehouse.StaLineCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;

/**
 * 秒杀订单--核对--出库--交接 业务逻辑实现
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
@Service("checkOutBoundManager")
public class CheckOutBoundManagerImpl extends BaseManagerImpl implements CheckOutBoundManager {

    /**
     * 
     */
    private static final long serialVersionUID = -7816841294393792542L;
    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private WhStaPickingListLogDao pickingListLogDao;

    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaDeliveryInfoDao diDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerExecute wareHouseManagerExecute;
    @Autowired
    private StockTransApplicationDao stockTransApplicationDao;

    /**
     * 根据查询条件查询秒杀配货清单列表
     */
    @Override
    public Pagination<PickingListCommand> getAllSecKillPickingListByStatus(int start, int pageSize, Date date, Date date2, PickingList pickingList, Long ouId, Sort[] sorts) {
        String code = null;
        if (StringUtils.hasText(pickingList.getCode())) {
            code = pickingList.getCode();
        }
        return pickingListDao.getAllSecKillPickingListByStatus(start, pageSize, date, date2, code, pickingList.getStatus().getValue(), ouId, sorts, new BeanPropertyRowMapper<PickingListCommand>(PickingListCommand.class));
    }

    /**
     * 根据查询条件查询秒杀配货清单列表
     */
    @Override
    public Pagination<PickingListCommand> getAllSecKillPickingListByStatusopc(int start, int pageSize, Date date, Date date2, PickingList pickingList, Long ouId, List<Long> lists, Sort[] sorts) {
        String code = null;
        if (StringUtils.hasText(pickingList.getCode())) {
            code = pickingList.getCode();
        }
        if (ouId != null) {
            lists = null;
        }
        return pickingListDao.getAllSecKillPickingListByStatusopc(start, pageSize, date, date2, code, pickingList.getStatus().getValue(), ouId, lists, sorts, new BeanPropertyRowMapper<PickingListCommand>(PickingListCommand.class));
    }

    @Override
    public List<StaLineCommand> getStaPickingListPgIndex(Long id, Sort[] sorts) {
        return staLineDao.getStaPickingListPgIndex(id, sorts, new BeanPropertyRowMapper<StaLineCommand>(StaLineCommand.class));
    }

    @Override
    public void generSecKillHandOverList(BigDecimal weight, Long pId, List<StaAdditionalLine> saddlines, Long userId, Long whId) {
        List<Long> sta = staDao.findStaByPid(pId, new SingleColumnRowMapper<Long>(Long.class));
        // List<String> transList = new ArrayList<String>();
        for (int i = 0; i < sta.size(); i++) {
            // 得到物流面单号 核对出库用
            String trackingNO = diDao.getTransNoById(sta.get(i), new SingleColumnRowMapper<String>(String.class));
            List<PackageInfo> packageInfos = new ArrayList<PackageInfo>();
            PackageInfo pi = new PackageInfo();
            pi.setTrackingNo(trackingNO);
            // 设置仓库ou_id
            OperationUnit opUnit = new OperationUnit();
            opUnit.setId(whId);
            pi.setOpUnit(opUnit);
            // //////////
            packageInfos.add(pi);
            // 核对
            wareHouseManager.checkDistributionList(null, packageInfos, sta.get(i), null, null, userId);
            // transList.add(trackingNO);
        }
        for (int i = 0; i < sta.size(); i++) {
            String trackingNO = diDao.getTransNoById(sta.get(i), new SingleColumnRowMapper<String>(String.class));
            boolean rs = wareHouseManager.salesStaOutBound(sta.get(i), userId, whId, trackingNO, weight, saddlines, false,null);
            if (!rs) {
                throw new BusinessException(ErrorCode.ERROR_OUT_PROCEDURE);// 出库过程失败
            }
        }


    }

    @SuppressWarnings("unchecked")
    @Override
    public Long generHandOverList(Long id, Long whId, Long ouId, Long userId) {
        Long handId = null;
        PickingList pl = pickingListDao.getByPrimaryKey(id);
        List<Long> sta = staDao.findStaByPid(id, new SingleColumnRowMapper<Long>(Long.class));
        List<String> transList = new ArrayList<String>();
        for (int i = 0; i < sta.size(); i++) {
            String trackingNO = diDao.getTransNoById(sta.get(i), new SingleColumnRowMapper<String>(String.class));
            transList.add(trackingNO);
        }
        // 创建单据
        List<PackageInfoCommand> plist = null;
        try {
            Map<String, Object> resultMap = wareHouseManager.hoListCreateByHandStep1(transList, pl.getLpcode(), whId, null);
            if (resultMap.get("removeByTrackingNo") != null || resultMap.get("removeBylpcode") != null || resultMap.get("removeBySta") != null) {
                throw new BusinessException(ErrorCode.HAVE_NO_CREATE);// 存在不可创建的单据
            } else {
                plist = (List<PackageInfoCommand>) resultMap.get("pclist");
            }
        } catch (BusinessException e) {
            throw new BusinessException(ErrorCode.CREATE_ERROE_HAND);// 创建交接清单过程出现错误
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.CREATE_FAILURE_HAND);// 未能成功创建交接清单
        }
        if (plist != null) {
            List<Long> idList = new ArrayList<Long>();
            for (PackageInfoCommand pc : plist) {
                idList.add(pc.getId());
            }
            try {
                // OperationUnit ou = operationUnitDao.getByPrimaryKey(whId);
                HandOverList hand = wareHouseManagerExecute.createHandOverList(pl.getLpcode(), idList, ouId, userId, false);
                handId = hand.getId();
                pl.setHandOverList(hand);
                pickingListDao.save(pl);
            } catch (BusinessException e) {
                throw new BusinessException(ErrorCode.CREATE_ERROR_HANDOVER);// 创建交货清单过程出现错误
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.CREATE_FALIURE_HANDOVER);// 未能成功创建交货清单
            }
        }

        return handId;
    }

    @Override
    public List<StockTransApplicationCommand> getCancelStaListPgIndex(Long id, Sort[] sorts) {
        List<Integer> statusList = new ArrayList<Integer>();
        statusList.add(StockTransApplicationStatus.CANCELED.getValue());
        statusList.add(StockTransApplicationStatus.CANCEL_UNDO.getValue());
        return stockTransApplicationDao.getCancelStaListPgIndex(id, statusList, sorts, new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
    }

    @Override
    public void deleteStaById(Long id, Long staId) {
        stockTransApplicationDao.updatePickingListIdById(staId);
        stockTransApplicationDao.flush();
        PickingList pl = pickingListDao.getByPrimaryKey(id);
        List<StockTransApplication> staList = pl.getStaList();
        if (staList.size() == 0) {
            pickingListLogDao.updatePickingListLog(id, "CheckOutBoundManagerImpl.deleteStaById");
            pickingListDao.deleteByPrimaryKey(id);
        }
    }


}
