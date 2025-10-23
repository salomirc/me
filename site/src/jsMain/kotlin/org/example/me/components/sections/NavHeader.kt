package org.example.me.components.sections

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.navigation.BasePath
import com.varabyte.kobweb.silk.components.icons.MoonIcon
import com.varabyte.kobweb.silk.components.icons.SunIcon
import com.varabyte.kobweb.silk.components.icons.fa.FaIcon
import com.varabyte.kobweb.silk.components.icons.fa.IconCategory
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.kobwebemptyproject.models.ui.NavItem
import org.example.me.AppStyles
import org.example.me.components.widgets.IconButton
import org.example.me.components.widgets.Spacer
import org.example.me.components.widgets.TextButton
import org.example.me.components.widgets.TextIconButton
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

@Composable
fun NavHeader(
    navItems: List<NavItem>,
    selectedButton: NavItem,
    isMobileMenuOpen: Boolean,
    onNavItemButtonClick: (NavItem) -> Unit,
    onMobileMenuOpen: (Boolean) -> Unit
) {
    var colorMode: ColorMode by ColorMode.currentState

    Div(attrs = {
        classes(AppStyles.siteStyleSheet.navBarContainer)
    }) {
        NavBarLandscapeMenu(
            navItems = navItems,
            selectedButton = selectedButton,
            colorMode = colorMode,
            onNavItemButtonClick = onNavItemButtonClick,
            onBarsMenuButtonClick = {
                onMobileMenuOpen(!isMobileMenuOpen)
            },
            onChangeThemeIconButtonClick = {
                colorMode = colorMode.opposite
            }
        )
    }
}

@Composable
fun NavBarLandscapeMenu(
    navItems: List<NavItem>,
    selectedButton: NavItem,
    colorMode: ColorMode,
    onNavItemButtonClick: (NavItem) -> Unit,
    onBarsMenuButtonClick: () -> Unit,
    onChangeThemeIconButtonClick: () -> Unit
) {
    Div(attrs = {
        classes(AppStyles.siteStyleSheet.navBarHorizontalContainer)
    }) {
        NavButtonsLandscape(
            navItems = navItems,
            selectedButton = selectedButton,
            onClick = onNavItemButtonClick
        )
        MobileBarsMenuButton(
            onClick = onBarsMenuButtonClick
        )
        Spacer(width = 20.px)
        ChangeThemeIconButton(
            colorMode = colorMode,
            onClick = onChangeThemeIconButtonClick
        )
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
fun ChangeThemeIconButton(
    colorMode: ColorMode,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
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