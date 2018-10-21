package com.jumbo.wms.manager.odo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baozun.ecp.ip.command.Response;
import com.baozun.ecp.ip.command.outboundOrders.OutboundOrders;
import com.baozun.ecp.ip.command.outboundOrders.OutboundOrders.OutboundOrder;
import com.baozun.ecp.ip.command.outboundOrders.OutboundOrders.OutboundOrder.OutboundLines.OutboundLine;
import com.baozun.ecp.ip.manager.wms3.Wms3AdapterInteractManager;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.odo.OdoDao;
import com.jumbo.dao.odo.OdoLineDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.wmsInterface.IntfcCfmDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.odo.Odo;
import com.jumbo.wms.model.odo.OdoCommand;
import com.jumbo.wms.model.odo.OdoLine;
import com.jumbo.wms.model.odo.OdoLineCommand;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.wmsInterface.cfm.IntfcCfm;

import loxia.dao.Pagination;
import loxia.dao.support.BeanPropertyRowMapperExt;

@Service("odoManager")
@Transactional
public class OdoManagerImpl extends BaseManagerImpl implements OdoManager {

    /**
     * 
     */
    private static final long serialVersionUID = -6958574854329779184L;

    @Autowired
    private OdoDao odoDao;
    @Autowired
    private OdoLineDao odoLineDao;
    @Autowired
    private OperationUnitDao ouDao;
    @Autowired
    private InventoryStatusDao isDao;
    @Autowired
    private Wms3AdapterInteractManager adapterManager;

    @Autowired
    private IntfcCfmDao intfcCfmDao;

    @Autowired
    private StockTransApplicationDao staDao;

    @Override
    public Pagination<OdoCommand> findOdoByParams(int start, int pageSize, Map<String, Object> params) {
        
        return null;
    }
  
    /**
     * 创建出库单
     * 
     * @param id
     */
    public String createOdoOutStaById(Long id) {
        String msg = "";
        Odo odo=odoDao.getByPrimaryKey(id);
        // 再次校验作业单是否重复创建
        OperationUnit ou = ouDao.getByPrimaryKey(odo.getOuId());
        List<StockTransApplication> staList = staDao.findBySlipCodeOuIdType(odo.getCode(), ou.getId(), StockTransApplicationType.VMI_RETURN);
        if (staList != null && staList.size() > 0 && 1 == odo.getStatus()) {
            for (StockTransApplication s : staList) {
                if (s.getStatus() != StockTransApplicationStatus.CANCELED) {
                    msg = "出库单创建成功！";
                    odo.setStatus(2);
                    odoDao.save(odo);
                    return msg;
                }
            }
        }
        List<OdoLineCommand> olList = odoLineDao.findOdoLineCommandByOdOId(id, 1, new BeanPropertyRowMapper<OdoLineCommand>(OdoLineCommand.class));
        OutboundOrders oos = new OutboundOrders();
        oos.setSourceMarkCode("WMS3");

        List<OutboundOrder> ooList = oos.getOutboundOrder();
        OutboundOrder oo = new OutboundOrder();
        ooList.add(oo);
        oo.setWhCode(ou.getCode());
        oo.setStoreCode(odo.getOwner());
        oo.setOrderType("101");
        oo.setOutboundNo(odo.getCode());
        oo.setPlatformCode(odo.getCode());
        oo.setDataSource("WMS3");

        OutboundOrder.Receiver rec = new OutboundOrder.Receiver();
        rec.setCountry("中国");
        rec.setProvince("无");
        rec.setCity("无");
        rec.setDistrict("无");
        rec.setAddress("无");
        rec.setReceiverMobile("021");

        oo.setReceiver(rec);

        InventoryStatus is = isDao.getByPrimaryKey(odo.getInvStatusId());
        String invStatus = tranferInvStatus(is.getName());
        OutboundOrder.OutboundLines obLines = new OutboundOrder.OutboundLines();
        oo.setOutboundLines(obLines);
        List<OutboundLine> oblineList = obLines.getOutboundLine();
        for (OdoLineCommand ol : olList) {
            OutboundLine obl = new OutboundLine();
            oblineList.add(obl);

            obl.setUpc(ol.getSkuCode());
            obl.setInvStatus(invStatus);
            obl.setStoreCode(odo.getOwner());
            obl.setQty(ol.getQty().doubleValue());
        }
        Response res = adapterManager.outboundOrderNotify(oos);
        if (res != null && res.getResult() != null && res.getResult() == 1) {
            msg = "出库单创建成功！";
            odo.setStatus(2);
            odoDao.save(odo);
        } else {
            msg = "出库单创建失败！errorCode:" + res.getErrorCode() + " errorMsg:" + res.getErrorMsg();
            // odo.setErrorCount(odo.getErrorCount() + 1);
        }
        return msg;
    }

    /**
     * 转换库存状态
     * 
     * @return
     */
    public String tranferInvStatus(String invStatus) {
        if ("良品".equals(invStatus)) {
            return "1";
        } else if ("残次品".equals(invStatus)) {
            return "2";
        } else if ("待报废".equals(invStatus)) {
            return "3";
        } else if ("待处理品".equals(invStatus)) {
            return "4";
        } else if ("临近保质期".equals(invStatus)) {
            return "5";
        } else if ("良品不可销售".equals(invStatus)) {
            return "6";
        } else if ("残次可销售".equals(invStatus)) {
            return "7";
        }
        return null;
    }

    /**
     * 根据仓库id获取同公司下的所有仓库
     * 
     * @param ouId
     * @param sorts
     * @return
     */
    public List<OperationUnit> findWarehouseOuListByOuId(Long ouId) {
        Long comOuId = ouDao.getCompanyIdByWarehouseOu(ouId, new SingleColumnRowMapper<Long>(Long.class));
        return ouDao.findWarehouseOuListByComOuId(comOuId, new BeanPropertyRowMapperExt<OperationUnit>(OperationUnit.class), null);
    }

    /**
     * 根据仓库id获取公司下的所有库存状态
     * 
     * @param ouId
     * @param sorts
     * @return
     */
    @Transactional(readOnly = true)
    public List<InventoryStatus> findInvStatusListByCompany(Long ouId) {
        Long comOuId = ouDao.getCompanyIdByWarehouseOu(ouId, new SingleColumnRowMapper<Long>(Long.class));
        return isDao.findInvStatusListByCompany(comOuId, null);
    }


    public void updateOdoStatus(String code, int status, Long icId) {
        Odo odo = odoDao.findOdOByCode(code);
        List<OdoLine> odoLineList = odoLineDao.findOdoLineByOdoId2(odo.getId(), new BeanPropertyRowMapper<OdoLine>(OdoLine.class));
        if (odoLineList != null && odoLineList.size() > 0) {
            odoDao.updateOdoStatus(code, status);
        } else {
            odoDao.updateOdoStatus(code, 10);
        }
        IntfcCfm icf = intfcCfmDao.getByPrimaryKey(icId);
        icf.setStatus(2);
        intfcCfmDao.save(icf);
    }

    public void saveOdoLine(List<OdoLine> odoLineList, OdoLine odoLine, Odo odo) {
        for (OdoLine line : odoLineList) {
            if (odo.getStatus() == 4 || odo.getStatus() == 5) {
                odoLine.setType(3);
            } else if (odo.getStatus() == 7) {
                odoLine.setType(4);
            }
            odoLine.setSkuId(line.getSkuId());
            odoLine.setQty(line.getQty());
            odoLine.setOdoId(odo.getId());
            odoLine.setVersion(0);
            odoLineDao.save(odoLine);
            odoLineDao.flush();
        }
    }

    /**
     * 修改出库反馈状态
     * 
     * @param intfcCfmId
     * @param odoId
     * @param odoLineList
     */
    public void modifyOdoOutSendStatus(Long intfcCfmId, Long odoId, List<OdoLine> odoLineList) {
        IntfcCfm ic = intfcCfmDao.getByPrimaryKey(intfcCfmId);
        ic.setStatus(2);
        intfcCfmDao.save(ic);

        Odo odo = odoDao.getByPrimaryKey(odoId);
        odo.setStatus(3);
        odoDao.save(odo);


        for (OdoLine ol : odoLineList) {
            odoLineDao.save(ol);
        }
    }

}
