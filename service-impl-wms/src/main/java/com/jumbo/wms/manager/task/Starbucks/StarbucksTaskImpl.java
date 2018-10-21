package com.jumbo.wms.manager.task.Starbucks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.baozun.bizhub.manager.starbucks.StarbucksMsrCardManager;
import com.baozun.bizhub.model.starbucks.MsrCardCustom;
import com.baozun.bizhub.model.starbucks.MsrCardCustoms;
import com.baozun.task.annotation.SingleTaskLock;
import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.daemon.StarbucksTask;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.mongodb.MSRCustomCardInfo;
import com.jumbo.wms.model.warehouse.StockTransApplicationCommand;

import cn.baozun.bh.util.DateUtil;
import net.sf.json.JSONObject;

@Service("starbucksTask")
public class StarbucksTaskImpl extends BaseManagerImpl implements StarbucksTask {

    private static final long serialVersionUID = 6874000345582969141L;

    protected static final Logger log = LoggerFactory.getLogger(StarbucksTaskImpl.class);
    @Autowired
    private StockTransApplicationDao staDao;

    @Autowired
    private StarbucksMsrCardManager starbucksMsrCardManager;

    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;



    /**
     * 发送订制信息
     * 
     */
    @SingleTaskLock(timeout = TASK_LOCK_TIMEOUT, value = TASK_LOCK_VALUE)
    public void sendCardInfoToJBD() {
        MsrCardCustoms mccs = new MsrCardCustoms();
        List<MsrCardCustom> list = new ArrayList<MsrCardCustom>();
        mccs.setList(list);
        String date = DateUtil.formatDate(new Date(), DateUtil.PATTERN_SIMPLE);
        try {
            List<StockTransApplicationCommand> staList = staDao.findStarbucksCustom(new BeanPropertyRowMapper<StockTransApplicationCommand>(StockTransApplicationCommand.class));
            if (staList != null && staList.size() > 0) {
                for (StockTransApplicationCommand sta : staList) {
                    JSONObject json = JSONObject.fromObject(sta.getExtMemo());
                    MSRCustomCardInfo mcci = new MSRCustomCardInfo();
                    mcci.setDate(date);
                    mcci.setPickingListCode(sta.getPickingCode());
                    mcci.setOrderCode(sta.getSlipCode1());
                    mcci.setStaCode(sta.getCode());
                    mcci.setSku(sta.getSkuCode());
                    mcci.setQty(sta.getQty().toString());
                    mcci.setCreateDate(DateUtil.formatDate(new Date(), DateUtil.PATTERN_NORMAL));
                    mcci.setDrink(json.getString("Drink"));
                    mcci.setDecaf(json.getString("Decaf"));
                    mcci.setShots(json.getString("Shots"));
                    mcci.setSyrup(json.getString("Syrup"));
                    mcci.setMilk(json.getString("Milk"));
                    mcci.setCustom(json.getString("Custom"));
                    mcci.setSendStatus(10);

                    String infoCustomized = mcci.getCustom();
                    infoCustomized = infoCustomized.replaceAll("\\[|\\]|\"|'", "").replaceAll(",", " ");
                    mcci.setCustom(infoCustomized);

                    MsrCardCustom mcc = new MsrCardCustom();
                    list.add(mcc);
                    mcc.setPoNo(sta.getPickingCode());
                    mcc.setPlatformOrderId(sta.getSlipCode1());
                    mcc.setBzOrderId(sta.getCode());
                    mcc.setDate(date);
                    mcc.setSku(sta.getSkuCode());
                    mcc.setQty(sta.getQty().toString());
                    mcc.setDrink(mcci.getDrink());
                    mcc.setDecaf(mcci.getDecaf());
                    mcc.setShots(mcci.getShots());
                    mcc.setSyrup(mcci.getSyrup());
                    mcc.setMilk(mcci.getMilk());
                    mcc.setCustom(infoCustomized);

                    mongoOperation.save(mcci);
                }
                try {
                    MsrCardCustoms mcc = starbucksMsrCardManager.createCardCustomFile("4000105990", mccs);
                    if (mcc.isError()) {
                        log.error("星巴克订制信息HUB异常反馈", mcc.getErrMsg());
                        // 发送失败
                        // mongoOperation.updateFirst(new Query(Criteria.where("date").is(date)),
                        // new
                        // Update().set("sendStatus", 0), MSRCustomCardInfo.class);
                        mongoOperation.updateMulti(new Query(Criteria.where("date").is(date)), new Update().set("sendStatus", 0), MSRCustomCardInfo.class);
                    }
                } catch (Exception e) {
                    log.error("星巴克订制信息同步HUB异常", e);
                    mongoOperation.updateMulti(new Query(Criteria.where("date").is(date)), new Update().set("sendStatus", 0), MSRCustomCardInfo.class);
                }
            }
        } catch (Exception e) {
            log.error("封装星巴克订制卡信息异常：", e);
            mongoOperation.updateMulti(new Query(Criteria.where("date").is(date)), new Update().set("sendStatus", 0), MSRCustomCardInfo.class);
        }
    }

}
