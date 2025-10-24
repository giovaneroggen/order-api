package com.example.order.request

data class OrderRequest(val id: String,
                        val customerId: String,
                        val items: List<String>)