package example.itemcontroller.directory.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Range;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemUpdateDto {

    @NotNull
    private long id;

    @NotBlank
    private String ItemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotNull
    private Integer quantity;

}
