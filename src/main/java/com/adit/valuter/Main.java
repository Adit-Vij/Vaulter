package com.adit.valuter;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try{
            UIManager.setLookAndFeel(new FlatDarkLaf());
        }catch(Exception e){
            System.err.println("Failed to initialize LaF!");
        }
        //PinPrompt ui1 = new PinPrompt();
        CreatePin ui = new CreatePin();
    }
}