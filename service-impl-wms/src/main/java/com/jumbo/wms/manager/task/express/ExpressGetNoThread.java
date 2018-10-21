package com.jumbo.wms.manager.task.express;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.expressDelivery.TransOlManager;
import com.jumbo.wms.manager.warehouse.WareHouseManagerExecute;

/**
 * 物流获取运单号线程
 * @author kai.zhu
 *
 */
public class ExpressGetNoThread extends Thread {
    protected static final Logger log = LoggerFactory.getLogger(ExpressGetNoThread.class);

    private Long staId;
	private TransOlManager transOlManager;
    private WareHouseManagerExecute wareHouseManagerExecute;
	
    public ExpressGetNoThread(Long staId) {
    	this.staId = staId;
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        transOlManager = (TransOlManager) webContext.getBean("transOlManager");
        wareHouseManagerExecute = (WareHouseManagerExecute) webContext.getBean("wareHouseManagerExecute");
    }

    @Override
    public void run() {
    	// 下单
        try {
            // 设置运单号
        	transOlManager.matchingTransNo(staId);
        } catch (BusinessException e) {
            // 记录获取运单失败的单据
            wareHouseManagerExecute.failedToGetTransno(staId);
            log.error("IntefaceByWarehouse error code is : " + e.getErrorCode());
        } catch (Exception e) {
            wareHouseManagerExecute.failedToGetTransno(staId);
            log.error("", e);
        }
    }

	public Long getStaId() {
		return staId;
	}

	public void setStaId(Long staId) {
		this.staId = staId;
	}

}

