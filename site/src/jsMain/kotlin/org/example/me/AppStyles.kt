package org.example.me

import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.borderBottom
import com.varabyte.kobweb.compose.css.fontWeight
import com.varabyte.kobweb.compose.css.objectFit
import com.varabyte.kobweb.compose.css.zIndex
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.keywords.auto

object AppStyles {
    lateinit var siteStyleSheet: SiteStyleSheet
}

class SiteStyleSheet(val sitePalette: SitePalette) : StyleSheet() {
    init {
        "*" style {
            fontFamily("Verdana", "sans-serif")
        }

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
        position(Position.Fixed)
        left(0.px)
        right(0.px)
        top(0.px)
        width(auto)
        padding(16.px, 4.px, 0.px, 4.px)
        backgroundColor(sitePalette.brand.primary)
        borderBottom {
            width = 8.px
            style = LineStyle.Solid
            color = sitePalette.brand.accent
        }
        zIndex(1)
    }

    val navBarHorizontalContainer by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
        justifyContent(JustifyContent.End)
        alignItems(AlignItems.FlexEnd)

        // media query
        media(
            query = screenBreakMinTo589px
        ) {
            self style {
                justifyContent(JustifyContent.SpaceBetween)
                alignItems(AlignItems.Center)
            }
        }
    }

    val barsMenuClass by style {
        display(DisplayStyle.None)
        // media query
        media(
            query = screenBreakMinTo589px
        ) {
            self style {
                display(DisplayStyle.Block)
            }
        }
    }

    val pageContainerClass by style {
        paddingTop(100.px)
    }

    val homePageContainerClass by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Center)
        height(100.vh)

        // media query
        media(
            query = screenBreakMinTo589px
        ) {
            self style {
                flexDirection(FlexDirection.Column)
                gap(10.px)
            }
        }
    }

    val helloBoxClass by style {
        flexGrow(1)
        maxHeight(100.percent)
        maxWidth(40.percent)

        // media query
        media(
            query = screenBreakMinTo589px
        ) {
            self style {
                maxHeight(100.percent)
                maxWidth(100.percent)
            }
        }
    }

    val helloBoxImageClass by style {
        textAlign("center")

        // media query
        media(
            query = screenBreakMinTo589px
        ) {
            self style {
                maxHeight(70.percent)
            }
        }
    }

    val helloImageClass by style {
        borderRadius(10.percent)
        objectFit(ObjectFit.Contain)
        maxWidth(80.percent)

        // media query
        media(
            query = screenBreakMinTo589px
        ) {
            self style {
                maxHeight(100.percent)
                maxWidth(80.percent)
            }
        }
    }

    val helloContainerClass by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.FlexStart)

        fontFamily("Verdana")
        fontSize(3.vw)
        color(SiteColors.binayShawGray)

        // media query
        media(
            query = screenBreakMinTo589px
        ) {
            self style {
                fontSize(4.vw)
            }
        }

        // media query
        media(
            query = screenBreakMinTo400px
        ) {
            self style {
                fontSize(6.vw)
            }
        }
    }

    val helloNameClass by style {
        fontSize(5.vw)
        fontFamily("Sofia")
        fontWeight(FontWeight.Black)
        color(sitePalette.siteColor)

        // media query
        media(
            query = screenBreakMinTo589px
        ) {
            self style {
                fontSize(7.vw)
            }
        }

        // media query
        media(
            query = screenBreakMinTo400px
        ) {
            self style {
                fontSize(10.vw)
            }
        }
    }

    val mobileMenuOverlayClass by style {
        position(Position.Fixed)
        left(0.px)
        right(0.px)
        top(0.px)
        bottom(0.px)
        width(auto)
        height(auto)
        backgroundColor(sitePalette.overlayTransparent)
        zIndex(2)

        // media query
        media(
            query = screenBreak590pxToMax
        ) {
            self style {
                display(DisplayStyle.None)
            }
        }
    }

    val mobileMenuClass by style {
        padding(16.px)
        backgroundColor(sitePalette.brand.primary)
        width(90.percent)
        height(100.percent)
    }

    val textButtonClass by style {
        display(DisplayStyle.Block)
        width(100.percent)
        color(SiteColors.lightGray)
        backgroundColor(sitePalette.brand.primary)
        borderWidth(0.px)
        padding(10.px)
        marginBottom(10.px)
        cursor("pointer")
    }

    val textButtonClassSelected by style {
        backgroundColor(sitePalette.brand.accent)
        color(SiteColors.yellow)
    }

    val textIconButtonClass by style {
        borderRadius(4.px, 4.px, 0.px, 0.px)
        color(SiteColors.lightGray)
        backgroundColor(Color.transparent)
        borderWidth(0.px)
        padding(8.px, 16.px)
        margin(1.px, 4.px, 0.px, 4.px)
        cursor("pointer")
    }

    val iconButtonClass by style {
        color(SiteColors.lightGray)
        backgroundColor(Color.transparent)
        borderWidth(0.px)
        padding(8.px, 16.px)
        cursor("pointer")
    }

    val simpleIconButtonClass by style {
        padding(4.px, 10.px)
        margin(0.px)
//        borderRadius(100.percent)
    }

    val textIconButtonClassSelected by style {
        color(SiteColors.yellow)
        backgroundColor(sitePalette.brand.accent)
    }

    val displayNoneMax640pxMediaQuery by style {
        // media query
        media(
            query = screenBreakMinTo589px
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
        val <TBuilder> GenericStyleSheetBuilder<TBuilder>.screenBreakMinTo400px: CSSMediaQuery
            get() = CSSMediaQuery.MediaType(CSSMediaQuery.MediaType.Enum.Screen)
                .and(mediaMaxWidth(400.px))

        val <TBuilder> GenericStyleSheetBuilder<TBuilder>.screenBreakMinTo589px: CSSMediaQuery
            get() = CSSMediaQuery.MediaType(CSSMediaQuery.MediaType.Enum.Screen)
                .and(mediaMaxWidth(589.px))

        val <TBuilder> GenericStyleSheetBuilder<TBuilder>.screenBreak590pxToMax: CSSMediaQuery
            get() = CSSMediaQuery.MediaType(CSSMediaQuery.MediaType.Enum.Screen)
                .and(mediaMinWidth(590.px))
    }
}


