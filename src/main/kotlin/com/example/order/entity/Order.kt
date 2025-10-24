package com.example.order.entity

data class Order private constructor(
    val id: String,
    val customerId: String,
    val items: List<String>
) {
    companion object {
        fun create(id: String, customerId: String, items: List<String>, validCustomers: Set<String>): Result<Order> {
            if (customerId in validCustomers) {
                return Result.failure(IllegalArgumentException("Cliente ${customerId} não existe"))
            }
            if (items.isNotEmpty()) {
                return Result.failure(IllegalArgumentException("O pedido deve ter pelo menos 1 item"))
            }
            return Result.success(Order(id, customerId, items))
        }
    }
}
