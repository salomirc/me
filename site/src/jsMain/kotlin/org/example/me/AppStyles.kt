package org.example.me

import com.varabyte.kobweb.compose.css.borderBottom
import com.varabyte.kobweb.compose.css.transitionDuration
import org.jetbrains.compose.web.css.*

object AppStyles {
    lateinit var siteStyleSheet: SiteStyleSheet
}

class SiteStyleSheet(val sitePalette: SitePalette) : StyleSheet() {
    val navBarContainer by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
        justifyContent(JustifyContent.End)
        alignItems(AlignItems.FlexEnd)
        padding(16.px, 4.px, 0.px, 4.px)
        backgroundColor(sitePalette.nearBackground)
        borderBottom {
            width = 4.px
            style = LineStyle.Solid
            color = sitePalette.brand.primary
        }

        // media query
        media(
            query = screenMaxWidth640pxMediaQuery
        ) {
            self style {
                justifyContent(JustifyContent.SpaceBetween)
            }
        }
    }

    val barsMenuClass by style {
        display(DisplayStyle.None)
        // media query
        media(
            query = screenMaxWidth640pxMediaQuery
        ) {
            self style {
                display(DisplayStyle.Block)
            }
        }
    }

    val aboutImageContainerClass by style {
        display(DisplayStyle.Flex)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Center)
        paddingBottom(20.px)

        // media query
        media(
            query = screenMaxWidth640pxMediaQuery
        ) {
            self style {
                alignItems(AlignItems.FlexStart)
            }
        }
    }

    val aboutImageClass by style {
        maxHeight(50.vh)
        maxWidth(50.percent)
        borderRadius(10.percent)
    }

    val mobileMenuClass by style {
        // media query
        media(
            query = screenMinWidth640pxMediaQuery
        ) {
            self style {
                display(DisplayStyle.None)
            }
        }
    }

    val mobileMenuLink by style {
        textDecoration("none")
        fontFamily("Arial")
    }

    val iconButtonClass by style {
        borderRadius(4.px, 4.px, 0.px, 0.px)
        color(sitePalette.siteColor)
        backgroundColor(Color.transparent)
        borderWidth(0.px)
        padding(8.px, 16.px)
        margin(1.px, 1.px, 0.px, 1.px)
        cursor("pointer")
    }

    val regularButtonClass by style {
        backgroundColor(Color.transparent)
        borderWidth(0.px)
        borderRadius(12.px)
        padding(8.px)
        textAlign("center")
        textDecoration("none")
        fontSize(14.px)
        margin(4.px, 2.px)
        transitionDuration(0.4.s)
        cursor("pointer")

        // hover selector for a class
        self + hover style { // self is a selector for `container`
            backgroundColor(sitePalette.brand.primary)
            color(sitePalette.siteColorInverse)
        }
    }

    val regularButtonClassSelected by style {
        color(sitePalette.nearBackground)
        backgroundColor(sitePalette.brand.primary)
    }

    val displayNoneMax640pxMediaQuery by style {
        // media query
        media(
            query = screenMaxWidth640pxMediaQuery
        ) {
            self style {
                display(DisplayStyle.None)
            }
        }
    }

    val displayNone by style {
        display(DisplayStyle.None)
    }

    companion object {
        val <TBuilder> GenericStyleSheetBuilder<TBuilder>.screenMaxWidth640pxMediaQuery: CSSMediaQuery
            get() = CSSMediaQuery.MediaType(CSSMediaQuery.MediaType.Enum.Screen)
                .and(mediaMaxWidth(589.px))

        val <TBuilder> GenericStyleSheetBuilder<TBuilder>.screenMinWidth640pxMediaQuery: CSSMediaQuery
            get() = CSSMediaQuery.MediaType(CSSMediaQuery.MediaType.Enum.Screen)
                .and(mediaMinWidth(590.px))
    }
}


