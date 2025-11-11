package org.example.me.components.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.navigation.BasePath
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.example.me.AppStyles.siteStyleSheet
import org.example.me.components.widgets.SimpleTextButton
import org.example.me.components.widgets.Spacer
import org.example.me.models.ui.NavItem
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*

@Composable
fun FooterContainer(
    navItems: List<NavItem>,
    onNavItemButtonClick: (NavItem) -> Unit
) {
    var colorMode: ColorMode by ColorMode.currentState
    Div(attrs = {
        classes(siteStyleSheet.footerContainerClass)
    }) {
        Spacer(style = { flexGrow(2) })
        Div(attrs = {
            style {
                textAlign("center")
            }
        }) {
            navItems.forEachIndexed { index, navItem ->
                SimpleTextButton(
                    text = navItem.title,
                    onClick = {
                        onNavItemButtonClick(navItem)
                    },
                    classes = listOf(siteStyleSheet.footerButtonClass)
                )
                if (index < navItems.lastIndex) {
                    Span {
                        Text("|")
                    }
                }
            }
            Br {}
            Span {
                Text("Copyright © 2025 Ciprian Salomir - Web Design by BitRabbit")
            }
        }
        Spacer(style = { flexGrow(2) })
//        val imageName = if (colorMode.isLight) "bithipster-logo.png" else "bithipster-logo-inverted.png"
        Img(
            alt = "bithipster-logo",
//            src = BasePath.prependTo("/images/$imageName"),
            src = BasePath.prependTo("/images/bit-rabbit-logo.png"),
            attrs = {
                style {
                    width(150.px)
                    height(64.px)
                }
            }
        )
        Spacer(width = 85.px)
    }
}