package com.example.order.service

import com.example.order.repository.OrderRepository
import com.example.order.stub.OrderRequestStub
import com.example.order.stub.OrderRequestStub.invalidCustomer
import com.example.order.stub.OrderRequestStub.validCustomer
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.UUID

class OrderServiceTest {

    val service = OrderService(OrderRepository())

    @Test
    fun createOrderSuccess(){
        val order = service.createOrder(OrderRequestStub.stubFor(validCustomer))
        assertNotNull(order)
    }

    @Test
    fun createOrderErrorInvalidCustomer(){
        val exception = assertThrows(IllegalArgumentException::class.java){
            service.createOrder(OrderRequestStub.stubFor(invalidCustomer))
        }
        assertNotNull(exception)
        assertEquals("Cliente $invalidCustomer não existe", exception.message)
    }

    @Test
    fun createOrderErrorInvalidItemsSize(){
        val request = OrderRequestStub.stubFor(validCustomer)
            .copy(items = emptyList())
        val exception = assertThrows(IllegalArgumentException::class.java){
            service.createOrder(request)
        }
        assertNotNull(exception)
        assertEquals("O pedido deve ter pelo menos 1 item", exception.message)
    }
}