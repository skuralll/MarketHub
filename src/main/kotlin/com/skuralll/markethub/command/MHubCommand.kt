package com.skuralll.markethub.command

import com.github.shynixn.mccoroutine.bukkit.launch
import com.skuralll.markethub.ItemSerializer
import com.skuralll.markethub.Market
import com.skuralll.markethub.MarketHub
import com.skuralll.markethub.gui.MainMenuGUI
import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.*
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataType


class MHubCommand(plugin: MarketHub) : Command(plugin) {

    companion object {
        const val MIN_PRICE = 1
        const val MAX_PRICE = 1000000
    }

    val sell = subcommand("sell") {
        integerArgument("価格", MIN_PRICE, MAX_PRICE)
        playerExecutor { player, args ->
            val price = args[0] as Int
            Market.sell(player, price)
        }
    }

    override fun register() {
        commandAPICommand("mhub") {
            subcommand(sell)
            playerExecutor { player, _ ->
                MainMenuGUI(player).open()
            }
        }
    }
}