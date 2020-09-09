package com.fastcampus.javaallinone.project3.mycontact.service;

import com.fastcampus.javaallinone.project3.mycontact.controller.dto.PersonDto;
import com.fastcampus.javaallinone.project3.mycontact.domain.Person;
import com.fastcampus.javaallinone.project3.mycontact.repository.PersonRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {
    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;


    @Test
    void getPeopleByName() {
        when(personRepository.findByName("dosang"))
                .thenReturn(Lists.newArrayList(new Person("dosang")));

        List<Person> result = personService.getPeopleByName("dosang");

        assertThat(result.size()).isEqualTo(1);
        assertThat(result.get(0).getName()).isEqualTo("dosang");
    }

    @Test
    void getPerson() {
        when(personRepository.findById(1L))
                .thenReturn(Optional.of(new Person("dosang")));

        Person person = personService.getPerson(1L);

        assertThat(person.getName()).isEqualTo("dosang");
    }

    @Test
    void getPersonIfNotFound(){
        when(personRepository.findById(1L))
                .thenReturn(Optional.empty());

        Person person = personService.getPerson(1L);

        assertThat(person).isNull();
    }

    @Test
    void put(){
        PersonDto dto = PersonDto.of("dosang", "programming", "강동", LocalDate.now(), "programmer", "010-0000-1111");

        personService.put(dto);

        verify(personRepository, times(1)).save(any(Person.class));
    }
}