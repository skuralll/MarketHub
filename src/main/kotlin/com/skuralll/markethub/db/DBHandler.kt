package com.skuralll.markethub.db

import com.skuralll.markethub.ItemSerializer
import com.skuralll.markethub.MarketHub
import com.skuralll.markethub.db.tables.MetaTable
import com.skuralll.markethub.db.tables.ProductsTable
import kotlinx.coroutines.delay
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

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

}