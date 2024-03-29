package com.skuralll.markethub.gui

import com.github.shynixn.mccoroutine.bukkit.launch
import com.skuralll.markethub.Market
import com.skuralll.markethub.MarketHub
import com.skuralll.markethub.gui.items.ProductAsyncItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper
import xyz.xenondevs.invui.gui.PagedGui
import xyz.xenondevs.invui.gui.structure.Markers
import xyz.xenondevs.invui.window.Window

class MyItemsGUI(private val plugin: MarketHub, player: Player) : GUI(player) {
    override fun open() {
        plugin.launch {
            // create items
            val items = Market.getSellerProducts(player).map {
                ProductAsyncItem(it, false, listOf())
            }

            // create gui
            val gui = PagedGui.items().setStructure(
                "x x x x x x x x x",
                "x x x x x x x x x",
                "x x x x x x x x x",
                "x x x x x x x x x",
                "x x x x x x x x x",
                ". . . . . . . . ."
            ).addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .setContent(items)
                .build()

            // open gui
            val window =
                Window.single()
                    .setTitle(
                        AdventureComponentWrapper(
                            Component.text("出品中のアイテム").decorate(TextDecoration.BOLD)
                        )
                    )
                    .setGui(gui)
                    .setViewer(player).build()
            window.open()
        }
    }
}