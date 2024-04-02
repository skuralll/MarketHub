package com.skuralll.markethub.config

import de.exlll.configlib.Configuration

@JvmRecord
data class MySQLConfig(val user: String, val password: String)

@Configuration
class BaseConfig {
    var version: Int = 1
    var mysql: MySQLConfig = MySQLConfig("root", "password")
}