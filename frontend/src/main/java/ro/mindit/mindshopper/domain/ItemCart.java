package ro.mindit.mindshopper.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A ItemCart.
 */
@Entity
@Table(name = "item_cart")
public class ItemCart implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(max = 50)
    @Column(name = "item_id", length = 50, nullable = false)
    private String itemId;

    @NotNull
    @Size(max = 150)
    @Column(name = "item_description", length = 150, nullable = false)
    private String itemDescription;

    @NotNull
    @Column(name = "price", precision = 10, scale = 2, nullable = false)
    private BigDecimal price;

    @Column(name = "quantity")
    private Integer quantity;

    @NotNull
    @Size(max = 50)
    @Column(name = "category_id", length = 50, nullable = false)
    private String categoryId;

    @NotNull
    @Size(max = 100)
    @Column(name = "category_description", length = 100, nullable = false)
    private String categoryDescription;

    @ManyToOne
    @JsonIgnoreProperties("items")
    private Cart cart;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public ItemCart itemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public ItemCart itemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
        return this;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ItemCart price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public ItemCart quantity(Integer quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public ItemCart categoryId(String categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public ItemCart categoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
        return this;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public Cart getCart() {
        return cart;
    }

    public ItemCart cart(Cart cart) {
        this.cart = cart;
        return this;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ItemCart itemCart = (ItemCart) o;
        if (itemCart.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemCart.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemCart{" +
            "id=" + getId() +
            ", itemId='" + getItemId() + "'" +
            ", itemDescription='" + getItemDescription() + "'" +
            ", price=" + getPrice() +
            ", quantity=" + getQuantity() +
            ", categoryId='" + getCategoryId() + "'" +
            ", categoryDescription='" + getCategoryDescription() + "'" +
            "}";
    }
}
