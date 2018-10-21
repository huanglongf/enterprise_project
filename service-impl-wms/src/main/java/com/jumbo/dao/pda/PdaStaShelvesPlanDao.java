package com.jumbo.dao.pda;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.automaticEquipment.PopUpArea;
import com.jumbo.wms.model.pda.PdaStaShelvesPlan;
import com.jumbo.wms.model.pda.PdaStaShelvesPlanCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;


/**
 * 
 * @author lizaibiao
 * 
 */
@Transactional
public interface PdaStaShelvesPlanDao extends GenericEntityDao<PdaStaShelvesPlan, Long> {
    /**
     * 查询LIST
     */
    @NativeQuery(model = PdaStaShelvesPlan.class)
    List<PdaStaShelvesPlan> getByCid(@QueryParam("cId") long cId, RowMapper<PdaStaShelvesPlan> rowMapper);

    /**
     * 查询LIST
     */
    @NamedQuery
    List<PdaStaShelvesPlan> getByCidByName(@QueryParam("cId") long cId);

    /**
     * 查询LIST2
     */
    @NamedQuery
    List<PdaStaShelvesPlan> getByCidByName2(@QueryParam("cId") long cId);

    /**
     * PDA库位推荐
     * 
     * @param skuCode
     * @param skuSupplier
     * @param skuId
     * @param skuTypeId
     * @param owner
     * @param ouId
     * @param chaos
     * @param rowMapper
     * @return
     */
    @NativeQuery
    List<PdaStaShelvesPlanCommand> recommendLocation(@QueryParam("skuCode") String skuCode, @QueryParam("skuSupplier") String skuSupplier, @QueryParam("skuId") Long skuId, @QueryParam("skuTypeId") Long skuTypeId, @QueryParam("owner") String owner,
            @QueryParam("ouId") Long ouId, @QueryParam("chaos") Boolean chaos, @QueryParam("lineMin") Long lineMin, @QueryParam("lineMax") Long lineMax, RowMapper<PdaStaShelvesPlanCommand> rowMapper);

    @NativeQuery
    List<PopUpArea> findPopUpAreaByCarton(@QueryParam("cartonId") long cartonId, BeanPropertyRowMapper<PopUpArea> beanPropertyRowMapper);

    @NativeUpdate
    int updatePlanOver(@QueryParam("id") Long id, @QueryParam("staId") Long staId, @QueryParam("status") Long status);


    @NativeQuery
    List<PdaStaShelvesPlan> ckeckLocation(@QueryParam("locationId") Long locationId, @QueryParam("ouId") Long ouId, RowMapper<PdaStaShelvesPlan> rowMapper);


}
