package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductControllerUnitTest {

    @Test
    void productListPage_shouldReturnProductListView() {
        ProductService service = Mockito.mock(ProductService.class);
        ProductController controller = new ProductController();

        // inject service via reflection because field is @Autowired
        try {
            var f = ProductController.class.getDeclaredField("service");
            f.setAccessible(true);
            f.set(controller, service);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Mockito.when(service.findAll()).thenReturn(List.of(new Product()));

        Model model = new ConcurrentModel();
        String view = controller.productListPage(model);

        assertEquals("ProductList", view);
    }
}