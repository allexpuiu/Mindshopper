package ro.mindit.mindshopper.service.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.mindit.mindshopper.domain.Cart;
import ro.mindit.mindshopper.repository.CartRepository;

import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;


    public Optional<Cart> findById(Long cartId) {
        return cartRepository.findById(cartId);
    }

    @Transactional(readOnly = false)
    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }
}
