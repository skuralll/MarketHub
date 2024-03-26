package com.skuralll.markethub

import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException


class ItemSerializer {
    companion object {
        @Throws(IllegalStateException::class)
        fun itemTo64(stack: ItemStack): String {
            try {
                val outputStream = ByteArrayOutputStream()
                val dataOutput = BukkitObjectOutputStream(outputStream)
                dataOutput.writeObject(stack)
                dataOutput.close()
                return Base64Coder.encodeLines(outputStream.toByteArray())
            } catch (e: Exception) {
                throw IllegalStateException("Unable to save item stack.", e)
            }
        }

        @Throws(IOException::class)
        fun itemFrom64(data: String): ItemStack {
            try {
                val inputStream = ByteArrayInputStream(Base64Coder.decodeLines(data))
                val dataInput = BukkitObjectInputStream(inputStream)
                try {
                    return dataInput.readObject() as ItemStack
                } finally {
                    dataInput.close()
                }
            } catch (e: ClassNotFoundException) {
                throw IOException("Unable to decode class type.", e)
            }
        }
    }
}