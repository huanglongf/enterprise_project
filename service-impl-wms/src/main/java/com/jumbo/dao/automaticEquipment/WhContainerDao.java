package com.jumbo.dao.automaticEquipment;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.authorization.OperationUnit;
import com.jumbo.wms.model.automaticEquipment.WhContainer;
import com.jumbo.wms.model.automaticEquipment.WhContainerCommand;

/**
 * 出库周转箱DAO
 * 
 * @author jinlong.ke
 * @date 2016年2月26日下午5:18:36
 */
@Transactional
public interface WhContainerDao extends GenericEntityDao<WhContainer, Long> {
    @NamedQuery
    WhContainer getWhContainerByCode(@QueryParam("code") String code);

    @NativeQuery(pagable = true)
    Pagination<WhContainerCommand> getAllWhContainer(int start, int pageSize, @QueryParam("code") String cd, @QueryParam("plCode") String plCode, @QueryParam("status") Integer status, @QueryParam("ouId") Long ouId, Sort[] sorts,
            BeanPropertyRowMapper<WhContainerCommand> beanPropertyRowMapper);

    @NativeUpdate
    void resetWhContainerStatus(@QueryParam("tId") Long tId);

    /**
     * @param orderCode
     * @param beanPropertyRowMapper
     * @param ouId
     * @return
     */
    @NativeQuery
    WhContainer getCheckOrderByCode(@QueryParam("orderCode") String orderCode, @QueryParam("ouId") Long ouId, BeanPropertyRowMapper<WhContainer> beanPropertyRowMapper);

    /**
     * @param id
     * @return
     */
    @NamedQuery
    WhContainer getWcByPb(@QueryParam("pbId") Long id);

    /**
     * 根据二级批次ID获取
     * 
     * @param id
     * @return
     */
    @NamedQuery
    List<WhContainer> getWcByPbId(@QueryParam("pbId") Long id);

    /**
     * 根据波次ID和周转箱编码获取周转箱
     * 
     * @param plId
     * @param code
     * @return
     */
    @NamedQuery
    WhContainer getWcByPlIdAndCode(@QueryParam("plId") Long plId, @QueryParam("code") String code);

    /**
     * 根据批次号获取未完成的周转箱
     * 
     * @param pickId
     * @return
     */
    @NamedQuery
    List<WhContainer> getWcByPickId(@QueryParam("pickId") Long pickId);

    /**
     * 根据批次获取绑定的箱号
     * 
     * @param pickId
     * @return
     */
    @NamedQuery
    List<String> getWcByPickIdNoStatus(@QueryParam("pickId") Long pickId, SingleColumnRowMapper<String> r);


    /**
     * 获取已经绑定周转箱的批次
     * 
     * @return
     */
    @NativeQuery
    List<Long> findContainerPickId(@QueryParam("autoSeedGroup") String autoSeedGroup, SingleColumnRowMapper<Long> r);

    /**
     * 获取已经占用周转箱的组织
     * 
     * @param beanPropertyRowMapper
     * @return
     */
    @NativeQuery
    List<OperationUnit> findOccupyOu(BeanPropertyRowMapper<OperationUnit> beanPropertyRowMapper);
    
    /**
     * pda退仓绑定容器
     * @param pId
     * @param staId
     * @param diekingId
     * @param code
     */
    @NativeUpdate
    void bindingContainer(@QueryParam("pId")Long pId,@QueryParam("staId")Long staId,@QueryParam("diekingId")Long diekingId,@QueryParam("code")String code);
    
    @NamedQuery
    List<WhContainer> getWcByDekingId(@QueryParam("p3Id")Long p3Id);
    
    /**
     * 退仓拣货释放周转箱
     * @param pickingCode
     */
    @NativeUpdate
    void updateContainerByStaId(@QueryParam("staId")Long staId);
}
