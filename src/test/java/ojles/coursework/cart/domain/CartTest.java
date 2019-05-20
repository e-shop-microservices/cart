package ojles.coursework.cart.domain;

import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class CartTest {
    private Cart cart = new Cart();

    @Before
    public void init() {
        CartItem item1 = new CartItem();
        ReflectionTestUtils.setField(item1, "id", 1L);
        item1.setAmount(2);
        item1.setProductId(2L);
        CartItem item2 = new CartItem();
        ReflectionTestUtils.setField(item2, "id", 2L);
        item2.setAmount(3);
        item2.setProductId(3L);
        cart.addItem(item1);
        cart.addItem(item2);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetItemsShouldReturnUnmodifiableList() {
        List<CartItem> unmodifiableList = cart.getItems();
        unmodifiableList.add(new CartItem());
    }

    @Test
    public void testAddItemShouldSetHimselfInCartItem() {
        CartItem newItem = new CartItem();
        cart.addItem(newItem);
        assertThat(ReflectionTestUtils.getField(newItem, "cart"), is(not(nullValue())));
    }

    @Test
    public void testAddItemShouldIncrementAmountWhenCartItemPresent() {
        CartItem item3 = new CartItem();
        item3.setProductId(2L);
        cart.addItem(item3);

        assertThat(cart.getItems().size(), equalTo(2));
        assertThat(cart.getItems().get(0).getAmount(), equalTo(3));
    }

    @Test
    public void testChangeItemAmountShouldRemoveItemWhenNewAmountIsZeroOrLess() {
        cart.changeItemAmount(2L, 0);
        assertThat(cart.getItems().size(), equalTo(1));
        assertThat(cart.getItems().get(0).getId(), equalTo(1L));
    }

    @Test
    public void testChangeAmountShouldWorkAsExpected() {
        cart.changeItemAmount(2L, 1);
        assertThat(cart.getItems().size(), equalTo(2));
        assertThat(cart.getItems().get(1).getAmount(), equalTo(1));
    }

    @Test
    public void testRemoveByIdShouldReturnTrueWhenItemFoundAndRemoved() {
        boolean result = cart.removeItemById(1L);
        assertThat(result, equalTo(true));
    }

    @Test
    public void whenItemNotPresentInCartThenRemoveItemByIdShouldReturnFalse() {
        boolean result = cart.removeItemById(3L);
        assertThat(result, equalTo(false));
    }
}
