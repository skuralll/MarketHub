plugins {
    kotlin("jvm") version "1.9.22"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.skuralll"
version = "0.1"

repositories {
    mavenCentral()
    maven("https://papermc.io/repo/repository/maven-public/")
    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://repo.xenondevs.xyz/releases")
    maven("https://jitpack.io")
}

val exposedVersion: String by project
dependencies {
    implementation(kotlin("stdlib"))
    compileOnly("io.papermc.paper:paper-api:1.20.4-R0.1-SNAPSHOT")
    // Command API
    implementation("dev.jorel:commandapi-bukkit-kotlin:9.3.0")
    compileOnly("dev.jorel:commandapi-bukkit-core:9.3.0")
    // InventoryUI API
    implementation("xyz.xenondevs.invui:invui:1.26")
    implementation("xyz.xenondevs.invui:invui-kotlin:1.26")
    // Exposed (ORM)
    implementation("org.jetbrains.exposed:exposed-core:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")
    implementation("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
    // MySQL Connector
    implementation("mysql:mysql-connector-java:8.0.33")
    // Coroutines
    implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-api:2.15.0")
    implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:2.15.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")
    // Vault API
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    // Config API
    implementation("de.exlll:configlib-yaml:4.5.0")
}

java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

tasks {

    javadoc {
        options.encoding = "UTF-8"
    }

    compileJava {
        options.encoding = "UTF-8"
    }

    compileKotlin {
        kotlinOptions.jvmTarget = "17"
    }

    processResources {
        filesMatching("*.yml") {
            expand(project.properties)
        }
    }

    create<Jar>("sourceJar") {
        archiveClassifier.set("source")
        from(sourceSets["main"].allSource)
    }

    shadowJar {
        archiveBaseName.set("MarketHub")
        archiveClassifier.set("")
    }
}