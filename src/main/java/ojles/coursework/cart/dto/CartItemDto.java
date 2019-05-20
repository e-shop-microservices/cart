package ojles.coursework.cart.dto;

import lombok.Getter;
import lombok.Setter;
import ojles.coursework.cart.domain.CartItem;

@Getter
@Setter
public class CartItemDto {
    private long id;
    private int amount;
    private ProductDto product;

    public static CartItemDto from(CartItem cartItem, ProductDto product) {
        if (cartItem == null) {
            return null;
        }
        if (product == null) {
            throw new IllegalArgumentException("Product can't be null");
        }

        CartItemDto dto = new CartItemDto();
        dto.setId(cartItem.getId());
        dto.setAmount(cartItem.getAmount());
        dto.setProduct(product);
        return dto;
    }
}
