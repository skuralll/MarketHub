package com.skuralll.markethub.gui

import com.github.shynixn.mccoroutine.bukkit.launch
import com.skuralll.markethub.Market
import com.skuralll.markethub.MarketHub
import com.skuralll.markethub.db.tables.Product
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player

class MyItemsGUI(plugin: MarketHub, player: Player) : AbstractItemListGUI(plugin, player) {

    override val title: String = "出品しているアイテム"
    override val extraLore: List<TextComponent> = listOf(
        Component.text("Shift+クリックで取り下げる")
            .decoration(TextDecoration.ITALIC, false)
            .color(TextColor.color(255, 85, 85))
    )
    override val onShiftClick: (Player, Product) -> Unit =
        { player, product -> plugin.launch { Market.returnProduct(player, product) } }

    override suspend fun getProducts(): List<Product> {
        return Market.getSellerProducts(player)
    }

}