package com.jumbo.wms.manager.thread;

import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jumbo.dao.thread.ThreadConfigDao;
import com.jumbo.util.TimeHashMap;
import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.exception.ErrorCode;
import com.jumbo.wms.model.config.ThreadConfig;

@Transactional
@Service("threadUtilManager")
public class ThreadUtilManagerImpl implements ThreadUtilManager {

    /**
     * 
     */
    private static final long serialVersionUID = 372781102095097261L;

    static TimeHashMap<String, ThreadConfig> threadConfigCodeAndSysKeyMap = new TimeHashMap<String, ThreadConfig>();

    @Autowired
    private ThreadConfigDao threadConfigDao;
    @Autowired
    private ZkClient zkClient;

    @Override
    public List<ThreadConfig> getAllThreadPools(String sysKey) {
        return threadConfigDao.getAllBySysKey(sysKey);
    }

    @Override
    public ThreadConfig getByCodeAndSysKey(String key, String sysKey) {
        String mapKey = key + "⊥" + sysKey;
        ThreadConfig t = threadConfigCodeAndSysKeyMap.get(mapKey);
        if (null == t) {
            t = threadConfigDao.getByCodeAndSysKey(key, sysKey);
            threadConfigCodeAndSysKeyMap.put(mapKey, t, 5 * 60 * 1000);
        }
        return t;
    }

    @Override
    public ThreadConfig getThreadConfigById(Long id) {
        return threadConfigDao.getByPrimaryKey(id);
    }

    @Override
    public void updateThreadCount(Long id, int count) {
        ThreadConfig tmp = threadConfigDao.getByPrimaryKey(id);
        if (tmp == null) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR);
        } else {
            tmp.setThreadCount(count);
        }
    }

    @Override
    public Pagination<ThreadConfig> getAllThreadConfigPools(int start, int pageSize, Sort[] sorts) {
        return threadConfigDao.getAllThreadConfig(start, pageSize, sorts, new BeanPropertyRowMapper<ThreadConfig>(ThreadConfig.class));
    }

    @Override
    public String saveThreadConfig(ThreadConfig threadConfig, String path) {
        String flag = "";
        if ("".equals(threadConfig.getId()) || threadConfig.getId() == null) {
            if (stringUnitl(threadConfig.getThreadCode()) && threadConfig.getMemo() != null && stringUnitl(threadConfig.getSysKey()) && stringUnitl(threadConfig.getMemo())) {
                // 为空就执行新增操作
                threadConfigDao.save(threadConfig);
                String object = threadConfig.getId() + "," + threadConfig.getThreadCount();
                zkClient.writeData(path, object);
            } else {
                flag = "信息不完整";
            }

        } else {
            // 不为空就执行更新操作（判断是否存在？就更新：就报错
            ThreadConfig threadConfig2 = threadConfigDao.checkThreadConfig(threadConfig.getId());
            if (threadConfig2 != null) {
                threadConfig2.setThreadCount(threadConfig.getThreadCount());
                threadConfigDao.save(threadConfig2);
                String object = threadConfig2.getId() + "," + threadConfig2.getThreadCount();
                zkClient.writeData(path, object);
            } else {
                flag = "id不存在";
            }
        }



        return flag;
    }

    private Boolean stringUnitl(String string) {
        if (!"".equals(string) && string != null) {
            return true;
        } else {
            return false;
        }
    }

}
