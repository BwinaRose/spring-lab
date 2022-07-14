package krueger.training.spring.boot.lab.phonenumber.models;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String phoneNumber;
    private Boolean isMobile;

    public PhoneNumber(String phoneNumber, Boolean isMobile) {
        this.phoneNumber = phoneNumber;
        this.isMobile = isMobile;
    }
}
