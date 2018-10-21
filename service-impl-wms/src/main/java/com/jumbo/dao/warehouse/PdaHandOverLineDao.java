package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.pda.PdaHandOverLine;

@Transactional
public interface PdaHandOverLineDao extends GenericEntityDao<PdaHandOverLine, Long> {
    @NativeQuery
    List<String> getDetailList(@QueryParam("poId") Long id, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    List<PdaHandOverLine> findPdaHandOverLogLine(@QueryParam("pId") Long id, Sort[] sorts, BeanPropertyRowMapper<PdaHandOverLine> beanPropertyRowMapper);

}
