package com.gidraph.redisDemo.configs;

import com.gidraph.redisDemo.domains.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@Slf4j
public class RedisConfig {


    @Primary
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();  
             
        log.info("first");
        return jedisConFactory;
    }


    @Bean
    @Primary
    public RedisOperations<String, Message> redisOperations() {
       
        Jackson2JsonRedisSerializer<Message> serializer = new Jackson2JsonRedisSerializer<>(Message.class);

        log.info("second");
        RedisTemplate<String, Message> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        template.setDefaultSerializer(serializer);
        template.setKeySerializer(new StringRedisSerializer());

        return template;
    }
}
