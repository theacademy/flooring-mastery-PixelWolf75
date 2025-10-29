package com.mthree.dao;

import com.mthree.model.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoFileImpl implements OrderDao{

    final String ORDER_FOLDER = "Orders/";
    Map<LocalDate, Map<Integer, Order>> orders;
    int largestOrderNumber = 0;

    @Override
    public void writeToFile() {

    }

    @Override
    public void loadFromFile() {

    }

    @Override
    public int getNextOrderNumber() {
        return this.largestOrderNumber;
    }

    @Override
    public Order addOrder(Order order) {

        largestOrderNumber++;
        return null;
    }

    @Override
    public Order getOrder(Date orderDate, int orderNo) {
        return null;
    }

    @Override
    public Order editOrder(Date orderDate, int orderNo) {
        return null;
    }

    @Override
    public List<Order> getOrdersFromDate(LocalDate orderDate) {
        return List.of();
    }

    @Override
    public Order removeOrder(Date orderDate, int orderNo) {
        largestOrderNumber--;
        return null;
    }

    @Override
    public Map<LocalDate, Map<Integer, Order>> getAllOrders() {
        return Map.of();
    }
}
