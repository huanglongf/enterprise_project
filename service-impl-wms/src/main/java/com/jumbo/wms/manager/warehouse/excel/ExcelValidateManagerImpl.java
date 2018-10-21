package com.jumbo.wms.manager.warehouse.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WarehouseLocationDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExe;
import com.jumbo.wms.model.authorization.User;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.PickingListCommand;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StvLine;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.WarehouseLocation;

import loxia.support.excel.ExcelReader;
import loxia.support.excel.ExcelUtil;
import loxia.support.excel.ReadStatus;
import loxia.support.excel.definition.ExcelSheet;

@Service("excelValidateManager")
public class ExcelValidateManagerImpl extends BaseManagerImpl implements ExcelValidateManager {

    /**
     * 
     */
    private static final long serialVersionUID = -6513920315914898625L;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private WarehouseLocationDao locationDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private WareHouseManagerExe whManagerExe;
    @Resource(name = "pickingListSNReader")
    private ExcelReader pickingListSNReader;
    @Resource(name = "resultRequestInbountReader")
    private ExcelReader resultRequestInbountReader;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;

    @SuppressWarnings("unchecked")
    public ReadStatus importRefreshPickingListSN(File file, Long ouId, Long userId) throws FileNotFoundException {
        // PickingList pl = pickingListDao.getByPrimaryKey(plId);
        // if (pl == null) {
        // throw new BusinessException(ErrorCode.NOT_PICKING_LIST);
        // }
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = pickingListSNReader.readSheet(new FileInputStream(file), 0, beans);
        if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
            return rs;
        }
        List<PickingListCommand> plc = (List<PickingListCommand>) beans.get("list");

        for (PickingListCommand pc : plc) {
            // 拼接错误信息：相关单据号：
            rs.addException(new BusinessException(ErrorCode.EXCEL_PICKINGLIST_CODE_ERROR, new Object[] {pc.getSlipCode()}));
            StockTransApplication sta = staDao.findStaByCode(pc.getStaCode());
            if (sta == null) {
                rs.setStatus(-2);
                // 作业单不存在
                rs.addException(new BusinessException(ErrorCode.STA_NOT_FOUND, new Object[] {pc.getStaCode()}));
            } else {

                if (!sta.getRefSlipCode().equals(pc.getSlipCode())) {
                    rs.setStatus(-2);
                    // 相关单据号与作业单不一致
                    rs.addException(new BusinessException(ErrorCode.EXCEL_SLIPCODE_CODE_ERROR));// "相关单据号与作业单不一致;";
                }
                if (sta.getIsSn() != null && sta.getIsSn()) {
                    if (StringUtils.hasText(pc.getSns())) {
                        String s = validateSnBySta(sta.getId(), pc.getSns());
                        if (s != null) {
                            if (s.equals(REPEATE)) {
                                rs.setStatus(-2);
                                rs.addException(new BusinessException(ErrorCode.EXCEL_PICKINGLIST_SN_REPEAT));// "sn号存在重复;"));
                            } else if (s.equals(WRONG)) {
                                rs.setStatus(-2);
                                rs.addException(new BusinessException(ErrorCode.EXCEL_PICKINGLIST_SN_ERROR));
                            }
                        }
                    } else {
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_PICKINGLIST_NEEDSN_BUTNULL));
                    }
                } else {
                    if (StringUtils.hasText(pc.getSns())) {
                        rs.setStatus(-2);
                        rs.addException(new BusinessException(ErrorCode.EXCEL_PICKINGLIST_NOT_NEEDSN));
                    }
                }
            }

            if (ReadStatus.STATUS_SUCCESS == rs.getStatus()) {
                try {
                    wareHouseManager.salesCreatePage(ouId, userId, pc.getTrackingNo(), new BigDecimal(pc.getTemp()), sta == null ? null : sta.getId(), pc.getLpcode(), pc.getSns(), null);
                    rs.getExceptions().remove(rs.getExceptions().size() - 1);
                } catch (BusinessException e) {
                    rs.setStatus(-2);
                    rs.addException(e);
                } catch (Exception e) {
                    rs.setStatus(-2);
                    // 执行失败
                    rs.addException(new BusinessException(ErrorCode.PDA_EXECUTE_FAILED));
                }
            }
        }
        return rs;
    }

    /**
     * 退货入库导入
     */
    @SuppressWarnings("unchecked")
    public ReadStatus importReturnRequestInbound(File file, Long ouId, User user) throws FileNotFoundException {
        Map<String, Object> beans = new HashMap<String, Object>();
        ReadStatus rs = resultRequestInbountReader.readSheet(new FileInputStream(file), 0, beans);
        if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
            return rs;
        }
        // 导入的数据
        List<StockTransApplicationCommand> staList = (List<StockTransApplicationCommand>) beans.get("staList");
        // 保存对应每个sta的明细信息
        Map<String, Map<String, List<StaLine>>> staLineMap = new HashMap<String, Map<String, List<StaLine>>>();

        // 保存导入正确的数据
        Map<String, List<StvLine>> staResultMap = new HashMap<String, List<StvLine>>();

        // 保存上架数量
        Map<Long, Long> inboundQty = new HashMap<Long, Long>();

        // 获取默认库位
        WarehouseLocation location = locationDao.findOneWarehouseLocationByOuid(ouId);

        //
        Map<String, StockTransApplication> staMap = new HashMap<String, StockTransApplication>();

        final ExcelSheet sheets = resultRequestInbountReader.getDefinition().getExcelSheets().get(0);
        String strCell = ExcelUtil.getCellIndex(sheets.getExcelBlocks().get(0).getStartRow(), sheets.getExcelBlocks().get(0).getStartCol());
        String currCell = null;
        int offsetRow = 0;
        for (StockTransApplicationCommand com : staList) {
            Map<String, List<StaLine>> map = staLineMap.get(com.getCode());
            StockTransApplication sta = staMap.get(com.getCode());
            if (com.getInboundInfo() == null || !com.getInboundInfo().equals(Constants.INBOUND_INFO)) {
                currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                rs.addException(new BusinessException(ErrorCode.RETURN_REQUEST_INPUT_INBOUND_INFO_ERROR, new Object[] {SHEET_0, currCell, com.getCode(), com.getBarCode(), com.getInvStrutsName()}));
                rs.setStatus(-2);
                continue;
            }
            if (map == null) {
                sta = staDao.findStaByCode(com.getCode());
                if (sta == null) {
                    rs.addException(new BusinessException(ErrorCode.STA_NOT_FOUND, new Object[] {com.getCode()}));
                    rs.setStatus(-2);
                    continue;
                }
                if (!sta.getMainWarehouse().getId().equals(ouId)) {
                    currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 1);
                    rs.addException(new BusinessException(ErrorCode.RETURN_REQUEST_INPUT_STA_NOT_WH, new Object[] {SHEET_0, currCell, com.getCode()}));
                    rs.setStatus(-2);
                    continue;
                }
                if (!sta.getStatus().equals(StockTransApplicationStatus.CREATED)) {
                    rs.addException(new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {com.getCode()}));
                    rs.setStatus(-2);
                    continue;
                }
                staMap.put(com.getCode(), sta);
                staMap.put(com.getCode() + "_COM", com);
                staResultMap.put(com.getCode(), new ArrayList<StvLine>());
                List<StaLine> lineList = staLineDao.findByStaId(sta.getId());
                map = new HashMap<String, List<StaLine>>();
                // 保存作业单明细数据
                for (StaLine l : lineList) {
                    Sku sku = skuDao.getByPrimaryKey(l.getSku().getId());
                    InventoryStatus is = inventoryStatusDao.getByPrimaryKey(l.getInvStatus().getId());
                    String key = sku.getBarCode() + is.getName();
                    if (map.containsKey(key)) {
                        map.get(key).add(l);
                    } else {
                        List<StaLine> ls = new ArrayList<StaLine>();
                        ls.add(l);
                        map.put(key, ls);
                    }
                }
                staLineMap.put(sta.getCode(), map);
            }
            String key = com.getBarCode() + com.getInvStrutsName();
            if (map.containsKey(key)) {
                List<StaLine> staLineList = map.get(key);
                List<StvLine> stvLineList = staResultMap.get(sta.getCode());
                Long quantity = com.getQuantity();
                for (int i = 0; i < staLineList.size(); i++) {
                    StaLine l = staLineList.get(i);
                    long qty = inboundQty.get(l.getId()) == null ? l.getQuantity() : inboundQty.get(l.getId());
                    Sku sku = skuDao.getByPrimaryKey(l.getSku().getId());
                    Boolean isSN = sku.getIsSnSku();
                    if (qty > 0) {
                        StvLine line = new StvLine();
                        line.setInBoundTime(com.getInboundTime());
                        line.setDirection(TransactionDirection.INBOUND);
                        line.setInvStatus(l.getInvStatus());
                        line.setSku(l.getSku());
                        line.setLocation(location);
                        line.setDistrict(location.getDistrict());
                        line.setOwner(l.getOwner() == null ? sta.getOwner() : l.getOwner());
                        line.setStaLine(l);

                        Long tempQty = qty > quantity ? quantity : qty;
                        if (isSN != null && isSN) {
                            tempQty = 1L;
                            line.setSns(com.getSn());
                        }
                        line.setQuantity(tempQty);
                        stvLineList.add(line);
                        inboundQty.put(l.getId(), qty - line.getQuantity());
                        quantity -= line.getQuantity();
                        if (quantity.equals(0L)) {
                            break;
                        }
                    }
                }
                if (!quantity.equals(0L)) {
                    currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                    rs.addException(new BusinessException(ErrorCode.RETURN_REQUEST_INPUT_SKU_QTY_ERROR, new Object[] {SHEET_0, currCell, com.getCode(), com.getBarCode(), com.getInvStrutsName()}));
                    rs.setStatus(-2);
                }
            } else {
                currCell = ExcelUtil.offsetCellIndex(strCell, offsetRow, 0);
                rs.addException(new BusinessException(ErrorCode.RETURN_REQUEST_INPUT_SKU_ERROR, new Object[] {SHEET_0, currCell, com.getCode(), com.getBarCode(), com.getInvStrutsName()}));
                rs.setStatus(-2);
            }
        }
        if (ReadStatus.STATUS_SUCCESS != rs.getStatus()) {
            return rs;
        }
        for (String key : staResultMap.keySet()) {
            try {
                StockTransApplicationCommand com = (StockTransApplicationCommand) staMap.get(key + "_COM");
                whManagerExe.inboundReturn(staMap.get(key).getId(), key, staResultMap.get(key), user, com.getInboundTime(), com.getMemo());
            } catch (BusinessException e) {
                log.debug("", e);
                rs.addException(e);
                rs.setStatus(-2);
            } catch (Exception e) {
                log.debug("", e);
                rs.addException(new BusinessException(ErrorCode.RETURN_REQUEST_EXE_ERROR, new Object[] {key}));
                rs.setStatus(-2);
            }
        }
        return rs;
    }

    private String validateSnBySta(Long id, String sns) {
        Map<String, String> snMap1 = new HashMap<String, String>();
        String[] snsList = sns.split(",");
        for (int i = 0; i < snsList.length; i++) {
            snMap1.put(snsList[i], snsList[i]);
        }
        if (snsList.length != snMap1.size()) {
            return REPEATE;
        } else {
            List<String> snList = Arrays.asList(snsList);
            List<Long> lineIdList = staLineDao.findErrorSnInfo(id, snList, new SingleColumnRowMapper<Long>(Long.class));
            if (lineIdList != null && lineIdList.size() > 0) {
                return WRONG;
            } else {
                return RIGHT;
            }
        }
    }

}
