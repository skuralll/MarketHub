package com.skuralll.markethub.gui

import com.github.shynixn.mccoroutine.bukkit.launch
import com.skuralll.markethub.MarketHub
import com.skuralll.markethub.db.tables.Product
import com.skuralll.markethub.gui.items.BackItem
import com.skuralll.markethub.gui.items.ForwardItem
import com.skuralll.markethub.gui.items.ProductAsyncItem
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
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

abstract class ItemListGUI(protected val plugin: MarketHub, player: Player) : GUI(player) {

    open val title: String = "title"
    open val extraLore: List<TextComponent> = listOf()
    open val onShiftClick: (Player, Product) -> Unit = { _, _ -> }

    open suspend fun getOnClick(): (ClickType, Player, InventoryClickEvent, Product) -> Unit {
        return { clickType: ClickType, player: Player, event: InventoryClickEvent, product: Product ->
            if (clickType.isShiftClick) {
                onShiftClick(player, product)
            }
        }
    }

    open suspend fun getProducts(): List<Product> {
        return listOf()
    }

    suspend fun getItems(): List<ProductAsyncItem> {
        return getProducts().map {
            ProductAsyncItem(it, true, extraLore, getOnClick())
        }
    }

    override fun open() {
        plugin.launch {
            // border item
            val border =
                SimpleItem(ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("§r"))

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
                .setContent(getItems())
                .build()

            // open gui
            val window =
                Window.single()
                    .setTitle(
                        AdventureComponentWrapper(
                            Component.text(title).decorate(TextDecoration.BOLD)
                        )
                    )
                    .setGui(gui)
                    .setViewer(player).build()
            window.open()
        }
    }
}