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

    //Loads all files from the order directory and deserialises the information to fit into the orders
    @Override
    public void loadFromFile() {

        final int NUMBER_OF_ORDER_VALUES = 12;
        Path dir = Paths.get(ORDER_FOLDER);
        try
        {
            //For each file in the orders folder
            Files.list(dir).filter(f -> f.toString().endsWith(".txt")).forEach(
                    orderPath -> {
                        Scanner sc = null;
                        try {
                            sc = new Scanner(new BufferedReader(new FileReader(orderPath.toFile())));
                            String orderFirstLine = sc.nextLine(); // move past category line
                            String[] categories = orderFirstLine.split(DELIMITER, NUMBER_OF_ORDER_VALUES);

                            LocalDate orderDate = getLocalDate(orderPath);

                            orders.put(orderDate, new HashMap<Integer, Order>());

                            //Scan each order line and deserialises it into new orders
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
                                            if (orderNumber > largestOrderNumber) largestOrderNumber = orderNumber; //Updates the largest order number
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

    //Gets the local date from the file path
    private LocalDate getLocalDate(Path orderPath) {
        int start = ORDER_FOLDER.length() + PREFIX.length(); // right after "Orders_"
        int end = orderPath.toString().length() - SUFFIX.length(); // before ".txt"

        // Extract substring date
        String fileDate = orderPath.toString().substring(start, end);
        int month = Integer.parseInt(fileDate.substring(0, 2));
        int day = Integer.parseInt(fileDate.substring(2, 4));
        int year = Integer.parseInt(fileDate.substring(4));
        return LocalDate.of(year, month, day);
    }

    //Returns largest order number to be used for order creation
    @Override
    public int getNextOrderNumber() {
        return this.largestOrderNumber;
    }

    //Adds a new order to the dao, increments the order number and writes it to the file
    @Override
    public Order addOrder(Order order) {
        orders.computeIfAbsent(order.getOrderDate(), k -> new HashMap<>());
        orders.get(order.getOrderDate()).put(order.getOrderNumber(), order);
        largestOrderNumber++;
        writeToFile();
        return order;
    }

    //Gets the order based on the date by loading the order folder if no orders exist then it returns a null
    @Override
    public Order getOrder(LocalDate orderDate, int orderNo) {
        loadFromFile();
        return orders.get(orderDate) == null ? null : orders.get(orderDate).get(orderNo);
    }

    //Gets the order based on the date by loading the order folder if no orders exist it returns a null
    @Override
    public Order editOrder(LocalDate orderDate, int orderNo) {
        loadFromFile();
        return orders.get(orderDate) == null ? null : orders.get(orderDate).get(orderNo);
    }

    //Gets all orders from a date
    @Override
    public List<Order> getOrdersFromDate(LocalDate orderDate) {
        loadFromFile();
        return orders.get(orderDate) == null ? null :  new ArrayList<>(orders.get(orderDate).values());
    }

    //Removes an order based on a date and order number
    @Override
    public Order removeOrder(LocalDate orderDate, int orderNo) {
        orders.get(orderDate).remove(orderNo);
        largestOrderNumber--;
        writeToFile();
        return null;
    }

    //Gets all orders mapped from date to the orders
    @Override
    public Map<LocalDate, Map<Integer, Order>> getAllOrders() {
        loadFromFile();
        return orders;
    }
}
