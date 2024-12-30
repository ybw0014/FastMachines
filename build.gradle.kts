import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.1.0"
    id("com.gradleup.shadow") version "8.3.5"
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://s01.oss.sonatype.org/content/groups/public/")
    maven("https://repo.alessiodp.com/releases/")
    maven("https://jitpack.io")
}

dependencies {
    fun compileOnlyAndTestImpl(dependencyNotation: Any) {
        compileOnly(dependencyNotation)
        testImplementation(dependencyNotation)
    }

    compileOnly(kotlin("stdlib")) // loaded through library loader
    compileOnly(kotlin("reflect")) // loaded through library loader
    compileOnlyAndTestImpl("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    compileOnlyAndTestImpl("com.github.Slimefun:Slimefun4:d12ae8580b")
    compileOnly("net.guizhanss:SlimefunTranslation:e03b01a7b7")
    compileOnly("com.github.schntgaispock:SlimeHUD:1.3.0")
    compileOnly("com.github.SlimefunGuguProject:InfinityExpansion:bebf0bd0f9")
    compileOnly("com.github.VoperAD:SlimeFrame:8af2379a01")
    implementation("net.guizhanss:guizhanlib-all:2.2.0-SNAPSHOT")
    implementation("org.bstats:bstats-bukkit:3.1.0")
    implementation("io.github.seggan:sf4k:0.8.0") {
        exclude(group = "org.jetbrains.kotlin")
        exclude(group = "com.github.Slimefun")
    }

    testImplementation(kotlin("test"))
    testImplementation("org.mockbukkit.mockbukkit:mockbukkit-v1.21:4.10.0")
}

group = "net.guizhanss"
description = "FastMachines"

val mainPackage = "net.guizhanss.fastmachines"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    compilerOptions {
        javaParameters = true
        jvmTarget = JvmTarget.JVM_21
    }
}

tasks.shadowJar {
    fun doRelocate(from: String, to: String? = null) {
        val last = to ?: from.split(".").last()
        relocate(from, "$mainPackage.libs.$last")
    }

    doRelocate("net.byteflux.libby")
    doRelocate("net.guizhanss.guizhanlib")
    doRelocate("org.bstats")
    doRelocate("io.github.seggan.sf4k")
    doRelocate("io.papermc.lib", "paperlib")
    minimize()
    archiveClassifier = ""
}

bukkit {
    main = "$mainPackage.FastMachines"
    apiVersion = "1.18"
    authors = listOf("ybw0014")
    description = "More Slimefun machines that bulk craft items with all shapeless recipes"
    depend = listOf("Slimefun")
    softDepend = listOf(
        "GuizhanLibPlugin",
        "SlimefunTranslation",
        "InfinityExpansion",
        "SlimeFrame",
        "SlimeHUD",
        "InfinityExpansion2",
    )
    loadBefore = listOf(
        "SlimeCustomizer",
        "RykenSlimeCustomizer",
        "SlimeFunRecipe"
    )
}

tasks.runServer {
    downloadPlugins {
        // Slimefun
        url("https://blob.build/dl/Slimefun4/Dev/latest")
        // SlimeHUD
        url("https://blob.build/dl/SlimeHUD/Dev/latest")
        // GuizhanCraft for testing convenient
        url("https://builds.guizhanss.com/api/download/ybw0014/GuizhanCraft/master/latest")
    }
    jvmArgs("-Dcom.mojang.eula.agree=true")
    minecraftVersion("1.20.6")
}

tasks.test {
    useJUnitPlatform()
}
