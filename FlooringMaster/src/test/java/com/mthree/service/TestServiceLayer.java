package com.mthree.service;

import com.mthree.controller.Controller;
import com.mthree.dao.OrderDaoFileImpl;
import com.mthree.dao.ProductDaoFileImpl;
import com.mthree.dao.TaxDaoFileImpl;
import com.mthree.model.Order;
import com.mthree.model.Product;
import com.mthree.model.Tax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestServiceLayer {

    private ServiceLayerImpl service;
    private Path tempOrderDir;

    @BeforeEach
    void setUp() throws Exception {
        tempOrderDir = Files.createTempDirectory("test-orders");

        OrderDaoFileImpl orderDao = new OrderDaoFileImpl() {
            {
                ORDER_FOLDER = tempOrderDir.toString() + File.separator;
            }
        };
        ProductDaoFileImpl productDao = new ProductDaoFileImpl();
        TaxDaoFileImpl taxDao = new TaxDaoFileImpl();

        service = new ServiceLayerImpl(orderDao, productDao, taxDao);
    }

    @Test
    void testAddAndRetrieveOrder() {
        LocalDate date = LocalDate.of(2025, 10, 30);
        Order order = new Order(
                1, "Alice", "TX", date,
                new BigDecimal("4.45"), "Tile",
                new BigDecimal("100"),
                new BigDecimal("3.50"),
                new BigDecimal("4.15")
        );

        service.addOrder(order);

        File[] files = tempOrderDir.toFile().listFiles();
        assertNotNull(files);
        assertTrue(files.length > 0, "Expected order file to be created");

        Order retrieved = service.getOrder(date, 1);
        assertNotNull(retrieved);
        assertEquals("Alice", retrieved.getCustomerName());
        assertEquals("TX", retrieved.getState());
    }

    @Test
    void testGetProductsAndTaxes() {
        List<Product> products = service.getProducts();
        List<Tax> taxes = service.getTaxes();

        assertFalse(products.isEmpty(), "Products should not be empty");
        assertFalse(taxes.isEmpty(), "Taxes should not be empty");

        assertTrue(products.stream().anyMatch(p -> p.getProductType().equalsIgnoreCase("Tile")));
        assertTrue(taxes.stream().anyMatch(t -> t.getStateAbr().equalsIgnoreCase("TX")));
    }
}
