//package classes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class Registration implements ActionListener {
    private JFrame frame;
    private JTextField nameField, emailField, addressField, phoneField;
    private JPasswordField passwordField;
    private JComboBox<String> genderBox;
    private JButton registerButton, clearButton, backButton;

    public Registration() {
        frame = new JFrame("Registration");
        frame.setSize(900, 700);
        frame.setLayout(null);

        Color c = new Color(222, 255, 218);
        frame.getContentPane().setBackground(c);

        JLabel label = new JLabel("BAZAR BONDHU'S USER SIGN-UP");
        label.setBounds(100, 10, 700, 60);
        Font fn = new Font("Castellar", Font.BOLD, 35);
        label.setFont(fn);
        frame.add(label);

        ImageIcon img = new ImageIcon(getClass().getResource("/IMAGE/reg.png"));
        JLabel imglabel = new JLabel();
        imglabel.setIcon(img);
        imglabel.setBounds(0, 370, 300, 300);
        frame.add(imglabel);

        ImageIcon icon = new ImageIcon(getClass().getResource("/IMAGE/icon.png"));
        frame.setIconImage(icon.getImage());

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(200, 80, 100, 20);
        frame.add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(300, 80, 300, 30);
        frame.add(nameField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(200, 120, 100, 20);
        frame.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(300, 120, 300, 30);
        frame.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(200, 160, 100, 20);
        frame.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(300, 160, 300, 30);
        frame.add(passwordField);

        JLabel phoneLabel = new JLabel("Number:");
        phoneLabel.setBounds(200, 200, 100, 20);
        frame.add(phoneLabel);

        phoneField = new JTextField();
        phoneField.setBounds(300, 200, 300, 30);
        frame.add(phoneField);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(200, 240, 100, 20);
        frame.add(genderLabel);

        String[] genders = {"Male", "Female", "Other"};
        genderBox = new JComboBox<>(genders);
        genderBox.setBounds(300, 240, 250, 30);
        genderBox.setBackground(Color.white);
        genderBox.setForeground(Color.blue);
        frame.add(genderBox);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(200, 280, 100, 20);
        frame.add(addressLabel);

        addressField = new JTextField();
        addressField.setBounds(300, 280, 300, 30);
        frame.add(addressField);

        registerButton = new JButton("Register");
        registerButton.setBounds(300, 340, 100, 30);
        registerButton.setBackground(new Color(205, 170, 106));
        registerButton.addActionListener(this);
        frame.add(registerButton);

        clearButton = new JButton("Clear");
        clearButton.setBounds(450, 340, 100, 30);
        clearButton.setBackground(new Color(205, 170, 106));
        clearButton.addActionListener(this);
        frame.add(clearButton);

        backButton = new JButton("Go Back");
        backButton.setBounds(380, 390, 100, 30);
        backButton.setBackground(new Color(205, 170, 106));
        backButton.addActionListener(this);
        frame.add(backButton);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            register();
        } else if (e.getSource() == clearButton) {
            clearFields();
        } else if (e.getSource() == backButton) {
            frame.dispose();
            new Login();
        }
    }

    private void register() {
        String name = nameField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String number = phoneField.getText();
        String password = new String(passwordField.getPassword());
        String gender = (String) genderBox.getSelectedItem();

        if (name.isEmpty() || email.isEmpty() || address.isEmpty() || number.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please fill all fields!");
            return;
        }

        User newUser = new User(name, email, password, gender,address,number);
        if (!number.matches("\\d{11}")) {
            JOptionPane.showMessageDialog(frame, "Phone number must be exactly 11 digits!");
            return;
        }

        if (isEmailExists(email)) {
            JOptionPane.showMessageDialog(frame, "User with this email already exists!");
            return;
        }

        try (FileWriter writer = new FileWriter("userdata.txt", true)) {
            writer.write(name + "," + email + "," + password + "," + gender + "," + address + "," + number + "\n");
            JOptionPane.showMessageDialog(frame, "Registration Successful!");
            frame.dispose();
            new Login();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isEmailExists(String email) {
        try (BufferedReader reader = new BufferedReader(new FileReader("userdata.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[1].equals(email)) {
                    return true;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        passwordField.setText("");
        genderBox.setSelectedIndex(0);
        addressField.setText("");
    }

    public static void main(String[] args) {
        new Registration();
    }
}
