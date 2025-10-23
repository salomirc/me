import com.varabyte.kobweb.common.navigation.BasePath
import com.varabyte.kobweb.gradle.application.util.configAsKobwebApplication
import kotlinx.html.LinkAs
import kotlinx.html.link

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kobweb.application)
    alias(libs.plugins.kobwebx.markdown)
}

group = "org.example.me"
version = "1.0-SNAPSHOT"

kobweb {
    app {
        index {
            description.set("Powered by Kobweb")
            head.add {
                link(rel = "preconnect", href = "https://fonts.googleapis.com")
                link(rel = "preconnect", href = "https://fonts.gstatic.com")
                link(rel = "stylesheet", href = basePath.prependTo("/css/normalize.css"))
                link(rel = "stylesheet", href = "https://fonts.googleapis.com/css?family=Sofia")
                link(rel = "stylesheet", href = "https://fonts.googleapis.com/css?family=Bitcount+Grid+Single")
            }
        }
    }
}

kotlin {
    configAsKobwebApplication("me", includeServer = true)

    sourceSets {
//        commonMain.dependencies {
//          // Add shared dependencies between JS and JVM here
//        }
        jsMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.html.core)
            implementation(libs.kobweb.core)
            implementation(libs.kobweb.silk)
            implementation(libs.silk.icons.fa)
            implementation(libs.kobwebx.markdown)
            
        }
        jvmMain.dependencies {
            compileOnly(libs.kobweb.api) // Provided by Kobweb backend at runtime
        }
    }
}
