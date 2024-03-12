package be.kuleuven.foodrestservice.domain;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;

import java.util.Collection;
import java.util.Date;

public class Order {
    private String id;
    private String customer;
    private Collection<Meal> meals;
    private Double totalPrice;
    private Date date;

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
