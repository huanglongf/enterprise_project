package com.jumbo.dao.boc;

import java.util.List;

import loxia.annotation.NamedQuery;
import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.boc.VmiInventorySnapshotData;
import com.jumbo.wms.model.boc.VmiInventorySnapshotDataCommand;

@Transactional
public interface VmiInventorySnapshotDataDao extends GenericEntityDao<VmiInventorySnapshotData, Long> {
    
    @NamedQuery
    List<VmiInventorySnapshotData> findVmiInventorySnapshotData();
    
    @NativeUpdate
    void updateVmiInventorySnapshotStaCode(@QueryParam(value = "staCode") String staCode, @QueryParam(value = "vId") Long vId);
    
    @NativeUpdate
    void updateVmiInventorySnapshotStatus(@QueryParam(value = "status") int status, @QueryParam(value = "vId") Long vId);
    
    @NativeQuery
    List<VmiInventorySnapshotDataCommand> findVmiInventorySnapshotData(@QueryParam(value = "invOwner") String invOwner,@QueryParam(value = "fileName") String fileName, @QueryParam(value = "ouId") Long ouId,RowMapper<VmiInventorySnapshotDataCommand> orders);
    
    @NativeUpdate
    void updateInventorySnapshotByfileName(@QueryParam(value = "fileName") String fileName,@QueryParam(value = "status") int status);
    
    @NativeQuery
    List<VmiInventorySnapshotDataCommand> findGdvInventoryData(@QueryParam(value = "fileName") String fileName,@QueryParam(value = "ouid") Long ouid,@QueryParam(value = "source") String source,RowMapper<VmiInventorySnapshotDataCommand> orders);
 
    @NativeUpdate
    void updategdvVmiInventoryStaCode(@QueryParam(value = "staCode") String staCode,@QueryParam(value = "vmistatus") Long vmistatus, @QueryParam(value = "upc") String upc,@QueryParam(value = "fileName") String fileName,@QueryParam(value = "source") String source);
}
