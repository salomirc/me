package org.example.me

import com.varabyte.kobweb.compose.css.BoxShadow
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.ObjectFit
import com.varabyte.kobweb.compose.css.Visibility
import com.varabyte.kobweb.compose.css.borderBottom
import com.varabyte.kobweb.compose.css.borderTop
import com.varabyte.kobweb.compose.css.boxShadow
import com.varabyte.kobweb.compose.css.fontWeight
import com.varabyte.kobweb.compose.css.objectFit
import com.varabyte.kobweb.compose.css.visibility
import com.varabyte.kobweb.compose.css.zIndex
import org.example.me.AnimationTiming.TIME_LARGE
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.keywords.auto

object AppStyles {
    lateinit var siteStyleSheet: SiteStyleSheet
}

object AnimationTiming {
    const val TIME_VERY_FAST = 100
    const val TIME_FAST = 150
    const val TIME_REGULAR = 300
    const val TIME_LARGE = 500
    const val TIME_EXTRA_LARGE = 1000
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
            fontFamily("system-ui")
        }

        "p, ul" style {
            fontSize(1.6.cssRem)
            lineHeight(2.2.cssRem)
        }

        "input, textarea" style {
            border {
                width = 1.px
                style = LineStyle.Solid
                color = SiteColors.lightGray
            }
        }
    }

    val navBarContainer by style {
        position(Position.Fixed)
        left(0.px)
        right(0.px)
        top(0.px)
        width(auto)
        padding(16.px, 0.px, 0.px, 0.px)
        backgroundColor(sitePalette.brand.primary)
        zIndex(1)
    }

    val navBarHorizontalContainer by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
        justifyContent(JustifyContent.End)
        alignItems(AlignItems.FlexEnd)
        borderBottom {
            width = 8.px
            style = LineStyle.Solid
            color = sitePalette.brand.accent
        }

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

    val footerContainerClass by style {
        borderTop {
            width = 8.px
            style = LineStyle.Solid
            color = SiteColors.yellowIntense
        }
        paddingTop(24.px)
        backgroundColor(sitePalette.footerBackgroundColor)
        height(139.px)

        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
        justifyContent(JustifyContent.FlexEnd)
        alignItems(AlignItems.FlexStart)
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
        padding(100.px, 16.px, 16.px, 16.px)
    }

    val homePageContainerClass by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Center)
        height(100.vh)
        padding(100.px, 0.px, 0.px, 0.px)

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
                maxHeight(40.percent)
                maxWidth(100.percent)
            }
        }
    }

    val helloBoxImageClass by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.Center)
    }

    val helloImageClass by style {
        borderRadius(10.percent)
        backgroundColor(sitePalette.contourBackground)
        padding(8.px)
        boxShadow(
            BoxShadow.of(
            offsetX = 0.px,
            offsetY = 0.px,
            blurRadius = 48.px,
            color = sitePalette.overlayTransparent
            )
        )
        objectFit(ObjectFit.Contain)
        maxWidth(75.percent)

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

    val helloContainerClass by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
        justifyContent(JustifyContent.Center)
        alignItems(AlignItems.FlexStart)

        fontSize(3.vw)
        color(SiteColors.gray)
        fontFamily("sans-serif")

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

    val mobileMenuRootContainerHiddenClass by style {
        position(Position.Fixed)
        left(0.px)
        right(0.px)
        top(0.px)
        bottom((-100).px)
        width(auto)
        height(auto)
        visibility(Visibility.Hidden)
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

    val mobileMenuRootContainerVisibleClass by style {
        visibility(Visibility.Visible)
    }

    @OptIn(ExperimentalComposeWebApi::class)
    val mobileMenuOverlayContainerClass by style {
        width(100.percent)
        height(100.percent)
        backgroundColor(Color.transparent)

        transitions {
            "background-color" { duration(TIME_LARGE.ms) }
        }
    }

    val mobileMenuOverlayTransitionedContainerClass by style {
        backgroundColor(sitePalette.overlayTransparent)
    }

    @OptIn(ExperimentalComposeWebApi::class)
    val mobileMenuContainerClass by style {
        padding(16.px)
        backgroundColor(SiteColors.heavyDarkGray)
        width(90.percent)
        height(100.percent)
        rowGap(4.px)

        position(Position.Absolute)
        top(0.px)
        left((-100).percent)
        transitions {
            "left" {
                duration(TIME_LARGE.ms)
                timingFunction(AnimationTimingFunction.cubicBezier(0.22, 0.9, 0.29, 1.0))
            }
        }
    }

    @OptIn(ExperimentalComposeWebApi::class)
    val mobileMenuContainerTransitionedClass by style {
        left(0.px)
    }

    val flexColumnDefaultClass by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Column)
    }

    val textButtonClass by style {
        display(DisplayStyle.Block)
        width(100.percent)
        color(SiteColors.ultraLightGray)
        backgroundColor(SiteColors.overlayTransparent)
        borderWidth(0.px)
        padding(16.px)
        cursor("pointer")
    }

    val textButtonClassSelected by style {
        backgroundColor(sitePalette.brand.accent)
        color(SiteColors.yellow)
    }

    val textIconButtonClass by style {
        borderRadius(4.px, 4.px, 0.px, 0.px)
        color(SiteColors.ultraLightGray)
        backgroundColor(Color.transparent)
        borderWidth(0.px)
        padding(8.px, 16.px)
        margin(1.px, 4.px, 0.px, 4.px)
        cursor("pointer")
    }

    val iconButtonClass by style {
        color(SiteColors.ultraLightGray)
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

        val <TBuilder> GenericStyleSheetBuilder<TBuilder>.screenBreakMinTo799px: CSSMediaQuery
            get() = CSSMediaQuery.MediaType(CSSMediaQuery.MediaType.Enum.Screen)
                .and(mediaMaxWidth(799.px))

        val <TBuilder> GenericStyleSheetBuilder<TBuilder>.screenBreak590pxToMax: CSSMediaQuery
            get() = CSSMediaQuery.MediaType(CSSMediaQuery.MediaType.Enum.Screen)
                .and(mediaMinWidth(590.px))
    }
}


