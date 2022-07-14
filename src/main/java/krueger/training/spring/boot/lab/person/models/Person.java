package krueger.training.spring.boot.lab.person.models;

import krueger.training.spring.boot.lab.phonenumber.models.PhoneNumber;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Getter
@Setter
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String firstName;
    private String lastName;
    @OneToMany(cascade = CascadeType.ALL)
    private List<PhoneNumber> phoneNumbers;

    public Person() {
    }

    public Person(String firstName, String lastName, List<PhoneNumber> phoneNumbers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumbers = phoneNumbers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(id, person.id) && Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(phoneNumbers, person.phoneNumbers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, phoneNumbers);
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumbers=" + phoneNumbers +
                '}';
    }

}
