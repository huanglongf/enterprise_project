package com.jumbo.wms.manager.warehouse;

import java.util.List;

import loxia.dao.support.BeanPropertyRowMapperExt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.warehouse.StaAdditionalLineDao;
import com.jumbo.util.StringUtil;
import com.jumbo.wms.manager.BaseManagerImpl;
import com.jumbo.wms.model.warehouse.StaAdditionalLineCommand;


@Transactional
@Service("staUpdateManager")
public class StaUpdateManagerImpl extends BaseManagerImpl implements StaUpdateManager {


    /**
	 * 
	 */
    private static final long serialVersionUID = -4211525393705813750L;
    @Autowired
    private StaAdditionalLineDao staAdditionalLineDao;

    public void updateTrackingAndSku(long staId) {
        List<StaAdditionalLineCommand> staList = staAdditionalLineDao.findTrackingAndSkuByStuId(staId, new BeanPropertyRowMapperExt<StaAdditionalLineCommand>(StaAdditionalLineCommand.class));

        StringBuffer sb = new StringBuffer();

        for (StaAdditionalLineCommand staAdditionalLineCommand : staList) {
            sb.append(staAdditionalLineCommand.getTrackingValue()).append(":").append(staAdditionalLineCommand.getSkuId()).append(";");
        }

        String trackingAndSku = sb.toString().substring(0, sb.toString().length() - 1);
        staAdditionalLineDao.flush();
        // System.out.println("============================="+staId+"|========"+trackingAndSku);
        if (!StringUtil.isEmpty(trackingAndSku) && trackingAndSku.length() > 3000) {
            trackingAndSku = trackingAndSku.substring(0, 3000);
        }
        staAdditionalLineDao.updateTrackingAndSkuByStuId(trackingAndSku, staId);
        // System.out.println("============================="+staId+"|========"+trackingAndSku);

    }

}
