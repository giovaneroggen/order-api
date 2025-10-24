package com.example.order.repository

import com.example.order.entity.Order
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap

@Repository
class OrderRepository {

    private val orders = ConcurrentHashMap<String, Order>()

    fun save(order: Order): Order {
        val compute = orders.compute(order.id) { key, value ->
            value ?: order
        }
        return compute!!
    }

    fun findById(id: String) = orders[id]

    fun findAll() = orders.values.toList()
}