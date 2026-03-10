package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class Payment {
    private Order order;
    private String paymentId;
    private String paymentMethod;
    private String paymentStatus;
    private Map<String, String> paymentData;

    public Payment(Order order, String method, Map<String, String> paymentData) {
        this.paymentId = UUID.randomUUID().toString();
        this.order = order;
        this.paymentMethod = method;
        this.paymentData = paymentData;
    }
}
