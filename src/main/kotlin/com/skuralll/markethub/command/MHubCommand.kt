package com.skuralll.markethub.command

import com.github.shynixn.mccoroutine.bukkit.launch
import com.skuralll.markethub.ItemSerializer
import com.skuralll.markethub.Market
import com.skuralll.markethub.MarketHub
import com.skuralll.markethub.gui.MainMenuGUI
import com.skuralll.markethub.gui.MyItemsGUI
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

    private val sell = subcommand("sell") {
        integerArgument("価格", MIN_PRICE, MAX_PRICE)
        playerExecutor { player, args ->
            val price = args[0] as Int
            Market.sell(player, price)
        }
    }

    private val own = subcommand("own") {
        playerExecutor { player, _ ->
            MyItemsGUI(plugin, player).open()
        }
    }

    private val category = subcommand("list") {
        playerExecutor { player, _ ->
            MyItemsGUI(plugin, player).open()
        }
    }

    override fun register() {
        commandAPICommand("mhub") {
            subcommand(sell)
            subcommand(own)
            playerExecutor { player, _ ->
                MainMenuGUI(plugin, player).open()
            }
        }
    }
}