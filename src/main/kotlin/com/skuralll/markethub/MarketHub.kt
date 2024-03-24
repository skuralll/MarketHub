package com.skuralll.markethub

import com.skuralll.markethub.command.CommandRegister
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.kotlindsl.commandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor
import org.bukkit.plugin.java.JavaPlugin

class MarketHub : JavaPlugin() {
    override fun onEnable() {
        CommandAPI.onEnable()
        CommandRegister.registerCommands()
    }

    override fun onDisable() {
        CommandAPI.onDisable()
    }
}
