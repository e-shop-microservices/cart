package ojles.coursework.cart.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Entity
public class Cart {
    @Id
    private long userId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    public long getUserId() {
        return userId;
    }

    public List<CartItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void addItem(CartItem item) {
        Optional<CartItem> cartItem = items.stream()
                .filter(i -> i.getProductId() == item.getProductId())
                .findFirst();
        if (cartItem.isPresent()) {
            cartItem.get().incrementAmount();
        } else {
            items.add(item);
            item.setCart(this);
        }
    }

    public void changeItemAmount(long itemId, int newAmount) {
        if (newAmount <= 0) {
            items.removeIf(i -> i.getId() == itemId);
        } else {
            for (CartItem item : items) {
                if (item.getId() == itemId) {
                    item.setAmount(newAmount);
                    return;
                }
            }
        }
    }

    public boolean removeItemById(long cartItemId) {
        return items.removeIf(c -> c.getId() == cartItemId);
    }
}
