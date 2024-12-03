package com.example.springboot.controllers;

import com.example.springboot.models.OrderModel;
import com.example.springboot.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/orders")
    public ResponseEntity<OrderModel> createOrder(@RequestBody @Valid OrderModel orderModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderRepository.save(orderModel));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderModel>> getAllOrders() {
        return ResponseEntity.status(HttpStatus.OK).body(orderRepository.findAll());
    }
}