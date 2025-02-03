//package classes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
 
 
 
// OrderDashboard Class
public class OrderDashboard implements ActionListener {
    private final JFrame frame;
    private final User user;
    private final Cart cart;
    private JButton orderFruitsButton;
    private JButton orderVegetablesButton;
    private JButton viewCartButton;
    private JButton backButton;
 
    public OrderDashboard(User user) {
        this.user = user;
        this.cart = new Cart();
 
        frame = new JFrame("Order Dashboard");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
 
        frame.add(createBackgroundPanel(), BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
 
    private JPanel createBackgroundPanel() {
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, new Color(255, 223, 186), 0, getHeight(), new Color(144, 238, 144));
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
 
        backgroundPanel.add(createWelcomeLabel(), BorderLayout.NORTH);
        backgroundPanel.add(createButtonPanel(), BorderLayout.CENTER);
 
        return backgroundPanel;
    }
 
    private JLabel createWelcomeLabel() {
        JLabel welcomeLabel = new JLabel("ENJOY YOUR SHOPPING", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Verdana", Font.BOLD, 30));
        welcomeLabel.setForeground(new Color(60, 120, 180));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        return welcomeLabel;
    }
 
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 20, 20));  // Change from 6 to 5
        buttonPanel.setBackground(new Color(200, 230, 250));
 
        orderFruitsButton = createButton("Order Fruits");
        orderVegetablesButton = createButton("Order Vegetables");
        viewCartButton = createButton("View Cart");
        backButton = createBackButton();
 
        buttonPanel.add(orderFruitsButton);
        buttonPanel.add(orderVegetablesButton);
        buttonPanel.add(viewCartButton);
        buttonPanel.add(backButton);  // Only add backButton here
 
        return buttonPanel;
    }
 
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 18));
        button.setBackground(new Color(144, 238, 144));
        button.setForeground(Color.BLACK);
        button.setPreferredSize(new Dimension(300, 60));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(144, 238, 144), 2, true));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(0, 255, 0));
            }
 
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(144, 238, 144));
            }
        });
        button.addActionListener(this);
        return button;
    }
 
    private JButton createBackButton() {
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setBackground(new Color(255, 99, 71));
        backButton.setForeground(Color.WHITE);
        backButton.setPreferredSize(new Dimension(300, 50));
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createLineBorder(new Color(255, 99, 71), 2, true));
        backButton.addActionListener(e -> {
            frame.dispose();
            new UserDashboard(user);
        });
        return backButton;
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Order Fruits":
                displayProducts("fruits");
                break;
            case "Order Vegetables":
                displayProducts("vegetables");
                break;
            case "View Cart":
                viewCart();
                break;
        }
    }
   
    // Method to update the product stock in the file
    private void updateProductStockFile(Product product) {
        String category = getCategoryFromProduct(product);  // Get category based on the product
        File file = new File(category + ".txt");
 
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder content = new StringBuilder();
            String line;
 
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(product.getName())) {
                    parts[3] = String.valueOf(product.getInstock());  // Update stock in the file
                }
                content.append(String.join(",", parts) + "\n");
            }
            reader.close();
 
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(content.toString());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    // Helper method to get the category from the product
    private String getCategoryFromProduct(Product product) {
        // Assuming you determine category by product name or other criteria
        if (product.getName().toLowerCase().contains("fruit")) {
            return "fruits";
        } else {
            return "vegetables";
        }
    }
 
    private void displayProducts(String category) {
        ArrayList<Product> products = loadProductsFromFile(category);
        if (products.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No products available for " + category + ".");
            return;
        }
 
        JPanel productPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        products.forEach(product -> productPanel.add(createProductPanel(product)));
 
        JScrollPane scrollPane = new JScrollPane(productPanel);
        scrollPane.setPreferredSize(new Dimension(800, 580));
        JOptionPane.showMessageDialog(frame, scrollPane, "Products", JOptionPane.PLAIN_MESSAGE);
    }
 
    private JPanel createProductPanel(Product product) {
        JPanel itemPanel = new JPanel(new BorderLayout(10, 10));
        itemPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        itemPanel.setBackground(new Color(220, 240, 255));
 
        itemPanel.add(loadProductImage(product), BorderLayout.NORTH);
 
        JPanel detailsPanel = new JPanel(new GridLayout(2, 1));
        detailsPanel.setBackground(new Color(245, 255, 250));
        detailsPanel.add(createLabel(product.getName(), Font.BOLD, 16));
        detailsPanel.add(createLabel("BDT " + product.getPrice() + " TAKA / KG | In Stock: " + product.getInstock() + " KG", Font.PLAIN, 12));
 
        itemPanel.add(detailsPanel, BorderLayout.CENTER);
 
        JButton buyButton = new JButton("BUY");
        buyButton.setBackground(new Color(0, 255, 0));
        buyButton.setForeground(Color.WHITE);
        buyButton.setFont(new Font("Arial", Font.BOLD, 14));
        buyButton.addActionListener(e -> buyProduct(product));
        itemPanel.add(buyButton, BorderLayout.SOUTH);
 
        return itemPanel;
    }
 
    private JLabel loadProductImage(Product product) {
        try {
            File imageFile = new File(product.getPhotoPath());
            if (imageFile.exists()) {
                ImageIcon productImage = new ImageIcon(product.getPhotoPath());
                Image image = productImage.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                return new JLabel(new ImageIcon(image));
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error loading image for " + product.getName() + ": " + ex.getMessage());
        }
        return new JLabel("Image not found", JLabel.CENTER);
    }
 
    private ArrayList<Product> loadProductsFromFile(String category) {
        ArrayList<Product> products = new ArrayList<>();
        File file = new File(category + ".txt");
 
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    products.add(new Product(parts[0], parts[1], Double.parseDouble(parts[2]), Integer.parseInt(parts[3]), parts[4]));
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(frame, "Error loading products: " + ex.getMessage());
        }
 
        return products;
    }
 
private void buyProduct(Product product) {
    String quantityStr = JOptionPane.showInputDialog(frame, "Enter Quantity (KG):");
    try {
        int quantity = Integer.parseInt(quantityStr);
        if (quantity <= 0) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid quantity greater than 0.");
            return;
        }
        if (product.isAvailable(quantity)) {
            product.reduceStock(quantity);
            cart.addToCart(product, quantity);
            updateProductStockFile(product);  // Update stock in the product file
            JOptionPane.showMessageDialog(frame, "Added to cart!");
        } else {
            JOptionPane.showMessageDialog(frame, "Not enough stock available.");
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(frame, "Invalid quantity.");
    }
}
 
 
private void viewCart() {
    if (cart.getCartItems().isEmpty()) {
        JOptionPane.showMessageDialog(frame, "Your cart is empty!", "Cart", JOptionPane.INFORMATION_MESSAGE);
        return;
    }
 
    // Create a cart panel with attractive design elements
    JPanel cartPanel = new JPanel();
    cartPanel.setLayout(new BoxLayout(cartPanel, BoxLayout.Y_AXIS));
    cartPanel.setBackground(new Color(250, 250, 250)); // Light grey background
    cartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
 
    // Add items to the cart panel with better styling
    cart.getCartItems().forEach(item -> {
        JPanel itemPanel = new JPanel(new BorderLayout(10, 10));
        itemPanel.setBackground(new Color(240, 240, 240));  // Light background for each item
        itemPanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2)); // Subtle border for item
        itemPanel.setPreferredSize(new Dimension(350, 80));
       
        JLabel itemNameLabel = new JLabel(item.getProductInfo());
        itemNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        itemNameLabel.setForeground(new Color(50, 50, 50)); // Dark gray text for the name
 
        // Adding quantity and price info
        JPanel quantityPricePanel = new JPanel();
        quantityPricePanel.setLayout(new GridLayout(1, 2));  // Separate quantity and price
        quantityPricePanel.setBackground(new Color(240, 240, 240));
 
        JLabel quantityLabel = new JLabel("Quantity: " + item.getQuantity() + " KG");
        quantityLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        quantityLabel.setForeground(new Color(100, 100, 100));
 
        JLabel priceLabel = new JLabel("BDT " + item.getTotalPrice() + " TAKA");
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        priceLabel.setForeground(new Color(0, 128, 0));  // Green color for the price
 
        quantityPricePanel.add(quantityLabel);
        quantityPricePanel.add(priceLabel);
 
        // Add name and quantity-price to the item panel
        itemPanel.add(itemNameLabel, BorderLayout.NORTH);
        itemPanel.add(quantityPricePanel, BorderLayout.CENTER);
 
        // Add a "Remove" button
        JButton removeButton = new JButton("Remove");
        removeButton.setFont(new Font("Arial", Font.PLAIN, 12));
        removeButton.setBackground(new Color(255, 80, 80)); // Red color for remove button
        removeButton.setForeground(Color.WHITE);
        removeButton.setFocusPainted(false);
        removeButton.setPreferredSize(new Dimension(80, 30));
 
        // Remove item from the cart on button click
        removeButton.addActionListener(e -> {
            cart.removeItem(item); // Assuming removeItem() is a method in your cart class
            viewCart();  // Refresh the cart view after removal
        });
 
        // Add the remove button to the item panel
        JPanel removePanel = new JPanel();
        removePanel.setBackground(new Color(240, 240, 240));
        removePanel.add(removeButton);
        itemPanel.add(removePanel, BorderLayout.SOUTH);
 
        // Add item to the main cart panel
        cartPanel.add(itemPanel);
    });
 
    // Add a styled total price label
    double totalAmount = cart.calculateTotal();
    JLabel totalLabel = new JLabel("Total Amount: BDT " + totalAmount + " TAKA");
    totalLabel.setFont(new Font("Arial", Font.BOLD, 18));
    totalLabel.setForeground(new Color(51, 102, 255));  // Blue color for total
    totalLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
    cartPanel.add(Box.createVerticalStrut(20)); // Add some space before the total
    cartPanel.add(totalLabel);
 
    // Styled checkout button with hover effects
    JButton checkoutButton = new JButton("Proceed to payment");
    checkoutButton.setFont(new Font("Arial", Font.BOLD, 14));
    checkoutButton.setBackground(new Color(51, 102, 255));  // Blue background for button
    checkoutButton.setForeground(Color.WHITE);
    checkoutButton.setFocusPainted(false);
    checkoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
    checkoutButton.setPreferredSize(new Dimension(200, 50));
    checkoutButton.setBorder(BorderFactory.createLineBorder(new Color(51, 102, 255), 2));
 
    // Hover effect for the button
    checkoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            checkoutButton.setBackground(new Color(30, 60, 150)); // Darker blue
        }
 
        @Override
        public void mouseExited(java.awt.event.MouseEvent evt) {
            checkoutButton.setBackground(new Color(51, 102, 255));  // Reset to original blue
        }
    });
 
    checkoutButton.addActionListener(e -> showPaymentOptions());
 
    cartPanel.add(Box.createVerticalStrut(10)); // Space between total label and button
    cartPanel.add(checkoutButton);
 
    // Show the cart in a scrollable panel with rounded corners and a drop shadow
    JScrollPane scrollPane = new JScrollPane(cartPanel);
    scrollPane.setPreferredSize(new Dimension(400, 400));
    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Add space inside scroll
    cartPanel.setMaximumSize(new Dimension(400, 600));  // Limit maximum height for the scrollable area
 
    // Custom styling for the scroll panel's viewport
    JPanel scrollPanePanel = new JPanel();
    scrollPanePanel.setBackground(new Color(250, 250, 250));
    scrollPanePanel.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
    scrollPanePanel.setLayout(new BorderLayout());
    scrollPanePanel.add(scrollPane, BorderLayout.CENTER);
 
    // Show the cart with the enhanced design
    JOptionPane.showMessageDialog(frame, scrollPanePanel, "Your Cart", JOptionPane.PLAIN_MESSAGE);
}
 
 
private void showPaymentOptions() {
    JPanel paymentPanel = new JPanel(new GridLayout(5, 1, 10, 10));  
    paymentPanel.setBackground(new Color(245, 245, 245));  
    paymentPanel.add(createLabel("Select Payment Option", Font.BOLD, 16));
 
    // Payment methods dropdown
    String[] paymentOptions = {"BKASH", "NAGAD", "ROCKET"};
    JComboBox<String> paymentMethods = new JComboBox<>(paymentOptions);
    paymentMethods.setFont(new Font("Arial", Font.PLAIN, 14));
    paymentPanel.add(paymentMethods);
 
    // Phone number input
    JTextField phoneField = new JTextField();
    phoneField.setBorder(BorderFactory.createTitledBorder("Phone Number"));
    paymentPanel.add(phoneField);
 
    // PIN input field
    JPasswordField pinField = new JPasswordField();
    pinField.setBorder(BorderFactory.createTitledBorder("PIN"));
    paymentPanel.add(pinField);
 
    // Submit button setup
    JButton submitButton = new JButton("Submit Payment");
    submitButton.setFont(new Font("Arial", Font.BOLD, 14));
    submitButton.setBackground(new Color(51, 102, 255));
    submitButton.setForeground(Color.WHITE);
    submitButton.setFocusPainted(false);
    submitButton.setPreferredSize(new Dimension(200, 40));
 
    submitButton.addActionListener(e -> {
        String paymentMethod = (String) paymentMethods.getSelectedItem();
        String phone = phoneField.getText();
        String pin = new String(pinField.getPassword());
 
        // Validate phone number
        if (phone.length() != 11 || !phone.matches("\\d+")) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid 11-digit phone number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
 
        // Validate PIN
        if (pin.length() != 5 || !pin.matches("\\d{5}")) {
            JOptionPane.showMessageDialog(frame, "PIN must be exactly 5 digits.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
 
        double totalAmount = cart.calculateTotal();
        JOptionPane.showMessageDialog(frame,
            "Payment of BDT " + totalAmount + " TAKA via " + paymentMethod + " was successfully completed! \n\n" +
            "Thank you for shopping with us!");
 
        // Update product stock after payment
        cart.getCartItems().forEach(item -> {
            updateProductStockFile(item.getProduct());  // Update stock in file
        });
 
        // Close the current frame and open the new OrderDashboard
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window.isDisplayable()) {
                window.dispose();
            }
        }
        new OrderDashboard(user); // Pass the user object as needed
    });
 
    paymentPanel.add(submitButton);
 
    // Show payment options in a scrollable panel
    JScrollPane scrollPane = new JScrollPane(paymentPanel);
    scrollPane.setPreferredSize(new Dimension(400, 400));
    JOptionPane.showMessageDialog(frame, scrollPane, "Payment", JOptionPane.PLAIN_MESSAGE);
}
 
 
private JLabel createLabel(String text, int style, int size) {
    JLabel label = new JLabel(text);
    label.setFont(new Font("Arial", style, size));
    label.setForeground(new Color(0, 0, 0));  // Black text
    label.setAlignmentX(Component.CENTER_ALIGNMENT);
    return label;
}
 
 
 
}