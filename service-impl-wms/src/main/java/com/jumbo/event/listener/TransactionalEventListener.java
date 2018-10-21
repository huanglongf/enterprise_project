package com.jumbo.event.listener;

import java.util.EventListener;

import com.jumbo.event.TransactionalEvent;

public interface TransactionalEventListener<E extends TransactionalEvent> extends EventListener {

    /**
     * 处理事件
     * 
     * @param event
     */
    void onEvent(E event);
}
