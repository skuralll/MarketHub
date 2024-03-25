package com.skuralll.markethub.gui

import org.bukkit.entity.Player

abstract class GUI(protected val player: Player) {
    abstract fun open()
}