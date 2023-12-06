/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package expense_tracker;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Sambit
 */
//The ExpenseIcomeTracker class extends JFrame to create the main application window.....
public class ExpenseIncomeTracker extends JFrame {
    
    private final ExpenseIncomeTableModel TableModel;
    private final JTable table;
    private final JTextField dateField;
    private final JTextField descriptionField;    
    private final JTextField amountField;
    private final JComboBox<String> typeCombobox;
    private final JButton addButton;
    private final JLabel balanceLabel;
    private double balance; // The current balance based on the added expenses and incomes....
    
    //Constructor to initialize the application and set up the form.....
    public ExpenseIncomeTracker (){ 
    
        try{        
            //Apply the FlatDarkLaf look and feel for a morden and flat appearance.....
           UIManager.setLookAndFeel(new FlatDarkLaf());
        }
        catch(Exception ex){
            System.err.println("Failed to set FlatDarkLaf LookAndFeel");
        }
        // Custom color schemes for specific swing Components.
        UIManager.put("TextField.foreground", Color.WHITE);
        UIManager.put("TextField.background", Color.DARK_GRAY);
        UIManager.put("TextField.CaretForeground", Color.RED);
        UIManager.put("ComboBox.foreground", Color.YELLOW);
        UIManager.put("ComboBox.selectionForeground", Color.WHITE);
        UIManager.put("ComboBox.selectionBackground", Color.BLACK);
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.background", Color.ORANGE);
        UIManager.put("Label.foreground", Color.WHITE);
        
        //Set the default font for the entire application
        Font customFont = new Font("Arial", Font.PLAIN, 18);
        UIManager.put("Label.font", customFont);
        UIManager.put("TextField.font", customFont);
        UIManager.put("ComboBox.font", customFont);
        UIManager.put("Button.font", customFont);


        //Initialize the table model and balance variable....
        balance = 0.0;
        TableModel= new ExpenseIncomeTableModel();
        
        //Create a JTable and set up a scroll pane to display the data... 
        table = new JTable(TableModel);
        JScrollPane scrollPane= new JScrollPane(table);
        table.setFillsViewportHeight(true);
        
        //Create input fields and components for adding new entries...
        dateField=new JTextField(10);
        descriptionField= new JTextField(20);
        amountField=new JTextField(10);
        typeCombobox= new JComboBox<>(new String[]{"Expense","Income"});
        
        //Attach an ActionListener to the "Add" button to handle new entry addition......
        addButton=new JButton("Add");
        addButton.addActionListener(e-> addEntry());
        balanceLabel= new JLabel("Balance: ₹"+ balance);
        
        //Create input panel to arrange input components.....
        JPanel inputPanel= new JPanel();
        inputPanel.add(new JLabel("Date"));
        inputPanel.add(dateField);
        
        inputPanel.add(new JLabel("Description"));
        inputPanel.add(descriptionField);
        
        inputPanel.add(new JLabel("Amount"));
        inputPanel.add(amountField);
        
        inputPanel.add(new JLabel("Type"));
        inputPanel.add(typeCombobox);
        
        inputPanel.add(addButton);
        
        //Create bottom panel to display the balance....
        JPanel bottomPanel= new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(balanceLabel);
        setLayout(new BorderLayout());
        
        //Set theb layout of the main frame add components to appropriate position....
        add(inputPanel,BorderLayout.NORTH);
        add(scrollPane,BorderLayout.CENTER);
        add(bottomPanel,BorderLayout.SOUTH);
        
        //Set the title, default close operation, and visibility of the main frame.
        setTitle("Expense And Incomes Tracker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        
        }
    //Method to handle adding new entries to the table....
    private void addEntry()
    {
        //Get input values from input fields.
        String date= dateField.getText();
        String description = descriptionField.getText();
        String amountStr = amountField.getText();
        String type = (String)typeCombobox.getSelectedItem();
        double amount;
        
        //validate input values
        //you can the description and date to the validation
        if(amountStr.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "Enter the Amount....","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try
        {
            amount= Double.parseDouble(amountStr);
        }
        catch(NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(this, "Invalid Amount Format","Error",JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        //Convert expenses to negative values
        if(type.equals("Expense"))
        {
            amount *= -1;
        }
        
        //Create a new entry and add it to the table.
        ExpenseIncomeEntry entry= new ExpenseIncomeEntry(date, description, amount, type);
        TableModel.addEntry(entry);
        
        //Update the balance and display the new balance..
        balance+= amount;
        balanceLabel.setText("Balance: ₹"+balance);
        
        //Clear input fields for the next entry.
        clearInputField();
        
    }
    
    //Method to clear input fields...
    private void clearInputField()
    {
        dateField.setText("");
        descriptionField.setText("");
        amountField.setText("");
        typeCombobox.setSelectedIndex(0);
    
    }
            
            
            
 



}
