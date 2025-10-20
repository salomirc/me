package org.example.me.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.varabyte.kobweb.core.layout.Layout

@Layout
@Composable
fun AppContainerLayout(content: @Composable AppContainerLayoutScope.() -> Unit) {
    val scope = remember { AppContainerLayoutScope() }

    scope.content()
}

class AppContainerLayoutScope {
    init {
        console.log("AppContainerLayoutScope init = ${this.hashCode()}")
    }
}