package ojles.coursework.cart.dao;

import ojles.coursework.cart.domain.Cart;
import ojles.coursework.cart.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemDao extends JpaRepository<CartItem, Long> {
    CartItem findByCartAndId(Cart cart, long id);
}
