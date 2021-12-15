package com.study.redis.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("people") //해당 엔티티가 redis엔티티임을 명시
@Data
@AllArgsConstructor
public class Person {


    @Id
    Long id;
    String name;
    String address;


}
