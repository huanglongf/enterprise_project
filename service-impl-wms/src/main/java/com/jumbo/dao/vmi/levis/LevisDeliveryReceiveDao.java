package com.jumbo.dao.vmi.levis;

import java.util.List;

import loxia.annotation.NativeQuery;
import loxia.annotation.QueryParam;
import loxia.dao.GenericEntityDao;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.wms.model.command.vmi.levis.LevisDeliveryReceiveCommand;
import com.jumbo.wms.model.vmi.levisData.LevisDeliveryReceive;

@Transactional
public interface LevisDeliveryReceiveDao extends GenericEntityDao<LevisDeliveryReceive, Long> {

    @NativeQuery(model = LevisDeliveryReceiveCommand.class)
    List<LevisDeliveryReceiveCommand> findByDate(@QueryParam("date") String date);
}
