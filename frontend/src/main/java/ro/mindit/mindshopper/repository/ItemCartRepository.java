package ro.mindit.mindshopper.repository;

import ro.mindit.mindshopper.domain.ItemCart;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the ItemCart entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ItemCartRepository extends JpaRepository<ItemCart, Long> {

    List<ItemCart> findItemsByCartId(Long cartId);
}
