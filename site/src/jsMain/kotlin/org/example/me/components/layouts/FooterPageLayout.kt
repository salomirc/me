package org.example.me.components.layouts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.core.rememberPageContext
import org.example.me.AppStyles
import org.example.me.components.sections.FooterContainer
import org.example.me.view_models.MainViewModel
import org.jetbrains.compose.web.dom.Div

@Layout(".components.layouts.PageMainLayout")
@Composable
fun AppContainerLayoutScope.PageFooterLayout(
    content: @Composable AppContainerLayoutScope.() -> Unit
) {
    val mainViewModel: MainViewModel = remember { this.provideMainViewModel() }
    val model by mainViewModel.modelStateFlow.collectAsState()

    FooterLayout(
        model = model,
        content = content,
    )
}

@Composable
fun AppContainerLayoutScope.FooterLayout(
    model: MainViewModel.Model,
    content: @Composable AppContainerLayoutScope.() -> Unit
) {
    val ctx = rememberPageContext()
    Div(attrs = {
        classes(AppStyles.siteStyleSheet.footerLayoutContentContainer)
    }) {
        this@FooterLayout.content()
    }
    FooterContainer(
        navItems = model.navItems,
        onNavItemButtonClick = { navItem ->
            ctx.router.navigateTo(navItem.target)
        }
    )
}