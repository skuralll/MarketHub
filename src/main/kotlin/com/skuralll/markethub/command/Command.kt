package com.skuralll.markethub.command

import com.skuralll.markethub.MarketHub

abstract class Command(protected val plugin: MarketHub) {
    abstract fun register()
}