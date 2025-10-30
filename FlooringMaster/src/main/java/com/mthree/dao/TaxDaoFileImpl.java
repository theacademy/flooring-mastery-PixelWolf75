package com.mthree.dao;

import com.mthree.model.Product;
import com.mthree.model.Tax;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class TaxDaoFileImpl implements TaxDao{

    final String TAX_FILE = "Data/Taxes.txt";
    final String DELIMITER = ",";
    List<Tax> allTaxes = new ArrayList<>();

    //Loads all tax information from the tax file
    @Override
    public void loadFile() {
        try
        {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(TAX_FILE)));
            final int NUMBER_OF_TAX_VALUES = 3;
            sc.nextLine(); // move past category line
            while(sc.hasNext())
            {
                String taxEntry = sc.nextLine();
                String[] taxValues;
                taxValues = taxEntry.split(DELIMITER, NUMBER_OF_TAX_VALUES);
                allTaxes.add(new Tax(taxValues[1], taxValues[0], new BigDecimal(taxValues[2])));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //Get all taxes by loading the file
    @Override
    public List<Tax> getAllTaxes() {
        loadFile();
        return allTaxes;
    }
}
