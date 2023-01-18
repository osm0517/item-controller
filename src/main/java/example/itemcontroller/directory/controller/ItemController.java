package example.itemcontroller.directory.controller;

import example.itemcontroller.directory.domain.Item;
import example.itemcontroller.directory.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@Slf4j
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){

        List<Item> items = itemRepository.findAll();

        model.addAttribute("items", items);

        return "items";
    }

    @GetMapping("/{itemId}")
    public String item(Model model, @PathVariable int itemId){

        Item item = itemRepository.findById(itemId);

        model.addAttribute("item", item);

        return "item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "addform";
    }

    @PostMapping("/add")
    public String addItem(Item item, RedirectAttributes redirectAttributes){
        Item savedItem = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable int itemId, Model model){

        Item findItem = itemRepository.findById(itemId);

        model.addAttribute("item", findItem);

        return "editform";
    }

    @PostMapping("/{itemId}/edit")
    public String editform(@PathVariable int itemId, Item item, RedirectAttributes redirectAttributes){
        Item findItem = itemRepository.findById(itemId);

        findItem.setItemName(item.getItemName());
        findItem.setPrice(item.getPrice());
        findItem.setQuantity(item.getQuantity());

        Item savedItem = itemRepository.save(findItem);

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/basic/items/{itemId}";
    }
}
