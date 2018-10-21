package com.jumbo.event.listener;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.jumbo.event.TransactionalEvent;

public class EventObserver implements ApplicationContextAware, InitializingBean {
    private ApplicationContext applicationContext;

    private Map<String, List<TransactionalEventListener<?>>> listeners = new HashMap<String, List<TransactionalEventListener<?>>>();

    @SuppressWarnings("unchecked")
    public void onEvent(final TransactionalEvent event) {
        if (event == null) return;
        List<TransactionalEventListener<?>> listenerList = listeners.get(event.getClass().getName());
        for (@SuppressWarnings("rawtypes")
        TransactionalEventListener l : listenerList)
            l.onEvent(event);
    }

    @SuppressWarnings("rawtypes")
    public void afterPropertiesSet() throws Exception {
        Collection<TransactionalEventListener> list = applicationContext.getBeansOfType(TransactionalEventListener.class).values();
        for (TransactionalEventListener l : list) {
            for (Type type : l.getClass().getGenericInterfaces()) {
                if (type instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) type;
                    Type[] ts = pt.getActualTypeArguments();
                    if (ts == null || ts.length == 0) continue;
                    if (TransactionalEvent.class.isAssignableFrom((Class) ts[0])) {
                        String className = ((Class) ts[0]).getName();
                        List<TransactionalEventListener<?>> tl = listeners.get(className);
                        if (tl == null) {
                            tl = new ArrayList<TransactionalEventListener<?>>();
                            listeners.put(className, tl);
                        }
                        tl.add(l);
                    }
                }
            }
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
