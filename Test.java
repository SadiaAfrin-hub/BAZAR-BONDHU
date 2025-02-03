//package classes.Login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Test implements ActionListener {
    private JFrame f;
    private ImageIcon img,icon;
    private JButton startButton; 

    public Test() {
        f = new JFrame("BAZAR BONDHU");
        f.setSize(800, 600);
        f.setLocationRelativeTo(null);
        f.setLayout(null);

        icon = new ImageIcon(getClass().getResource("IMAGE/icon.png"));
        f.setIconImage(icon.getImage());
		
        img = new ImageIcon(getClass().getResource("IMAGE/shop2.png")); 
        JLabel label = new JLabel(); 
        label.setIcon(img);
        label.setBounds(0, 0, 800, 600);
        f.add(label);
        
        JLabel l1 = new JLabel("YOUR TRUSTWORTHY ONLINE BAZAR");
        l1.setBounds(200, 0, 500, 50);  
        Font fn = new Font("Showcard Gothic", Font.PLAIN, 20);
        l1.setFont(fn);
        label.add(l1);
        
        JLabel l5 = new JLabel("**BAZAR BONDHUU**");
        l5.setBounds(350,282, 120, 20);  
        Font fn2 = new Font("Showcard Gothic", Font.PLAIN,10);
        l5.setFont(fn2);
		l5.setForeground(Color.WHITE);
        label.add(l5);

        startButton = new JButton("LOG-IN");
        startButton.setBounds(342, 445, 135, 55);
		Font fn1 = new Font("Georgia", Font.BOLD, 20);
        startButton.setFont(fn1);
		startButton.setForeground(Color.RED);
		Color c=new Color(130,200,229);
		startButton.setBackground(c);
		
        startButton.addActionListener(this);
        label.add(startButton);

        f.setVisible(true); 
		Color c1=new Color(126,190,205);
		f.getContentPane().setBackground(c1);
		
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            startButton = (JButton) e.getSource();
            if (startButton.getText().equals("LOG-IN")) {
                f.dispose();
                new Login(); 
        }
    }

   
}
}