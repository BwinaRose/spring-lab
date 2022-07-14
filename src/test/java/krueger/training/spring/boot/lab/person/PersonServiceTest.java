package krueger.training.spring.boot.lab.person;

import krueger.training.spring.boot.lab.person.exceptions.PersonNotFoundException;
import krueger.training.spring.boot.lab.person.models.Person;
import krueger.training.spring.boot.lab.person.repos.PersonRepo;
import krueger.training.spring.boot.lab.person.services.PersonService;
import krueger.training.spring.boot.lab.phonenumber.models.PhoneNumber;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class PersonServiceTest {

    @Autowired
    private PersonService personService;

    @MockBean
    private PersonRepo mockPersonRepo;

    private Person mockPerson;
    private Person mockResponsePerson1;
    private Person mockResponsePerson2;

    @BeforeEach
    public void setUp(){
        List<PhoneNumber> numbers = new ArrayList<>();
        numbers.add(new PhoneNumber("5555555555",true));
        numbers.add(new PhoneNumber("6666666666",false));
        mockPerson = new Person("Sabrina","Rose", numbers);

        mockResponsePerson1 = new Person("Bwina","Rose", numbers);
        mockResponsePerson1.setId(1);

        mockResponsePerson2 = new Person("Sabrina","Krueger", numbers);
        mockResponsePerson2.setId(2);
    }

    @Test
    public void createPersonTestSuccess(){
        BDDMockito.doReturn(mockResponsePerson1).when(mockPersonRepo).save(ArgumentMatchers.any());
        Person returnedPerson = personService.create(mockPerson);
        Assertions.assertNotNull(returnedPerson, "Person should not be null");
        Assertions.assertEquals(returnedPerson.getId(),1 );
    }

    @Test
    public void getPersonByIdTestSuccess() throws PersonNotFoundException {
        BDDMockito.doReturn(Optional.of(mockResponsePerson1)).when(mockPersonRepo).findById(1);
        Person foundPerson = personService.getPersonById(1);
        Assertions.assertEquals(mockResponsePerson1.toString(), foundPerson.toString());
    }

    @Test
    public void getPersonByIdTestFail() {
        BDDMockito.doReturn(Optional.empty()).when(mockPersonRepo).findById(1);
        Assertions.assertThrows(PersonNotFoundException.class, () -> {
            personService.getPersonById(1);
        });
    }

    @Test
    public void getAllPersonsTestSuccess(){
        List<Person> people = new ArrayList<>();
        people.add(mockResponsePerson1);
        people.add(mockResponsePerson2);

        BDDMockito.doReturn(people).when(mockPersonRepo).findAll();

        List<Person> responsePeople = personService.getAllPersons();
        Assertions.assertIterableEquals(people,responsePeople);
    }

    @Test
    public void updatePersonTestSuccess() throws PersonNotFoundException {
        List<PhoneNumber> numbers = new ArrayList<>();
        numbers.add(new PhoneNumber("5555555555",true));
        numbers.add(new PhoneNumber("6666666666",false));
        Person expectedPersonUpdate = new Person("Sabrina Rose","Krueger", numbers);

        BDDMockito.doReturn(Optional.of(mockResponsePerson1)).when(mockPersonRepo).findById(1);
        BDDMockito.doReturn(expectedPersonUpdate).when(mockPersonRepo).save(ArgumentMatchers.any());

        Person actualPerson = personService.updatePerson(1, expectedPersonUpdate);
        Assertions.assertEquals(expectedPersonUpdate.toString(), actualPerson.toString());
    }

    @Test
    public void updatePersonTestFail()  {
        List<PhoneNumber> numbers = new ArrayList<>();
        numbers.add(new PhoneNumber("5555555555",true));
        numbers.add(new PhoneNumber("6666666666",false));
        Person expectedPersonUpdate = new Person("Sabrina Rose","Krueger", numbers);
        BDDMockito.doReturn(Optional.empty()).when(mockPersonRepo).findById(1);
        Assertions.assertThrows(PersonNotFoundException.class, ()-> {
            personService.updatePerson(1, expectedPersonUpdate);
        });
    }

    @Test
    public void deletePersonTestSuccess() throws PersonNotFoundException {
        BDDMockito.doReturn(Optional.of(mockResponsePerson1)).when(mockPersonRepo).findById(1);
        Boolean actualResponse = personService.deletePerson(1);
        Assertions.assertTrue(actualResponse);
    }

    @Test
    public void deletePersonTestFail()  {
        BDDMockito.doReturn(Optional.empty()).when(mockPersonRepo).findById(1);
        Assertions.assertThrows(PersonNotFoundException.class, ()-> {
            personService.deletePerson(1);
        });
    }

    @Test
    public void getPersonByLastNameTestSuccess() throws PersonNotFoundException {
        List<Person> expected = List.of(mockResponsePerson1);
        BDDMockito.doReturn(expected).when(mockPersonRepo).findPersonByLastName("Rose");
        List<Person> foundPeople = personService.findByLastName("Rose");
        Assertions.assertEquals(expected, foundPeople);
    }

    @Test
    public void getPersonByLastNameTestFail() {
        List<Person> expected = new ArrayList<>();
        BDDMockito.doReturn(expected).when(mockPersonRepo).findPersonByLastName("Rose");
        Assertions.assertThrows(PersonNotFoundException.class, () -> {
            personService.findByLastName("Rose");
        });
    }

}
