package com.mthree.dao;

import com.mthree.model.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

@Component
public class OrderDaoFileImpl implements OrderDao{

    final String ORDER_FOLDER = "Orders/";
    Map<LocalDate, Map<Integer, Order>> orders;
    int largestOrderNumber = 0;

    @Override
    public void writeToFile() {
        String fileName = "";
        try{
            PrintWriter writer = new PrintWriter(new FileWriter(ORDER_FOLDER + fileName));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadFromFile() {
        String fileName = "";
        final int NUMBER_OF_ORDER_VALUES = 12;
        try
        {
            Scanner sc = new Scanner(new BufferedReader(new FileReader(ORDER_FOLDER + fileName)));
            String orderFirstLine = sc.nextLine(); // move past category line
            String[] categories = orderFirstLine.split(DELIMITER, NUMBER_OF_ORDER_VALUES);
            while(sc.hasNext())
            {
                String orderEntry = sc.nextLine();
                String[] orderValues = new String[12];
                orderValues = productEntry.split(DELIMITER, NUMBER_OF_ORDER_VALUES);

                switch (orderValues[i])
                {
                    case "OrderNumber":
                        break;
                    case "CustomerName":
                        break;
                    case "State":
                        break;
                    case "TaxRate":
                        break;
                    case "ProductType":
                        break;
                    case "Area":
                        break;
                    case "CostPerSquareFoot":
                        break;
                    case "LaborCostPerSquareFoot":
                        break;
                    case "MaterialCost":
                        break;
                    case "laborCost":
                        break;
                    case "Tax":
                        break;
                    case "Total":
                        break;
                    case default:
                        break;
                }


                allProducts.add(new Product(productValues[0], new BigDecimal(productValues[1]), new BigDecimal(productValues[2])));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
        return orders.get(orderDate).get(orderNo);
    }

    @Override
    public Order editOrder(Date orderDate, int orderNo) {
        return orders.get(orderDate).get(orderNo);
    }

    @Override
    public List<Order> getOrdersFromDate(LocalDate orderDate) {
        return (List<Order>) orders.get(orderDate).values();
    }

    @Override
    public Order removeOrder(Date orderDate, int orderNo) {
        largestOrderNumber--;
        return null;
    }

    @Override
    public Map<LocalDate, Map<Integer, Order>> getAllOrders() {
        return orders;
    }
}
