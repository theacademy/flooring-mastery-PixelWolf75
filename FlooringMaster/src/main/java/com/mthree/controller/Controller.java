package com.mthree.controller;

import com.mthree.service.ServiceLayer;
import com.mthree.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        view.testRun();
    }
}
