package example.itemcontroller.directory.controller.validation;

import example.itemcontroller.directory.domain.DeliveryCode;
import example.itemcontroller.directory.domain.Item;
import example.itemcontroller.directory.domain.ItemType;
import example.itemcontroller.directory.repository.ItemRepository;
import example.itemcontroller.directory.validator.ItemValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

@Controller
@RequestMapping("/validation/v2/items")
@Slf4j
@RequiredArgsConstructor
public class ValidationControllerV2 {

    private final ItemRepository itemRepository;
    private final ItemValidator itemValidator;

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

    @InitBinder
    public void init(WebDataBinder webDataBinder){
        log.debug("init");
        webDataBinder.addValidators(itemValidator);
    }


    @GetMapping
    public String items(Model model){

        List<Item> items = itemRepository.findAll();

        model.addAttribute("items", items);

        return "/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(Model model, @PathVariable int itemId){

        Item item = itemRepository.findById(itemId);

        model.addAttribute("item", item);

        return "/v2/item";
    }

    @GetMapping("/add")
    public String addForm(
            Model model
    ){
        model.addAttribute("item", new Item());
        return "/v2/addform";
    }

//    @PostMapping("/add")
    public String addItemV1(Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        log.debug("item = {}", item);

        if(Objects.equals(item.getItemName(), "")){
            bindingResult.addError(new FieldError("item", "itemName", "상품명은 필수입니다."));
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            bindingResult.addError(new FieldError("item", "price", "가격은 1,000~1,000,000 사이에 값을 입력해야합니다."));
        }
        if(item.getQuantity() == null || item.getQuantity() > 9999){
            bindingResult.addError(new FieldError("item", "quantity", "최대 수량은 9999입니다."));
        }
        if(item.getQuantity() != null && item.getPrice() != null){
            if(item.getQuantity() * item.getPrice() < 10000){
                bindingResult.addError(new ObjectError("item", "수량과 가격의 곱이 10,000원 이상이여야합니다."));
            }
        }
        if(bindingResult.hasErrors()){

            return "/v2/addform";
        }

        Item savedItem = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/validation/v2/items/{itemId}";
    }

//    @PostMapping("/add")
    public String addItemV2(Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        log.debug("item = {}", item);

        if(Objects.equals(item.getItemName(), "")){
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, null, null, "상품명은 필수입니다."));
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, null, null, "가격은 1,000~1,000,000 사이에 값을 입력해야합니다."));
        }
        if(item.getQuantity() == null || item.getQuantity() > 9999){
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, null, null, "최대 수량은 9999입니다."));
        }
        if(item.getQuantity() != null && item.getPrice() != null){
            if(item.getQuantity() * item.getPrice() < 10000){
                bindingResult.addError(new ObjectError("item", null, null, "수량과 가격의 곱이 10,000원 이상이여야합니다."));
            }
        }
        if(bindingResult.hasErrors()){

            return "/v2/addform";
        }

        Item savedItem = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/validation/v2/items/{itemId}";
    }

//    @PostMapping("/add")
    public String addItemV3(Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        log.debug("item = {}", item);

        if(Objects.equals(item.getItemName(), "")){
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
        }
        if(item.getQuantity() == null || item.getQuantity() > 9999){
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999}, null));
        }
        if(item.getQuantity() != null && item.getPrice() != null){
            if(item.getQuantity() * item.getPrice() < 10000){
                bindingResult.addError(new ObjectError("item", new String[]{"minValueBorder"}, new Object[]{10000}, null));
            }
        }
        if(bindingResult.hasErrors()){

            return "/v2/addform";
        }

        Item savedItem = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/validation/v2/items/{itemId}";
    }

//    @PostMapping("/add")
    public String addItemV4(Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        log.debug("item = {}", item);

        if(Objects.equals(item.getItemName(), "")){
            bindingResult.rejectValue("itemName", "required");
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000){
            bindingResult.rejectValue("price", "range", new Object[]{1000, 1000000}, null);
        }
        if(item.getQuantity() == null || item.getQuantity() > 9999){
            bindingResult.rejectValue("quantity", "max", new Object[]{9999}, null);
        }
        if(item.getQuantity() != null && item.getPrice() != null){
            if(item.getQuantity() * item.getPrice() < 10000){
                bindingResult.reject("minValueBorder", new Object[]{10000}, null);
            }
        }
        if(bindingResult.hasErrors()){

            return "/v2/addform";
        }

        Item savedItem = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/validation/v2/items/{itemId}";
    }

    @PostMapping("/add")
    public String addItemV5(@Validated Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model){

        if(bindingResult.hasErrors()){
            log.debug("errors = {}", bindingResult);
            return "/v2/addform";
        }

        Item savedItem = itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/validation/v2/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable int itemId, Model model){

        Item findItem = itemRepository.findById(itemId);

        model.addAttribute("item", findItem);

        return "/v2/editform";
    }

    @PostMapping("/{itemId}/edit")
    public String editform(@PathVariable int itemId, Item item, RedirectAttributes redirectAttributes){
        Item findItem = itemRepository.findById(itemId);

        updateItem(item, findItem);

        Item savedItem = itemRepository.save(findItem);

        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/validation/v2/items/{itemId}";
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
