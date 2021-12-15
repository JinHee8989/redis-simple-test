package com.study.redis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories //redis repository를 사용한다고 지정
public class RedisConfig {

}
