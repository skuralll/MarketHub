package com.skuralll.markethub.command;

public object CommandRegister {

    private var commands = mutableListOf<Command>()

    init {
        commands.add(TestCommand())
    }

    fun registerCommands() {
        commands.forEach { it.register() }
    }

}
