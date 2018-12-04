package com.mszq.uas.sso;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

@Log4j2
@Configuration
public class SessionConfiguration {

    @Bean("springSessionDefaultRedisSerializer")
    public RedisSerializer<Object> defaultRedisSerializer(){
        return valueSerializer();
    }

    private RedisSerializer<Object> valueSerializer() {
        return new GenericJackson2JsonRedisSerializer();
    }

}

