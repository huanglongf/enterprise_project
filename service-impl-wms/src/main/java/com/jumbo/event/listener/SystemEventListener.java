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

package com.jumbo.event.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;

import com.jumbo.event.AbstractSystemEvent;

public class SystemEventListener implements ApplicationListener<AbstractSystemEvent> {

    private static final Logger logger = LoggerFactory.getLogger(SystemEventListener.class);

    private boolean logEveryEvent = true;

    public void setLogEveryEvent(boolean logEveryEvent) {
        this.logEveryEvent = logEveryEvent;
    }

    public void onApplicationEvent(AbstractSystemEvent event) {
        if (logEveryEvent) {
            if (logger.isWarnEnabled()) {
                final StringBuilder builder = new StringBuilder();
                builder.append("SystemEvent [");
                builder.append(event.getNotifyType());
                builder.append("]: ");
                builder.append("details: ");
                builder.append(event.toString());

                logger.warn(builder.toString());
            }
        }
    }

}
