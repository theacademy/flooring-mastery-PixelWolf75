package com.mthree.dao;

import com.mthree.model.Order;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestOrderDao {
    private OrderDaoFileImpl dao;
    private Path tempDir;

    @BeforeEach
    void setUp() throws Exception {
        // Create a temporary directory for test files
        tempDir = Files.createTempDirectory("orders_test_");

        dao = new OrderDaoFileImpl() {
            { // Override the order folder for testing
                this.orders = new HashMap<>();
                this.largestOrderNumber = 0;
                // Redirect the order folder to our temp folder
                super.ORDER_FOLDER.replace("Orders/", tempDir.toString() + File.separator);
            }
        };

        setOrderFolderForTesting(dao, tempDir);
    }

    private void setOrderFolderForTesting(OrderDaoFileImpl dao, Path folder) {
        try {
            var field = OrderDaoFileImpl.class.getDeclaredField("ORDER_FOLDER");
            field.setAccessible(true);
            field.set(dao, folder.toString() + File.separator);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    // Clean up temporary files
    @AfterEach
    void tearDown() throws Exception {
        Files.walk(tempDir)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

    //Failed because it can't create in the temp folder
    @Test
    void testAddAndGetOrder() {
        LocalDate date = LocalDate.of(2025, 10, 30);
        Order order = new Order(1, "Alice", "TX", date,
                new BigDecimal("4.45"), "Tile",
                new BigDecimal("100"), new BigDecimal("3.50"), new BigDecimal("4.15"));

        dao.addOrder(order);

        // File should have been created
        File[] files = tempDir.toFile().listFiles();
        assertNotNull(files);
        assertTrue(files.length > 0);

        // Should be able to read back the same order
        Order loaded = dao.getOrder(date, 1);
        assertNotNull(loaded);
        assertEquals("Alice", loaded.getCustomerName());
        assertEquals("TX", loaded.getState());
    }

    @Test
    void testGetOrdersFromDate() {
        LocalDate date = LocalDate.of(2025, 10, 31);
        Order o1 = new Order(1, "Bob", "CA", date,
                new BigDecimal("2.5"), "Carpet",
                new BigDecimal("50"), new BigDecimal("2.25"), new BigDecimal("2.75"));
        dao.addOrder(o1);

        List<Order> orders = dao.getOrdersFromDate(date);

        assertNotNull(orders);
        assertEquals(1, orders.size());
        assertEquals("Bob", orders.get(0).getCustomerName());
    }

    @Test
    void testRemoveOrder() {
        LocalDate date = LocalDate.of(2025, 10, 30);
        Order order = new Order(1, "Charlie", "TX", date,
                new BigDecimal("3.25"), "Wood",
                new BigDecimal("80"), new BigDecimal("5.15"), new BigDecimal("4.25"));
        dao.addOrder(order);

        dao.removeOrder(date, 1);

        List<Order> orders = dao.getOrdersFromDate(date);
        assertTrue(orders == null || orders.isEmpty());
    }

    @Test
    void testGetNextOrderNumber() {
        LocalDate date = LocalDate.of(2025, 10, 29);
        dao.addOrder(new Order(1, "Dave", "TX", date,
                new BigDecimal("1.0"), "Laminate",
                new BigDecimal("20"), new BigDecimal("1.5"), new BigDecimal("2.0")));

        assertEquals(1, dao.getNextOrderNumber());
    }
}
