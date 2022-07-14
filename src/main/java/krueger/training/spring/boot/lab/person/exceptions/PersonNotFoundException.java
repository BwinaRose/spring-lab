package krueger.training.spring.boot.lab.person.exceptions;

public class PersonNotFoundException extends Exception{
    public PersonNotFoundException(String message) {
        super(message);
    }
}
