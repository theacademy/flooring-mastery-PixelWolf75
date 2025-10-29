package com.mthree.view;

public interface UserIO {
    void display(String message);
    String readString(String prompt);
    int readInt(String prompt);
    int readInt(String prompt, int min);
    int readInt(String prompt, int min, int max);
}
