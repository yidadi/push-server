package com.even.push.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.MapPropertySource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClusterConnection;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther yidadi
 * @Date 17-9-7 下午2:21
 */
public class RedisConfig {
    @Value("${com.even.redis.nodes}")
    private String clusterNodes;

    @Value("${com.even.redis.timeout}")
    private Long timeout;

    @Value("${com.even.redis.max-redirects}")
    private int redirects;

    @Bean
    public RedisClusterConfiguration getClusterConfiguration() {

        Map<String, Object> source = new HashMap<String, Object>();
        source.put("spring.redis.cluster.nodes", clusterNodes);
        source.put("spring.redis.cluster.timeout", timeout);
        source.put("spring.redis.cluster.max-redirects", redirects);
        return new RedisClusterConfiguration(new MapPropertySource("RedisClusterConfiguration", source));

    }

    @Bean
    public JedisConnectionFactory getConnectionFactory() {
        return new JedisConnectionFactory(getClusterConfiguration());
    }

    @Bean
    public JedisClusterConnection getJedisClusterConnection() {
        return (JedisClusterConnection) getConnectionFactory().getConnection();

    }

    @Bean
    public RedisTemplate getRedisTemplate() {
        RedisTemplate clusterTemplate = new RedisTemplate();
        clusterTemplate.setConnectionFactory(getConnectionFactory());
        clusterTemplate.setKeySerializer(new StringRedisSerializer());
        clusterTemplate.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return clusterTemplate;
    }
}
