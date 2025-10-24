package com.example.order.stub

import com.example.order.entity.Order
import java.util.UUID

object OrderStub {
    val items = mutableListOf("item-1", "item-2", "item-3")
    fun stubFor(): Order {
        val order = UUID.randomUUID().toString()
        val customerId = "ORDER_ID:${order}:${UUID.randomUUID()}"
        return Order.create(order, customerId, items, setOf(customerId))
            .getOrThrow()
    }
}