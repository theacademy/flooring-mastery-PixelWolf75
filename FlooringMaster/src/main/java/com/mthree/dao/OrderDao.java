package com.mthree.dao;

import com.mthree.model.Order;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderDao {
    void writeToFile();
    void loadFromFile();
    int getNextOrderNumber();
    Order addOrder(Order order);
    Order getOrder(Date orderDate, int orderNo);
    Order editOrder(Date orderDate, int orderNo);
    List<Order> getOrdersFromDate(LocalDate orderDate);
    Order removeOrder(Date orderDate, int orderNo);
    Map<LocalDate, Map<Integer, Order>> getAllOrders();
}
