package com.example.order.service

import com.example.order.entity.Order
import com.example.order.repository.OrderRepository
import com.example.order.request.OrderRequest
import org.springframework.stereotype.Service

@Service
class OrderService(private val repository: OrderRepository) {

    private val validCustomers = setOf("customer-1", "customer-2", "customer-3")

    fun createOrder(request: OrderRequest): Order {
        // Validação de cliente
        val order = Order.create(
            id = request.id,
            customerId = request.customerId,
            items = request.items,
            validCustomers = validCustomers
        ).getOrThrow()
        return repository.save(order)
    }
}