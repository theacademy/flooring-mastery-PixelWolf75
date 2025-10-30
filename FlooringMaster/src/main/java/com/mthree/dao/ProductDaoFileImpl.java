package com.mthree.dao;

import com.mthree.model.Product;
import com.mthree.model.Tax;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.*;

@Component
public class ProductDaoFileImpl implements ProductDao{

    final String PRODUCT_FILE = "Data/Products.txt";
    final String DELIMITER = ",";
    List<Product> allProducts = new ArrayList<>();

    //Loads all products from the product file
    @Override
    public void loadFile() {
        try
        {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(PRODUCT_FILE)));
            final int NUMBER_OF_PRODUCT_VALUES = 3;
            sc.nextLine(); // move past category line
            while(sc.hasNext())
            {
                String productEntry = sc.nextLine();
                String[] productValues = new String[3];
                productValues = productEntry.split(DELIMITER, NUMBER_OF_PRODUCT_VALUES);
                allProducts.add(new Product(productValues[0], new BigDecimal(productValues[1]), new BigDecimal(productValues[2])));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Gets all products after loading the file
    @Override
    public List<Product> getAllProducts() {
        loadFile();
        return allProducts;
    }
}
