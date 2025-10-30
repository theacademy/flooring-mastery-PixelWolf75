package com.mthree.controller;

import com.mthree.model.Order;
import com.mthree.model.Product;
import com.mthree.model.Tax;
import com.mthree.service.ServiceLayer;
import com.mthree.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class Controller {
    private View view;
    private ServiceLayer service;

    @Autowired
    public Controller(View view, ServiceLayer service)
    {
        this.view = view;
        this.service = service;
    }

    //Run the project allows the users to choose what function to do
    public void run()
    {
        while(true) {
            int choice = view.displayMainMenuAndGetSelection();

            switch(choice) {
                case 1: //view all
                    List<Order> orders = service.getAllOrders();
                    view.displayAllOrders(orders);
                    break;
                case 2:
                    addOrder();
                    break;
                case 3:
                    editOrder();
                    break;
                case 4:
                    removeOrder();
                    break;
                case 5:
                    view.displayError("Export hasn't been implemented");
                    break;
                case 6:
                    exitMessage();
                    break;
                default: //unknown
                    view.displayError("Unknown Option");
                    break;
            }
        }
    }

    //Runs the service and view to add an order
    public void addOrder()
    {
        List<Tax> taxes = service.getTaxes();
        List<Product> products = service.getProducts();
        service.addOrder(view.getAddOrderInput(taxes, products, service.getNextOrderNumber()));
    }

    //Runs the service and view to edit an existing order
    public void editOrder()
    {
        LocalDate orderDate = view.DateInput();
        int orderNo = view.getOrderNumberInput();
        List<Tax> taxes = service.getTaxes();
        List<Product> products = service.getProducts();
        Order orderToEdit = service.editOrder(orderDate, orderNo);
        if (orderToEdit == null)
        {
            view.displayError("No Order found");
            return;
        }
        Order editedOrder = view.getOrderEditInput(orderToEdit, taxes, products);
        service.removeOrder(orderDate, orderNo);
        service.addOrder(editedOrder);
    }

    //Runs the service and view to remove an existing order
    public void removeOrder()
    {
        LocalDate orderDate = view.DateInput();
        List<Order> orders = service.getOrdersFromDate(orderDate);
        int orderNo = view.getOrderNumberInput();

        if (orders == null)
        {
            view.removeOrderInputFailure();
            return;
        }

        Order removedOrder = orders.stream().filter((o) -> o.getOrderNumber() == orderNo).findFirst().orElse(null);

        if (removedOrder == null)
        {
            view.removeOrderInputFailure();
        }
        else
        {
            view.removeOrderInputSuccess();
            service.removeOrder(orderDate, orderNo);
        }
    }

    //Runs view to display exit message then exit the system
    public void exitMessage()
    {
        view.displayExit();
        System.exit(0);
    }

}
