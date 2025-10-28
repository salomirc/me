package org.example.me.components.layouts

import androidx.compose.runtime.*
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.navigation.BasePath
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.kobwebemptyproject.models.ui.NavItem
import org.example.me.AnimationTiming
import org.example.me.components.sections.MobileMenuRootContainer
import org.example.me.components.sections.NavBarContainer

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