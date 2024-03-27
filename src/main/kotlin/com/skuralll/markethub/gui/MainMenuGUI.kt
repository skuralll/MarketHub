package com.skuralll.markethub.gui

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper
import xyz.xenondevs.invui.gui.Gui
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.AbstractItem
import xyz.xenondevs.invui.item.impl.CommandItem
import xyz.xenondevs.invui.item.impl.SimpleItem
import xyz.xenondevs.invui.window.Window

class MainMenuGUI(player: Player) : GUI(player) {
    override fun open() {

        // sell item
        val sellItem = object : AbstractItem() {
            override fun getItemProvider(): ItemProvider {
                return ItemBuilder(Material.HEART_OF_THE_SEA).setDisplayName("アイテムを売る")
            }

            override fun handleClick(p0: ClickType, p1: Player, p2: InventoryClickEvent) {
                player.sendMessage("アイテムを手に持って以下のコマンドを実行してください")
                player.sendMessage("/mhub sell <価格>")
                player.closeInventory()
            }
        }

        val gui = Gui.normal().setStructure(
            ". . . . . . . . .",
            ". . . . s . . . .",
            ". . . . . . . . ."
        ).addIngredient(
            's',
            sellItem
        )
            .build()

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