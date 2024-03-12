package be.kuleuven.foodrestservice.controllers;
import be.kuleuven.foodrestservice.domain.Order;
import be.kuleuven.foodrestservice.domain.OrderRepository;
import be.kuleuven.foodrestservice.exceptions.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
public class OrdersRestRpcStyleController {

        private final OrderRepository orderRepository;

        @Autowired
        OrdersRestRpcStyleController(OrderRepository orderRepository) {
            this.orderRepository = orderRepository;
        }

        @GetMapping("/restrpc/orders/{id}")
        Order getOrderById(@PathVariable String id) {
            Optional<Order> order = orderRepository.findOrder(id);

            return order.orElseThrow(() -> new OrderNotFoundException(id));
        }

        @GetMapping("/restrpc/orders")
        Collection<Order> getOrders() {
            return orderRepository.getAllOrder();
        }

        @GetMapping("/restrpc/orders/totalprice/{id}")
        Double getTotalPrice(@PathVariable String id) {
            return orderRepository.getTotalPrice(id);
        }

        @GetMapping("/restrpc/orders/cheapest")
        Order getCheapestOrder() {
            return orderRepository.getCheapestOrder();
        }

        @GetMapping("/restrpc/orders/largest")
        Order getLargestOrder() {
            return orderRepository.getLargestOrder();
        }

        @GetMapping("/restrpc/orders/add")
        void addOrder(@RequestBody Order order) {
            orderRepository.addOrder(order);
        }

        @GetMapping("/restrpc/orders/delete/{id}")
        void deleteOrder(@PathVariable String id) {
            orderRepository.deleteOrder(id);
        }

        @GetMapping("/restrpc/orders/update/{id}")
        void updateOrder(@PathVariable String id, @RequestBody Order order) {
            orderRepository.updateOrder(id, order);
        }
}
