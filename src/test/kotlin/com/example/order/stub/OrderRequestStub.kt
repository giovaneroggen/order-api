package com.example.order.stub

import com.example.order.request.OrderRequest
import java.util.UUID

object OrderRequestStub {
    val validCustomer = "customer-1";
    val invalidCustomer = UUID.randomUUID().toString();
    val items = mutableListOf("item-1", "item-2", "item-3")
    fun stubFor(customerId: String): OrderRequest {
        return OrderRequest(UUID.randomUUID().toString(), customerId, items)
    }
}
