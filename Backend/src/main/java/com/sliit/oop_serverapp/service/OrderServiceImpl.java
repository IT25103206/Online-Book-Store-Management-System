package com.sliit.oop_serverapp.service;

import com.sliit.oop_serverapp.dto.OrderDTO;
import com.sliit.oop_serverapp.entity.Order;
import com.sliit.oop_serverapp.entity.User;
import com.sliit.oop_serverapp.entity.Status;
import com.sliit.oop_serverapp.exception.ResourceNotFoundException;
import com.sliit.oop_serverapp.repository.OrderRepository;
import com.sliit.oop_serverapp.repository.UserRepository;
import com.sliit.oop_serverapp.repository.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OOP Concept: Abstraction & Polymorphism
 * OrderServiceImpl implements OrderService, encapsulating the complex 
 * logic for processing customer transactions.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        updateEntityFromDTO(order, orderDTO);
        return convertToDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO) {
        Order order = orderRepository.findById(orderDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderDTO.getId()));
        updateEntityFromDTO(order, orderDTO);
        return convertToDTO(orderRepository.save(order));
    }

    @Override
    public void deleteOrder(Integer id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
        orderRepository.deleteById(id);
    }

    private OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setDate(order.getDate());
        if (order.getUser() != null) dto.setUserId(order.getUser().getId());
        if (order.getStatus() != null) dto.setStatusId(order.getStatus().getId());
        return dto;
    }

    private void updateEntityFromDTO(Order order, OrderDTO dto) {
        order.setDate(dto.getDate());
        if (dto.getUserId() != null) {
            User user = userRepository.findById(dto.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + dto.getUserId()));
            order.setUser(user);
        }
        if (dto.getStatusId() != null) {
            Status status = statusRepository.findById(dto.getStatusId())
                    .orElseThrow(() -> new ResourceNotFoundException("Status not found with id: " + dto.getStatusId()));
            order.setStatus(status);
        }
    }
}
