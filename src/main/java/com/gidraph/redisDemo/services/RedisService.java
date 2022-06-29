package com.gidraph.redisDemo.services;

import com.gidraph.redisDemo.domains.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisService {


    final RedisOperations redisOperations;

    public boolean saveMessage(Message saveMe){
        try{
            redisOperations.opsForValue().set(saveMe.getSender(),saveMe);
            redisOperations.expire(saveMe.getSender(),saveMe.getExpireIn(), TimeUnit.SECONDS);
            return true;
        }catch (Exception e ){
            log.error("Something went wrong saving the message "+e.getMessage());
        }
        return false;
    }

    public Message getMessage(String key){
        try{
            Message message = (Message) redisOperations.opsForValue().get(key);
            return message;
        }catch (Exception e ){
            log.error("Something went wrong saving the message "+e.getMessage());
        }
        return null;
    }

    public List<Message> getAllMessages(){
        try{
            Set<String> keys = redisOperations.keys("*");
            List<Message> results = new ArrayList<>();

            keys.forEach(it ->{
                Message message = getMessage(it);
                if(message != null){
                    results.add(message);
                }
            });
            return results;
        }catch (Exception e ){
            log.error("Something went wrong saving the message "+e.getMessage());
        }
        return null;
    }
}
