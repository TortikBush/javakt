package kt.kt1java.orders;
import kt.kt1java.orders.model.Order;
import kt.kt1java.orders.repository.OrderRepository;
import kt.kt1java.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;  // Мокаем репозиторий

    @InjectMocks
    private OrderService orderService;  // Сервис, который тестируем

    private Order order;

    @BeforeEach
    public void setUp() {
        // Создание тестового заказа
        order = new Order("Product A", 1, new BigDecimal("100.00"), "CREATED");
    }

    @Test
    public void testCreateOrder() {
        // Настроим поведение мока: при сохранении заказа возвращаем сам заказ
        when(orderRepository.save(order)).thenReturn(order);

        // Выполним метод createOrder
        Order createdOrder = orderService.createOrder(order);

        // Проверяем, что заказ был создан правильно
        assertNotNull(createdOrder);
        assertEquals(order.getProduct(), createdOrder.getProduct());
        assertEquals(order.getQuantity(), createdOrder.getQuantity());
        assertEquals(order.getPrice(), createdOrder.getPrice());
        assertEquals(order.getStatus(), createdOrder.getStatus());

        // Проверяем, что метод save() был вызван один раз
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testGetOrderById() {
        // Настроим поведение мока: когда ищем заказ по ID, возвращаем его
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        // Выполним метод getOrderById
        Optional<Order> foundOrder = orderService.getOrderById(1L);

        // Проверяем, что заказ найден
        assertTrue(foundOrder.isPresent());
        assertEquals(order, foundOrder.get());

        // Проверяем, что метод findById() был вызван один раз
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateOrder() {
        // Настроим поведение мока: заказ существует, его можно обновить
        when(orderRepository.existsById(1L)).thenReturn(true);
        when(orderRepository.save(order)).thenReturn(order);

        // Выполним метод updateOrder
        Order updatedOrder = orderService.updateOrder(1L, order);

        // Проверяем, что заказ обновлен
        assertNotNull(updatedOrder);
        assertEquals(order.getProduct(), updatedOrder.getProduct());
        assertEquals(order.getQuantity(), updatedOrder.getQuantity());
        assertEquals(order.getPrice(), updatedOrder.getPrice());

        // Проверяем, что метод save() был вызван один раз
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testDeleteOrder() {
        // Настроим поведение мока: заказ существует, его можно удалить
        when(orderRepository.existsById(1L)).thenReturn(true);

        // Выполним метод deleteOrder
        boolean result = orderService.deleteOrder(1L);

        // Проверяем, что заказ был удален
        assertTrue(result);

        // Проверяем, что метод deleteById() был вызван один раз
        verify(orderRepository, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteOrder_NotFound() {
        // Настроим поведение мока: заказ не существует
        when(orderRepository.existsById(1L)).thenReturn(false);

        // Выполним метод deleteOrder
        boolean result = orderService.deleteOrder(1L);

        // Проверяем, что заказ не был удален
        assertFalse(result);

        // Проверяем, что метод deleteById() не был вызван
        verify(orderRepository, never()).deleteById(1L);
    }
}
