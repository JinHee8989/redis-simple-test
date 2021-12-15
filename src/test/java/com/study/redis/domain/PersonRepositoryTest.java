package com.study.redis.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration
public class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

    @Resource(name="redisTemplate")
    private ValueOperations<String, String> valueOperations;

    @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOperations;

    @Resource(name = "redisTemplate")
    private HashOperations<String, String, String> hashOperations;

    @Resource(name = "redisTemplate")
    private SetOperations<String, String> setOperations;

    @Resource(name="redisTemplate")
    private ZSetOperations<String, String> zSetOperations;

    @Test
    public void basicCrudOperations(){

        Address home = new Address("Korea","SungNam");
        Person person = new Person(null,"jinhee",home);

        //when
        Person savedPerson = personRepository.save(person);

        System.out.println("savedPerson.getId() = " + savedPerson.getId());
        System.out.println("savedPerson.getName() = " + savedPerson.getName());
        
        
        //then
        Optional<Person> findPerson = personRepository.findById(savedPerson.getId());
        Iterable<Person> findAllPerson = personRepository.findAll();
        System.out.println("findPerson = " + findPerson);

        System.out.println("삭제 전  = " + findAllPerson);
        personRepository.deleteAll();
        System.out.println("삭제 후  = " + findAllPerson);

        assertThat(findPerson.isPresent()).isEqualTo(Boolean.TRUE);
        assertThat(findPerson.get().getName()).isEqualTo(person.getName());

    }


    @Test
    public void init() {

        //List put
        listOperations.rightPush("test:task", "자기소개");
        listOperations.rightPush("test:task", "취미소개");
        listOperations.rightPush("test:task", "희망사항");

        //hash put
        hashOperations.put("test:user:info", "name", "변진희");
        hashOperations.put("test:user:info", "age", "33");

        //set put
        setOperations.add("test:user:hobby", "개발");
        setOperations.add("test:user:hobby", "잠");
        setOperations.add("test:user:hobby", "쇼핑");

        //zSet
        zSetOperations.add("test:user:hope", "내 집 구입", 1);
        zSetOperations.add("test:user:hope", "연봉상승", 2);
        zSetOperations.add("test:user:hope", "내 산 구입", 3);
        zSetOperations.add("test:user:hope", "내 차 구입", 4);

    }


    @Test
    public void redisTest() {

        String task = listOperations.leftPop("test:task");
        StringBuilder sb = new StringBuilder();

        while(task != null) {

            switch (task) {
                case "자기소개":
                    Map<String, String> intro = hashOperations.entries("test:user:info");
                    sb.append("***** 자기소개 *****\n");
                    sb.append("이름은 ");
                    sb.append(intro.get("name") + ", ");
                    sb.append("나이는 ");
                    sb.append(intro.get("age") + "입니다.");
                    break;

                case "취미소개":
                    Set<String> hobbies = setOperations.members("test:user:hobby");
                    sb.append("***** 취미소개 *****\n");
                    sb.append("취미는 ");
                    for (String hobby : hobbies) {
                        sb.append(hobby + "..");
                    }
                    break;
                case "희망사항":
                    Set<String> wishes = zSetOperations.range("test:user:hope", 0, 3);
                    sb.append("***** 희망소개 *****\n");
                    for (String wish : wishes) {
                        sb.append(wish + " ");
                    }
                    break;
                default:
                    sb.append("none");
            }

            task = listOperations.leftPop("tast:task");
        }

            System.out.println("result ==== " + sb.toString());
        }




}
