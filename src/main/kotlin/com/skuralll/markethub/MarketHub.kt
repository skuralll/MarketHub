package com.skuralll.markethub

import com.skuralll.markethub.command.CommandRegister
import com.skuralll.markethub.db.DBHandler
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import org.bukkit.plugin.java.JavaPlugin

class MarketHub : JavaPlugin() {

    override fun onLoad() {
        CommandAPI.onLoad(CommandAPIBukkitConfig(this))
    }

    override fun onEnable() {
        // Register commands
        CommandAPI.onEnable()
        CommandRegister.registerCommands(this)
        // DB init
        DBHandler.connect(this)
    }

    override fun onDisable() {
        CommandAPI.onDisable()
    }
}
