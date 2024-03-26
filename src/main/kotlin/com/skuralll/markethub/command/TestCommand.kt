package com.skuralll.markethub.command

import com.skuralll.markethub.ItemSerializer
import com.skuralll.markethub.MarketHub
import dev.jorel.commandapi.kotlindsl.commandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType


class TestCommand(plugin: MarketHub) : Command(plugin) {
    override fun register() {
        commandAPICommand("mtest") {
            playerExecutor { player, _ ->
//                MainMenuGUI(player).open()
                val metaStr = player.inventory.itemInMainHand.itemMeta.serialize().toString()
                player.sendMessage(metaStr)
            }
        }
    }
}