package com.jumbo.dao.automaticEquipment;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.automaticEquipment.MsgToWms;

/**
 * @author xiaolong.fei
 * @date 2016年6月18日下午8:45:47
 * 
 */
@Transactional
public interface MsgToWmsDao extends GenericEntityDao<MsgToWms, Long> {
    /**
     * 保存WCS 反馈消息
     * 
     * @param contexts
     * @param iType
     */
    @NativeUpdate
    Integer insertMsgToWms(@QueryParam(value = "contexts") String contexts, @QueryParam(value = "iType") Integer iType);

    @NativeQuery
    List<MsgToWms> findMsgBzDate(BeanPropertyRowMapper<MsgToWms> r);

    @NativeUpdate
    void updateBzStatusById(@QueryParam(value = "id") Long id);
}
