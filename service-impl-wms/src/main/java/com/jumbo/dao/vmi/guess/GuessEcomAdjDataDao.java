package com.jumbo.dao.vmi.guess;

import java.util.List;

import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.guess.GuessEcomAdjData;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;

@Transactional
public interface GuessEcomAdjDataDao  extends GenericEntityDao<GuessEcomAdjData, Long>{

    @NativeQuery
    List<GuessEcomAdjData>  findGuessEcomAdjDataDataListBystatus(RowMapper<GuessEcomAdjData> rowMapper);
    
    @NativeQuery
    List<GuessEcomAdjData>  findGuessEcomAdjDataDataListByCode(@QueryParam("adjCode") String adjCode,RowMapper<GuessEcomAdjData> rowMapper);
    
    @NativeUpdate
    void updateGuessEcomAdjDataByCode(@QueryParam(value = "staCode") String staCode,@QueryParam(value = "status") int status,@QueryParam("adjCode")  String adjCode);
}
