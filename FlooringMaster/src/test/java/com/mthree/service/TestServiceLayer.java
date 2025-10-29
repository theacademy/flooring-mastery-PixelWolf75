package com.mthree.service;

import com.mthree.controller.Controller;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TestServiceLayer {

    public TestServiceLayer()
    {
        //Setup appContext and scan for annotations
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.mthree");
        appContext.refresh();

        //Instantiate the controller using the beans from the app context
        ServiceLayer service = appContext.getBean("serviceLayer", ServiceLayer.class);
    }
}
