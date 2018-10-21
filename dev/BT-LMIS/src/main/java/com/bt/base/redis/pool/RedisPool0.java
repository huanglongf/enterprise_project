package com.bt.base.redis.pool;

import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.bt.base.redis.pool.common.RedisPool;
import com.bt.base.redis.pool.common.RedisPoolHelper;

import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/** 
 * @ClassName: RedisPool0
 * @Description: TODO(redis连接0)
 * @author Ian.Huang
 * @date 2017年11月16日 下午2:37:27 
 * 
 */
@Component("redisPool0")
public class RedisPool0 extends RedisPool implements RedisPoolHelper {
	
	private static final Logger logger = Logger.getLogger(RedisPool0.class);
	
	private static ReentrantLock lockPool = new ReentrantLock();
	
    protected static ReentrantLock lockJedis = new ReentrantLock();
    
    private static ShardedJedisPool jedisPool = null;
    
    private static final int db = 0;
    
    /**
     * @Title: poolInit
     * @Description: TODO(在多线程环境同步初始化，可配置dbindex)
     * @return: void
     * @author: Ian.Huang
     * @date: 2017年11月12日 下午8:42:20
     */
    protected void poolInit(int db) {
	    // 断言 ，当前锁是否已经锁住，如果锁住了，就啥也不干，没锁的话就执行下面步骤  
	    assert ! lockPool.isHeldByCurrentThread();    
	    lockPool.lock(); 
	    try {    
	        if (jedisPool == null) {jedisPool = initialPool(db);}
	    }catch(Exception e){
	        e.printStackTrace();
	        logger.error(e);
	    } finally {    
	        lockPool.unlock();
	    }
    }
    
    
    /**
     * @Title: getJedis
     * @Description: TODO(获取jedis连接，可配置dbindex)
     * @param db
     * @return: ShardedJedis
     * @author: Ian.Huang
     * @date: 2017年11月12日 下午8:46:19
     */
    public ShardedJedis getJedis() {
        // 断言 ，当前锁是否已经锁住，如果锁住了，就啥也不干，没锁的话就执行下面步骤  
        assert ! lockJedis.isHeldByCurrentThread();    
        lockJedis.lock();  
        if (jedisPool == null) {poolInit(db);}  
        ShardedJedis jedis = null;  
        try {    
            if (jedisPool != null) {jedis = jedisPool.getResource();}
        } catch (Exception e) {    
        	logger.error("Get jedis error : "+ e);
        } finally {  
            lockJedis.unlock();
        }  
        return jedis;
    }
    
}
	
