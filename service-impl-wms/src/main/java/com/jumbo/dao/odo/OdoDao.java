package com.jumbo.dao.odo;

import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.odo.Odo;
import com.jumbo.wms.model.odo.OdoCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;


@Transactional
public interface OdoDao extends GenericEntityDao<Odo, Long> {


    @NamedQuery
    public List<Odo> findCreateInBoundStaList();

    @NamedQuery
    public Odo findOdOByCode(@QueryParam("code") String code);

    @NativeUpdate
    void updateOdoStatus(@QueryParam("code") String code, @QueryParam("status") int status);


    @NativeQuery(pagable = true)
    Pagination<OdoCommand> findOdOAllQuery(int start, int pageSize, @QueryParam("code") String code, @QueryParam("ouName") String ouName, @QueryParam("inOuName") String inOuName, @QueryParam("ownerName") String ownerName,
            @QueryParam("status") Integer status, Sort[] sorts, RowMapper<OdoCommand> rowMapper);


    @NativeQuery
    Boolean findOdoLineByOdoId(@QueryParam("id") String id, SingleColumnRowMapper<Boolean> r);


    @NativeUpdate
    void deleteOdoLineByOdoId(@QueryParam("odoId") Long odoId);

   
}
