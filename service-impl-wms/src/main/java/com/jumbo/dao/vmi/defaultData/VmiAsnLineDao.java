package com.jumbo.dao.vmi.defaultData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.Default.VmiAsnLineCommand;
import com.jumbo.wms.model.vmi.Default.VmiAsnLineDefault;

@Transactional
public interface VmiAsnLineDao extends GenericEntityDao<VmiAsnLineDefault, Long> {

    @NativeQuery
    List<VmiAsnLineCommand> findVmiAsnLineList(@QueryParam("ordercode") String ordercode, @QueryParam("cartonno") String cartonno, RowMapper<VmiAsnLineCommand> rowMapper);

    @NativeUpdate
    void updateVmiAsnLineStatus(@QueryParam("asnid") Long asnid, @QueryParam("status") Integer status);

    @NativeQuery
    Integer findVmiAsnLineErrorCount(@QueryParam("asnid") Long asnid, SingleColumnRowMapper<Integer> s);

    @NativeQuery
    Integer findVmiAsnLineOkCount(@QueryParam("asnid") Long asnid, SingleColumnRowMapper<Integer> s);

    @NativeQuery
    VmiAsnLineCommand findVmiAsnLineByOrdercodeCartonnoUpc(@QueryParam("ordercode") String ordercode, @QueryParam("cartonno") String cartonno, @QueryParam("upc") String upc, RowMapper<VmiAsnLineCommand> rowMapper);

    @NativeQuery
    List<VmiAsnLineCommand> findVmiAsnLineListIgnoreStatus(@QueryParam("ordercode") String ordercode, @QueryParam("cartonno") String cartonno, RowMapper<VmiAsnLineCommand> rowMapper);


    @NativeQuery
    VmiAsnLineDefault findExtMemoByStaId(@QueryParam("staId") Long staId, @QueryParam("upc") String upc, RowMapper<VmiAsnLineDefault> r);
}
