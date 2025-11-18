package org.example.me.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.example.me.AppStyles
import org.example.me.components.widgets.PageTitle
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Page
@Composable
@Layout(".components.layouts.PageFooterLayout")
fun AboutPage(ctx: PageContext) {
    var colorMode by ColorMode.currentState
    Div(attrs = {
        classes(AppStyles.siteStyleSheet.pageContainerClass)
    }) {
        PageTitle("About me")
        P {
            Text("I am an enthusiastic, self-motivated, Android Developer with Kotlin and Java. I started" +
                    " my career in IT as a Web Developer using Open Source technologies. Then I graduated" +
                    " .NET Web Development course at The Informal School of IT. After that I have graduated" +
                    " Xamarin University Professional course with Microsoft certification. After 2 years of" +
                    " cross platform mobile development with Xamarin I decided to focus on native Android" +
                    " development.")
        }
    }
}

