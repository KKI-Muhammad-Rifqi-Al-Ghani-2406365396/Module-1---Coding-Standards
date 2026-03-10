package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PaymentRepository {
    private final List<Payment> paymentData = new ArrayList<>();

    public Payment save(Payment payment) {
        if (payment == null || payment.getPaymentId() == null) {
            throw new IllegalArgumentException();
        }

        int existingIndex = findPaymentIndexById(payment.getPaymentId());
        if (existingIndex != -1) {
            paymentData.set(existingIndex, payment);
        } else {
            paymentData.add(payment);
        }

        return payment;
    }

    public Payment findById(String paymentId) {
        if (paymentId == null) {
            return null;
        }

        int index = findPaymentIndexById(paymentId);
        return index != -1 ? paymentData.get(index) : null;
    }

    public List<Payment> findAll() {
        return new ArrayList<>(paymentData);
    }

    private int findPaymentIndexById(String paymentId) {
        for (int i = 0; i < paymentData.size(); i++) {
            if (paymentId.equals(paymentData.get(i).getPaymentId())) {
                return i;
            }
        }
        return -1;
    }
}