package com.mthree.view;

import org.springframework.stereotype.Component;

@Component
public class UserIOConsoleImpl implements UserIO{
    @Override
    public void display(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        return "";
    }

    @Override
    public int readInt(String prompt) {
        return 0;
    }

    @Override
    public int readInt(String prompt, int min) {
        return 0;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        return 0;
    }
}
