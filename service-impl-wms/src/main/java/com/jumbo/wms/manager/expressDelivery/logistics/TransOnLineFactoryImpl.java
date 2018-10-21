package com.jumbo.wms.manager.expressDelivery.logistics;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.system.ChooseOptionDao;
import com.jumbo.dao.warehouse.TransInfoDao;
import com.jumbo.util.TimeHashMap;
import com.jumbo.wms.Constants;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.system.ChooseOption;

@Service("transOnLineFactory")
public class TransOnLineFactoryImpl implements TransOnLineFactory {

    @Resource(name = "transOlSf")
    private TransOlInterface sf;
    @Resource(name = "transOlZto")
    private TransOlInterface zto;
    @Resource(name = "transOlSto")
    private TransOlInterface sto;
    @Resource(name = "transOlJd")
    private TransOlInterface jd;
    @Resource(name = "transOlEms")
    private TransOlInterface ems;
    @Resource(name = "transOlTtk")
    private TransOlInterface ttk;
    @Resource(name = "transOlYto")
    private TransOlInterface yto;
    @Resource(name = "transOlDhl")
    private TransOlInterface dhl;
    @Resource(name = "transOlWX")
    private TransOlInterface WX;
    @Resource(name = "transOlKerry")
    private TransOlInterface kerry;
    @Resource(name = "transOlCxc")
    private TransOlInterface CXC;
    @Resource(name = "transOlRfd")
    private TransOlInterface rfd;
    @Resource(name = "transOlDefault")
    private TransOlInterface def;
    @Resource(name = "transOlCNP")
    private TransOlInterface cnp;
    @Resource(name = "transOlCnjh")
    private TransOlInterface cnjh;
    @Resource(name = "transOlYamato")
    private TransOlInterface ymt;
    @Resource(name = "transOlLogistics")
    private TransOlInterface logistics;
    @Resource(name = "transOlCnyz")
    private TransOlInterface cnyz;

    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private TransInfoDao transInfoDao;
    @Autowired
    private ChooseOptionDao chooseOptionDao;
    /**
     * 匹配物流缓存
     */
    TimeHashMap<String, TransOlInterface> transCache = new TimeHashMap<String, TransOlInterface>();

    /**
     * 快递服务配置缓存
     */
    TimeHashMap<String, TransOlInterface> transLpCodeCache = new TimeHashMap<String, TransOlInterface>();

    /**
     * 菜鸟云栈配置缓存
     */
    TimeHashMap<String, String> caiNiaoLpCodeCache = new TimeHashMap<String, String>();


    @Override
    public TransOlInterface getTransOnLine(String lpcode, Long whOuId) {
        Warehouse wh = warehouseDao.getByOuId(whOuId);
        if (wh == null) {
            return null;
        }

        // 菜鸟云栈
        if (caiNiaoLpCodeCache.get("reset") == null) {
            List<ChooseOption> option = chooseOptionDao.findAllOptionListByCategoryCode(Constants.ALI_WAYBILL);
            for (ChooseOption co : option) {
                if (Constants.ALI_WAYBILL_SWITCH_ON.equals(co.getOptionValue())) {
                    caiNiaoLpCodeCache.put(co.getOptionKey(), "1");
                }
            }
            caiNiaoLpCodeCache.put("reset", "1");
        }
        String transOlCaiNiao = caiNiaoLpCodeCache.get(lpcode);
        if (transOlCaiNiao != null) {
            return cnyz;
        }
        Boolean isNew = false;
        // 快递服务逻辑
        TransOlInterface transOlLogis = transLpCodeCache.get(lpcode);
        if (transOlLogis == null) {
            List<ChooseOption> ChooseOptionList = chooseOptionDao.findListByCategoryCodeAndKey("logisticsCode", "1"); // 1是开，0是关
            for (ChooseOption choose : ChooseOptionList) {
                transLpCodeCache.put(choose.getOptionValue(), logistics);
            }
            transOlLogis = transLpCodeCache.get(lpcode);
            if (transOlLogis != null) {
                isNew = true;
            }
        } else {
            transOlLogis = transLpCodeCache.get(lpcode);
            if (transOlLogis != null) {
                isNew = true;
            }
        }
        // 快递服务逻辑 end
        if (wh.getIsSfOlOrder() != null && wh.getIsSfOlOrder() && (Transportator.SF.equals(lpcode) || Transportator.SFCOD.equals(lpcode) || Transportator.SFDSTH.equals(lpcode))) {
            if (isNew) {
                return transOlLogis;
            }
            return sf;
        } else if (wh.getIsZtoOlOrder() != null && wh.getIsZtoOlOrder() && Transportator.ZTO.equals(lpcode)) {
            if (isNew) {
                return transOlLogis;
            }
            return zto;
        } else if (wh.getIsOlSto() != null && wh.getIsOlSto() && Transportator.STO.equals(lpcode)) {
            if (isNew) {
                return transOlLogis;
            }
            return sto;
        } else if (Transportator.JD.equals(lpcode) || Transportator.JDCOD.equals(lpcode)) {
            return jd;
        } else if (wh.getIsEmsOlOrder() != null && wh.getIsEmsOlOrder() && (Transportator.EMS.equals(lpcode) || Transportator.EMS_COD.equals(lpcode))) {
            if (isNew) {
                return transOlLogis;
            }
            return ems;
        } else if (null != wh.getIsTtkOlOrder() && wh.getIsTtkOlOrder() && Transportator.TTKDEX.equals(lpcode)) {
            return ttk;
        } else if (wh.getIsYtoOlOrder() != null && wh.getIsYtoOlOrder() && (Transportator.YTO.equals(lpcode) || Transportator.YTOCOD.equals(lpcode))) {
            if (isNew) {
                return transOlLogis;
            }
            return yto;
        } else if (wh.getIsDhlOlOrder() != null && wh.getIsDhlOlOrder() && (Transportator.DHL.equals(lpcode))) {
            return dhl;
        } else if (Transportator.KERRY_A.equals(lpcode)) {
            return kerry;
        } else if (Transportator.CXC.equals(lpcode)) {
            return CXC;
        } else if (wh.getIsRfdOlOrder() != null && wh.getIsRfdOlOrder() && Transportator.RFD.equals(lpcode)) {
            return rfd;
        } else if (Transportator.CNP.equals(lpcode)) {
            return cnp;
        } else if (Transportator.CNJH.equals(lpcode)) {
            return cnjh;
        } else if (Transportator.ZJB.equals(lpcode)) {
            return ymt;
        }
        // 标准物流对接
        TransOlInterface transOl = transCache.get(lpcode);
        if (transOl == null) {
            List<String> lpCodeList = transInfoDao.getAllLpCode(new SingleColumnRowMapper<String>(String.class));
            for (String transCode : lpCodeList) {
                transCache.put(transCode, def);
            }
            transOl = transCache.get(lpcode);
        }
        return transOl;
    }

    @Override
    public TransOlInterface getTransOnLineForFillInInvoice(String lpcode) {
        // 快递服务逻辑
        TransOlInterface transOlLogis = transLpCodeCache.get(lpcode);
        if (transOlLogis == null) {
            List<ChooseOption> ChooseOptionList = chooseOptionDao.findListByCategoryCodeAndKey("logisticsCode", "1"); // 1是开，0是关
            for (ChooseOption choose : ChooseOptionList) {
                transLpCodeCache.put(choose.getOptionValue(), logistics);
            }
            transOlLogis = transLpCodeCache.get(lpcode);
            if (transOlLogis != null) {
                return transOlLogis;
            }
        } else {
            transOlLogis = transLpCodeCache.get(lpcode);
            if (transOlLogis != null) {
                return transOlLogis;
            }
        }
        if (Transportator.SF.equals(lpcode) || Transportator.SFCOD.equals(lpcode) || Transportator.SFDSTH.equals(lpcode)) {
            return sf;
        } else if (Transportator.EMS.equals(lpcode) || Transportator.EMS_COD.equals(lpcode)) {
            return ems;
        } else if (Transportator.YTO.equals(lpcode) || Transportator.YTOCOD.equals(lpcode)) {
            return yto;
        } else if (Transportator.STO.equals(lpcode)) {
            return sto;
        }
        return null;
    }

    @Override
    public TransOlInterface getTransOnLine1(String lpcode, Long whOuId) {
        Warehouse wh = warehouseDao.getByOuId(whOuId);
        if (wh == null) {
            return null;
        }
        Boolean isNew = false;
        // 快递服务逻辑
        TransOlInterface transOlLogis = transLpCodeCache.get(lpcode);
        if (transOlLogis == null) {
            List<ChooseOption> ChooseOptionList = chooseOptionDao.findListByCategoryCodeAndKey("logisticsCode", "1"); // 1是开，0是关
            for (ChooseOption choose : ChooseOptionList) {
                transLpCodeCache.put(choose.getOptionValue(), logistics);
            }
            transOlLogis = transLpCodeCache.get(lpcode);
            if (transOlLogis != null) {
                isNew = true;
            }
        } else {
            transOlLogis = transLpCodeCache.get(lpcode);
            if (transOlLogis != null) {
                isNew = true;
            }
        }
        if (wh.getIsSfOlOrder() != null && (Transportator.SF.equals(lpcode) || Transportator.SFCOD.equals(lpcode) || Transportator.SFDSTH.equals(lpcode))) {
            if (isNew) {
                return transOlLogis;
            }
            return sf;
        } else if (wh.getIsZtoOlOrder() != null && Transportator.ZTO.equals(lpcode)) {
            if (isNew) {
                return transOlLogis;
            }
            return zto;
        } else if (wh.getIsOlSto() != null && Transportator.STO.equals(lpcode)) {
            if (isNew) {
                return transOlLogis;
            }
            return sto;
        } else if (Transportator.JD.equals(lpcode) || Transportator.JDCOD.equals(lpcode)) {
            return jd;
        } else if (wh.getIsEmsOlOrder() != null && (Transportator.EMS.equals(lpcode) || Transportator.EMS_COD.equals(lpcode))) {
            if (isNew) {
                return transOlLogis;
            }
            return ems;
        } else if (wh.getIsYtoOlOrder() != null && (Transportator.YTO.equals(lpcode) || Transportator.YTOCOD.equals(lpcode))) {
            if (isNew) {
                return transOlLogis;
            }
            return yto;
        }
        // 标准物流对接
        TransOlInterface transOl = transCache.get(lpcode);
        if (transOl == null) {
            List<String> lpCodeList = transInfoDao.getAllLpCode(new SingleColumnRowMapper<String>(String.class));
            for (String transCode : lpCodeList) {
                transCache.put(transCode, def);
            }
            transOl = transCache.get(lpcode);
        }
        return transOl;
    }
}
