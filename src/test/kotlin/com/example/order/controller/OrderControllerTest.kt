package com.example.order.controller

import com.example.order.stub.OrderRequestStub
import com.example.order.stub.OrderRequestStub.invalidCustomer
import com.example.order.stub.OrderRequestStub.validCustomer
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseBody
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "build/generated-snippets")
class OrderControllerTest(
    @Autowired private val mockMvc: MockMvc,
    @Autowired private val objectMapper: ObjectMapper
) {

    @Test
    @WithMockUser
    fun `create order success`() {
        val request = OrderRequestStub.stubFor(validCustomer)
        mockMvc.perform(
            put("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk)
        .andExpect(content().json(objectMapper.writeValueAsString(request)))
        .andDo(document("create-order-success",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("id").description("ID único do pedido"),
                fieldWithPath("customerId").description("ID do cliente que realiza o pedido"),
                fieldWithPath("items").description("Lista de itens do pedido")
            ),
            responseFields(
                fieldWithPath("id").description("ID único do pedido"),
                fieldWithPath("customerId").description("ID do cliente que realiza o pedido"),
                fieldWithPath("items").description("Lista de itens do pedido")
            )
        ))
    }

    @Test
    @WithMockUser
    fun `create order error - invalid customer`() {
        val request = OrderRequestStub.stubFor(invalidCustomer)
        mockMvc.perform(
            put("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isBadRequest)
        .andExpect(content().string("Cliente ${request.customerId} não existe"))
        .andDo(document("create-order-error-invalid-customer",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("id").description("ID único do pedido"),
                fieldWithPath("customerId").description("ID do cliente que realiza o pedido"),
                fieldWithPath("items").description("Lista de itens do pedido")
            ),
            responseBody()
        ))
    }

    @Test
    @WithMockUser
    fun `create order error - empty items`() {
        val request = OrderRequestStub.stubFor(validCustomer).copy(items = emptyList())
        mockMvc.perform(
            put("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isBadRequest)
        .andExpect(content().string("O pedido deve ter pelo menos 1 item"))
        .andDo(document("create-order-error-empty-items",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            requestFields(
                fieldWithPath("id").description("ID único do pedido"),
                fieldWithPath("customerId").description("ID do cliente que realiza o pedido"),
                fieldWithPath("items").description("Lista de itens do pedido")
            ),
            responseBody()
        ))
    }
}