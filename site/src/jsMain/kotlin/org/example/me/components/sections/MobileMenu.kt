package org.example.me.components.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.icons.fa.FaIcon
import com.varabyte.kobweb.silk.components.icons.fa.IconCategory
import kotlinx.coroutines.delay
import org.example.kobwebemptyproject.models.ui.NavItem
import org.example.me.AnimationTiming
import org.example.me.AppStyles.siteStyleSheet
import org.example.me.SiteColors
import org.example.me.components.widgets.IconButton
import org.example.me.components.widgets.Spacer
import org.example.me.components.widgets.TextButton
import org.jetbrains.compose.web.css.flexGrow
import org.jetbrains.compose.web.css.paddingBottom
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.textAlign
import org.jetbrains.compose.web.dom.Div

@Composable
fun MobileMenuRootContainer(
    navItems: List<NavItem>,
    selectedButton: NavItem,
    isMobileMenuOpen: Boolean,
    onNavItemButtonClick: (NavItem) -> Unit,
    onCloseButtonClick: () -> Unit
) {
    val styles = remember {
        mutableStateListOf(siteStyleSheet.mobileMenuRootContainerHiddenClass).apply {
            if (isMobileMenuOpen) add(siteStyleSheet.mobileMenuRootContainerVisibleClass)
        }
    }

    LaunchedEffect(key1 = isMobileMenuOpen) {
        val containContainerVisibleClass = styles.contains(siteStyleSheet.mobileMenuRootContainerVisibleClass)
        if (isMobileMenuOpen) {
            if (!containContainerVisibleClass) {
                styles.add(siteStyleSheet.mobileMenuRootContainerVisibleClass)
            }
        } else {
            if (containContainerVisibleClass) {
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