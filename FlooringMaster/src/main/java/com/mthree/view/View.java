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
}
