package com.mthree.service;

import com.mthree.model.Order;
import com.mthree.model.Product;
import com.mthree.model.Tax;

import java.time.LocalDate;
import java.util.List;

public interface ServiceLayer {
    int getNextOrderNumber();
    void addOrder(Order order);
    Order getOrder(LocalDate orderDate, int orderNo);
    Order editOrder(LocalDate orderDate, int orderNo);
    List<Order> getOrdersFromDate(LocalDate orderDate);
    List<Order> getAllOrders();
    void removeOrder(LocalDate orderDate, int orderNo);
    List<Tax> getTaxes();
    List<Product> getProducts();
}
