/*package interfaces;

import classes.*; */








public interface IProduct 



{
    String getName();
    String getId();
    double getPrice();
    int getInstock();
    String getPhotoPath();
    boolean isAvailable(int quantity);
    void reduceStock(int quantity);
}