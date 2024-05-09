plugins {
    `java-library`
}

base {
    archivesName = "obsidian-common"
}

dependencies {
    // can't believe I'm importing Guava.
    // It's not a bad library, but I only need it for domain name parsing.
    implementation(libs.guava)
    compileOnly(libs.lavaplayer.v1)
    compileOnly(libs.slf4j)
    compileOnly(libs.annotations)

    testImplementation(libs.slf4j)
}
