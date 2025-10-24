package org.example.me.components.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.icons.MoonIcon
import com.varabyte.kobweb.silk.components.icons.SunIcon
import com.varabyte.kobweb.silk.components.icons.fa.FaIcon
import com.varabyte.kobweb.silk.components.icons.fa.IconCategory
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.example.kobwebemptyproject.models.ui.NavItem
import org.example.me.AppStyles
import org.example.me.components.widgets.IconButton
import org.example.me.components.widgets.Spacer
import org.example.me.components.widgets.TextIconButton
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

@Composable
fun NavBarContainer(
    navItems: List<NavItem>,
    selectedButton: NavItem,
    isMobileMenuOpen: Boolean,
    onNavItemButtonClick: (NavItem) -> Unit,
    onMobileMenuOpen: (Boolean) -> Unit
) {

    Div(attrs = {
        id("navBarContainer")
        classes(AppStyles.siteStyleSheet.navBarContainer)
    }) {
        NavBarLandscapeMenu(
            navItems = navItems,
            selectedButton = selectedButton,
            onNavItemButtonClick = onNavItemButtonClick,
            onBarsMenuButtonClick = {
                onMobileMenuOpen(!isMobileMenuOpen)
            }
        )
    }
}

@Composable
fun NavBarLandscapeMenu(
    navItems: List<NavItem>,
    selectedButton: NavItem,
    onNavItemButtonClick: (NavItem) -> Unit,
    onBarsMenuButtonClick: () -> Unit
) {
    Div(attrs = {
        id("navBarHorizontalContainer")
        classes(AppStyles.siteStyleSheet.navBarHorizontalContainer)
    }) {
        MobileBarsMenuButton(
            onClick = onBarsMenuButtonClick
        )
        NavButtonsLandscape(
            navItems = navItems,
            selectedButton = selectedButton,
            onClick = onNavItemButtonClick
        )
        Spacer(width = 20.px)
        ChangeThemeIconButton()
    }
}

@Composable
fun NavButtonsLandscape(
    navItems: List<NavItem>,
    selectedButton: NavItem,
    onClick: (NavItem) -> Unit,
) {
    navItems.forEach { navItem ->
        TextIconButton(
            iconName = navItem.iconName,
            text = navItem.title,
            onClick = { onClick(navItem) },
            isSelected = selectedButton == navItem,
            styles = listOf(AppStyles.siteStyleSheet.displayNoneMax640pxMediaQuery),
        )
    }
}

@Composable
fun MobileBarsMenuButton(
    onClick: () -> Unit
) {
    IconButton(
        id = "barsMenuButton",
        styles = listOf(AppStyles.siteStyleSheet.barsMenuClass),
        onClick = onClick,
        backgroundColor = Color.transparent,
        content = {
            FaIcon(
                name = "bars",
                modifier = Modifier.padding(top = 4.px),
                style = IconCategory.SOLID
            )
        }
    )
}

@Composable
fun ChangeThemeIconButton() {
    var colorMode: ColorMode by ColorMode.currentState
    IconButton(
        id = "ChangeThemeIconButton",
        onClick = {
            colorMode = colorMode.opposite
        },
        backgroundColor = Color.transparent,
        content = {
            if (colorMode.isLight) {
                MoonIcon(
                    modifier = Modifier
                        .padding(top = 8.px)
                )
            } else {
                SunIcon(
                    modifier = Modifier
                        .padding(top = 8.px)
                )
            }
        }
    )
}