package com.jumbo.dao.pda;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.pda.PdaLocationType;
import com.jumbo.wms.model.pda.PdaLocationTypeBinding;
import com.jumbo.wms.model.pda.PdaLocationTypeCommand;


/**
 * 
 * @author lizaibiao
 * 
 */
@Transactional
public interface PdaLocationTypeDao extends GenericEntityDao<PdaLocationType, Long> {
    /**
     * 根据code获取 PdaLocationType实体
     */
    @NativeQuery(model = PdaLocationType.class)
    PdaLocationType getPdaLocationType(@QueryParam("ouId") Long ouId, @QueryParam("code") String code, @QueryParam("name") String name, RowMapper<PdaLocationType> r);

    /**
     * 根据code排除本身Id获取 PdaLocationType实体
     */
    @NativeQuery(model = PdaLocationType.class)
    PdaLocationType getPdaLocationTypeRemoveId(@QueryParam("ouId") Long ouId, @QueryParam("id") Long id, @QueryParam("code") String code, @QueryParam("name") String name, RowMapper<PdaLocationType> r);

    /**
     * 分页查询
     */
    @NativeQuery(pagable = true)
    Pagination<PdaLocationTypeCommand> getPdaLocationTypeByPage(int start, int pagesize, @QueryParam("ouId") long ouId, RowMapper<PdaLocationTypeCommand> r, Sort[] sorts);

    /**
     * 验证库位类型是否已经绑定库位
     */
    @NativeQuery(model = PdaLocationType.class)
    PdaLocationType verifyLocation(@QueryParam("id") Long id, RowMapper<PdaLocationType> r);

    /**
     * 验证库位类型是否已经绑定商品容量
     */
    @NativeQuery(model = PdaLocationType.class)
    PdaLocationType verifySkuCap(@QueryParam("id") Long id, RowMapper<PdaLocationType> r);

    /**
     * 查询LIST
     */
    @NativeQuery(model = PdaLocationType.class)
    List<PdaLocationType> getPdaLocationTypeList(@QueryParam("ouId") long ouId);

    /**
     * 分页查询（库位绑定）
     */
    @NativeQuery(pagable = true)
    Pagination<PdaLocationTypeCommand> getPdaLocationTypeBindingByPage(int start, int pagesize, @QueryParam("ouId") long ouId, @QueryParam("locationCode") String locationCode, @QueryParam("name") String name, @QueryParam("code") String code,
            RowMapper<PdaLocationTypeCommand> r, Sort[] sorts);



    /**
     * 查询库位类型（name,code,ouId)
     */
    @NativeQuery(model = PdaLocationType.class)
    PdaLocationType queryLocationTypeByNameCodeOuId(@QueryParam("code") String code, @QueryParam("name") String name, @QueryParam("ouId") Long ouId, @QueryParam("id") Long id, RowMapper<PdaLocationType> r);

    /**
     * 根据code获取 PdaLocationType实体是否已经绑定作业单基于ouId
     */
    @NativeQuery(model = PdaLocationTypeBinding.class)
    PdaLocationTypeBinding getPdaLocationTypeBinding(@QueryParam("locationCode") String locationCode, @QueryParam("code") String code, @QueryParam("ouId") Long ouId, RowMapper<PdaLocationTypeBinding> r);
}
