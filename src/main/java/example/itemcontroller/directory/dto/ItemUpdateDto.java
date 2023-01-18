package example.itemcontroller.directory.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemUpdateDto {

    private String ItemName;
    private Integer price;
    private int quantity;

}
