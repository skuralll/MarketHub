package com.skuralll.markethub.command

import com.github.shynixn.mccoroutine.bukkit.launch
import com.skuralll.markethub.ItemSerializer
import com.skuralll.markethub.Market
import com.skuralll.markethub.MarketHub
import com.skuralll.markethub.db.DBHandler
import com.skuralll.markethub.gui.MainMenuGUI
import dev.jorel.commandapi.kotlindsl.commandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType


class TestCommand(plugin: MarketHub) : Command(plugin) {
    override fun register() {
        commandAPICommand("mtest") {
            playerExecutor { player, _ ->
                plugin.launch {
                    for (i in 1..30) {
                        val material = Material.entries.toTypedArray().random()
                        if (material.isItem) {
                            val item = ItemStack(material, 1)
                            withContext(Dispatchers.IO) {
                                DBHandler.addProduct(player, item, 1)
                            }
                        }
                    }
                }
            }
        }
    }
}