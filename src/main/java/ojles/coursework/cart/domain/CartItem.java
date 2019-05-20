package ojles.coursework.cart.domain;

import javax.persistence.*;

@Entity
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long productId;
    private int amount;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public long getId() {
        return id;
    }

    public long getProductId() {
        return productId;
    }

    public int getAmount() {
        return amount;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void incrementAmount() {
        amount++;
    }

    void setCart(Cart cart) {
        this.cart = cart;
    }
}
