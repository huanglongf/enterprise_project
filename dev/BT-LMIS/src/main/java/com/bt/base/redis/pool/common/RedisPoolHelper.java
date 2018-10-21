package com.bt.base.redis.pool.common;

import redis.clients.jedis.ShardedJedis;

/** 
 * @ClassName: RedisPoolHelper
 * @Description: TODO()
 * @author Ian.Huang
 * @date 2017年11月17日 下午5:14:19 
 * 
 */
public interface RedisPoolHelper {

    /**
     * @Title: getJedis
     * @Description: TODO(获取redis连接)
     * @return: ShardedJedis
     * @author: Ian.Huang
     * @date: 2017年11月17日 下午5:03:16
     */
    ShardedJedis getJedis(); 
	
}
