package com.mthree.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class View {

    @Autowired
    private UserIO io;

    public void testRun()
    {
        io.display("This a test message which would work if spring is properly setup");
    }

    public void addOrder()
    {
        io.display("--Enter Order Details--");
        String customerName = io.readString("Enter the customer name:");
        String state = io.readString("Enter State:");
        String product = io.readString("Whatproduct are you buying?:");
        int sqft = io.readInt("What's the area in sqft (minimum of 100sqft)", 100);
    }
}
