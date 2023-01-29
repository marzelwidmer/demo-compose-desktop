import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "ch.keepcalm"
version = "1.0-SNAPSHOT"
val ktor_version: String by project
val azuer_servicebus_version: String by project

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}


kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation("io.ktor:ktor-client-core:$ktor_version")
                implementation("io.ktor:ktor-client-cio:$ktor_version")
                implementation("com.azure:azure-messaging-servicebus:$azuer_servicebus_version")

            //                implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
//                implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.3.0-rc1")
            }
        }
        val jvmTest by getting
    }
}



compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "demo-compose-desktop"
            packageVersion = "1.0.0"
        }
    }
}
