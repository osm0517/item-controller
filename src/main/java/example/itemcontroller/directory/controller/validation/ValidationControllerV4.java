package example.itemcontroller.directory.controller.validation;

import example.itemcontroller.directory.domain.DeliveryCode;
import example.itemcontroller.directory.domain.Item;
import example.itemcontroller.directory.domain.ItemType;
import example.itemcontroller.directory.dto.ItemSaveDto;
import example.itemcontroller.directory.dto.ItemUpdateDto;
import example.itemcontroller.directory.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/validation/v4/items")
@Slf4j
@RequiredArgsConstructor
public class ValidationControllerV4 {

    private final ItemRepository itemRepository;

    @ModelAttribute("regions")
    public Map<String, String> regions(){
        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");
        return regions;
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes(){
        return ItemType.values();
    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {
        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST", "빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW", "느린 배송"));
        return deliveryCodes;
    }

    @GetMapping
    public String items(Model model){

        List<Item> items = itemRepository.findAll();

        model.addAttribute("items", items);

        return "/v4/items";
    }

    @GetMapping("/{itemId}")
    public String item(Model model, @PathVariable int itemId){

        Item item = itemRepository.findById(itemId);

        model.addAttribute("item", item);

        return "/v4/item";
    }

    @GetMapping("/add")
    public String addForm(
            Model model
    ){
        model.addAttribute("item", new Item());
        return "/v4/addform";
    }

    @PostMapping("/add")
    public String addItem(@Validated @ModelAttribute(name = "item") ItemSaveDto dto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        if(bindingResult.hasErrors()){
            log.debug("errors = {}", bindingResult);
            return "/v4/addform";
        }

        Item item = new Item(dto.getItemName(), dto.getPrice(), dto.getQuantity());

        Item savedItem = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/validation/v4/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable int itemId, Model model){

        Item findItem = itemRepository.findById(itemId);

        model.addAttribute("item", findItem);

        return "/v4/editform";
    }

    @PostMapping("/{itemId}/edit")
    public String editform(@PathVariable int itemId, @Validated @ModelAttribute(name = "item") ItemUpdateDto dto, RedirectAttributes redirectAttributes){
        Item findItem = itemRepository.findById(itemId);

        Item item = new Item(dto.getItemName(), dto.getPrice(), dto.getQuantity());

        updateItem(item, findItem);

        Item savedItem = itemRepository.save(findItem);

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/validation/v4/items/{itemId}";
    }

    private static void updateItem(Item item, Item findItem) {
        findItem.setItemName(item.getItemName());
        findItem.setPrice(item.getPrice());
        findItem.setQuantity(item.getQuantity());
        findItem.setOpen(item.getOpen());
        findItem.setRegions(item.getRegions());
        findItem.setItemType(item.getItemType());
        findItem.setDeliveryCode(item.getDeliveryCode());
    }

}
