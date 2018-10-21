package com.jumbo.wms.manager.vmi.guess;

import java.math.BigDecimal;
import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.baozun.bh.connector.guess.model.GuessProductMaster;
import cn.baozun.bh.util.JSONUtil;

import com.jumbo.dao.vmi.guess.GuessEcomAdjDataDao;
import com.jumbo.dao.vmi.guess.GuessProductMasterDataDao;
import com.jumbo.dao.warehouse.InventoryCheckDao;
import com.jumbo.webservice.ids.manager.IdsManager;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.DefaultStatus;
import com.jumbo.wms.model.vmi.guess.GuessEcomAdjData;
import com.jumbo.wms.model.vmi.guess.GuessProductMasterData;
import com.jumbo.wms.model.warehouse.InventoryCheck;

@Service("guessManagerProxy")
public class GuessManagerProxyImpl extends BaseManagerImpl implements GuessManagerProxy {

    @Autowired
    private GuessProductMasterDataDao guessPMasterDatadao;

    @Autowired
    private GuessEcomAdjDataDao guessEcomAdjDataDao;

    @Autowired
    private InventoryCheckDao inventoryCheckDao;

    @Autowired
    private GuessManager guessmanager;

    @Autowired
    private IdsManager idsManager;
    /**
     * 
     */
    private static final long serialVersionUID = -5042035972348795957L;

    public void receiveProductMasterByMq(String message) {
        log.info("=========GUESS ProductMaster START===========");

        try {
            List<?> obj = JSONUtil.jsonToList(message);
            if (obj == null || obj.size() == 0) {
                log.info("**ProductMaster is null ");
                return;
            }

            for (int i = 0; i < obj.size(); i++) {
                GuessProductMaster m = JSONUtil.jsonToBean(JSONUtil.beanToJson(obj.get(i)), GuessProductMaster.class);

                GuessProductMasterData data = new GuessProductMasterData();
                data.setProductCode(m.getProductCode());
                data.setProductDesc(m.getProductDesc());
                data.setColorDesc(m.getColorDesc());
                data.setSizeDesc(m.getSizeDesc());
                data.setBarCode(m.getBarCode());
                data.setShortSku(m.getShortSku());
                data.setCnMsrp(new BigDecimal(m.getCnmsrp()));
                guessPMasterDatadao.save(data);
            }
            log.info("=========GUESS ProductMaster END===========");

        } catch (Exception e) {
            log.error("", e);
        }
    }

    public void receiveEcomAdj() {
        log.info("=========GUESS receiveEcomAdj START===========");
        List<GuessEcomAdjData> ecomAdjDatas = guessEcomAdjDataDao.findGuessEcomAdjDataDataListBystatus(new BeanPropertyRowMapperExt<GuessEcomAdjData>(GuessEcomAdjData.class));
        log.info("=========GUESS receiveEcomAdj SIZE:===========" + ecomAdjDatas.size());
        if (ecomAdjDatas.size() > 0) {
            for (GuessEcomAdjData ecomAdjData : ecomAdjDatas) {
                executeIDSInvAdjustADJ(ecomAdjData);
            }

        }
        log.info("=========GUESS receiveEcomAdj END===========");

    }


    public void executeIDSInvAdjustADJ(GuessEcomAdjData inv) {
        try {
            InventoryCheck checkinfoCheck = inventoryCheckDao.findinvCheckBySlipCode(inv.getAdjCode());
            if (checkinfoCheck != null) {
                guessEcomAdjDataDao.updateGuessEcomAdjDataByCode("", DefaultStatus.FINISHED.getValue(), inv.getAdjCode());
                return;
            }
            InventoryCheck check = guessmanager.vmiInvAdjustByWh(inv);
            if (check != null) {
                idsManager.executionVmiAdjustment(check);
                guessEcomAdjDataDao.updateGuessEcomAdjDataByCode(check.getCode(), DefaultStatus.FINISHED.getValue(), inv.getAdjCode());
            } else {
                log.debug("===============CREATE {} BOCSTOREREFERENCE ERROR================", new Object[] {inv.getAdjCode()});
                try {
                    throw new Exception("InventoryCheck create error" + inv.getAdjCode());
                } catch (Exception e) {
                    guessEcomAdjDataDao.updateGuessEcomAdjDataByCode("", DefaultStatus.ERROR.getValue(), inv.getAdjCode());
                    log.error("", e);
                }
            }
            guessEcomAdjDataDao.updateGuessEcomAdjDataByCode(check.getCode(), DefaultStatus.FINISHED.getValue(), inv.getAdjCode());
        } catch (Exception e) {
            guessEcomAdjDataDao.updateGuessEcomAdjDataByCode("", DefaultStatus.ERROR.getValue(), inv.getAdjCode());
            log.error("", e);

        }
    }



}
