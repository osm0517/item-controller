package example.itemcontroller.directory.validator;

import example.itemcontroller.directory.domain.Item;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        /**
         * isAssignableFrom 은 파라미터로 받은 class에 자식 class도 가능
         */
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;
        if(errors.hasErrors()){

        }

        if(Objects.equals(item.getItemName(), "")){
            errors.rejectValue("itemName", "required");
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            errors.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if(item.getQuantity() == null || item.getQuantity() > 9999){
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }
        if(item.getQuantity() != null && item.getPrice() != null){
            if(item.getQuantity() * item.getPrice() < 10000){
                errors.reject("minValueBorder", new Object[]{10000}, null);
            }
        }

    }
}
