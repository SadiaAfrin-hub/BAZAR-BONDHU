//package classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
//import interfaces.*;

public class ManageUsers implements ActionListener {
    private JFrame frame;

    public ManageUsers() {
        // Initialize JFrame
        frame = new JFrame("Manage Users");
        frame.setSize(800, 700);
        frame.setLayout(new BorderLayout()); // Using BorderLayout for main frame layout
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null); // Center the frame on the screen

        // Use frame's content pane directly for layout
        Container container = frame.getContentPane();
        container.setLayout(new GridBagLayout());
        container.setBackground(new Color(245, 245, 245)); // Soft gray background for a professional look

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding around components
        gbc.anchor = GridBagConstraints.CENTER; // Center-align components

        // Title Label
        JLabel titleLabel = new JLabel("Manage Users", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Large, bold font for the title
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Center the title across two columns
        container.add(titleLabel, gbc);

        // Buttons for user operations
        String[] buttonLabels = {"Display Users", "Add User", "Update User", "Search User", "Delete User"};

        int yPosition = 1; // Start from the second row
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFont(new Font("Arial", Font.PLAIN, 16)); // Consistent font for buttons
            button.setBackground(new Color(0, 123, 255)); // Blue background for buttons
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false); // Remove focus outline
            button.setPreferredSize(new Dimension(200, 40)); // Set a consistent button size
            button.addActionListener(this); // Add action listener for button functionality

            gbc.gridx = 0;
            gbc.gridy = yPosition++;
            gbc.fill = GridBagConstraints.HORIZONTAL; // Stretch buttons to fill available width
            container.add(button, gbc);
        }

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.PLAIN, 16));
        backButton.setBackground(new Color(220, 53, 69)); // Red color for back button
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false); // Remove focus outline
        backButton.setPreferredSize(new Dimension(200, 40));
        backButton.addActionListener(e -> {
            frame.dispose(); // Close the current frame
            new AdminDashboard(); // Open the Admin Dashboard
        });
        gbc.gridx = 0;
        gbc.gridy = yPosition++;
        container.add(backButton, gbc);

        // Logout Button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.PLAIN, 16));
        logoutButton.setBackground(new Color(40, 167, 69)); // Green color for logout
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false); // Remove focus outline
        logoutButton.setPreferredSize(new Dimension(200, 40));
        logoutButton.addActionListener(e -> {
            frame.dispose(); // Close the current frame
            new Login(); // Open the Login screen
        });
        gbc.gridx = 1;
        gbc.gridy = yPosition++;
        container.add(logoutButton, gbc);

        // Set frame visibility
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();
        switch (action) {
            case "Display Users":
                displayUsers();
                break;
            case "Add User":
                frame.dispose();
                new AddUser(this);
                break;
            case "Update User":
                updateUserInfo();
                break;
            case "Search User":
                searchUser();
                break;
            case "Delete User":
                deleteUser();
                break;
        }
    }

    public void displayUsers() {
        try (BufferedReader reader = new BufferedReader(new FileReader("userdata.txt"))) {
            StringBuilder usersInfo = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String userInfo = String.format("Name: %s\nEmail: %s\nGender: %s\nAddress: %s\nPhone: %s\n\n", 
                            parts[0], parts[1], parts[3], parts[4], parts[5]);
                    usersInfo.append(userInfo);
                }
            }

            if (usersInfo.length() == 0) {
                JOptionPane.showMessageDialog(frame, "No users registered yet!", "No Users", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Create a JTextArea for displaying users' info
                JTextArea textArea = new JTextArea(usersInfo.toString());
                textArea.setEditable(false); // Make text area non-editable
                textArea.setFont(new Font("Arial", Font.PLAIN, 16)); // Larger font size for better readability
                textArea.setBackground(new Color(240, 240, 240)); // Light gray background for readability
                textArea.setForeground(new Color(33, 33, 33)); // Dark text color for contrast
                textArea.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 2)); // Blue border for style
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(700, 500)); // Make scrollable area bigger

                // Show custom dialog with the styled text area
                JOptionPane.showMessageDialog(frame, scrollPane, "Registered Users", JOptionPane.PLAIN_MESSAGE);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void updateUserInfo() {
        String email = JOptionPane.showInputDialog(frame, "Enter email of the user to update:");
        if (email == null || email.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Email cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        frame.dispose();
        new UpdateUser(email, this);
    }

    private void searchUser() {
        String email = JOptionPane.showInputDialog(frame, "Enter email of the user to search:");
        if (email == null || email.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Email cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("userdata.txt"))) {
            String line;
            StringBuilder userInfo = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[1].equals(email) && parts.length >= 6) {
                    userInfo.append(String.format("Name: %s\nEmail: %s\nGender: %s\nAddress: %s\nPhone: %s\n", 
                            parts[0], parts[1], parts[3], parts[4], parts[5]));
                    break;
                }
            }

            if (userInfo.length() > 0) {
                // Create a JTextArea for displaying the found user's info
                JTextArea textArea = new JTextArea(userInfo.toString());
                textArea.setEditable(false); // Make text area non-editable
                textArea.setFont(new Font("Arial", Font.PLAIN, 16)); // Larger font size for better readability
                textArea.setBackground(new Color(240, 240, 240)); // Light gray background for readability
                textArea.setForeground(new Color(33, 33, 33)); // Dark text color for contrast
                textArea.setBorder(BorderFactory.createLineBorder(new Color(0, 123, 255), 2)); // Blue border for style
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(600, 400)); // Make scrollable area bigger

                JOptionPane.showMessageDialog(frame, scrollPane, "User Details", JOptionPane.PLAIN_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void deleteUser() {
        String email = JOptionPane.showInputDialog(frame, "Enter email of the user to delete:");
        if (email == null || email.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Email cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("userdata.txt"))) {
            StringBuilder updatedData = new StringBuilder();
            String line;
            boolean userDeleted = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (!parts[1].equals(email)) {
                    updatedData.append(line).append("\n");
                } else {
                    userDeleted = true;
                }
            }

            if (userDeleted) {
                try (FileWriter writer = new FileWriter("userdata.txt")) {
                    writer.write(updatedData.toString());
                }
                JOptionPane.showMessageDialog(frame, "User deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, "User not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
