package com.skuralll.markethub.db

import com.skuralll.markethub.MarketHub
import com.skuralll.markethub.db.tables.MetaTable
import com.skuralll.markethub.db.tables.ProductsTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.transactions.transaction

object DBHandler {

    private lateinit var plugin: MarketHub

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

}