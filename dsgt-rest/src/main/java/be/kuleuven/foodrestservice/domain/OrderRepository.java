package be.kuleuven.foodrestservice.domain;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class OrderRepository {
    private static final Map<String, Order> orders = new HashMap<>();

    @PostConstruct
    public void initData() {

//        meal array 1
        Meal m1 = new Meal();
        m1.setId("5268203c-de76-4921-a3e3-439db69c462a");
        m1.setName("Steak");
        m1.setDescription("Steak with fries");
        m1.setMealType(MealType.MEAT);
        m1.setKcal(1100);
        m1.setPrice((10.00));

//        meal array 2
        Meal m2 = new Meal();
        m2.setId("4237681a-441f-47fc-a747-8e0169bacea1");
        m2.setName("Portobello");
        m2.setDescription("Portobello Mushroom Burger");
        m2.setMealType(MealType.VEGAN);
        m2.setKcal(637);
        m2.setPrice((7.00));

//       meal array 3
        Meal m3 = new Meal();
        m3.setId("cfd1601f-29a0-485d-8d21-7607ec0340c8");
        m3.setName("Fish and Chips");
        m3.setDescription("Fried fish with chips");
        m3.setMealType(MealType.FISH);
        m3.setKcal(950);
        m3.setPrice(5.00);

//        meals
        Collection<Meal> meals = new ArrayList<>();
        meals.add(m1);
        meals.add(m2);

        Collection<Meal> meals2 = new ArrayList<>();
        meals2.add(m3);
        meals2.add(m2);
        meals2.add(m1);

        Collection<Meal> meals3 = new ArrayList<>();
        meals3.add(m3);
        meals3.add(m2);



        Order a = new Order();
        a.setId("5268203c-de76-4921-a3e3-439db69c462a");
        a.setCustomer("John");
        a.setMeals(meals);
        a.setTotalPrice(10.00);
        orders.put(a.getId(), a);

        Order b = new Order();
        b.setId("4237681a-441f-47fc-a747-8e0169bacea1");
        b.setCustomer("Jane");
        b.setMeals(meals2);
        b.setTotalPrice(7.00);
        orders.put(b.getId(), b);

        Order c = new Order();
        c.setId("cfd1601f-29a0-485d-8d21-7607ec0340c8");
        c.setCustomer("Jack");
        c.setMeals(meals3);
        c.setTotalPrice(5.00);
        orders.put(c.getId(), c);
    }

    public Optional<Order> findOrder(String id) {
        Assert.notNull(id, "The order id must not be null");
        Order order = orders.get(id);
        return Optional.ofNullable(order);
    }

    public Collection<Order> getAllOrder() {
        return orders.values();
    }


    public void addOrder(Order order) {
        if(orders.containsKey(order.getId()))
            orders.replace(order.getId(), order);
        else
            orders.put(order.getId(), order);
    }

    public void deleteOrder(String id) {
        orders.remove(id);
    }

    public Order getLargestOrder() {
        return orders.values().stream().max(Comparator.comparing(Order::getTotalPrice)).orElse(null);
    }

    public Order getCheapestOrder() {
        return orders.values().stream().min(Comparator.comparing(Order::getTotalPrice)).orElse(null);
    }

    public Order getMostRecentOrder() {
        return orders.values().stream().max(Comparator.comparing(Order::getId)).orElse(null);
    }

    public Order getOldestOrder() {
        return orders.values().stream().min(Comparator.comparing(Order::getId)).orElse(null);
    }

    public void updateOrder(String id, Order order) {
        orders.replace(id, order);
    }

    public Double getTotalPrice(String id) {
        return orders.get(id).getTotalPrice();
    }

    public Collection<Order> sortOrders() {
        List<Order> sortedOrders = new ArrayList<>(orders.values());
        sortedOrders.sort(Comparator.comparing(Order::getTotalPrice));
        return sortedOrders;
    }
}
