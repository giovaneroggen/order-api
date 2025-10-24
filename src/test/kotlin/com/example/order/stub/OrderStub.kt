package com.example.order.stub

import com.example.order.entity.Order
import java.util.UUID

object OrderStub {
    val items = mutableListOf("item-1", "item-2", "item-3")
    fun stubFor(): Order {
        val customerId = UUID.randomUUID()
        return Order("$customerId", "ORDER_ID:${customerId}:${UUID.randomUUID()}", items)
    }
}