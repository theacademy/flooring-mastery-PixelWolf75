package com.mthree.view;

import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class UserIOConsoleImpl implements UserIO{

    Scanner scan = new Scanner(System.in);

    //Output a message
    @Override
    public void display(String message) {
        System.out.println(message);
    }

    //Get a string input from user
    @Override
    public String readString(String prompt) {
        this.display(prompt);
        return scan.nextLine();
    }

    //Get an integer input asks for re-entry if no an integer
    @Override
    public int readInt(String prompt) {
        while(true) {
            String input = this.readString(prompt);
            try{
                return Integer.parseInt(input);
            }
            catch(NumberFormatException e)
            {
                this.display("The input isn't an integer please try again");
            }
        }
    }

    //Gets an integer input above a minimum inclusive
    @Override
    public int readInt(String prompt, int min) {
        while(true) {
            int input = this.readInt(prompt);
            if (input >= min)
            {
                return input;
            }
            else
            {
                this.display("The number needs to be at least " + min);
            }
        }
    }

    //Gets an integer input within a range
    @Override
    public int readInt(String prompt, int min, int max) {
        while (true)
        {
            int input = this.readInt(prompt, min);
            if (input <= max)
            {
                return input;
            }
            else
            {
                this.display("The number needs to be at most " + max);
            }
        }
    }
}
