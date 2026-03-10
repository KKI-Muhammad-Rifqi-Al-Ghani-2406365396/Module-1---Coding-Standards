package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import lombok.AccessLevel;
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

    @Setter(AccessLevel.NONE)
    private String paymentStatus;

    private Map<String, String> paymentData;

    public Payment(Order order, String method, Map<String, String> paymentData) {
        if (order == null) {
            throw new IllegalArgumentException();
        }
        if (method == null || method.trim().isEmpty()) {
            throw new IllegalArgumentException();
        }
        if (paymentData == null) {
            throw new IllegalArgumentException();
        }

        this.paymentId = UUID.randomUUID().toString();
        this.order = order;
        this.paymentMethod = method;
        this.paymentData = paymentData;
        this.setPaymentStatus(PaymentStatus.WAITING_PAYMENT.getValue());
    }

    public void setPaymentStatus(String paymentStatus) {
        if (PaymentStatus.contains(paymentStatus)) {
            this.paymentStatus = paymentStatus;
        } else {
            throw new IllegalArgumentException();
        }
    }
}