package kt.kt1java.orders.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String product;

    @Min(1)
    private int quantity;

    @Min(0)
    private BigDecimal price;

    private String status;

    private LocalDate orderDate;

    // Конструкторы, геттеры и сеттеры

    public Order() {
        this.orderDate = LocalDate.now();  // по умолчанию дата создания заказа - сегодня
    }

    public Order(String product, int quantity, BigDecimal price, String status) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
        this.orderDate = LocalDate.now();  // по умолчанию дата создания заказа - сегодня
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }
}
