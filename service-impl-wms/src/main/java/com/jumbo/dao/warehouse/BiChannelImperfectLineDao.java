package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;

import com.jumbo.wms.model.warehouse.BiChannelImperfectLine;
import com.jumbo.wms.model.warehouse.BiChannelImperfectLineCommand;

public interface BiChannelImperfectLineDao extends GenericEntityDao<BiChannelImperfectLine, Long> {
    /**
     * 查询渠道残次列表
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
    Pagination<BiChannelImperfectLineCommand> findBiChanneImperfectlLineList(int start, int pagesize,@QueryParam(value = "imperfectId") Long imperfectId, RowMapper<BiChannelImperfectLineCommand> r, Sort[] sorts);

    @NativeQuery
    List<BiChannelImperfectLineCommand> findImperfectLine(@QueryParam(value = "imperfectId") Long imperfectId, RowMapper<BiChannelImperfectLineCommand> r);
    @NamedQuery
    BiChannelImperfectLine findImperfectCode(@QueryParam(value = "code") String code, @QueryParam(value = "channelId") Long channelId);

}
