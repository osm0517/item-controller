package example.itemcontroller.directory.repository;

import example.itemcontroller.directory.domain.Item;
import example.itemcontroller.directory.dto.ItemUpdateDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository repository;

    @AfterEach
    void clear() {
        repository.deleteAllInBatch();
    }

    @Test
    void save() {
//        given
        Item item = new Item("itemA", 1000, 1);

//        when
        Item savedItem = repository.save(item);

        Optional<Item> findItem = repository.findById(savedItem.getId());
        Item result = findItem.get();
//        then
        assertThat(savedItem).usingRecursiveComparison().isEqualTo(result);
    }

    @Test
    void findAll() {
//        given
        Item item1 = new Item("exItem1", 1000, 1);
        Item item2 = new Item("exItem2", 2000, 10);

//        when
        repository.save(item1);
        repository.save(item2);

        List<Item> items = repository.findAll();
//        then
        assertThat(items).size().isEqualTo(2);
        assertThat(items).usingRecursiveFieldByFieldElementComparator().contains(item1, item2);
    }

    @Test
    void update() {
//        given
        Item item = new Item("itemA", 1000, 1);

//        when
        Item savedItem = repository.save(item);
        savedItem.setItemName("itemB");
        Item updatedItem = repository.save(savedItem);

        List<Item> all = repository.findAll();
//        then
        assertThat(all).size().isEqualTo(1);
        assertThat(updatedItem.getItemName()).isEqualTo("itemB");
    }
}