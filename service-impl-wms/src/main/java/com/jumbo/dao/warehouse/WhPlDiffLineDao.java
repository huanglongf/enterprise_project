package com.jumbo.dao.warehouse;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.warehouse.WhPlDiffLine;
import com.jumbo.wms.model.warehouse.WhPlDiffLineCommand;

@Transactional
public interface WhPlDiffLineDao extends GenericEntityDao<WhPlDiffLine, Long>{

    
    @NativeQuery
    List<WhPlDiffLineCommand> findStaLineByPickingId(@QueryParam(value = "plid") Long plid,@QueryParam(value = "status") int status,BeanPropertyRowMapperExt<WhPlDiffLineCommand> beanPropertyRowMapperExt);
    
    @NativeQuery
    List<WhPlDiffLineCommand> findWhPlDillLineByPidS(@QueryParam(value = "plid") Long plid,@QueryParam(value = "status") int status,BeanPropertyRowMapperExt<WhPlDiffLineCommand> beanPropertyRowMapperExt);
    
    @NativeQuery
    List<WhPlDiffLineCommand> findWhPlDillLineByPidSD(@QueryParam(value = "plid") Long plid,@QueryParam(value = "statusOne") int statusOne,@QueryParam(value = "statusTwo") int statusTwo,BeanPropertyRowMapperExt<WhPlDiffLineCommand> beanPropertyRowMapperExt);
    
    @NativeUpdate
    void deleteWhPlDillLineByPid(@QueryParam(value = "plid") Long plid,@QueryParam(value = "status") int status);
    
    @NativeUpdate
    void updateWhPlDillLineByPid(@QueryParam(value = "id") Long id,@QueryParam(value = "planQty") int planQty,@QueryParam(value = "qty") int qty);
    
}
