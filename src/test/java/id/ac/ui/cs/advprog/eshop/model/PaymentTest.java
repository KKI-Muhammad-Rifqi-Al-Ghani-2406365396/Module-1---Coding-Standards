package id.ac.ui.cs.advprog.eshop.model;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    private Order order;
    private List<Product> products;

    @BeforeEach
    void setUp() {
        products = new ArrayList<>();

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
    }

    @Test
    void testCreatePayment() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);

        assertNotNull(payment);
        assertNotNull(payment.getPaymentId());
        assertEquals(order, payment.getOrder());
        assertEquals("VOUCHER_CODE", payment.getPaymentMethod());
        assertEquals(paymentData, payment.getPaymentData());
    }

    @Test
    void testSetPaymentStatus() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);
        payment.setPaymentStatus("SUCCESS");

        assertEquals("SUCCESS", payment.getPaymentStatus());
    }

    @Test
    void testSetPaymentStatusRejected() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);
        payment.setPaymentStatus("REJECTED");

        assertEquals("REJECTED", payment.getPaymentStatus());
    }
    @Test
    void testSetPaymentStatusInvalid() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        Payment payment = new Payment(order, "VOUCHER_CODE", paymentData);

        assertThrows(IllegalArgumentException.class, () -> {
            payment.setPaymentStatus("MEOW");
        });
    }
    @Test
    void testCreatePaymentWithEmptyMethod() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");

        assertThrows(IllegalArgumentException.class, () -> {
            new Payment(order, "", paymentData);
        });
    }
    @Test
    void testCreatePaymentWithNullPaymentData() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment(order, "VOUCHER_CODE", null);
        });
    }
    @Test
    void testCreatePaymentWithNullPaymentMethod() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Payment(order, "VOUCHER_CODE", null);
        });
    }

}
