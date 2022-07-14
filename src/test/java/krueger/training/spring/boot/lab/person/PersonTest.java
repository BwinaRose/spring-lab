package krueger.training.spring.boot.lab.person;

import krueger.training.spring.boot.lab.person.models.Person;
import krueger.training.spring.boot.lab.phonenumber.models.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PersonTest {

    private Person emptyPerson;
    private Person emptyPerson2;
    private Person person;
    private Person person2;
    @BeforeEach
    public void setUp(){
        emptyPerson = new Person();
        emptyPerson2 = new Person();

        List<PhoneNumber> numbers = new ArrayList<>();
        numbers.add(new PhoneNumber("55555555555",true));
        numbers.add(new PhoneNumber("55555555555",false));

        person = new Person("Sabrina","Rose", numbers);
        person.setId(1);

        person2 = new Person("Sabrina","Rose",numbers);
        person2.setId(2);

    }

    @Test
    public void testEmptyEquals() throws Exception {
        assertTrue(emptyPerson.equals(emptyPerson2), "Both are empty");
    }
    @Test
    public void testContentEquals() throws Exception {
        assertFalse(
                person.equals(person2),
                "Both non-empty Widget instances should be equal");
    }

    @Test
    public void testNotEquals() throws Exception {
        assertFalse(
                emptyPerson.equals(person2),
                "The Widget instances should not be equal");
    }

    @Test
    public void testEmptyHashCode() throws Exception {
        assertEquals(
                emptyPerson.hashCode(),
                emptyPerson2.hashCode(),
                "Both empty Widget instances should have the same hashCode");
    }

    @Test
    public void testContentHashCode() throws Exception {
        assertNotEquals(
                person.hashCode(),
                person2.hashCode(),
                "Both non-empty Widget instances should have the same hashCode");
    }

    @Test
    public void testHashCode() throws Exception {

        assertNotEquals(
                emptyPerson.hashCode(),
                person2.hashCode(),
                "The Widget instances should not have the same hashCode");
    }

    @Test
    public void testEmptyToString() throws Exception {

        assertEquals(
                emptyPerson.toString(),
                emptyPerson2.toString(),
                "Both empty Widget instances should have the same toString");
    }

    @Test
    public void testContentToString() throws Exception {

        assertNotEquals(
                person.toString(),
                person2.toString(),
                "Both non-empty Widget instances should have the same toString");
    }

    @Test
    public void testNotToString() throws Exception {

        assertNotEquals(
                emptyPerson.toString(),
                person2.toString(),
                "The Widget instances should not have the same toString");
    }
}
