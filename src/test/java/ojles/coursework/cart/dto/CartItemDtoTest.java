package ojles.coursework.cart.dto;

import ojles.coursework.cart.domain.CartItem;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CartItemDtoTest {
    @Test
    public void testFromShouldReturnWhenCartItemIsNull() {
        assertThat(CartItemDto.from(null, new ProductDto()), is(nullValue()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromShouldThrowExceptionWhenProductIsNull() {
        CartItemDto.from(new CartItem(), null);
    }

    @Test
    public void testFromShouldReturnValidCartItemDto() {
        CartItem cartItem = new CartItem();
        ReflectionTestUtils.setField(cartItem, "id", 3L);
        cartItem.setProductId(1L);
        cartItem.setAmount(12);
        ProductDto product = new ProductDto();

        CartItemDto dto = CartItemDto.from(cartItem, product);
        assertThat(dto.getId(), equalTo(3L));
        assertThat(dto.getAmount(), equalTo(12));
        assertThat(dto.getProduct(), equalTo(product));
    }
}
