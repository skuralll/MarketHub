package com.skuralll.markethub.command;

import com.skuralll.markethub.MarketHub

public object CommandRegister {

    private var commands = mutableListOf<Command>()

    fun registerCommands(plugin: MarketHub) {
        commands.add(TestCommand(plugin))
        commands.add(MHubCommand(plugin))
        commands.forEach { it.register() }
    }

}
