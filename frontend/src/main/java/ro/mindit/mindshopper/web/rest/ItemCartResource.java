package ro.mindit.mindshopper.web.rest;

import com.codahale.metrics.annotation.Timed;
import ro.mindit.mindshopper.domain.ItemCart;
import ro.mindit.mindshopper.repository.ItemCartRepository;
import ro.mindit.mindshopper.web.rest.errors.BadRequestAlertException;
import ro.mindit.mindshopper.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ItemCart.
 */
@RestController
@RequestMapping("/api")
public class ItemCartResource {

    private final Logger log = LoggerFactory.getLogger(ItemCartResource.class);

    private static final String ENTITY_NAME = "itemCart";

    private final ItemCartRepository itemCartRepository;

    public ItemCartResource(ItemCartRepository itemCartRepository) {
        this.itemCartRepository = itemCartRepository;
    }

    /**
     * POST  /item-carts : Create a new itemCart.
     *
     * @param itemCart the itemCart to create
     * @return the ResponseEntity with status 201 (Created) and with body the new itemCart, or with status 400 (Bad Request) if the itemCart has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/item-carts")
    @Timed
    public ResponseEntity<ItemCart> createItemCart(@Valid @RequestBody ItemCart itemCart) throws URISyntaxException {
        log.debug("REST request to save ItemCart : {}", itemCart);
        if (itemCart.getId() != null) {
            throw new BadRequestAlertException("A new itemCart cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ItemCart result = itemCartRepository.save(itemCart);
        return ResponseEntity.created(new URI("/api/item-carts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /item-carts : Updates an existing itemCart.
     *
     * @param itemCart the itemCart to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated itemCart,
     * or with status 400 (Bad Request) if the itemCart is not valid,
     * or with status 500 (Internal Server Error) if the itemCart couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/item-carts")
    @Timed
    public ResponseEntity<ItemCart> updateItemCart(@Valid @RequestBody ItemCart itemCart) throws URISyntaxException {
        log.debug("REST request to update ItemCart : {}", itemCart);
        if (itemCart.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ItemCart result = itemCartRepository.save(itemCart);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, itemCart.getId().toString()))
            .body(result);
    }

    /**
     * GET  /item-carts : get all the itemCarts.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of itemCarts in body
     */
    @GetMapping("/item-carts")
    @Timed
    public List<ItemCart> getAllItemCarts() {
        log.debug("REST request to get all ItemCarts");
        return itemCartRepository.findAll();
    }

    /**
     * GET  /item-carts/:id : get the "id" itemCart.
     *
     * @param id the id of the itemCart to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the itemCart, or with status 404 (Not Found)
     */
    @GetMapping("/item-carts/{id}")
    @Timed
    public ResponseEntity<ItemCart> getItemCart(@PathVariable Long id) {
        log.debug("REST request to get ItemCart : {}", id);
        Optional<ItemCart> itemCart = itemCartRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(itemCart);
    }

    /**
     * DELETE  /item-carts/:id : delete the "id" itemCart.
     *
     * @param id the id of the itemCart to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/item-carts/{id}")
    @Timed
    public ResponseEntity<Void> deleteItemCart(@PathVariable Long id) {
        log.debug("REST request to delete ItemCart : {}", id);

        itemCartRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
