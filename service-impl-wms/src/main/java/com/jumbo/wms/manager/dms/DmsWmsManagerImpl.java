package com.jumbo.wms.manager.dms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.warehouse.StockTransApplicationDao;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.dms.StaInfoCommand;

@Service("dmsWmsManager")
public class DmsWmsManagerImpl extends BaseManagerImpl implements DmsWmsManager {

    /**
     * 
     */
    private static final long serialVersionUID = -8699257411106382125L;

    @Autowired
    private StockTransApplicationDao staDao;

    @Override
    public StaInfoCommand findStaInfoByCode(String code) {
        if (null != code && !"".equals(code)) {
            StaInfoCommand staInfoCommand = staDao.findStaInfoByCode(code, new BeanPropertyRowMapper<StaInfoCommand>(StaInfoCommand.class));
            return staInfoCommand;
        } else {
            return null;
        }
    }



}
