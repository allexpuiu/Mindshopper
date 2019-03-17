package ro.mindit.mindshopper.web.rest.custom;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ro.mindit.mindshopper.domain.Cart;
import ro.mindit.mindshopper.domain.ItemCart;
import ro.mindit.mindshopper.service.custom.CartService;
import ro.mindit.mindshopper.service.custom.ItemCartService;
import ro.mindit.mindshopper.service.dto.custom.RequestItemToCartDTO;

import java.util.List;
import java.util.Optional;
import java.util.Set;


@RestController
@CrossOrigin
@RequestMapping("/api")
public class BotConnectorResource {
    private static final Logger LOG = LoggerFactory.getLogger(BotConnectorResource.class);

    @Autowired
    private ItemCartService itemCartService;
    @Autowired
    private CartService cartService;

    @GetMapping(value = "/message")
    public ResponseEntity getBotMessage() {

        return null;
    }

    @PostMapping(value = "/message")
    public ResponseEntity postBotMessage() {

        return null;
    }

    @GetMapping(value = "/carts/{cartId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getCart(@Param("cartId") Long cartId) {
        Optional<Cart> cart =  cartService.findById(cartId);

        if(!cart.isPresent()) {
            ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cart.get());
    }

    @GetMapping(value = "/carts/{cartId}/items", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getItemsByCart(@Param("cartId") Long cartId) {
        List<ItemCart> items =  itemCartService.findByCartId(cartId);

        return ResponseEntity.ok(items);
    }

    @PutMapping(value = "/carts/add-item")
    public ResponseEntity putItemCartToCart(RequestItemToCartDTO itemToCartDTO) {
        Optional<Cart> optionalCart = cartService.findById(itemToCartDTO.getCartId());
        if (!optionalCart.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        Cart cart = optionalCart.get();
        Set<ItemCart> items = cart.getItems();
        items.add(itemToCartDTO.getItemCart());

        cart.setItems(items);
        return ResponseEntity.ok(cartService.save(cart));
    }

    @DeleteMapping(value = "/delete-from")
    private ResponseEntity deleteItemFromCart(RequestItemToCartDTO itemToCartDTO) {
        Optional<Cart> optionalCart = cartService.findById(itemToCartDTO.getCartId());
        if (!optionalCart.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Cart cart = optionalCart.get();
        Set<ItemCart> items = cart.getItems();
        if(!items.remove(itemToCartDTO.getItemCart())) {
            return ResponseEntity.notFound().build();
        }

        cart.setItems(items);
        return ResponseEntity.ok(cartService.save(cart));
    }
}

