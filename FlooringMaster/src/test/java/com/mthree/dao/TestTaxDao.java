package com.mthree.dao;

import com.mthree.model.Product;
import com.mthree.model.Tax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestTaxDao {
    TaxDao dao;

    @BeforeEach
    void setUp() throws Exception{
        dao = new TaxDaoFileImpl();
    }

    @Test
    void testGetAllProducts() {
        List<Tax> taxes = dao.getAllTaxes();
        assertNotEquals(0, taxes.size());
    }
}
