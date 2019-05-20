package ojles.coursework.cart.service;

import lombok.RequiredArgsConstructor;
import ojles.coursework.cart.dao.CartDao;
import ojles.coursework.cart.dao.CartItemDao;
import ojles.coursework.cart.domain.Cart;
import ojles.coursework.cart.domain.CartItem;
import ojles.coursework.cart.dto.AddCartItemRequest;
import ojles.coursework.cart.dto.CartDto;
import ojles.coursework.cart.dto.ProductDto;
import ojles.coursework.cart.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;


/**
 * TODO: add authorization and get userId
 */

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartDao cartDao;
    private final CartItemDao cartItemDao;
    private final CatalogueService catalogueService;

    // FIXME: remove mockUserId
    // TODO: use authentication and get userId from `user` microservice
    private long mockUserId = 1L;

    public CartDto findProductInformation() {
        Cart cart = findByUserId(mockUserId);
        List<Long> productIds = cart.getItems().stream()
                .map(CartItem::getId)
                .collect(Collectors.toList());
        List<ProductDto> products = catalogueService.getProductsByIds(productIds);
        return CartDto.from(cart, products);
    }

    @Transactional
    public void addItem(AddCartItemRequest addRequest) {
        CartItem item = new CartItem();
        item.setAmount(addRequest.getAmount());
        item.setProductId(addRequest.getProductId());
        Cart cart = findByUserId(mockUserId);
        cart.addItem(item);
        cartDao.save(cart);
    }

    @Transactional
    public void removeItem(long itemId) {
        Cart cart = findByUserId(mockUserId);
        cart.removeItemById(itemId);
        cartDao.save(cart);
    }

    @Transactional
    public void changeAmount(long itemId, int newAmount) {
        Cart cart = findByUserId(mockUserId);
        CartItem item = cartItemDao.findByCartAndId(cart, itemId);
        if (item == null) {
            throw new ResourceNotFoundException("Couldn't find cart item with id=" + itemId);
        }
        item.setAmount(newAmount);
        cartDao.save(cart);
    }

    private Cart findByUserId(long userId) {
        Cart cart = cartDao.findByUserId(userId);
        if (cart == null) {
            throw new ResourceNotFoundException("Couldn't find user cart with id=" + userId);
        }
        return cart;
    }
}
