package com.jumbo.wms.manager.inventorySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import com.baozun.scm.baseservice.message.common.MessageCommond;
import com.baozun.scm.baseservice.message.rocketmq.service.server.RocketMQProducerServer;
import com.baozun.utilities.json.JsonUtil;
import com.jumbo.dao.inventorySnapshot.InventorySnapShotDao;
import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.util.StringUtil;
import com.jumbo.util.UUIDUtil;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.inventorySnapShot.InvWhFullInventory;
import com.jumbo.wms.model.msg.MongoDBMessage;

@Service("inventorySnapshotManagerProxy")
public class InventorySnapshotManagerProxyImpl extends BaseManagerImpl implements InventorySnapshotManagerProxy {

    private static final long serialVersionUID = -8690969161925133327L;

    protected static final Logger logger = LoggerFactory.getLogger(InventorySnapshotManagerProxyImpl.class);

    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private InventorySnapshotManager inventorySnapshotManager;
    @Autowired
    private InventorySnapShotDao inventorySnapShotDao;
    @Autowired
    private RocketMQProducerServer producerServer;
    @Resource(name = "mongoTemplate")
    private MongoOperations mongoOperation;


    /***
     * 同步IM需要的库存信息 owner 店铺 skuCode 商品编码 ouCode 仓库编码 bin.hu
     */
    @Override
    public void InventorySnapshotToIm(String owner, List<String> skuCode, List<String> ouCode) {
        if (log.isInfoEnabled()) {
            log.info("owner: " + owner + " InventorySnapshotToIm start..........");
        }
        // 批次号
        String batch = UUID.randomUUID().toString();
        // 插入库存同步表
        inventoryDao.insertInventorySnapshot(owner, skuCode, ouCode, batch);
        Date date = new Date();
        while (true) {
            // 同归批次查询数据 每次查询50条 直到查询不到数据 查询没有同步的数据
            List<InvWhFullInventory> List = inventorySnapShotDao.findInvWhFullInventoryToIm(batch, new BeanPropertyRowMapper<InvWhFullInventory>(InvWhFullInventory.class));
            if (null != List && List.size() > 0) {
                // 有数据丢MQ
                MessageCommond mes = new MessageCommond();
                String msgBody = JsonUtil.writeValue(List);
                try {
                    mes.setMsgId(System.currentTimeMillis() + "" + UUIDUtil.getUUID());
                    mes.setIsMsgBodySend(false);
                    mes.setMsgType("InventorySnapshotManagerProxyImpl.InventorySnapshotToIm");
                    mes.setMsgBody(msgBody);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    mes.setSendTime(sdf.format(date));
                    producerServer.sendDataMsgConcurrently(MQ_WMS32IM_INVENTORY_SNAPSHOT, mes);
                } catch (Exception e) {
                    // 丢失败记录错误信息
                    logger.error("InventorySnapshotToIm producerServer.sendDataMsgConcurrently error owner: " + owner);
                    continue;
                }
                try {
                    // 保存进mongodb
                    MongoDBMessage mdbm = new MongoDBMessage();
                    BeanUtils.copyProperties(mes, mdbm);
                    mdbm.setStaCode(null);
                    mdbm.setOtherUniqueKey(null);
                    mdbm.setMsgBody(msgBody);
                    mdbm.setMemo(MQ_WMS32IM_INVENTORY_SNAPSHOT);
                    mongoOperation.save(mdbm);
                } catch (Exception e) {
                    // MongoDB失败记录日志
                    logger.error("InventorySnapshotToIm mongoOperation.save error owner: " + owner);
                }
                // 丢MQ成功 更新对应状态
                try {
                    inventorySnapshotManager.updateInventorySnapshotStatusById(List, 1);
                } catch (Exception e) {
                    // 报错记录LOG
                    logger.error("InventorySnapshotToIm updateInventorySnapshotStatusById error owner: " + owner);
                }
            } else {
                // 没有数据结束循环
                break;
            }
        }
        // 获取本批次成功同步汇总信息
        String success = inventorySnapShotDao.getInvWhFullInventorySuccessToIm(batch, new SingleColumnRowMapper<String>(String.class));
        if (!StringUtil.isEmpty(success)) {
            // 丢入汇总MQ
            MessageCommond mes = new MessageCommond();
            String msgBody = JsonUtil.writeValue(success);
            try {
                mes.setMsgId(System.currentTimeMillis() + "" + UUIDUtil.getUUID());
                mes.setIsMsgBodySend(false);
                mes.setMsgType("InventorySnapshotManagerProxyImpl.InventorySnapshotToIm.success");
                mes.setMsgBody(msgBody);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                mes.setSendTime(sdf.format(date));
                producerServer.sendDataMsgConcurrently(MQ_WMS32IM_INVENTORY_SNAPSHOT_SUCCESS, mes);
            } catch (Exception e) {
                // 丢失败记录错误信息
                logger.error("InventorySnapshotToIm producerServer.sendDataMsgConcurrently success error owner: " + owner);
            }
            try {
                // 保存进mongodb
                MongoDBMessage mdbm = new MongoDBMessage();
                BeanUtils.copyProperties(mes, mdbm);
                mdbm.setStaCode(null);
                mdbm.setOtherUniqueKey(null);
                mdbm.setMsgBody(msgBody);
                mdbm.setMemo(MQ_WMS32IM_INVENTORY_SNAPSHOT_SUCCESS);
                mongoOperation.save(mdbm);
            } catch (Exception e) {
                // MongoDB失败记录日志
                logger.error("InventorySnapshotToIm mongoOperation.save success error owner: " + owner);
            }
        }
        if (log.isInfoEnabled()) {
            log.info("owner: " + owner + " InventorySnapshotToIm end..........");
        }
    }
}
