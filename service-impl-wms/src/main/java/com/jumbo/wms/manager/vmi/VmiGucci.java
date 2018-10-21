package com.jumbo.wms.manager.vmi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.vmi.defaultData.VmiAsnDao;
import com.jumbo.dao.vmi.defaultData.VmiAsnLineDao;
import com.jumbo.dao.warehouse.StaLineDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.Constants;
import com.jumbo.wms.manager.vmi.gucciData.GucciManager;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.vmi.Default.AsnOrderType;
import com.jumbo.wms.model.vmi.Default.VmiAsnCommand;
import com.jumbo.wms.model.vmi.Default.VmiAsnDefault;
import com.jumbo.wms.model.vmi.Default.VmiAsnLineCommand;
import com.jumbo.wms.model.vmi.Default.VmiAsnLineDefault;
import com.jumbo.wms.model.vmi.Default.VmiGeneralStatus;
import com.jumbo.wms.model.warehouse.BiChannelCommand;
import com.jumbo.wms.model.warehouse.InventoryCheck;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationType;
import com.jumbo.wms.model.warehouse.StockTransVoucher;

@Transactional
public class VmiGucci extends VmiBaseBrand {

    private static final long serialVersionUID = -57836685610078921L;
    protected static final Logger log = LoggerFactory.getLogger(VmiGucci.class);
    private static final String invName = "良品";

    @Autowired
    private VmiAsnDao vmiAsnDao;
    @Autowired
    private VmiAsnLineDao vmiAsnLineDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaLineDao staLineDao;
    // @Autowired
    // private StockTransVoucherDao stvDao;
    // @Autowired
    // public StvLineDao stvLineDao;
    // @Autowired
    // private GucciVMIInstructionDao gucciVMIInstructionDao;
    // @Autowired
    // private GucciVMIInstructionLineDao gucciVMIInstructionLineDao;
    @Autowired
    private GucciManager gucciManager;

    /**
     * 按单或按箱得到相应数据
     * 
     * @param vmiCode
     * @param vmiSource
     * @param asnType
     */
    public List<String> generateInboundStaGetAllOrderCodeDefault(String vmiCode, String vmiSource, String asnType) {
        List<String> s = null;
        /**
         * 如果是按单code[0]和code[1]=收货单号 如果是按箱code[0]=箱号 code[1]=收货单号
         */
        if (asnType.equals(String.valueOf(AsnOrderType.TYPEONE.getValue()))) {
            // 按单创建
            s = vmiAsnDao.findVmiAsnByType1(vmiCode, vmiSource, new SingleColumnRowMapper<String>(String.class));
        }
        if (asnType.equals(String.valueOf(AsnOrderType.TYPETWO.getValue()))) {
            // 按箱
            s = vmiAsnDao.findVmiAsnByType2(vmiCode, vmiSource, new SingleColumnRowMapper<String>(String.class));
        }
        return s;
    }

    /**
     * 创建入库作业单-设置作业单头信息
     * 
     * @param slipCode
     * @param head
     */
    @Override
    public void generateInboundStaSetHeadDefault(String slipCode, String vmisource, StockTransApplication head) {
        // 分解slipCode 分辨按单按箱
        String[] code = slipCode.split(",");
        /**
         * 如果是按单code[0]和code[1]=收货单号 如果是按箱code[0]=箱号 code[1]=收货单号
         */
        List<VmiAsnCommand> list = vmiAsnDao.findVmiAsnList(code[1].trim(), vmisource, new BeanPropertyRowMapper<VmiAsnCommand>(VmiAsnCommand.class));
        if (list.size() > 0) {
            head.setFromLocation(list.get(0).getFromLocation());
            head.setToLocation(list.get(0).getToLoaction());
            if (!code[0].equals(code[1])) {
                // 按箱
                head.setSlipCode1(code[1]);
            }
            // Gucci定制
            if (code[0].equals(code[1])) {
                // 按单
                head.setSlipCode1(code[1]);
            }
        }
    }

    /**
     * 创建入库作业单--根据唯一编码获取一单的入库单数据
     * 
     * @param slipCode
     * @return key:sku extcode2,value:qty
     */
    @Override
    public Map<String, Long> generateInboundStaGetDetialDefault(String slipCode, String vmisource, String asnType) {
        // 分解slipCode 分辨按单按箱
        String[] code = slipCode.split(",");
        Map<String, Long> detials = new HashMap<String, Long>();
        List<VmiAsnLineCommand> vList = null;
        /**
         * 如果是按单code[0]和code[1]=收货单号 如果是按箱code[0]=箱号 code[1]=收货单号
         */
        if (asnType.equals(String.valueOf(AsnOrderType.TYPEONE.getValue()))) {
            // 按单创建
            vList = vmiAsnLineDao.findVmiAsnLineList(code[1], null, new BeanPropertyRowMapper<VmiAsnLineCommand>(VmiAsnLineCommand.class));
            for (VmiAsnLineCommand v : vList) {
                Long val = detials.get(v.getUpc());
                if (val == null) {
                    detials.put(v.getUpc(), v.getQty());
                } else {
                    detials.put(v.getUpc(), val + v.getQty());
                }
            }
        }
        if (asnType.equals(String.valueOf(AsnOrderType.TYPETWO.getValue()))) {
            // 按箱创建
            vList = vmiAsnLineDao.findVmiAsnLineList(code[1], code[0], new BeanPropertyRowMapper<VmiAsnLineCommand>(VmiAsnLineCommand.class));
            for (VmiAsnLineCommand v : vList) {
                Long val = detials.get(v.getUpc());
                if (val == null) {
                    detials.put(v.getUpc(), v.getQty());
                } else {
                    detials.put(v.getUpc(), val + v.getQty());
                }
            }
        }
        return detials;
    }

    /**
     * 根据中间表创建入库单据（ 已修改为使用通用方法）
     */
    @Override
    public void generateInboundSta() {
        // List<GucciVMIInstruction> vmiInstructions =
        // gucciVMIInstructionDao.findInstructionsByStatus(0);
        // if (vmiInstructions == null || vmiInstructions.isEmpty()) {
        // return;
        // }
        // for (GucciVMIInstruction vmiInstruction : vmiInstructions) {
        // if (!StringUtils.isEmpty(vmiInstruction.getJDADocumentNumber())) {
        // try {
        // gucciManager.generateStaByJDADocumentNo(vmiInstruction.getJDADocumentNumber());
        // } catch (Exception e) {
        // log.error("gucci入库单创建失败！JDADocumentNumber=" + vmiInstruction.getJDADocumentNumber(), e);
        // Integer count = vmiInstruction.getErrCount();
        // if (count == null) {
        // count = 0;
        // }
        // count++;
        // if (count >= 3) {
        // vmiInstruction.setStatus(-1);
        // } else {
        // vmiInstruction.setStatus(0);
        // }
        // vmiInstruction.setErrCount(count);
        // gucciVMIInstructionDao.save(vmiInstruction);
        // }
        // }
        // }
    }

    /**
     * 创建入库作业单-成功后回调方法
     */
    @Override
    public void generateInboundStaCallBackDefault(String slipCode, String orderCode, Long staId, BiChannelCommand shop) {
        /**
         * 判断按单按箱 如果是按单直接更改vmiasn和vmiasnline状态为10 并把sta关联vmiasn 如果是按箱只修改vmiasnLine状态为10
         * 并把sta关联vmiasnLine
         */
        String asnType = shop.getAsnTypeString();
        List<VmiAsnLineCommand> vList = null;
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        /**
         * 如果是按单code[0]和code[1]=收货单号 如果是按箱code[0]=箱号 code[1]=收货单号
         */
        if (asnType.equals(String.valueOf(AsnOrderType.TYPEONE.getValue()))) {
            // 按单
            vList = vmiAsnLineDao.findVmiAsnLineList(orderCode, null, new BeanPropertyRowMapper<VmiAsnLineCommand>(VmiAsnLineCommand.class));
            VmiAsnDefault v = vmiAsnDao.getByPrimaryKey(vList.get(0).getaId());
            v.setStaId(sta);
            v.setFinishTime(new Date());
            v.setStatus(VmiGeneralStatus.FINISHED);
            vmiAsnDao.save(v);
            for (VmiAsnLineCommand vl : vList) {
                VmiAsnLineDefault avd = vmiAsnLineDao.getByPrimaryKey(vl.getId());
                avd.setStatus(VmiGeneralStatus.FINISHED);
                vmiAsnLineDao.save(avd);
            }
            // Gucci定制信息
            sta.setSystemKey(Constants.GUCCI_SYSTEM_KEY);
            if (v != null) {
                sta.setExtMemo(v.getExtMemo());
            }
            DateFormat df = new SimpleDateFormat("yyyyMMdd");
            Date arriveTime = null;
            try {
                arriveTime = df.parse(v.getArriveDate());
            } catch (Exception e) {
                log.error("Gucci入库时间解析异常！", e);
            }
            sta.setArriveTime(arriveTime);
        }
        if (asnType.equals(String.valueOf(AsnOrderType.TYPETWO.getValue()))) {
            // 按箱
            vList = vmiAsnLineDao.findVmiAsnLineList(orderCode, slipCode, new BeanPropertyRowMapper<VmiAsnLineCommand>(VmiAsnLineCommand.class));
            for (VmiAsnLineCommand v : vList) {
                VmiAsnLineDefault avd = vmiAsnLineDao.getByPrimaryKey(v.getId());
                avd.setStaId(sta);
                avd.setStatus(VmiGeneralStatus.FINISHED);
                vmiAsnLineDao.save(avd);
                vmiAsnLineDao.flush();
                // Gucci定制信息
                VmiAsnDefault v2 = vmiAsnDao.getByPrimaryKey(v.getaId());
                sta.setSystemKey(Constants.GUCCI_SYSTEM_KEY);
                if (v2 != null) {
                    sta.setExtMemo(v2.getExtMemo());
                }
                DateFormat df = new SimpleDateFormat("yyyyMMdd");
                Date arriveTime = null;
                try {
                    arriveTime = df.parse(v2.getArriveDate());
                } catch (Exception e) {
                    log.error("Gucci入库时间解析异常！", e);
                }
                sta.setArriveTime(arriveTime);
                // try {
                // JSONObject obj = new JSONObject(v.getExtMemo());
                // String lineNo = obj.get("lineNo").toString();
                // List<StaLine> staLines = staLineDao.findByStaId(sta.getId());
                // // Gucci按箱创单,明细行只有一条,staLine上加上入库单对应行号
                // staLines.get(0).setOrderLineNo(lineNo);
                // staLineDao.save(staLines.get(0));
                // } catch (Exception e) {
                // log.error("Gucci明细行添加失败！staCode=" + sta.getCode());
                // }
            }
        }
    }

    @Override
    public void generateReceivingWhenInbound(StockTransApplication sta, StockTransVoucher stv) {
        // DO NOTHING
    }

    @Override
    public void generateReceivingWhenShelv(StockTransApplication sta, StockTransVoucher stv) {
        // DO NOTHING
    }

    @Override
    public void generateReceivingWhenFinished(StockTransApplication sta) {
        // DO NOTHING
    }

    /**
     * VMI入库单关闭反馈
     * 
     * @param sta
     */
    @Override
    public void generateReceivingWhenClose(StockTransApplication sta) {
        gucciManager.generateInBoundFeedbackDate(sta);
    }

    /**
     * 退大仓反馈
     */
    @Override
    public void generateRtnWh(StockTransApplication sta) {
        // 未使用通用接口
        gucciManager.generateVmiRtnFeedbackDate(sta);
    }


    @Override
    public void generateVMIReceiveInfoByInvCk(InventoryCheck inv) {
        // DO NOTHING
    }

    @Override
    public void generateReceivingTransfer(StockTransApplication sta) {
        // DO NOTHING
    }

    @Override
    public SkuCommand findSkuCommond(String skuCode) {
        return null;
    }

    @Override
    public String generateRtnStaSlipCode(String vmiCode, StockTransApplicationType type) {
        return null;
    }

}
