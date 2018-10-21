package com.jumbo.wms.manager.system;

import java.util.List;

import loxia.dao.Pagination;
import loxia.dao.Sort;

import org.I0Itec.zkclient.ZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jumbo.dao.baseinfo.SkuDao;
import com.jumbo.dao.system.SysSchedulerTaskDao;
import com.jumbo.wms.model.baseinfo.Sku;
import com.jumbo.wms.model.baseinfo.SkuSpType;
import com.jumbo.wms.model.command.SkuCommand;
import com.jumbo.wms.model.system.SysSchedulerTask;


@Transactional
@Service("schedulerTaskManager")
public class SchedulerTaskManagerImpl implements SchedulerTaskManager {

    private static final long serialVersionUID = 6183889107438325863L;
    @Autowired
    private SysSchedulerTaskDao schedulerTaskDao;
    // // 获取zk信息
    @Value("${zk.notice.task.wms}")
    private String znode;

    @Autowired
    private ZkClient zkClient;
    @Autowired
    private SkuDao skuDao;

    @Override
    public int enableOrDisableSchedulerTaskByIds(List<Long> ids, Integer state) {
        for (Long id : ids) {
            SysSchedulerTask ss = schedulerTaskDao.getByPrimaryKey(id);
            ss.setLifecycle(state);
        }
        return ids.size();
    }

    @Override
    public void removeSchedulerTaskByIds(List<Long> ids) {
        for (Long id : ids) {
            schedulerTaskDao.deleteByPrimaryKey(id);
        }
    }

    @Override
    public List<SysSchedulerTask> findAllEffectSchedulerTaskList(Integer node) {
        return schedulerTaskDao.getByLifecycle(SysSchedulerTask.LIFECYCLE_NORMAL, node);
    }

    @Override
    public SysSchedulerTask findSchedulerTaskById(Long id) {
        return schedulerTaskDao.getByPrimaryKey(id);
    }

    public boolean deleteSkuType(Long id) {
        boolean msg = false;
        Sku sku = skuDao.getByPrimaryKey(id);
        if (null != sku) {
            sku.setSpType(SkuSpType.COMMON);
            msg = true;
        }
        return msg;
    }

    public String updateSkuCode(String code) {
        String msg = "";
        Sku sku = skuDao.getByCode(code);
        if (null != sku) {
            sku.setSpType(SkuSpType.STARBUCKS_MSR_VIRTUAL_NEW);
            msg = "sccuess";
        } else {
            msg = "none";
        }
        return msg;
    }

    @Override
    public SysSchedulerTask saveSchedulerTask(SysSchedulerTask schedulerTask) {
        if (schedulerTask.getId() == null) {
            schedulerTask.setLifecycle(SysSchedulerTask.LIFECYCLE_NORMAL);
        }
        schedulerTaskDao.save(schedulerTask);
        Object object = schedulerTask.getId();
        zkClient.writeData(znode, object);
        return schedulerTask;
    }

    @Override
    public Pagination<SysSchedulerTask> findSysSchedulerTaskPage(int start, int pageSize, Sort[] sorts, SysSchedulerTask schedulerTask) {
        String beanName = null;
        String methodName = null;
        if (schedulerTask != null) {
            if (StringUtils.hasText(schedulerTask.getBeanName())) {
                beanName = schedulerTask.getBeanName();
            }
            if (StringUtils.hasText(schedulerTask.getMethodName())) {
                methodName = schedulerTask.getMethodName();
            }
        }
        return schedulerTaskDao.findSysSchedulerTaskList(start, pageSize, sorts, beanName, methodName, new BeanPropertyRowMapper<SysSchedulerTask>(SysSchedulerTask.class));
    }

    public Pagination<SkuCommand> findSkubySkuCode(int start, int pageSize, Sort[] sorts, String code) {
        String beanName = null;
        if (StringUtils.hasText(code)) {
            beanName = code;
        }
        return skuDao.findSkubySkuCode(start, pageSize, sorts, beanName, new BeanPropertyRowMapper<SkuCommand>(SkuCommand.class));
    }


}
