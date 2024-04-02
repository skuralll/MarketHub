package com.skuralll.markethub.config

import com.skuralll.markethub.MarketHub
import de.exlll.configlib.YamlConfigurations
import java.nio.file.Path
import java.nio.file.Paths
import kotlin.io.path.exists
import kotlin.io.path.notExists

object ConfigHandler {

    private lateinit var plugin: MarketHub

    private lateinit var configFile: Path
    var config: BaseConfig = BaseConfig()
        private set

    fun onEnable(plugin: MarketHub) {
        this.plugin = plugin
        configFile = Paths.get(plugin.dataFolder.toString(), "config.yml")
        if (configFile.notExists()) {
            config = BaseConfig()
            YamlConfigurations.save(configFile, BaseConfig::class.java, config)
        } else {
            load()
        }
    }

    private fun load() {
        config = YamlConfigurations.load(configFile, BaseConfig::class.java)
    }
}