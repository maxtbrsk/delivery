package com.example.springboot.controllers;

import com.example.springboot.dtos.OrderDto;
import com.example.springboot.models.ClientModel;
import com.example.springboot.models.OrderModel;
import com.example.springboot.repositories.ClientRepository;
import com.example.springboot.repositories.OrderRepository;
import com.example.springboot.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ItemRepository itemRepository;

    @PostMapping("/orders")
    public ResponseEntity<OrderModel> createOrder(@RequestBody @Valid OrderModel orderModel) {
        // Calcula o valor total do pedido
        BigDecimal totalValue = orderModel.getItemIds().stream()
                .map(itemRepository::findById)
                .filter(Optional::isPresent)
                .map(item -> item.get().getValue().multiply(BigDecimal.valueOf(orderModel.getQuantities().get(orderModel.getItemIds().indexOf(item.get().getIdItem())))))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        orderModel.setTotalValue(totalValue.doubleValue());

        return ResponseEntity.status(HttpStatus.CREATED).body(orderRepository.save(orderModel));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderModel> orders = orderRepository.findAll();
        List<OrderDto> orderDtos = orders.stream().map(order -> {
            ClientModel client = clientRepository.findById(order.getUserId()).orElse(null);
            return new OrderDto(
                    order.getIdOrder(),
                    order.getUserId(),
                    order.getItemIds(),
                    order.getQuantities(),
                    order.getStreet(),
                    order.getNumber(),
                    order.getDistrict(),
                    order.getCity(),
                    order.getTotalValue(),
                    client != null ? client.getName() : null,
                    client != null ? client.getTelefone() : null
            );
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(orderDtos);
    }
}