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
import org.example.me.AnimationTiming
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
        }
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
    this.content()
}

@Composable
fun MobileMenuRootContainer(
    navItems: List<NavItem>,
    selectedButton: NavItem,
    isMobileMenuOpen: Boolean,
    onNavItemButtonClick: (NavItem) -> Unit,
    onCloseButtonClick: () -> Unit
) {
    val styles = remember { mutableStateListOf(siteStyleSheet.mobileMenuRootContainerHiddenClass) }

    LaunchedEffect(key1 = isMobileMenuOpen) {
        if (isMobileMenuOpen) {
            styles.add(siteStyleSheet.mobileMenuRootContainerVisibleClass)
        } else {
            if (styles.contains(siteStyleSheet.mobileMenuRootContainerVisibleClass)) {
                delay(AnimationTiming.TIME_LARGE.toLong())
                styles.remove(siteStyleSheet.mobileMenuRootContainerVisibleClass)
            }
        }
    }

    Div(attrs = {
        classes(styles)
    }) {
        MobileMenuOverlay(
            isMobileMenuOpen = isMobileMenuOpen,
            navItems = navItems,
            selectedButton = selectedButton,
            onNavItemButtonClick = onNavItemButtonClick,
            onCloseButtonClick = onCloseButtonClick
        )
    }
}


@Composable
fun MobileMenuOverlay(
    isMobileMenuOpen: Boolean,
    navItems: List<NavItem>,
    selectedButton: NavItem,
    onNavItemButtonClick: (NavItem) -> Unit,
    onCloseButtonClick: () -> Unit
) {
    val styles = mutableListOf(siteStyleSheet.mobileMenuOverlayContainerClass).apply {
        if (isMobileMenuOpen) add(siteStyleSheet.mobileMenuOverlayTransitionedContainerClass)
    }
    Div(attrs = {
        classes(styles)
    }) {
        MobileMenuContainer(
            isMobileMenuOpen = isMobileMenuOpen,
            onCloseButtonClick = onCloseButtonClick,
            navItems = navItems,
            selectedButton = selectedButton,
            onNavItemButtonClick = onNavItemButtonClick
        )
    }
}

@Composable
fun MobileMenuContainer(
    isMobileMenuOpen: Boolean,
    onCloseButtonClick: () -> Unit,
    navItems: List<NavItem>,
    selectedButton: NavItem,
    onNavItemButtonClick: (NavItem) -> Unit
) {
    val styles = mutableListOf(siteStyleSheet.flexColumnDefaultClass, siteStyleSheet.mobileMenuContainerClass).apply {
        if (isMobileMenuOpen) add(siteStyleSheet.mobileMenuContainerTransitionedClass)
    }
    Div(attrs = {
        id("mobileMenuContainer")
        classes(styles)
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
fun CloseButtonContainer(
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
fun PortraitMenuButtons(
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