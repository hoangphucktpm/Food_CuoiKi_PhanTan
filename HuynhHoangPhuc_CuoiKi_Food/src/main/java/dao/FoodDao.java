package dao;

import entity.Food;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface FoodDao extends Remote {
    public boolean addFood(Food food) throws RemoteException;
    public Map<Food, Double> listFoodAndCost() throws RemoteException;
}
