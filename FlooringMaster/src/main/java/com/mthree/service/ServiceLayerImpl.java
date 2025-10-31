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
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceLayerImpl implements ServiceLayer{
    private final OrderDao orderDao;
    private final ProductDao productDao;
    private final TaxDao taxDao;

    @Autowired
    public ServiceLayerImpl(OrderDao orderDao, ProductDao productDao, TaxDao taxDao)
    {
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
    }

    //Gets the next order number for the creation of new orders
    @Override
    public int getNextOrderNumber() {
        return orderDao.getNextOrderNumber();
    }

    //Informs the dao to add a new order
    @Override
    public void addOrder(Order order) {
        orderDao.addOrder(order);
    }

    //Gets an order based on the day it was created and the order number
    @Override
    public Order getOrder(LocalDate orderDate, int orderNo) {
        return orderDao.getOrder(orderDate, orderNo);
    }

    //Gets the order to be edited based on date and order number
    @Override
    public Order editOrder(LocalDate orderDate, int orderNo) {
        return orderDao.editOrder(orderDate, orderNo);
    }

    //Gets all orders from a specific date
    @Override
    public List<Order> getOrdersFromDate(LocalDate orderDate) {
        return orderDao.getOrdersFromDate(orderDate);
    }

    //Gets all orders and returns it as a list
    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        orderDao.getAllOrders().values().forEach((m) -> orders.addAll(m.values()));
        return orders;
    }

    //Calls the dao to remove an order based on date and order number
    @Override
    public void removeOrder(LocalDate orderDate, int orderNo) {
        orderDao.removeOrder(orderDate, orderNo);
    }

    //Gets all the taxes from the dao
    @Override
    public List<Tax> getTaxes() {
        return taxDao.getAllTaxes();
    }

    //Gets all the product from the dao
    @Override
    public List<Product> getProducts() {
        return productDao.getAllProducts();
    }
}
