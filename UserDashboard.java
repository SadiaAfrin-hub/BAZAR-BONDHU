//package classes;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserDashboard implements ActionListener {
    private JFrame frame;
    private User user;


   public UserDashboard(User user) {
    this.user = user;

    frame = new JFrame("User Dashboard");
    frame.setSize(800, 600);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.getContentPane().setBackground(new Color(248, 248, 248)); 

   
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); 
    buttonPanel.setBackground(new Color(248, 248, 248)); 

    
    JButton orderButton = createProfessionalButton("Order", new Color(34, 167, 240));
    orderButton.addActionListener(e -> {
        frame.dispose(); 
        new OrderDashboard(user); 
    });

    JButton viewProfileButton = createProfessionalButton("View Profile", new Color(78, 203, 196));
    viewProfileButton.addActionListener(this);

    JButton updateProfileButton = createProfessionalButton("Update Profile", new Color(76, 175, 80));
    updateProfileButton.addActionListener(this);

    JButton logoutButton = createProfessionalButton("Logout", new Color(255, 76, 57));
    logoutButton.addActionListener(this);

   
    buttonPanel.add(orderButton);
    buttonPanel.add(viewProfileButton);
    buttonPanel.add(updateProfileButton);
    buttonPanel.add(logoutButton);

    
    JLabel welcomeLabel = new JLabel("WELCOME TO BAZAR BONDHU");
    welcomeLabel.setFont(new Font("Helvetica Neue", Font.BOLD, 26)); // Modern font
    welcomeLabel.setForeground(new Color(0, 51, 102)); // Professional dark blue color
    welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
    frame.add(welcomeLabel, BorderLayout.NORTH);

    
    frame.add(buttonPanel, BorderLayout.CENTER);

    
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
}


private JButton createProfessionalButton(String text, Color backgroundColor) {
    JButton button = new JButton(text);
    button.setPreferredSize(new Dimension(180, 50)); // Set preferred size for consistency
    button.setBackground(backgroundColor);
    button.setForeground(Color.WHITE);
    button.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1)); // Subtle border
    button.setContentAreaFilled(true);
    button.setOpaque(true);

    
    button.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
        BorderFactory.createEmptyBorder(10, 20, 10, 20)
    ));

   
    button.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            button.setBackground(backgroundColor.darker()); // Darker color on hover
        }

        public void mouseExited(java.awt.event.MouseEvent evt) {
            button.setBackground(backgroundColor); // Reset to original color
        }
    });

    return button;
}



    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "View Profile":
                viewProfile();
                break;
            case "Update Profile":
                updateProfile();
                break;
            case "Logout":
                frame.dispose();
                JOptionPane.showMessageDialog(null, "You have been logged out.", "Logout", JOptionPane.INFORMATION_MESSAGE);
                new Login(); 
                break;
            default:
                JOptionPane.showMessageDialog(frame, "Unknown action: " + command);
        }
    }

    private void viewProfile() {
        JDialog viewDialog = new JDialog(frame, "View Profile", true);
        viewDialog.setSize(500, 400);
        viewDialog.setLayout(new GridBagLayout());
        viewDialog.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding for components

       
        JLabel titleLabel = new JLabel("Your Profile");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180)); // Blueish color
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        viewDialog.add(titleLabel, gbc);

        
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        nameLabel.setForeground(new Color(60, 60, 60)); // Dark grey color
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        viewDialog.add(nameLabel, gbc);

        JLabel nameValue = new JLabel(user.getName());
        nameValue.setFont(new Font("Arial", Font.PLAIN, 18));
        nameValue.setForeground(new Color(30, 30, 30)); // Darker text
        gbc.gridx = 1;
        viewDialog.add(nameValue, gbc);

        
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        emailLabel.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 2;
        viewDialog.add(emailLabel, gbc);

        JLabel emailValue = new JLabel(user.getEmail());
        emailValue.setFont(new Font("Arial", Font.PLAIN, 18));
        emailValue.setForeground(new Color(30, 30, 30));
        gbc.gridx = 1;
        viewDialog.add(emailValue, gbc);

        
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        genderLabel.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 3;
        viewDialog.add(genderLabel, gbc);

        JLabel genderValue = new JLabel(user.getGender());
        genderValue.setFont(new Font("Arial", Font.PLAIN, 18));
        genderValue.setForeground(new Color(30, 30, 30));
        gbc.gridx = 1;
        viewDialog.add(genderValue, gbc);

        
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        addressLabel.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 4;
        viewDialog.add(addressLabel, gbc);

        JLabel addressValue = new JLabel(user.getAddress());
        addressValue.setFont(new Font("Arial", Font.PLAIN, 18));
        addressValue.setForeground(new Color(30, 30, 30));
        gbc.gridx = 1;
        viewDialog.add(addressValue, gbc);

        
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        phoneLabel.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 5;
        viewDialog.add(phoneLabel, gbc);

        JLabel phoneValue = new JLabel(user.getPhone());
        phoneValue.setFont(new Font("Arial", Font.PLAIN, 18));
        phoneValue.setForeground(new Color(30, 30, 30));
        gbc.gridx = 1;
        viewDialog.add(phoneValue, gbc);

        
        JButton closeButton = createButton("Close", new Color(255, 69, 0), new Color(255, 255, 255), 0, 0);
        closeButton.addActionListener(e -> viewDialog.dispose());
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        viewDialog.add(closeButton, gbc);

        
        viewDialog.setLocationRelativeTo(frame);
        viewDialog.setVisible(true);
    }

    private JButton createButton(String text, Color bgColor, Color fgColor, int x, int y) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        button.setFocusPainted(false);
        return button;
    }

    private void updateProfile() {
        JDialog updateDialog = new JDialog(frame, "Update Profile", true);
        updateDialog.setSize(600, 700);
        updateDialog.setLayout(new GridBagLayout());
        updateDialog.setBackground(new Color(255, 255, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding for components

       
        JLabel titleLabel = new JLabel("Update Your Profile");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        titleLabel.setForeground(new Color(70, 130, 180));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        updateDialog.add(titleLabel, gbc);

       
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        nameLabel.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        updateDialog.add(nameLabel, gbc);

        JTextField nameField = new JTextField(user.getName(), 20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 16));
        nameField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        gbc.gridx = 1;
        updateDialog.add(nameField, gbc);

        
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        emailLabel.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 2;
        updateDialog.add(emailLabel, gbc);

        JTextField emailField = new JTextField(user.getEmail(), 20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField.setEditable(false); // Non-editable email field
        emailField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        gbc.gridx = 1;
        updateDialog.add(emailField, gbc);

       
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        genderLabel.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 3;
        updateDialog.add(genderLabel, gbc);

        JComboBox<String> genderBox = new JComboBox<>(new String[]{"Male", "Female", "Other"});
        genderBox.setSelectedItem(user.getGender());
        genderBox.setFont(new Font("Arial", Font.PLAIN, 16));
        genderBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        gbc.gridx = 1;
        updateDialog.add(genderBox, gbc);

       
        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        addressLabel.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 4;
        updateDialog.add(addressLabel, gbc);

        JTextField addressField = new JTextField(user.getAddress(), 20);
        addressField.setFont(new Font("Arial", Font.PLAIN, 16));
        addressField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        gbc.gridx = 1;
        updateDialog.add(addressField, gbc);

       
        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        phoneLabel.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 5;
        updateDialog.add(phoneLabel, gbc);

        JTextField phoneField = new JTextField(user.getPhone(), 20);
        phoneField.setFont(new Font("Arial", Font.PLAIN, 16));
        phoneField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        gbc.gridx = 1;
        updateDialog.add(phoneField, gbc);

       
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordLabel.setForeground(new Color(60, 60, 60));
        gbc.gridx = 0;
        gbc.gridy = 6;
        updateDialog.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        gbc.gridx = 1;
        updateDialog.add(passwordField, gbc);

       
        JButton updateButton = createButton("Update", new Color(0, 128, 0), new Color(255, 255, 255), 0, 0);
        updateButton.addActionListener(e -> {
            user.setName(nameField.getText());
            user.setGender((String) genderBox.getSelectedItem());
            user.setAddress(addressField.getText());
            user.setPhone(phoneField.getText());
            user.setPassword(new String(passwordField.getPassword()));
            
            updateDialog.dispose(); // Close dialog after update
            JOptionPane.showMessageDialog(frame, "Profile Updated Successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        updateDialog.add(updateButton, gbc);

        
        JButton cancelButton = createButton("Cancel", new Color(255, 69, 0), new Color(255, 255, 255), 0, 0);
        cancelButton.addActionListener(e -> updateDialog.dispose());
        gbc.gridx = 0;
        gbc.gridy = 8;
        updateDialog.add(cancelButton, gbc);

      
        updateDialog.setLocationRelativeTo(frame);
        updateDialog.setVisible(true);
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }


}
