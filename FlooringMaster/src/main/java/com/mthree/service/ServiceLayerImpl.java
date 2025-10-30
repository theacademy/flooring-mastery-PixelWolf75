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
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
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
        return orderDao.getNextOrderNumber();
    }

    @Override
    public Order addOrder(Order order) {
        return orderDao.addOrder(order);
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNo) {
        return orderDao.getOrder(orderDate, orderNo);
    }

    @Override
    public Order editOrder(LocalDate orderDate, int orderNo) {
        return orderDao.editOrder(orderDate, orderNo);
    }

    @Override
    public List<Order> getOrdersFromDate(LocalDate orderDate) {
        return orderDao.getOrdersFromDate(orderDate);
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        orderDao.getAllOrders().values().forEach((m) -> orders.addAll(m.values()));
        return orders;
    }

    @Override
    public Order removeOrder(LocalDate orderDate, int orderNo) {
        return orderDao.removeOrder(orderDate, orderNo);
    }

    @Override
    public List<Tax> getTaxes() {
        return taxDao.getAllTaxes();
    }

    @Override
    public List<Product> getProducts() {
        return productDao.getAllProducts();
    }
}
