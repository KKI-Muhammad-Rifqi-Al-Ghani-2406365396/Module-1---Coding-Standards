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

    public void deleteById(String productID) {
        productData.removeIf(p -> p.getProductID() != null && p.getProductID().equals(productID));
    }

    public Optional<Product> findById(String productID) {
        return productData.stream()
                .filter(p -> p.getProductID() != null && p.getProductID().equals(productID))
                .findFirst();
    }



    public Product updateFields(String productID, String newName, int newQty) {
        Product existing = findById(productID)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productID));

        existing.setProductName(newName);
        existing.setProductQuantity(newQty);
        return existing;
    }
}
