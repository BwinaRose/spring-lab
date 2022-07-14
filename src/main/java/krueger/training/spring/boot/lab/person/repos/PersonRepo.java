package krueger.training.spring.boot.lab.person.repos;

import krueger.training.spring.boot.lab.person.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PersonRepo extends JpaRepository<Person, Integer> {
    List<Person> findPersonByLastName(String lastName);
}
