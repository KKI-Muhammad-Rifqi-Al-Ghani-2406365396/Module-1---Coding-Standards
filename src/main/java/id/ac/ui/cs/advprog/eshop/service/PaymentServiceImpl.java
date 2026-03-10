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
        } else if ("BANK_TRANSFER".equals(method)) {
            processBankTransferPayment(payment);
        }

        return paymentRepository.save(payment);
    }
    // Vouchers
    private void processVoucherPayment(Payment payment) {
        PaymentStatus paymentStatus = isValidVoucherCode(
                payment.getPaymentData().get("voucherCode"))
                ? PaymentStatus.SUCCESS : PaymentStatus.REJECTED;

        applyPaymentStatus(payment, paymentStatus);
    }

    private boolean isValidVoucherCode(String voucherCode) {
        if (voucherCode == null || voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            return false;
        }

        int digitCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }
        return digitCount == 8;
    }
    // Bank Transfers
    private void processBankTransferPayment(Payment payment) {
        String bankName = payment.getPaymentData().get("bankName");
        String referenceCode = payment.getPaymentData().get("referenceCode");

        PaymentStatus paymentStatus = isValidBankTransfer(bankName, referenceCode)
                ? PaymentStatus.SUCCESS
                : PaymentStatus.REJECTED;

        applyPaymentStatus(payment, paymentStatus);
    }
    private boolean isValidBankTransfer(String bankName, String referenceCode) {
        return bankName != null && !bankName.trim().isEmpty()
                && referenceCode != null && !referenceCode.trim().isEmpty();
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        if (payment == null) {
            throw new IllegalArgumentException();
        }

        PaymentStatus paymentStatus = parsePaymentStatus(status);
        applyPaymentStatus(payment, paymentStatus);

        return paymentRepository.save(payment);
    }

    private void applyPaymentStatus(Payment payment, PaymentStatus paymentStatus) {
        payment.setPaymentStatus(paymentStatus.getValue());

        switch (paymentStatus) {
            case SUCCESS -> payment.getOrder().setStatus(OrderStatus.SUCCESS.getValue());
            case REJECTED -> payment.getOrder().setStatus(OrderStatus.FAILED.getValue());
            case WAITING_PAYMENT -> {
                // do nothing
            }
        }
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