package kt.kt1java.service;
import kt.kt1java.orders.model.Order;
import kt.kt1java.orders.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

    @Service
    public class OrderService {

        @Autowired
        private OrderRepository orderRepository;

        public Order createOrder(Order order) {
            return orderRepository.save(order);
        }

        public List<Order> getAllOrders() {
            return orderRepository.findAll();
        }

        public Optional<Order> getOrderById(Long id) {
            return orderRepository.findById(id);
        }

        public Order updateOrder(Long id, Order order) {
            if (orderRepository.existsById(id)) {
                order.setId(id);
                return orderRepository.save(order);
            }
            return null;  // Можно выбросить исключение, если заказ не найден
        }

        public boolean deleteOrder(Long id) {
            if (orderRepository.existsById(id)) {
                orderRepository.deleteById(id);
                return true;
            }
            return false;  // Можно выбросить исключение, если заказ не найден
        }
    }

