package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.baseinfo.SkuWarehouseRef;
import com.jumbo.wms.model.command.SkuWarehouseRefCommand;

@Transactional
public interface SkuWarehouseRefDao extends GenericEntityDao<SkuWarehouseRef, Long> {

    /**
     * 获取所有品牌
     */
    @NativeQuery(pagable = true)
    Pagination<SkuWarehouseRefCommand> findSkuWarehouseRefListAll(int start, int size, @QueryParam(value = "brandId") Long brandId, @QueryParam(value = "source") String source, @QueryParam(value = "sourcewh") String sourcewh,
            @QueryParam(value = "channelId") Long channelId, RowMapper<SkuWarehouseRefCommand> rowMapper, Sort[] sorts);

    @NativeQuery
    SkuWarehouseRefCommand findSkuWarehouseRefList(@QueryParam(value = "sourcewh") String sourcewh, RowMapper<SkuWarehouseRefCommand> rowMap);

    @NativeQuery
    SkuWarehouseRefCommand findChannelBySourceWh(@QueryParam(value = "sourcewh") String sourcewh, RowMapper<SkuWarehouseRefCommand> rowMap);

    /**
     * 仓库下拉框数据品牌
     * 
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<SkuWarehouseRefCommand> findByBrandName(RowMapper<SkuWarehouseRefCommand> rowMap);

    /**
     * 仓库下拉框数据品牌
     * 
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<SkuWarehouseRefCommand> findByChannelName(RowMapper<SkuWarehouseRefCommand> rowMap);

    /**
     * 加载 品牌下拉列表 用于新建
     * 
     * @param rowMap
     * @return
     */
    @NativeQuery
    List<SkuWarehouseRefCommand> findBrandNameAll(RowMapper<SkuWarehouseRefCommand> rowMap);

    /**
     * 删除外包仓品牌与仓库关联数据
     * 
     * @param branName
     * @param source
     * @param warehouseName
     */
    @NativeUpdate
    void deleteSkuWarehouseRef(@QueryParam(value = "brandId") Long brandId, @QueryParam(value = "source") String source, @QueryParam(value = "sourcewh") String sourcewh, @QueryParam("channelId") Long channelId);

    /**
     * 插入外包仓品牌与仓库关联数据
     * 
     * @param brandId
     * @param source
     * @param whid
     */
    @NativeUpdate
    void insertSkuWarehouseRef(@QueryParam(value = "brandId") Long brandId, @QueryParam(value = "source") String source, @QueryParam(value = "sourcewh") String sourcewh, @QueryParam(value = "channelId") Long channelId);

    /**
     * 根据品牌查找外包仓
     * 
     * @return
     */
    @NativeQuery
    List<SkuWarehouseRefCommand> findRefByBand(@QueryParam(value = "brandId") Long brandId, RowMapper<SkuWarehouseRefCommand> rowMap);


    /**
     * 查询物流宝店铺
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findSkuRefBinnael(@QueryParam(value = "source") String source, SingleColumnRowMapper<Long> singleColumnRowMapper);

    /**
     * 查询物流宝店铺
     * 
     * @param singleColumnRowMapper
     * @return
     */
    @NativeQuery
    List<Long> findBrandByRefSource(@QueryParam(value = "source") String source, SingleColumnRowMapper<Long> singleColumnRowMapper);
}
