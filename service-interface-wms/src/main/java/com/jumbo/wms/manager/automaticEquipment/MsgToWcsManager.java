package com.jumbo.wms.manager.automaticEquipment;

import java.util.List;
import java.util.Map;

import loxia.dao.Pagination;

import com.jumbo.wms.manager.BaseManager;
import com.jumbo.wms.model.automaticEquipment.MsgToWcs;
import com.jumbo.wms.model.automaticEquipment.MsgToWms;
import com.jumbo.wms.model.command.automaticEquipment.MsgToWcsCommand;

/**
 * WMS与WCS接口交互通用方法
 * 
 * @author jinlong.ke
 * @date 2016年6月7日下午2:40:43
 * 
 */
public interface MsgToWcsManager extends BaseManager {
    /* ------宝尊WMS系统服务接口------- */

    /**
     * 3.1.货箱流向取消回复结果
     */

    String cancelFlowConfirm(String context);

    /**
     * 3.2.货箱到达信息日志通知
     */

    String flowLog(String context);

    /**
     * 3.3.箱体到达指定位置确认
     */

    String arriveConfirm(String context);

    /**
     * 3.4.播种任务取消结果确认
     */

    String cancelSeedingConfirm(String context);

    /**
     * 3.5.播种任务执行结果确认
     */

    String seedingConfirm(String context);

    /**
     * 3.6.订单铺货货位匹配关系
     */

    String ordersDistribution(String context);

    /**
     * 3.7.播种任务执行明细
     */

    String seedingDetials(String context);

    /**
     * 3.8.设备状态回传
     */

    String equipmentStatus(String context);

    /**
     * 3.8.快递集货回传
     */

    String seedingTrackShipp(String context);

    /* ------WCS系统服务接口------- */

    /**
     * 4.1.收货入库货箱流向
     * 
     * @param context
     * @param msgId
     */
    void sShouRongQi(String context, Long msgId);

    /**
     * 4.2.收货入库货箱流向取消
     * 
     * @param context
     * @param msgId
     */
    void SQuxiaoRongQi(String context, Long msgId);

    /**
     * 4.3.播种任务
     * 
     * @param context
     * @param msgId
     */
    void SBoZhong(String context, Long msgId);

    /**
     * 4.4.出库拣完货货箱流向
     * 
     * @param context
     * @param msgId
     */
    void OShouRongQi(String context, Long msgId);

    /**
     * 4.5.货箱流向取消
     * 
     * @param context
     * @param msgId
     */
    void OQuxiaoRongQi(String context, Long msgId);

    /**
     * 4.6.集货
     * 
     * @param context
     * @param msgId
     */
    void SjiHuo(String context, Long msgId);


    /**
     * 4.7.播种取消
     * 
     * @param context
     * @param msgId
     */
    void OQuxiaoBoZhong(String context, Long msgId);

    /**
     * 查找消息
     * 
     * @param msgId
     * @return
     */
    MsgToWcs findMsgToWcsById(Long msgId);

    /**
     * 删除消息 并记录日志
     * 
     * @param msgId
     * @return
     */
    void deleteMsgToWcsById(Long msgId);

    /**
     * 保存
     * 
     * @param msgId
     * @return
     */
    void saveMsgToWcsById(MsgToWcs wcs);

    /**
     * 补偿机制
     * 
     * @return
     */
    List<MsgToWcsCommand> findWcsList();

    List<MsgToWms> findWmsBzList();

    void updateBzStatusById(Long id);

    /**
     * 方法说明：查询 errorCount(错误次数)大于1 MsgToWcs实体
     * 
     * @author LuYingMing
     * @date 2016年7月19日 下午5:36:52
     * @param start
     * @param pageSize
     * @param params
     * @return
     */
    public Pagination<MsgToWcsCommand> findMsgToWcsByParams(int start, int pageSize, Map<String, Object> params);

    /**
     * 方法说明：重置（修改error_count>1的数据为error_count=0）
     * 
     * @author LuYingMing
     * @date 2016年7月21日 上午10:06:32
     * @param id
     */
    public void resetErrorCount();

}
