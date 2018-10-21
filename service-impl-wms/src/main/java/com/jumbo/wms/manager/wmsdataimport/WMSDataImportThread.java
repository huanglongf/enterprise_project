package com.jumbo.wms.manager.wmsdataimport;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jumbo.wms.manager.util.SpringUtil;
import com.jumbo.wms.model.dataimport.SnAndValidDateSkuFlow;

public class WMSDataImportThread implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(WMSDataImportThread.class);
    private WMSDataImportManager wmsDataImportManager;
    /**
     * 标识执行id
     */
    private Long id;
    /**
     * 需要执行的方法区分标识
     */
    private String name;
    /**
     * 起始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;
    /**
     * 店铺ode
     */
    private String owner;
    /**
     * 平台单据号
     */
    private List<String> slipCode1List;
    /**
     * snAndValidDateSkuFlow数据
     */
    private SnAndValidDateSkuFlow snAndValidDateSkuFlow;
    /**
     * 生成路径
     */
    private String path;
    /**
     * 线程名称
     */
    private String threadName = "";
    /**
     * 子线程
     */
    private ExecutorService executorService;
    /**
     * 线程携带的list（需要根据实际情况重新封装数据）
     */
    private List<SnAndValidDateSkuFlow> list;
    /**
     * 数据来源
     */
    private String dataSource;

    public WMSDataImportThread(String threadName) {
        wmsDataImportManager = (WMSDataImportManager) SpringUtil.getBean("wmsDataImportManager");
        if (wmsDataImportManager == null) {
            Log.info("SpringUtil.getBean失败！");
            wmsDataImportManager = (WMSDataImportManager) SpringUtil.getBean("wmsDataImportManager", WMSDataImportManagerImpl.class);
        }
        this.threadName = threadName;
    }

    @Override
    public void run() {
        if (("getSnOrValidDateSkuFlow").equals(name) || ("getSnOrValidDateSkuFlow: no owner").equals(name)) {
            try {
                wmsDataImportManager.createEachFile(startTime, endTime, owner, slipCode1List, path, executorService);
            } catch (Exception e) {
                logger.error("多线程获取SnOrValidDateSkuFlow数据异常！owner=" + owner, e);
            }
        } else if ("packagingSnAndExpDate".equals(name)) {
            wmsDataImportManager.packagingSnAndExpDate(snAndValidDateSkuFlow, list);
        } else if ("brandUnFinishedInstructions".equals(name)) {
            wmsDataImportManager.packagingBrandUnFinishedIns(owner, path, dataSource);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThreadName() {
        return threadName;
    }

    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }


    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<String> getSlipCode1List() {
        return slipCode1List;
    }

    public void setSlipCode1List(List<String> slipCode1List) {
        this.slipCode1List = slipCode1List;
    }

    public SnAndValidDateSkuFlow getSnAndValidDateSkuFlow() {
        return snAndValidDateSkuFlow;
    }

    public void setSnAndValidDateSkuFlow(SnAndValidDateSkuFlow snAndValidDateSkuFlow) {
        this.snAndValidDateSkuFlow = snAndValidDateSkuFlow;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    public List<SnAndValidDateSkuFlow> getList() {
        return list;
    }

    public void setList(List<SnAndValidDateSkuFlow> list) {
        this.list = list;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }
}
