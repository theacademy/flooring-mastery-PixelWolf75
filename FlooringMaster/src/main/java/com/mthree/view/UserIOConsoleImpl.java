package com.mthree.view;

import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class UserIOConsoleImpl implements UserIO{

    Scanner scan = new Scanner(System.in);

    @Override
    public void display(String message) {
        System.out.println(message);
    }

    @Override
    public String readString(String prompt) {
        this.display(prompt);
        String result = scan.nextline();
        return result;
    }

    @Override
    public int readInt(String prompt) {
        do {
            String input = this.readString(prompt);
            try{
                return Integer.parse(input);
            }
            catch(NumberFormatException e)
            {
                this.display("The input isn't an integer please try again");
            }
        } while(true);

        return -1;
    }

    @Override
    public int readInt(String prompt, int min) {
        do {
            int input = this.readInt(prompt);
            if (input >= min)
            {
                return input;
            }
            else
            {
                this.display("The number needs to be at least " + min);
            }
        } while(true)

        return -1;
    }

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

        return -1;
    }
}
