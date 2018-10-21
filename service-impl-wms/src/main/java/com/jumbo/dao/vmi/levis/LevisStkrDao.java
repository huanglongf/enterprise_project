package com.jumbo.dao.vmi.levis;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.vmi.levis.LevisStkrCommand;
import com.jumbo.wms.model.vmi.levisData.LevisStkr;

@Transactional
public interface LevisStkrDao extends GenericEntityDao<LevisStkr, Long> {

    @NativeQuery
    List<LevisStkrCommand> findAll(RowMapper<LevisStkrCommand> r);
}
