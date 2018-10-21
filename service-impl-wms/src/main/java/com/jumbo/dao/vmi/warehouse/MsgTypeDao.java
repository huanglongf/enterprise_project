package com.jumbo.dao.vmi.warehouse;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;
import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import com.jumbo.dao.commandMapper.MapIdRowMapper;
import com.jumbo.wms.model.vmi.warehouse.MsgType;

@Transactional
public interface MsgTypeDao extends GenericEntityDao<MsgType, Long> {

    @NativeQuery
    String findTypeBySourceandType(@QueryParam("Source") String Source, @QueryParam("type") int type, RowMapper<String> rowmap);

    @NativeQuery
    Integer findTypeBySourceandType2(@QueryParam("Source") String Source, @QueryParam("type") String type, RowMapper<Integer> rowmap);

    @NativeQuery
    Map<Long, String> findTypeList(@QueryParam("source") String source, @QueryParam("type") String type, MapIdRowMapper rowmap);

    @NamedQuery
    List<MsgType> findTypeListBySource(@QueryParam("source") String source, @QueryParam("type") String type);

}
