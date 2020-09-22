package com.jt.test.redis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;



import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisSentinelPool;

public class TestRedis {
	/**
	 * 1.实例化jedis对象(IP端口
	 * 2.实现redis取值赋值操作
	 */
	/*@Test
	public void test01(){
		Jedis jedis =new Jedis("192.168.239.132",6379);
		jedis.set("name", "tomcat猫");
		System.out.println("获取redis数据:"+jedis.get("name"));
	}
	//测试redis分片实现redis的redis内存动态扩容
	@Test
	public void test02(){
		//定义redis池的配置文件,不写有默认值
		JedisPoolConfig poolConfig=new JedisPoolConfig();
		poolConfig.setMaxTotal(1000);
		poolConfig.setMaxIdle(200);
		poolConfig.setMinIdle(10);
		poolConfig.setTestOnBorrow(true);//连接前的校验
		//定义redis分片的节点信息
		List<JedisShardInfo> shards= new ArrayList<JedisShardInfo>();
		shards.add(new JedisShardInfo("192.168.239.132",6379));
		shards.add(new JedisShardInfo("192.168.239.132",6380));
		shards.add(new JedisShardInfo("192.168.239.132",6381));
		
		ShardedJedisPool jedisPool=new ShardedJedisPool(poolConfig, shards);
		//已经把三个redis放入了池子,现在需要获取redis,用到getResource
		ShardedJedis shardedJedis = jedisPool.getResource();
		
		shardedJedis.set("name", "我是redis分片");
		System.out.println("获取redis信息:"+shardedJedis.get("name"));
	}*/
	
	
	//哨兵测试
	@Test
	public void test03(){
		Set<String> sentinels=new HashSet<String>();
		//new HostAndPort("192.168.239.132", 26379).toString();
		sentinels.add("192.168.239.132:26379");
		sentinels.add("192.168.239.132:26380");
		sentinels.add("192.168.239.132:26381");
		
		//定义哨兵的连接池对象
		JedisSentinelPool sentinelPool=new JedisSentinelPool("mymaster", sentinels);
		Jedis resource = sentinelPool.getResource();
		resource.set("name", "我是哨兵的redis");
		System.out.println("获取哨兵redis的数据:"+resource.get("name")+resource.info());
	}
	
	
	/**
	 * 测试redis集群
	 * 1.redis的节点3主6从9个节点
	 * 2.每个节点需要通过IP:端口的形式进行衔接
	 * 3.创建集群的连接对象API调用
	 */
	@Test
	public void testCluster(){
		Set<HostAndPort> nodes=new HashSet<>();
		String host="192.168.239.132";
		nodes.add(new HostAndPort(host,7000));
		nodes.add(new HostAndPort(host,7001));
		nodes.add(new HostAndPort(host,7002));
		nodes.add(new HostAndPort(host,7003));
		nodes.add(new HostAndPort(host,7004));
		nodes.add(new HostAndPort(host,7005));
		nodes.add(new HostAndPort(host,7006));
		nodes.add(new HostAndPort(host,7007));
		nodes.add(new HostAndPort(host,7008));
		JedisCluster jedisCluster =new JedisCluster(nodes);
		jedisCluster.set("p", "集群搭建完成");
		System.out.println("获取数据="+jedisCluster.get("p"));
	}
	
	

	
	
	
	
	
}
