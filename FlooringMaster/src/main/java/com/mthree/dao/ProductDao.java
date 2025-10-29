package com.mthree.dao;

import com.mthree.model.Product;

import java.util.List;

public interface ProductDao {
    void loadFile();
    List<Product> getAllProducts();
}
