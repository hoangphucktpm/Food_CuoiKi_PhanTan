package TestDao;

import dao.FoodDao;
import dao.Impl.FoodImpl;
import entity.Food;
import entity.Type;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FoodDaoTest {

    private FoodDao foodDao;

    @BeforeAll
    public void setUp() throws RemoteException {
        foodDao = new FoodImpl();
    }

    @Test
    //Câu 1. Thêm một món ăn mới. Trong đó, mã số của món phải bắt đầu là F và theo sau ít
    //nhất 3 ký số.
    //+ addFood (food: Food) : boolean

    void testAddFood() throws RemoteException {
        Food food = new Food("F001", "Banh Mi", 20000, "Banh Mi Thit", false, Type.APPETIZER, 10, 5);
        assertTrue(foodDao.addFood(food));
    }

    @Test
    void testAddFood2() throws RemoteException {
        Food food = new Food("F01", "Banh Mi", 20000, "Banh Mi Thit", false, Type.APPETIZER, 10, 5);
        assertEquals(false, foodDao.addFood(food));
    }

    @Test
    void testAddFood3() throws RemoteException {
        Food food = new Food("A", "Banh Mi", 20000, "Banh Mi Thit", false, Type.APPETIZER, 10, 5);
        assertEquals(false, foodDao.addFood(food));
    }

    @Test
    // (1.5 điểm) Tính giá gốc của từng món ăn sau khi chế biết thành phẩm. Kết quả sắp xếp giảm
    //dần theo đơn giá gốc.
    //Trong đó: Giá gốc món ăn = (số lượng nguyên liệu * đơn giá nguyên liệu) + (thời gian chuẩn
    //bị + thời gian phục vụ) * 0.2$
    //+ public listFoodAndCost(): Map<Food, Double>

    void testListFoodAndCost() throws RemoteException {
        foodDao.listFoodAndCost().forEach((k, v) -> System.out.println(k + " " + v));
    }

    @Test
    void testListFoodAndCost2() throws RemoteException {
        assertNotNull(foodDao.listFoodAndCost());
    }

    @Test
    void testListFoodAndCost3() throws RemoteException {
        assertTrue(foodDao.listFoodAndCost().size() > 0);
    }



    @AfterAll
    public void tearDown() throws RemoteException {
        foodDao = null;
    }
}

