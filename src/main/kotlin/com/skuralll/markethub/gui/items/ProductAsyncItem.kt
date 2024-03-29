package com.skuralll.markethub.gui.items

import com.skuralll.markethub.ItemSerializer
import com.skuralll.markethub.db.tables.Product
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import xyz.xenondevs.invui.item.ItemProvider
import xyz.xenondevs.invui.item.ItemWrapper
import xyz.xenondevs.invui.item.impl.AsyncItem
import java.time.format.DateTimeFormatter
import java.util.function.Supplier

private class ProductItemSupplier(private val product: Product, private val isOwner: Boolean, private val extraLore: List<Component>) :
    Supplier<ItemProvider> {
    override fun get(): ItemProvider {
        val item = ItemSerializer.itemFrom64(product.itemStack)
        // for show date
        val dtFormatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日")
        // generate product info lore
        val baseInfoLore = mutableListOf<Component>()
        baseInfoLore.add(Component.text(" "))
        if (isOwner) baseInfoLore.add(getLoreComponent("出品者: ${product.sellerName}"))
        baseInfoLore.add(getLoreComponent("出品日: ${product.createdAt.format(dtFormatter)}"))
        baseInfoLore.add(getLoreComponent("有効期限: ${product.expiredAt.format(dtFormatter)}"))
        baseInfoLore.add(Component.text(" "))
        // set lore
        item.lore()?.let {
            item.lore(it + baseInfoLore + extraLore)
        } ?: let {
            item.lore(baseInfoLore + extraLore)
        }
        return ItemWrapper(item)
    }

    private fun getLoreComponent(text: String): Component {
        return Component.text(text).decoration(TextDecoration.ITALIC, false).color(
            TextColor.color(255, 170, 0)
        )
    }
}

class ProductAsyncItem(product: Product, isOwner: Boolean, extraLore: List<Component> = listOf()) :
    AsyncItem(ProductItemSupplier(product, isOwner, extraLore)) {

    override fun handleClick(p0: ClickType, p1: Player, p2: InventoryClickEvent) {

    }

}