package org.example.me.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.fontWeight
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.navigation.BasePath
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.example.me.AppStyles
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
        AndroidMeImage()
    }
}

@Composable
fun HomePageContainer(
    content: @Composable () -> Unit
) {
    Div(attrs = {
        classes(AppStyles.siteStyleSheet.homePageContainerClass)
    }) {
        content()
    }
}

@Composable
fun HelloComposable() {
    Div(attrs = {
        classes(AppStyles.siteStyleSheet.helloComposableClass)
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
            classes(AppStyles.siteStyleSheet.nameDivClass)
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
fun AndroidMeImage() {
    Img(
        src = BasePath.prependTo("/images/androidify_me.png"),
        attrs = {
            classes(AppStyles.siteStyleSheet.aboutImageClass)
        }
    )
}

