plugins {
    `java-library`
    alias(libs.plugins.lavalink.gradle.plugin)
}

lavalinkPlugin {
    name = "obsidian-plugin"
    path = "me.devoxin.obsidian.plugin"
    apiVersion = libs.versions.lavalink
    serverVersion = "4.0.4"
    configurePublishing = false
}

base {
    archivesName = "obsidian-plugin"
}

dependencies {
    implementation(projects.common)
    compileOnly(libs.lavalink.server)
    compileOnly(libs.slf4j)
    compileOnly(libs.annotations)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
