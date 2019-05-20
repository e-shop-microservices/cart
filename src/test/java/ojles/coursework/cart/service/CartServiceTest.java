package ojles.coursework.cart.service;

import ojles.coursework.cart.dao.CartDao;
import ojles.coursework.cart.dao.CartItemDao;
import ojles.coursework.cart.domain.Cart;
import ojles.coursework.cart.domain.CartItem;
import ojles.coursework.cart.dto.AddCartItemRequest;
import ojles.coursework.cart.dto.CartDto;
import ojles.coursework.cart.dto.CartItemDto;
import ojles.coursework.cart.dto.ProductDto;
import ojles.coursework.cart.exception.ResourceNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class CartServiceTest {
    private final CartDao cartDao = mock(CartDao.class);
    private final CartItemDao cartItemDao = mock(CartItemDao.class);
    private final CatalogueService catalogueService = mock(CatalogueService.class);

    private final CartService cartService = new CartService(cartDao, cartItemDao, catalogueService);

    private final Cart cart = new Cart();
    private final CartItem item = new CartItem();
    private final ProductDto product = new ProductDto();

    @Before
    public void configureMocks() {
        ReflectionTestUtils.setField(item, "id", 123L);
        item.setAmount(32);
        item.setProductId(234L);
        cart.addItem(item);
        product.setId(234L);

        when(cartDao.findByUserId(1L)).thenReturn(cart);
        when(cartItemDao.findByCartAndId(cart, item.getId())).thenReturn(item);
        when(catalogueService.getProductsByIds(any(List.class)))
                .thenReturn(singletonList(product));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testFindProductInformationShouldThrowExceptionWhenCartNotFound() {
        when(cartDao.findByUserId(1L)).thenReturn(null);
        cartService.findProductInformation();
    }

    @Test
    public void testFindProductInformationShouldReturnValidCartDto() {
        when(cartDao.findByUserId(1L)).thenReturn(cart);

        CartDto cartDto = cartService.findProductInformation();
        List<CartItemDto> items = cartDto.getItems();
        assertThat(items.size(), equalTo(1));
        CartItemDto itemDto = items.get(0);
        assertThat(itemDto.getProduct(), equalTo(product));
        assertThat(itemDto.getId(), equalTo(123L));
    }

    @Test
    public void testAddItemShouldWorkCorrectly() {
        AddCartItemRequest addRequest = new AddCartItemRequest();
        addRequest.setAmount(12);
        addRequest.setProductId(product.getId());

        cartService.addItem(addRequest);

        verify(cartDao).save(cart);
        assertThat(cart.getItems().size(), equalTo(1));
        assertThat(cart.getItems().get(0).getAmount(), equalTo(33));
    }

    @Test
    public void testRemoveItemShouldWorkCorrectly() {
        cartService.removeItem(item.getId());

        verify(cartDao).save(cart);
        assertThat(cart.getItems().size(), equalTo(0));
    }

    @Test(expected = ResourceNotFoundException.class)
    public void testChangeAmountShouldThrowExceptionWhenCartItemNotFound() {
        cartService.changeAmount(1414141L, 3);
    }

    @Test
    public void testChangeAmountShouldWorkCorrectly() {
        cartService.changeAmount(item.getId(), 333);

        verify(cartDao).save(cart);
        assertThat(cart.getItems().get(0).getAmount(), equalTo(333));
    }
}
