package org.example.me.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.layout.Layout
import org.example.me.components.sections.NavHeader
import org.jetbrains.compose.web.css.minHeight
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.vh
import org.jetbrains.compose.web.dom.Div

@Layout(".components.layouts.AppContainerLayout")
@Composable
fun AppContainerLayoutScope.PageMainLayout(
    content: @Composable AppContainerLayoutScope.() -> Unit
) {
    LayoutContainer {
        NavHeader()
        this.content()
    }
}

@Composable
fun AppContainerLayoutScope.LayoutContainer(
    content: @Composable AppContainerLayoutScope.() -> Unit
) {
    Div(attrs = {
        style {
            paddingBottom(1.px)
            minHeight(100.vh)
        }
    }) {
        this@LayoutContainer.content()
    }
}