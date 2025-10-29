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

    public void run()
    {
        while(true) {
            int choice = view.displayMainMenuAndGetSelection();

            switch(choice) {
                case 1: //view all
                    List<Order> orders = service.getOrdersFromDate(LocalDate.now());
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
                    //Export
                    break;
                case 6:
                    exitMessage();
                    System.exit(0);
                    break;
                default: //unknown
                    view.displayError("Unknown Option");
                    break;
            }
        }
    }

    public int getMenuSelection()
    {
        return view.displayMainMenuAndGetSelection();
    }

    public void addOrder()
    {
        List<Tax> taxes = service.getTaxes();
        List<Product> products = service.getProducts();
        service.addOrder(view.getAddOrderInput(taxes, products));
    }

    public void editOrder()
    {
        LocalDate orderDate = view.DateInput();
        int orderNo = view.getOrderNumberInput();
        List<Tax> taxes = service.getTaxes();
        List<Product> products = service.getProducts();
        Order editedOrder = view.getOrderEditInput(service.editOrder(orderDate, orderNo), taxes, products);
        service.removeOrder(orderDate, orderNo);
        service.addOrder(editedOrder);
    }

    public void removeOrder()
    {
        LocalDate orderDate = view.DateInput();
        List<Order> orders = service.getOrdersFromDate(orderDate);
        int orderNo = view.getOrderNumberInput();

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

    public void exitMessage()
    {
        view.displayExit();
    }

}
