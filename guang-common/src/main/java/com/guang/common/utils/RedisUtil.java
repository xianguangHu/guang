package com.guang.common.utils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 工具类
 * @author huxianguang
 * @create 2017-10-25-下午8:38
 **/
public class RedisUtil {


    private static Logger _log = LoggerFactory.getLogger(RedisUtil.class);

    /**
     * 是线程安全的redis链接池
     */
    private static JedisPool jedisPool = null;


    /**
     * 初始化redis连接池
     */
    private static void initialPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        //最大连接数
        config.setMaxTotal(500);
        //最大空闲数
        config.setMaxIdle(5);
        //获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间,  默认-1
        config.setMaxWaitMillis(10000);
        //在连接时候检查有效性
        config.setTestOnBorrow(false);
        jedisPool = new JedisPool(config,"localhost",6379);


    }


    /**
     * 设置String过期时间
     * @param key
     * @param value
     * @param seconds
     */
    public synchronized static void set(String key,String value, int seconds) {
        try {
            value = StringUtils.isBlank(value) ? "" : value;
            Jedis jedis = getJedis();
            jedis.setex(key,seconds,value);
            jedis.close();
        } catch (Exception e) {
            _log.error("Set keyex error : " + e);
        }
    }

    /**
     * 获取String值
     * @param key
     * @return
     */
    public synchronized static String get(String key) {
        Jedis jedis = getJedis();
        if (null == jedis) {
            return null;
        }
        String value = jedis.get(key);
        //关闭jedis
        jedis.close();
        return value;
    }

    /**
     * 同步获取Jedis实例
     * @return
     */
    public synchronized static Jedis getJedis() {
        poolInit();
        Jedis jedis = null;
        if (null != jedisPool) {
            jedis = jedisPool.getResource();
            //jedis.auth(AESUtil.AESDecode("FNFl9F2O2Skb8yoKM0jhHA=="));
        }
        return jedis;
    }

    /**
     * lpush
     * @param key
     * @param key
     */
    public synchronized static void lpush(String key, String... strings) {
        try {
            Jedis jedis = RedisUtil.getJedis();
            jedis.lpush(key, strings);
            jedis.close();
        } catch (Exception e) {
            _log.error("lpush error : " + e);
        }
    }


    /**
     * 在多线程环境下同步初始化
     */
    private static void poolInit() {
        if (null == jedisPool) {
            initialPool();
        }
    }


}
