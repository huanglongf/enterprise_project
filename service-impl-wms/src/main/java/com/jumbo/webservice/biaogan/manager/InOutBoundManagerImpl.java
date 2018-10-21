package com.jumbo.webservice.biaogan.manager;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgInventoryStatusDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderCancelDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgTypeDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WarehouseMsgSkuDao;
import com.jumbo.mq.MarshallerUtil;
import com.jumbo.util.FormatUtil;
import com.jumbo.webservice.biaogan.clientNew2.PushExpressInfoPortTypeClientNew2;
import com.jumbo.webservice.biaogan.command.Asns;
import com.jumbo.webservice.biaogan.command.AsnsDetails;
import com.jumbo.webservice.biaogan.command.Detail;
import com.jumbo.webservice.biaogan.command.InBoundCommand;
import com.jumbo.webservice.biaogan.command.InBoundLineCommand;
import com.jumbo.webservice.biaogan.command.InBoundLineList;
import com.jumbo.webservice.biaogan.command.InOutBoundResponse;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.manager.baseinfo.BaseinfoManager;
import com.jumbo.wms.manager.vmi.warehouse.VmiWarehouseFactory;
import com.jumbo.wms.manager.vmi.warehouse.VmiWarehouseInterface;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrderLineCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgInventoryStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderCancelStatus;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrderLine;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;

@Transactional
@Service("inOutBoundManager")
public class InOutBoundManagerImpl extends BaseManagerImpl implements InOutBoundManager {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4710073952439661195L;

    @Autowired
    private MsgInboundOrderLineDao msgInboundOrderLineDao;

    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInDao;
    @Autowired
    private MsgInventoryStatusDao msgInventoryStautsDao;
    @Autowired
    private MsgRtnInboundOrderLineDao msgLineDao;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private MsgTypeDao msgTypeDao;
    @Autowired
    private MsgInboundOrderDao msgInboundOrderDao;
    @Autowired
    private MsgOutboundOrderCancelDao msgOutboundOrderCancelDao;
    @Autowired
    private BaseinfoManager baseManger;
    @Autowired
    private VmiWarehouseFactory vmiWarehouseFactory;
    @Autowired
    private WarehouseMsgSkuDao warehouseMsgSkuDao;

    /**
     * 调用仓库webservice，发送入库通知单至仓库。
     */
    public InOutBoundResponse ansToWms(MsgInboundOrder msg) {
        String vimType = "";
        InOutBoundResponse resp = new InOutBoundResponse();
        if (msg != null) {
            if (StockTransApplicationType.INBOUND_PURCHASE.equals(msg.getType()) || StockTransApplicationType.INBOUND_SETTLEMENT.equals(msg.getType()) || StockTransApplicationType.INBOUND_OTHERS.equals(msg.getType())
                    || StockTransApplicationType.INBOUND_GIFT.equals(msg.getType()) || StockTransApplicationType.INBOUND_MOBILE.equals(msg.getType()) || StockTransApplicationType.TRANSIT_CROSS.equals(msg.getType())

                    || StockTransApplicationType.SAMPLE_INBOUND.equals(msg.getType()) || StockTransApplicationType.SKU_EXCHANGE_INBOUND.equals(msg.getType()) || StockTransApplicationType.REAPAIR_INBOUND.equals(msg.getType())
                    || StockTransApplicationType.SERIAL_NUMBER_INBOUND.equals(msg.getType()) || StockTransApplicationType.SERIAL_NUMBER_GROUP_INBOUND.equals(msg.getType()) || StockTransApplicationType.SAME_COMPANY_TRANSFER.equals(msg.getType())
                    || StockTransApplicationType.DIFF_COMPANY_TRANSFER.equals(msg.getType())

                    || StockTransApplicationType.INBOUND_CONSIGNMENT.equals(msg.getType())) {
                vimType = msgTypeDao.findTypeBySourceandType("BiaoGan", 11, new SingleColumnRowMapper<String>(String.class));
            } else if (StockTransApplicationType.INBOUND_RETURN_REQUEST.equals(msg.getType())) {
                vimType = msgTypeDao.findTypeBySourceandType("BiaoGan", msg.getType().getValue(), new SingleColumnRowMapper<String>(String.class));
            }
            InBoundCommand in = new InBoundCommand();
            in.setWarehouseid("BML_KSWH");
            in.setType(vimType);
            in.setOrderCode(msg.getStaCode());
            in.setCustomerId("BZ_ALL");
            in.setZDRQ(FormatUtil.formatDate(msg.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            in.setDHRQ(FormatUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
            in.setZDR("zdr_001");
            in.setBZ(msg.getRemark() == null ? "" : msg.getRemark());

            Long msgId = msg.getId();
            List<MsgInboundOrderLineCommand> msgLineList = msgInboundOrderLineDao.findVmiMsgInboundOrderLine(msgId, new BeanPropertyRowMapper<MsgInboundOrderLineCommand>(MsgInboundOrderLineCommand.class));

            List<InBoundLineCommand> inboundList = new ArrayList<InBoundLineCommand>();
            for (MsgInboundOrderLineCommand msgl : msgLineList) {
                String vimstatus = msgInventoryStautsDao.findInventoryStatusByBzStatus(msgl.getInvStatusId(), "BiaoGan", new SingleColumnRowMapper<String>(String.class));
                InBoundLineCommand inLine = new InBoundLineCommand();
                inLine.setItemCount(msgl.getQty());
                inLine.setItemName(msgl.getSkuName().replaceAll("'", " "));
                inLine.setItemValue(new BigDecimal(0));
                inLine.setRemark(vimstatus == null ? "" : vimstatus);
                inLine.setSpuCode(msgl.getSpuCode() == null ? "" : msgl.getSpuCode());
                inboundList.add(inLine);
            }
            InBoundLineList lineList = new InBoundLineList();
            lineList.setInboundList(inboundList);
            in.setList(lineList);

            String xml = MarshallerUtil.buildJaxb(in);
            // System.out.println("----发送中。。。。。。。。。。。");
            // 发送入库通知单到仓库
            resp = PushExpressInfoPortTypeClientNew2.ansToWms(xml);
            StringBuilder sb = new StringBuilder();
            sb.append(resp.getSuccess()).append("|").append(resp.getCode()).append("|").append(resp.getDesc());
            log.info("====>BiaoGan inbound order notify result is [{}], the  staCode is [{}]", sb.toString(), msg.getStaCode());
            if ("true".equals(resp.getSuccess())) {
                msgInboundOrderDao.updateMsgInboundStatus(msg.getId(), DefaultStatus.FINISHED.getValue());
            } else if ("false".equals(resp.getSuccess())) {
                // System.out.println(resp.getDesc());
                log.debug("inBoundToBiaoGan:" + msg.getStaCode() + ";error:" + resp.getDesc());
                msgInboundOrderDao.updateMsgInboundStatus(msg.getId(), DefaultStatus.CANCELED.getValue());
            }
        }
        return resp;
    }

    /**
     * 更具作业单对应的外包仓获取库存状态
     * 
     * @param staCode
     * @return 返回Map集合
     */
    public Map<String, InventoryStatus> findMsgInvStatusByStaCode(String staCode) {
        List<MsgInventoryStatus> list = msgInventoryStautsDao.findMsgInvStatusByStaCode(staCode, new BeanPropertyRowMapperExt<MsgInventoryStatus>(MsgInventoryStatus.class));
        Map<String, InventoryStatus> result = new HashMap<String, InventoryStatus>();
        for (MsgInventoryStatus msg : list) {
            result.put(msg.getVmiStatus(), inventoryStatusDao.getByPrimaryKey(msg.getWhStatus()));
        }
        return result;
    }

    public List<MsgRtnInboundOrder> createMsgRtnInboundLine(String xml) throws Exception {

        List<MsgRtnInboundOrder> rtnList = new ArrayList<MsgRtnInboundOrder>();
        AsnsDetails rtnInbound = (AsnsDetails) MarshallerUtil.buildJaxb(AsnsDetails.class, xml);

        for (Object asns : rtnInbound.getAsns()) {

            MsgRtnInboundOrder inorder = new MsgRtnInboundOrder();
            Asns obj = (Asns) asns;
            if (obj.getCustmorOrderNo() == null || obj.getCustmorOrderNo().equals("")) {
                continue;
            }

            inorder.setSource("BiaoGan");
            inorder.setSourceWh("BiaoGan");
            inorder.setStaCode(obj.getCustmorOrderNo());
            inorder.setCreateTime(new Date());
            Map<String, InventoryStatus> invoiceNumMap = findMsgInvStatusByStaCode(inorder.getStaCode());
            inorder.setType(0);
            inorder.setStatus(DefaultStatus.CREATED);
            inorder = msgRtnInDao.save(inorder);
            rtnList.add(inorder);
            for (Object detail : obj.getObj()) {
                Detail d = (Detail) detail;
                MsgRtnInboundOrderLine line = new MsgRtnInboundOrderLine();
                line.setSkuCode(d.getSkuCode());
                line.setQty(d.getReceivedQty());
                line.setOutStatus(d.getLotatt06());
                if (d.getLotatt06().equals("")) {
                    line.setInvStatus(invoiceNumMap.get("HG"));
                } else {
                    InventoryStatus status = invoiceNumMap.get(d.getLotatt06());
                    if (status == null) {
                        line.setInvStatus(invoiceNumMap.get("HG"));
                    } else {
                        line.setInvStatus(status);
                    }
                }

                line.setMsgRtnInOrder(inorder);
                msgLineDao.save(line);
            }

        }
        return rtnList;
    }

    /**
     * 更新SKU_ID
     * 
     * @param rtnorderLine
     */
    public void updateInOrderSkuId(Long rtnOrderId) throws Exception {
        List<MsgRtnInboundOrderLine> rntLine = msgLineDao.findRtnOrderLineByRId(rtnOrderId);
        for (MsgRtnInboundOrderLine line : rntLine) {
            Sku sku = skuDao.getByCode(line.getSkuCode());
            if (sku != null) {
                line.setSkuId(sku.getId());
            }
        }
    }

    /**
     * 查出库通知中间表
     */
    public List<Long> findMsgOutboundOrderIds(String source) {
        return msgOutboundOrderDao.findMsgOutboundOrderId(source, new SingleColumnRowMapper<Long>(Long.class));
    }

    public boolean vmiisCancelboundOrder(String soCode) {
        boolean isCancel = true;
        List<StockTransApplication> stas = staDao.findBySlipCodeByType(soCode, StockTransApplicationType.OUTBOUND_SALES);
        for (StockTransApplication sta : stas) {
            Warehouse wh = baseManger.findWarehouseByOuId(sta.getMainWarehouse().getId());
            if (wh != null && StringUtils.hasText(wh.getVmiSource())) {
                MsgOutboundOrderCancel mc = msgOutboundOrderCancelDao.getByStaCode(sta.getCode());
                // 判断重复，存在则不再记录
                if (mc == null) {
                    mc = new MsgOutboundOrderCancel();
                    Long id = warehouseMsgSkuDao.getThreePlSeq(new SingleColumnRowMapper<Long>(Long.class));
                    mc.setUuid(id);
                    mc.setStaCode(sta.getCode());
                    mc.setStaId(sta.getId());
                    mc.setCreateTime(new Date());
                    mc.setSource(wh.getVmiSource());
                    mc.setStatus(MsgOutboundOrderCancelStatus.CREATED);
                    mc.setCreateTime(new Date());
                    msgOutboundOrderCancelDao.save(mc);
                }
                VmiWarehouseInterface vw = vmiWarehouseFactory.getVmiWarehouse(wh.getVmiSource());
                isCancel = vw.cancelSalesOutboundSta(sta.getCode(), mc);
            }
        }
        return isCancel;
    }
}
