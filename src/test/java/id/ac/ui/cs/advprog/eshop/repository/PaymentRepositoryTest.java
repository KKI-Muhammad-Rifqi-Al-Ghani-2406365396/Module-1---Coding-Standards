package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    PaymentRepository paymentRepository;
    List<Payment> payments;
    Order order;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("prod-1");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        order = new Order(
                "order-123",
                products,
                1708560000L,
                "Safira Sudrajat",
                OrderStatus.WAITING_PAYMENT.getValue()
        );

        payments = new ArrayList<>();

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment1 = new Payment(order, "VOUCHER_CODE", paymentData1);
        payment1.setPaymentStatus(PaymentStatus.SUCCESS.getValue());
        payments.add(payment1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("bankName", "BCA");
        paymentData2.put("referenceCode", "TRF-001");
        Payment payment2 = new Payment(order, "BANK_TRANSFER", paymentData2);
        payment2.setPaymentStatus(PaymentStatus.WAITING_PAYMENT.getValue());
        payments.add(payment2);
    }

    @Test
    void testSaveCreate() {
        Payment payment = payments.get(0);
        Payment result = paymentRepository.save(payment);

        Payment findResult = paymentRepository.findById(payment.getPaymentId());
        assertEquals(payment.getPaymentId(), result.getPaymentId());
        assertNotNull(findResult);
        assertEquals(payment.getPaymentId(), findResult.getPaymentId());
        assertEquals(payment.getOrder(), findResult.getOrder());
        assertEquals(payment.getPaymentMethod(), findResult.getPaymentMethod());
        assertEquals(payment.getPaymentStatus(), findResult.getPaymentStatus());
        assertEquals(payment.getPaymentData(), findResult.getPaymentData());
    }

    @Test
    void testSaveUpdate() {
        Payment payment = payments.get(0);
        paymentRepository.save(payment);

        Map<String, String> newPaymentData = new HashMap<>();
        newPaymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment updatedPayment = new Payment(order, "VOUCHER_CODE", newPaymentData);
        updatedPayment.setPaymentStatus(PaymentStatus.REJECTED.getValue());

        // force same id so repository should treat it as update
        updatedPayment.setPaymentId(payment.getPaymentId());

        Payment result = paymentRepository.save(updatedPayment);
        Payment findResult = paymentRepository.findById(payment.getPaymentId());

        assertEquals(payment.getPaymentId(), result.getPaymentId());
        assertNotNull(findResult);
        assertEquals(payment.getPaymentId(), findResult.getPaymentId());
        assertEquals(PaymentStatus.REJECTED.getValue(), findResult.getPaymentStatus());
        assertEquals(updatedPayment.getPaymentMethod(), findResult.getPaymentMethod());
        assertEquals(updatedPayment.getPaymentData(), findResult.getPaymentData());
    }

    @Test
    void testFindByIdIfIdFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById(payments.get(0).getPaymentId());
        assertNotNull(findResult);
        assertEquals(payments.get(0).getPaymentId(), findResult.getPaymentId());
        assertEquals(payments.get(0).getOrder(), findResult.getOrder());
        assertEquals(payments.get(0).getPaymentMethod(), findResult.getPaymentMethod());
        assertEquals(payments.get(0).getPaymentStatus(), findResult.getPaymentStatus());
        assertEquals(payments.get(0).getPaymentData(), findResult.getPaymentData());
    }

    @Test
    void testFindByIdIfIdNotFound() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        Payment findResult = paymentRepository.findById("payment-does-not-exist");
        assertNull(findResult);
    }

    @Test
    void testFindAllIfNotEmpty() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        List<Payment> result = paymentRepository.findAll();
        assertEquals(2, result.size());
    }

    @Test
    void testFindAllIfEmpty() {
        List<Payment> result = paymentRepository.findAll();
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSaveNullPaymentShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> paymentRepository.save(null));
    }

    @Test
    void testFindByIdIfIdNull() {
        assertNull(paymentRepository.findById(null));
    }

    @Test
    void testSaveUpdateShouldNotIncreaseSize() {
        Payment payment = payments.get(0);
        paymentRepository.save(payment);

        Map<String, String> newPaymentData = new HashMap<>();
        newPaymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment updatedPayment = new Payment(order, "VOUCHER_CODE", newPaymentData);
        updatedPayment.setPaymentStatus(PaymentStatus.REJECTED.getValue());
        updatedPayment.setPaymentId(payment.getPaymentId());

        paymentRepository.save(updatedPayment);

        List<Payment> result = paymentRepository.findAll();
        assertEquals(1, result.size());
        assertEquals(payment.getPaymentId(), result.get(0).getPaymentId());
        assertEquals(PaymentStatus.REJECTED.getValue(), result.get(0).getPaymentStatus());
    }

    @Test
    void testFindAllShouldContainSavedPayments() {
        for (Payment payment : payments) {
            paymentRepository.save(payment);
        }

        List<Payment> result = paymentRepository.findAll();
        assertEquals(2, result.size());
        assertEquals(payments.get(0).getPaymentId(), result.get(0).getPaymentId());
        assertEquals(payments.get(1).getPaymentId(), result.get(1).getPaymentId());
    }
}
