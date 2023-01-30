package example.itemcontroller.directory.controller.validation;

import example.itemcontroller.directory.domain.DeliveryCode;
import example.itemcontroller.directory.domain.Item;
import example.itemcontroller.directory.domain.ItemType;
import example.itemcontroller.directory.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/validation/v1/items")
@Slf4j
@RequiredArgsConstructor
public class ValidationControllerV1 {

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

        return "/v1/items";
    }

    @GetMapping("/{itemId}")
    public String item(Model model, @PathVariable int itemId){

        Item item = itemRepository.findById(itemId);

        model.addAttribute("item", item);

        return "/v1/item";
    }

    @GetMapping("/add")
    public String addForm(
            Model model
    ){
        model.addAttribute("item", new Item());
        return "/v1/addform";
    }

    @PostMapping("/add")
    public String addItem(Item item, RedirectAttributes redirectAttributes, Model model){

        Map<String, String> errors = new HashMap<>();

        log.debug("item = {}", item);

        if(Objects.equals(item.getItemName(), "")){
            errors.put("itemName", "상품명은 필수입니다.");
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            errors.put("price", "가격은 1,000~1,000,000 사이에 값을 입력해야합니다.");
        }
        if(item.getQuantity() == null || item.getQuantity() > 9999){
            errors.put("quantity", "최대 수량은 9999입니다.");
        }
        if(item.getQuantity() != null && item.getPrice() != null){
            if(item.getQuantity() * item.getPrice() < 10000){
                errors.put("globalError", "수량과 가격의 곱이 10,000원 이상이여야합니다.");
            }
        }
        if(!errors.isEmpty()){
            model.addAttribute("errors", errors);
            return "/v1/addform";
        }

        Item savedItem = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/validation/v1/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable int itemId, Model model){

        Item findItem = itemRepository.findById(itemId);

        model.addAttribute("item", findItem);

        return "/v1/editform";
    }

    @PostMapping("/{itemId}/edit")
    public String editform(@PathVariable int itemId, Item item, RedirectAttributes redirectAttributes){
        Item findItem = itemRepository.findById(itemId);

        updateItem(item, findItem);

        Item savedItem = itemRepository.save(findItem);

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/validation/v1/items/{itemId}";
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
