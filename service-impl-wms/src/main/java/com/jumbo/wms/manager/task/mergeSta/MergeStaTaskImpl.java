package com.jumbo.wms.manager.task.mergeSta;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jumbo.wms.daemon.MergeStaTask;
import com.jumbo.wms.manager.BaseManagerImpl;

/**
 * 
 * 
 * 项目名称：Wms 类名称：MergeStaTask 类描述：合并作业单定时 创建人：bin.hu 创建时间：2014-7-22 下午03:04:11
 * 
 * @version
 * 
 */
public class MergeStaTaskImpl extends BaseManagerImpl implements MergeStaTask {

    @Autowired
    private MergeStaManager mergeStaManager;

    private static final long serialVersionUID = -3898106199358644821L;

    private static Map<Long, Date> dateMap = new HashMap<Long, Date>();

    /**
     * 定时任务-每30分钟执行一次，合并对应的STA生成新的STA
     */
    public void mergeSta(Long whOuId) {
        log.info("mergeStaTask begin！");
        boolean isExecute = !dateMap.containsKey(whOuId);
        if (!isExecute) {
            // 获取当前时间
            Calendar c = Calendar.getInstance();
            // 当前时间 减去 25 分钟 因为定时任务配置的是30小时执行一次 以防时间上存在毫秒级差距所以记录间隔时间为25分钟
            c.add(Calendar.MINUTE, -25);
            // 当前时间减去一小时的情况下 再次比较 上次执行时间 如果大于上次执行时间 说明 上次执行到当前时间已经超过25分钟了
            if (c.getTime().getTime() > dateMap.get(whOuId).getTime()) {
                isExecute = true;
            }
        }
        if (isExecute) {
            // 设置当前仓库执行时间
            dateMap.put(whOuId, new Date());
            try {
                mergeStaManager.mergeStaTask(whOuId);
            } catch (Exception e) {
                log.error("AutoPickingManagerProxyImpl.autoGeneratePickingLilst----mergeSta error");
                log.error("", e);
            }
        }
    }

}
