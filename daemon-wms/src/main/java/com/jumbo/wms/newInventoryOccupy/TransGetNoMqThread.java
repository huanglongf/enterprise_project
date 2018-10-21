package com.jumbo.wms.newInventoryOccupy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.jumbo.wms.daemon.GainTransNoMQTask;
import com.jumbo.wms.exception.BusinessException;

/**
 * 物流获取运单号线程
 * @author sjk
 *
 */
public class TransGetNoMqThread extends Thread {
    protected static final Logger log = LoggerFactory.getLogger(TransGetNoMqThread.class);

    private Long staId;
    private GainTransNoMQTask gainTransNoMQTask;
    
    public TransGetNoMqThread(Long staId) {
    	this.staId = staId;
        WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
        gainTransNoMQTask = (GainTransNoMQTask) webContext.getBean("gainTransNoMQTask");
    }

    @Override
    public void run() {
    	// SF下单
        try {
            // 设置运单号
            gainTransNoMQTask.gainTransNoMQ(staId);
        } catch (BusinessException e) {
            log.error("gainTransNoMQTask error code is : " + e.getErrorCode());
        } catch (Exception e) {
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
