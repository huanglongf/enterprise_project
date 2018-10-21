package com.jumbo.wms.manager.task.autoPicking;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumbo.dao.warehouse.AutoPlConfigDao;
import com.jumbo.wms.model.warehouse.AutoPlConfig;


@Service("autoPickingManager")
public class AutoPickingManagerImpl implements AutoPickingManager {


    private static final long serialVersionUID = -1088529154943115725L;

    @Autowired
    private AutoPlConfigDao apDao;

    /**
     * 创建配货清单
     */
    @Override
    public void generatePicking(List<Long> staId) {

    }

    /**
     * 更新下次执行时间
     */
    public void updateAutoPickingNextExecuteTime(Long apcId) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        AutoPlConfig ap = apDao.getByPrimaryKey(apcId);
        calendar.add(Calendar.MINUTE, ap.getIntervalMinute());
        ap.setNextExecuteTime(calendar.getTime());
        apDao.save(ap);
    }
}
