//package classes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


class Login implements ActionListener {
    private JFrame f;
    private JTextField emailField;
    private JPasswordField passwordField;
    private ImageIcon img, img1, icon;

    public Login() {
        f = new JFrame("Login");
        f.setSize(800, 600);
        f.setLayout(null);

        Color c = new Color(204, 229, 255);
        f.getContentPane().setBackground(c);

        icon = new ImageIcon(getClass().getResource("/IMAGE/icon.png"));
        f.setIconImage(icon.getImage());

        img = new ImageIcon(getClass().getResource("/IMAGE/man.png"));
        JLabel imglabel = new JLabel();
        imglabel.setIcon(img);
        imglabel.setBounds(0, 0, 250, 599);
        f.add(imglabel);

        JLabel label = new JLabel("BAZAR BONDHU'S USER");
        label.setBounds(280, 10, 500, 80);
        Font fn = new Font("Castellar", Font.BOLD, 35);
        label.setFont(fn);
        f.add(label);

        JLabel labellog = new JLabel("LOG-IN");
        labellog.setBounds(470, 100, 100, 30);
        Font fnlog = new Font("Castellar", Font.BOLD, 20);
        labellog.setFont(fnlog);
        f.add(labellog);
        labellog.setForeground(Color.RED);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(320, 150, 80, 30);
        f.add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(400, 150, 300, 30);
        f.add(emailField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(320, 200, 80, 30);
        f.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(400, 200, 300, 30);
        f.add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(470, 250, 100, 30);
        loginButton.addActionListener(this);
        f.add(loginButton);
        loginButton.setForeground(Color.BLUE);
        loginButton.setBackground(Color.lightGray);

        JLabel label1 = new JLabel("Don't have an Account? Click *Register*");
        label1.setBounds(340, 300, 300, 60);
        Font fn1 = new Font("Castellar", Font.BOLD, 10);
        label1.setFont(fn1);
        f.add(label1);
        label1.setForeground(Color.RED);

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(470, 350, 100, 30);
        registerButton.addActionListener(this);
        f.add(registerButton);
        registerButton.setForeground(Color.RED);
        Color reg = new Color(51, 153, 255);
        registerButton.setBackground(reg);

        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("Login")) {
                login();
            } else if (button.getText().equals("Register")) {
                f.dispose();
                new Registration();
            }
        }
    }

     private void login() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        User user = null;
		
		 if (email.equals("admin@gmail.com") && password.equals("admin123")) {
            JOptionPane.showMessageDialog(f, "Admin Login Successful!");
            f.dispose();
            new AdminDashboard();
            return;
        }

        try {
            File file = new File("userdata.txt");
            if (!file.exists()) {
                JOptionPane.showMessageDialog(f, "No user registered yet!");
                return;
            }

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            boolean loggedIn = false;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[1].equals(email) && parts[2].equals(password)) {
                    loggedIn = true;
                    user = new User(parts[0], parts[1], parts[2], parts[3],parts[4],parts[5]);
                    break;
                }
            }
            reader.close();

            if (loggedIn) {
                JOptionPane.showMessageDialog(f, "Login Successful!");
                f.dispose();
                new UserDashboard(user);
            } else {
                JOptionPane.showMessageDialog(f, "Invalid email or password!");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
