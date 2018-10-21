package com.jumbo.dao.vmi.cathKidstonData;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.dao.GenericEntityDao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.vmi.cathKidstonData.CKTransferOut;
import com.jumbo.wms.model.vmi.cathKidstonData.CKTransferOutCommand;

@Transactional
public interface CKTransferOutDao extends GenericEntityDao<CKTransferOut, Long> {

    @NativeQuery
    List<CKTransferOutCommand> findCKTransferOut(RowMapper<CKTransferOutCommand> rowMapper);
    
}
