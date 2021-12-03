plugins {
    kotlin("jvm") version "1.6.0"
}

repositories {
    mavenCentral()
}

dependencies {
    val multikVersion = "0.1.1"
    
    implementation("org.jetbrains.kotlinx:multik-api:$multikVersion")
    implementation("org.jetbrains.kotlinx:multik-default:$multikVersion")
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "7.3"
    }
}
