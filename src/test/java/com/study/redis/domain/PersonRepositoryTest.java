package com.study.redis.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ContextConfiguration
public class PersonRepositoryTest {

    @Autowired
    PersonRepository personRepository;

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

        assertThat(findPerson.isPresent()).isEqualTo(Boolean.TRUE);
        assertThat(findPerson.get().getName()).isEqualTo(person.getName());





    }


}
