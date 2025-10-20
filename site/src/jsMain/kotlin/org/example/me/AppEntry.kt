package org.example.me

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.css.StyleVariable
import com.varabyte.kobweb.compose.css.Transition
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.App
import com.varabyte.kobweb.core.init.InitKobweb
import com.varabyte.kobweb.core.init.InitKobwebContext
import com.varabyte.kobweb.silk.SilkApp
import com.varabyte.kobweb.silk.components.layout.Surface
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.init.registerStyleBase
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.base
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.style.vars.animation.TransitionDurationVars
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.loadFromLocalStorage
import com.varabyte.kobweb.silk.theme.colors.saveToLocalStorage
import com.varabyte.kobweb.silk.theme.colors.systemPreference
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

private const val COLOR_MODE_KEY = "me:colorMode"

@InitSilk
fun initColorMode(ctx: InitSilkContext) {
    ctx.config.initialColorMode = ColorMode.loadFromLocalStorage(COLOR_MODE_KEY) ?: ColorMode.systemPreference
}

@InitSilk
fun initStyles(ctx: InitSilkContext) {
    ctx.stylesheet.apply {
        registerStyleBase("html, body") { Modifier.fillMaxHeight() }
        registerStyleBase("body") { Modifier.scrollBehavior(ScrollBehavior.Smooth) }
    }
}

@InitKobweb
fun setCustomErrorPage(ctx: InitKobwebContext) {
    ctx.router.setErrorPage {
        Surface(
            modifier = FastColorStyle
                .toModifier()
                .width(100.percent)
                .minHeight(100.vh)
                .scrollBehavior(ScrollBehavior.Smooth)
        ) {
            Div(attrs = {
                style {
                    height(50.vh)
                    display(DisplayStyle.Flex)
                    flexDirection(FlexDirection.Column)
                    justifyContent(JustifyContent.Center)
                    alignItems(AlignItems.Center)
                }
            }) {
                Span(attrs = {
                    style {
                        fontSize(18.px)
                        fontWeight("bold")
                    }
                }) {
                    Text("\"404 – We couldn’t find that page. Double-check the URL or return home.\"")
                }
            }
        }
    }
}

@App
@Composable
fun AppEntry(content: @Composable () -> Unit) {
    SilkApp {
        val colorMode = ColorMode.current
        LaunchedEffect(colorMode) {
            colorMode.saveToLocalStorage(org.example.me.COLOR_MODE_KEY)
        }

        AppStyles.siteStyleSheet = SiteStyleSheet(colorMode.toSitePalette())
        Style(AppStyles.siteStyleSheet)

        Surface(
            modifier = FastColorStyle
                .toModifier()
                .width(100.percent)
                .minHeight(100.vh)
                .scrollBehavior(ScrollBehavior.Smooth)
        ) {
            content()
        }
    }
}

val FastColorTransitionDurationVar by StyleVariable(
    prefix = "silk",
    defaultFallback = TransitionDurationVars.Fast.value()
)

val FastColorStyle = CssStyle.base {
    Modifier.transition(Transition.of("background-color", FastColorTransitionDurationVar.value()))
}
