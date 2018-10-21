/**
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Jumbomart. You shall not
 * disclose such Confidential Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Jumbo.
 * 
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY
 * DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES.
 * 
 */

package com.jumbo.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;

import com.jumbo.wms.exception.BusinessException;
import com.jumbo.wms.model.warehouse.StockTransApplication;
import com.jumbo.wms.model.warehouse.StockTransApplicationStatus;


public class StockTransApplicationStatusChangeEvent extends ApplicationEvent {


    /**
	 * 
	 */
    private static final long serialVersionUID = 4655725656210150583L;
    private static final Logger logger = LoggerFactory.getLogger(StockTransApplicationStatusChangeEvent.class);
    /**
     * The original status
     */
    private StockTransApplicationStatus before;

    /**
     * 
     * @param source must be instanceof StockTransApplication.
     * @param before the original status
     */
    public StockTransApplicationStatusChangeEvent(Object source, StockTransApplicationStatus before) {
        super(source);
        if (source instanceof StockTransApplication) {
            this.before = before;
            StockTransApplication sta = (StockTransApplication) source;
            logger.debug("The current sta[id={},code={} 's status has been changed from {} to {}!", new Object[] {sta.getId(), sta.getCode(), before == null ? "NULL" : before.name(), sta.getStatus() == null ? "NULL" : sta.getStatus().name()});
        } else {
            throw new BusinessException("StockTransApplicationStatusChangeEvent's source must be instanceof StockTransApplication!");
        }
    }

    public StockTransApplicationStatus getBefore() {
        return before;
    }

    public void setBefore(StockTransApplicationStatus before) {
        this.before = before;
    }

}
