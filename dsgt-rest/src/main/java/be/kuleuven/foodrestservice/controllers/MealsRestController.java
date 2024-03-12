package be.kuleuven.foodrestservice.controllers;

import be.kuleuven.foodrestservice.domain.Meal;
import be.kuleuven.foodrestservice.domain.MealsRepository;
import be.kuleuven.foodrestservice.exceptions.MealNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class MealsRestController {

    private final MealsRepository mealsRepository;

    @Autowired
    MealsRestController(MealsRepository mealsRepository) {
        this.mealsRepository = mealsRepository;
    }

    @GetMapping("/rest/meals/{id}")
    EntityModel<Meal> getMealById(@PathVariable String id) {
        Meal meal = mealsRepository.findMeal(id).orElseThrow(() -> new MealNotFoundException(id));

        return mealToEntityModel(id, meal);
    }

    @GetMapping("/rest/meals")
    CollectionModel<EntityModel<Meal>> getMeals() {
        Collection<Meal> meals = mealsRepository.getAllMeal();

        List<EntityModel<Meal>> mealEntityModels = new ArrayList<>();
        for (Meal m : meals) {
            EntityModel<Meal> em = mealToEntityModel(m.getId(), m);
            mealEntityModels.add(em);
        }
        return CollectionModel.of(mealEntityModels,
                linkTo(methodOn(MealsRestController.class).getMeals()).withSelfRel());
    }

    @GetMapping("/rest/meals/cheapest")
    EntityModel<Meal> getCheapestMeal() {
            Meal meal = mealsRepository.getCheapestMeal();
            return mealToEntityModel(meal.getId(), meal);
    }
    @GetMapping("/rest/meals/largest")
    EntityModel<Meal> getLargestMeal() {
        Meal meal = mealsRepository.getLargestMeal();
        return mealToEntityModel(meal.getId(), meal);
    }

    @PostMapping("/rest/meals/add")
    EntityModel<Meal> addMeal(@RequestBody Meal meal) {
        mealsRepository.addMeal(meal);
        EntityModel<Meal> em = mealToEntityModel(meal.getId(), meal);
        return em;
    }
    @PutMapping("/rest/meals/update/{id}")
    EntityModel<Meal> updateMeal(@PathVariable String id, @RequestBody Meal meal) {
        mealsRepository.updateMeal(id, meal);
        EntityModel<Meal> em = mealToEntityModel(id, meal);
        return em;
    }
    @DeleteMapping("/rest/meals/delete/{id}")
    Class<?> deleteMeal(@PathVariable String id) {
        mealsRepository.deleteMeal(id);
        return null;
    }



    private EntityModel<Meal> mealToEntityModel(String id, Meal meal) {
        return EntityModel.of(meal,
                linkTo(methodOn(MealsRestController.class).getMealById(id)).withSelfRel(),
                linkTo(methodOn(MealsRestController.class).getMeals()).withRel("rest/meals"),
                linkTo(methodOn(MealsRestController.class).getCheapestMeal()).withRel("rest/meals/cheapest"),
                linkTo(methodOn(MealsRestController.class).getLargestMeal()).withRel("rest/meals/largest"),
                linkTo(methodOn(MealsRestController.class).addMeal(meal)).withRel("rest/meals/add"),
                linkTo(methodOn(MealsRestController.class).deleteMeal(id)).withRel("rest/meals/delete/{id}"),
                linkTo(methodOn(MealsRestController.class).updateMeal(id, meal)).withRel("rest/meals/update/{id}"));

    }



}
