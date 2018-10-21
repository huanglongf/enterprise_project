package com.jumbo.wms.manager.vmi.itochuData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jcraft.jsch.ChannelSftp;
import com.jumbo.dao.authorization.OperationUnitDao;
import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.mail.MailLogDao;
import com.jumbo.dao.mail.MailTemplateDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.vmi.itochuData.ItochuRtnInvDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgOutboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnInboundOrderLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnOutboundLineDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnReturnDao;
import com.jumbo.dao.vmi.warehouse.MsgRtnReturnLineDao;
import com.jumbo.dao.vmi.warehouse.MsgTypeDao;
import com.jumbo.dao.warehouse.BiChannelDao;
import com.jumbo.dao.warehouse.InventoryStatusDao;
import com.jumbo.dao.warehouse.MsgRaCancelDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.StockTransTxLogDao;
import com.jumbo.dao.warehouse.StockTransVoucherDao;
import com.jumbo.dao.warehouse.StvLineDao;
import com.jumbo.dao.warehouse.TransactionTypeDao;
import com.jumbo.dao.warehouse.WhUaInventoryLogDao;
import com.jumbo.dao.warehouse.WmsOtherOutBoundInvNoticeOmsDao;
import com.jumbo.util.FormatUtil;
import com.jumbo.util.MailUtil;
import com.jumbo.util.SFTPUtil;
import com.jumbo.util.StringUtil;
import com.jumbo.util.UnicodeReader;
import com.jumbo.wms.Constants;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.hub2wms.HubWmsService;
import com.jumbo.wms.manager.system.SequenceManager;
import com.jumbo.wms.manager.task.CommonConfigManager;
import com.jumbo.wms.manager.warehouse.WareHouseManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerProxy;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.WmsOtherOutBoundInvNoticeOmsStatus;
import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.baseinfo.BiChannel;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.mail.MailLog;
import com.jumbo.wms.model.mail.MailTemplate;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.vmi.warehouse.ItochuRtnInv;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgInboundOrderLineCommand;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgOutboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRaCancel;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrder;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnInboundOrderLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutbound;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnOutboundLine;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnReturn;
import com.jumbo.wms.model.vmi.warehouse.MsgRtnReturnLine;
import com.jumbo.wms.model.warehouse.InventoryStatus;
import com.jumbo.wms.model.warehouse.StaDeliveryInfoCommand;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;
import com.jumbo.wms.model.warehouse.StockTransVoucherStatus;
import com.jumbo.wms.model.warehouse.TransactionDirection;
import com.jumbo.wms.model.warehouse.TransactionType;
import com.jumbo.wms.model.warehouse.WmsOtherOutBoundInvNoticeOms;

import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.utils.DateUtil;

/**
 * UA
 * 
 * @author Administrator
 * 
 */

@Transactional
@Service("itochuUAManager")
public class ItochuUAManagerImpl implements ItochuUAManager {

    private static final long serialVersionUID = -6328901177856263028L;
    @Autowired
    private MsgOutboundOrderDao msgOutboundOrderDao;
    @Autowired
    private MsgOutboundOrderLineDao msgOutboundOrderLineDao;
    @Autowired
    private MsgRtnOutboundDao msgRtnOutboundDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private MsgRtnOutboundLineDao msgRtnOutboundLineDao;
    @Autowired
    private MsgInboundOrderDao msgInboundOrderDao;
    @Autowired
    private MsgInboundOrderLineDao msgInboundOrderLineDao;
    @Autowired
    private MsgRtnInboundOrderLineDao msgRtnInboundOrderLineDao;
    @Autowired
    private MsgRtnInboundOrderDao msgRtnInboundOrderDao;
    @Autowired
    private BiChannelDao biChannelDao;
    @Autowired
    private WareHouseManager whManager;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockTransVoucherDao stvDao;
    @Autowired
    private TransactionTypeDao transactionTypeDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private StvLineDao stvLineDao;
    @Autowired
    private SequenceManager sequenceManager;
    @Autowired
    private InventoryStatusDao inventoryStatusDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private OperationUnitDao operationUnitDao;
    @Autowired
    private WareHouseManager wareHouseManager;
    @Autowired
    private MsgRtnReturnDao msgRtnReturnDao;
    @Autowired
    private MsgTypeDao msgTypeDao;
    @Autowired
    private WareHouseManagerProxy wareHouseManagerProxy;
    @Autowired
    private MsgRtnReturnLineDao msgRtnReturnLineDao;
    @Autowired
    private MsgRaCancelDao msgRaCancelDao;
    @Autowired
    private ItochuRtnInvDao itochuRtnInvDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    @Autowired
    private MailTemplateDao mailTemplateDao;
    @Autowired
    private MailLogDao mailLogDao;
    @Autowired
    private CommonConfigManager configManager;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private StockTransTxLogDao stLogDao;
    @Autowired
    private WmsOtherOutBoundInvNoticeOmsDao wmsOtherOutBoundInvNoticeOmsDao;
    @Autowired
    private WhUaInventoryLogDao whUaInventoryLogDao;
    @Autowired
    private HubWmsService hubWmsService;
    /**
	 * 
	 */
    protected static final Logger log = LoggerFactory.getLogger(ItochuUAManagerImpl.class);
    /**
     * 行 起始前缀
     */

    private String delimiter = "";

    // private static String ITOCHU_FILE_POSTFIX = ".shupl";
    private String outBoundRtn = "outBoundRtn";
    private String inBoundRtn = "inBoundRtn";
    private String inBoundRtn2 = "inBoundRtn2";
    private String returnRtn = "returnRtn";
    private String checkInventory = "checkInventory";

    /**
     * UA 出库通知
     */
    @Transactional
    public void uaOutBoundNotify(String localFileDir) {
        log.info("-------------UA outBoundNotify---------------start-------");
        // 1.读中间表
        List<MsgOutboundOrder> orders = msgOutboundOrderDao.findMsgOutboundOrder(Constants.VIM_WH_SOURCE_ITOCHU_UA, null, DefaultStatus.CREATED.getValue(), true, new BeanPropertyRowMapper<MsgOutboundOrder>(MsgOutboundOrder.class));
        String fileName = "Deliver_" + FormatUtil.formatDate(new Date(), "yyyyMMddHHmmss") + ".sh";
        StringBuilder sbl = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        // 2.写到本地文件
        for (MsgOutboundOrder order : orders) {
            List<MsgOutboundOrderLine> lines = msgOutboundOrderLineDao.findeMsgOutLintBymsgOrderId2(order.getId());
            for (MsgOutboundOrderLine line : lines) {
                sbl.append(connectOutBoundLineData(order, line));
            }
            String data = connectOutBoundData(order);
            if (data == null || data.trim().length() == 0) {
                log.debug("data is null.......");
                continue;
            }
            sb.append(data);
        }
        try {
            writeDataFile(sb.toString(), localFileDir, fileName);
            writeDataFile(sbl.toString(), localFileDir, fileName);
        } catch (Exception e) {
            File f = new File(fileName);
            f.delete();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        // 上传出库通知文件到ftp
        uploadFtpUtil();
        // update 状态
        for (MsgOutboundOrder o : orders) {
            msgOutboundOrderDao.updateStatusById(DefaultStatus.FINISHED.getValue(), o.getId());
        }

        log.info("-------------UA outBoundNotify---------------end-------");
    }

    /**
     * UA退货入库通知
     */
    @Transactional
    public void uaRtnInBoundNotify(String localFileDir) {
        log.info("-------------UA uaRtnInBoundNotify-----------------start-------");
        // 读中间表
        // List<MsgInboundOrder> ins =
        // msgInboundOrderDao.findItochuMsgInboundByStatus(Constants.VIM_WH_SOURCE_ITOCHU_UA,
        // new
        // BeanPropertyRowMapperExt<MsgInboundOrder>(MsgInboundOrder.class));
        List<MsgInboundOrder> ins = msgInboundOrderDao.findMsgReturnInboundByStatus(Constants.VIM_WH_SOURCE_ITOCHU_UA, StockTransApplicationType.INBOUND_RETURN_REQUEST);
        // 写数据到本地文件
        StringBuilder sbl = new StringBuilder();
        String fileName = "Deliver_" + FormatUtil.formatDate(new Date(), "yyyyMMddHHmmss") + ".rc";
        StringBuilder sb = new StringBuilder();
        for (MsgInboundOrder in : ins) {
            StockTransApplication sta = staDao.findStaByCode(in.getStaCode());
            sb.append(Constants.ILC_RCHDRRTNEW);
            sb.append(delimiter);
            sb.append(Constants.VIM_WH_SOURCE_ITOCHU);
            sb.append(delimiter);
            sb.append(in.getStaCode());
            sb.append(delimiter);
            sb.append(in.getStaCode());
            sb.append(delimiter);
            sb.append(Constants.ILC_UA_EC);
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append(in.getTotalActual() == null ? 0 : in.getTotalActual());//
            sb.append(delimiter);
            sb.append("");//
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append(in.getTelephone() == null ? "" : in.getTelephone());// phone
            sb.append(delimiter);
            sb.append(in.getMobile() == null ? "" : in.getMobile());// mobile
            sb.append(delimiter);
            sb.append("");
            sb.append(delimiter);
            sb.append(sta.getRefSlipCode());
            // String taobaoCode =
            // msgInboundOrderDao.findOutOrderCodeBySlipCode(sta.getRefSlipCode(), new
            // SingleColumnRowMapper<String>(String.class));
            // sb.append(taobaoCode == null ? "" : taobaoCode);
            sb.append(delimiter);

            String type0 = msgTypeDao.findTypeBySourceandType(Constants.VIM_WH_SOURCE_ITOCHU_UA, sta.getType().getValue(), new SingleColumnRowMapper<String>(String.class));
            if (type0 == null) {
                type0 = "";
            }
            sb.append(type0);

            sb.append(delimiter);
            // String salesOutOrder =
            // msgInboundOrderDao.findSalesOutOrderBySlipCode(sta.getRefSlipCode(), new
            // SingleColumnRowMapper<String>(String.class));
            // 查询sta.code
            sb.append(sta.getSlipCode2() == null ? sta.getSlipCode1() : sta.getSlipCode2());
            sb.append("\r\n");
            if (sb.toString() != null && sb.toString().trim().length() != 0) {
                List<MsgInboundOrderLineCommand> lines = msgInboundOrderLineDao.findVmiMsgInboundOrderLine(in.getId(), new BeanPropertyRowMapper<MsgInboundOrderLineCommand>(MsgInboundOrderLineCommand.class));
                for (MsgInboundOrderLineCommand line : lines) {
                    sbl.append(Constants.ILC_RCDTLDNNEW);
                    sbl.append(delimiter);
                    sbl.append(in.getStaCode());
                    sbl.append(delimiter);
                    sbl.append(line.getId());
                    sbl.append(delimiter);
                    sbl.append(Constants.ILC_UA_EC);
                    sbl.append(delimiter);
                    sbl.append(line.getBarCode());
                    sbl.append(delimiter);
                    sbl.append(0);
                    sbl.append(delimiter);
                    sbl.append(line.getQty());
                    sbl.append(delimiter);
                    sbl.append("");
                    sbl.append(delimiter);
                    sbl.append("");
                    sbl.append(delimiter);
                    sbl.append(line.getInvStsName() == null ? "" : line.getInvStsName());// 库存状态
                    sbl.append(delimiter);
                    sbl.append(line.getId());// 流水号
                    sbl.append(delimiter);
                    sbl.append(type0);
                    sbl.append(delimiter);
                    sbl.append(line.getId());
                    sbl.append("\r\n");
                }
            }
        }
        try {
            writeDataFile(sb.toString(), localFileDir, fileName);
            writeDataFile(sbl.toString(), localFileDir, fileName);
        } catch (Exception e) {
            File f = new File(fileName);
            f.delete();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }

        uploadFtpUtil();

        for (MsgInboundOrder in : ins) {
            msgInboundOrderDao.updateMsgInboundStatus(in.getId(), DefaultStatus.FINISHED.getValue());
        }
        log.info("-------------UA uaRtnInBoundNotify-----------------end-------");
    }

    /**
     * 退货入库取消通知
     */
    @Transactional
    public void rtnCancelNotify(String localFileDir) {
        log.debug("-------------Etam rtnCancelNotify-----------------start-------");
        // 读中间表
        List<MsgRaCancel> list = msgRaCancelDao.findNewMsgBySource(Constants.VIM_WH_SOURCE_ITOCHU_UA, new BeanPropertyRowMapperExt<MsgRaCancel>(MsgRaCancel.class));
        // 写数据到本地文件
        StringBuilder sbl = new StringBuilder();
        String fileName = "RcDel_" + FormatUtil.formatDate(new Date(), "yyyyMMddHHmmss") + ".rc";
        StringBuilder sb = new StringBuilder();
        for (MsgRaCancel msg : list) {
            sb.append("REDEL");
            sb.append(delimiter);
            sb.append(msg.getStaCode());
            sb.append(delimiter);
            sb.append(msg.getStaCode());
            sb.append("");
            sb.append("\r\n");
        }
        try {
            writeDataFile(sb.toString(), localFileDir, fileName);
            writeDataFile(sbl.toString(), localFileDir, fileName);
        } catch (Exception e) {
            File f = new File(fileName);
            f.delete();
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        uploadFtpUtil();
        // update status
        for (MsgRaCancel msg : list) {
            msgRaCancelDao.updateStatusById(msg.getId(), DefaultStatus.FINISHED.getValue());
        }
        log.debug("-------------Etam rtnCancelNotify-----------------end-------");
    }

    /**
     * 读取UA出库反馈数据
     */
    public void readUAOutBoundRtnData(String localFileDir, String bakFileDir) {
        readFileIntoDBInOutBoundRtn(localFileDir, bakFileDir, outBoundRtn, Constants.UA_OUTFILE_PREFIX, Constants.UA_OUTFILE_SUFFIX);
    }

    /**
     * 读UA退货入库反馈数据
     */
    @Override
    public void readUARtnInBoundData(String localFileDir, String bakFileDir) {
        readFileIntoDBInOutBoundRtn(localFileDir, bakFileDir, inBoundRtn2, Constants.UA_INRTNFILE_PREFIX, Constants.UA_INRTNFILE_SUFFIX);
    }

    /**
     * 读UA入库反馈数据
     */
    @Override
    public void readUAInBoundRtn(String localFileDir, String bakFileDir) {
        readFileIntoDBInOutBoundRtn(localFileDir, bakFileDir, inBoundRtn, Constants.UA_INFILE_PREFIX, Constants.UA_INTFILE_SUFFIX);
    }

    /**
     * UA 读退仓数据
     */
    @Override
    public void readUAReturnData(String localFileDir, String bakFileDir) {
        readFileIntoDBInOutBoundRtn(localFileDir, bakFileDir, returnRtn, Constants.UA_RETURN_PREFIX, Constants.UA_RETURN_SUFFIX);
    }

    /**
     * save 盘点数据
     */
    @Transactional
    public void saveCheckInventory(String localFileDir, String bakFileDir) {
        // save date
        readFileIntoDBInOutBoundRtn(localFileDir, bakFileDir, checkInventory, "RecCheck", "txt");
    }

    /**
     * UA 退仓执行
     */
    @Override
    public void uaReturnExecute(MsgRtnReturn rtn) {
        String vmiSource = Constants.VIM_WH_SOURCE_ITOCHU_UA;
        Warehouse warehouse = warehouseDao.findWarehouseByVmiSource(vmiSource);
        if (warehouse == null) throw new BusinessException(ErrorCode.WAREHOUSE_NOT_FOUND);
        OperationUnit operationUnit = operationUnitDao.getByPrimaryKey(warehouse.getOu().getId());
        if (operationUnit == null) {
            throw new BusinessException(ErrorCode.WAREHOUSE_OU_NOT_FOUND);
        }
        BigDecimal transactionid = transactionTypeDao.findByStaType(StockTransApplicationType.VMI_RETURN.getValue(), new SingleColumnRowMapper<BigDecimal>(BigDecimal.class));
        if (transactionid == null) {
            throw new BusinessException(ErrorCode.TRANSTACTION_TYPE_NOT_FOUND, new Object[] {""});
        }
        TransactionType transactionType = transactionTypeDao.getByPrimaryKey(transactionid.longValue());
        if (transactionType == null) {
            throw new BusinessException(ErrorCode.TRANSACTION_TYPE_NOT_FOUND);
        }
        // 创sta
        createStaForUAReturn(rtn.getRefSlipCode(), vmiSource, rtn.getId(), transactionType, operationUnit);
    }

    private void createStaForUAReturn(String slipCode, String vmiSource, Long returnId, TransactionType transType, OperationUnit ou) {
        StockTransApplication sta = new StockTransApplication();
        sta.setType(StockTransApplicationType.VMI_RETURN);
        sta.setMainWarehouse(ou);
        sta.setRefSlipCode(slipCode);
        sta.setCreateTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setIsNeedOccupied(true);
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCode(sequenceManager.getCode(StockTransApplication.class.getName(), sta));
        sta.setIsNotPacsomsOrder(true);
        staDao.save(sta);
        staDao.flush();

        /***** 调整逻辑：前置退仓增量 ************************************/
        incrementInv(sta.getId());
        List<MsgRtnReturnLine> lines = msgRtnReturnLineDao.findReturnLinesByReturnId(returnId);

        log.info("returnId===" + returnId);

        for (MsgRtnReturnLine line : lines) {
            StaLine staLine = new StaLine();
            staLine.setQuantity(line.getQty());
            staLine.setCompleteQuantity(0L);
            staLine.setSta(sta);
            staLine.setInvStatus(line.getInvStatus());
            Sku sku = skuDao.getByPrimaryKey(line.getSkuId());
            if (sku == null) {
                // log.debug("===========================sku is  null sku :{} ===============================",
                // sku.getBarCode());
                throw new BusinessException(ErrorCode.SKU_NOT_FOUND);
            }
            staLine.setSku(new Sku(line.getSkuId()));
            staLineDao.save(staLine);
        }
        staLineDao.flush();
        // 占用库存
        StockTransVoucher stv = occupyInventoryByStaId(sta.getId(), transType, ou);
        // 新增其他出库占用明细记录中间表通知oms/pac,定时任务通知
        whManager.createWmsOtherOutBoundInvNoticeOms(sta.getId(), 2l, WmsOtherOutBoundInvNoticeOmsStatus.OTHER_OUTBOUND);

        // 删除库存出库
        wareHouseManager.removeInventory(sta, stv);
        sta.setStatus(StockTransApplicationStatus.FINISHED);
        // 其他出库更新中间表，传递明细给oms/pac
        WmsOtherOutBoundInvNoticeOms wtoms = wmsOtherOutBoundInvNoticeOmsDao.findOtherOutInvNoticeOmsByStaCode(sta.getCode());
        if (wtoms != null) {
            wmsOtherOutBoundInvNoticeOmsDao.updateOtherOutBoundInvNoticeOmsByStaCode(sta.getCode(), 10l);
        }
        stv.setStatus(StockTransVoucherStatus.FINISHED);
        staLineDao.flush();
        // 更新中间表状态
        msgRtnReturnDao.updateFinishBySlipCode(sta.getCode(), returnId);
    }

    private void incrementInv(Long id) {
        stLogDao.insertIncrementInv(id);
    }

    // vmi UA退仓- 占用库存
    private StockTransVoucher occupyInventoryByStaId(Long staId, TransactionType transType, OperationUnit ou) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.STA_NOT_FOUND);
        }
        if (!(StockTransApplicationStatus.CREATED.equals(sta.getStatus()) || StockTransApplicationStatus.FAILED.equals(sta.getStatus()))) {
            throw new BusinessException(ErrorCode.STA_STATUS_ERROR, new Object[] {sta.getCode()});
        }
        // 库存占用
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("in_sta_id", staId);
        SqlOutParameter s = new SqlOutParameter("error_sku_id", Types.VARCHAR);
        SqlParameter[] sqlParameters = {new SqlParameter("in_sta_id", Types.NUMERIC), s};
        Map<String, Object> result = null;
        result = staDao.executeSp("sp_occupy_inv_for_rtn_no_loc", sqlParameters, params);
        String errorSku = (String) result.get("error_sku_id");
        BusinessException root = null;
        if (StringUtils.hasText(errorSku)) {
            String[] skus = errorSku.split(",");
            for (String str : skus) {
                String[] strs = str.split(Constants.STA_SKUS_SLIPT_STR);
                Long skuId = Long.parseLong(strs[0]);
                Long qty = Long.parseLong(strs[1]);
                if (root == null) {
                    root = new BusinessException(ErrorCode.OCCPUAID_INVENTORY_ERROR_NO_ENOUGHT_QTY);
                }
                BusinessException current = root;
                while (current.getLinkedException() != null) {
                    current = current.getLinkedException();
                }
                Sku sku = skuDao.getByPrimaryKey(skuId);

                // log.info("returnId====="+returnId);
                log.info(sku.getBarCode() + "--" + qty);

                BusinessException be = new BusinessException(ErrorCode.SKU_NO_INVENTORY_QTY, new Object[] {sku.getName(), sku.getCode(), sku.getBarCode(), qty});
                current.setLinkedException(be);
            }
            throw root;
        } else {
            // 创建 STV ,STV LINE
            // save stv
            String code = stvDao.getCode(sta.getId(), new SingleColumnRowMapper<String>());
            StockTransVoucher stvCreate = new StockTransVoucher();
            stvCreate.setCode(code);
            stvCreate.setSta(sta);
            stvCreate.setStatus(StockTransVoucherStatus.CREATED);
            stvCreate.setTransactionType(transType);
            stvCreate.setDirection(TransactionDirection.OUTBOUND);
            stvCreate.setWarehouse(ou);
            stvDao.save(stvCreate);
            // find stave
            stvDao.flush();
            stvLineDao.createByStaId(staId);
            // 更新sta状态为库存占用
            sta.setStatus(StockTransApplicationStatus.OCCUPIED);
            staDao.save(sta);
            // IM
            hubWmsService.insertOccupiedAndRelease(sta.getId());
            return stvCreate;
        }
    }

    /**
     * UA入库执行
     */
    @Transactional
    public void createStaForInBoundRtnExecute(StockTransApplication sta, MsgRtnInboundOrder msg) {
        // 创建Sta
        createInBoundSta(sta, msg);
        // 执行入库
        // MsgRtnInboundOrder msg2 =
        // msgRtnInboundOrderDao.findInboundBySlipCode(slipCode);
        MsgRtnInboundOrder msg2 = msgRtnInboundOrderDao.getByPrimaryKey(msg.getId());
        if (null != msg2 && StringUtils.hasText(msg2.getStaCode())) {
            try {
                wareHouseManagerProxy.uaInboundToBz(msg2);
            } catch (BusinessException e) {
                throw new BusinessException();
            }
        }
    }

    /**
     * 创STA
     */
    @Transactional
    private void createInBoundSta(StockTransApplication sta, MsgRtnInboundOrder msg) {
        log.info("-------------UA create STA-----------------start-------");
        String innerShopCode = null;
        Warehouse wh = whManager.getWareHouseByVmiSource(Constants.VIM_WH_SOURCE_ITOCHU_UA);
        if (wh == null) {
            // log.info("=========OPERATION UNIT {} NOT FOUNT===========",new
            // Object[]{vmiCode});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }
        BiChannel shop = null;
        List<BiChannel> shops = biChannelDao.getAllRefShopByWhOuId(wh.getOu().getId());
        if (shops != null && shops.size() > 0) {
            // shop = shops.get(0);
            for (BiChannel sp : shops) {
                if (sp.getCode().equals("1UA店铺")) {
                    shop = sp;
                }
            }
        }
        if (shop == null) {
            log.info("========= {} NOT FOUNT SHOP===========", new Object[] {wh.getOu().getId()});
            throw new BusinessException(ErrorCode.END_OPERATION_UNIT_NOT_FOUNT);
        }

        innerShopCode = shop.getCode();
        // sta.setRefSlipCode(box.getBoxNo());
        sta.setBusinessSeqNo(staDao.getBusinessSeqNo(new SingleColumnRowMapper<BigDecimal>()).longValue());
        sta.setCreateTime(new Date());
        sta.setStatus(StockTransApplicationStatus.CREATED);
        sta.setRefSlipCode(msg.getRefSlipCode());
        sta.setOwner(innerShopCode);
        sta.setMainWarehouse(wh.getOu());
        // sta.setMainStatus(is);
        staDao.save(sta);
        // 更新staid 到 中间表
        List<MsgRtnInboundOrderLine> lines = msgRtnInboundOrderLineDao.findRtnOrderLineByRId(msg.getId());
        for (MsgRtnInboundOrderLine msgRtnLine : lines) {
            Sku sku = skuDao.getByBarcode1(msgRtnLine.getBarcode());
            if (sku == null) {
                throw new BusinessException(ErrorCode.SKU_NOT_FOUND, new Object[] {msgRtnLine.getBarcode()});
            }
            StaLine staLine = new StaLine();
            staLine.setQuantity(msgRtnLine.getQty());
            staLine.setSku(sku);
            staLine.setCompleteQuantity(0L);// 已执行数量
            staLine.setSta(sta);
            staLine.setOwner(innerShopCode);
            staLine.setInvStatus(msgRtnLine.getInvStatus());
            staLineDao.save(staLine);
        }
        msgRtnInboundOrderDao.flush();
        msgRtnInboundOrderDao.updateStaCodeToMsg(sta.getCode(), msg.getId());
        log.info("-------------UA create STA-----------------end-------");
    }

    /**
     * 读文件数据
     */
    @Transactional
    private void readFileIntoDBInOutBoundRtn(String localFileDir, String bakFileDir, String condition, String fileStart, String fileEnd) {
        File fileDir = new File(localFileDir);
        File[] files = fileDir.listFiles();
        // log.info("files.length="+files.length);
        if (files == null || files.length == 0) {
            log.debug("{} is null, has no file ============================", localFileDir);
        } else {
            // 从本地读取文件写入到数据库中
            for (File file : files) {
                if (!file.isDirectory() && (file.getName().indexOf(fileStart) != -1 && file.getName().indexOf(fileEnd) != -1)) {
                    FileInputStream in = null;
                    BufferedReader buffRead = null;
                    UnicodeReader uReader = null;
                    boolean success = false;
                    try {
                        String line = null;
                        List<String> results = new ArrayList<String>();
                        in = new FileInputStream(file);
                        uReader = new UnicodeReader(in, Charset.defaultCharset().name());
                        buffRead = new BufferedReader(uReader);
                        while ((line = buffRead.readLine()) != null) {
                            results.add(line);
                        }
                        success = true;
                        if (condition.equals(outBoundRtn)) {
                            outBoundRtnInsertIntoDB(results);
                        } else if (condition.equals(inBoundRtn)) {
                            inBoundRtnInsertIntoDB(results);
                        } else if (condition.equals(inBoundRtn2)) {
                            inBoundRtnInsertIntoDB2(results);
                        } else if (condition.equals(returnRtn)) {
                            returnRtnInsertIntoDB(results);
                        }
                    } catch (FileNotFoundException e) {
                        success = false;
                        throw new BusinessException(ErrorCode.FILE_NOT_FOUND);
                    } catch (IOException e) {
                        success = false;
                        throw new BusinessException(ErrorCode.IO_EXCEPTION);
                    } catch (Exception e) {
                        success = false;
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    } finally {
                        if (buffRead != null) {
                            try {
                                buffRead.close();
                            } catch (IOException e) {}
                        }
                        if (uReader != null) {
                            try {
                                uReader.close();
                            } catch (IOException e) {}
                        }

                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {}
                        }
                        try {
                            if (success) {
                                FileUtils.copyFileToDirectory(file, new File(bakFileDir), true);
                                file.delete();
                            }
                        } catch (IOException e) {
                            if (log.isErrorEnabled()) {
                                log.error("Ito readFileIntoDBInOutBoundRtn Exception:", e);
                            }
                            throw new BusinessException(ErrorCode.IO_EXCEPTION);
                        }
                    }
                }
            }
        }
    }

    /**
     * UA-将文件数据写到出库反馈中间表
     * 
     * @throws ParseException
     */
    @Transactional
    public boolean outBoundRtnInsertIntoDB(List<String> lines) {
        boolean flag = true;
        if (lines != null && !lines.isEmpty()) {
            for (String s : lines) {
                try {
                    if (s == null || "".equals(s)) {
                        continue;
                    }
                    String result[] = s.split(delimiter);

                    if (result.length == 11) {
                        MsgRtnOutbound msg0 = msgRtnOutboundDao.findByStaCode(result[2]);
                        // 数据重复则返回
                        if (msg0 != null) {
                            continue;
                        }
                        MsgRtnOutbound rtn = new MsgRtnOutbound();
                        rtn.setSource(Constants.VIM_WH_SOURCE_ITOCHU_UA);
                        rtn.setStaCode(result[2]);
                        rtn.setOutboundTime(FormatUtil.stringToDate(result[4], "yyyyMMdd"));
                        rtn.setLpCode(result[5]);
                        rtn.setTrackingNo(result[6]);
                        rtn.setTransFeeCost(!StringUtils.hasText(result[7]) ? new BigDecimal("0") : new BigDecimal(result[7]));
                        rtn.setRemark(result[8]);
                        rtn.setField1(result[9]);
                        rtn.setSourceWh(Constants.VIM_WH_SOURCE_ITOCHU_UA);
                        rtn.setType(result[10]);
                        rtn.setStatus(DefaultStatus.CREATED);
                        msgRtnOutboundDao.save(rtn);
                    } else if (result.length == 8) {
                        MsgRtnOutbound msg0 = msgRtnOutboundDao.findByStaCode(result[2]);
                        MsgRtnOutboundLine msgline0 = msgRtnOutboundLineDao.findMsgOutBoundLineByStaCodeAndSkuCode(result[2], result[3], new BeanPropertyRowMapper<MsgRtnOutboundLine>(MsgRtnOutboundLine.class));
                        if (msg0 == null || msgline0 != null) {
                            continue;
                        }
                        MsgRtnOutboundLine line = new MsgRtnOutboundLine();
                        line.setSkuCode(result[3]);
                        line.setQty(Long.parseLong(result[4]));
                        MsgRtnOutboundLine line2 = msgRtnOutboundLineDao.save(line);
                        msgRtnOutboundLineDao.flush();
                        // find,update
                        MsgRtnOutbound msg = msgRtnOutboundDao.findByStaCode(result[2]);
                        msgRtnOutboundLineDao.updateMsgIdById(msg.getId(), line2.getId());
                    }
                } catch (Exception e) {
                    log.debug(e.getMessage());
                    if (log.isErrorEnabled()) {
                        log.error("Ito outBoundRtnInsertIntoDB Exception:", e);
                    }
                    flag = false;
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
            }
        }
        return flag;
    }

    /**
     * 将文件数据写到入库反馈中间表
     */
    @Transactional
    public boolean inBoundRtnInsertIntoDB(List<String> lines) {
        boolean flag = true;
        // int index = 0;
        List<String> listMsg = new ArrayList<String>();
        if (lines != null && !lines.isEmpty()) {
            for (String s : lines) {
                try {
                    if (s == null || "".equals(s)) {
                        continue;
                    }
                    String result[] = s.split(delimiter);
                    if (result.length == 5 || result.length == 6) {
                        // 判断重复订单头
                        MsgRtnInboundOrder order0 = msgRtnInboundOrderDao.findInboundBySlipCode(result[1]);
                        if (order0 != null) {
                            continue;
                        }
                        // 入库反馈中间表
                        MsgRtnInboundOrder rtnOrder = new MsgRtnInboundOrder();
                        rtnOrder.setSource(Constants.VIM_WH_SOURCE_ITOCHU_UA);
                        rtnOrder.setCreateTime(new Date());
                        rtnOrder.setInboundTime(FormatUtil.stringToDate(result[4], "yyyyMMdd"));
                        rtnOrder.setRefSlipCode(result[1]);
                        rtnOrder.setStatus(DefaultStatus.CREATED);
                        rtnOrder.setSourceWh(Constants.VIM_WH_SOURCE_ITOCHU_UA);
                        rtnOrder = msgRtnInboundOrderDao.save(rtnOrder);
                    } else if (result.length == 8) {
                        // is 库存状态
                        Map<String, InventoryStatus> stsMap = new HashMap<String, InventoryStatus>();
                        InventoryStatus invStatus = stsMap.get(result[7]);
                        if (invStatus == null) {
                            invStatus = inventoryStatusDao.findByVmiSourceAndStatus(Constants.VIM_WH_SOURCE_ITOCHU_UA, result[7], new BeanPropertyRowMapperExt<InventoryStatus>(InventoryStatus.class));
                            stsMap.put(result[7], invStatus);
                        }
                        // 判断重复订单行
                        MsgRtnInboundOrder order0 = msgRtnInboundOrderDao.findInboundBySlipCode(result[1]);
                        // 通过sku,单据号,库存状态查找入库明细
                        MsgRtnInboundOrderLine orderLine = msgRtnInboundOrderLineDao.findRtnLineBySlipCodeAndSku(result[1], result[5], result[7]);
                        if (order0 == null) {
                            continue;
                        }
                        if (orderLine != null) {
                            continue;
                        }
                        // 入库反馈中间表行
                        MsgRtnInboundOrderLine rtnLine = new MsgRtnInboundOrderLine();
                        Sku sku = skuDao.getByBarcode1(result[5]);// 根据barcode获取SKU
                        // index++;
                        if (null == sku) {
                            // System.out.println(result[5]);
                            // System.out.println(index);
                            log.debug("条形码barCode：" + result[5] + "不存在！");
                            listMsg.add(result[5]);
                            continue;
                        }
                        rtnLine.setBarcode(result[5]);
                        rtnLine.setInvStatus(invStatus);
                        rtnLine.setSkuCode(sku.getCode());
                        rtnLine.setQty(new BigDecimal(result[6]).longValue());
                        rtnLine.setMsgRtnInOrder(order0);
                        rtnLine.setOutStatus(result[7]);
                        msgRtnInboundOrderLineDao.save(rtnLine);
                    }
                } catch (Exception e) {
                    if (log.isErrorEnabled()) {
                        log.error("Ito inBoundRtnInsertIntoDB Exception:", e);
                    }
                    log.error(e.getMessage());
                    flag = false;
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
            }
            flag = sendMailForBarCodeNotExist(flag, listMsg);
        }
        return flag;
    }

    /**
     * 将退货文件数据写到入库反馈中间表
     */
    @Transactional
    public boolean inBoundRtnInsertIntoDB2(List<String> lines) {
        boolean flag = true;
        if (lines != null && !lines.isEmpty()) {
            for (String s : lines) {
                try {
                    if (s == null || "".equals(s)) {
                        continue;
                    }
                    String result[] = s.split(delimiter);
                    if (result.length > 6 && result.length < 8) {
                        String result2[] = new String[8];
                        for (int i = 0; i < result.length; i++) {
                            result2[i] = result[i];
                        }
                        result2[result.length] = "";
                        result = result2;
                    }
                    if (result.length == 6 || result.length == 5) {
                        // 去除重复的行
                        MsgRtnInboundOrder rtn2 = msgRtnInboundOrderDao.findInboundByStaCode(result[1], new BeanPropertyRowMapperExt<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
                        if (rtn2 != null) {
                            continue;
                        }
                        MsgRtnInboundOrder rtn = new MsgRtnInboundOrder();
                        rtn.setStaCode(result[1]);
                        Integer t = msgTypeDao.findTypeBySourceandType2(Constants.VIM_WH_SOURCE_ITOCHU_UA, result[2], new SingleColumnRowMapper<Integer>(Integer.class));
                        int type0 = 0;
                        if (t != null) {
                            type0 = t.intValue();
                        }

                        rtn.setType(type0);
                        rtn.setSource(Constants.VIM_WH_SOURCE_ITOCHU_UA);
                        rtn.setSourceWh(Constants.VIM_WH_SOURCE_ITOCHU_UA);
                        rtn.setStatus(DefaultStatus.CREATED);
                        rtn.setCreateTime(new Date());
                        rtn.setInboundTime(FormatUtil.stringToDate(result[4], "yyyyMMdd"));
                        msgRtnInboundOrderDao.save(rtn);
                        msgRtnInboundOrderDao.flush();
                    } else if (result.length == 8) {
                        MsgRtnInboundOrderLine line = new MsgRtnInboundOrderLine();
                        Sku k = skuDao.getByBarcode1(result[5]);
                        // 去除重复的行
                        MsgRtnInboundOrder rtn2 = msgRtnInboundOrderDao.findInboundByStaCode(result[1], new BeanPropertyRowMapperExt<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
                        MsgRtnInboundOrderLine line1 = msgRtnInboundOrderLineDao.findRtnOrderLineByStaCodeAndSkuCode2(result[1], k.getCode(), new BeanPropertyRowMapperExt<MsgRtnInboundOrderLine>(MsgRtnInboundOrderLine.class));
                        if (rtn2 == null || line1 != null) {
                            continue;
                        }
                        // is 库存状态
                        Map<String, InventoryStatus> stsMap = new HashMap<String, InventoryStatus>();
                        InventoryStatus invStatus = stsMap.get(result[7]);
                        if (invStatus == null) {
                            invStatus = inventoryStatusDao.findByVmiSourceAndStatus(Constants.VIM_WH_SOURCE_ITOCHU_UA, result[7], new BeanPropertyRowMapperExt<InventoryStatus>(InventoryStatus.class));
                            stsMap.put(result[7], invStatus);
                        }
                        // MsgRtnInboundOrderLine line0 =
                        // msgRtnInboundOrderLineDao.findRtnOrderLineByStaCodeAndSkuCode(result[1],
                        // k.getCode(), new
                        // BeanPropertyRowMapperExt<MsgRtnInboundOrderLine>(MsgRtnInboundOrderLine.class));
                        line.setSkuCode(k.getCode());
                        line.setQty(Long.parseLong(result[6]));
                        line.setInvStatus(invStatus);
                        line = msgRtnInboundOrderLineDao.save(line);
                        msgRtnInboundOrderLineDao.flush();
                        MsgRtnInboundOrder rtnMsg = msgRtnInboundOrderDao.findInboundByStaCode(result[1], new BeanPropertyRowMapperExt<MsgRtnInboundOrder>(MsgRtnInboundOrder.class));
                        msgRtnInboundOrderLineDao.updateRtnMsgIdById(rtnMsg.getId(), line.getId());
                    }
                } catch (Exception e) {
                    log.debug(e.getMessage());
                    if (log.isErrorEnabled()) {
                        log.error("Ito inBoundRtnInsertIntoDB2 Exception:", e);
                    }
                    flag = false;
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
            }
        }
        return flag;
    }

    /**
     * 读退仓数据
     */
    private boolean returnRtnInsertIntoDB(List<String> lines) {
        boolean flag = true;
        if (lines != null && !lines.isEmpty()) {
            for (String s : lines) {
                try {
                    if (!StringUtils.hasText(s)) {
                        continue;
                    }
                    String result[] = s.split(delimiter);
                    if (result.length == 11) {
                        // 去除重复的行
                        MsgRtnReturn rtn0 = new MsgRtnReturn();
                        rtn0 = msgRtnReturnDao.findReturnBySlipCode(result[2]);
                        if (rtn0 != null) {
                            continue;
                        }
                        MsgRtnReturn rtn = new MsgRtnReturn();
                        rtn.setRefSlipCode(result[2]);
                        rtn.setSource(Constants.VIM_WH_SOURCE_ITOCHU_UA);
                        rtn.setSourceWh(Constants.VIM_WH_SOURCE_ITOCHU_UA);
                        rtn.setStatus(DefaultStatus.CREATED);
                        rtn.setCreateTime(new Date());
                        // rtn.setType(result[10]);//退库返厂类型
                        msgRtnReturnDao.save(rtn);
                    } else if (result.length == 8) {
                        MsgRtnReturn rtn2 = new MsgRtnReturn();
                        rtn2 = msgRtnReturnDao.findReturnBySlipCode(result[2]);
                        Sku k = skuDao.getByBarcode1(result[3]);
                        MsgRtnReturnLine rtnLine0 = new MsgRtnReturnLine();
                        rtnLine0 = msgRtnReturnLineDao.findLineBySlipCodeAndSkuBarCode(result[2], k.getId());
                        if (rtn2 == null || rtnLine0 != null) {
                            continue;
                        }
                        // is 库存状态
                        Map<String, InventoryStatus> stsMap = new HashMap<String, InventoryStatus>();
                        InventoryStatus invStatus = stsMap.get(result[5]);
                        if (invStatus == null) {
                            invStatus = inventoryStatusDao.findByVmiSourceAndStatus(Constants.VIM_WH_SOURCE_ITOCHU_UA, result[5], new BeanPropertyRowMapperExt<InventoryStatus>(InventoryStatus.class));
                            stsMap.put(result[5], invStatus);
                        }
                        MsgRtnReturnLine rtnLine = new MsgRtnReturnLine();
                        rtnLine.setInvStatus(invStatus);
                        rtnLine.setSkuId(k.getId());
                        rtnLine.setMsgRtnInOrder(rtn2);
                        rtnLine.setQty(new BigDecimal(result[4]).longValue());
                        msgRtnReturnLineDao.save(rtnLine);
                    }
                } catch (Exception e) {
                    log.debug(e.getMessage());
                    if (log.isErrorEnabled()) {
                        log.error("Ito returnRtnInsertIntoDB Exception:", e);
                    }
                    flag = false;
                    throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                }
            }
        }
        return flag;
    }


    private boolean sendMailForBarCodeNotExist(boolean flag, List<String> listMsg) {
        // 查询系统常量表 收件人
        if (listMsg.size() != 0) {
            ChooseOption option = chooseOptionDao.findByCategoryCode("BARCODE_NOT_EXIST");
            if (!StringUtil.isEmpty(option.getOptionValue())) {
                // 传人邮件模板的CODE -- 查询String类型可用的模板
                MailTemplate template = mailTemplateDao.findTemplateByCode("BARCODE_NOT_EXIST");
                if (template != null) {
                    try {
                        // 如果存在条形码不存在的sku,先发邮件,后抛异常,执行下一个文件
                        // MailTemplate mailTemplate =
                        // mailTemplateDao.findByCode(Constants.MAIL_TEMPLATE_CODE_FALIED_TO_WAREHOUSE);
                        // List<String> recipients = new ArrayList<String>();
                        // List<String> mailContents = new ArrayList<String>();
                        StringBuffer sBuffer = new StringBuffer();
                        // Map<String, Object> params = new HashMap<String, Object>();
                        // mailContents.add("=======================以下barCode不存在=======================<br/>");
                        for (int i = 0; i < listMsg.size(); i++) {
                            // mailContents.add("<b>" + listMsg.get(i).toString() + "</b>");
                            sBuffer.append("<tr><td><b>" + listMsg.get(i).toString() + "</b></td></tr>");
                        }

                        String subject = template.getSubject();// 标题
                        String addressee = option.getOptionValue(); // 查询收件人
                        String mailBodyHead = template.getMailBody().substring(0, template.getMailBody().indexOf("$"));// 邮件内容头
                        String mailHtml = template.getMailBody().substring(template.getMailBody().indexOf("$") + 1, template.getMailBody().length());// 邮件样式
                        String mailBodyDetail = sBuffer.toString();// 邮件内容详情
                        // 替换样式内容
                        String tcontent = mailHtml.replaceAll("mailBodyHead", mailBodyHead);
                        tcontent = tcontent.replaceAll("mailBodyDetail", mailBodyDetail);

                        // params.put("contents", mailContents);
                        /*
                         * recipients.add("bzhou@underarmour.com");
                         * recipients.add("wei.wang2@baozun.cn");
                         */
                        // recipients.add("zhouzheng.deng@baozun.cn");

                        List<MailLog> listMaillog = mailLogDao.findMailLog();
                        if (listMaillog.size() == 0) { // 没有发送过邮件
                            MailUtil.sendMail(subject, addressee, "", tcontent, true, null);
                        }
                        for (int i = 0; i < listMaillog.size(); i++) { // 已经发送了邮件
                            MailLog mailLog = listMaillog.get(i);
                            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                            String dateStr = sf.format(mailLog.getSendTime());

                            SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd");
                            String dateStr2 = sf2.format(new Date());

                            if (!dateStr.equals(dateStr2)) {
                                MailUtil.sendMail(subject, addressee, "", tcontent, false, null);
                            }
                        }
                    } catch (Exception e) {
                        if (log.isErrorEnabled()) {
                            log.error("入库反馈barcode不存在，发邮件出错！", e);
                        }
                    }
                    flag = false;
                    throw new BusinessException("数据库：" + ErrorCode.SKU_BAR_CODE_NOT_NULL);
                } else {
                    log.debug("邮件模板不存在或被禁用");
                }
            } else {
                log.debug("邮件通知失败,收件人为空！");
            }
        }
        return flag;
    }

    /**
     * 将数据写入到本地文件
     */
    @Transactional
    private boolean writeDataFile(String data, String localDir, String fileName) {
        if (data == null || data.trim().length() == 0) {
            log.debug("data is null ***********************  app exit");
            return false;
        }
        boolean flag = false;
        if (data == null || data.length() == 0) return false;
        BufferedWriter br = null;
        FileOutputStream out = null;
        OutputStreamWriter osw = null;
        try {
            out = new FileOutputStream(localDir + File.separator + fileName, true);
            // br = new BufferedWriter(new FileWriter(new File(localDir +
            // File.separator + fileName),true)); // 追加方式-添加数据
            osw = new OutputStreamWriter(out, "UTF-8");
            br = new BufferedWriter(osw);
            br.write(data);
            // br.newLine();
            flag = true;
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("Ito writeDataFile IOException！", e);
            }
            throw new BusinessException(ErrorCode.IO_EXCEPTION);
        } finally {
            try {
                if (br != null) {
                    br.flush();
                    br.close();
                }
                if (osw != null) {
                    osw.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                log.error("",e);
                throw new BusinessException(ErrorCode.IO_EXCEPTION);
            }
        }
        return flag;
    }

    private void uploadFtpUtil() {
        Map<String, String> cfg = configManager.getCommonFTPConfig(Constants.CONFIG_GROUP_UA_FTP);
        String localUpFileDir = cfg.get(Constants.VMI_FTP_UP_LOCALPATH);
        String bakFileDir = cfg.get(Constants.ITOCHU_FTP_DOWN_LOCALPATH_BACKUP);
        ChannelSftp sftp = SFTPUtil.connect(cfg.get(Constants.VMI_FTP_URL), Integer.parseInt(cfg.get(Constants.VMI_FTP_PORT)), cfg.get(Constants.VMI_FTP_USERNAME), cfg.get(Constants.VMI_FTP_PASSWORD));
        try {
            String upfileDir = cfg.get(Constants.VMI_FTP_UPPATH);
            File dir = new File(localUpFileDir);
            if (dir.listFiles() != null && dir.listFiles().length != 0) {
                for (File f : dir.listFiles()) {
                    try {
                        boolean boolSendFile = SFTPUtil.sendFile(upfileDir, f.getPath(), sftp);

                        if (null != sftp && boolSendFile) {
                            FileUtils.copyFileToDirectory(f, new File(bakFileDir), true);
                            f.delete();
                        }
                    } catch (IOException e) {
                        if (log.isErrorEnabled()) {
                            log.error("Ito uploadFtpUtil IOException！", e);
                        }
                    }
                }
            }
        } finally {
            SFTPUtil.disconnect(sftp);
        }

    }

    private String connectOutBoundLineData(MsgOutboundOrder order, MsgOutboundOrderLine line) {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.ILC_SHDTLDNNEW);
        sb.append(delimiter);
        sb.append(order.getStaCode());
        sb.append(delimiter);
        sb.append(line.getId());
        sb.append(delimiter);
        sb.append(line.getId());
        sb.append(delimiter);
        sb.append(Constants.ILC_UA_EC);
        sb.append(delimiter);
        sb.append(line.getSku().getBarCode());
        sb.append(delimiter);
        sb.append(line.getUnitPrice() == null ? 0 : line.getUnitPrice());
        sb.append(delimiter);
        sb.append(line.getQty());
        sb.append(delimiter);
        sb.append("");
        sb.append(delimiter);
        sb.append("");
        sb.append(delimiter);
        sb.append(line.getSku().getName());// 商品名称
        sb.append(delimiter);
        sb.append("");
        sb.append(delimiter);
        sb.append(line.getId());
        sb.append("\r\n");
        return sb.toString();
    }

    private String connectOutBoundData(MsgOutboundOrder order) {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.ILC_SHHDRDNNEW);
        sb.append(delimiter);
        sb.append(Constants.VIM_WH_SOURCE_ITOCHU);
        sb.append(delimiter);
        sb.append(order.getStaCode());
        sb.append(delimiter);
        sb.append(order.getStaCode());
        sb.append(delimiter);
        sb.append(Constants.ILC_UA_EC);
        sb.append(delimiter);
        sb.append(order.getReceiver());
        sb.append(delimiter);
        sb.append(order.getTotalActual() == null ? 0 : order.getTotalActual());// orderPrice
        sb.append(delimiter);
        sb.append(order.getTransferFee() == null ? 0 : order.getTransferFee());// transFee
        sb.append(delimiter);
        sb.append("");
        sb.append(delimiter);
        sb.append("");
        sb.append(delimiter);
        sb.append(order.getReceiver() == null ? "" : order.getReceiver());
        sb.append(delimiter);
        sb.append("中国");// SoDeliveryInfo.COUNTRY_CHINA
        sb.append(delimiter);
        sb.append(order.getProvince() == null ? "" : order.getProvince());
        sb.append(delimiter);
        sb.append(order.getCity() == null ? "" : order.getCity());
        sb.append(delimiter);
        sb.append(order.getDistrict() == null ? "" : order.getDistrict());
        sb.append(delimiter);
        sb.append(order.getZipcode() == null ? "100000" : order.getZipcode());
        sb.append(delimiter);
        String addr = order.getAddress();
        if (addr != null) {
            Pattern p = Pattern.compile("\r|\n|\r\n");
            Matcher m = p.matcher(addr);
            addr = m.replaceAll(" ");
        }
        String addr0 = "", addr1 = "", addr2 = "";
        if (addr != null) {
            if (addr.length() > 50 && addr.length() <= 100) {
                addr0 = addr.substring(0, 49);
                addr1 = addr.substring(50, addr.length());
            } else if ((addr.length() > 50 && addr.length() <= 150)) {
                addr0 = addr.substring(0, 49);
                addr1 = addr.substring(50, addr.length());
                addr2 = addr.substring(100, addr.length());
            } else if (addr.length() > 50 && addr.length() > 150) {
                addr0 = addr.substring(0, 49);
                addr1 = addr.substring(50, addr.length());
                addr2 = addr.substring(100, 150);
            } else {
                addr0 = addr;
            }
        }
        sb.append(addr0 == null ? "" : addr0);
        sb.append(delimiter);
        sb.append(addr1);
        sb.append(delimiter);
        sb.append(addr2);
        sb.append(delimiter);
        sb.append(StringUtils.hasText(order.getLpCode()) ? order.getLpCode() : "快递");
        sb.append(delimiter);
        sb.append(StringUtils.hasText(order.getLpCode()) ? order.getLpCode() : "快递");
        sb.append(delimiter);
        sb.append(order.getTelePhone() == null ? "" : order.getTelePhone());// phone
        sb.append(delimiter);
        sb.append(order.getMobile() == null ? "" : order.getMobile());// mobile
        sb.append(delimiter);
        sb.append("");
        sb.append(delimiter);
        sb.append("");
        sb.append(delimiter);
        sb.append("0");
        sb.append(delimiter);
        sb.append("");// 备注不传
        sb.append(delimiter);

        StockTransApplication sta = staDao.findStaByCode(order.getStaCode());
        String taobaoCode = "";
        String remark0 = "";
        @SuppressWarnings("unused")
        String type0 = "";
        int saleType = sta.getType().getValue();
        if (sta != null && saleType == 21) {
            taobaoCode = sta.getSlipCode2();// 确定oms的UA订单去向
            remark0 = sta.getMemo() == null ? "" : sta.getMemo();
        } else if (sta != null && saleType == 42) {
            taobaoCode = sta.getRefSlipCode();
        }
        sb.append(taobaoCode == null ? "" : taobaoCode);// 淘宝订单号
        sb.append(delimiter);
        sb.append(remark0);
        sb.append(delimiter);
        sb.append("Y");
        sb.append(delimiter);

        if (sta != null && !StringUtil.isEmpty(sta.getOwner())) {
            Integer orderKey = null;
            StaDeliveryInfoCommand deliveryInfo = staDeliveryInfoDao.findTheStaDeliveryInfoByStaId(sta.getId(), new BeanPropertyRowMapperExt<StaDeliveryInfoCommand>(StaDeliveryInfoCommand.class));
            // 通过sta的owner判断是否是官网商城订单，且是cod类型，则为官网商城COD订单
            // 换货并且原始订单为cod订单，新订单默认改成支付宝,这里做一个OUTBOUND_RETURN_REQUEST的筛选
            if (Constants.UA_CHANNEL.equals(sta.getOwner()) && deliveryInfo.getIsCod() && !sta.getType().equals(StockTransApplicationType.OUTBOUND_RETURN_REQUEST)) {
                orderKey = Constants.ITCHU_STORE_COD;
            }// 通过sta的owner判断是哪种平台类型，基本可以判断返回的平台类型
            else {
                if (Constants.UA_CHANNEL.equals(sta.getOwner())) {
                    orderKey = Constants.ITCHU_STORE_NORMAL_ORDER;
                } else if (Constants.UA_HK_CHANNEL.equals(sta.getOwner())) {
                    orderKey = Constants.ITCHU_STORE_HKUA;
                } else if (Constants.UA_TW_CHANNEL.equals(sta.getOwner())) {
                    orderKey = Constants.ITCHU_STORE_TW;
                } else if (Constants.UA_TB_CHANNEL.equals(sta.getOwner())) {
                    orderKey = Constants.ITCHU_STORE_TB;
                }
            }
            String itchoOrderStr = "";
            ChooseOption option = chooseOptionDao.findByCategoryCodeAndKey(Constants.ITCHU_ORDER_TYPE, orderKey.toString());
            if (null != option) {
                itchoOrderStr = option.getOptionValue();
            }
            // String itchuoType = backItchoOrderTypeBySoTypeAndPay(orderTypeInt, paymentTypeInt);
            sb.append(itchoOrderStr);
        }
        sb.append("\r\n");
        return sb.toString();
    }

    /**
     * 根据订单类型、支付方式 返回伊藤忠订单类型
     * 
     * @return
     */
    @SuppressWarnings("unused")
    private String backItchoOrderTypeBySoTypeAndPay(Integer orderType, Integer paymentType) {
        MultiKeyMap mMap = new MultiKeyMap();
        // 官网商城订单 货到付款 官网商城COD订单
        mMap.put(Constants.STORE_ONLINE_UA, Constants.PAYMENT_COD, Constants.ITCHU_STORE_COD);
        // 官网商城订单 网银在线 官网商城普通订单
        mMap.put(Constants.STORE_ONLINE_UA, Constants.PAYMENT_PAY_ONLINE, Constants.ITCHU_STORE_NORMAL_ORDER);
        // 官网商城订单 支付宝 官网商城普通订单
        mMap.put(Constants.STORE_ONLINE_UA, Constants.PAYMENT_ZFB, Constants.ITCHU_STORE_NORMAL_ORDER);
        // 官网员工订单 网银在线 官网商城普通订单
        mMap.put(Constants.STORE_ONLINE_EMPLOYEE_UA, Constants.PAYMENT_PAY_ONLINE, Constants.ITCHU_STORE_NORMAL_ORDER);
        // 官网员工订单 支付宝 官网商城普通订单
        mMap.put(Constants.STORE_ONLINE_EMPLOYEE_UA, Constants.PAYMENT_ZFB, Constants.ITCHU_STORE_NORMAL_ORDER);
        // 台湾商城订单 贝宝 台湾商城普通订单
        mMap.put(Constants.STORE_ONLINE_TW_UA, Constants.PAYMENT_PAYPAL, Constants.ITCHU_STORE_TW);
        // 台湾商城订单 国际支付 - 支付宝 台湾商城普通订单
        mMap.put(Constants.STORE_ONLINE_TW_UA, Constants.PAYMENT_INTERNATIONAL_PAY, Constants.ITCHU_STORE_TW);
        // 台湾员工订单 贝宝 台湾商城普通订单
        mMap.put(Constants.STORE_ONLINE_TW_EMPLOYEE_UA, Constants.PAYMENT_PAYPAL, Constants.ITCHU_STORE_TW);
        // 台湾员工订单 国际支付 - 支付宝 台湾商城普通订单
        mMap.put(Constants.STORE_ONLINE_TW_EMPLOYEE_UA, Constants.PAYMENT_INTERNATIONAL_PAY, Constants.ITCHU_STORE_TW);
        // 香港商城订单 贝宝 香港商城普通订单
        mMap.put(Constants.STORE_ONLINE_HK_UA, Constants.PAYMENT_PAYPAL, Constants.ITCHU_STORE_HKUA);
        // 香港商城订单 国际支付 - 支付宝 香港商城普通订单
        mMap.put(Constants.STORE_ONLINE_HK_UA, Constants.PAYMENT_INTERNATIONAL_PAY, Constants.ITCHU_STORE_HKUA);
        // 香港员工订单 贝宝 香港商城普通订单
        mMap.put(Constants.STORE_ONLINE_HK_EMPLOYEE_UA, Constants.PAYMENT_PAYPAL, Constants.ITCHU_STORE_HKUA);
        // 香港员工订单 国际支付 - 支付宝 香港商城普通订单
        mMap.put(Constants.STORE_ONLINE_HK_EMPLOYEE_UA, Constants.PAYMENT_INTERNATIONAL_PAY, Constants.ITCHU_STORE_HKUA);
        // 淘宝商城订单 货到付款 淘宝商城普通订单
        mMap.put(Constants.PLATFORM_ONLINE_TB, Constants.PAYMENT_COD, Constants.ITCHU_STORE_TB);
        // 淘宝商城订单 银行电汇 淘宝商城普通订单
        mMap.put(Constants.PLATFORM_ONLINE_TB, Constants.PAYMENT_BANK, Constants.ITCHU_STORE_TB);
        // 淘宝商城订单 网银在线 淘宝商城普通订单
        mMap.put(Constants.PLATFORM_ONLINE_TB, Constants.PAYMENT_PAY_ONLINE, Constants.ITCHU_STORE_TB);
        // 淘宝商城订单 支付宝 淘宝商城普通订单
        mMap.put(Constants.PLATFORM_ONLINE_TB, Constants.PAYMENT_ZFB, Constants.ITCHU_STORE_TB);
        // 淘宝商城订单 贝宝 淘宝商城普通订单
        mMap.put(Constants.PLATFORM_ONLINE_TB, Constants.PAYMENT_PAYPAL, Constants.ITCHU_STORE_TB);
        // 淘宝商城订单 国际支付 淘宝商城普通订单
        mMap.put(Constants.PLATFORM_ONLINE_TB, Constants.PAYMENT_INTERNATIONAL_PAY, Constants.ITCHU_STORE_TB);
        String itchoOrderStr = "";
        if (null != mMap.get(orderType, paymentType)) {
            ChooseOption option = chooseOptionDao.findByCategoryCodeAndKey(Constants.ITCHU_ORDER_TYPE, mMap.get(orderType, paymentType).toString());
            if (null != option) {
                itchoOrderStr = option.getOptionValue();
            }

        }
        return itchoOrderStr;
    }

    /**
     * 
     * 读文件数据 （伊藤忠库存数据）
     */
    public void readItochuRtnInvToDB(String localFileDir, String bakFileDir) {
        readItochuRtnInvToDB_2(localFileDir, bakFileDir, "IBAL", "ibupl");
        msgRtnInboundOrderDao.insertIntoUAInvLog();
    }

    /**
     * 读文件数据 （伊藤忠库存数据）
     */
    private void readItochuRtnInvToDB_2(String localFileDir, String bakFileDir, String fileStart, String fileEnd) {
        File fileDir = new File(localFileDir);
        File[] files = fileDir.listFiles();
        if (files == null || files.length == 0) {
            log.debug("{} is null, has no file ============================", localFileDir);
        } else {
            // 从本地读取文件写入到数据库中
            for (File file : files) {
                if (!file.isDirectory() && (file.getName().indexOf(fileStart) != -1 && file.getName().indexOf(fileEnd) != -1)) {
                    FileInputStream in = null;
                    BufferedReader buffRead = null;
                    UnicodeReader uReader = null;
                    boolean success = false;
                    try {
                        String line = null;
                        List<String> results = new ArrayList<String>();
                        in = new FileInputStream(file);
                        uReader = new UnicodeReader(in, Charset.defaultCharset().name());
                        buffRead = new BufferedReader(uReader);
                        while ((line = buffRead.readLine()) != null) {
                            results.add(line);
                        }
                        readItochuRtnInv(results);
                        success = true;
                    } catch (FileNotFoundException e) {
                        success = false;
                        throw new BusinessException(ErrorCode.FILE_NOT_FOUND);
                    } catch (IOException e) {
                        success = false;
                        throw new BusinessException(ErrorCode.IO_EXCEPTION);
                    } catch (Exception e) {
                        success = false;
                        throw new BusinessException(ErrorCode.SYSTEM_ERROR);
                    } finally {
                        if (buffRead != null) {
                            try {
                                buffRead.close();
                            } catch (IOException e) {}
                        }
                        if (uReader != null) {
                            try {
                                uReader.close();
                            } catch (IOException e) {}
                        }

                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {}
                        }
                        try {
                            if (success) {
                                FileUtils.copyFileToDirectory(file, new File(bakFileDir), true);
                                file.delete();
                            }
                        } catch (IOException e) {
                            throw new BusinessException(ErrorCode.IO_EXCEPTION);
                        }
                    }
                }
            }
        }
    }

    /**
     * 读伊藤忠反馈的库存数据
     */
    private void readItochuRtnInv(List<String> lines) {
        String result[] = new String[6];
        if (lines == null || lines.isEmpty()) {
            return;
        }
        try {
            for (String s : lines) {
                result = s.split(delimiter);
                ItochuRtnInv inv = new ItochuRtnInv();
                inv.setInvup(result[0]);
                inv.setCompany(result[1]);
                inv.setWarehouse(result[2]);
                inv.setSku(result[3]);
                inv.setQty(new BigDecimal(result[4]).longValue());
                inv.setInvStatus(result[5]);
                inv.setCreateTime(DateUtil.now());
                itochuRtnInvDao.save(inv);
            }
            itochuRtnInvDao.flush();
        } catch (Exception e) {
            throw new BusinessException();
        }
    }

    /**
     * 接收MQ UA INVENTORY 数据并插入数据库 param : message
     */
    public void receiveWhUaInventoryByMq(String message) {
        // WhUaInventory whUaInventory = null;
        // try {
        // // 以下为新添加
        // UaInventorys uaInventorysList = (UaInventorys) JSONUtil.jsonToBean(message,
        // UaInventorys.class);
        // if (uaInventorysList == null) {
        // return;
        // }
        // List<UaInventory> uaInventorys = uaInventorysList.getUaInventory();
        // for (UaInventory uaInventory : uaInventorys) {
        // whUaInventory = new WhUaInventory();
        // setWhUaInventory(whUaInventory, uaInventory);
        // whUaInventoryDao.save(whUaInventory);
        // }
        // whUaInventoryDao.flush();
        // // whUaInventoryLogDao.insertUaInventoryLog();
        // } catch (Exception e) {
        // throw new BusinessException();
        // }
    }

    // private void setWhUaInventory(WhUaInventory whUaInventory, UaInventory uaInventory){
    // whUaInventory.setSku(uaInventory.getSku());
    // whUaInventory.setStorerKey(uaInventory.getStorerKey());
    // whUaInventory.setTotalQty(uaInventory.getTotalQty());
    // whUaInventory.setAvaiableQty(uaInventory.getAvaiableQty());
    // whUaInventory.setShorts(uaInventory.getShorts());
    // whUaInventory.setCreateTime(new Date());
    // }

    /**
     * 插入数据到日志表
     * 
     */
    public void insertUaInventoryLog() {
        try {
            whUaInventoryLogDao.insertUaInventoryLog();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Ito insertUaInventoryLog Exception", e);
            }
        }
    }

    @Override
    public void insertAeoInventoryLog() {
        try {
            whUaInventoryLogDao.insertAeoInventoryLog();;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Ito insertAeoInventoryLog Exception", e);
            }
        }

    }

    @Override
    public void insertAeoJDInventoryLog() {
        try {
            whUaInventoryLogDao.insertAeoJDInventoryLog();;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Ito insertAeoJDInventoryLog Exception", e);
            }
        }
    }

    @Override
    public void insertAfInventoryLog() {
        try {
            whUaInventoryLogDao.insertAfInventoryLog();;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Ito insertAfInventoryLog Exception", e);
            }
        }

    }

    @Override
    public void insertNikeNewInventoryLog() {
        try {
            whUaInventoryLogDao.insertNikeNewInventoryLog();;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("insertNikeNewInventoryLog Exception", e);
            }
        }

    }
    
    @Override
    public void insertNikeNewInventoryLog2() {
        try {
            whUaInventoryLogDao.insertNikeNewInventoryLog2();;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("insertNikeNewInventoryLog2 Exception", e);
            }
        }

    }

    @Override
    public void insertNikeCrwInventoryLog() {
        try {
            whUaInventoryLogDao.insertNikeCrwInventoryLog();;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("insertNikeNewInventoryLog Exception", e);
            }
        }

    }



    @Override
    public void insertConverseInventoryLog() {
        try {
            whUaInventoryLogDao.insertConverseInventoryLog();;
        } catch (Exception e) {
            log.error("insertConverseInventoryLog Exception", e);
        }

    }

    @Override
    public void insertNewLookInventoryLog() {
        try {
            whUaInventoryLogDao.insertNewLookInventoryLog();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Ito insertNewLookInventoryLog Exception", e);
            }
        }

    }

    @Override
    public void insertUaNbaInventoryLog() {
        try {
            whUaInventoryLogDao.insertUaNbaInventoryLog();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Ito insertUaNbaInventoryLog Exception", e);
            }
        }

    }

    @Override
    public void insertNikeInventoryLog() {
        try {
            whUaInventoryLogDao.insertNikeInventoryLog();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Ito insertNikeInventoryLog Exception", e);
            }
        }

    }

    @Override
    public void insertNikeInventoryLogGZ() {
        try {
            whUaInventoryLogDao.insertNikeInventoryLogGZ();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Ito insertNikeInventoryLogGZ Exception", e);
            }
        }

    }

    @Override
    public void insertNikeInventoryLogTM() {
        try {
            whUaInventoryLogDao.insertNikeInventoryLogTM();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Ito insertNikeInventoryLogTM Exception", e);
            }
        }

    }

    @Override
    public void insertNikeInventoryLogGZTM() {
        try {
            whUaInventoryLogDao.insertNikeInventoryLogGZTM();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Ito insertNikeInventoryLogGZTM Exception", e);
            }
        }

    }

    @Override
    public void insertIDSVSInventoryLog() {
        try {
            whUaInventoryLogDao.insertIDSVSInventoryLog();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Ito insertIDSVSInventoryLog Exception", e);
            }
        }

    }


    @Override
    public void insertNewLookJDInventoryLog() {
        try {
            whUaInventoryLogDao.insertNewLookJDInventoryLog();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Ito insertNewLookJDInventoryLog Exception", e);
            }
        }
    }
}
