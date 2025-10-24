package org.example.me.components.layouts

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.navigation.BasePath
import com.varabyte.kobweb.silk.components.icons.fa.FaIcon
import com.varabyte.kobweb.silk.components.icons.fa.IconCategory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.kobwebemptyproject.models.ui.NavItem
import org.example.me.AppStyles.siteStyleSheet
import org.example.me.SiteColors
import org.example.me.components.sections.NavBarContainer
import org.example.me.components.widgets.IconButton
import org.example.me.components.widgets.Spacer
import org.example.me.components.widgets.TextButton
import org.jetbrains.compose.web.css.flexGrow
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.textAlign
import org.jetbrains.compose.web.dom.Div

@Layout(".components.layouts.AppContainerLayout")
@Composable
fun AppContainerLayoutScope.PageMainLayout(
    ctx: PageContext,
    content: @Composable AppContainerLayoutScope.() -> Unit
) {
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

    NavBarContainer(
        navItems = navItems,
        selectedButton = selectedButton,
        isMobileMenuOpen = isMobileMenuOpen,
        onNavItemButtonClick = ::onNavItemButtonClick,
        onMobileMenuOpen = { boolean ->
            isMobileMenuOpen = boolean
        }
    )
    MobileMenuOverlayContainer(
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
fun MobileMenuOverlayContainer(
    navItems: List<NavItem>,
    selectedButton: NavItem,
    isMobileMenuOpen: Boolean,
    onNavItemButtonClick: (NavItem) -> Unit,
    onCloseButtonClick: () -> Unit
) {
    Div(attrs = {
        id("mobileMenuOverlayClass")
        classes(
            if (isMobileMenuOpen) {
                listOf(
                    siteStyleSheet.mobileMenuOverlayClass
                )
            } else {
                listOf(siteStyleSheet.displayNone)
            }
        )
    }) {
        MobileMenuContainer(
            onCloseButtonClick = onCloseButtonClick,
            navItems = navItems,
            selectedButton = selectedButton,
            onNavItemButtonClick = onNavItemButtonClick
        )
    }
}

@Composable
private fun MobileMenuContainer(
    onCloseButtonClick: () -> Unit,
    navItems: List<NavItem>,
    selectedButton: NavItem,
    onNavItemButtonClick: (NavItem) -> Unit
) {
    Div(attrs = {
        id("mobileMenuContainer")
        classes(siteStyleSheet.flexColumnDefaultClass, siteStyleSheet.mobileMenuContainerClass)
    }) {
        CloseButtonContainer(
            onCloseButtonClick = onCloseButtonClick
        )
        Spacer(style = { flexGrow(1) })
        PortraitMenuButtons(
            navItems = navItems,
            selectedButton = selectedButton,
            onNavItemButtonClick = onNavItemButtonClick
        )
        Spacer(style = { flexGrow(4) })
    }
}


@Composable
private fun CloseButtonContainer(
    onCloseButtonClick: () -> Unit,
) {
    Div(attrs = {
        id("xMarkIconButtonContainer")
        style {
            paddingBottom(32.px)
            textAlign("right")
        }
    }) {
        IconButton(
            id = "xMarkIconButton",
            styles = listOf(siteStyleSheet.simpleIconButtonClass),
            fontSize = 16.px,
            onClick = onCloseButtonClick,
            backgroundColor = SiteColors.overlayTransparent,
            content = {
                FaIcon(
                    name = "xmark",
                    modifier = Modifier.padding(top = 4.px),
                    style = IconCategory.SOLID
                )
            }
        )
    }
}

@Composable
private fun PortraitMenuButtons(
    navItems: List<NavItem>,
    selectedButton: NavItem,
    onNavItemButtonClick: (NavItem) -> Unit
) {
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