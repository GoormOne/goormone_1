package com.profect.delivery.domain.order.service;

import com.profect.delivery.domain.order.repository.OrderItemRepository;
import com.profect.delivery.domain.order.repository.OrderRepository;
import com.profect.delivery.global.entity.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;


    public List<Order> findAll(String userId){
        return orderRepository.findAllByUserId(userId);
    }

    public Order findOrder(String orderId){
        return orderRepository.findById(UUID.fromString(orderId))
                .orElseThrow(() -> new RuntimeException("주문 없음"));
    }

}
