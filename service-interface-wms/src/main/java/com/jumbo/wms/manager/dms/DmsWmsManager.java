package com.jumbo.wms.manager.dms;

import com.jumbo.wms.model.dms.StaInfoCommand;

public interface DmsWmsManager {

    StaInfoCommand findStaInfoByCode(String code);
}
