package com.mthree.dao;

import com.mthree.model.Tax;

import java.util.List;

public interface TaxDao {
    void loadFile();
    List<Tax> getAllTaxes();
}
