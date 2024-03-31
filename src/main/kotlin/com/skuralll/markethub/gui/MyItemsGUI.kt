package com.skuralll.markethub.gui

import com.github.shynixn.mccoroutine.bukkit.launch
import com.skuralll.markethub.Market
import com.skuralll.markethub.MarketHub
import com.skuralll.markethub.db.tables.Product
import com.skuralll.markethub.gui.items.BackItem
import com.skuralll.markethub.gui.items.ForwardItem
import com.skuralll.markethub.gui.items.ProductAsyncItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.inventoryaccess.component.AdventureComponentWrapper
import xyz.xenondevs.invui.gui.PagedGui
import xyz.xenondevs.invui.gui.structure.Markers
import xyz.xenondevs.invui.item.builder.ItemBuilder
import xyz.xenondevs.invui.item.impl.SimpleItem
import xyz.xenondevs.invui.window.Window

class MyItemsGUI(private val plugin: MarketHub, player: Player) : GUI(player) {
    override fun open() {
        plugin.launch {
            // create items
            val extraLore = listOf(
                Component.text("Shift+クリックで取り下げる")
                    .decoration(TextDecoration.ITALIC, false)
                    .color(TextColor.color(255, 85, 85))
            )
            val onClick =
                { clickType: ClickType, player: Player, event: InventoryClickEvent, product: Product ->
                    if (clickType.isShiftClick) {
                        plugin.launch {
                            Market.returnProduct(player, product)
                            player.inventory.close()
                            MyItemsGUI(plugin, player).open()
                        }
                    }
                }
            val items = Market.getSellerProducts(player).map {
                ProductAsyncItem(it, false, extraLore, onClick)
            }

            val border =
                SimpleItem(ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("§r"))

            // create gui
            val gui = PagedGui.items().setStructure(
                "x x x x x x x x x",
                "x x x x x x x x x",
                "x x x x x x x x x",
                "x x x x x x x x x",
                "x x x x x x x x x",
                "< < < # # # > > >"
            ).addIngredient('x', Markers.CONTENT_LIST_SLOT_HORIZONTAL)
                .addIngredient('<', BackItem())
                .addIngredient('>', ForwardItem())
                .addIngredient('#', border)
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