package org.example.me.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.fontWeight
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.navigation.BasePath
import org.example.me.AppStyles.siteStyleSheet
import org.jetbrains.compose.web.css.marginTop
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Img
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
@Layout(".components.layouts.PageMainLayout")
fun HomePage(ctx: PageContext) {
    HomePageContainer {
        HelloComposable()
        AndroidMeComposable()
    }
}

@Composable
fun HomePageContainer(
    content: @Composable () -> Unit
) {
    Div(attrs = {
        classes(siteStyleSheet.homePageContainerClass)
    }) {
        content()
    }
}

@Composable
fun HelloComposable() {
    Div(attrs = {
        classes(siteStyleSheet.helloBoxClass, siteStyleSheet.helloContainerClass)
    }) {
        Div(attrs = {
            style {
                marginTop(32.px)
                fontWeight(FontWeight.Bold)
            }
        }) {
            Text("Hello I'm")
        }
        Div(attrs = {
            classes(siteStyleSheet.helloNameClass)
        }) {
            Text("Ciprian Salomir.")
        }
        Div(attrs = {
            style {
                marginTop(24.px)
            }
        }) {
            Text("and Nice to meet you!")
        }
    }
}

@Composable
fun AndroidMeComposable() {
    Div(attrs = {
        classes(siteStyleSheet.helloBoxClass, siteStyleSheet.helloBoxImageClass)
    }) {
        Img(
            alt = "androidify_me",
            src = BasePath.prependTo("/images/androidify_me.png"),
            attrs = {
                classes(siteStyleSheet.helloImageClass)
            }
        )
    }
}

