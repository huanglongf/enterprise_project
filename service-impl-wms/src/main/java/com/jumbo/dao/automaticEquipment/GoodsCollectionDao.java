package com.jumbo.dao.automaticEquipment;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.automaticEquipment.GoodsCollection;
import com.jumbo.wms.web.commond.GoodsCollectionCommand;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;



/**
 * @author hui.li
 * 
 */
@Transactional
public interface GoodsCollectionDao extends GenericEntityDao<GoodsCollection, Long> {

    @NativeQuery
    Long recommendCollectionCode(@QueryParam(value = "isAutoWh") String isAutoWh, @QueryParam(value = "ouId") Long ouId, SingleColumnRowMapper<Long> s);

    @NativeQuery
    String checkCollectionBox(@QueryParam(value = "code") String code, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery
    List<Long> findWaitBox(@QueryParam(value = "code") String code, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NamedQuery
    GoodsCollection getGoodsCollectionByCode(@QueryParam(value = "code") String code, @QueryParam(value = "ouId") Long ouId);

    @NamedQuery
    GoodsCollection findGoodsCollectionByCode(@QueryParam(value = "code") String code, @QueryParam(value = "ouId") Long ouId);

    @NamedQuery
    GoodsCollection getGoodsCollectionByPickingCode(@QueryParam(value = "pickingCode") String pickingCode);

    @NamedQuery
    List<GoodsCollection> querySortByOuId(@QueryParam(value = "ouId") Long ouId);

    @NativeQuery
    Long countCollectionByStatus(@QueryParam(value = "status") Integer status, @QueryParam(value = "ouId") Long ouId, SingleColumnRowMapper<Long> singleColumnRowMapper);

    @NativeQuery
    String recommendMoveCollectionCode(@QueryParam(value = "ouId") Long ouId, SingleColumnRowMapper<String> singleColumnRowMapper);

    @NativeQuery(pagable = true)
    Pagination<GoodsCollectionCommand> findShippingPointCollectionList(int start, int pageSize, @QueryParam(value = "ouId") Long ouId, @QueryParam(value = "collectionCode") String collectionCode, @QueryParam(value = "sort") String sort,
            @QueryParam(value = "popUpCode") String popUpCode, @QueryParam(value = "plCode") String plCode, @QueryParam(value = "container") String container,@QueryParam(value = "passWay") String passWay,@QueryParam(value = "pickModel") String pickModel, BeanPropertyRowMapper<GoodsCollectionCommand> r, Sort[] sorts);

    @NativeQuery
    GoodsCollection findGoodsCollectionByPlCodeSql(@QueryParam(value = "pickingCode") String pickingCode, BeanPropertyRowMapper<GoodsCollection> r);

    @NativeQuery
    GoodsCollection findGoodsCollectionByCodeSql(@QueryParam(value = "code") String code, @QueryParam(value = "ouId") Long ouId, BeanPropertyRowMapper<GoodsCollection> r);

    @NativeUpdate
    void resetGoodsCollectionById(@QueryParam(value = "id") Long id);

    @NativeUpdate
    void resetGoodsCollectionByPickingId(@QueryParam(value = "pId") Long pId);
	
	 /**
     * 集货库位看板
     * @param ouId
     * @param r
     * @return
     */
    @NativeQuery
    List<GoodsCollection> findGoodsCollection(@QueryParam(value = "ouId") Long ouId, BeanPropertyRowMapper<GoodsCollection> r);

    /**
     * 查询已经二次分拣完成的集货库位
     * 
     * @param ouId
     * @param r
     * @return
     */
    @NativeQuery
    List<GoodsCollectionCommand> findTwoPickingOver(@QueryParam(value = "ouId") Long ouId, BeanPropertyRowMapper<GoodsCollectionCommand> r);

    /**
     * 根据集货库位ID批次是否二次分拣完成
     * 
     * @param gcId
     * @param r
     * @return
     */
    @NativeQuery
    GoodsCollection findTwoPickingOverIsOver(@QueryParam(value = "gcId") Long gcId, BeanPropertyRowMapper<GoodsCollection> r);
	
}
