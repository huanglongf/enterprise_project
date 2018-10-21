package com.jumbo.wms.manager.automaticEquipment;

import java.util.Date;
import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;
import loxia.dao.support.BeanPropertyRowMapperExt;
import loxia.support.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.automaticEquipment.MsgToWcsDao;
import com.jumbo.dao.automaticEquipment.MsgToWcsLogDao;
import com.jumbo.dao.automaticEquipment.MsgToWmsDao;
import com.jumbo.dao.warehouse.PickingListDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.automaticEquipment.MsgToWcs;
import com.jumbo.wms.model.automaticEquipment.MsgToWcsResponse;
import com.jumbo.wms.model.automaticEquipment.MsgToWms;
import com.jumbo.wms.model.automaticEquipment.WcsInterfaceType;
import com.jumbo.wms.model.command.automaticEquipment.MsgToWcsCommand;
import com.jumbo.wms.model.warehouse.PickingList;

/**
 * @author jinlong.ke
 * @date 2016年6月7日下午2:43:30
 * 
 */
@Transactional
@Service("msgToWcsManager")
public class MsgToWcsManagerImpl extends BaseManagerImpl implements MsgToWcsManager {

    protected static final Logger log = LoggerFactory.getLogger(MsgToWcsManagerImpl.class);
    /**
     * 
     */
    private static final long serialVersionUID = 6650439937297696964L;
    @Autowired
    private MsgToWcsLogDao msgToWcsLogDao;
    @Autowired
    private MsgToWcsDao msgToWcsDao;
    @Autowired
    private MsgToWmsDao msgToWmsDao;
    @Autowired
    private PickingListDao pickingListDao;

    /**
     * 3.1.货箱流向取消回复结果
     */
    public String cancelFlowConfirm(String context) {
        log.info("cancelFlowConfirm:" + context);
        String resposeJson = ""; // 返回的JSON
        Integer count = msgToWmsDao.insertMsgToWms(context, WcsInterfaceType.HXQX.getValue());
        MsgToWcsResponse response = new MsgToWcsResponse();
        if (count == 1) {
            response.setStatus("1");
        } else {
            response.setStatus("0");
            response.setMsg("WMS系统异常");
        }
        resposeJson = net.sf.json.JSONObject.fromObject(response).toString();
        return resposeJson;
    }

    /**
     * 3.2.货箱到达信息日志通知
     */
    public String flowLog(String context) {
        log.info("flowLog:" + context);
        String resposeJson = ""; // 返回的JSON
        Integer count = msgToWmsDao.insertMsgToWms(context, WcsInterfaceType.HXDDRZ.getValue());
        MsgToWcsResponse response = new MsgToWcsResponse();
        if (count == 1) {
            response.setStatus("1");
        } else {
            response.setStatus("0");
            response.setMsg("WMS系统异常");
        }
        resposeJson = net.sf.json.JSONObject.fromObject(response).toString();
        return resposeJson;
    }

    /**
     * 3.3.箱体到达指定位置确认
     */
    public String arriveConfirm(String context) {
        log.info("arriveConfirm:" + context);
        String resposeJson = ""; // 返回的JSON
        Integer count = msgToWmsDao.insertMsgToWms(context, WcsInterfaceType.HXDDQR.getValue());
        MsgToWcsResponse response = new MsgToWcsResponse();
        if (count == 1) {
            response.setStatus("1");
        } else {
            response.setStatus("0");
            response.setMsg("WMS系统异常");
        }
        resposeJson = net.sf.json.JSONObject.fromObject(response).toString();
        return resposeJson;
    }

    /**
     * 3.4.播种任务取消结果确认
     */
    public String cancelSeedingConfirm(String context) {
        log.info("cancelSeedingConfirm:" + context);
        String resposeJson = ""; // 返回的JSON
        Integer count = msgToWmsDao.insertMsgToWms(context, WcsInterfaceType.BZQXQR.getValue());
        MsgToWcsResponse response = new MsgToWcsResponse();
        if (count == 1) {
            response.setStatus("1");
        } else {
            response.setStatus("0");
            response.setMsg("WMS系统异常");
        }
        resposeJson = net.sf.json.JSONObject.fromObject(response).toString();
        return resposeJson;
    }

    /**
     * 3.5.播种任务执行结果确认
     */
    public String seedingConfirm(String context) {
        log.info("seedingConfirm:" + context);
        String resposeJson = ""; // 返回的JSON
        // Integer count = msgToWmsDao.insertMsgToWms(context, WcsInterfaceType.BZZXQR.getValue());
        MsgToWcsResponse response = new MsgToWcsResponse();
        // 记录日志信息
        try {
            JSONObject jo = new JSONObject(context);
            String o = (String) jo.get("pickingCode");
            if (o != null && !"".equals(o)) {
                PickingList p = pickingListDao.getByCode(o);
                MsgToWms s = new MsgToWms();
                s.setContext(context);
                s.setErrorCount(0);
                s.setPkId(p.getId());
                s.setStatus(1);
                s.setType(WcsInterfaceType.BZZXQR);
                s.setCreateTime(new Date());
                msgToWmsDao.save(s);
            }
            response.setStatus("1");
        } catch (Exception e) {
            response.setStatus("0");
            response.setMsg("系统异常");
            log.error("seedingConfirm error ...", e);
        }
        resposeJson = net.sf.json.JSONObject.fromObject(response).toString();
        return resposeJson;
    }


    /**
     * 3.6.订单铺货货位匹配关系
     */
    public String ordersDistribution(String context) {
        log.info("ordersDistribution:" + context);
        String resposeJson = ""; // 返回的JSON
        Integer count = msgToWmsDao.insertMsgToWms(context, WcsInterfaceType.DDPH.getValue());
        MsgToWcsResponse response = new MsgToWcsResponse();
        if (count == 1) {
            response.setStatus("1");
        } else {
            response.setStatus("0");
            response.setMsg("WMS系统异常");
        }
        resposeJson = net.sf.json.JSONObject.fromObject(response).toString();
        return resposeJson;
    }

    /**
     * 3.7.播种任务执行明细
     */
    public String seedingDetials(String context) {
        log.info("seedingDetials:" + context);
        String resposeJson = ""; // 返回的JSON
        Integer count = msgToWmsDao.insertMsgToWms(context, WcsInterfaceType.BZZXMX.getValue());
        MsgToWcsResponse response = new MsgToWcsResponse();
        if (count == 1) {
            response.setStatus("1");
        } else {
            response.setStatus("0");
            response.setMsg("WMS系统异常");
        }
        resposeJson = net.sf.json.JSONObject.fromObject(response).toString();
        return resposeJson;
    }


    /**
     * 3.8.设备状态回传
     */
    public String equipmentStatus(String context) {
        log.info("equipmentStatus:" + context);
        String resposeJson = ""; // 返回的JSON
        Integer count = msgToWmsDao.insertMsgToWms(context, WcsInterfaceType.SBZTHC.getValue());
        MsgToWcsResponse response = new MsgToWcsResponse();
        if (count == 1) {
            response.setStatus("1");
        } else {
            response.setStatus("0");
            response.setMsg("WMS系统异常");
        }
        resposeJson = net.sf.json.JSONObject.fromObject(response).toString();
        return resposeJson;
    }

    /**
     * 3.9.快递集货日志回传
     */
    public String seedingTrackShipp(String context) {
        log.info("seedingTrackShipp:" + context);
        String resposeJson = ""; // 返回的JSON
        Integer count = msgToWmsDao.insertMsgToWms(context, WcsInterfaceType.JHRZHC.getValue());
        MsgToWcsResponse response = new MsgToWcsResponse();
        if (count == 1) {
            response.setStatus("1");
        } else {
            response.setStatus("0");
            response.setMsg("WMS系统异常");
        }
        resposeJson = net.sf.json.JSONObject.fromObject(response).toString();
        return resposeJson;
    }


    /*-----------------------分隔线-------------------------------*/
    /**
     * 4.1.收货入库货箱流向
     */
    public void sShouRongQi(String context, Long msgId) {
        // TODO Auto-generated method stub
    }



    /**
     * 4.2.收货入库货箱流向取消
     * 
     * @param context
     * @param msgId
     */
    public void SQuxiaoRongQi(String context, Long msgId) {
        // TODO Auto-generated method stub

    }


    /**
     * 4.3.播种任务
     * 
     * @param context
     * @param msgId
     */
    public void SBoZhong(String context, Long msgId) {
        // TODO Auto-generated method stub

    }

    /**
     * 4.4.出库拣完货货箱流向
     * 
     * @param context
     * @param msgId
     */
    public void OShouRongQi(String context, Long msgId) {
        // TODO Auto-generated method stub

    }

    /**
     * 4.5.货箱流向取消
     * 
     * @param context
     * @param msgId
     */
    public void OQuxiaoRongQi(String context, Long msgId) {
        // TODO Auto-generated method stub

    }

    /**
     * 4.6.集货接口
     */
    public void SjiHuo(String context, Long msgId) {
        // TODO 调用hub接口
        // 调用成功后，删除信息记录日志
        Integer num = msgToWcsLogDao.insertMsgToWcsLog(msgId);
        if (num == 1) {
            msgToWcsDao.deleteByPrimaryKey(msgId);
        }

    }

    /**
     * 4.7.播种取消
     * 
     * @param context
     * @param msgId
     */
    public void OQuxiaoBoZhong(String context, Long msgId) {
        // TODO Auto-generated method stub

    }

    /**
     * 查找消息
     */
    public MsgToWcs findMsgToWcsById(Long msgId) {
        return msgToWcsDao.getByPrimaryKey(msgId);
    }

    /**
     * 删除消息
     */
    public void deleteMsgToWcsById(Long msgId) {
        Integer num = msgToWcsLogDao.insertMsgToWcsLog(msgId);
        if (num == 1) {
            msgToWcsDao.deleteByPrimaryKey(msgId);
        }
    }

    /**
     * 保存消息
     */
    public void saveMsgToWcsById(MsgToWcs wcs) {
        msgToWcsDao.save(wcs);
    }

    /**
     * 查询2000单通知WCS
     * 
     * @return
     */
    public List<MsgToWcsCommand> findWcsList() {
        return msgToWcsDao.findWcsList(new BeanPropertyRowMapperExt<MsgToWcsCommand>(MsgToWcsCommand.class));
    }

    /**
     * 查找播种确认数据
     */
    public List<MsgToWms> findWmsBzList() {
        return msgToWmsDao.findMsgBzDate(new BeanPropertyRowMapper<MsgToWms>(MsgToWms.class));
    }

    public void updateBzStatusById(Long id) {
        msgToWmsDao.updateBzStatusById(id);
    }

    /**
     * @author LuYingMing
     * @date 2016年7月19日 下午5:37:58
     * @see com.jumbo.wms.manager.automaticEquipment.MsgToWcsManager#findMsgToWcsByParams(int, int,
     *      java.util.Map)
     */
    @Override
    public Pagination<MsgToWcsCommand> findMsgToWcsByParams(int start, int pageSize, Map<String, Object> params) {
        return msgToWcsDao.findMsgToWcsByParams(start, pageSize, params, new BeanPropertyRowMapper<MsgToWcsCommand>(MsgToWcsCommand.class));
    }

    /**
     * @author LuYingMing
     * @date 2016年7月21日 上午10:07:51
     * @see com.jumbo.wms.manager.automaticEquipment.MsgToWcsManager#resetErrorCount(java.lang.Long)
     */
    @Override
    public void resetErrorCount() {
        msgToWcsDao.resetErrorCount();
    }
}
