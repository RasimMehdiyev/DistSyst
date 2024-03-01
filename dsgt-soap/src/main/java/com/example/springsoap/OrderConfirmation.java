package com.example.springsoap;
import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import io.foodmenu.gt.webservice.*;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class OrderConfirmation {
    // orders
    private static final Map<String, Order> orders = new HashMap<String, Order>();

    @PostConstruct
    public void initData() {
        Order a = new Order();

        a.setAddress("1234 Main St");
        Meal meal = new Meal();
        meal.setName("Steak");
        meal.setDescription("Steak with fries");
        meal.setMealtype(Mealtype.MEAT);
        meal.setKcal(1100);
        meal.setPrice(20);
        a.setMeals(meal);
        a.setOrderID("1");

        orders.put(a.getOrderID(), a);

    }

    public Order addNewOrder(Order order) {
        orders.put(order.getOrderID(), order);
        return order;
    }

}
