package dao;

import entity.Item;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ItemDao extends Remote {
    public List<Item> listItems(String supplierName) throws RemoteException;
}
