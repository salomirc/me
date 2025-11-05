package org.example.me.components.sections

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.navigation.BasePath
import com.varabyte.kobweb.silk.components.icons.MoonIcon
import com.varabyte.kobweb.silk.components.icons.SunIcon
import com.varabyte.kobweb.silk.components.icons.fa.FaIcon
import com.varabyte.kobweb.silk.components.icons.fa.IconCategory
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.me.AnimationTiming
import org.example.me.AppStyles.siteStyleSheet
import org.example.me.components.widgets.IconButton
import org.example.me.components.widgets.Spacer
import org.example.me.components.widgets.TextIconButton
import org.example.me.models.ui.NavItem
import org.jetbrains.compose.web.css.Color
import org.jetbrains.compose.web.css.backgroundColor
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div

@Composable
fun NavHeader(globalMessageContent: @Composable () -> Unit) {
    val ctx = rememberPageContext()
    val currentPath = ctx.route.path
    val navItems = remember {
        listOf(
            NavItem(title = "Home", iconName = "home", target = "/"),
            NavItem(title = "About", iconName = "address-card", target = "/about"),
            NavItem(title = "Experience", iconName = "scissors", target = "/experience"),
            NavItem(title = "Projects", iconName = "plane-departure", target = "/projects"),
            NavItem(title = "Contact", iconName = "square-envelope", target = "/contact"),
            NavItem(title = "Backend", iconName = "square-envelope", target = "/custom-backend-demo"),
        )
    }

    var isMobileMenuOpen by remember { mutableStateOf(false) }
    var selectedButton by remember { mutableStateOf(navItems[0]) }

    val coroutineScope = rememberCoroutineScope()

    fun onNavItemButtonClick(navItem: NavItem, isMobileMenu: Boolean = false) {
        fun navigate() = ctx.router.navigateTo(navItem.target)
        selectedButton = navItem
        if (isMobileMenu) {
            coroutineScope.launch {
                delay(AnimationTiming.TIME_VERY_FAST.toLong())
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

    NavBarContainer(
        navItems = navItems,
        selectedButton = selectedButton,
        isMobileMenuOpen = isMobileMenuOpen,
        onNavItemButtonClick = ::onNavItemButtonClick,
        onMobileMenuOpen = { boolean ->
            isMobileMenuOpen = boolean
        },
        globalMessageContent = globalMessageContent
    )
    MobileMenuRootContainer(
        navItems = navItems,
        selectedButton = selectedButton,
        isMobileMenuOpen = isMobileMenuOpen,
        onNavItemButtonClick = { navItem ->
            onNavItemButtonClick(navItem = navItem, isMobileMenu = true)
        },
        onCloseButtonClick = {
            isMobileMenuOpen = false
        }
    )
}

@Composable
fun NavBarContainer(
    navItems: List<NavItem>,
    selectedButton: NavItem,
    isMobileMenuOpen: Boolean,
    onNavItemButtonClick: (NavItem) -> Unit,
    onMobileMenuOpen: (Boolean) -> Unit,
    globalMessageContent: @Composable () -> Unit
) {

    Div(attrs = {
        id("navBarContainer")
        classes(siteStyleSheet.navBarContainer)
    }) {
        NavBarLandscapeMenu(
            navItems = navItems,
            selectedButton = selectedButton,
            onNavItemButtonClick = onNavItemButtonClick,
            onBarsMenuButtonClick = {
                onMobileMenuOpen(!isMobileMenuOpen)
            }
        )
        globalMessageContent()
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
        classes(siteStyleSheet.navBarHorizontalContainer)
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
            styles = listOf(siteStyleSheet.displayNoneMax640pxMediaQuery),
        )
    }
}

@Composable
fun MobileBarsMenuButton(
    onClick: () -> Unit
) {
    IconButton(
        id = "barsMenuButton",
        styles = listOf(siteStyleSheet.barsMenuClass),
        onClick = onClick,
        inlineStyle = {
            backgroundColor(Color.transparent)
        },
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
        inlineStyle = {
            backgroundColor(Color.transparent)
        },
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