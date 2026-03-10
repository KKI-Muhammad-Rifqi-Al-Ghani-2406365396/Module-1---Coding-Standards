package id.ac.ui.cs.advprog.eshop.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;

@Getter
@Setter
public class Payment {
    private static final String STATUS_WAITING_PAYMENT = "WAITING_PAYMENT";
    private static final String STATUS_SUCCESS = "SUCCESS";
    private static final String STATUS_REJECTED = "REJECTED";

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
        this.setPaymentStatus(STATUS_WAITING_PAYMENT);
    }

    public void setPaymentStatus(String paymentStatus) {
        if (STATUS_WAITING_PAYMENT.equals(paymentStatus)
                || STATUS_SUCCESS.equals(paymentStatus)
                || STATUS_REJECTED.equals(paymentStatus)) {
            this.paymentStatus = paymentStatus;
        } else {
            throw new IllegalArgumentException();
        }
    }
}
