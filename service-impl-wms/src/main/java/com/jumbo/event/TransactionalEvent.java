package com.jumbo.event;

import java.util.EventObject;

public class TransactionalEvent extends EventObject {

    /**
	 * 
	 */
    private static final long serialVersionUID = 2596204996959717244L;

    /** System time when the event happened */
    private final long timestamp;

    public TransactionalEvent(Object source) {
        super(source);
        this.timestamp = System.currentTimeMillis();
    }

    public final long getTimestamp() {
        return this.timestamp;
    }

}
