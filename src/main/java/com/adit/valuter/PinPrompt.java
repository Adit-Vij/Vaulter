package com.adit.valuter;

import javax.swing.*;
public class PinPrompt {
    private JPanel superContainer;
    private JPanel fieldContainer;
    private JLabel lbl_pin;
    private JPasswordField passwordField1;
    private JButton btn_submit;
    private JPanel buttonContainer;
    private JButton btn_showhide;

    PinPrompt(){
        JFrame containerFrame = new JFrame("Vaulter - Enter PIN");
        containerFrame.setContentPane(superContainer);
        containerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        containerFrame.pack();
        containerFrame.setLocationRelativeTo(null);
        containerFrame.setSize(400,200);
        containerFrame.setVisible(true);
    }
}
