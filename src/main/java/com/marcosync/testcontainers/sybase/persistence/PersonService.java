package com.marcosync.testcontainers.sybase.persistence;
import com.marcosync.testcontainers.sybase.domain.person.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person save(Person person) {
        PersonEntity personEntity = new PersonEntity();
        personEntity.setName(person.getName());
        personEntity.setAge(person.getAge());
        return mapToDomain(personRepository.save(personEntity));
    }

    public List<Person> getAllPersons() {
        List<PersonEntity> personEntities = personRepository.findAll();
        return personEntities.stream().map(this::mapToDomain).collect(Collectors.toList());
    }

    private Person mapToDomain(PersonEntity personEntity) {
        return new Person(personEntity.getUuid(), personEntity.getName(), personEntity.getAge());
    }
}
