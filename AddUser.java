//package classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
//import interfaces.*;

public class AddUser implements ActionListener {
    private JFrame frame;
    private JTextField nameField, emailField, addressField, phoneField;
    private JPasswordField passwordField;
    private JComboBox<String> genderBox;
    private ManageUsers manageUsers;

    public AddUser(ManageUsers manageUsers) {
        this.manageUsers = manageUsers;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Add User");
        frame.setSize(600, 600);
        frame.setLayout(null);
        frame.getContentPane().setBackground(new Color(245, 245, 245)); // Light background color

        JLabel titleLabel = new JLabel("Add User");
        titleLabel.setBounds(250, 20, 150, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(60, 60, 60)); // Dark text color
        frame.add(titleLabel);

        // Name Label and Field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 80, 100, 30);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(nameLabel);
        
        nameField = new JTextField();
        nameField.setBounds(150, 80, 300, 30);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1));
        nameField.setBackground(Color.WHITE);
        frame.add(nameField);

        // Email Label and Field
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 120, 100, 30);
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(150, 120, 300, 30);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1));
        emailField.setBackground(Color.WHITE);
        frame.add(emailField);

        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 160, 100, 30);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 160, 300, 30);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1));
        passwordField.setBackground(Color.WHITE);
        frame.add(passwordField);

        // Gender Label and ComboBox
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 200, 100, 30);
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(genderLabel);

        String[] genders = {"Male", "Female", "Other"};
        genderBox = new JComboBox<>(genders);
        genderBox.setBounds(150, 200, 300, 30);
        genderBox.setFont(new Font("Arial", Font.PLAIN, 16));
        genderBox.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1));
        genderBox.setBackground(Color.WHITE);
        frame.add(genderBox);

        // Address Label and Field
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50, 240, 100, 30);
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(150, 240, 300, 30);
        addressField.setFont(new Font("Arial", Font.PLAIN, 16));
        addressField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1));
        addressField.setBackground(Color.WHITE);
        frame.add(addressField);

        // Phone Label and Field
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setBounds(50, 280, 100, 30);
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(150, 280, 300, 30);
        phoneField.setFont(new Font("Arial", Font.PLAIN, 16));
        phoneField.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150), 1));
        phoneField.setBackground(Color.WHITE);
        frame.add(phoneField);

        // Add Button
        JButton addButton = new JButton("Add");
        addButton.setBounds(150, 320, 100, 40);
        addButton.setFont(new Font("Arial", Font.BOLD, 16));
        addButton.setBackground(new Color(40, 167, 69)); // Green color
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createEmptyBorder());
        addButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        addButton.addActionListener(this);
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addButton.setBackground(new Color(33, 143, 58)); // Darker green
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addButton.setBackground(new Color(40, 167, 69)); // Green color
            }
        });
        frame.add(addButton);

        // Clear Button
        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(300, 320, 100, 40);
        clearButton.setFont(new Font("Arial", Font.BOLD, 16));
        clearButton.setBackground(new Color(255, 193, 7)); // Yellow color
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        clearButton.setBorder(BorderFactory.createEmptyBorder());
        clearButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        clearButton.addActionListener(this);
        clearButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clearButton.setBackground(new Color(255, 159, 0)); // Darker yellow
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                clearButton.setBackground(new Color(255, 193, 7)); // Yellow color
            }
        });
        frame.add(clearButton);

        // Back Button
        JButton backButton = new JButton("Back");
        backButton.setBounds(50, 370, 100, 40);
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(220, 53, 69)); // Red color
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createEmptyBorder());
        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backButton.addActionListener(e -> {
            frame.dispose();
            new ManageUsers(); // Go back to ManageUsers window
        });
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(185, 47, 58)); // Darker red
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(220, 53, 69)); // Red color
            }
        });
        frame.add(backButton);

        // Display the frame
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if ("Add".equals(button.getText())) {
                addUser();
            } else if ("Clear".equals(button.getText())) {
                clearFields();
            }
        }
    }

    private void addUser() {
        // Get the text fields' values
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String gender = (String) genderBox.getSelectedItem();
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();

        // Validation for empty fields
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields!");
            return;
        }

        // Check if email already exists
        if (isEmailExists(email)) {
            JOptionPane.showMessageDialog(frame, "User with this email already exists!");
            return;
        }

        // Create a new User object
        User newUser = new User(name, email, password, gender, address, phone);

        // Write the new user data to the file
        try (FileWriter writer = new FileWriter("userdata.txt", true)) {
            writer.write(newUser.getName() + "," + newUser.getEmail() + "," + newUser.getPassword() + "," +
                    newUser.getGender() + "," + newUser.getAddress() + "," + newUser.getPhone() + "\n");
            
            // Show success message and keep the window open
            JOptionPane.showMessageDialog(frame, "User added successfully!");

            // Optionally clear the fields after successful addition
            clearFields();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isEmailExists(String email) {
        // Check if the email already exists in the file
        try (BufferedReader reader = new BufferedReader(new FileReader("userdata.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip empty lines or lines that do not contain a comma
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                // Make sure the line has at least 2 elements (name, email)
                if (parts.length >= 2 && parts[1].equals(email)) {
                    return true;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private void clearFields() {
        // Clear all the input fields
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        genderBox.setSelectedIndex(0);
        addressField.setText("");
        phoneField.setText("");
    }
}
