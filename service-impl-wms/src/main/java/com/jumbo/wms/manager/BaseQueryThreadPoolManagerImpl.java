package com.jumbo.wms.manager;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import com.jumbo.dao.warehouse.InventoryDao;
import com.jumbo.dao.warehouse.InventoryZeroDao;
import com.jumbo.util.ThreadPoolUtil;
import com.jumbo.wms.Constants;
import com.jumbo.wms.manager.system.ChooseOptionManager;
import com.jumbo.wms.manager.warehouse.BaseQueryThreadPoolManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerQuery;
import com.jumbo.wms.model.system.ChooseOption;
import com.jumbo.wms.model.warehouse.InventoryCommand;

import loxia.dao.Pagination;
import loxia.dao.Sort;

@Service("baseQueryThreadPoolManager")
public class BaseQueryThreadPoolManagerImpl implements BaseQueryThreadPoolManager {

    private static final long serialVersionUID = 3461433631875448379L;

    protected static final Logger log = LoggerFactory.getLogger(BaseQueryThreadPoolManagerImpl.class);
    @Autowired
    private ChooseOptionManager chooseOptionManager;
    @Autowired
    private InventoryDao inventoryDao;
    @Autowired
    private InventoryZeroDao inventoryZeroDao;
    @Autowired
    private WareHouseManagerQuery wareHouseManagerQuery;

    private static ThreadPoolExecutor txQuery = null;

    private static ThreadPoolExecutor txExport = null;

    /**
     * 分组统计线程数
     */
    private static Map<String, Integer> countMap = new HashMap<String, Integer>();

    /**
     * 初始化线程池
     */
    private synchronized void initThreadPool() {
        if (txQuery == null) {
            ChooseOption co = chooseOptionManager.findChooseOptionByCategoryCodeAndKey(Constants.MORE_THREAD_QUERY, Constants.MORE_THREAD_QUERY_INV_QTY);
            Integer amount = Integer.parseInt(co.getOptionValue());

            // 根据参数创建线程池
            ExecutorService pool = Executors.newFixedThreadPool(amount);
            txQuery = (ThreadPoolExecutor) pool;
        }
        if (txExport == null) {
            ChooseOption co = chooseOptionManager.findChooseOptionByCategoryCodeAndKey(Constants.MORE_THREAD_QUERY, Constants.MORE_THREAD_EXPORT_QTY);
            Integer amount = Integer.parseInt(co.getOptionValue());

            // 根据参数创建线程池
            ExecutorService pool = Executors.newFixedThreadPool(amount);
            txExport = (ThreadPoolExecutor) pool;
        }
    }

    /**
     * 执行完毕线程数量+1
     * 
     * @param key
     */
    public synchronized static void threadQtyPlus(String threadGroup) {
        if (countMap.get(threadGroup) != null) {
            countMap.put(threadGroup, countMap.get(threadGroup) + 1);
        }
    }

    /**
     * 删除线程数
     * 
     * @param key
     */
    public void removeThreadQty(String threadGroup) {
        countMap.remove(threadGroup);
    }


    /**
     * 多线程创建
     * 
     * @param list
     * @throws Exception
     */
    public void createMoreThread(Collection list) throws Exception {
        if (log.isInfoEnabled()) {
            log.info("InventoryQueryMoreThread start");
        }

        String threadGroup = UUID.randomUUID().toString();
        countMap.put(threadGroup, 0);
        ThreadPoolExecutor tx = null;
        try {
            if (txQuery == null || txExport == null) {
                initThreadPool();
            }
            if (list.size() > 500) {
                tx = txExport;
            } else {
                tx = txQuery;
            }
            for (Object ic : list) {
                // 每条数据一个线程
                BaseQueryThread bqt = new BaseQueryThread(ic, threadGroup, wareHouseManagerQuery);
                Thread t = new Thread(bqt);
                // tx.execute(bqt);
                ThreadPoolUtil.executeThread(tx, t, 2000);
            }
            Long sleepTime = 0L;
            while (true) {
                Integer a = countMap.get(threadGroup);
                // isFinish = pool.isTerminated();
                if (countMap.get(threadGroup) >= list.size() || sleepTime > 10000L) {
                    break;
                }
                try {
                    Thread.sleep(100L);
                    sleepTime = sleepTime + 100L;
                } catch (InterruptedException e) {
                    if (log.isErrorEnabled()) {
                        log.error("createMoreThread Thread InterruptedException！", e);
                    }
                }
            }
            if (log.isInfoEnabled()) {
                log.info("InventoryQueryMoreThread end");
            }
        } catch (Exception e) {
            throw e;
        } finally {
            removeThreadQty(threadGroup);
        }
    }


    /**
     * 库存数量查询
     * 
     * @param start
     * @param pageSize
     * @param inv
     * @param whOuId
     * @param companyid
     * @param sorts
     * @return
     * @throws Exception
     */
    public Pagination<InventoryCommand> findCurrentInventoryByPage(int start, int pageSize, InventoryCommand inv, Long whOuId, Long companyid, Sort[] sorts) throws Exception {
        if (inv != null) {
            inv.setQueryLikeParam1();
        } else {
            inv = new InventoryCommand();
        }
        if (inv.getExtCode2() != null) {
            if ("".equals(inv.getExtCode2())) {
                inv.setExtCode2(null);
            }
        }
        if (inv.getExtCode1() != null) {
            if ("".equals(inv.getExtCode1())) {
                inv.setExtCode1(null);
            }
        }
        Pagination<InventoryCommand> list =
                inventoryDao.findCurrentInventoryByPageNew(start, pageSize, inv.getBarCode(), inv.getSkuCode(), inv.getExtCode2(), inv.getJmCode(), inv.getSkuName(), inv.getSupplierSkuCode(), inv.getBrandName(), inv.getInvOwner(), whOuId, companyid,
                        inv.getIsShowZero(), inv.getExtCode1(), inv.getNumberUp(), inv.getAmountTo(), new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class), sorts);
        if (list.getItems() != null && list.getItems().size() > 0) {
            createMoreThread(list.getItems());
        }
        return list;
    }

    @Override
    public List<InventoryCommand> findCurrentInventory(List<InventoryCommand> list) throws Exception {
        createMoreThread(list);
        return list;
    }

    @Override
    public Pagination<InventoryCommand> findCurrentInventoryZeroByPage(int start, int pageSize, InventoryCommand inv, Long whOuId, Long companyid, Sort[] sorts) throws Exception {
        if (inv != null) {
            inv.setQueryLikeParam1();
        } else {
            inv = new InventoryCommand();
        }
        if (inv.getExtCode2() != null) {
            if ("".equals(inv.getExtCode2())) {
                inv.setExtCode2(null);
            }
        }
        if (inv.getExtCode1() != null) {
            if ("".equals(inv.getExtCode1())) {
                inv.setExtCode1(null);
            }
        }
        Pagination<InventoryCommand> list = inventoryZeroDao.findCurrentInventoryByPageNew(start, pageSize, inv.getBarCode(), inv.getSkuCode(), inv.getExtCode2(), inv.getJmCode(), inv.getSkuName(), inv.getSupplierSkuCode(), inv.getBrandName(),
                inv.getInvOwner(), whOuId, companyid, inv.getIsShowZero(), inv.getExtCode1(), inv.getNumberUp(), inv.getAmountTo(), new BeanPropertyRowMapper<InventoryCommand>(InventoryCommand.class), sorts);
        if (list.getItems() != null && list.getItems().size() > 0) {
            createMoreThread(list.getItems());
        }
        return list;
    }
}
