package krueger.training.spring.boot.lab.person;

import krueger.training.spring.boot.lab.person.models.Person;
import krueger.training.spring.boot.lab.person.services.PersonService;
import krueger.training.spring.boot.lab.phonenumber.models.PhoneNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.hamcrest.core.Is;
import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
public class PersonControllerTest {

    @MockBean
    private PersonService mockPersonService;

    @Autowired
    private MockMvc mockMvc;

    private Person inputPerson;
    private Person mockResponsePerson1;

    @BeforeEach
    public void setUp(){
        List<PhoneNumber> numbers = new ArrayList<>();
        numbers.add(new PhoneNumber("5555555555",true));
        numbers.add(new PhoneNumber("6666666666",false));
        inputPerson = new Person("Sabrina","Rose", numbers);
        mockResponsePerson1 = new Person("Bwina","Rose", numbers);
        mockResponsePerson1.setId(1);
    }

    @Test
    public void PersonControllerTest01() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/persons"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void createPersonRequestSuccess() throws Exception {
        BDDMockito.doReturn(mockResponsePerson1).when(mockPersonService).create(any());

        mockMvc.perform(MockMvcRequestBuilders.post("/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonStringify.asJsonString(inputPerson)))

                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id",Is.is(1)));
    }

    @Test
    public void getPersonByIdTestSuccess() throws Exception{
        BDDMockito.doReturn(mockResponsePerson1).when(mockPersonService).getPersonById(1);

        mockMvc.perform(get("/persons/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));
    }

//    @Test
//    public void getPersonByIdTestFail() throws Exception {
//        BDDMockito.doThrow(new PersonNotFoundException("Not Found")).when(mockPersonService).getPersonById(1);
//        mockMvc.perform(get("/persons/{id}", 1))
//                .andExpect(status().isNotFound());
//    }

    @Test
    public void putPersonTestNotSuccess() throws Exception{
        List<PhoneNumber> numbers = new ArrayList<>();
        numbers.add(new PhoneNumber("5555555555",true));
        numbers.add(new PhoneNumber("6666666666",false));
        Person expectedPersonUpdate = new Person("Bwina","Rose", numbers);
        expectedPersonUpdate.setId(1);
        BDDMockito.doReturn(expectedPersonUpdate).when(mockPersonService).updatePerson(any(), any());
        mockMvc.perform(put("/persons/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonStringify.asJsonString(inputPerson)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(1)));
    }

//    @Test
//    public void putPersonTestNotFound() throws Exception{
//        BDDMockito.doThrow(new PersonNotFoundException("Not Found")).when(mockPersonService).updatePerson(any(), any());
//        mockMvc.perform(put("/persons/{id}", 1)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(JsonStringify.asJsonString(inputPerson)))
//                .andExpect(status().isNotFound());
//    }

    @Test
    public void deletePersonTestNotSuccess() throws Exception{
        BDDMockito.doReturn(true).when(mockPersonService).deletePerson(any());
        mockMvc.perform(delete("/persons/{id}", 1))
                .andExpect(status().isNoContent());
    }

//    @Test
//    public void deletePersonTestNotFound() throws Exception{
//        BDDMockito.doThrow(new PersonNotFoundException("Not Found")).when(mockPersonService).deletePerson(any());
//        mockMvc.perform(delete("/persons/{id}", 1))
//                .andExpect(status().isNotFound());
//    }
}

