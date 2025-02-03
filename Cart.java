//package classes;

import java.util.ArrayList;
//import interfaces.*;

public class Cart {
    private ArrayList<CartItem> cartItems;

    public Cart() {
        cartItems = new ArrayList<>();
    }

    // Adds a product to the cart with a specific quantity
    public void addToCart(Product product, int quantity) {
        boolean productExists = false;
        for (CartItem item : cartItems) {
            if (item.getProduct().equals(product)) {
                item.setQuantity(item.getQuantity() + quantity); // Increase the quantity
                productExists = true;
                break;
            }
        }
        if (!productExists) {
            cartItems.add(new CartItem(product, quantity));
        }
    }

    // Returns the list of cart items
    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    // Calculates the total price for all items in the cart
    public double calculateTotal() {
        double total = 0;
        for (CartItem item : cartItems) {
            total += item.getTotalPrice(); // get total price of each item
        }
        return total;
    }

    // Removes an item from the cart
    public void removeItem(CartItem item) {
        cartItems.remove(item);
    }

    // Clears all items from the cart
    public void clearCart() {
        cartItems.clear();
    }

    // Check if the cart is empty
    public boolean isEmpty() {
        return cartItems.isEmpty();
    }
}
