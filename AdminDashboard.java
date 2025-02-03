//package classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
//import interfaces.*;

class AdminDashboard implements ActionListener {
    private JFrame frame;
    private Container c;

    public AdminDashboard() {
        frame = new JFrame("Admin Dashboard");
        frame.setSize(800, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Set up container and layout
        c = frame.getContentPane();
        c.setLayout(null);
        c.setBackground(new Color(32, 34, 37)); // Modern dark background

        JLabel titleLabel = new JLabel("Admin Dashboard");
        titleLabel.setBounds(250, 30, 300, 40);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(new Color(255, 215, 0)); // Gold color
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        c.add(titleLabel);

        JButton manageUsersButton = createButton("Manage Users", 80);
        JButton manageFruitsButton = createButton("Manage Fruits", 140);
        JButton manageVegetablesButton = createButton("Manage Vegetables", 200);
        JButton logoutButton = createButton("Logout", 260);
        logoutButton.setBackground(new Color(231, 76, 60)); // Red for logout

        // Adding buttons to container
        c.add(manageUsersButton);
        c.add(manageFruitsButton);
        c.add(manageVegetablesButton);
        c.add(logoutButton);

        frame.setVisible(true);
    }

    private JButton createButton(String text, int yPosition) {
        JButton button = new JButton(text);
        button.setBounds(250, yPosition, 300, 50);
        button.setBackground(new Color(46, 204, 113)); // Greenish button color
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(39, 174, 96), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        button.addActionListener(this);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        String action = button.getText();

        frame.dispose();

        switch (action) {
            case "Manage Users":
                new ManageUsers();
                break;
            case "Manage Fruits":
                new ManageFruits();
                break;
            case "Manage Vegetables":
                new ManageVegetables();
                break;
            case "Logout":
                new Login();
                break;
            default:
                break;
        }
    }
}
