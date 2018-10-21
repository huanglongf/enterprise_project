package com.jumbo.util;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 带时间校验的Map,当存储数据超过有效期后,无法通过当前Map获取数据
 * 
 * @author sjk
 * 
 * @param <K> key
 * @param <V> value
 */
public class TimeHashMap<K, V> implements Serializable {

    private static final long serialVersionUID = 191964190588414063L;
    public static final long DEFAULT_TIMER = 1 * 60 * 60 * 1000;

    // 时间MAP
    private Map<K, Long> dataTimer = new HashMap<K, Long>();
    // 数据MAP
    private Map<K, V> dataMap = new HashMap<K, V>();
    // 过期时间
    private long expTime = DEFAULT_TIMER;

    public TimeHashMap() {
        super();
    }

    /**
     * 
     * @param defaultExpTime 过期时间
     */
    public TimeHashMap(long defaultExpTime) {
        super();
        this.expTime = defaultExpTime;
    }

    public void setDefaultExpTime(long expTime) {
        this.expTime = expTime;
    }

    /**
     * 设置数据，同HashMap.put,默认设置过期时间为1小时
     * 
     * @param k map key
     * @param v map value
     * 
     */
    public void put(K k, V v) {
        dataMap.put(k, v);
        dataTimer.put(k, System.currentTimeMillis() + expTime);
    }

    /**
     * 
     * 设置数据，同HashMap.put
     * 
     * @param k map key
     * @param v map value
     * @param timer 过期时间,单位毫秒
     */
    public synchronized void put(K k, V v, long expTime) {
        dataMap.put(k, v);
        dataTimer.put(k, System.currentTimeMillis() + expTime);
    }

    /**
     * 获取数据同HashMap.get,
     * 
     * @param k
     * @return 当数据过期返回为null
     */
    public V get(K k) {
        long currentTime = System.currentTimeMillis();
        Long expTime = dataTimer.get(k);
        if (expTime == null) {
            return null;
        } else if (currentTime > expTime) {
            return null;
        } else {
            return dataMap.get(k);
        }
    }
}
