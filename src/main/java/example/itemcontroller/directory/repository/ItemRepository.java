package example.itemcontroller.directory.repository;

import example.itemcontroller.directory.domain.Item;
import example.itemcontroller.directory.dto.ItemUpdateDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    Item save(Item item);

    Item findById(int id);

    List<Item> findAll();

//    @Query(
//            nativeQuery = true,
//            value = "UPDATE item " +
//                    "SET name = :name " +
//                    "WHERE id = :id"
//    )
//    Item updateName(int id, String name);
//    @Query(
//            value = "UPDATE item i " +
//                    "SET i.price = :price " +
//                    "WHERE i.id = :id"
//    )
//    Item updatePrice(int id, int price);
//    @Query(
//            value = "UPDATE item i " +
//                    "SET i.quantity = :quantity" +
//                    "WHERE i.id = :id"
//    )
//    Item updateQuantity(int id, int quantity);
}
