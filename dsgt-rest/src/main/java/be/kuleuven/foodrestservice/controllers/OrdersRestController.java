package be.kuleuven.foodrestservice.controllers;
import be.kuleuven.foodrestservice.domain.Order;
import be.kuleuven.foodrestservice.domain.OrderRepository;
import be.kuleuven.foodrestservice.exceptions.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class OrdersRestController {
    private final OrderRepository orderRepository;

    @Autowired
    OrdersRestController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/rest/orders/{id}")
    Order getOrderById(@PathVariable String id) {
        return orderRepository.findOrder(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    @GetMapping("/rest/orders")
    Collection<Order> getOrders() {
        return orderRepository.getAllOrder();
    }

    @PostMapping("/rest/orders/add")
    EntityModel<Order>  addOrder(@RequestBody Order order) {
        orderRepository.addOrder(order);
        return orderToEntityModel(order.getId(), order);
    }

    @DeleteMapping("/rest/orders/delete/{id}")
    Collection<EntityModel<Order>> deleteOrder(@PathVariable String id) {
        orderRepository.deleteOrder(id);
        Collection<Order> orders = orderRepository.getAllOrder();
        return orders.stream().map(order -> orderToEntityModel(order.getId(), order)).toList();
    }

    @PutMapping("/rest/orders/update/{id}")
    EntityModel<Order>  updateOrder(@PathVariable String id, @RequestBody Order order) {
        orderRepository.updateOrder(id, order);
        return orderToEntityModel(id, orderRepository.findOrder(id).orElseThrow(() -> new OrderNotFoundException(id)));
    }

    private EntityModel<Order> orderToEntityModel(String id, Order order) {
        return EntityModel.of(order,
                linkTo(methodOn(OrdersRestController.class).getOrderById(id)).withSelfRel(),
                linkTo(methodOn(OrdersRestController.class).getOrders()).withRel("rest/orders"),
                linkTo(methodOn(OrdersRestController.class).addOrder(order)).withRel("rest/orders/add"),
                linkTo(methodOn(OrdersRestController.class).deleteOrder(id)).withRel("rest/orders/delete/{id}"),
                linkTo(methodOn(OrdersRestController.class).updateOrder(id, order)).withRel("rest/orders/update/{id}"));
    }


}
