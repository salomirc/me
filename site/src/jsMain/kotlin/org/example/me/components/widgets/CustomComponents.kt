package org.example.me.components.widgets

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
import org.jetbrains.compose.web.dom.*

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
fun TextButton(
    text: String,
    textFontSize: CSSNumeric = 12.px,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val classes: MutableList<String> = mutableListOf(AppStyles.siteStyleSheet.textButtonClass).apply {
        if (isSelected) add(AppStyles.siteStyleSheet.textButtonClassSelected)
    }
    Button(attrs ={
        type(ButtonType.Button)
        classes(classes)
        onClick {
            onClick()
        }
    }) {
        Span(attrs = {
            style {
                fontSize(textFontSize)
            }
        }) {
            Text(text)
        }
    }
}

@Composable
fun SimpleTextButton(
    text: String,
    onClick: () -> Unit,
    classes: List<String>? = null,
) {
    Button(attrs ={
        type(ButtonType.Button)
        classes(
            mutableListOf(AppStyles.siteStyleSheet.simpleTextButtonClass).apply {
                classes?.let {
                    addAll(it)
                }
            }
        )
        onClick {
            onClick()
        }
    }) {
        Text(text)
    }
}

@Composable
fun IconButton(
    fontSize: CSSNumeric = 30.px,
    onClick: () -> Unit,
    id: String? = null,
    classes: List<String>? = null,
    inlineStyle: (StyleScope.() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val styles = mutableListOf(AppStyles.siteStyleSheet.iconButtonClass).apply {
        classes?.let { this.addAll(it) }
    }
    Button(attrs ={
        id?.let { s ->
            id(s)
        }
        onClick {
            onClick()
        }
        classes(styles)
        style {
            inlineStyle?.invoke(this)
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
    styles: List<String>? = null,
    inlineStyle: (StyleScope.() -> Unit)? = null
) {
    val classes: MutableList<String> = mutableListOf(AppStyles.siteStyleSheet.textIconButtonClass).apply {
        styles?.let { this.addAll(it) }
        if (isSelected) add(AppStyles.siteStyleSheet.textIconButtonClassSelected)
    }
    Button(attrs ={
        type(ButtonType.Button)
        classes(classes)
        onClick {
            onClick()
        }
    }) {
        Div(attrs = {
            style {
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
                justifyContent(JustifyContent.Center)
                alignItems(AlignItems.Center)
                gap(2.px)
                inlineStyle?.invoke(this)
            }
        }
        ) {
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

@Composable
fun Spacer(
    width: CSSNumeric = 0.px,
    height: CSSNumeric = 0.px,
    classes: List<String>? = null,
    style: (StyleScope.() -> Unit)? = null
) {
    Div(attrs = {
        classes?.let { list ->
            classes(list)
        }
        style {
            width(width)
            height(height)
            style?.let { lambda ->
                this.lambda()
            }
        }
    })
}

@Composable
fun PageContainer(
    content: @Composable () -> Unit
) {
    Div(attrs = {
        id("page_content")
        classes(AppStyles.siteStyleSheet.pageContainerClass)
    }) {
        content()
    }
}

@Composable
fun PageTitle(
    text: String
) {
    H1(attrs = {
        style {
            fontSize(2.8.cssRem)
        }
    }) {
        Text(text)
    }
}

/**
 * Inserts a forced page break for html2pdf.js exports.
 *
 * Usage: PdfPageBreak()  start a new PDF page below
 */
@Composable
fun PdfPageBreak() {
    Div(
        attrs = {
            classes("pdf-break-before")
            style {
                // Ensure the break has zero height but still registers in layout
                height(0.px)
                width(100.percent)
                display(DisplayStyle.Block)
            }
        }
    )
}

@Composable
fun PdfAvoidBreak(content: @Composable () -> Unit) {
    Div(
        attrs = {
            classes("pdf-avoid-break")
        }
    ) {
        content()
    }
}

