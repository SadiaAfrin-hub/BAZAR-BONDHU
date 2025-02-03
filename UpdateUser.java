//package classes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class UpdateUser {
    private JFrame frame;
    private JTextField nameField, emailField, addressField, phoneField;
    private JPasswordField passwordField;
    private String email;
    private ManageUsers manageUsers;

    public UpdateUser(String email, ManageUsers manageUsers) {
        this.email = email;
        this.manageUsers = manageUsers;
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Update User");
        frame.setSize(400, 350);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Label and field for Name
        JLabel nameLabel = createLabel("Name:");
        nameField = createTextField();
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(nameLabel, gbc);
        gbc.gridx = 1;
        frame.add(nameField, gbc);

        // Label and field for Email
        JLabel emailLabel = createLabel("Email:");
        emailField = createTextField();
        emailField.setEditable(false);  // Make email non-editable
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(emailLabel, gbc);
        gbc.gridx = 1;
        frame.add(emailField, gbc);

        // Label and field for Password
        JLabel passwordLabel = createLabel("Password:");
        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(passwordLabel, gbc);
        gbc.gridx = 1;
        frame.add(passwordField, gbc);

        // Label and field for Address
        JLabel addressLabel = createLabel("Address:");
        addressField = createTextField();
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(addressLabel, gbc);
        gbc.gridx = 1;
        frame.add(addressField, gbc);

        // Label and field for Phone
        JLabel phoneLabel = createLabel("Phone:");
        phoneField = createTextField();
        gbc.gridx = 0;
        gbc.gridy = 4;
        frame.add(phoneLabel, gbc);
        gbc.gridx = 1;
        frame.add(phoneField, gbc);

        // Update button
        JButton updateButton = createButton("Update", Color.BLUE, e -> updateUser());
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        frame.add(updateButton, gbc);

        // Back button
        JButton backButton = createButton("Back", Color.GRAY, e -> {
            frame.dispose(); // Close the UpdateUser window
            new ManageUsers(); // Open the ManageUsers window
        });
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;
        frame.add(backButton, gbc);

        // Logout button
        JButton logoutButton = createButton("Logout", Color.RED, e -> {
            frame.dispose();
            new Login(); // Assuming a Login class exists
        });
        gbc.gridx = 1;
        frame.add(logoutButton, gbc);

        // Set visibility and load user data
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        loadUserData();
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        return label;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField(20);
        textField.setFont(new Font("Arial", Font.PLAIN, 14));
        return textField;
    }

    private JButton createButton(String text, Color bgColor, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.addActionListener(actionListener);
        return button;
    }

    private void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader("userdata.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length < 2) continue;

                if (parts[1].equals(email)) {
                    nameField.setText(parts[0]);
                    emailField.setText(parts[1]);
                    passwordField.setText(parts[2]);
                    addressField.setText(parts[4]);
                    phoneField.setText(parts[5]);
                    break;
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error loading user data: " + ex.getMessage());
        }
    }

    private void updateUser() {
        String name = nameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        String address = addressField.getText().trim();
        String phone = phoneField.getText().trim();

        if (name.isEmpty() || password.isEmpty() || address.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields!");
            return;
        }

        try {
            File file = new File("userdata.txt");
            if (!file.exists()) {
                JOptionPane.showMessageDialog(frame, "No users registered yet!");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder updatedData = new StringBuilder();
            String line;
            boolean updated = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 2) continue;

                if (parts[1].equals(email)) {
                    updatedData.append(String.join(",", name, email, password, parts[3], address, phone)).append("\n");
                    updated = true;
                } else {
                    updatedData.append(line).append("\n");
                }
            }
            reader.close();

            if (!updated) {
                JOptionPane.showMessageDialog(frame, "User not found!");
                return;
            }

            try (FileWriter writer = new FileWriter(file)) {
                writer.write(updatedData.toString());
            }

            JOptionPane.showMessageDialog(frame, "User information updated successfully!");
            frame.dispose();
            manageUsers.displayUsers(); // Refresh the user data in ManageUsers
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error updating user data: " + ex.getMessage());
        }
    }
}
