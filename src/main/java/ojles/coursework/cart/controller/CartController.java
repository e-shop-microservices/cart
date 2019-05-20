package ojles.coursework.cart.controller;

import lombok.RequiredArgsConstructor;
import ojles.coursework.cart.dto.AddCartItemRequest;
import ojles.coursework.cart.dto.CartDto;
import ojles.coursework.cart.dto.ChangeAmountRequest;
import ojles.coursework.cart.service.CartService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/items")
    public CartDto getCart() {
        return cartService.findProductInformation();
    }

    @PostMapping("/items")
    public void addItem(AddCartItemRequest addRequest) {
        cartService.addItem(addRequest);
    }

    @PutMapping("/items/{itemId}")
    public void changeItemAmount(@PathVariable("itemId") long itemId, ChangeAmountRequest changeAmountRequest) {
        cartService.changeAmount(itemId, changeAmountRequest.getNewAmount());
    }

    @DeleteMapping("/items/{itemId}")
    public void removeItem(@PathVariable("itemId") long itemId) {
        cartService.removeItem(itemId);
    }
}
