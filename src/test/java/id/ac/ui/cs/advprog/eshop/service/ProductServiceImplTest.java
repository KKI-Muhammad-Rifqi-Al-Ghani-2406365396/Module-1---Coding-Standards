package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    private ProductServiceImpl service;

    @BeforeEach
    void setup() throws Exception {
        ProductRepository repo = new ProductRepository();
        service = new ProductServiceImpl();

        // Inject the repository into the @Autowired field using reflection
        Field f = ProductServiceImpl.class.getDeclaredField("productRepository");
        f.setAccessible(true);
        f.set(service, repo);
    }

    @Test
    void create_shouldGenerateId_whenIdNull() {
        Product p = new Product();
        p.setProductId(null);
        p.setProductName("A");
        p.setProductQuantity(1);

        Product created = service.create(p);

        assertNotNull(created.getProductId());
        assertFalse(created.getProductId().isBlank());
        assertEquals("A", created.getProductName());
        assertEquals(1, created.getProductQuantity());
    }

    @Test
    void create_shouldGenerateId_whenIdBlank() {
        Product p = new Product();
        p.setProductId("   ");
        p.setProductName("B");
        p.setProductQuantity(2);

        Product created = service.create(p);

        assertNotNull(created.getProductId());
        assertFalse(created.getProductId().isBlank());
    }

    @Test
    void create_shouldKeepId_whenAlreadySet() {
        Product p = new Product();
        p.setProductId("fixed-id");
        p.setProductName("C");
        p.setProductQuantity(3);

        Product created = service.create(p);

        assertEquals("fixed-id", created.getProductId());
    }

    @Test
    void findAll_shouldReturnEmptyList_whenNoProducts() {
        List<Product> all = service.findAll();

        assertNotNull(all);
        assertTrue(all.isEmpty());
    }

    @Test
    void findAll_shouldReturnAllProducts_whenProductsExist() {
        Product p1 = new Product();
        p1.setProductId("1");
        p1.setProductName("P1");
        p1.setProductQuantity(10);
        service.create(p1);

        Product p2 = new Product();
        p2.setProductId("2");
        p2.setProductName("P2");
        p2.setProductQuantity(20);
        service.create(p2);

        List<Product> all = service.findAll();

        assertEquals(2, all.size());
        assertEquals("1", all.get(0).getProductId());
        assertEquals("2", all.get(1).getProductId());
    }

    @Test
    void deleteById_shouldRemoveProduct_whenIdExists() {
        Product p = new Product();
        p.setProductId("A");
        p.setProductName("ToDelete");
        p.setProductQuantity(5);
        service.create(p);

        service.deleteById("A");

        assertThrows(IllegalArgumentException.class, () -> service.findById("A"));
    }

    @Test
    void deleteById_shouldNotThrow_whenIdDoesNotExist() {
        // just ensure no exception is thrown
        assertDoesNotThrow(() -> service.deleteById("NOT_EXIST"));
    }

    @Test
    void findById_shouldReturnProduct_whenExists() {
        Product p = new Product();
        p.setProductId("X");
        p.setProductName("Found");
        p.setProductQuantity(7);
        service.create(p);

        Product found = service.findById("X");

        assertEquals("X", found.getProductId());
        assertEquals("Found", found.getProductName());
        assertEquals(7, found.getProductQuantity());
    }

    @Test
    void findById_shouldThrow_whenNotFound() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.findById("missing")
        );
        assertTrue(ex.getMessage().contains("Product not found"));
    }

    @Test
    void update_shouldUpdateNameAndQty_whenExists() {
        Product p = new Product();
        p.setProductId("U1");
        p.setProductName("OldName");
        p.setProductQuantity(1);
        service.create(p);

        Product updated = service.update("U1", "NewName", 99);

        assertEquals("U1", updated.getProductId());
        assertEquals("NewName", updated.getProductName());
        assertEquals(99, updated.getProductQuantity());

        // verify persisted object is updated too
        Product fetchedAgain = service.findById("U1");
        assertEquals("NewName", fetchedAgain.getProductName());
        assertEquals(99, fetchedAgain.getProductQuantity());
    }

    @Test
    void update_shouldThrow_whenNotFound() {
        assertThrows(IllegalArgumentException.class, () ->
                service.update("MISSING", "NewName", 10)
        );
    }
}