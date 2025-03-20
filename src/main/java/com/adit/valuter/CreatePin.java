package com.adit.valuter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;

public class CreatePin {
    private JPanel superContainer;
    private JPanel fieldContainer;
    private JLabel lbl_pin;
    private JPasswordField pass_pin;
    private JButton btn_showhide1;
    private JPasswordField pass_confirm;
    private JButton btn_showhide2;
    private JLabel lbl_confirm;
    private JComboBox combo_ques1;
    private JLabel lbl_ques1;
    private JTextField txt_ans1;
    private JLabel lbl_ques2;
    private JTextField txt_ans2;
    private JComboBox combo_ques2;
    private JPanel buttonContainer;
    private JButton btn_submit;
    private boolean isUpdating = false;

    private final String[] questions = {
            "What is your pet's name?",
            "What is your mother's maiden name?",
            "What is your favorite movie?",
            "What was your childhood nickname?",
            "What is your favorite food?"
    };//Question Pool for JComboBox combo_ques1 and combo_ques2
    CreatePin(){
        pass_pin.setEchoChar('●');
        pass_confirm.setEchoChar('●');
        combo_ques1.setModel(new DefaultComboBoxModel<>(questions));
        combo_ques2.setModel(new DefaultComboBoxModel<>(questions));
        combo_ques1.setSelectedIndex(0);
        combo_ques2.setSelectedIndex(1);
        combo_ques1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateComboBoxes(combo_ques1,combo_ques2);
            }
        });
        combo_ques2.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                updateComboBoxes(combo_ques2,combo_ques1);
            }
        });
        btn_showhide1.addActionListener(e -> updateShowButton(btn_showhide1, pass_pin));
        btn_showhide2.addActionListener(e -> updateShowButton(btn_showhide2, pass_confirm));

        JFrame containerFrame = new JFrame("Vaulter - Create PIN");
        containerFrame.setContentPane(superContainer);
        containerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        containerFrame.pack();
        containerFrame.setLocationRelativeTo(null);
        containerFrame.setSize(450,500);
        containerFrame.setVisible(true);
    }
    private void updateComboBoxes(JComboBox<String> source, JComboBox<String> target) {
        if (isUpdating) return;  // Prevent recursion
        isUpdating = true;

        String selectedSource = (String) source.getSelectedItem();
        String selectedTarget = (String) target.getSelectedItem();

        // Reset target options excluding selected source
        ArrayList<String> availableQuestions = new ArrayList<>(Arrays.asList(questions));
        availableQuestions.remove(selectedSource);

        target.setModel(new DefaultComboBoxModel<>(availableQuestions.toArray(new String[0])));

        // If previous target selection is still valid, reselect it
        if (availableQuestions.contains(selectedTarget)) {
            target.setSelectedItem(selectedTarget);
        }

        isUpdating = false;
    }
    private void updateShowButton(JButton btn, JPasswordField pf){
        if(pf.getEchoChar() != (char)0){
            pf.setEchoChar((char)0);
            btn.setIcon(new ImageIcon("src/main/resources/hide_24X24.png"));
        }else{
            pf.setEchoChar('●');
            btn.setIcon(new ImageIcon("src/main/resources/show_24X24.png"));
        }
    }
}
