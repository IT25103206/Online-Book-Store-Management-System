package com.sliit.oop_serverapp.service;

import com.sliit.oop_serverapp.dto.OrderDTO;
import java.util.List;

public interface OrderService {
    List<OrderDTO> getAllOrders();
    OrderDTO createOrder(OrderDTO orderDTO);
    OrderDTO updateOrder(OrderDTO orderDTO);
    void deleteOrder(Integer id);
}
