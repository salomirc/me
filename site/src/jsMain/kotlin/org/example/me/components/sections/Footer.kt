package org.example.me.components.sections

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.navigation.BasePath
import org.example.me.AppStyles
import org.example.me.components.widgets.Spacer
import org.example.me.models.ui.NavItem
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img

@Composable
fun FooterContainer(
    navItems: List<NavItem>,
    onNavItemButtonClick: (NavItem) -> Unit
) {
    Div(attrs = {
        classes(AppStyles.siteStyleSheet.footerContainerClass)
    }) {
        Spacer(style = { flexGrow(4) })
        Div(attrs = {
            style {
                width(500.px)
                height(50.percent)
                backgroundColor(Color.orange)
            }
        })
        Spacer(style = { flexGrow(1) })
        Img(
            alt = "bithipster-logo",
            src = BasePath.prependTo("/images/bithipster-logo.png"),
            attrs = {
                style {
                    width(262.px)
                    height(88.px)
                }
            }
        )
        Spacer(style = { flexGrow(1) })
    }
}