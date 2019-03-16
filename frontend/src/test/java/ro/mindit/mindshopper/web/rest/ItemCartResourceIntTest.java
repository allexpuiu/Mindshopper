package ro.mindit.mindshopper.web.rest;

import ro.mindit.mindshopper.MindShopperApp;

import ro.mindit.mindshopper.domain.ItemCart;
import ro.mindit.mindshopper.repository.ItemCartRepository;
import ro.mindit.mindshopper.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;


import static ro.mindit.mindshopper.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ItemCartResource REST controller.
 *
 * @see ItemCartResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MindShopperApp.class)
public class ItemCartResourceIntTest {

    private static final String DEFAULT_ITEM_ID = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_ID = "BBBBBBBBBB";

    private static final String DEFAULT_ITEM_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ITEM_DESCRIPTION = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final String DEFAULT_CATEGORY_ID = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private ItemCartRepository itemCartRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restItemCartMockMvc;

    private ItemCart itemCart;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemCartResource itemCartResource = new ItemCartResource(itemCartRepository);
        this.restItemCartMockMvc = MockMvcBuilders.standaloneSetup(itemCartResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ItemCart createEntity(EntityManager em) {
        ItemCart itemCart = new ItemCart()
            .itemId(DEFAULT_ITEM_ID)
            .itemDescription(DEFAULT_ITEM_DESCRIPTION)
            .price(DEFAULT_PRICE)
            .quantity(DEFAULT_QUANTITY)
            .categoryId(DEFAULT_CATEGORY_ID)
            .categoryDescription(DEFAULT_CATEGORY_DESCRIPTION);
        return itemCart;
    }

    @Before
    public void initTest() {
        itemCart = createEntity(em);
    }

    @Test
    @Transactional
    public void createItemCart() throws Exception {
        int databaseSizeBeforeCreate = itemCartRepository.findAll().size();

        // Create the ItemCart
        restItemCartMockMvc.perform(post("/api/item-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCart)))
            .andExpect(status().isCreated());

        // Validate the ItemCart in the database
        List<ItemCart> itemCartList = itemCartRepository.findAll();
        assertThat(itemCartList).hasSize(databaseSizeBeforeCreate + 1);
        ItemCart testItemCart = itemCartList.get(itemCartList.size() - 1);
        assertThat(testItemCart.getItemId()).isEqualTo(DEFAULT_ITEM_ID);
        assertThat(testItemCart.getItemDescription()).isEqualTo(DEFAULT_ITEM_DESCRIPTION);
        assertThat(testItemCart.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testItemCart.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testItemCart.getCategoryId()).isEqualTo(DEFAULT_CATEGORY_ID);
        assertThat(testItemCart.getCategoryDescription()).isEqualTo(DEFAULT_CATEGORY_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createItemCartWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = itemCartRepository.findAll().size();

        // Create the ItemCart with an existing ID
        itemCart.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restItemCartMockMvc.perform(post("/api/item-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCart)))
            .andExpect(status().isBadRequest());

        // Validate the ItemCart in the database
        List<ItemCart> itemCartList = itemCartRepository.findAll();
        assertThat(itemCartList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkItemIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemCartRepository.findAll().size();
        // set the field null
        itemCart.setItemId(null);

        // Create the ItemCart, which fails.

        restItemCartMockMvc.perform(post("/api/item-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCart)))
            .andExpect(status().isBadRequest());

        List<ItemCart> itemCartList = itemCartRepository.findAll();
        assertThat(itemCartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkItemDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemCartRepository.findAll().size();
        // set the field null
        itemCart.setItemDescription(null);

        // Create the ItemCart, which fails.

        restItemCartMockMvc.perform(post("/api/item-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCart)))
            .andExpect(status().isBadRequest());

        List<ItemCart> itemCartList = itemCartRepository.findAll();
        assertThat(itemCartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemCartRepository.findAll().size();
        // set the field null
        itemCart.setPrice(null);

        // Create the ItemCart, which fails.

        restItemCartMockMvc.perform(post("/api/item-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCart)))
            .andExpect(status().isBadRequest());

        List<ItemCart> itemCartList = itemCartRepository.findAll();
        assertThat(itemCartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemCartRepository.findAll().size();
        // set the field null
        itemCart.setCategoryId(null);

        // Create the ItemCart, which fails.

        restItemCartMockMvc.perform(post("/api/item-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCart)))
            .andExpect(status().isBadRequest());

        List<ItemCart> itemCartList = itemCartRepository.findAll();
        assertThat(itemCartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCategoryDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = itemCartRepository.findAll().size();
        // set the field null
        itemCart.setCategoryDescription(null);

        // Create the ItemCart, which fails.

        restItemCartMockMvc.perform(post("/api/item-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCart)))
            .andExpect(status().isBadRequest());

        List<ItemCart> itemCartList = itemCartRepository.findAll();
        assertThat(itemCartList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllItemCarts() throws Exception {
        // Initialize the database
        itemCartRepository.saveAndFlush(itemCart);

        // Get all the itemCartList
        restItemCartMockMvc.perform(get("/api/item-carts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(itemCart.getId().intValue())))
            .andExpect(jsonPath("$.[*].itemId").value(hasItem(DEFAULT_ITEM_ID.toString())))
            .andExpect(jsonPath("$.[*].itemDescription").value(hasItem(DEFAULT_ITEM_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].categoryId").value(hasItem(DEFAULT_CATEGORY_ID.toString())))
            .andExpect(jsonPath("$.[*].categoryDescription").value(hasItem(DEFAULT_CATEGORY_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getItemCart() throws Exception {
        // Initialize the database
        itemCartRepository.saveAndFlush(itemCart);

        // Get the itemCart
        restItemCartMockMvc.perform(get("/api/item-carts/{id}", itemCart.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(itemCart.getId().intValue()))
            .andExpect(jsonPath("$.itemId").value(DEFAULT_ITEM_ID.toString()))
            .andExpect(jsonPath("$.itemDescription").value(DEFAULT_ITEM_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.categoryId").value(DEFAULT_CATEGORY_ID.toString()))
            .andExpect(jsonPath("$.categoryDescription").value(DEFAULT_CATEGORY_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingItemCart() throws Exception {
        // Get the itemCart
        restItemCartMockMvc.perform(get("/api/item-carts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateItemCart() throws Exception {
        // Initialize the database
        itemCartRepository.saveAndFlush(itemCart);

        int databaseSizeBeforeUpdate = itemCartRepository.findAll().size();

        // Update the itemCart
        ItemCart updatedItemCart = itemCartRepository.findById(itemCart.getId()).get();
        // Disconnect from session so that the updates on updatedItemCart are not directly saved in db
        em.detach(updatedItemCart);
        updatedItemCart
            .itemId(UPDATED_ITEM_ID)
            .itemDescription(UPDATED_ITEM_DESCRIPTION)
            .price(UPDATED_PRICE)
            .quantity(UPDATED_QUANTITY)
            .categoryId(UPDATED_CATEGORY_ID)
            .categoryDescription(UPDATED_CATEGORY_DESCRIPTION);

        restItemCartMockMvc.perform(put("/api/item-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedItemCart)))
            .andExpect(status().isOk());

        // Validate the ItemCart in the database
        List<ItemCart> itemCartList = itemCartRepository.findAll();
        assertThat(itemCartList).hasSize(databaseSizeBeforeUpdate);
        ItemCart testItemCart = itemCartList.get(itemCartList.size() - 1);
        assertThat(testItemCart.getItemId()).isEqualTo(UPDATED_ITEM_ID);
        assertThat(testItemCart.getItemDescription()).isEqualTo(UPDATED_ITEM_DESCRIPTION);
        assertThat(testItemCart.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testItemCart.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testItemCart.getCategoryId()).isEqualTo(UPDATED_CATEGORY_ID);
        assertThat(testItemCart.getCategoryDescription()).isEqualTo(UPDATED_CATEGORY_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingItemCart() throws Exception {
        int databaseSizeBeforeUpdate = itemCartRepository.findAll().size();

        // Create the ItemCart

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restItemCartMockMvc.perform(put("/api/item-carts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(itemCart)))
            .andExpect(status().isBadRequest());

        // Validate the ItemCart in the database
        List<ItemCart> itemCartList = itemCartRepository.findAll();
        assertThat(itemCartList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteItemCart() throws Exception {
        // Initialize the database
        itemCartRepository.saveAndFlush(itemCart);

        int databaseSizeBeforeDelete = itemCartRepository.findAll().size();

        // Get the itemCart
        restItemCartMockMvc.perform(delete("/api/item-carts/{id}", itemCart.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ItemCart> itemCartList = itemCartRepository.findAll();
        assertThat(itemCartList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ItemCart.class);
        ItemCart itemCart1 = new ItemCart();
        itemCart1.setId(1L);
        ItemCart itemCart2 = new ItemCart();
        itemCart2.setId(itemCart1.getId());
        assertThat(itemCart1).isEqualTo(itemCart2);
        itemCart2.setId(2L);
        assertThat(itemCart1).isNotEqualTo(itemCart2);
        itemCart1.setId(null);
        assertThat(itemCart1).isNotEqualTo(itemCart2);
    }
}
