//package classes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class UpdateVegetable {
    private JFrame frame;
    private JTextField nameField, idField, PriceField, instockField, photoPathField;
    private String id;
    private ManageVegetables manageVegetables;

    public UpdateVegetable(String id, ManageVegetables manageVegetables) {
        this.id = id;
        this.manageVegetables = manageVegetables;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Update Vegetable");
        frame.setSize(600, 700); // Updated frame size
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Increased padding for better spacing
        frame.setBackground(Color.LIGHT_GRAY); // Set background color for aesthetics

        // Create and style labels and fields
        JLabel nameLabel = createLabel("Name:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(nameLabel, gbc);

        nameField = createTextField();
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(nameField, gbc);

        JLabel idLabel = createLabel("ID:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(idLabel, gbc);

        idField = createTextField();
        idField.setEditable(false); // ID is not editable
        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(idField, gbc);

        JLabel priceLabel = createLabel("Price:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(priceLabel, gbc);

        PriceField = createTextField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        frame.add(PriceField, gbc);

        JLabel instockLabel = createLabel("In Stock:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(instockLabel, gbc);

        instockField = createTextField();
        gbc.gridx = 1;
        gbc.gridy = 3;
        frame.add(instockField, gbc);

        JLabel photoPathLabel = createLabel("Photo Path:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(photoPathLabel, gbc);

        photoPathField = createTextField();
        gbc.gridx = 1;
        gbc.gridy = 4;
        frame.add(photoPathField, gbc);

        // Create and style buttons
        JButton updateButton = createButton("Update");
        updateButton.addActionListener(e -> updateVegetable());
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(updateButton, gbc);

        JButton backButton = createButton("Back");
        backButton.addActionListener(e -> {
            frame.dispose();
            new ManageVegetables();
        });
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        frame.add(backButton, gbc);

        JButton logoutButton = createButton("Logout");
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new Login();
        });
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.EAST;
        frame.add(logoutButton, gbc);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        loadVegetableData();
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14)); // Set a bold font for labels
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14)); // Set a readable font
        textField.setBackground(Color.WHITE);
        textField.setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Stylish border
        return textField;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Set a bold font for buttons
        button.setBackground(new Color(0, 120, 215)); // Stylish button color
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 40)); // Larger button size
        return button;
    }

    private void loadVegetableData() {
        try {
            File file = new File("vegetables.txt");
            if (!file.exists()) {
                JOptionPane.showMessageDialog(frame, "No Vegetables registered yet!");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[1].equals(id)) {
                    nameField.setText(parts[0]);
                    idField.setText(parts[1]);
                    PriceField.setText(parts[2]);
                    instockField.setText(parts[3]);
                    photoPathField.setText(parts[4]);
                    break;
                }
            }
            reader.close();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error loading Vegetable data: " + ex.getMessage());
        }
    }

    private void updateVegetable() {
        String name = nameField.getText().trim();
        String id = idField.getText().trim();
        String priceStr = PriceField.getText().trim();
        String instockStr = instockField.getText().trim();
        String photoPath = photoPathField.getText().trim();

        if (name.isEmpty() || priceStr.isEmpty() || instockStr.isEmpty() || photoPath.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields!");
            return;
        }

        try {
            double price = Double.parseDouble(priceStr);
            int instock = Integer.parseInt(instockStr);

            File file = new File("vegetables.txt");
            if (!file.exists()) {
                JOptionPane.showMessageDialog(frame, "No Vegetables registered yet!");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder updatedData = new StringBuilder();
            String line;
            boolean updated = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[1].equals(id)) {
                    updatedData.append(String.join(",", name, id, String.valueOf(price), String.valueOf(instock), photoPath)).append("\n");
                    updated = true;
                } else {
                    updatedData.append(line).append("\n");
                }
            }
            reader.close();

            if (!updated) {
                JOptionPane.showMessageDialog(frame, "Vegetable not found!");
                return;
            }

            FileWriter writer = new FileWriter(file);
            writer.write(updatedData.toString());
            writer.close();

            JOptionPane.showMessageDialog(frame, "Vegetable information updated successfully!");
            frame.dispose();
            manageVegetables.displayVegetables(); // Refresh the Vegetable data in ManageVegetables
            new ManageVegetables();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input for price or instock!");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error updating Vegetable data: " + ex.getMessage());
        }
    }
}
