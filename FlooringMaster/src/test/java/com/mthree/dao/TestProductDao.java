package com.mthree.dao;

import com.mthree.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestProductDao {
    ProductDao dao;

    @BeforeEach
    void setUp() throws Exception{
        dao = new ProductDaoFileImpl();
    }

    @Test
    void testGetAllProducts() {
        List<Product> products = dao.getAllProducts();
        assertNotEquals(0, products.size());
    }
}
