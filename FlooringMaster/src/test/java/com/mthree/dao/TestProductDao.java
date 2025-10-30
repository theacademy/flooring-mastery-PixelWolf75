package com.mthree.dao;

import com.mthree.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestProductDao {
    ProductDaoFileImpl dao;

    @BeforeEach
    void setUp() {
        dao = new ProductDaoFileImpl(); // uses Data/Products.txt
    }

    @Test
    void testGetAllProducts_ReadsFromFile() {
        List<Product> products = dao.getAllProducts();

        assertNotNull(products, "Product list should not be null");
        assertFalse(products.isEmpty(), "Product list should not be empty");

        Product tile = products.stream()
                .filter(p -> "Tile".equalsIgnoreCase(p.getProductType()))
                .findFirst()
                .orElse(null);

        assertNotNull(tile, "Tile product should exist");
        assertEquals("3.50", tile.getCostPerSquareFoot().toPlainString());
        assertEquals("4.15", tile.getLaborCostPerSquareFoot().toPlainString());
    }
}
