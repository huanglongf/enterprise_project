package com.jumbo.dao.vmi.guess;

import java.util.Date;
import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.guess.GuessSalesorderData;


@Transactional
public interface GuessSalesorderDataDao extends GenericEntityDao<GuessSalesorderData, Long>{

    @NativeQuery
	List<GuessSalesorderData> findGuessSalesorderDatas(RowMapper<GuessSalesorderData> rowMapper);
	
    @NativeUpdate
	void insertGuessReport(@QueryParam("today") Date today,@QueryParam("now") Date now);

	@NativeUpdate
	void updateGuessSalesorderinfoById(@QueryParam("gid") Long gid);
}
