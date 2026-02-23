package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public void deleteById(String productId) {
        productData.removeIf(p ->
                p.getProductId() != null && p.getProductId().equals(productId)
        );
    }

    public Optional<Product> findById(String productId) {
        return productData.stream()
                .filter(p -> p.getProductId() != null && p.getProductId().equals(productId))
                .findFirst();
    }

    public Product updateFields(String productId, String newName, int newQty) {
        Product existing = findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));

        existing.setProductName(newName);
        existing.setProductQuantity(newQty);
        return existing;
    }
}
