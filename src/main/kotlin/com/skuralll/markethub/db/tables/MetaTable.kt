package com.skuralll.markethub.db.tables

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

object MetaTable : Table("meta") {
    val key = varchar("key", 32)
    val value = text("value")
    override val primaryKey = PrimaryKey(key)
}