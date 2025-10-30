package com.mthree.dao;

import com.mthree.exception.NoSuchOrderException;
import com.mthree.model.Order;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

@Component
public class OrderDaoFileImpl implements OrderDao{

    private static final String DELIMITER = ",";
    final String ORDER_FOLDER = "Orders/";
    final String PREFIX = "Orders_";
    final String SUFFIX = ".txt";
    Map<LocalDate, Map<Integer, Order>> orders = new HashMap<LocalDate, Map<Integer, Order>>();
    int largestOrderNumber = 0;

    @Override
    public void writeToFile() {
        try{
            for(LocalDate orderDate : orders.keySet())
            {
                String month = String.valueOf(orderDate.getMonthValue());
                month = month.length() != 2 ? "0" + month : month;
                String day = String.valueOf(orderDate.getDayOfMonth());
                day = day.length() != 2 ? "0" + day : day;
                String year = String.valueOf(orderDate.getYear());

                String fileName = ORDER_FOLDER + PREFIX + month + day + year + SUFFIX;
                PrintWriter writer = new PrintWriter(new FileWriter(fileName));

                writer.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");

                for (Order order : orders.get(orderDate).values())
                {
                    writer.println(order.toString());
                }
                writer.flush();
                writer.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void loadFromFile() {

        final int NUMBER_OF_ORDER_VALUES = 12;
        Path dir = Paths.get(ORDER_FOLDER);
        try
        {
            Files.list(dir).filter(f -> f.toString().endsWith(".txt")).forEach(
                    orderPath -> {
                        Scanner sc = null;
                        try {
                            sc = new Scanner(new BufferedReader(new FileReader(orderPath.toFile())));
                            String orderFirstLine = sc.nextLine(); // move past category line
                            String[] categories = orderFirstLine.split(DELIMITER, NUMBER_OF_ORDER_VALUES);

                            // Find start and end positions
                            int start = ORDER_FOLDER.length() + PREFIX.length(); // right after "Orders_"
                            int end = orderPath.toString().length() - SUFFIX.length(); // before ".txt"

                            // Extract substring date
                            String fileDate = orderPath.toString().substring(start, end);
                            int month = Integer.parseInt(fileDate.substring(0, 2));
                            int day = Integer.parseInt(fileDate.substring(2, 4));
                            int year = Integer.parseInt(fileDate.substring(4));
                            LocalDate orderDate = LocalDate.of(year, month, day);

                            orders.put(orderDate, new HashMap<Integer, Order>());

                            while(sc.hasNext()) {
                                String orderEntry = sc.nextLine();
                                String[] orderValues = new String[12];
                                orderValues = orderEntry.split(DELIMITER, NUMBER_OF_ORDER_VALUES);

                                int orderNumber = 0;
                                String customerName = "";
                                String state = "";
                                BigDecimal taxRate = null;
                                String productType = "";
                                BigDecimal area = null;
                                BigDecimal costPerSquareFoot = null;
                                BigDecimal laborCostPerSquareFoot = null;
                                BigDecimal materialCost = null;
                                BigDecimal laborCost = null;
                                BigDecimal tax = null;
                                BigDecimal total = null;

                                for (int i = 0; i < NUMBER_OF_ORDER_VALUES; i++) {
                                    switch (categories[i]) {
                                        case "OrderNumber":
                                            orderNumber = Integer.parseInt(orderValues[i]);
                                            if (orderNumber > largestOrderNumber) largestOrderNumber = orderNumber;
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
                                        default:
                                            break;
                                    }
                                }
                                orders.get(orderDate).put(orderNumber, new Order(orderNumber, customerName, state, orderDate, taxRate, productType, area, costPerSquareFoot, laborCostPerSquareFoot));
                            }
                        } catch (FileNotFoundException e) {
                            throw new NoSuchOrderException("Can't find an order");
                        }
                    }
            );

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
        orders.computeIfAbsent(order.getOrderDate(), k -> new HashMap<>());
        orders.get(order.getOrderDate()).put(order.getOrderNumber(), order);
        largestOrderNumber++;
        writeToFile();
        return order;
    }

    @Override
    public Order getOrder(LocalDate orderDate, int orderNo) {
        loadFromFile();
        return orders.get(orderDate).get(orderNo);
    }

    @Override
    public Order editOrder(LocalDate orderDate, int orderNo) {
        loadFromFile();
        return orders.get(orderDate).get(orderNo);
    }

    @Override
    public List<Order> getOrdersFromDate(LocalDate orderDate) {
        loadFromFile();
        return orders.get(orderDate) == null ? null : (List<Order>) orders.get(orderDate).values();
    }

    @Override
    public Order removeOrder(LocalDate orderDate, int orderNo) {
        orders.get(orderDate).remove(orderNo);
        largestOrderNumber--;
        writeToFile();
        return null;
    }

    @Override
    public Map<LocalDate, Map<Integer, Order>> getAllOrders() {
        loadFromFile();
        return orders;
    }
}
