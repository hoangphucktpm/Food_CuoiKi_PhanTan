package server;

import dao.FoodDao;
import dao.Impl.FoodImpl;
import dao.Impl.ItemImpl;
import dao.ItemDao;
import entity.Food;
import entity.Item;
import entity.Type;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;


public class Server {
    public static void main(String[] args) {
        try(ServerSocket serverSocket = new ServerSocket(6541)) {
            System.out.println("Server is running...");
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New client connected");
                System.out.println("Client IP: " + socket.getInetAddress().getHostAddress());
                Thread t = new Thread(new ClientHandler(socket));
                t.start();
            }
        } catch (IOException e) {
            System.out.println("Server exception " + e.getMessage());
        }
    }
}
class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) throws RemoteException {
        this.socket = socket;

    }

    @Override
    public void run() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            FoodDao foodDao = new FoodImpl();
            ItemDao itemDao = new ItemImpl();
            int choice = 0;
            while (true)
            {
                try {
                    choice = dis.readInt();
                } catch (SocketException | EOFException e) {
                    System.out.println("Client disconnected");
                    break;
                }
                switch (choice)
                {
                    case 1:
                        System.out.println("Cau 1: Them mon an moi");
                        //     public Food(String id, String name, double price, String description, boolean onSpecial, Type type, int preparationTime, int servingTime) {
                        String id = dis.readUTF();
                        String name = dis.readUTF();
                        double price = dis.readDouble();
                        String description = dis.readUTF();
                        boolean onSpecial = dis.readBoolean();
                        Type type = Type.valueOf(dis.readUTF());
                        int preparationTime = dis.readInt();
                        int servingTime = dis.readInt();

                        Food food = new Food(id, name, price, description, onSpecial, type, preparationTime, servingTime);
                        boolean result = foodDao.addFood(food);
                        oos.writeBoolean(result);
                        oos.flush();
                        break;
                    case 2:
                        System.out.println("Cau 2: Liet ke danh sach mat hang la mon dac biet cua nha hang ma co su dung nguyen lieu duoc nhap tu nha cung cap nao do khi biet ten nha cung cap");
                        String supplierName = dis.readUTF();
                        List<Item> items = itemDao.listItems(supplierName);
                        oos.writeObject(items);
                        oos.flush();
                        break;
                    case 3:
                        System.out.println("Cau 3: Tinh gia goc cua tung mon an sau khi che bien thanh pham. Ket qua sap xep giam dan theo don gia goc");
                        Map<Food, Double> foodAndCost = foodDao.listFoodAndCost();
                        oos.writeObject(foodAndCost);
                        oos.flush();
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Client exception " + e.getMessage());

        }
    }
}
