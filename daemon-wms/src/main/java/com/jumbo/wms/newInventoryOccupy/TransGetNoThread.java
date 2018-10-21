package com.jumbo.wms.newInventoryOccupy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.manager.task.sforder.SfOrderTaskManager;

/**
 * 物流获取运单号线程
 * @author sjk
 *
 */
public class TransGetNoThread extends Thread {
    protected static final Logger log = LoggerFactory.getLogger(TransGetNoThread.class);

    private Long whOuId;
    private Long staId;
    private String lpcode;
	private SfOrderTaskManager sfOrderTaskManager;
    
    public TransGetNoThread(Long whouid,Long staId,String lpcode) {
    	this.whOuId = whouid;
    	this.staId = staId;
    	this.lpcode = lpcode;
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        sfOrderTaskManager = (SfOrderTaskManager) webContext.getBean("sfOrderTaskManager");
    }

    @Override
    public void run() {
    	// SF下单
        try {
            // 设置顺丰单据号
        	sfOrderTaskManager.matchingTransNo(getLpcode(), getWhOuId(), getStaId());
        } catch (BusinessException e) {
            log.error("sfIntefaceByWarehouse error code is : " + e.getErrorCode());
        } catch (Exception e) {
            log.error("", e);
        }
    }

	public Long getWhOuId() {
		return whOuId;
	}

	public void setWhOuId(Long whOuId) {
		this.whOuId = whOuId;
	}

	public Long getStaId() {
		return staId;
	}

	public void setStaId(Long staId) {
		this.staId = staId;
	}

	public String getLpcode() {
		return lpcode;
	}

	public void setLpcode(String lpcode) {
		this.lpcode = lpcode;
	}


}
