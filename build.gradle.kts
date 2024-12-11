plugins {
    kotlin("jvm") version "1.9.21"
}

sourceSets {
    main {
        kotlin.srcDir("src")
    }
}

tasks {
    wrapper {
        gradleVersion = "8.5"
    }
}

dependencies {
    implementation("com.fasterxml.jackson.core:jackson-databind:2.0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
}