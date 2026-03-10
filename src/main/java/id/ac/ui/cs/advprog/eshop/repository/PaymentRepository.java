package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {
    private List<Payment> paymentData = new ArrayList<>();

    public Payment save(Payment payment) {
        if (payment == null || payment.getPaymentId() == null) {
            throw new IllegalArgumentException();
        }
        if (payment.getPaymentId() == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < paymentData.size(); i++) {
            Payment savedPayment = paymentData.get(i);
            if (savedPayment.getPaymentId() != null && savedPayment.getPaymentId().equals(payment.getPaymentId())) {
                paymentData.set(i, payment);
                return payment;
            }
        }

        paymentData.add(payment);
        return payment;
    }

    public Payment findById(String paymentId) {
        if (paymentId == null) {
            return null;
        }
        for (Payment savedPayment : paymentData) {
            if (savedPayment.getPaymentId() != null && savedPayment.getPaymentId().equals(paymentId)) {
                return savedPayment;
            }
        }
        return null;
    }

    public List<Payment> findAll() {
        return new ArrayList<>(paymentData);
    }
}
