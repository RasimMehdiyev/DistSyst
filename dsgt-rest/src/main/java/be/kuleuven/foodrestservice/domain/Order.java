package be.kuleuven.foodrestservice.domain;

import java.util.Collection;

public class Order {
    private String id;
    private String customer;
    private Collection<Meal> meals;
    private Double totalPrice;
    private String date;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCustomer() {
        return customer;
    }
    public void setCustomer(String customer) {
        this.customer = customer;
    }
    public Collection<Meal> getMeals() {
        return meals;
    }
    public void setMeals(Collection<Meal> meals) {
        this.meals = meals;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    public void addMeal(Meal meal) {
        meals.add(meal);
    }

    public Double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(Double totalPrice) {
        double price = 0;
        for (Meal m : meals) {
            price += m.getPrice();
        }
        this.totalPrice = price;

    }
}
