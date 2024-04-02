package com.skuralll.markethub

import com.skuralll.markethub.command.CommandRegister
import com.skuralll.markethub.config.ConfigHandler
import com.skuralll.markethub.db.DBHandler
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import org.bukkit.plugin.java.JavaPlugin

class MarketHub : JavaPlugin() {

    override fun onLoad() {
        CommandAPI.onLoad(CommandAPIBukkitConfig(this))
    }

    override fun onEnable() {
        try {
            // Load config
            ConfigHandler.onEnable(this)
            // Register commands
            CommandAPI.onEnable()
            CommandRegister.registerCommands(this)
            // DB init
            DBHandler.connect(this)
            // Market init
            Market.open(this)
        } catch (e: Exception) {
            logger.warning("MarketHubの起動に失敗しました")
        }
    }

    override fun onDisable() {
        CommandAPI.onDisable()
    }
}
