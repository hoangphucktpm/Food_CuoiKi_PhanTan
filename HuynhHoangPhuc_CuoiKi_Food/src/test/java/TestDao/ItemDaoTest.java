package TestDao;

import dao.FoodDao;
import dao.Impl.FoodImpl;
import dao.Impl.ItemImpl;
import dao.ItemDao;
import entity.Food;
import entity.Type;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ItemDaoTest{

    private ItemDao itemDao;

    @BeforeAll
    public void setUp() throws RemoteException {
        itemDao = new ItemImpl();
    }

    @Test
    // Liệt kê danh sách mặt hàng là món đặt biệt của nhà hàng mà có sử dụng nguyên
    //liệu được nhập từ nhà cung cấp nào đó khi biết tên nhà cung cấp (tìm tương đối, không phân biệt
    //chữ thường hoa).
    //+ listItems (supplierName: String) : List<Item>
    void testListItems() throws RemoteException {
        String supplierName = "Charlie's Meats";
        assertEquals(1, itemDao.listItems(supplierName).size());

    }






    @AfterAll
    public void tearDown() throws RemoteException {
        itemDao = null;
    }
}

