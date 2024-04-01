package com.skuralll.markethub.db

import com.skuralll.markethub.ItemSerializer
import com.skuralll.markethub.MarketHub
import com.skuralll.markethub.db.tables.MetaTable
import com.skuralll.markethub.db.tables.Product
import com.skuralll.markethub.db.tables.ProductsTable
import com.skuralll.markethub.db.tables.toProduct
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

object DBHandler {

    const val EXPIRATION_DAYS = 7

    private lateinit var plugin: MarketHub

    // DB setups

    fun connect(plugin: MarketHub) {
        this.plugin = plugin
        // connection
        Database.connect(
            "jdbc:mysql://mysql/markethub",
            driver = "com.mysql.jdbc.Driver",
            user = "root",
            password = "mysql"
        )
        createTables()
    }

    fun createTables() {
        transaction {
            create(MetaTable, ProductsTable)
        }
    }

    // DB operations

    fun addProduct(player: Player, item: ItemStack, price: Int): Boolean {
        try {
            if (item.type.isAir) {
                return false
            }
            transaction {
                ProductsTable.insert {
                    it[ProductsTable.seller_id] = player.uniqueId.toString()
                    it[ProductsTable.seller_name] = player.name
                    it[ProductsTable.quantity] = item.amount
                    it[ProductsTable.item_stack] = ItemSerializer.itemTo64(item)
                    it[ProductsTable.price] = price
                    it[ProductsTable.created_at] = java.time.LocalDateTime.now()
                    it[ProductsTable.expired_at] =
                        java.time.LocalDateTime.now().plusDays(EXPIRATION_DAYS.toLong())
                }
            }
            return true
        } catch (e: Exception) {
            plugin.logger.warning("アイテムの出品に失敗しました")
            e.printStackTrace()
            return false
        }
    }

    // get all products
    fun getAllProducts(): List<Product> {
        return transaction {
            ProductsTable.selectAll().map {
                it.toProduct()
            }
        }
    }

    // get seller products by player id
    fun getSellerProducts(sellerId: UUID): List<Product> {
        val sellerIdStr = sellerId.toString()
        return transaction {
            ProductsTable.select {
                ProductsTable.seller_id eq sellerIdStr
            }.map {
                it.toProduct()
            }
        }
    }

    // get seller products by player
    fun getSellerProducts(seller: Player): List<Product> {
        return getSellerProducts(seller.uniqueId)
    }

    // delete product from product id
    fun deleteProduct(productId: Int): Boolean {
        try {
            val result = transaction {
                return@transaction ProductsTable.deleteWhere {
                    ProductsTable.id eq productId
                } > 0
            }
            return result
        } catch (e: Exception) {
            plugin.logger.warning("アイテムの取り下げに失敗しました")
            e.printStackTrace()
            return false
        }
    }

}