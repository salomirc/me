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
import org.example.me.components.widgets.IconButton
import org.example.me.components.widgets.TextIconButton
import org.example.kobwebemptyproject.models.ui.NavItem
import org.example.me.AppStyles
import org.example.me.SiteColors
import org.example.me.SitePalette
import org.example.me.components.widgets.Spacer
import org.example.me.components.widgets.TextButton
import org.example.me.toSitePalette
import org.jetbrains.compose.web.attributes.ButtonType
import org.jetbrains.compose.web.attributes.type
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.A
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun NavHeader() {
    val ctx: PageContext = rememberPageContext()
    var colorMode: ColorMode by ColorMode.currentState
    val currentPath = ctx.route.path
    val coroutineScope = rememberCoroutineScope()

    val navItems = remember {
        listOf(
            NavItem(title = "Home", iconName = "home", target = "/"),
            NavItem(title = "About", iconName = "address-card", target = "/about"),
            NavItem(title = "Experience", iconName = "scissors", target = "/experience"),
            NavItem(title = "Projects", iconName = "plane-departure", target = "/projects"),
            NavItem(title = "Contact", iconName = "square-envelope", target = "/contact"),
        )
    }

    var selectedButton by remember { mutableStateOf(navItems[0]) }
    var isMobileMenuOpen by remember { mutableStateOf(false) }

    fun onNavItemButtonClick(navItem: NavItem, isMobileMenu: Boolean = false) {
        fun navigate() = ctx.router.navigateTo(navItem.target)
        selectedButton = navItem
        if (isMobileMenu) {
            coroutineScope.launch {
                delay(300)
                isMobileMenuOpen = false
                navigate()
            }
        } else {
            navigate()
        }
    }

    LaunchedEffect(currentPath) {
        console.log("ctx.route.path = $currentPath")
        navItems.find { BasePath.prependTo(it.target) == currentPath }?.let { selectedButton = it }
    }
    Div(attrs = {
        classes(AppStyles.siteStyleSheet.navBarContainer)
    }) {
        NavBarLandscapeMenu(
            navItems = navItems,
            selectedButton = selectedButton,
            colorMode = colorMode,
            onNavItemButtonClick = ::onNavItemButtonClick,
            onBarsMenuButtonClick = {
                isMobileMenuOpen = !isMobileMenuOpen
            },
            onChangeThemeIconButtonClick = {
                colorMode = colorMode.opposite
            }
        )
        MobilePortraitMenu(
            navItems = navItems,
            selectedButton = selectedButton,
            isMobileMenuOpen = isMobileMenuOpen,
            onNavItemButtonClick = { navItem ->
                onNavItemButtonClick(navItem = navItem, isMobileMenu = true)
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
fun MobilePortraitMenu(
    navItems: List<NavItem>,
    selectedButton: NavItem,
    isMobileMenuOpen: Boolean,
    onNavItemButtonClick: (NavItem) -> Unit,
) {
    Div(attrs = {
        classes(
            if (isMobileMenuOpen) {
                AppStyles.siteStyleSheet.mobileMenuClass
            } else {
                AppStyles.siteStyleSheet.displayNone
            }
        )
    }) {
        navItems.forEach { navItem ->
            TextButton(
                text = navItem.title,
                isSelected = selectedButton == navItem,
                onClick = {
                    onNavItemButtonClick(navItem)
                }
            )
        }
        Spacer(height = 1.px)
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
                modifier = Modifier.padding(top = 8.px),
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