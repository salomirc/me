package org.example.me

import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Height
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.Width
import com.varabyte.kobweb.compose.css.borderBottom
import com.varabyte.kobweb.compose.css.fontWeight
import com.varabyte.kobweb.compose.css.height
import com.varabyte.kobweb.compose.css.objectFit
import com.varabyte.kobweb.compose.css.width
import org.jetbrains.compose.web.css.*

object AppStyles {
    lateinit var siteStyleSheet: SiteStyleSheet
}

class SiteStyleSheet(val sitePalette: SitePalette) : StyleSheet() {
    init {
        "html" style {
            // 62.5% of 16px = 10px
            fontSize(62.5.percent)
        }

        "body" style {
            // 160% of 10px = 16px
            fontSize(160.percent)
        }
    }

    val navBarContainer by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
        justifyContent(JustifyContent.End)
        alignItems(AlignItems.FlexEnd)
        padding(16.px, 4.px, 0.px, 4.px)
        backgroundColor(sitePalette.brand.primary)
        borderBottom {
            width = 8.px
            style = LineStyle.Solid
            color = sitePalette.brand.accent
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

    val homePageContainerClass by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
        justifyContent(JustifyContent.FlexStart)
        alignItems(AlignItems.Center)
        height(80.vh)
        // media query
        media(
            query = screenMaxWidth640pxMediaQuery
        ) {
            self style {
                flexDirection(FlexDirection.Column)
                height(Height.FitContent)
            }
        }
    }

    val helloComposableClass by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.FlexStart)

        width(Width.FitContent)
        padding(10.vw)

        fontFamily("Verdana")
        fontSize(3.vw)
        color(SiteColors.binayShawGray)

        // media query
        media(
            query = screenMaxWidth640pxMediaQuery
        ) {
            self style {
                fontSize(6.vw)
            }
        }
    }

    val nameDivClass by style {
        fontSize(5.vw)
        width(100.px)
        fontWeight(FontWeight.Black)
        color(sitePalette.siteColor)

        // media query
        media(
            query = screenMaxWidth640pxMediaQuery
        ) {
            self style {
                fontSize(10.vw)
            }
        }
    }

    val aboutImageClass by style {
        margin(8.px)
        borderRadius(10.percent)
        maxHeight(70.percent)

        // media query
        media(
            query = screenMaxWidth640pxMediaQuery
        ) {
            self style {
                maxWidth(70.percent)
            }
        }
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
        color(SiteColors.lightGray)
        backgroundColor(Color.transparent)
        borderWidth(0.px)
        padding(8.px, 16.px)
        margin(1.px, 4.px, 0.px, 4.px)
        cursor("pointer")
    }

    val textIconButtonClassSelected by style {
        color(SiteColors.yellow)
        backgroundColor(sitePalette.brand.accent)
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


