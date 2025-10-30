package com.mthree.dao;

import com.mthree.model.Product;
import com.mthree.model.Tax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestTaxDao {
    TaxDaoFileImpl dao;

    @BeforeEach
    void setUp() {
        dao = new TaxDaoFileImpl(); // uses Data/Taxes.txt
    }

    @Test
    void testGetAllTaxes_ReadsFromFile() {
        List<Tax> taxes = dao.getAllTaxes();

        assertNotNull(taxes, "Tax list should not be null");
        assertFalse(taxes.isEmpty(), "Tax list should not be empty");

        Tax tx = taxes.stream()
                .filter(t -> "TX".equalsIgnoreCase(t.getStateAbr()))
                .findFirst()
                .orElse(null);

        assertNotNull(tx, "TX tax should exist");
        assertEquals("Texas", tx.getState());
        assertEquals("4.45", tx.getTaxRate().toPlainString());
    }
}
