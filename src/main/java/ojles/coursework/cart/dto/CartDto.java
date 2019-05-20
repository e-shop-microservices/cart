package ojles.coursework.cart.dto;

import lombok.Getter;
import lombok.Setter;
import ojles.coursework.cart.domain.Cart;
import ojles.coursework.cart.domain.CartItem;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CartDto {
    private List<CartItemDto> items = new ArrayList<>();

    public static CartDto from(Cart cart, List<ProductDto> products) {
        if (cart == null) {
            return null;
        }
        if (products == null) {
            throw new IllegalArgumentException("Products can't be null");
        }
        if (cart.getItems().size() != products.size()) {
            throw new IllegalArgumentException("Cart item amount and products amount differ");
        }

        List<CartItemDto> items = new ArrayList<>(cart.getItems().size());
        for (CartItem item : cart.getItems()) {
            for (ProductDto product : products) {
                if (product.getId() == item.getProductId()) {
                    items.add(CartItemDto.from(item, product));
                    break;
                }
            }

        }
        CartDto dto = new CartDto();
        dto.setItems(items);
        return dto;
    }
}
