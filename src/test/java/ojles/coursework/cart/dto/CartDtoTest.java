package ojles.coursework.cart.dto;

import ojles.coursework.cart.domain.Cart;
import ojles.coursework.cart.domain.CartItem;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CartDtoTest {
    @Test
    public void testFromShouldReturnWhenCartIsNull() {
        assertThat(CartDto.from(null, new ArrayList<>()), is(nullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromShouldThrowExceptionWhenProductsNull() {
        CartDto.from(new Cart(), null);
    }

    @Test
    public void testFromShouldReturnValidCartDto() {
        ProductDto product1 = new ProductDto();
        product1.setId(2L);
        ProductDto product2 = new ProductDto();
        product2.setId(3L);
        List<ProductDto> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);

        CartItem item1 = new CartItem();
        ReflectionTestUtils.setField(item1, "id", 1L);
        item1.setAmount(2);
        item1.setProductId(product1.getId());
        CartItem item2 = new CartItem();
        ReflectionTestUtils.setField(item2, "id", 2L);
        item2.setAmount(3);
        item2.setProductId(product2.getId());
        Cart cart = new Cart();
        cart.addItem(item1);
        cart.addItem(item2);

        CartDto cartDto = CartDto.from(cart, products);
        assertThat(cartDto.getItems().size(), equalTo(2));
        for (CartItemDto cartItemDto : cartDto.getItems()) {
            if (cartItemDto.getId() == 1L) {
                assertThat(cartItemDto.getProduct(), equalTo(product1));
            } else {
                assertThat(cartItemDto.getProduct(), equalTo(product2));
            }
        }
    }
}
