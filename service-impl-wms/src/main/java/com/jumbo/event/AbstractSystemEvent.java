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

import org.springframework.context.ApplicationEvent;

public abstract class AbstractSystemEvent extends ApplicationEvent {

    /**
	 * 
	 */
    private static final long serialVersionUID = -6153639321773113877L;

    public AbstractSystemEvent(Object source) {
        super(source);
    }

    public AbstractSystemEvent(Object source, String eventContent) {
        super(source);
        this.eventContent = eventContent;
    }

    protected String eventContent;

    public abstract String getNotifyType();

    public String getEventContent() {
        return eventContent;
    }

    public void setEventContent(String eventContent) {
        this.eventContent = eventContent;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [eventContent=" + eventContent + "]";
    }
}
