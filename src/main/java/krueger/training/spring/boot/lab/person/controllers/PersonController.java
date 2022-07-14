package krueger.training.spring.boot.lab.person.controllers;

import krueger.training.spring.boot.lab.person.exceptions.PersonNotFoundException;
import krueger.training.spring.boot.lab.person.models.Person;
import krueger.training.spring.boot.lab.person.services.PersonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {

    private final Logger logger = LoggerFactory.getLogger(PersonController.class);
    private PersonService personService;

    @Autowired
    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @GetMapping
    public List<Person> getAllPeople(){
        return personService.getAllPersons();
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody Person person) {
        person = personService.create(person);
        return new ResponseEntity<>(person,HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable  Integer id) throws PersonNotFoundException {
        Person person = personService.getPersonById(id);
        return ResponseEntity.ok(person);
    }

    @PutMapping("{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Integer id,@RequestBody Person personDetails) throws PersonNotFoundException {
        Person updatePerson = personService.updatePerson(id, personDetails);
        return ResponseEntity.ok(updatePerson);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable Integer id) throws PersonNotFoundException {
        personService.deletePerson(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
