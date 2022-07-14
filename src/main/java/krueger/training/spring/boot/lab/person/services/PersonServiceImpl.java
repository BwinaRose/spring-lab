package krueger.training.spring.boot.lab.person.services;

import krueger.training.spring.boot.lab.person.exceptions.PersonNotFoundException;
import krueger.training.spring.boot.lab.person.models.Person;
import krueger.training.spring.boot.lab.person.repos.PersonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonServiceImpl implements PersonService {

    private PersonRepo personRepo;

    @Autowired
    public PersonServiceImpl(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @Override
    public Person create(Person person) {
        return personRepo.save(person);
    }

    @Override
    public Person getPersonById(Integer id) throws PersonNotFoundException {
        Person person = personRepo.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not exist with id:" + id));
        return person;
    }

    @Override
    public List<Person> getAllPersons() {
        return personRepo.findAll();
    }

    @Override
    public Person updatePerson(Integer id, Person personDetails) throws PersonNotFoundException {
        Person updatePerson = personRepo.findById(id)
                .orElseThrow(() -> new PersonNotFoundException("Person not exist with id: " + id));
        updatePerson.setFirstName(personDetails.getFirstName());
        updatePerson.setLastName(personDetails.getLastName());
        updatePerson.setPhoneNumbers(personDetails.getPhoneNumbers());
        return personRepo.save(updatePerson);
    }

    @Override
    public Boolean deletePerson(Integer id) throws PersonNotFoundException {
        Optional<Person> personOptional = personRepo.findById(id);
        if(personOptional.isEmpty()){
            throw new PersonNotFoundException("Person does not exist, can not delete");
        }
        Person personToDelete = personOptional.get();
        personRepo.delete(personToDelete);
        return true;
    }

    @Override
    public List<Person> findByLastName(String lastName) throws PersonNotFoundException {
        List<Person> personList = personRepo.findPersonByLastName(lastName);
        if(personList.isEmpty()){
            throw new PersonNotFoundException("No person with last name exists");
        }
        return personList;
    }
}
