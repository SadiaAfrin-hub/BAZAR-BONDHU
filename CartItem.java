//package classes;

import javax.swing.*;
//import interfaces.*;

public class CartItem {
    private Product product;
    private int quantity;

    // Constructor to initialize product and quantity
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getter for product
    public Product getProduct() {
        return product;
    }

    // Setter for product
    public void setProduct(Product product) {
        this.product = product;
    }

    // Getter for quantity
    public int getQuantity() {
        return quantity;
    }

    // Setter for quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Method to get product information in a user-friendly format
    public String getProductInfo() {
        return product.getName() + " x" + quantity + " - BDT " + (product.getPrice() * quantity) + " TAKA";
    }

    // Method to calculate the total price for this cart item
    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    // Method to check if enough stock is available for the quantity requested
    public boolean isStockAvailable() {
        return quantity <= product.getInstock();  // Check if the requested quantity doesn't exceed the available stock
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        CartItem cartItem = (CartItem) obj;
        return product.equals(cartItem.product); // Check if the products are the same
    }

    @Override
    public int hashCode() {
        return product.hashCode(); // Use product's hashCode to ensure uniqueness
    }
}
