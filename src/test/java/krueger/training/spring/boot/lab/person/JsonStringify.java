package krueger.training.spring.boot.lab.person;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonStringify {
    public static String asJsonString( final Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
