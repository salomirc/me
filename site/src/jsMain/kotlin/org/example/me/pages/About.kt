package org.example.me.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.example.me.AppStyles
import org.example.me.components.widgets.NumberBox
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.FlexWrap
import org.jetbrains.compose.web.css.display
import org.jetbrains.compose.web.css.flexWrap
import org.jetbrains.compose.web.dom.Div

@Page
@Composable
@Layout(".components.layouts.PageMainLayout")
fun AboutPage(ctx: PageContext) {
    var colorMode by ColorMode.currentState
    Div(attrs = {
        classes(AppStyles.siteStyleSheet.pageContainerClass)
        style {
            display(DisplayStyle.Flex)
            flexWrap(FlexWrap.Wrap)
        }
    }) {
        repeat(100) { index ->
            NumberBox(
                text = "$index"
            )
        }
    }
}

