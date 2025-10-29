package com.mthree.service;

import com.mthree.dao.OrderDao;
import com.mthree.dao.ProductDao;
import com.mthree.dao.TaxDao;
import com.mthree.model.Order;
import com.mthree.model.Product;
import com.mthree.model.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ServiceLayerImpl implements ServiceLayer{
    private OrderDao orderDao;
    private ProductDao productDao;
    private TaxDao taxDao;

    @Autowired
    public ServiceLayerImpl(OrderDao orderDao, ProductDao productDao, TaxDao taxDao)
    {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
    }

    @Override
    public int getNextOrderNumber() {
        return 0;
    }

    @Override
    public Order addOrder(Order order) {
        return null;
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNo) {
        return null;
    }

    @Override
    public Order editOrder(LocalDate orderDate, int orderNo) {
        return null;
    }

    @Override
    public List<Order> getOrdersFromDate(LocalDate orderDate) {
        return List.of();
    }

    @Override
    public Order removeOrder(LocalDate orderDate, int orderNo) {
        return null;
    }

    @Override
    public List<Tax> getTaxes() {
        return List.of();
    }

    @Override
    public List<Product> getProducts() {
        return List.of();
    }
}
