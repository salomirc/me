package org.example.kobwebemptyproject.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.silk.components.icons.fa.FaIcon
import com.varabyte.kobweb.silk.components.icons.fa.IconCategory
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.example.me.AppStyles
import org.example.me.toSitePalette
import org.jetbrains.compose.web.attributes.ButtonType
import org.jetbrains.compose.web.attributes.type
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun NumberBox(
    text: String,
    style: StyleScope.() -> Unit = {}
) {
    val sitePalette = ColorMode.current.toSitePalette()
    Div(attrs = {
        style {
            backgroundColor(sitePalette.surfaceVariant)
            width(100.px)
            margin(10.px)
            textAlign("center")
            lineHeight(75.px)
            fontSize(30.px)
            style()
        }
    }) {
        Text(text)
    }
}

@Composable
fun RegularButton(
    styles: List<String>? = null,
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    val styles: MutableList<String> = mutableListOf(AppStyles.siteStyleSheet.regularButtonClass).apply {
        styles?.let { addAll(it) }
        if (isSelected) add(AppStyles.siteStyleSheet.regularButtonClassSelected)
    }
    Button(attrs ={
        type(ButtonType.Button)
        classes(styles)
        onClick {
            onClick()
        }
    }) {
        Text(text)
    }
}

@Composable
fun IconButton(
    styles: List<String>? = null,
    fontSize: CSSNumeric = 30.px,
    backgroundColor: CSSColorValue,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val styles: MutableList<String> = mutableListOf(AppStyles.siteStyleSheet.iconButtonClass).apply {
        styles?.let { this.addAll(it) }
    }
    Button(attrs ={
        onClick {
            onClick()
        }
        classes(styles)
        style {
            backgroundColor(backgroundColor)
        }
    }) {
        Div(attrs = {
            style {
                fontSize(fontSize)
            }
        }) {
            content()
        }
    }
}
@Composable
fun TextIconButton(
    iconName: String,
    text: String,
    onClick: () -> Unit,
    isSelected: Boolean = false,
    iconFontSize: CSSNumeric = 24.px,
    textFontSize: CSSNumeric = 12.px,
    styles: List<String>? = null
) {
    val styles: MutableList<String> = mutableListOf(AppStyles.siteStyleSheet.iconButtonClass).apply {
        styles?.let { this.addAll(it) }
        if (isSelected) add(AppStyles.siteStyleSheet.regularButtonClassSelected)
    }
    Button(attrs ={
        type(ButtonType.Button)
        classes(styles)
        onClick {
            onClick()
        }
    }) {
        Div {
            Div(attrs = {
                style {
                    fontSize(iconFontSize)
                }
            }
            ) {
                FaIcon(
                    name = iconName,
                    modifier = Modifier,
                    style = IconCategory.SOLID
                )
            }
            Span(attrs = {
                style {
                    fontSize(textFontSize)
                }
            }) { Text(text) }
        }
    }
}
