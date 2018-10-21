package com.jumbo.dao.vmi.af;

import java.util.Date;
import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.NativeUpdate;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.af.AFInvComReportCommand;
import com.jumbo.wms.model.vmi.af.AFInventoryCompareReport;
@Transactional
public interface AFInventoryCompareDao extends GenericEntityDao<AFInventoryCompareReport, Long> {
    
    @NativeQuery
    List<AFInvComReportCommand> findAFInventoryCompareData(@QueryParam("today") Date today,RowMapper<AFInvComReportCommand> orders);
    
    
    /**
     * 更新商品库存对比状态
     * 
     * @return
     */
    @NativeUpdate
    int updateAFInvComNorStatus(@QueryParam("status") String status,@QueryParam("today") Date today);
}
