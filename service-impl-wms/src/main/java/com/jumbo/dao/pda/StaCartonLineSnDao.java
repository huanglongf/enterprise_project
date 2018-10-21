package com.jumbo.dao.pda;

import java.util.Date;
import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.mongodb.StaCartonLineSn;
import com.jumbo.wms.model.mongodb.StaCartonLineSnCommand;

@Transactional
public interface StaCartonLineSnDao extends GenericEntityDao<StaCartonLineSn, Long> {

    @NamedQuery
    List<StaCartonLineSn> getStaCartonLineSnByClId(@QueryParam("clId") Long clId);

    @NamedQuery
    StaCartonLineSn getStaCartonLineSnByCode(@QueryParam("dmgCode") String dmgCode);

    @NativeQuery
    List<StaCartonLineSnCommand> getSns(@QueryParam("cId") Long cId, @QueryParam("skuId") Long skuId, @QueryParam("expDate") Date expDate, RowMapper<StaCartonLineSnCommand> rowMapper);


    @NamedQuery
    StaCartonLineSn findSnbysn(@QueryParam("sn") String sn);
    //
    // @NativeQuery(model = PdaStaShelvesPlan.class)
    // List<PdaStaShelvesPlan> getByCid(@QueryParam("cId") long cId, RowMapper<PdaStaShelvesPlan>
    // rowMapper);

}
