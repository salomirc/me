package org.example.me.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.PageContext
import com.varabyte.kobweb.core.layout.Layout
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.example.me.components.widgets.PageContainer
import org.example.me.components.widgets.PageTitle
import org.jetbrains.compose.web.dom.Li
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.dom.Ul

@Page
@Composable
@Layout(".components.layouts.PageFooterLayout")
fun ProjectsPage(ctx: PageContext) {
    var colorMode by ColorMode.currentState
    PageContainer {
        PageTitle("Projects")
        Ul {
            Li { Text("List of name and descriptions") }
            Li { Text("Skills and tools pictograms") }
        }
    }
}

