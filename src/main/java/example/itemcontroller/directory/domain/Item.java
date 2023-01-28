package example.itemcontroller.directory.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    @Column(name = "name", nullable = false)
    public String itemName;
    @Column(nullable = false)
    public Integer price;
    @Column(nullable = false)
    public int quantity;

    private Boolean open; //판매 여부
    private List<String> regions; //등록 지역
    @Enumerated(EnumType.STRING)
    private ItemType itemType; //상품 종류
    private String deliveryCode; //배송 방식

    public Item(String itemName, int price, int quantity){
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
