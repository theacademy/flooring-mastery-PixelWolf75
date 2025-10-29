package com.mthree.dao;

import com.mthree.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductDaoFileImpl implements ProductDao{
    @Override
    public void loadFile() {

    }

    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }
}
