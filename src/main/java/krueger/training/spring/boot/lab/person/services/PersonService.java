package krueger.training.spring.boot.lab.person.services;

import krueger.training.spring.boot.lab.person.exceptions.PersonNotFoundException;
import krueger.training.spring.boot.lab.person.models.Person;

import java.util.List;

public interface PersonService {
    Person create(Person person);
    Person getPersonById(Integer id) throws PersonNotFoundException;
    List<Person> getAllPersons();
    Person updatePerson(Integer id, Person personDetails) throws PersonNotFoundException;
    Boolean deletePerson(Integer id) throws PersonNotFoundException;
    List<Person> findByLastName(String lastName) throws PersonNotFoundException;
}
