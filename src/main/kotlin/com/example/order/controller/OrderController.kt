package com.example.order.controller

import com.example.order.request.OrderRequest
import com.example.order.service.OrderService
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/orders")
class OrderController(val service: OrderService) {

    @PutMapping
    fun createOrder(@RequestBody request: OrderRequest) =
        service.createOrder(request)
}