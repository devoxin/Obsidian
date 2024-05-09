rootProject.name = "obsidian"

include("common")
include("plugin")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            version("lavaplayer-v1", "1.5.3")
            library("lavaplayer-v1", "dev.arbjerg", "lavaplayer").versionRef("lavaplayer-v1")

            version("lavalink", "3.7.11")
            library("lavalink-server", "dev.arbjerg.lavalink", "Lavalink-Server").versionRef("lavalink")

            library("guava", "com.google.guava", "guava").version("33.2.0-jre")
            library("slf4j", "org.slf4j", "slf4j-api").version("1.7.25")
            library("annotations", "org.jetbrains", "annotations").version("24.1.0")

            plugin("lavalink-gradle-plugin", "dev.arbjerg.lavalink.gradle-plugin").version("1.0.15")
        }
    }
}
