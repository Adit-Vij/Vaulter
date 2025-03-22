package com.adit.valuter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Arrays;

public class CreatePin {
    //Form Elements
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
    private JLabel lbl_warn_pin;
    private JLabel lbl_warn_confirm;
    private JLabel lbl_warn_ans1;
    private JLabel lbl_warn_ans2;
    private boolean isUpdating = false;

    private final String[] questions = {
            "What is your pet's name?",
            "What is your mother's maiden name?",
            "What is your favorite movie?",
            "What was your childhood nickname?",
            "What is your favorite food?"
    };//Question Pool for JComboBox combo_ques1 and combo_ques2

    CreatePin(){
        //Hide lbl_warn_*
        lbl_warn_pin.setVisible(false);
        lbl_warn_confirm.setVisible(false);
        lbl_warn_ans1.setVisible(false);
        lbl_warn_ans2.setVisible(false);
        // Display '●' to Hide Actual PIN
        pass_pin.setEchoChar('●');
        pass_confirm.setEchoChar('●');

        //Add Question Pool to Combo Boxes as Options
        combo_ques1.setModel(new DefaultComboBoxModel<>(questions));
        combo_ques2.setModel(new DefaultComboBoxModel<>(questions));

        //Prevent Duplicate Questions in Combo Boxes
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
        btn_submit.setEnabled(false);

        //Implement PIN Show/Hide Logic
        btn_showhide1.addActionListener(e -> updateShowButton(btn_showhide1, pass_pin));
        btn_showhide2.addActionListener(e -> updateShowButton(btn_showhide2, pass_confirm));

        //Implement Submission Logic
        btn_submit.addActionListener(e -> getResponse(pass_pin, txt_ans1, txt_ans2));
        DocumentListener docListener = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { checkSubmitButton(); }
            public void removeUpdate(DocumentEvent e) { checkSubmitButton(); }
            public void changedUpdate(DocumentEvent e) { checkSubmitButton(); }
        };

        txt_ans1.getDocument().addDocumentListener(docListener);
        txt_ans2.getDocument().addDocumentListener(docListener);
        pass_pin.getDocument().addDocumentListener(docListener);
        pass_confirm.getDocument().addDocumentListener(docListener);

        JFrame containerFrame = new JFrame("Vaulter - Create PIN");
        containerFrame.setContentPane(superContainer);
        containerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        containerFrame.setLocationRelativeTo(null);
        //containerFrame.setSize();
        containerFrame.setVisible(true);
        Dimension d = new Dimension(450,500);
        containerFrame.setMinimumSize(d);
        containerFrame.pack();
    }

    //Define PIN Show/Hide Logic
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
    private void updateShowButton(JButton btn, JPasswordField pin){
        if(pin.getEchoChar() != (char)0){
            pin.setEchoChar((char)0);
            btn.setIcon(new ImageIcon("src/main/resources/hide_24X24.png"));
        }else{
            pin.setEchoChar('●');
            btn.setIcon(new ImageIcon("src/main/resources/show_24X24.png"));
        }
    }

    // Fetch User Response
    public String[] getResponse(JPasswordField pin, JTextField ans1, JTextField ans2){
        return new String[] {Arrays.toString(pin.getPassword()),(ans1.getText()+ans2.getText())};
    }
    private void checkSubmitButton(){
        boolean isFilled =
                pass_confirm.getPassword().length > 0 &&
                pass_pin.getPassword().length > 0 &&
                !txt_ans1.getText().isEmpty() &&
                !txt_ans2.getText().isEmpty();
        btn_submit.setEnabled(isFilled);
    }
    private void validateField(JPasswordField pin, JPasswordField confirm, JButton btn, JLabel warn){
        if(!Arrays.toString(pin.getPassword()).equals(Arrays.toString(confirm.getPassword()))){
            btn.setEnabled(false);
            warn.setVisible(true);
        }
        else{
            btn.setEnabled(true);
            warn.setVisible(false);
        }
    }
    private void validateField(JTextField txt, JButton btn, JLabel warn){
        if(txt.getText().isEmpty()){
            warn.setVisible(true);
            btn.setEnabled(false);
        }
        else{
            warn.setVisible(false);
            btn.setEnabled(true);
        }
    }
    private void validateField(JPasswordField pin, JButton btn, JLabel warn){
        if(!isPinValid(pin.getPassword())){
            warn.setVisible(true);
            btn.setEnabled(false);
        }
        else{
            warn.setVisible(false);
            btn.setEnabled(true);
        }
    }
    private boolean isPinValid(char[] pinChars) {
        for (char c : pinChars) {
            if (!Character.isDigit(c)) {
                return false;  // Found a non-numeric character
            }
        }
        return true;  // All characters are digits
    }

}