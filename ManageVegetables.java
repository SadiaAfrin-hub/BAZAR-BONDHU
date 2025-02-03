//package classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
//import interfaces.*;
public class ManageVegetables implements ActionListener, IManageVegetablesOperations {
    private JFrame frame;
    private Container container;

    public ManageVegetables() {
        frame = new JFrame("Manage Vegetables");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame on the screen
        frame.setLayout(new BorderLayout()); // Use BorderLayout for the main layout

        container = frame.getContentPane();
        container.setLayout(new GridBagLayout());
        container.setBackground(new Color(204, 255, 204)); // Light green background

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER; // Center-align components

        // Title Label
        JLabel titleLabel = new JLabel("Manage Vegetables", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        container.add(titleLabel, gbc);

        // Buttons for Vegetable operations
        String[] buttonLabels = {"Display Vegetables", "Add Vegetable", "Update Vegetable", "Search Vegetable", "Delete Vegetable"};
        int yPosition = 1;

        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 16));
            button.setBackground(new Color(255, 165, 0)); // Orange color for consistency
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setPreferredSize(new Dimension(200, 40));
            button.addActionListener(this);
            
            gbc.gridx = 0;
            gbc.gridy = yPosition++;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            container.add(button, gbc);
        }

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setBackground(new Color(220, 53, 69)); // Red color
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setPreferredSize(new Dimension(200, 40));
        backButton.addActionListener(e -> {
            frame.dispose();
            new AdminDashboard();
        });
        gbc.gridx = 0;
        gbc.gridy = yPosition++;
        container.add(backButton, gbc);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 16));
        logoutButton.setBackground(new Color(40, 167, 69)); // Green color
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setPreferredSize(new Dimension(200, 40));
        logoutButton.addActionListener(e -> {
            frame.dispose();
            new Login();
        });
        gbc.gridx = 1;
        gbc.gridy = yPosition++;
        container.add(logoutButton, gbc);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Display Vegetables":
                displayVegetables();
                break;
            case "Add Vegetable":
                new AddVegetable();
                break;
            case "Update Vegetable":
                updateVegetable();
                break;
            case "Search Vegetable":
                searchVegetable();
                break;
            case "Delete Vegetable":
                deleteVegetable();
                break;
        }
    }

    @Override
   public void displayVegetables() {
    File file = new File("vegetables.txt");
    
    if (!file.exists()) {
        JOptionPane.showMessageDialog(frame, "No Vegetables available!", "No Vegetables", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        StringBuilder VegetablesInfo = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 5) {
                String VegetableInfo = String.format("Name: %s\nID: %s\nPrice: $%.2f\nIn Stock: %s\nPhoto Path: %s\n\n",
                        parts[0], parts[1], Double.parseDouble(parts[2]), parts[3], parts[4]);
                VegetablesInfo.append(VegetableInfo);
            }
        }

        if (VegetablesInfo.length() == 0) {
            JOptionPane.showMessageDialog(frame, "No Vegetables data found in the file!", "Empty Data", JOptionPane.INFORMATION_MESSAGE);
        } else {
            // Create a JTextArea for displaying Vegetables' info
            JTextArea textArea = new JTextArea(VegetablesInfo.toString());
            textArea.setEditable(false);
            textArea.setFont(new Font("Arial", Font.PLAIN, 16)); // Improve readability
            textArea.setBackground(new Color(240, 240, 240));
            textArea.setForeground(new Color(33, 33, 33));
            textArea.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 2));

            // Add a scroll pane for better navigation
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(700, 500));

            // Display the Vegetables in a custom dialog
            JOptionPane.showMessageDialog(frame, scrollPane, "Available Vegetables", JOptionPane.PLAIN_MESSAGE);
        }
    } catch (IOException ex) {
        JOptionPane.showMessageDialog(frame, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(frame, "Invalid price format in the file.", "Data Error", JOptionPane.ERROR_MESSAGE);
    }
}


    @Override
    public void updateVegetable() {
        String id = JOptionPane.showInputDialog(frame, "Enter ID of the Vegetable to update:");
        if (id == null || id.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "ID cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        frame.dispose();
        new UpdateVegetable(id, this); // Consistent behavior with the current instance
    }

    @Override
public void searchVegetable() {
    String id = JOptionPane.showInputDialog(frame, "Enter the ID of the Vegetable to search:");
    if (id == null || id.trim().isEmpty()) {
        JOptionPane.showMessageDialog(frame, "ID cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try (BufferedReader reader = new BufferedReader(new FileReader("Vegetables.txt"))) {
        String line;
        StringBuilder VegetableInfo = new StringBuilder();
        boolean found = false;

        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 5 && parts[1].trim().equals(id.trim())) {
                VegetableInfo.append(String.format(
                        "Name: %s\nID: %s\nPrice: $%.2f\nIn Stock: %s\nPhoto Path: %s\n", 
                        parts[0].trim(), parts[1].trim(), Double.parseDouble(parts[2].trim()), 
                        parts[3].trim(), parts[4].trim()
                ));
                found = true;
                break;
            }
        }

        if (found) {
            showVegetableDetails(VegetableInfo.toString());
        } else {
            JOptionPane.showMessageDialog(frame, "Vegetable not found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (FileNotFoundException ex) {
        JOptionPane.showMessageDialog(frame, "Vegetable data file not found!", "Error", JOptionPane.ERROR_MESSAGE);
    } catch (IOException | NumberFormatException ex) {
        JOptionPane.showMessageDialog(frame, "Error reading file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}

private void showVegetableDetails(String VegetableDetails) {
    JTextArea textArea = new JTextArea(VegetableDetails);
    textArea.setEditable(false);
    textArea.setFont(new Font("Arial", Font.PLAIN, 16));
    textArea.setBackground(new Color(255, 250, 240)); // Light cream background
    textArea.setForeground(new Color(34, 139, 34)); // Forest green text color
    textArea.setBorder(BorderFactory.createLineBorder(new Color(0, 153, 76), 2)); // Green border

    JScrollPane scrollPane = new JScrollPane(textArea);
    scrollPane.setPreferredSize(new Dimension(600, 400));

    JOptionPane.showMessageDialog(frame, scrollPane, "Vegetable Details", JOptionPane.PLAIN_MESSAGE);
}

    @Override
    public void deleteVegetable() {
        String id = JOptionPane.showInputDialog(frame, "Enter ID of the Vegetable to delete:");
        if (id == null || id.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "ID cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            File file = new File("vegetables.txt");
            if (!file.exists()) {
                JOptionPane.showMessageDialog(frame, "No Vegetables available!", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder updatedData = new StringBuilder();
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5 && !parts[1].equals(id)) {
                    updatedData.append(line).append("\n");
                } else {
                    found = true;
                }
            }

            reader.close();

            if (found) {
                FileWriter writer = new FileWriter(file);
                writer.write(updatedData.toString());
                writer.close();
                JOptionPane.showMessageDialog(frame, "Vegetable deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "Vegetable not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error processing file: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
