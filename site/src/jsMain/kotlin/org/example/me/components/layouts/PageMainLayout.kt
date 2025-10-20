package org.example.me.components.layouts

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.core.layout.Layout
import org.example.me.components.sections.NavHeader

@Layout(".components.layouts.AppContainerLayout")
@Composable
fun AppContainerLayoutScope.PageMainLayout(
    content: @Composable AppContainerLayoutScope.() -> Unit
) {
    NavHeader()
    this.content()
}