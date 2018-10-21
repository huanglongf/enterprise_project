package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.MsgSkuUpdate;
import com.jumbo.wms.model.warehouse.MsgSkuUpdateCommand;

@Transactional
public interface MsgSkuUpdateDao extends GenericEntityDao<MsgSkuUpdate, Long> {

    /**
     * 获取所有execount<5的数据
     */
    @NativeQuery
    List<MsgSkuUpdateCommand> findMsgSkuUpdate(RowMapper<MsgSkuUpdateCommand> r);

    /**
     * 批量修改数据库execount<5的数据，全部+1
     */
    @NativeUpdate
    void updateMsgSkuExeCount();
}
