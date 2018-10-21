package com.jumbo.wms.manager.print;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import loxia.dao.support.BeanPropertyRowMapperExt;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.jumbo.dao.baseinfo.TransportatorDao;
import com.jumbo.dao.baseinfo.WarehouseDao;
import com.jumbo.dao.warehouse.StaDeliveryInfoDao;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.dao.warehouse.WhInfoTimeRefDao;
import com.jumbo.dao.warehouse.WhTransAreaNoDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.manager.expressDelivery.ChannelInsuranceManager;
import com.jumbo.wms.manager.expressDelivery.TransManagerImpl;
import com.jumbo.wms.model.baseinfo.Transportator;
import com.jumbo.wms.model.baseinfo.Warehouse;
import com.jumbo.wms.model.warehouse.StaDeliveryInfoCommand;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefBillType;
import com.jumbo.wms.model.warehouse.WhInfoTimeRefNodeType;
import com.jumbo.wms.model.warehouse.WhTransAreaNo;

@Service("staPrintManager")
public class StaPrintManagerImpl extends BasePrintManagerImpl implements StaPrintManager {

    private static final long serialVersionUID = 479146527835361802L;
    @Autowired
    private TransportatorDao transportatorDao;
    @Autowired
    private WarehouseDao warehouseDao;
    @Autowired
    private StockTransApplicationDao staDao;
    @Autowired
    private StaDeliveryInfoDao staDeliveryInfoDao;
    @Autowired
    private ChannelInsuranceManager channelInsuranceManager;
    @Autowired
    private WhTransAreaNoDao transAreaNoDao;
    @Autowired
    private WhInfoTimeRefDao whInfoTimeRefDao;

    /**
     * 按配货清单连打面单
     * 
     * @param pickingListId
     * @param isOline 是否指定面单类型，null系统判断,true电子面单,false标准面单
     * @return
     */
    // public List<JasperPrint> printExpressBillByPickingList(Long pickingListId, Boolean isOline,
    // Long userId) {
    // PickingList p = pickingListDao.getByPrimaryKey(pickingListId);
    // if (p == null) {
    // throw new BusinessException(ErrorCode.SYSTEM_ERROR);
    // }
    // List<JasperPrint> list = new ArrayList<JasperPrint>();
    // List<Long> staList = staDao.findAllStaByPickingList(p.getId(), new
    // SingleColumnRowMapper<Long>(Long.class));
    // for (Long id : staList) {
    // JasperPrint jp = printExpressBillBySta(id, null, userId);
    // list.add(jp);
    // }
    // return list;
    // }

    // public JasperPrint printExpressBillByPickingListLpCode(Long pickingListId, Boolean isOline,
    // Long userId) {
    // PickingList p = pickingListDao.getByPrimaryKey(pickingListId);
    // if (p == null) {
    // throw new BusinessException(ErrorCode.SYSTEM_ERROR);
    // }
    // List<StaDeliveryInfoCommand> datas =
    // staDeliveryInfoDao.findPrintExpressBillData(pickingListId, null, new
    // BeanPropertyRowMapperExt<StaDeliveryInfoCommand>(StaDeliveryInfoCommand.class));
    // for (StaDeliveryInfoCommand command : datas) {
    // command.setTransTimeTypeName(chooseOptionDao.findAllOptionListByOptionKey(command.getTransTimeTypeB(),
    // "transTimeType", new SingleColumnRowMapper<String>(String.class)));
    // command.setTransTypeName(chooseOptionDao.findAllOptionListByOptionKey(command.getTransTypeB(),
    // "transType", new SingleColumnRowMapper<String>(String.class)));
    // }
    // return setExpressBillData(p.getCode(), p.getLpcode(),
    // WhInfoTimeRefBillType.STA_PICKING.getValue(), WhInfoTimeRefNodeType.PRINT_TRANK.getValue(),
    // p.getWarehouse().getId(), userId, datas, isOline);
    // }

    /**
     * 按作业单打面单
     * 
     * @param isOline 是否指定面单类型，null系统判断,true电子面单,false标准面单
     * @param staId
     * @return
     */
    public JasperPrint printExpressBillBySta(Long staId, Boolean isOline, Long userId) {
        StockTransApplication sta = staDao.getByPrimaryKey(staId);
        if (sta == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        List<StaDeliveryInfoCommand> datas = staDeliveryInfoDao.findPrintExpressBillData(false, null, staId, new BeanPropertyRowMapperExt<StaDeliveryInfoCommand>(StaDeliveryInfoCommand.class));
        return setExpressBillData(sta.getRefSlipCode(), sta.getStaDeliveryInfo().getLpCode(), WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.PRINT_TRANK.getValue(), sta.getMainWarehouse().getId(), userId, datas, isOline);
    }

    /**
     * 按作业单、运单号打面单
     * 
     * @param isOline 是否指定面单类型，null系统判断,true电子面单,false标准面单
     * @param staId
     * @return
     */
    // public JasperPrint printExpressBillByTrankNo(Long staId, Boolean isOline, String packId, Long
    // userId) {
    // StockTransApplication sta = staDao.getByPrimaryKey(staId);
    // if (sta == null) {
    // throw new BusinessException(ErrorCode.SYSTEM_ERROR);
    // }
    // List<StaDeliveryInfoCommand> datas = staDeliveryInfoDao.findPrintExpressBillData2(null,
    // staId, packId, new
    // BeanPropertyRowMapperExt<StaDeliveryInfoCommand>(StaDeliveryInfoCommand.class));
    // return setExpressBillData(sta.getRefSlipCode(), sta.getStaDeliveryInfo().getLpCode(),
    // WhInfoTimeRefBillType.STA.getValue(), WhInfoTimeRefNodeType.PRINT_TRANK.getValue(),
    // sta.getMainWarehouse().getId(), userId, datas, isOline);
    // }



    /**
     * 模板数据特殊处理
     * 
     * @param lpcode
     * @param whOuId
     * @param datas
     * @param isOline 是否指定面单类型，null系统判断,true电子面单,false标准面单
     * @return
     */
    private JasperPrint setExpressBillData(String slipCode, String lpcode, int billType, int nodeType, Long whOuId, Long userId, List<StaDeliveryInfoCommand> datas, Boolean isOline) {
        if (datas == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        // 查询模板
        Transportator trans = transportatorDao.findByCode(lpcode);
        Warehouse wh = warehouseDao.getByOuId(whOuId);
        if (wh == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        }
        String jpPath = trans.getJasperNormal();
        if (isOline == null) {
            // 顺丰电子面单判断
            if (wh.getIsSfOlOrder() != null && wh.getIsSfOlOrder() && (Transportator.SF.equals(lpcode) || Transportator.SFCOD.equals(lpcode) || Transportator.SFDSTH.equals(lpcode))) {
                if (wh.getIsSfOlOrder() != null && wh.getIsSfOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            } else if (wh.getIsOlSto() != null && wh.getIsOlSto() && (Transportator.STO.equals(lpcode))) {
                if (wh.getIsOlSto() != null && wh.getIsOlSto()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
            // Zto电子面单判断
            if (wh.getIsZtoOlOrder() != null && wh.getIsZtoOlOrder() && (Transportator.ZTOOD.equals(lpcode) || Transportator.ZTO.equals(lpcode))) {
                if (wh.getIsZtoOlOrder() != null && wh.getIsZtoOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
            // EMS电子面单判断
            if (wh.getIsEmsOlOrder() != null && wh.getIsEmsOlOrder() && (Transportator.EMS.equals(lpcode) || Transportator.EMS_COD.equals(lpcode))) {
                if (wh.getIsEmsOlOrder() != null && wh.getIsEmsOlOrder()) {
                    jpPath = trans.getJasperOnLine();
                }
            }
        } else if (isOline != null && isOline) {
            // 使用电子免单
            jpPath = trans.getJasperOnLine();
        } else {
            // 使用普通免单
            jpPath = trans.getJasperNormal();
        }
        // STO电子面单增加省份编码
        HashMap<String, List<WhTransAreaNo>> transAreaCache = TransManagerImpl.transAreaCache;
        if (wh.getIsOlSto() != null && wh.getIsOlSto() && (Transportator.STO.equals(lpcode))) {
            if (transAreaCache.isEmpty()) {
                List<WhTransAreaNo> list = transAreaNoDao.getTransAreaByLpcode(lpcode, new BeanPropertyRowMapperExt<WhTransAreaNo>(WhTransAreaNo.class));
                transAreaCache.put(lpcode, list);
            }
        }
        for (StaDeliveryInfoCommand cmd : datas) {
            String sn = cmd.getQuantity();
            cmd.setQuantity("商品" + sn + "件");
            // 匹配物流,增加省份编码
            if (!transAreaCache.isEmpty() && wh.getIsOlSto() != null && wh.getIsOlSto() && (Transportator.STO.equals(lpcode))) {
                Set<String> keys = transAreaCache.keySet();
                Iterator<String> iterator = keys.iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    List<WhTransAreaNo> list = transAreaCache.get(key);
                    for (WhTransAreaNo whTransAreaNo : list) {
                        if (whTransAreaNo.getProvince().contains(cmd.getProvince()) || cmd.getProvince().contains(whTransAreaNo.getProvince())) {
                            cmd.setAreaNumber(whTransAreaNo.getAreaNumber());
                        }
                    }
                }
            }
            // 打字处理
            if (cmd.getBigAddress() != null) {
                if (cmd.getBigAddress().endsWith("-")) {
                    cmd.setBigAddress(cmd.getBigAddress().substring(0, cmd.getBigAddress().length() - 1));
                }
            }
            // SF电子面单电话号码屏蔽后5位
            if (wh.getIsSfOlOrder() != null && wh.getIsSfOlOrder() && (Transportator.SF.equals(lpcode) || Transportator.SFCOD.equals(lpcode) || Transportator.SFDSTH.equals(lpcode))) {
                // cmd.setMobile(FormatUtil.shieldinMobile(cmd.getMobile()));
                // cmd.setTelephone(FormatUtil.shieldinPhoneNumber(cmd.getTelephone()));
                cmd.setLogoImg(Transportator.SF_LOGO_IMAGE);
            } else if (wh.getIsOlSto() != null && wh.getIsOlSto() && (Transportator.STO.equals(lpcode))) {
                cmd.setLogoImg(Transportator.STO_LOGO_IMAGE);
                cmd.setQuantity("商品" + sn + "件");
                // wh.getIsZtoOlOrder() != null && wh.getIsZtoOlOrder() &&
            } else if ((Transportator.ZTO.equals(lpcode))) {
                cmd.setLogoImg(Transportator.ZTO_LOGO_IMAGE);
            }
            // 设置中文金额
            if (cmd.getAmount() != null) {
                // cmd.setStrAmount(formatIntToChinaBigNumStr(cmd.getAmount().intValue()));
                cmd.setStrAmount(StringUtil.getCHSNumber(cmd.getAmount().intValue() + ".0"));
            }
            // 设置详细地址,出去重复省市区，如果不存在则拼接
            String address = cmd.getAddress();
            address = StringUtil.getRealAddress(address, cmd.getProvince(), cmd.getCity(), cmd.getDistrict(), true);
            address += "                                                                                                                #";
            cmd.setAddress(address);
            // 设置发运地
            if (StringUtils.hasText(wh.getDeparture())) {
                cmd.setDeparture(wh.getDeparture());
            }
            // 设置保价金额
            BigDecimal insurance = channelInsuranceManager.getInsurance(cmd.getChannelCode(), cmd.getInsuranceAmount());
            if (null != insurance)
                cmd.setInsuranceAmount(insurance);// 保价
            else
                cmd.setInsuranceAmount(null);// 非保价

            // 拼接配货信息
            String[] staBarCode = cmd.getBarCode().split(",");
            String[] staQuentity = cmd.getStaQuantity().split(",");
            String skuList = "";
            if (staBarCode.length > 6) {
                for (int i = 0; i < 6; i++) {
                    if (i == 5) {
                        skuList = skuList + staBarCode[i] + "【" + staQuentity[i] + "】...";
                    } else {
                        skuList = skuList + staBarCode[i] + "【" + staQuentity[i] + "】/";
                    }
                }
            } else {
                for (int i = 0; i < staBarCode.length; i++) {
                    if (i == staBarCode.length - 1) {
                        skuList = skuList + staBarCode[i] + "【" + staQuentity[i] + "】";
                    } else {
                        skuList = skuList + staBarCode[i] + "【" + staQuentity[i] + "】/";
                    }
                }
            }
            cmd.setSkuList(skuList);
        }
        if (!StringUtil.isEmpty(slipCode)) {
            whInfoTimeRefDao.insertWhInfoTime2(slipCode, billType, nodeType, userId, whOuId);
        }
        // 填写模板数据 jasperprint/ZTO.jasper
        return this.getJasperPrint(datas, jpPath);
    }

}
