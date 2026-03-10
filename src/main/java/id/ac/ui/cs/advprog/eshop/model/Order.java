package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
public class Order {
    private static final Set<String> ALLOWED_STATUSES = Set.of(
            "WAITING_PAYMENT",
            "FAILED",
            "CANCELLED",
            "SUCCESS"
    );

    @Setter
    private String id; // UUID
    private List<Product> products; // not null, not empty
    @Setter
    private long orderTime; // UNIX timestamp (seconds)
    private String author; // not null
    private String status = "WAITING_PAYMENT";

    public Order() {
        this.id = UUID.randomUUID().toString();
        this.orderTime = Instant.now().getEpochSecond();
        this.status = "WAITING_PAYMENT";
    }

    public void setProducts(List<Product> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Order products must not be null or empty");
        }
        this.products = products;
    }

    public void setAuthor(String author) {
        if (author == null) {
            throw new IllegalArgumentException("Order author must not be null");
        }
        this.author = author;
    }

    /**
     * Only accepts the 4 allowed status strings.
     * Invalid inputs are rejected by keeping the previous status.
     */
    public void setStatus(String status) {
        if (status != null && ALLOWED_STATUSES.contains(status)) {
            this.status = status;
        }
    }
}

