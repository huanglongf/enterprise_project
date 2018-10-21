package com.jumbo.wms.manager.vmi;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.vmi.converseYxData.ConverseYxReceiveDao;
import com.jumbo.dao.vmi.converseYxData.ConverseYxStockinDao;
import com.jumbo.dao.vmi.converseYxData.ConverseYxTransferOutDao;
import com.jumbo.dao.vmi.nikeDate.NikeStockReceiveDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.util.FormatUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.vmi.converseData.ConverseYxReceive;
import com.jumbo.wms.model.vmi.converseData.ConverseYxStockin;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StaLine;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;

public class VmiConverseYx extends VmiBaseBrand {
    @Autowired
    private ConverseYxStockinDao converseYxStockinDao;
    @Autowired
    private StaLineDao staLineDao;
    @Autowired
    private ConverseYxReceiveDao converseYxReceiveDao;
    @Autowired
    private NikeStockReceiveDao nikeStockReceiveDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private ConverseYxTransferOutDao converseYxTransferOutDao;

    /**
     * 
     */
    private static final long serialVersionUID = 4726818844454581533L;

    /**
     * 逐行解析文件，插入数据库，初始StaId为-1L<br/>
     * 如果插入失败或中间异常，删除新增数据<br/>
     * 插入成功，且无异常，将插入数据StaId置空<br/>
     * 标记事务如果中间宕机等情况插入的部分数据应该会回滚
     */
    @Transactional
    public boolean inBoundInsertIntoDB(List<String> lines) {
        Boolean flag = false, canMove = false;
        if (lines == null || lines.isEmpty()) {
            return flag;
        }
        try {
            String cartonNumber = null;
            String receiveDate = null;
            String fromLocation = null;
            String toLocation = null;
            String upc = null;
            Long quantity = null;
            String lineSequenceNo = null;
            String transferNo = null;
            String result[] = null;
            for (String s : lines) {
                if (s == null || "".equals(s)) {
                    continue;
                }
                result = s.split(",");
                if (result == null || result.length == 0) {
                    continue;
                }
                boolean b = false;
                if (!StringUtils.hasText(result[0])) {
                    b = true;
                } else {
                    cartonNumber = result[0];
                }
                if (!StringUtils.hasText(result[1])) {
                    b = true;
                } else {
                    receiveDate = result[1];
                }
                if (!StringUtils.hasText(result[2])) {
                    b = true;
                } else {
                    fromLocation = result[2];
                }
                if (!StringUtils.hasText(result[3])) {
                    b = true;
                } else {
                    toLocation = result[3];
                }
                if (!StringUtils.hasText(result[4])) {
                    b = true;
                } else {
                    upc = result[4];
                }
                quantity = Long.parseLong(result[5]);
                if (!StringUtils.hasText(result[6])) {
                    b = true;
                } else {
                    lineSequenceNo = result[6];
                }
                if (!StringUtils.hasText(result[7])) {
                    b = true;
                } else {
                    transferNo = result[7];
                }
                if (b) {
                    log.error("line data exists error");
                    throw new BusinessException("");
                }
                converseYxStockinDao.saveConverseYxStockinData(cartonNumber, receiveDate, fromLocation, toLocation, upc, quantity, lineSequenceNo, transferNo, -1L);
            }
            canMove = true;
        } catch (Exception e) {
            try {
                converseYxStockinDao.deleteNewData();
            } catch (Exception e1) {
                if (log.isErrorEnabled()) {
                    log.error("converseYxStockinDao.deleteNewData Exception:", e1);
                }
            }
            flag = false;
            canMove = false;
            log.error("", e);
        } finally {
            if (canMove) {
                flag = true;
                try {
                    converseYxStockinDao.updateNewDataCanUse();
                } catch (Exception e) {
                    flag = false;
                    log.error("", e);
                }
            }
        }
        return flag;
    }

    public void generateInboundStaCallBack(String slipCode, Long staId, Map<String, Long> lineDetial) {
        List<ConverseYxStockin> list = converseYxStockinDao.getByReferenceNo(slipCode);
        for (ConverseYxStockin cmd : list) {
            cmd.setStaId(staId);
        }
    }


    public List<String> generateInboundStaGetAllOrderCode() {
        return converseYxStockinDao.findAllDataNeedToCreate(new SingleColumnRowMapper<String>(String.class));
    }

    public Map<String, Long> generateInboundStaGetDetial(String slipCode) {
        List<ConverseYxStockin> list = converseYxStockinDao.getByReferenceNo(slipCode);
        Map<String, Long> detials = new HashMap<String, Long>();
        for (ConverseYxStockin cmd : list) {
            Long val = detials.get(cmd.getUpc());
            if (val == null) {
                detials.put(cmd.getUpc(), cmd.getQuantity());
            } else {
                detials.put(cmd.getUpc(), val + cmd.getQuantity());
            }
        }
        return detials;
    }

    public void generateInboundStaSetHead(String slipCode, StockTransApplication head) {
        ConverseYxStockin cs = converseYxStockinDao.getOneRecordBySlipCode(slipCode);
        head.setFromLocation(cs.getFromLocation());
        head.setToLocation(cs.getToLocation());
        head.setSlipCode1(cs.getTransferNo());
    }

    public void generateReceivingWhenInbound(StockTransApplication sta, StockTransVoucher stv) {
        if (sta != null && stv != null) {
            if (!sta.getVmiRCStatus()) {
                List<StaLine> staline = staLineDao.findByStaId(sta.getId());
                int i = 1;
                for (StaLine line : staline) {
                    ConverseYxReceive rec = new ConverseYxReceive();
                    rec.setCreateTime(new Date());
                    rec.setLastModifyTime(new Date());
                    rec.setStatus(DefaultStatus.CREATED);
                    rec.setTransferPrefix(sta.getFromLocation());
                    rec.setCartonNumber(sta.getRefSlipCode());
                    rec.setReceiveDate(FormatUtil.formatDate(new Date(), "yyyyMMdd"));
                    rec.setFromLocation(sta.getFromLocation());
                    rec.setToLocation(sta.getToLocation());
                    rec.setUpc(line.getSku().getExtensionCode2());
                    rec.setQuantity(line.getQuantity());
                    rec.setLineSequenceNo(new Long(i));
                    i++;
                    rec.setTransferNo(sta.getSlipCode1());
                    converseYxReceiveDao.save(rec);
                }
            }
            nikeStockReceiveDao.updateStatusByStaId(sta.getId(), 1);
        }
    }

    public void generateRtnWh(StockTransApplication sta) {
        if (sta == null) {
            return;
        }
        StockTransApplication sta1 = staDao.getByPrimaryKey(sta.getId());
        if (sta1 == null) {
            throw new BusinessException(ErrorCode.STA_IS_NULL);
        }
        converseYxTransferOutDao.saveRtnResult(sta1.getId());
    }

    /**
     * 生成SLIP_CODE
     */
    public String generateRtnStaSlipCode(String vmiCode, StockTransApplicationType type) {
        return String.valueOf(staDao.findConverseSEQ(new SingleColumnRowMapper<BigDecimal>(BigDecimal.class)));
    }

    /**
     * 创建VMI转店反馈数据
     * 
     * @param sta
     */
    public void generateReceivingTransfer(StockTransApplication stocktransapplication) {

    }

    /**
     * VMI入库收货反馈（作业单完成时反馈）
     * 
     * @param sta
     */
    @Override
    public void generateReceivingWhenFinished(StockTransApplication sta) {
        // DO NOTHING
    }



    @Override
    public void generateInboundSta() {

    }

    @Override
    public void generateReceivingWhenShelv(StockTransApplication sta, StockTransVoucher stv) {

    }

    @Override
    public void generateVMIReceiveInfoByInvCk(InventoryCheck inv) {
        // TODO Auto-generated method stub

    }

    @Override
    public void generateVMITranscationWH() {}

    @Override
    public void generateReceivingFlitting(StockTransApplication sta) {}

    @Override
    public void generateSkuByOrder(String orderName, String vmiCode) {}

    @Override
    public void generateInvStatusChange(StockTransApplication sta) {}

    @Override
    public void validateReturnManager(StockTransApplication sta) {}

    @Override
    public void generateInvStatusChangeByInboundSta(StockTransApplication sta) {}

    @Override
    public SkuCommand findSkuCommond(String skuCode) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> generateInboundStaGetAllOrderCodeDefault(String vmiCode, String vmisource, String asnType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Long> generateInboundStaGetDetialDefault(String slipCode, String vmisource, String asnType) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void generateInboundStaSetHeadDefault(String slipCode, String vmisource, StockTransApplication head) {}

    @Override
    public void generateInboundStaCallBackDefault(String slipCode, String orderCode, Long staId, BiChannelCommand b) {}

    @Override
    public void generateReceivingWhenClose(StockTransApplication sta) {}

    @Override
    public void loggerErrorMsg(Exception e) {}


}
