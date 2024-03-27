package com.skuralll.markethub

import com.skuralll.markethub.db.DBHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player

object Market {

    private lateinit var plugin: MarketHub
    private lateinit var economy: Economy

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

    suspend fun sell(player: Player, price: Int) {
        val item = player.inventory.itemInMainHand
        if (item.type == Material.AIR) {
            player.sendMessage("アイテムを持ってください")
            return
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