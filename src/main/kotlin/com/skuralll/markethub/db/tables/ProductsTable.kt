package com.skuralll.markethub.db.tables

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object ProductsTable : Table("products") {
    val id = integer("id").autoIncrement()
    val seller_id = varchar("seller_id", 36)
    val seller_name = varchar("seller_name", 16)
    val quantity = integer("quantity")
    val item_stack = text("item_stack")
    val price = integer("price")
    val created_at = datetime("created_at")
    val expired_at = datetime("expired_at")
    override val primaryKey = PrimaryKey(id)
}

fun ResultRow.toProduct(): Product =
    Product(
        this[ProductsTable.id],
        this[ProductsTable.seller_id],
        this[ProductsTable.seller_name],
        this[ProductsTable.quantity],
        this[ProductsTable.item_stack],
        this[ProductsTable.price],
        this[ProductsTable.created_at],
        this[ProductsTable.expired_at]
    )

data class Product(
    val id: Int,
    val sellerId: String,
    val sellerName: String,
    val quantity: Int,
    val itemStack: String,
    val price: Int,
    val createdAt: java.time.LocalDateTime,
    val expiredAt: java.time.LocalDateTime
)