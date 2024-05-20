package client;

import entity.Item;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try(Socket socket = new Socket("localhost", 6541);
            Scanner scanner = new Scanner(System.in);
        ){
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to server");
            int choice =0;
            while (true)
            {
                System.out.println("1. Thêm một món ăn mới");
                System.out.println("2. Liệt kê danh sách mặt hàng là món đặt biệt của nhà hàng mà có sử dụng nguyên liệu được nhập từ nhà cung cấp nào đó khi biết tên nhà cung cấp");
                System.out.println("3. Tính giá gốc của từng món ăn sau khi chế biết thành phẩm");
                System.out.println("4. Thoát");
                System.out.println("Nhập lựa chọn của bạn: ");
                choice = scanner.nextInt();
                dos.writeInt(choice);
                dos.flush();
                switch (choice)
                {
                    case 1:
                        System.out.println("Nhập mã số món ăn: ");
                        String id = scanner.next();
                        System.out.println("Nhập tên món ăn: ");
                        String name = scanner.next();
                        System.out.println("Nhập giá món ăn: ");
                        double price = scanner.nextDouble();
                        System.out.println("Nhập mô tả món ăn: ");
                        String description = scanner.next();
                        System.out.println("Món ăn có phải món đặc biệt không (true/false): ");
                        boolean onSpecial = scanner.nextBoolean();
                        System.out.println("Nhập loại món ăn: ");
                        String type = scanner.next();
                        System.out.println("Nhập thời gian chuẩn bị: ");
                        int preparationTime = scanner.nextInt();
                        System.out.println("Nhập thời gian phục vụ: ");
                        int servingTime = scanner.nextInt();
                        dos.writeUTF(id);
                        dos.writeUTF(name);
                        dos.writeDouble(price);
                        dos.writeUTF(description);
                        dos.writeBoolean(onSpecial);
                        dos.writeUTF(type);
                        dos.writeInt(preparationTime);
                        dos.writeInt(servingTime);
                        dos.flush();
                        boolean result = ois.readBoolean();
                        if (result)
                        {
                            System.out.println("Thêm món ăn thành công");
                        }
                        else
                        {
                            System.out.println("Thêm món ăn thất bại");
                        }
                        break;
                    case 2:
                        scanner.nextLine();
                        System.out.println("Nhập tên nhà cung cấp: ");
                        String supplierName = scanner.nextLine();
                        dos.writeUTF(supplierName);
                        dos.flush();
                        List<Item> items = (List<Item>) ois.readObject();
                        if (items.size() == 0)
                        {
                            System.out.println("Không có món ăn nào");
                        }
                        else
                        {
                            System.out.println("Danh sách món ăn: ");
                            for (Item item : items)
                            {
                                System.out.println(item);
                            }
                        }
                    case 3:
                        scanner.nextLine();
                        System.out.println("Danh sách món ăn và giá gốc: ");

                        Map<Item, Double> itemAndCost  = (Map<Item, Double>) ois.readObject();
                        for (Map.Entry<Item, Double> entry : itemAndCost.entrySet())
                        {
                            System.out.println(entry.getKey() + " " + entry.getValue());
                        }
                        break;
                    case 4:
                        System.out.println("Thoát");
                        break;
                    default:
                        System.out.println("Nhập sai. Vui lòng nhập lại");
                        break;
                }


            }

        } catch (Exception e) {
            e.printStackTrace();

}
}
}

