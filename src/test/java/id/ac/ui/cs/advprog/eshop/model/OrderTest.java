package id.ac.ui.cs.advprog.eshop.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testDefaultStatusIsWaitingPayment() {
        Order order = new Order();
        assertEquals("WAITING_PAYMENT", order.getStatus());
    }

    @Test
    void testDefaultIdIsUuid() {
        Order order = new Order();
        assertDoesNotThrow(() -> UUID.fromString(order.getId()));
    }

    @ParameterizedTest
    @ValueSource(strings = {"WAITING_PAYMENT", "FAILED", "CANCELLED", "SUCCESS"})
    void testSetStatusAcceptsAllowedValues(String status) {
        Order order = new Order();
        order.setStatus(status);
        assertEquals(status, order.getStatus());
    }

    @Test
    void testSetStatusRejectsInvalidValueByKeepingPreviousStatus() {
        Order order = new Order();
        order.setStatus("SUCCESS");
        assertEquals("SUCCESS", order.getStatus());

        order.setStatus("NOT_A_VALID_STATUS");
        assertEquals("SUCCESS", order.getStatus());
    }

    @Test
    void testSetStatusRejectsNullByKeepingPreviousStatus() {
        Order order = new Order();
        order.setStatus("FAILED");
        assertEquals("FAILED", order.getStatus());

        order.setStatus(null);
        assertEquals("FAILED", order.getStatus());
    }

    @Test
    void testSetProductsRejectsNull() {
        Order order = new Order();
        assertThrows(IllegalArgumentException.class, () -> order.setProducts(null));
    }

    @Test
    void testSetProductsRejectsEmptyList() {
        Order order = new Order();
        assertThrows(IllegalArgumentException.class, () -> order.setProducts(List.of()));
    }

    @Test
    void testSetProductsAcceptsNonEmptyList() {
        Product p = new Product();
        p.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        p.setProductName("Sampo Cap Bambang");
        p.setProductQuantity(1);

        List<Product> products = List.of(p);

        Order order = new Order();
        order.setProducts(products);
        assertEquals(products, order.getProducts());
    }

    @Test
    void testSetAuthorRejectsNull() {
        Order order = new Order();
        assertThrows(IllegalArgumentException.class, () -> order.setAuthor(null));
    }
}

