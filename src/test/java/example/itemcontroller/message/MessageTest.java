package example.itemcontroller.message;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageTest {

    @Autowired
    MessageSource ms;

    @Test
    void messageTest(){
        String message = ms.getMessage("label.item", null, null);
        System.out.println("message = " + message);
        assertThat(message).isEqualTo("상품");
    }
}
