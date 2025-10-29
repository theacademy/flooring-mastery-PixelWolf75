package com.mthree.dao;

import com.mthree.model.Tax;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaxDaoFileImpl implements TaxDao{
    @Override
    public void loadFile() {

    }

    @Override
    public List<Tax> getAllTaxes() {
        return List.of();
    }
}
