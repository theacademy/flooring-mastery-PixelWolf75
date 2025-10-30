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

                int orderNumber;
                String customerName;
                String state;
                BigDecimal taxRate;
                String productType;
                BigDecimal area;
                BigDecimal costPerSquareFoot;
                BigDecimal laborCostPerSquareFoot;
                BigDecimal materialCost;
                BigDecimal laborCost;
                BigDecimal tax;
                BigDecimal total;

                switch (orderValues[i])
                {
                    case "OrderNumber":
                        orderNumber = Integer.parseInt(orderValues[i]);
                        break;
                    case "CustomerName":
                        customerName = orderValues[i];
                        break;
                    case "State":
                        state = orderValues[i];
                        break;
                    case "TaxRate":
                        taxRate = new BigDecimal(orderValues[i]);
                        break;
                    case "ProductType":
                        productType = orderValues[i];
                        break;
                    case "Area":
                        area = new BigDecimal(orderValues[i]);
                        break;
                    case "CostPerSquareFoot":
                        costPerSquareFoot = new BigDecimal(orderValues[i]);
                        break;
                    case "LaborCostPerSquareFoot":
                        laborCostPerSquareFoot = new BigDecimal(orderValues[i]);
                        break;
                    case "MaterialCost":
                        materialCost = new BigDecimal(orderValues[i]);
                        break;
                    case "laborCost":
                        laborCost = new BigDecimal(orderValues[i]);
                        break;
                    case "Tax":
                        tax = new BigDecimal(orderValues[i]);
                        break;
                    case "Total":
                        total = new BigDecimal(orderValues[i]);
                        break;
                    case default:
                        break;
                }


                //orders.put(new Order(productValues[0], new BigDecimal(productValues[1]), new BigDecimal(productValues[2])));
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
