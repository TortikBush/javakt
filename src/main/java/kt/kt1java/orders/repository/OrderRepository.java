package kt.kt1java.orders.repository;

import kt.kt1java.orders.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

    public interface OrderRepository extends JpaRepository<Order, Long> {

    }

