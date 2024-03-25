package com.skuralll.markethub.gui

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Color
import org.bukkit.entity.Player
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.window.Window

class MainMenuGUI(player: Player) : GUI(player) {
    override fun open() {
        val gui = Gui.normal().setStructure(
            ". . . . . . . . .",
            ". . . . . . . . .",
            ". . . . . . . . ."
        ).build()
        val window =
            Window.single()
                .setTitle(
                    AdventureComponentWrapper(
                        Component.text("MarketHub").decorate(TextDecoration.BOLD)
                    )
                )
                .setGui(gui)
                .setViewer(player).build()
        window.open()
    }
}