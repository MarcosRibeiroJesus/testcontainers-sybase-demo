package com.marcosync.testcontainers.sybase.in;

import com.marcosync.testcontainers.sybase.domain.person.Person;
import com.marcosync.testcontainers.sybase.persistence.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {
    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        Person savedPerson = personService.save(person);
        return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Person>> getAllPersons() {
        List<Person> people = personService.getAllPersons();
        return new ResponseEntity<>(people, HttpStatus.OK);
    }
}
