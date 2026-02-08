package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createProduct_userCanSeeNewProduct(ChromeDriver driver) {
        String newProductName = "Functional Test Product";
        String newProductQty = "67";

        // Open Create Product page
        driver.get(baseUrl + "/product/create");

        // Fill inputs
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement qtyInput = driver.findElement(By.id("quantityInput"));

        nameInput.clear();
        nameInput.sendKeys(newProductName);

        qtyInput.clear();
        qtyInput.sendKeys(newProductQty);

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        //
        if (!driver.getCurrentUrl().contains("/product/list")) {
            driver.get(baseUrl + "/product/list");
        }

        // Verify new product is visible
        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains(newProductName),
                "Expected product name to appear in product list page");
        assertTrue(pageSource.contains(newProductQty),
                "Expected product quantity to appear in product list page");
    }
}
