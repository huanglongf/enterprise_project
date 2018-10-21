package com.bt.base.redis;

import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.log4j.Logger;

import com.bt.base.redis.pool.RedisPool0;
import com.bt.lmis.base.spring.SpringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.ShardedJedis;

/** 
 * @ClassName: RedisUtils
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author Ian.Huang
 * @date 2017年11月16日 下午5:09:53 
 * 
 */
public class RedisUtils {

	/**
	 * TODO redis仓库使用
	 * */

	private static final Logger logger = Logger.getLogger(RedisPool0.class);

	private static final String PREFIX_REDIS = "redisPool";

	private static final String METHOD = "getJedis";

	/**
	 * @Title: getJedis
	 * @Description: TODO(获取Jedis连接)
	 * @param db
	 * @return: ShardedJedis
	 * @author: Ian.Huang
	 * @throws Exception
	 * @date: 2017年11月16日 下午5:30:53
	 */
	private static ShardedJedis getJedis(int db) throws Exception {
		Object redisPool = SpringUtils.getBean(PREFIX_REDIS + db);
		if(redisPool != null) {
			return (ShardedJedis) (redisPool.getClass().getMethod(METHOD).invoke(redisPool));
		} else {
			throw new Exception("this redis pool ["+ db +"] is not exist");
		}
	}

	/**
     * @Title: check_con
     * @Description: TODO(检查redis连接是否正常)
     * @param db
     * @return: boolean
     * @author: Ian.Huang
     * @date: 2017年11月12日 下午10:13:21
     */
    public static boolean check_con(int db) {
    	ShardedJedis jedis = null;
    	boolean flag = false;
    	try {
    		jedis = getJedis(db);
    		jedis.get("");
    		flag = true;
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
    	return flag;
    }

    public static boolean check_con() {
    	return check_con(0);
    }

    /**
	 * @Title: set
	 * @Description: TODO(添加key value)
	 * @param db
	 * @param key
	 * @param value
	 * @return: void
	 * @author: Ian.Huang
	 * @date: 2017年11月12日 下午9:58:06
	 */
	public static void set(int db, String key, String value) {
		ShardedJedis jedis = null;
		try {
			jedis = getJedis(db);
			jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
	}

	public static void set(String key, String value) {
		set(0, key, value);
	}

	/**
     * @Title: set
     * @Description: TODO(添加key value 并且设置存活时间)
     * @param db
     * @param key
     * @param value
     * @param liveTime
     * @return: void
     * @author: Ian.Huang
     * @date: 2017年11月12日 下午10:01:25
     */
    public static void set(int db, String key, String value, int liveTime) {
    	ShardedJedis jedis = null;
		try {
			jedis = getJedis(db);
			jedis.set(key, value);
			jedis.expire(key, liveTime);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
    }

    public static void set(String key, String value, int liveTime) {
    	set(0, key, value, liveTime);
    }

    /**
     * @Title: set
     * @Description: TODO(添加key value 并且设置当天有效)
     * @param db
     * @param key
     * @param value
     * @param iso
     * @return: void
     * @author: Ian.Huang
     * @date: 2017年11月12日 下午10:04:39
     */
    public static void set(int db, String key, String value, boolean iso) {
    	ShardedJedis jedis = null;
    	try {
	    	jedis = getJedis(db);
	    	jedis.set(key, value);
	    	if(iso) {
	    		SimpleDateFormat formart = new SimpleDateFormat("HH:mm:ss");
	    		jedis.expire(key, (int)((formart.parse("24:00:00").getTime() - formart.parse(formart.format(new Date())).getTime()) / 1000));
	    	}
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
    }

    public static void set(String key, String value, boolean iso) {
    	set(0, key, value, iso);
    }

    /**
	 * @Title: exist
	 * @Description: TODO(判断键值是否存在)
	 * @param db
	 * @param key
	 * @return: boolean
	 * @author: Ian.Huang
	 * @date: 2017年11月12日 下午9:55:12
	 */
	public static boolean exist(int db, String key) {
    	ShardedJedis jedis = null;
    	boolean flag = false;
		try {
			jedis = getJedis(db);
			flag = jedis.exists(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
		return flag;
    }

	public static boolean exist(String key) {
		return exist(0, key);
	}

	/**
     * @Title: get
     * @Description: TODO(获取redis value(String))
     * @param db
     * @param key
     * @return: String
     * @author: Ian.Huang
     * @date: 2017年11月12日 下午9:53:32
     */
    public static String get(int db, String key) {
    	ShardedJedis jedis = null;
    	String value = null;
		try {
			jedis = getJedis(db);
			value = jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
        return value;
    }

    public static String get(String key) {
    	return get(0, key);
    }

    /**
     * @Title: getKeysLike
     * @Description: TODO(模糊查询)
     * @param db
     * @param likeKey 模糊匹配的key
     * @return: List<String> 查询的集合
     * @author: Ian.Huang
     * @date: 2017年11月12日 下午9:48:15
     */
    public static List<String> getKeysLike(int db, String likeKey) {
    	ShardedJedis jedis = null;
    	List<String> list = null;
    	try {
    		jedis = getJedis(db);
    		list = new ArrayList<String>();
    		Iterator<Jedis> iter = jedis.getAllShards().iterator();
        	while (iter.hasNext()) {
        		Iterator<String> i = iter.next().keys(likeKey).iterator();
        		while(i.hasNext()){
        			//遍历
        			list.add(i.next());
        		}
        	}
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
    	return list;
    }

    public static List<String> getKeysLike(String likeKey) {
    	return getKeysLike(0, likeKey);
    }

    /**
     * @Title: delKeysLike
     * @Description: TODO(模糊KEY批量删除)
     * @param db
     * @param likeKey 模糊匹配的key
     * @return: void 删除成功的条数
     * @author: Ian.Huang
     * @date: 2017年11月12日 下午9:45:50
     */
    public static void delKeysLike(int db, String likeKey) {
    	ShardedJedis jedis = null;
    	try {
    		jedis = getJedis(db);
	    	Iterator<Jedis> iter = jedis.getAllShards().iterator();
	    	 while (iter.hasNext()) {
                 Iterator<String> it = iter.next().keys(likeKey).iterator();
                 while(it.hasNext()){
                     jedis.del(it.next());
                 }
             }
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
    }

    public static void delKeysLike(String likeKey) {
    	delKeysLike(0, likeKey);
    }

    /**
     * @Title: remove
     * @Description: TODO(移除key value)
     * @param db
     * @param key
     * @return: void
     * @author: Ian.Huang
     * @date: 2017年11月12日 下午9:38:51
     */
    public static void remove(int db, String key) {
    	ShardedJedis jedis = null;
    	try {
			jedis= getJedis(db);
			jedis.del(key);
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
    }

    public static void remove(String key) {
    	remove(0, key);
    }

    /**
     * @Title: expireKey
     * @Description: TODO(延长key的过期时间)
     * @param db
     * @param key
     * @param seconds
     * @return: void
     * @author: Ian.Huang
     * @date: 2017年11月12日 下午9:37:41
     */
    public static void expireKey(int db, String key, int seconds) {
    	ShardedJedis jedis = null;
    	try {
	    	jedis = getJedis(db);
	    	jedis.expire(key, seconds);
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
    }

    public static void expireKey(String key, int seconds) {
    	expireKey(0, key, seconds);
    }

    /**
     * @Title: lpush
     * @Description: TODO(在key对应 list的头部添加字符串元素，栈-先进后出)
     * @param db
     * @param key
     * @param value
     * @return: void
     * @author: Ian.Huang
     * @date: 2017年11月12日 下午9:36:30
     */
    public static void lpush(int db, byte[] key, byte[] value) {
    	ShardedJedis jedis = null;
    	try {
    		jedis = getJedis(db);
    		jedis.lpush(key, value);
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
    }

    public static void lpush(byte[] key, byte[] value) {
    	lpush(0, key, value);
    }

    /**
     * @Title: rpush
     * @Description: TODO(在key对应 list的尾部添加字符串元素，队列-先进先出)
     * @param db
     * @param key
     * @param value
     * @return: void
     * @author: Ian.Huang
     * @date: 2017年11月12日 下午9:32:31
     */
    public static void rpush(int db, byte[]key, byte[]value) {
    	ShardedJedis jedis = null;
    	try {
	        jedis = getJedis(db);
	        jedis.rpush(key, value);
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
    }

    public static void rpush(byte[]key, byte[]value) {
    	rpush(0, key, value);
    }

    /**
     * @Title: flushDB
     * @Description: TODO(清空当前连接池数据库所有数据)
     * @param db
     * @return: void
     * @author: Ian.Huang
     * @date: 2017年11月12日 下午9:17:07
     */
    public static void flushDB(int db){
    	ShardedJedis jedis = null;
    	try {
	    	jedis = getJedis(db);
	    	Iterator<Jedis> iter = jedis.getAllShards().iterator();
	    	while (iter.hasNext()) {
				Jedis _jedis = iter.next();
				_jedis.flushDB();
            }
    	} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if(jedis != null) {
				jedis.close();
			}
		}
    }

    public static void flushDB(){
    	flushDB(0);
    }

    /**
     *
     * @Description: TODO()
     * @param jedis
     * @return: void
     * @author Ian.Huang
     * @date 2016年12月1日上午9:55:13
     */
    public static void close(ShardedJedis jedis) {
        try {
        	if(jedis != null) {
        		jedis.close();
        	}
        } catch (Exception e) {
        	e.printStackTrace();
        	logger.error(e);
            if (check_con()) {
                jedis.disconnect();
            }
        }
    }
/*---------------------------------------------------------------------------------------------------------------------------*/


//	/**
//	 * TODO redis idc机房使用 哨兵模式配置
//	 * */
//	private static final Logger logger = Logger.getLogger(RedisUtils.class);
//
//	//主
//	private static  final String MYMASTER="redis-test";
//
//	//#sentinel1的IP和端口
//	private static final String REDIS_SENTINEL_HOST1="rediscluster01.public.test.baozun.com:26379";
//
//	//#sentinel2的IP和端口
//	private static final String REDIS_SENTINEL_HOST2="rediscluster02.public.test.baozun.com:26379";
//
//	//#sentinel3的IP和端口
//	private static final String REDIS_SENTINEL_HOST3="rediscluster03.public.test.baozun.com:26379";
//
//	private static JedisSentinelPool sentinelPool=null;
//
//	/**
//	 * @Title: getJedis
//	 * @Description: TODO(获取Jedis连接)
//	 * @param db
//	 * @return: ShardedJedis
//	 * @author: Ian.Huang
//	 * @throws Exception
//	 * @date: 2017年11月16日 下午5:30:53
//	 */
//	private static Jedis getJedis(int db) throws Exception {
////		Object redisPool = SpringUtils.getBean(PREFIX_REDIS + db);
////		if(redisPool != null) {
////			return (ShardedJedis) (redisPool.getClass().getMethod(METHOD).invoke(redisPool));
////		} else {
////			throw new Exception("this redis pool ["+ db +"] is not exist");
////		}
//		try{
//			JedisPoolConfig config = new JedisPoolConfig();
//			//最大连接数
//			config.setMaxTotal(30);
//			//最大空闲时间
//			config.setMaxIdle(10);
//			//每次最大连接数
//			config.setNumTestsPerEvictionRun(1024);
//			//释放扫描的扫描间隔
//			config.setTimeBetweenEvictionRunsMillis(30000);
//			//连接的最小空间时间
//			config.setMinEvictableIdleTimeMillis(1800000);
//			//连接控歘按时间多久后释放，当空闲时间>该值且空闲连接>最大空闲连接数时直接释放
//			config.setSoftMinEvictableIdleTimeMillis(10000);
//			//获得链接时的最大等待毫秒数，小于0：阻塞不确定时间，默认-1
//			config.setMaxWaitMillis(1500);
//			//在获得链接的时候检查有效性，默认false
//			config.setTestOnBorrow(true);
//			//在空闲时检查有效性，默认false
//			config.setTestWhileIdle(true);
//			//连接耗尽时是否阻塞，false报异常，true阻塞超时,默认true
//			config.setBlockWhenExhausted(false);
//			Set<String> sentinels = new HashSet<String>();
//			sentinels.add(REDIS_SENTINEL_HOST1);
//			sentinels.add(REDIS_SENTINEL_HOST2);
//			sentinels.add(REDIS_SENTINEL_HOST3);
//			if(sentinelPool == null){
//				sentinelPool = new JedisSentinelPool(MYMASTER,
//						sentinels,config);
//			}
//
//			if(sentinelPool == null)   throw new Exception("this redis pool  is not exist");
//			Jedis jedis = sentinelPool.getResource();
//
//			if(jedis == null) throw new Exception("this redis pool  is not exist");
//			return jedis;
//		}catch(Exception e){
//			e.printStackTrace();
//			logger.error(e);
//		}
//		return  null;
//	}
//
//	/**
//	 * @Title: check_con
//	 * @Description: TODO(检查redis连接是否正常)
//	 * @param db
//	 * @return: boolean
//	 * @author: Ian.Huang
//	 * @date: 2017年11月12日 下午10:13:21
//	 */
//	public static boolean check_con(int db) {
//		Jedis jedis = null;
//		boolean flag = false;
//		try {
//			jedis = getJedis(db);
//			jedis.get("");
//			flag = true;
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e);
//		} finally {
//			if(jedis != null) {
//				jedis.close();
//			}
//		}
//		return flag;
//	}
//
//	public static boolean check_con() {
//		return check_con(0);
//	}
//
//	/**
//	 * @Title: set
//	 * @Description: TODO(添加key value)
//	 * @param db
//	 * @param key
//	 * @param value
//	 * @return: void
//	 * @author: Ian.Huang
//	 * @date: 2017年11月12日 下午9:58:06
//	 */
//	public static void set(int db, String key, String value) {
//		Jedis jedis = null;
//		try {
//			jedis = getJedis(db);
//			jedis.set(key, value);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e);
//		} finally {
//			if(jedis != null) {
//				jedis.close();
//			}
//		}
//	}
//
//	public static void set(String key, String value) {
//		set(0, key, value);
//	}
//
//	/**
//	 * @Title: set
//	 * @Description: TODO(添加key value 并且设置存活时间)
//	 * @param db
//	 * @param key
//	 * @param value
//	 * @param liveTime
//	 * @return: void
//	 * @author: Ian.Huang
//	 * @date: 2017年11月12日 下午10:01:25
//	 */
//	public static void set(int db, String key, String value, int liveTime) {
//		Jedis jedis = null;
//		try {
//			jedis = getJedis(db);
//			jedis.set(key, value);
//			jedis.expire(key, liveTime);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e);
//		} finally {
//			if(jedis != null) {
//				jedis.close();
//			}
//		}
//	}
//
//	public static void set(String key, String value, int liveTime) {
//		set(0, key, value, liveTime);
//	}
//
//	/**
//	 * @Title: set
//	 * @Description: TODO(添加key value 并且设置当天有效)
//	 * @param db
//	 * @param key
//	 * @param value
//	 * @param iso
//	 * @return: void
//	 * @author: Ian.Huang
//	 * @date: 2017年11月12日 下午10:04:39
//	 */
//	public static void set(int db, String key, String value, boolean iso) {
//		Jedis jedis = null;
//		try {
//			jedis = getJedis(db);
//			jedis.set(key, value);
//			if(iso) {
//				SimpleDateFormat formart = new SimpleDateFormat("HH:mm:ss");
//				jedis.expire(key, (int)((formart.parse("24:00:00").getTime() - formart.parse(formart.format(new Date())).getTime()) / 1000));
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e);
//		} finally {
//			if(jedis != null) {
//				jedis.close();
//			}
//		}
//	}
//
//	public static void set(String key, String value, boolean iso) {
//		set(0, key, value, iso);
//	}
//
//	/**
//	 * @Title: exist
//	 * @Description: TODO(判断键值是否存在)
//	 * @param db
//	 * @param key
//	 * @return: boolean
//	 * @author: Ian.Huang
//	 * @date: 2017年11月12日 下午9:55:12
//	 */
//	public static boolean exist(int db, String key) {
//		Jedis jedis = null;
//		boolean flag = false;
//		try {
//			jedis = getJedis(db);
//			flag = jedis.exists(key);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e);
//		} finally {
//			if(jedis != null) {
//				jedis.close();
//			}
//		}
//		return flag;
//	}
//
//	public static boolean exist(String key) {
//		return exist(0, key);
//	}
//
//	/**
//	 * @Title: get
//	 * @Description: TODO(获取redis value(String))
//	 * @param db
//	 * @param key
//	 * @return: String
//	 * @author: Ian.Huang
//	 * @date: 2017年11月12日 下午9:53:32
//	 */
//	public static String get(int db, String key) {
//		Jedis jedis = null;
//		String value = null;
//		try {
//			jedis = getJedis(db);
//			value = jedis.get(key);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e);
//		} finally {
//			if(jedis != null) {
//				jedis.close();
//			}
//		}
//		return value;
//	}
//
//	public static String get(String key) {
//		return get(0, key);
//	}
//
//	/**
//	 * @Title: getKeysLike
//	 * @Description: TODO(模糊查询)
//	 * @param db
//	 * @param likeKey 模糊匹配的key
//	 * @return: List<String> 查询的集合
//	 * @author: Ian.Huang
//	 * @date: 2017年11月12日 下午9:48:15
//	 */
//	public static List<String> getKeysLike(int db, String likeKey) {
//		Jedis jedis = null;
//		List<String> list = null;
//		try {
////    		jedis = getJedis(db);
////    		list = new ArrayList<String>();
////    		Iterator<Jedis> iter = jedis.getAllShards().iterator();
////        	while (iter.hasNext()) {
////        		Iterator<String> i = iter.next().keys(likeKey).iterator();
////        		while(i.hasNext()){
////        			//遍历
////        			list.add(i.next());
////        		}
////        	}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e);
//		} finally {
//			if(jedis != null) {
//				jedis.close();
//			}
//		}
//		return list;
//	}
//
//	public static List<String> getKeysLike(String likeKey) {
//		return getKeysLike(0, likeKey);
//	}
//
//	/**
//	 * @Title: delKeysLike
//	 * @Description: TODO(模糊KEY批量删除)
//	 * @param db
//	 * @param likeKey 模糊匹配的key
//	 * @return: void 删除成功的条数
//	 * @author: Ian.Huang
//	 * @date: 2017年11月12日 下午9:45:50
//	 */
//	public static void delKeysLike(int db, String likeKey) {
//		Jedis jedis = null;
//		try {
////    		jedis = getJedis(db);
////	    	Iterator<Jedis> iter = jedis.getAllShards().iterator();
////	    	 while (iter.hasNext()) {
////                 Iterator<String> it = iter.next().keys(likeKey).iterator();
////                 while(it.hasNext()){
////                     jedis.del(it.next());
////                 }
////             }
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e);
//		} finally {
//			if(jedis != null) {
//				jedis.close();
//			}
//		}
//	}
//
//	public static void delKeysLike(String likeKey) {
//		delKeysLike(0, likeKey);
//	}
//
//	/**
//	 * @Title: remove
//	 * @Description: TODO(移除key value)
//	 * @param db
//	 * @param key
//	 * @return: void
//	 * @author: Ian.Huang
//	 * @date: 2017年11月12日 下午9:38:51
//	 */
//	public static void remove(int db, String key) {
//		Jedis jedis = null;
//		try {
//			jedis= getJedis(db);
//			jedis.del(key);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e);
//		} finally {
//			if(jedis != null) {
//				jedis.close();
//			}
//		}
//	}
//
//	public static void remove(String key) {
//		remove(0, key);
//	}
//
//	/**
//	 * @Title: expireKey
//	 * @Description: TODO(延长key的过期时间)
//	 * @param db
//	 * @param key
//	 * @param seconds
//	 * @return: void
//	 * @author: Ian.Huang
//	 * @date: 2017年11月12日 下午9:37:41
//	 */
//	public static void expireKey(int db, String key, int seconds) {
//		Jedis jedis = null;
//		try {
//			jedis = getJedis(db);
//			jedis.expire(key, seconds);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e);
//		} finally {
//			if(jedis != null) {
//				jedis.close();
//			}
//		}
//	}
//
//	public static void expireKey(String key, int seconds) {
//		expireKey(0, key, seconds);
//	}
//
//	/**
//	 * @Title: lpush
//	 * @Description: TODO(在key对应 list的头部添加字符串元素，栈-先进后出)
//	 * @param db
//	 * @param key
//	 * @param value
//	 * @return: void
//	 * @author: Ian.Huang
//	 * @date: 2017年11月12日 下午9:36:30
//	 */
//	public static void lpush(int db, byte[] key, byte[] value) {
//		Jedis jedis = null;
//		try {
//			jedis = getJedis(db);
//			jedis.lpush(key, value);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e);
//		} finally {
//			if(jedis != null) {
//				jedis.close();
//			}
//		}
//	}
//
//	public static void lpush(byte[] key, byte[] value) {
//		lpush(0, key, value);
//	}
//
//	/**
//	 * @Title: rpush
//	 * @Description: TODO(在key对应 list的尾部添加字符串元素，队列-先进先出)
//	 * @param db
//	 * @param key
//	 * @param value
//	 * @return: void
//	 * @author: Ian.Huang
//	 * @date: 2017年11月12日 下午9:32:31
//	 */
//	public static void rpush(int db, byte[]key, byte[]value) {
//		Jedis jedis = null;
//		try {
//			jedis = getJedis(db);
//			jedis.rpush(key, value);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e);
//		} finally {
//			if(jedis != null) {
//				jedis.close();
//			}
//		}
//	}
//
//	public static void rpush(byte[]key, byte[]value) {
//		rpush(0, key, value);
//	}
//
//	/**
//	 * @Title: flushDB
//	 * @Description: TODO(清空当前连接池数据库所有数据)
//	 * @param db
//	 * @return: void
//	 * @author: Ian.Huang
//	 * @date: 2017年11月12日 下午9:17:07
//	 */
//	public static void flushDB(int db){
//		Jedis jedis = null;
//		try {
////	    	jedis = getJedis(db);
////	    	Iterator<Jedis> iter = jedis.getAllShards().iterator();
////	    	while (iter.hasNext()) {
////				Jedis _jedis = iter.next();
////				_jedis.flushDB();
////            }
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e);
//		} finally {
//			if(jedis != null) {
//				jedis.close();
//			}
//		}
//	}
//
//	public static void flushDB(){
//		flushDB(0);
//	}
//
//	/**
//	 *
//	 * @Description: TODO()
//	 * @param jedis
//	 * @return: void
//	 * @author Ian.Huang
//	 * @date 2016年12月1日上午9:55:13
//	 */
//	public static void close(Jedis jedis) {
//		try {
//			if(jedis != null) {
//				jedis.close();
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(e);
//			if (check_con()) {
//				jedis.disconnect();
//			}
//		}
//	}
	
}
