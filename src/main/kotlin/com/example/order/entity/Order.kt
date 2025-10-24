package com.example.order.entity

import org.slf4j.LoggerFactory

data class Order private constructor(
    val id: String,
    val customerId: String,
    val items: List<String>
) {
    companion object {
        private val logger = LoggerFactory.getLogger(Order::class.java)

        fun create(id: String, customerId: String, items: List<String>, validCustomers: Set<String>): Result<Order> {
            logger.info("Criando pedido id={} para cliente={} com {} item(ns)", id, customerId, items.size)
            if (customerId !in validCustomers) {
                logger.warn("Falha ao criar pedido: cliente {} não existe", customerId)
                return Result.failure(IllegalArgumentException("Cliente $customerId não existe"))
            }
            if (items.isEmpty()) {
                logger.warn("Falha ao criar pedido: lista de itens vazia")
                return Result.failure(IllegalArgumentException("O pedido deve ter pelo menos 1 item"))
            }

            logger.debug("Pedido {} criado com sucesso para cliente {}", id, customerId)
            return Result.success(Order(id, customerId, items))
        }
    }
}
