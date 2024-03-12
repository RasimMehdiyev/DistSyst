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
        EntityModel<Order> o = orderToEntityModel(order.getId(), order);
        return o;
    }

    @DeleteMapping("/rest/orders/delete/{id}")
    EntityModel<Order> deleteOrder(@PathVariable String id) {
        orderRepository.deleteOrder(id);
        EntityModel<Order> o = orderToEntityModel(id, orderRepository.findOrder(id).orElseThrow(() -> new OrderNotFoundException(id)));
        return o;
    }

    @PutMapping("/rest/orders/update/{id}")
    EntityModel<Order>  updateOrder(@PathVariable String id, @RequestBody Order order) {
        orderRepository.updateOrder(id, order);
        EntityModel<Order> o = orderToEntityModel(id, orderRepository.findOrder(id).orElseThrow(() -> new OrderNotFoundException(id)));
        return o;
    }

    @GetMapping("/rest/orders/totalprice/{id}")
    Double getTotalPrice(@PathVariable String id) {
        return orderRepository.getTotalPrice(id);
    }

    private EntityModel<Order> orderToEntityModel(String id, Order order) {
        return EntityModel.of(order,
                linkTo(methodOn(OrdersRestController.class).getOrderById(id)).withSelfRel(),
                linkTo(methodOn(OrdersRestController.class).getOrders()).withRel("rest/orders"),
                linkTo(methodOn(OrdersRestController.class).getTotalPrice(id)).withRel("rest/orders/totalprice/{id}"),
                linkTo(methodOn(OrdersRestController.class).addOrder(order)).withRel("rest/orders/add"),
                linkTo(methodOn(OrdersRestController.class).deleteOrder(id)).withRel("rest/orders/delete/{id}"),
                linkTo(methodOn(OrdersRestController.class).updateOrder(id, order)).withRel("rest/orders/update/{id}"));
    }


}
