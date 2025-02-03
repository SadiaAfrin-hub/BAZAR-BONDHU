//package classes;
import java.io.*;
 
 
 
public class Product {
    private String name;
    private String description;
    private double price;
    private int instock;
    private String photoPath;
 
    public Product(String name, String description, double price, int instock, String photoPath) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.instock = instock;
        this.photoPath = photoPath;
    }
 
    public String getName() {
        return name;
    }
 
    public double getPrice() {
        return price;
    }
 
    public int getInstock() {
        return instock;
    }
 
    public String getPhotoPath() {
        return photoPath;
    }
 
    public boolean isAvailable(int quantity) {
        return instock >= quantity;
    }
 
    public void reduceStock(int quantity) {
        if (instock >= quantity) {
            instock -= quantity;
        }
    }
 
    public String getProductInfo() {
        return name + " - " + price + " TAKA / KG";
    }
 
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
 
private String getCategoryFromProduct(Product product) {
    // Assuming you have a way to determine the category of the product based on its name or other details.
    // For simplicity, you can decide that the category is "fruits" or "vegetables" based on the product's name or other criteria.
    if (product.getName().toLowerCase().contains("fruit")) {
        return "fruits";
    } else {
        return "vegetables";
    }
}
 
}