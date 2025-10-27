# API Orders

**Version:** 0.0.1-SNAPSHOT
**Last Updated:** 2025-10-24 12:44:25 -0300

A API Orders permite criar pedidos com itens para clientes válidos. Possui autenticação básica (Basic Auth) e controle de acesso por roles.

---

## Table of Contents

* [Order Request](#order-request)
* [Order Response](#order-response)
* [Create Order - Success](#create-order---success)
* [Create Order - Invalid Customer](#create-order---invalid-customer)
* [Create Order - Empty Items](#create-order---empty-items)
* [Authentication & Security](#authentication--security)
* [Valid Customers](#valid-customers)

---

## Order Request

| Path       | Type   | Description                        |
| ---------- | ------ | ---------------------------------- |
| id         | String | ID único do pedido                 |
| customerId | String | ID do cliente que realiza o pedido |
| items      | Array  | Lista de itens do pedido           |

### Example

```json
{
  "id": "cb278dd0-dcd2-4920-ada4-3b0eff5e7879",
  "customerId": "customer-1",
  "items": ["item-1", "item-2", "item-3"]
}
```

---

## Order Response

| Path       | Type   | Description              |
| ---------- | ------ | ------------------------ |
| id         | String | ID único do pedido       |
| customerId | String | ID do cliente            |
| items      | Array  | Lista de itens do pedido |

---

## Create Order - Success

```bash
$ curl 'http://localhost:8080/orders' -i -X PUT \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -u user:password \
    -d '{
  "id": "cb278dd0-dcd2-4920-ada4-3b0eff5e7879",
  "customerId": "customer-1",
  "items": ["item-1", "item-2", "item-3"]
}'
```

**Response:** `HTTP 200 OK`

---

## Create Order - Invalid Customer

```bash
$ curl 'http://localhost:8080/orders' -i -X PUT \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -u user:password \
    -d '{
  "id": "cb278dd0-dcd2-4920-ada4-3b0eff5e7879",
  "customerId": "invalid-customer",
  "items": ["item-1", "item-2", "item-3"]
}'
```

**Response:** `HTTP 400 Bad Request`
**Body:** `Cliente e0c8db1c-ce25-4476-b268-417439a5e022 não existe`

---

## Create Order - Empty Items

```bash
$ curl 'http://localhost:8080/orders' -i -X PUT \
    -H 'Content-Type: application/json;charset=UTF-8' \
    -u user:password \
    -d '{
  "id": "cb278dd0-dcd2-4920-ada4-3b0eff5e7879",
  "customerId": "customer-1",
  "items": []
}'
```

**Response:** `HTTP 400 Bad Request`
**Body:** `Lista de itens não pode estar vazia`

---

## Authentication & Security

A API utiliza **Basic Auth**. Para criar um pedido, o usuário precisa ter a role `USER`.

```kotlin
@Bean
fun userDetailsService(): UserDetailsService {
    val user = User.withUsername("user")
        .password(passwordEncoder().encode("password"))
        .roles("USER")
        .build()
    val admin = User.withUsername("admin")
        .password(passwordEncoder().encode("adminpass"))
        .roles("ADMIN", "USER")
        .build()
    return InMemoryUserDetailsManager(user, admin)
}
```

---

## Valid Customers

* `customer-1`
* `customer-2`
* `customer-3`

---

## Notes

* Todos os requests devem incluir header `Content-Type: application/json`.
* Certifique-se de usar credenciais válidas para autenticação.
* IDs de pedidos devem ser únicos para cada requisição.
