package ro.mindit.mindshopper.service.dto.custom;

import ro.mindit.mindshopper.domain.ItemCart;

public class RequestItemToCartDTO implements java.io.Serializable {

    private Long cartId;
    private ItemCart itemCart;

    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public ItemCart getItemCart() {
        return itemCart;
    }

    public void setItemCart(ItemCart itemCart) {
        this.itemCart = itemCart;
    }
}
