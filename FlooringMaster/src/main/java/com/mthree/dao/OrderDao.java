package com.mthree.dao;

import com.mthree.model.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderDao {
    void writeToFile();
    void loadFromFile();
    int getNextOrderNumber();
    void addOrder(Order order);
    Order getOrder(LocalDate orderDate, int orderNo);
    Order editOrder(LocalDate orderDate, int orderNo);
    List<Order> getOrdersFromDate(LocalDate orderDate);
    void removeOrder(LocalDate orderDate, int orderNo);
    Map<LocalDate, Map<Integer, Order>> getAllOrders();
}
