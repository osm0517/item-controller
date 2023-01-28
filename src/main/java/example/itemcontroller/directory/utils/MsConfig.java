package example.itemcontroller.directory.utils;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;

import java.util.Locale;

//@Component
public class MsConfig {

    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages.properties");
        messageSource.setDefaultLocale(Locale.KOREAN);
        messageSource.setDefaultEncoding("utf-8");
        return messageSource;
    }
}
