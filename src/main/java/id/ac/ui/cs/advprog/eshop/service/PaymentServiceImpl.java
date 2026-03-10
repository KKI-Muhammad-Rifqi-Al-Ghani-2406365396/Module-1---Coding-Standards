package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment(order, method, paymentData);

        if ("VOUCHER_CODE".equals(method)) {
            processVoucherPayment(payment);
        }

        return paymentRepository.save(payment);
    }
    // Voucher Implementation
    private void processVoucherPayment(Payment payment) {
        String voucherCode = payment.getPaymentData().get("voucherCode");

        if (isValidVoucherCode(voucherCode)) {
            payment.setPaymentStatus(PaymentStatus.SUCCESS.getValue());
            payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
        } else {
            payment.setPaymentStatus(PaymentStatus.REJECTED.getValue());
            payment.getOrder().setStatus(OrderStatus.FAILED.getValue());
        }
    }

    private boolean isValidVoucherCode(String voucherCode) {
        if (voucherCode == null) {
            return false;
        }
        if (voucherCode.length() != 16) {
            return false;
        }
        if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }

        int digitCount = 0;
        for (int i = 0; i < voucherCode.length(); i++) {
            if (Character.isDigit(voucherCode.charAt(i))) {
                digitCount++;
            }
        }
        return digitCount == 8;
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        if (payment == null) {
            throw new IllegalArgumentException();
        }

        PaymentStatus paymentStatus = parsePaymentStatus(status);
        payment.setPaymentStatus(paymentStatus.getValue());
        switch (paymentStatus) {
            case SUCCESS -> payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
            case REJECTED -> payment.getOrder().setStatus(OrderStatus.FAILED.getValue());
            case WAITING_PAYMENT -> {
                // do nothing
            }
        }

        return paymentRepository.save(payment);
    }

    private static PaymentStatus parsePaymentStatus(String status) {
        if (status == null) {
            throw new IllegalArgumentException();
        }
        for (PaymentStatus s : PaymentStatus.values()) {
            if (s.getValue().equals(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}
