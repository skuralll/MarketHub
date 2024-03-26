package com.skuralll.markethub.db.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object ProductsTable : Table("products") {
    val id = integer("id").autoIncrement()
    val seller_id = varchar("seller_id", 32)
    val seller_name = varchar("seller_name", 16)
    val quantity = integer("quantity")
    val item_stack = text("item_stack")
    val price = integer("price")
    val created_at = datetime("created_at")
    val expired_at = datetime("expired_at")
    override val primaryKey = PrimaryKey(id)
}