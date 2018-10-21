package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.ImperfectStv;
import com.jumbo.wms.model.warehouse.ImperfectStvCommand;

@Transactional
public interface ImperfectStvDao extends GenericEntityDao<ImperfectStv, Long> {
    @NativeQuery
    public List<ImperfectStvCommand> findImperfectStvLine(@QueryParam(value = "staId") Long staId, RowMapper<ImperfectStvCommand> r);

    @NativeQuery
    public List<ImperfectStvCommand> findImperfectStvLineBarCode(@QueryParam(value = "staId") Long staId, @QueryParam(value = "barCode") String barCode, RowMapper<ImperfectStvCommand> r);
    @NativeQuery
    public ImperfectStvCommand findSkuImperfectStv(@QueryParam(value = "stvId") Long stvId, RowMapper<ImperfectStvCommand> r);

    @NamedQuery
    ImperfectStv findImperfectStv(@QueryParam(value = "defectCode") String defectCode);


    // @NamedQuery
    // SkuSn findSkuSnBySn(@QueryParam(value = "sn") String sn, @QueryParam(value = "ouid") Long
    // ouid, @QueryParam(value = "status") SkuSnStatus status);

}
