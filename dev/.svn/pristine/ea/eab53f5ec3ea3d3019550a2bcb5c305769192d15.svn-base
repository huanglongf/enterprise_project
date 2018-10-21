package com.bt.base.redis.pool.common;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bt.base.Constant;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedisPool;

/** 
 * @ClassName: RedisPool
 * @Description: TODO(redis连接基类)
 * @author Ian.Huang
 * @date 2017年11月16日 下午7:16:17 
 * 
 */
@Component("redisPool")
public class RedisPool {
	
	private static final String protocol = "http://";
    
	// Redis服务器IP
    @Value("#{redisConfig['redis.ip']}")
    private String ip;
    
    // Redis端口号
 	@Value("#{redisConfig['redis.port']}")
 	private int port;

 	// Redis密码
 	@Value("#{redisConfig['redis.password']}")
	private String password;
 	
    public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// 可用连接实例的最大数目，默认值为8；  
    // 如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。  
    protected int MAX_ACTIVE = -1;  
      
    // 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。  
    protected int MAX_IDLE = 30;  
      
    // 等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；  
    protected int MAX_WAIT = -1;  
  
    // 超时时间  
    protected int TIMEOUT = 400000;  
      
    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；  
    protected boolean TEST_ON_BORROW = false;
	
	/** 
     * redis过期时间,以秒为单位 
     */  
    protected int EXRP_HOUR = 60*60;          //一小时  
    protected int EXRP_DAY = 60*60*24;        //一天  
    protected int EXRP_MONTH = 60*60*24*30;   //一个月  
	
    /**
     * @Title: createURI
     * @Description: TODO(获取redis路径地址)
     * @param db
     * @return: String
     * @author: Ian.Huang
     * @date: 2017年11月12日 下午8:34:56
     */
    private String createURI(int db) {
    	return protocol
    			+ Constant.SEPARATE_COLON
    			+ password
    			+ Constant.SEPARATE_AT
    			+ ip
    			+ Constant.SEPARATE_COLON
    			+ port
    			+ Constant.SEPARATE_FWD_SLASH
    			+ db;
    }
    
    /**
     * @Title: initialPool
     * @Description: TODO(初始化redis连接池，可配置dbindex)
     * @param db
     * @return: ShardedJedisPool
     * @author: Ian.Huang
     * @date: 2017年11月16日 下午7:51:02
     */
    protected ShardedJedisPool initialPool(int db) {
			List<JedisShardInfo> listShard = new LinkedList<JedisShardInfo>();
			// JedisShardInfo host= new JedisShardInfo(IP, PORT);
			// String HOST = "rediss://10.88.26.115:6379/";
			// String HOST = "http://:BaoZun2016@10.88.26.115:6379/1";
			JedisShardInfo host= new JedisShardInfo(createURI(db));
			host.setConnectionTimeout(TIMEOUT);
			listShard.add(host);
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_ACTIVE);
			// 最大空闲连接数
			config.setMaxIdle(MAX_IDLE);
			// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
			config.setMaxWaitMillis(MAX_WAIT);
			// 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
			config.setMinEvictableIdleTimeMillis(1800000);
			// 对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)
			config.setSoftMinEvictableIdleTimeMillis(1800000);
			// 在获取连接的时候检查有效性, 默认false
			config.setTestOnBorrow(TEST_ON_BORROW);
			// 在空闲时检查有效性, 默认false
			config.setTestWhileIdle(false);
			return new ShardedJedisPool(config, listShard);
    }
    
	/**
     * @Title: poolInit
     * @Description: TODO(初始化redis连接池)
     * @param db
     * @return: void
     * @author: Ian.Huang
     * @date: 2017年11月17日 下午5:02:56
     */
    protected void poolInit(int db) {};
    
}
