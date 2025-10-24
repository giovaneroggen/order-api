package com.example.order.repository

import com.example.order.stub.OrderStub
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class OrderRepositoryTest {

    private lateinit var repository: OrderRepository;

    @BeforeEach
    fun beforeEach(){
        repository = OrderRepository();
    }

    @Test
    fun save_success() {
        val order = OrderStub.stubFor()
        val (id, customerId, items) = repository.save(order);
        assertEquals(1, repository.findAll().size)
        assertEquals(id, order.id)
        assertEquals(customerId, order.customerId)
        assertEquals(items, order.items)
    }

    @Test
    fun save_only_one_order_concurrency() {
        val threads = 10
        val executor = Executors.newFixedThreadPool(threads)
        val latch = CountDownLatch(threads)

        val order = OrderStub.stubFor()

        repeat(threads) {
            executor.submit {
                try {
                    repository.save(order)
                } finally {
                    latch.countDown()
                }
            }
        }

        latch.await() // espera todas threads terminarem
        executor.shutdown()

        val allOrders = repository.findAll()
        assertEquals(1, allOrders.size)
        assertEquals(order, allOrders.first())
    }

    @Test
    fun findByIdNotFound() {
        assertNull(repository.findById("INVALID_ID"))
    }

    @Test
    fun findByIdSuccess() {
        val order = OrderStub.stubFor()
        repository.save(order)
        assertNotNull(repository.findById(order.id))
    }

    @Test
    fun findAllEmpty() {
        assertEquals(0, repository.findAll().size)
    }

    @Test
    fun findAllSuccess() {
        repeat(100){
            println(repository.save(OrderStub.stubFor()));
        }
        assertEquals(100, repository.findAll().size)
    }
}