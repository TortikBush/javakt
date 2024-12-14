package kt.kt1java.orders;
import  kt.kt1java.orders.model.Order;
import  kt.kt1java.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderService orderService;  // Мокаем сервис

    @InjectMocks
    private OrderController orderController;  // Контроллер, который тестируем

    private MockMvc mockMvc;  // Для выполнения HTTP-запросов в тестах

    private Order order;

    @BeforeEach
    public void setUp() {
        // Инициализация MockMvc и создание тестового заказа
        mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
        order = new Order("Product A", 1, new BigDecimal("100.00"), "CREATED");
    }

    @Test
    public void testCreateOrder() throws Exception {
        // Настроим поведение мока: когда создаем заказ, он возвращается
        when(orderService.createOrder(order)).thenReturn(order);

        // Выполним POST-запрос на создание нового заказа
        mockMvc.perform(post("/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"product\": \"Product A\", \"quantity\": 1, \"price\": 100.00, \"status\": \"CREATED\"}"))
                .andExpect(status().isCreated())  // Ожидаем статус 201 CREATED
                .andExpect(jsonPath("$.product").value("Product A"))  // Проверка, что в ответе правильное имя продукта
                .andExpect(jsonPath("$.quantity").value(1))  // Проверка правильного количества
                .andExpect(jsonPath("$.price").value(100.00))  // Проверка правильной цены
                .andExpect(jsonPath("$.status").value("CREATED"));  // Проверка правильного статуса

        verify(orderService, times(1)).createOrder(order);  // Проверяем, что метод createOrder был вызван один раз
    }

    @Test
    public void testGetAllOrders() throws Exception {
        // Настроим поведение мока: возвращаем список заказов
        when(orderService.getAllOrders()).thenReturn(Collections.singletonList(order));

        // Выполним GET-запрос для получения всех заказов
        mockMvc.perform(get("/orders"))
                .andExpect(status().isOk())  // Ожидаем статус 200 OK
                .andExpect(jsonPath("$[0].product").value("Product A"))  // Проверка первого заказа
                .andExpect(jsonPath("$[0].quantity").value(1))
                .andExpect(jsonPath("$[0].price").value(100.00))
                .andExpect(jsonPath("$[0].status").value("CREATED"));

        verify(orderService, times(1)).getAllOrders();  // Проверяем, что метод getAllOrders был вызван один раз
    }

    @Test
    public void testGetOrderById() throws Exception {
        // Настроим поведение мока: возвращаем заказ по ID
        when(orderService.getOrderById(1L)).thenReturn(java.util.Optional.of(order));

        // Выполним GET-запрос для получения заказа по ID
        mockMvc.perform(get("/orders/1"))
                .andExpect(status().isOk())  // Ожидаем статус 200 OK
                .andExpect(jsonPath("$.product").value("Product A"))
                .andExpect(jsonPath("$.quantity").value(1))
                .andExpect(jsonPath("$.price").value(100.00))
                .andExpect(jsonPath("$.status").value("CREATED"));

        verify(orderService, times(1)).getOrderById(1L);  // Проверяем, что метод getOrderById был вызван один раз
    }

    @Test
    public void testUpdateOrder() throws Exception {
        // Настроим поведение мока: обновляем заказ
        when(orderService.updateOrder(eq(1L), any(Order.class))).thenReturn(order);

        // Выполним PUT-запрос для обновления заказа
        mockMvc.perform(put("/orders/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"product\": \"Updated Product\", \"quantity\": 2, \"price\": 150.00, \"status\": \"SHIPPED\"}"))
                .andExpect(status().isOk())  // Ожидаем статус 200 OK
                .andExpect(jsonPath("$.product").value("Updated Product"))
                .andExpect(jsonPath("$.quantity").value(2))
                .andExpect(jsonPath("$.price").value(150.00))
                .andExpect(jsonPath("$.status").value("SHIPPED"));

        verify(orderService, times(1)).updateOrder(eq(1L), any(Order.class));  // Проверяем, что метод updateOrder был вызван один раз
    }

    @Test
    public void testDeleteOrder() throws Exception {
        // Настроим поведение мока: заказ существует
        when(orderService.deleteOrder(1L)).thenReturn(true);

        // Выполним DELETE-запрос для удаления заказа
        mockMvc.perform(delete("/orders/1"))
                .andExpect(status().isOk())  // Ожидаем статус 200 OK
                .andExpect(content().string("true"));  // Ожидаем, что вернется "true", если заказ успешно удален

        verify(orderService, times(1)).deleteOrder(1L);  // Проверяем, что метод deleteOrder был вызван один раз
    }
}
