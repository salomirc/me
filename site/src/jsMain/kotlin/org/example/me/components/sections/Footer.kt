package org.example.me.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.objectFit
import com.varabyte.kobweb.navigation.BasePath
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
    Div(attrs = {
        classes(siteStyleSheet.footerContainerClass)
    }) {
        Spacer(style = { flexGrow(4) })
        Div(attrs = {
            style {
                textAlign("center")
                fontSize(14.px)
            }
        }) {
            navItems.forEachIndexed { index, navItem ->
                SimpleTextButton(
                    text = navItem.title,
                    onClick = {
                        onNavItemButtonClick(navItem)
                    }
                )
                if (index < navItems.lastIndex) {
                    Span {
                        Text("|")
                    }
                }
            }
            Br {}
            Span {
                Text("Copyright © 2025 Ciprian Salomir - Web Design by BitHipster")
            }
        }
        Spacer(style = { flexGrow(1) })
        Img(
            alt = "bithipster-logo",
            src = BasePath.prependTo("/images/bithipster-logo.png"),
            attrs = {
                style {
                    width(262.px)
                    height(88.px)
                    objectFit(ObjectFit.None)
                    flexGrow(0)
                }
            }
        )
        Spacer(style = { flexGrow(1) })
    }
}