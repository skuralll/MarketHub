package com.skuralll.markethub

import org.bukkit.plugin.java.JavaPlugin

class MarketHub : JavaPlugin() {
    override fun onEnable() {
        // Plugin startup logic
        logger.info("MarketHub enabled")
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
