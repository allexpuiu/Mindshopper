package ro.mindit.mindshopper.service.custom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.mindit.mindshopper.domain.ItemCart;
import ro.mindit.mindshopper.repository.ItemCartRepository;

import java.util.List;

@Service
@Transactional
public class ItemCartService {

    @Autowired
    private ItemCartRepository itemCartRepository;

    public List<ItemCart> findByCartId(Long cartId) {
        return  itemCartRepository.findItemsByCart(cartId);
    }
}
