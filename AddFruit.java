//package classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
//import interfaces.*;

public class AddFruit implements ActionListener {
    private JFrame frame;
    private JTextField nameField, idField, priceField, instockField, photoPathField;
    
    // Declare buttons as class members
    private JButton addButton, clearButton, backButton;

    public AddFruit() {
        // Initialize frame with modern settings
        frame = new JFrame("Add Fruit");
        frame.setSize(600, 500);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(245, 245, 245));

        // Title Label
        JLabel titleLabel = new JLabel("Add Fruit", JLabel.CENTER);
        titleLabel.setBounds(0, 20, 600, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(54, 69, 79));
        frame.add(titleLabel);

        // Create labels and fields for each input
        createLabel("Name:", 60);
        nameField = createTextField(60);

        createLabel("ID:", 100);
        idField = createTextField(100);

        createLabel("Price (BDT):", 140);  // Changed label to reflect BDT
        priceField = createTextField(140);

        createLabel("In Stock (KG):", 180);  // Changed label to reflect KG
        instockField = createTextField(180);

        createLabel("Photo Path:", 220);
        photoPathField = createTextField(220);

        // Create and add buttons
        addButton = createButton("Add", 280);
        clearButton = createButton("Clear", 280);  // Both "Add" and "Clear" will be in the same row
        backButton = createButton("Back", 320);  // "Back" will be placed in the row below

        // Add buttons to frame
        frame.add(addButton);
        frame.add(clearButton);
        frame.add(backButton);

        // Align buttons
        alignButtons();

        // Set up the frame visibility and close operation
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    private void createLabel(String text, int yPosition) {
        JLabel label = new JLabel(text);
        label.setBounds(50, yPosition, 120, 30);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(new Color(54, 69, 79));
        frame.add(label);
    }

    private JTextField createTextField(int yPosition) {
        JTextField textField = new JTextField();
        textField.setBounds(180, yPosition, 300, 30);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        frame.add(textField);
        return textField;
    }

    private JButton createButton(String text, int yPosition) {
        JButton button = new JButton(text);
        button.setBounds(150, yPosition, 100, 40);  // Set button width to 100
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setBackground(new Color(53, 121, 249));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);
        return button;
    }

    // Method to align buttons with gap
    private void alignButtons() {
        int gap = 20; // Space between buttons
        int buttonWidth = 100;
        int buttonHeight = 40;

        // Add button and clear button are on the same row
        addButton.setBounds(150, 280, buttonWidth, buttonHeight);
        clearButton.setBounds(150 + buttonWidth + gap, 280, buttonWidth, buttonHeight);  // Clear button next to Add button

        // Back button is placed under Add and Clear buttons
        backButton.setBounds(150 + (buttonWidth + gap) * 2, 280, buttonWidth, buttonHeight);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if ("Add".equals(button.getText())) {
                addFruit();
            } else if ("Clear".equals(button.getText())) {
                clearFields();
            } else if ("Back".equals(button.getText())) {
                frame.dispose();  // Close the frame on back button press
            }
        }
    }

    private void addFruit() {
        String name = nameField.getText().trim();
        String id = idField.getText().trim();
        String priceStr = priceField.getText().trim();
        String instockStr = instockField.getText().trim();
        String photoPath = photoPathField.getText().trim();

        // Validate inputs
        if (name.isEmpty() || id.isEmpty() || priceStr.isEmpty() || instockStr.isEmpty() || photoPath.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            int instock = Integer.parseInt(instockStr);

            if (isIdExists(id)) {
                JOptionPane.showMessageDialog(frame, "Fruit with the same ID already exists!", "ID Error", JOptionPane.ERROR_MESSAGE);
                clearFields();
                return;
            }

            // Save fruit data to file
            FileWriter writer = new FileWriter("fruits.txt", true);
            writer.write(name + "," + id + "," + price + "," + instock + "," + photoPath + "\n");
            writer.close();

            JOptionPane.showMessageDialog(frame, "Fruit added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            frame.dispose();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid price or instock format! Please enter valid numbers.", "Format Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error saving the fruit: " + ex.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields() {
        nameField.setText("");
        idField.setText("");
        priceField.setText("");
        instockField.setText("");
        photoPathField.setText("");
    }

    private boolean isIdExists(String id) {
        try {
            File file = new File("fruits.txt");
            if (!file.exists()) {
                return false;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 1 && parts[1].equals(id)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }


}
