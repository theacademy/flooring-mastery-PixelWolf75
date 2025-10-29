package com.mthree.view;

import com.mthree.model.Order;
import com.mthree.model.Product;
import com.mthree.model.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class View {

    @Autowired
    private UserIO io;

    private int orderNo = 0;

    public void testRun() {
        io.display("This a test message which would work if spring is properly setup");
    }

    public int displayMainMenuAndGetSelection() {
        io.display("");
        io.display("Main Menu");
        io.display("1. View Orders");
        io.display("2. Add Book");
        io.display("3. Update Book");
        io.display("4. Delete Book");
        io.display("5. Export data");
        io.display("6. Exit");

        return io.readInt("Please select an option:", 1, 6);
    }

    public void displayError(String error) {
        io.display("ERROR: " + error);
    }

    public void displayAllOrders(List<Order> orders) {
        io.display("");
        io.display("All Orders");
        orders.forEach(this::displayOrderDetails);
    }

    private void displayOrderDetails(Order order) {
        io.display("");
        io.display("OrderNo: " + order.getOrderNumber());
        io.display("CustomerName: " + order.getCustomerName());
        io.display("State: " + order.getState());
        io.display("OrderDate: " + order.getOrderDate());
        io.display("ProductType: " + order.getProductType());
    }

    public int getOrderNumberInput() {
        return io.readInt("Enter an order number between 0 and " + (orderNo - 1) + ":", 0, orderNo - 1);
    }

    private LocalDate DateCreationInput() {
        LocalDate present = LocalDate.now();
        int year;
        int month;
        int day;
        LocalDate orderDate;
        while (true) {
            year = io.readInt("Enter the year of the order, has to be in the future no the past", present.getYear());
            month = io.readInt("Enter the month as a number it has to be in the future", 1, 12);
            day = io.readInt("Enter the day as a number it has to be in the future", 1, month % 2 == 0 ? 30 : 31);
            orderDate = LocalDate.of(year, month, day);

            if (present.isAfter(orderDate)) {
                io.display("This date is in the past so its invalid");
            } else {
                break;
            }
        }

        return orderDate;
    }

    public LocalDate DateInput() {
        int year;
        int month;
        int day;
        LocalDate orderDate;
        year = io.readInt("Enter the year of the order, has to be an existing year that an order was made", 0);
        month = io.readInt("Enter the month as a number it has to be in the future", 1, 12);
        day = io.readInt("Enter the day as a number it has to be in the future", 1, month % 2 == 0 ? 30 : 31);
        orderDate = LocalDate.of(year, month, day);

        return orderDate;
    }


    public Order getAddOrderInput(List<Tax> taxes, List<Product> products) {
        io.display("--Enter Order Details--");
        String customerName = io.readString("Enter the customer name:");

        String state;
        Tax tax;
        while (true) {
            for (Tax t : taxes) {
                io.display(t.toString());
            }

            state = io.readString("Enter State from the following states:");
            String finalState = state;
            tax = taxes.stream().filter((t) -> t.getState().equals(finalState) || t.getStateAbr().equals(finalState)).findAny().orElse(null);

            if (tax == null) {
                io.display("Not a valid state nor abbreviation");
            } else {
                break;
            }
        }

        LocalDate orderDate = DateCreationInput();

        String productType;
        Product product;

        while (true) {
            for (Product p : products) {
                io.display(p.toString());
            }

            productType = io.readString("What product are you buying?:");
            String finalProductType = productType;
            product = products.stream().filter((p) -> p.getProductType().equals(finalProductType)).findAny().orElse(null);

            if (product == null) {
                io.display("Not a valid product type");
            } else {
                break;
            }
        }
        BigDecimal area = new BigDecimal(io.readInt("What's the area in sqft (minimum of 100sqft)", 100));

        return new Order(orderNo++, customerName, tax.getStateAbr(), orderDate, tax.getTaxRate(), product.getProductType(), area, product.getCostPerSquareFoot(), product.getLaborCostPerSquareFoot());
    }

    public Order getOrderEditInput(Order order, List<Tax> taxes, List<Product> products)
    {
        io.display("--Enter New Order Details--");
        io.display("Press enter if you wish to leave the field unchanged");
        String customerName = io.readString("Enter the customer name (" + order.getCustomerName() + "):");
        customerName = customerName.isEmpty() ? order.getCustomerName() : customerName;

        String state;
        Tax tax;
        while (true) {
            for (Tax t : taxes) {
                io.display(t.toString());
            }

            state = io.readString("Enter State from the following states (" + order.getState() + "):");

            if (state.isEmpty())
            {
                state = order.getState();
            }

            String finalState = state;

            tax = taxes.stream().filter((t) -> t.getState().equals(finalState) || t.getStateAbr().equals(finalState)).findAny().orElse(null);

            if (tax == null) {
                io.display("Not a valid state nor abbreviation");
            } else {
                break;
            }
        }

        String enterNewDate = io.readString("About to enter a new date say yes to proceed else press enter");
        LocalDate orderDate = enterNewDate.equals("yes") ? DateCreationInput() : order.getOrderDate();

        String productType;
        Product product;

        while (true) {
            for (Product p : products) {
                io.display(p.toString());
            }

            productType = io.readString("What product are you buying? (" + order.getProductType() + "):");

            if (productType.isEmpty())
            {
                productType = order.getState();
            }

            String finalProductType = productType;

            product = products.stream().filter((p) -> p.getProductType().equals(finalProductType)).findAny().orElse(null);

            if (product == null) {
                io.display("Not a valid product type");
            } else {
                break;
            }
        }

        int areaInput = io.readInt("What's the area in sqft (minimum of 100sqft), enter anything less to keep the same value (" + order.getArea() + ")");
        BigDecimal area = areaInput < 100 ? order.getArea() : new BigDecimal(areaInput);

        order.setCustomerName(customerName);
        order.setState(tax.getStateAbr());
        order.setOrderDate(orderDate);
        order.setTaxRate(tax.getTaxRate());
        order.setProductType(product.getProductType());
        order.setArea(area);
        order.setCostPerSquareFoot(product.getCostPerSquareFoot());
        order.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot());

        io.display("Order has been edited");
        return order;
    }

    public void removeOrderInputSuccess() {
        io.display("Removed an Order");
        orderNo--;
    }

    public void removeOrderInputFailure() {
        io.display("Couldn't remove Order");
    }

    public void displayExit() {
        io.display("Existing Floor Master");
    }

}
