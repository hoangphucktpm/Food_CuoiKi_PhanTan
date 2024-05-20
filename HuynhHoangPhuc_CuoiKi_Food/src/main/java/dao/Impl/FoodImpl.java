package dao.Impl;

import dao.FoodDao;
import entity.Food;
import entity.Ingredient;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FoodImpl extends UnicastRemoteObject implements FoodDao {

    private static final long serialVersionUID = 1L;
    private EntityManager em;

    public FoodImpl() throws RemoteException {
        em = Persistence.createEntityManagerFactory("SQLdb").createEntityManager();
    }

    // a) Thêm một món ăn mới. Trong đó, mã số của món phải bắt đầu là F và theo sau ít
    //nhất 3 ký số.
    //+ addFood (food: Food) : boolean

    public boolean addFood(Food food) throws RemoteException {
        if (food.getId().matches("F[0-9]{3}")) {
            em.getTransaction().begin();
            em.persist(food);
            em.getTransaction().commit();
            return true;
        }
        return false;
    }

    // Cau c
    //Tính giá gốc của từng món ăn sau khi chế biết thành phẩm. Kết quả sắp xếp giảm
    //dần theo đơn giá gốc.
    //Trong đó: Giá gốc món ăn = (số lượng nguyên liệu * đơn giá nguyên liệu) + (thời gian chuẩn
    //bị + thời gian phục vụ) * 0.2$
    //+ public listFoodAndCost(): Map<Food, Double>

    public Map<Food, Double> listFoodAndCost() throws RemoteException {
        List<Food> foods = em.createQuery("SELECT f FROM Food f", Food.class).getResultList();
        Map<Food, Double> result = new HashMap<>();
        for (Food food : foods) {
            double cost = 0;
            for (Ingredient ingredient : food.getIngredients()) {
                cost += ingredient.getQuantity() * ingredient.getPrice();
            }
            cost += (food.getPreparationTime() + food.getServingTime()) * 0.2;
            result.put(food, cost);
        }

        // Sort the result map by cost in descending order
        Map<Food, Double> sortedResult = result.entrySet().stream()
                .sorted(Map.Entry.<Food, Double>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        return sortedResult;
    }


}
