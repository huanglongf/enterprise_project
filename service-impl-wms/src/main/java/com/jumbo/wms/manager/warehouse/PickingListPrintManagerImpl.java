package com.jumbo.wms.manager.warehouse;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhPlDiffLineDao;
import com.jumbo.dao.warehouse.WhStaPickingListLogDao;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.command.StaDeliverCommand;
import com.jumbo.wms.model.warehouse.BiWarehouseAddStatusCommand;
import com.jumbo.wms.model.warehouse.PickingList;
import com.jumbo.wms.model.warehouse.PickingListCheckMode;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.WhAddStatusMode;
import com.jumbo.wms.model.warehouse.WhAddTypeMode;
import com.jumbo.wms.model.warehouse.WhPlDiffLine;
import com.jumbo.wms.model.warehouse.WhPlDiffLineCommand;
import com.jumbo.wms.model.warehouse.WhStaPickingListLog;

import loxia.dao.Pagination;
import loxia.dao.Sort;
import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.excel.ExcelReader;
import loxia.support.excel.ExcelWriter;
import loxia.support.excel.ReadStatus;

/**
 * 
 * @author jinlong.ke
 * 
 */
@Transactional
@Service("pickingListPrintManager")
public class PickingListPrintManagerImpl extends BaseManagerImpl implements PickingListPrintManager {

    /**
     * 
     */
    private static final long serialVersionUID = 8429682856141610395L;
    @Autowired
    private PickingListDao pickingListDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private WareHouseManager wareHouseManger;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private WareHouseOutBoundManager wobManager;
    @Autowired
    private WhStaPickingListLogDao logDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private WhPlDiffLineDao wpdDao;
    @Resource(name = "staDeliverReader")
    private ExcelReader staDeliverReader;
    @Resource(name = "staDeliverWriter")
    private ExcelWriter staDeliverWriter;

    @Override
    public Pagination<PickingList> getAllPickingList(int start, int pageSize, PickingList pickingList, Boolean isMacaoOrder1, Boolean isPrintMaCaoHGD1, Long id, Sort[] sorts) {
        String code = null;
        Integer status = null;
        Integer pickType = null; // 包装类型
        Integer checkMode = null;
        String isSn = null;
        String lpCode = null;
        String sendCity = null;
        Long categoryId = null;
        Long skuSizeId = null;
        String isInvoice = null;
        String toLocation = null;
        String worker = null;
		String isPreSale1 = null;
        Integer isMacaoOrder = null;
        Integer isPrintMaCaoHGD = null;
        if (isMacaoOrder1 != null && isMacaoOrder1) {
            isMacaoOrder = 1;
        }
        if (isPrintMaCaoHGD1 != null && isPrintMaCaoHGD1) {
            isPrintMaCaoHGD = 1;
        }
        if (pickingList != null) {
            if (StringUtils.hasText(pickingList.getCode())) {
                code = pickingList.getCode();
            }
            if (StringUtils.hasText(pickingList.getIsSn1())) {
                if (pickingList.getIsSn1().equals("true")) {
                    isSn = "true";
                } else {
                    isSn = "false";
                }
            }
            if (StringUtils.hasText(pickingList.getLpcode())) {
                lpCode = pickingList.getLpcode();
            }
            if (pickingList.getStatus() != null) {
                status = pickingList.getStatus().getValue();
            }
            if (pickingList.getPackingType() != null) {
                pickType = pickingList.getPackingType();
            }
            if (StringUtils.hasText(pickingList.getIsInvoice1())) {
                if (pickingList.getIsInvoice1().equals("true")) {
                    isInvoice = "true";
                } else {
                    isInvoice = "false";
                }
            }
            // isInvoice = pickingList.getIsInvoice();
            if (StringUtils.hasText(pickingList.getCity())) {
                sendCity = pickingList.getCity();
            }
            categoryId = pickingList.getCategoryId();
            skuSizeId = pickingList.getSkuSizeId();
            // O2O+QS
            if (StringUtils.hasText(pickingList.getToLocation())) {
                toLocation = pickingList.getToLocation();
            }
            if (pickingList.getCheckMode() != null && pickingList.getCheckMode().equals(PickingListCheckMode.PICKING_SECKILL)) {
                checkMode = pickingList.getCheckMode().getValue();
            }
            if (StringUtils.hasText(pickingList.getWorker())) {
                worker = pickingList.getWorker();
            }
        }
        return pickingListDao.getAllPickingList(start, pageSize, sendCity, categoryId, skuSizeId, isInvoice, code, status, pickType, isSn, lpCode, checkMode, id, worker, toLocation, isMacaoOrder, isPrintMaCaoHGD,sorts, isPreSale1, new BeanPropertyRowMapper<PickingList>(
                PickingList.class));
    }

    @Override
    public Pagination<PickingList> getAllPickingQuery(int start, int pageSize, PickingList pickingList, Long id, Sort[] sorts) {
        String code = null;
        Integer status = null;
        Integer checkMode = null;
        String isSn = null;
        String lpCode = null;
        String sendCity = null;
        Long categoryId = null;
        Long skuSizeId = null;
        String isInvoice = null;
        if (pickingList != null) {
            if (StringUtils.hasText(pickingList.getCode())) {
                code = pickingList.getCode();
            }
            if (StringUtils.hasText(pickingList.getIsSn1())) {
                if (pickingList.getIsSn1().equals("true")) {
                    isSn = "true";
                } else {
                    isSn = "false";
                }
            }
            if (StringUtils.hasText(pickingList.getLpcode())) {
                lpCode = pickingList.getLpcode();
            }
            if (pickingList.getStatus() != null) {
                status = pickingList.getStatus().getValue();
            }
            if (StringUtils.hasText(pickingList.getIsInvoice1())) {
                if (pickingList.getIsInvoice1().equals("true")) {
                    isInvoice = "true";
                } else {
                    isInvoice = "false";
                }
            }
            // isInvoice = pickingList.getIsInvoice();
            if (StringUtils.hasText(pickingList.getCity())) {
                sendCity = pickingList.getCity();
            }
            categoryId = pickingList.getCategoryId();
            skuSizeId = pickingList.getSkuSizeId();
            if (pickingList.getCheckMode() != null && pickingList.getCheckMode().equals(PickingListCheckMode.PICKING_SECKILL)) {
                checkMode = pickingList.getCheckMode().getValue();
            }
        }
        return pickingListDao.getAllPickingQuery(start, pageSize, sendCity, categoryId, skuSizeId, isInvoice, code, status, isSn, lpCode, checkMode, id, sorts, new BeanPropertyRowMapper<PickingList>(PickingList.class));
    }

    @Override
    public Pagination<PickingList> getAllPickingListStatus(int start, int pageSize, PickingList pickingList, Long id, Sort[] sorts, List<String> priorityList) {
        String code = null;
        Integer status = null;
        Integer checkMode = null;
        Integer pickType = null; // 包装类型
        String isSn = null;
        String lpCode = null;
        List<String> cityList = null;
        Long categoryId = null;
        Long skuSizeId = null;
        String isInvoice = null;
        String toLocation = null;
        String worker = null;
        String isPreSale1 = null;

        if (pickingList != null) {
            if (null != pickingList.getIsPreSale() && !"".equals(pickingList.getIsPreSale())) {
                isPreSale1 = pickingList.getIsPreSale();
            } else {
                isPreSale1 = "0";
            }
            if (StringUtils.hasText(pickingList.getCode())) {
                code = pickingList.getCode();
            }
            if (StringUtils.hasText(pickingList.getIsSn1())) {
                if (pickingList.getIsSn1().equals("true")) {
                    isSn = "true";
                } else {
                    isSn = "false";
                }
            }
            if (StringUtils.hasText(pickingList.getLpcode())) {
                lpCode = pickingList.getLpcode();
            }
            if (pickingList.getStatus() != null) {
                status = pickingList.getStatus().getValue();
            }
            if (pickingList.getPackingType() != null) {
                pickType = pickingList.getPackingType();
            }
            if (StringUtils.hasText(pickingList.getIsInvoice1())) {
                if (pickingList.getIsInvoice1().equals("true")) {
                    isInvoice = "true";
                } else {
                    isInvoice = "false";
                }
            }
            cityList=getCityList(pickingList.getCity());

            categoryId = pickingList.getCategoryId();
            skuSizeId = pickingList.getSkuSizeId();
            // O2O+QS
            if (StringUtils.hasText(pickingList.getToLocation())) {
                toLocation = pickingList.getToLocation();
            }
            if (pickingList.getCheckMode() != null && pickingList.getCheckMode().equals(PickingListCheckMode.PICKING_SECKILL)) {
                checkMode = pickingList.getCheckMode().getValue();
            }
            if (StringUtils.hasText(pickingList.getWorker())) {
                worker = pickingList.getWorker();
            }
        }
        if (null == isPreSale1) {
            isPreSale1 = "0";
        }
        Boolean flag = true;
        if (null != priorityList && priorityList.size() > 0) {
            if (priorityList.contains("opposite")) {
                flag = false;
            }
        }
        // dropDown 优先发货城市标志位
        Boolean msg = true;
        if (null != cityList && cityList.size() > 0) {
            if (cityList.contains("opposite")) {
            	msg = false;
            }
        }

        return pickingListDao.getAllPickingListStatus(start, pageSize, cityList,msg, categoryId, skuSizeId, isInvoice, code, status, pickType, isSn, lpCode, checkMode, id, worker, WhAddStatusMode.PRINT.getValue(), toLocation, sorts, isPreSale1, flag,
                priorityList, new BeanPropertyRowMapper<PickingList>(PickingList.class));
    }

    @Override
    public Pagination<PickingList> getAllPickingListStatusBulk(int start, int pageSize, PickingList pickingList, Long id, Sort[] sorts, List<String> priorityList) {
        String code = null;
        Integer status = null;
        Integer checkMode = null;
        String isSn = null;
        String lpCode = null;
        List<String> cityList = null;
        Long categoryId = null;
        Long skuSizeId = null;
        String isInvoice = null;
        String toLocation = null;
        String worker = null;
        String isPreSale1 = null;
        if (pickingList != null) {
            if (null != pickingList.getIsPreSale() && !"".equals(pickingList.getIsPreSale())) {
                isPreSale1 = pickingList.getIsPreSale();
            } else {
                isPreSale1 = "0";
            }
            if (StringUtils.hasText(pickingList.getCode())) {
                code = pickingList.getCode();
            }
            if (StringUtils.hasText(pickingList.getIsSn1())) {
                if (pickingList.getIsSn1().equals("true")) {
                    isSn = "true";
                } else {
                    isSn = "false";
                }
            }
            if (StringUtils.hasText(pickingList.getLpcode())) {
                lpCode = pickingList.getLpcode();
            }
            if (pickingList.getStatus() != null) {
                status = pickingList.getStatus().getValue();
            }
            if (StringUtils.hasText(pickingList.getIsInvoice1())) {
                if (pickingList.getIsInvoice1().equals("true")) {
                    isInvoice = "true";
                } else {
                    isInvoice = "false";
                }
            }
            cityList=getCityList(pickingList.getCity());

            categoryId = pickingList.getCategoryId();
            skuSizeId = pickingList.getSkuSizeId();
            // O2O+QS
            if (StringUtils.hasText(pickingList.getToLocation())) {
                toLocation = pickingList.getToLocation();
            }
            if (pickingList.getCheckMode() != null && pickingList.getCheckMode().equals(PickingListCheckMode.PICKING_SECKILL)) {
                checkMode = pickingList.getCheckMode().getValue();
            }
            if (StringUtils.hasText(pickingList.getWorker())) {
                worker = pickingList.getWorker();
            }
        }
        if (null == isPreSale1) {
            isPreSale1 = "0";
        }
        Boolean msg = true;
        if (null != priorityList && priorityList.size() > 0) {
            if (priorityList.contains("opposite")) {
                msg = false;
            }
        }
     // dropDown 优先发货城市标志位
        Boolean flag = true;
        if (null != cityList && cityList.size() > 0) {
            if (cityList.contains("opposite")) {
            	flag = false;
            }
        }
        return pickingListDao.getAllPickingListStatusBulk(start, pageSize, cityList,flag, categoryId, skuSizeId, isInvoice, code, status, isSn, lpCode, checkMode, id, worker, WhAddStatusMode.PRINT.getValue(), toLocation, sorts, isPreSale1, msg, priorityList,
                new BeanPropertyRowMapper<PickingList>(PickingList.class));
    }

    @Override
    public Pagination<PickingList> findAllPickingList(int start, int pageSize, PickingList pickingList, Long id, Sort[] sorts) {
        String code = null;
        String lpCode = null;
        if (pickingList != null) {
            if (StringUtils.hasText(pickingList.getCode())) {
                code = pickingList.getCode();
            }
            if (StringUtils.hasText(pickingList.getLpcode())) {
                lpCode = pickingList.getLpcode();
            }

        }
        return pickingListDao.findAllPickingList(start, pageSize, code, lpCode, id, sorts, new BeanPropertyRowMapper<PickingList>(PickingList.class));
    }

    // 查询所有的配货清单(带仓库)
    @Override
    public Pagination<PickingListCommand> getAllPickingListsigleopc(int start, int pageSize, PickingList pickingList, Long id, List<Long> lists, Sort[] sorts) {
        String code = null;
        Integer status = null;
        Integer checkMode = null;
        Boolean isSn = null;
        String lpCode = null;
        if (pickingList != null) {
            if (StringUtils.hasText(pickingList.getCode())) {
                code = pickingList.getCode();
            }
            if (pickingList.getIsSn() != null) {
                isSn = pickingList.getIsSn();
            }
            if (StringUtils.hasText(pickingList.getLpcode())) {
                lpCode = pickingList.getLpcode();
            }
            if (pickingList.getStatus() != null) {
                status = pickingList.getStatus().getValue();
            }
            if (pickingList.getCheckMode() != null && pickingList.getCheckMode().equals(PickingListCheckMode.PICKING_SECKILL)) {
                checkMode = pickingList.getCheckMode().getValue();
            }
        }
        return pickingListDao.getAllPickingListsingleopc(start, pageSize, code, status, isSn, lpCode, checkMode, id, lists, sorts, new BeanPropertyRowMapper<PickingListCommand>(PickingListCommand.class));
    }

    @Override
    public PickingListCommand getPickingListByCode(String code, Integer checkMode, int whStatus, Long ouId) {
        if (whStatus == 0) {
            return pickingListDao.getPickingListByCode(code, ouId, checkMode, new BeanPropertyRowMapper<PickingListCommand>(PickingListCommand.class));
        } else {
            return pickingListDao.getPickingListByCodeAndStatus(code, ouId, checkMode, WhAddStatusMode.PRINT.getValue(), new BeanPropertyRowMapper<PickingListCommand>(PickingListCommand.class));
        }
    }

    @Override
    public Pagination<StockTransApplicationCommand> getAllStaByPickingListId(int start, int pageSize, Long pId, Long ouId, Sort[] sorts) {

        return staDao.getAllStaByPickingListId(start, pageSize, pId, ouId, sorts, new BeanPropertyRowMapperExt<StockTransApplicationCommand>(StockTransApplicationCommand.class));
    }

    @Override
    public void updateTransNoBySlipCode(List<StaDeliverCommand> std) {
        for (StaDeliverCommand sc : std) {
            staDeliveryInfoDao.updateTransNoBySlipCode(sc.getSlipCode(), sc.getTransNo());
        }
    }

    @Override
    public ReadStatus importStaDeliveryInfo(File file, Long pId) {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus readStatus = null;
        try {
            readStatus = staDeliverReader.readSheet(new FileInputStream(file), 0, beans);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        if (ReadStatus.STATUS_SUCCESS == readStatus.getStatus()) {
            @SuppressWarnings("unchecked")
            List<StaDeliverCommand> list = (List<StaDeliverCommand>) beans.get("staDelivery");
            if (list == null) {
                throw new BusinessException(ErrorCode.PRO_NO_DATA);// 导入的Excel没有数据
            }
            List<String> errList = new ArrayList<String>();// 定义错误List
            Map<String, String> st = new HashMap<String, String>();// 辅助计算是否重复
            // 验证是否存在重复维护的作业单
            for (StaDeliverCommand sc : list) {
                if (st.get(sc.getSlipCode()) == null) {
                    st.put(sc.getSlipCode(), sc.getTransNo());
                } else {
                    errList.add(sc.getSlipCode());
                }
            }
            if (!errList.isEmpty() && errList.size() > 0) {
                throw new BusinessException(ErrorCode.ERROR_SLIPCODE, new Object[] {errList.toString()});
            }
            errList.clear();
            st.clear();
            // 验证是否存在重复维护的快递单号
            for (StaDeliverCommand sc : list) {
                if (st.get(sc.getTransNo()) == null) {
                    st.put(sc.getTransNo(), sc.getSlipCode());
                } else {
                    errList.add(sc.getTransNo());
                }
            }
            if (!errList.isEmpty() && errList.size() > 0) {
                throw new BusinessException(ErrorCode.ERROR_TRANS, new Object[] {errList.toString()});
            }
            errList.clear();
            st.clear();
            // 验证是否为当前配货批的作业单，并判断快递单号是否正确
            for (StaDeliverCommand sc : list) {
                StockTransApplication sta = staDao.findStaBySlipCodeAndPid(pId, sc.getSlipCode(), new BeanPropertyRowMapper<StockTransApplication>(StockTransApplication.class));
                if (sta == null) {
                    errList.add(sc.getSlipCode() + "非本次维护对象");
                } else {
                    Boolean rs = wareHouseManger.checkTrackingNo(sc.getTransNo(), sta.getId());
                    if (!rs) {
                        errList.add(sc.getTransNo() + "格式不正确");
                    }
                }
            }
            if (!errList.isEmpty() && errList.size() > 0) {
                throw new BusinessException(ErrorCode.NULL_SET_ERROR, new Object[] {errList.toString()});
            } else {
                updateTransNoBySlipCode(list);
            }
        }
        return readStatus;
    }

    /**
     * 更新配货清单状态 bin,hu
     */
    @Override
    public void updatePickListWhStatus(Long plid, Long ouId, Long userID) {
        PickingList pList = pickingListDao.getByPrimaryKey(plid);
        WhStaPickingListLog pickLog = new WhStaPickingListLog();
        pickLog.setStatus(pList.getWhStatus());
        BiWarehouseAddStatusCommand bi = wobManager.getBiWhST(ouId, pList.getCheckMode(), 1, pList.getWhStatus().getValue(), 0, plid);
        pList.setWhStatus(WhAddStatusMode.valueOf(bi.getStatus()));
        pList.setWhType(WhAddTypeMode.valueOf(bi.getType()));
        pickingListDao.save(pList);
        // 插入picklingLog表
        pickLog.setLogTime(new Date());
        pickLog.setPickList(pList);
        pickLog.setUserId(userID);
        pickLog.setAddStatus(WhAddStatusMode.valueOf(bi.getStatus()));
        logDao.save(pickLog);
    }

    public void updatPickListById(Long plid) {
        PickingList pList = pickingListDao.getByPrimaryKey(plid);
        pList.setStartPrint(true);
        pickingListDao.save(pList);
    }

    /**
     * 拣货有差异，插入t_wh_pl_diff_line表
     */
    @Override
    public void addWhPlDiffLine(Long plid, List<WhPlDiffLine> whPlDiffLineList) {
        PickingList pList = pickingListDao.getByPrimaryKey(plid);
        for (WhPlDiffLine wpdList : whPlDiffLineList) {
            WhPlDiffLine wpd = new WhPlDiffLine();
            Sku sku = skuDao.getByPrimaryKey(wpdList.getSkuId().getId());
            wpd.setPickingList(pList);
            wpd.setSkuId(sku);
            wpd.setAddStatus(pList.getWhStatus());
            wpd.setPlanQty(wpdList.getPlanQty());
            wpd.setQty(wpdList.getQty());
            wpd.setPgIndex(wpdList.getPgIndex());
            wpdDao.save(wpd);
        }
    }

    /**
     * 获取拣货差异明细
     */
    @Override
    public List<WhPlDiffLineCommand> findWhPlDillLineByPid(Long plid, int status) {
        List<WhPlDiffLineCommand> wpdList = null;
        if (status == 0) {
            // 直接根据配货清单STATUS去查找
            PickingList pList = pickingListDao.getByPrimaryKey(plid);
            wpdList = wpdDao.findStaLineByPickingId(plid, pList.getWhStatus().getValue(), new BeanPropertyRowMapperExt<WhPlDiffLineCommand>(WhPlDiffLineCommand.class));
        }
        if (status == 1) {
            // 根据待拣货去查询 24
            wpdList = wpdDao.findStaLineByPickingId(plid, WhAddStatusMode.DIEKING.getValue(), new BeanPropertyRowMapperExt<WhPlDiffLineCommand>(WhPlDiffLineCommand.class));
        }
        return wpdList;
    }

    /**
     * 删除拣货差异明细 plid
     */
    @Override
    public void deleteWhPlDillLineByPid(Long plid) {
        PickingList pList = pickingListDao.getByPrimaryKey(plid);
        wpdDao.deleteWhPlDillLineByPid(plid, pList.getWhStatus().getValue());
    }

    /**
     * 差异明细确认完成
     */
    @Override
    public void donePickListWhDieking(Long plid, Long ouId, Long userID, List<WhPlDiffLine> whPlDiffLineList) {
        for (WhPlDiffLine wpd : whPlDiffLineList) {
            if (wpd.getPlanQty() == wpd.getQty()) {
                // 如果执行量和计划量相同就删除该条数据
                wpdDao.delete(wpd);
            } else {
                // 有变动保留数据，更新值
                wpdDao.updateWhPlDillLineByPid(wpd.getId(), wpd.getPlanQty(), wpd.getQty());
            }
        }
        updatePickListWhStatus(plid, ouId, userID);
    }

    /**
     * 获取分拣差异明细
     */
    @Override
    public List<WhPlDiffLineCommand> findWhPlDillLineByPidS(Long plid) {
        PickingList pList = pickingListDao.getByPrimaryKey(plid);
        return wpdDao.findWhPlDillLineByPidS(plid, pList.getWhStatus().getValue(), new BeanPropertyRowMapperExt<WhPlDiffLineCommand>(WhPlDiffLineCommand.class));
    }

    /**
     * 获取拣货和分拣的差异
     */
    @Override
    public List<WhPlDiffLineCommand> findWhPlDillLineByPidSD(Long plid) {
        return wpdDao.findWhPlDillLineByPidSD(plid, WhAddStatusMode.DIEKING.getValue(), WhAddStatusMode.SEPARATION.getValue(), new BeanPropertyRowMapperExt<WhPlDiffLineCommand>(WhPlDiffLineCommand.class));
    }

    /**
     * 多件核对查询明细
     */
    @Override
    public List<PickingListCommand> findPickListDetail(Long plId) {
        return pickingListDao.findPickListDetail(plId, new BeanPropertyRowMapperExt<PickingListCommand>(PickingListCommand.class));
    }

    /**
     * 配货批次分配 分页查询
     */
    public Pagination<PickingListCommand> findBatchAllocation(int start, int pageSize, Date createTimes, Date endTime, Date exeTime, Date endExeTime, PickingListCommand p, Sort[] sorts, Long ouId) {
        Date createTime = null;
        Date endCreateTime = null;
        Date executionTime = null;
        Date endExecutionTime = null;
        Integer status = null;
        String jobNumber = null;
        String code = null;
        String nodeType = null;
        if (p != null) {
            if (createTimes != null) {
                createTime = createTimes;
            }
            if (endTime != null) {
                endCreateTime = endTime;
            }
            if (exeTime != null) {
                executionTime = exeTime;
            }
            if (endExeTime != null) {
                endExecutionTime = endExeTime;
            }
            if (p.getStatusInt() != null) {
                status = p.getStatusInt();
            }
            if (StringUtils.hasText(p.getCode())) {
                code = p.getCode();
            }
            if (StringUtils.hasText(p.getJobNumber())) {
                jobNumber = p.getJobNumber();
            }
            if (StringUtils.hasText(p.getNodeType())) {
                nodeType = p.getNodeType();
            }
        }
        return pickingListDao.findBatchAllocation(start, pageSize, createTime, endCreateTime, executionTime, endExecutionTime, code, status, jobNumber, nodeType, sorts, ouId, new BeanPropertyRowMapper<PickingListCommand>(PickingListCommand.class));
    }

    /**
     * 根据产品id查找仓库id
     */
    public Long getWhidBypickid(String pcode) {

        return pickingListDao.getWhidBypickid(pcode, new SingleColumnRowMapper<Long>(Long.class));
    }

    /**
     * 作业单id，查询其作业单实体
     * 
     * @param staId
     * @param beanPropertyRowMapper
     * @return
     */
    public Long findPickingListByPickId(Long pickId) {

        return pickingListDao.findPickingListByPickId(pickId, new SingleColumnRowMapper<Long>(Long.class));
    }


    public PickingList getPickingListById(Long id) {
        PickingList list = pickingListDao.getByPrimaryKey(id);
        PickingListCommand cmdList = new PickingListCommand();
        BeanUtils.copyProperties(list, cmdList);
        cmdList.setWarehouse(null);
        cmdList.setCreator(null);
        cmdList.setOperator(null);
        cmdList.setStaList(null);
        cmdList.setHandOverList(null);
        cmdList.setWarehouse(null);
        return cmdList;
    }

    /**
     * 根据配货批次号查询秒杀配货批基本信息
     * 
     * @param code
     * @param beanPropertyRowMapper
     * @return
     */
    public PickingListCommand findCheckoutPickingByCode(String code, Long whId, List<Long> idList) {
        return pickingListDao.findCheckoutPickingByCode(code, whId, idList, new BeanPropertyRowMapper<PickingListCommand>(PickingListCommand.class));
    }

    /**
     * 根据配货清单查询物流交接单
     * 
     * @param id
     * @param singleColumnRowMapper
     * @return
     */
    public Long getHandOverIdByPickingListId(Long id) {
        return pickingListDao.getHandOverIdByPickingListId(id, new SingleColumnRowMapper<Long>(Long.class));
    }

    /**
     * 作业单id，是否特定
     * 
     * @param staId
     * @param beanPropertyRowMapper
     * @return
     */
    public Long findPickingListByPickIdS(Long pickId) {
        return pickingListDao.findPickingListByPickIdS(pickId, new SingleColumnRowMapper<Long>(Long.class));
    }

    public BigDecimal findOutputCount(Long plId) {
        return pickingListDao.findOutputCount(plId, new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
    }

    @Override
    public void updatePickListWhStatusBulk(List<Long> plids, Long ouId, Long userID) {
        for (int i = 0; i < plids.size(); i++) {
            PickingList pList = pickingListDao.getByPrimaryKey(plids.get(i));
            WhStaPickingListLog pickLog = new WhStaPickingListLog();
            pickLog.setStatus(pList.getWhStatus());
            BiWarehouseAddStatusCommand bi = wobManager.getBiWhST(ouId, pList.getCheckMode(), 1, pList.getWhStatus().getValue(), 0, plids.get(i));
            pList.setWhStatus(WhAddStatusMode.valueOf(bi.getStatus()));
            pList.setWhType(WhAddTypeMode.valueOf(bi.getType()));
            pickingListDao.save(pList);
            // 插入picklingLog表
            pickLog.setLogTime(new Date());
            pickLog.setPickList(pList);
            pickLog.setUserId(userID);
            pickLog.setAddStatus(WhAddStatusMode.valueOf(bi.getStatus()));
            logDao.save(pickLog);
        }

    }
    private List<String> getCityList(String str) {
        if (!StringUtils.hasText(str)) {
            return null;
        }
        List<String> list = new ArrayList<String>();
        if (str != null && StringUtils.hasText(str)) {
            for (String s : str.split(",")) {
                list.add(s);
            }
        }
        return list;
    }

}
