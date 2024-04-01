package com.skuralll.markethub.command

import com.skuralll.markethub.Market
import com.skuralll.markethub.MarketHub
import com.skuralll.markethub.gui.MainMenuGUI
import com.skuralll.markethub.gui.MyItemsGUI
import com.skuralll.markethub.gui.ItemListGUI
import dev.jorel.commandapi.kotlindsl.*


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
            ItemListGUI(plugin, player).open()
        }
    }

    override fun register() {
        commandAPICommand("mhub") {
            subcommand(sell)
            subcommand(own)
            subcommand(category)
            playerExecutor { player, _ ->
                MainMenuGUI(plugin, player).open()
            }
        }
    }
}