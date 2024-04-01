package com.skuralll.markethub

import com.github.shynixn.mccoroutine.bukkit.launch
import com.skuralll.markethub.db.DBHandler
import com.skuralll.markethub.db.tables.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.UUID

object Market {

    private lateinit var plugin: MarketHub
    public lateinit var economy: Economy

    fun open(plugin: MarketHub) {
        this.plugin = plugin
        // Vault
        plugin.server.servicesManager.getRegistration(Economy::class.java)?.let {
            economy = it.provider
        } ?: let {
            plugin.logger.warning("Vaultが見つかりません")
            plugin.server.pluginManager.disablePlugin(plugin)
        }
    }

    fun sell(player: Player, price: Int) {
        plugin.launch {
            val item = player.inventory.itemInMainHand
            if (item.type == Material.AIR) {
                player.sendMessage("アイテムを持ってください")
                return@launch
            }
            val result = withContext(Dispatchers.IO) {
                DBHandler.addProduct(player, item, price)
            }
            if (result) {
                player.inventory.setItemInMainHand(null)
                player.sendMessage("アイテムを出品しました")
            } else {
                player.sendMessage("アイテムの出品に失敗しました")
            }
        }
    }

    suspend fun buy(player: Player, product: Product) {
        if (!economy.has(player, product.price.toDouble())) {
            player.sendMessage("お金が足りません")
            return
        }
        val inventory = player.inventory
        if (inventory.firstEmpty() == -1) {
            player.sendMessage("インベントリがいっぱいです")
            return
        }
        val result = withContext(Dispatchers.IO) {
            DBHandler.deleteProduct(product.id)
        }
        if (result) {
            economy.withdrawPlayer(player, product.price.toDouble())
            economy.depositPlayer(
                Bukkit.getOfflinePlayer(UUID.fromString(product.sellerId)),
                product.price.toDouble()
            )
            inventory.addItem(ItemSerializer.itemFrom64(product.itemStack))
            player.sendMessage("アイテムを購入しました")
        } else {
            player.sendMessage("アイテムの購入に失敗しました")
        }
    }

    suspend fun getAllProducts(): List<Product> {
        return withContext(Dispatchers.IO) {
            DBHandler.getAllProducts()
        }
    }

    suspend fun getSellerProducts(player: Player): List<Product> {
        return withContext(Dispatchers.IO) {
            DBHandler.getSellerProducts(player)
        }
    }

    suspend fun returnProduct(player: Player, product: Product) {
        val inventory = player.inventory
        if (inventory.firstEmpty() == -1) {
            player.sendMessage("インベントリがいっぱいです")
            return
        }
        val result = withContext(Dispatchers.IO) {
            DBHandler.deleteProduct(product.id)
        }
        if (result) {
            inventory.addItem(ItemSerializer.itemFrom64(product.itemStack))
            player.sendMessage("アイテムを取り下げました")
        } else {
            player.sendMessage("アイテムの取り下げに失敗しました")
        }
    }


}