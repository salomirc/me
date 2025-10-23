package org.example.me.components.layouts

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.navigation.BasePath
import com.varabyte.kobweb.silk.components.icons.fa.FaIcon
import com.varabyte.kobweb.silk.components.icons.fa.IconCategory
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.kobwebemptyproject.models.ui.NavItem
import org.example.me.AppStyles
import org.example.me.components.sections.NavHeader
import org.example.me.components.widgets.IconButton
import org.example.me.components.widgets.TextButton
import org.example.me.toSitePalette
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div

@Layout(".components.layouts.AppContainerLayout")
@Composable
fun AppContainerLayoutScope.PageMainLayout(
    content: @Composable AppContainerLayoutScope.() -> Unit
) {
    val ctx: PageContext = rememberPageContext()
    val currentPath = ctx.route.path

    val navItems = remember {
        listOf(
            NavItem(title = "Home", iconName = "home", target = "/"),
            NavItem(title = "About", iconName = "address-card", target = "/about"),
            NavItem(title = "Experience", iconName = "scissors", target = "/experience"),
            NavItem(title = "Projects", iconName = "plane-departure", target = "/projects"),
            NavItem(title = "Contact", iconName = "square-envelope", target = "/contact"),
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
                delay(100)
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

    NavHeader(
        navItems = navItems,
        selectedButton = selectedButton,
        isMobileMenuOpen = isMobileMenuOpen,
        onNavItemButtonClick = ::onNavItemButtonClick,
        onMobileMenuOpen = { boolean ->
            isMobileMenuOpen = boolean
        }
    )
    MobilePortraitMenu(
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
    this.content()
}

@Composable
fun MobilePortraitMenu(
    navItems: List<NavItem>,
    selectedButton: NavItem,
    isMobileMenuOpen: Boolean,
    onNavItemButtonClick: (NavItem) -> Unit,
    onCloseButtonClick: () -> Unit
) {
    var colorMode: ColorMode by ColorMode.currentState
    Div(attrs = {
        classes(
            if (isMobileMenuOpen) {
                listOf(
                    AppStyles.siteStyleSheet.mobileMenuOverlayClass
                )
            } else {
                listOf(AppStyles.siteStyleSheet.displayNone)
            }
        )
    }) {
        Div(attrs = {
            classes(AppStyles.siteStyleSheet.mobileMenuClass)
        }) {
            Div(attrs = {
                style {
                    paddingBottom(32.px)
                    textAlign("right")
                }
            }) {
                IconButton(
                    styles = listOf(AppStyles.siteStyleSheet.simpleIconButtonClass),
                    fontSize = 16.px,
                    onClick = onCloseButtonClick,
                    backgroundColor = colorMode.toSitePalette().overlayTransparent,
                    content = {
                        FaIcon(
                            name = "xmark",
                            modifier = Modifier.padding(top = 4.px),
                            style = IconCategory.SOLID
                        )
                    }
                )
            }
            navItems.forEach { navItem ->
                TextButton(
                    text = navItem.title,
                    isSelected = selectedButton == navItem,
                    onClick = {
                        onNavItemButtonClick(navItem)
                    }
                )
            }
        }
    }
}