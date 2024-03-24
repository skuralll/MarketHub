package com.skuralll.markethub.command

import dev.jorel.commandapi.kotlindsl.commandAPICommand
import dev.jorel.commandapi.kotlindsl.playerExecutor

class TestCommand : Command {
    override fun register() {
        commandAPICommand("mtest") {
            playerExecutor { player, _ ->
                player.sendMessage("Hello, ${player.name}!")
            }
        }
    }
}