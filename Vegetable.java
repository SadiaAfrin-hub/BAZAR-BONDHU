//package classes;
import java.io.Serializable;
//import interfaces.*;

public class Vegetable implements IProduct, Serializable {
    private String name;
    private String id;
    private double price;
    private int instock;
    private String photoPath;

    public Vegetable(String name, String id, double price, int instock, String photoPath) {
        this.name = name;
        this.id = id;
        this.price = price;
        this.instock = instock;
        this.photoPath = photoPath;
    }

    
    public String getName() {
        return name;
    }

    
    public String getId() {
        return id;
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
        if (isAvailable(quantity)) {
            instock -= quantity;
        }
    }
}
