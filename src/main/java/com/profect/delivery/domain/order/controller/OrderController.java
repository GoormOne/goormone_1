package com.profect.delivery.domain.order.controller;


import com.profect.delivery.domain.order.repository.OrderRepository;
import com.profect.delivery.domain.order.service.OrderService;
import com.profect.delivery.global.dto.ApiResponse;
import com.profect.delivery.global.dto.ErrorResponse;
import com.profect.delivery.global.entity.Order;
import com.profect.delivery.global.exception.BusinessException;
import com.profect.delivery.global.exception.custom.AuthErrorCode;
import com.profect.delivery.global.exception.custom.BusinessErrorCode;
import com.profect.delivery.global.exception.custom.DefaultErrorCode;
import com.profect.delivery.global.exception.custom.OrderErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
private final OrderService orderService;


    @GetMapping
    public ResponseEntity<ApiResponse<?>> getOrdersList(HttpServletRequest request){
        String userId = request.getParameter("userId");
        if (userId == null){
            userId = "U000000012";
        }

        List<Order> orders = orderService.findAll(userId);

        if (orders.isEmpty()){
            throw new BusinessException(OrderErrorCode.NOT_FOUND_ORDER);
//            ErrorResponse errorResponse = new ErrorResponse(
//                    2000, "내역이 존재하지 않습니다.", request.getRequestURI(), LocalDateTime.now());
//            return new ResponseEntity<>(ApiResponse.failure(errorResponse),  HttpStatus.OK);
        }

        return new  ResponseEntity<>(ApiResponse.success(orders), HttpStatus.OK);
    }

    @GetMapping("/receipt/{orderId}")
    public ResponseEntity<ApiResponse<?>> getReceiptList(
            HttpServletRequest request,
            @PathVariable String orderId){
        String userId = request.getParameter("userId");
        if (userId == null){
            userId = "U000000012";
        }
        Order order = orderService.findOrder(orderId);
        if (order.getUserId().equals(userId)){
            return new  ResponseEntity<>(ApiResponse.success(order), HttpStatus.OK);
        }else{
            throw new BusinessException(AuthErrorCode.UNAUTHORIZED);
//            ErrorResponse errorResponse = new ErrorResponse(
//                    2002, "보인 주문 내역이 아닙니다.", request.getRequestURI(), LocalDateTime.now());
//            return new  ResponseEntity<>(ApiResponse.failure(errorResponse), HttpStatus.OK);
        }
    }

}
