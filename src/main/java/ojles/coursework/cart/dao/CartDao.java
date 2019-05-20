package ojles.coursework.cart.dao;

import ojles.coursework.cart.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDao extends JpaRepository<Cart, Long> {
    Cart findByUserId(long userId);
}
