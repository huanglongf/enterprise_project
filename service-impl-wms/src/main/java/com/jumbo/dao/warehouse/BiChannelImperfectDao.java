package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;

import com.jumbo.wms.model.warehouse.BiChannelImperfect;
import com.jumbo.wms.model.warehouse.BiChannelImperfectCommand;

public interface BiChannelImperfectDao extends GenericEntityDao<BiChannelImperfect, Long> {
    /**
     * 查询渠道原因列表
     * 
     * @param start
     * @param pagesize
     * @param cmpouid
     * @param name
     * @param r
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<BiChannelImperfectCommand> getBiChannelImperfectlList(int start, int pagesize, @QueryParam(value = "channelId") Long channelId, RowMapper<BiChannelImperfectCommand> r, Sort[] sorts);
    /**
     * 查询渠道原因列表
     * 
     * @param start
     * @param pagesize
     * @param cmpouid
     * @param name
     * @param r
     * @param sorts
     * @return
     */
    @NativeQuery(pagable = true)
    Pagination<BiChannelImperfectCommand> getBiChannelImperfectlListOuId(int start, int pagesize, @QueryParam(value = "ouId") Long ouId, RowMapper<BiChannelImperfectCommand> r, Sort[] sorts);

    @NativeQuery
    List<BiChannelImperfectCommand> findImperfect(@QueryParam(value = "channelId") Long channelId, RowMapper<BiChannelImperfectCommand> r);

    @NamedQuery
    BiChannelImperfect findImperfectCode(@QueryParam(value = "code") String code, @QueryParam(value = "channelId") Long channelId);
    @NamedQuery
    BiChannelImperfect findImperfectCodeOuId(@QueryParam(value = "code") String code, @QueryParam(value = "ouId") Long ouId);
    /**
     * 查询渠道原因列表
     * 
     * @param start
     * @param pagesize
     * @param cmpouid
     * @param name
     * @param r
     * @param sorts
     * @return
     */
    @NativeQuery
    List<BiChannelImperfectCommand> getBiChannelImperfectlOuId(@QueryParam(value = "ouId") Long ouId, RowMapper<BiChannelImperfectCommand> r);



}
