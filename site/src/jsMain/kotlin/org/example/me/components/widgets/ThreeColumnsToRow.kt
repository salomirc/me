package org.example.me.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.example.me.SitePalette
import org.example.me.SiteStyleSheet.Companion.screenBreakMinTo799px
import org.example.me.toSitePalette
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

object ThreeColumnsToRowContainerStyles {
    lateinit var styleSheet: ThreeColumnsToRowContainerStyleSheet
}

class ThreeColumnsToRowContainerStyleSheet(val sitePalette: SitePalette): StyleSheet() {

    val threeColumnsToRowContainer by style {
        display(DisplayStyle.Flex)
        flexDirection(FlexDirection.Row)
        justifyContent(JustifyContent.FlexStart)
        alignItems(AlignItems.FlexStart)
        gap(1.6.cssRem)

        // media query
        media(
            query = screenBreakMinTo799px
        ) {
            self style {
                flexDirection(FlexDirection.Column)
            }
        }
    }

    val box by style {
        backgroundColor(sitePalette.nearBackground)
    }

    val boxOne by style {
        width(30.percent)

        // media query
        media(
            query = screenBreakMinTo799px
        ) {
            self style {
                width(100.percent)
            }
        }
    }

    val boxTwo by style {
        width(30.percent)

        // media query
        media(
            query = screenBreakMinTo799px
        ) {
            self style {
                width(100.percent)
            }
        }
    }

    val boxThree by style {
        width(40.percent)

        // media query
        media(
            query = screenBreakMinTo799px
        ) {
            self style {
                width(100.percent)
            }
        }
    }
}

@Composable
fun ThreeColumnsToRowContainer(
    contentOne: @Composable () -> Unit = {
        Text(Lorem.lenght50)
    },
    contentTwo: @Composable () -> Unit = {
        Text(Lorem.lenght50)
    },
    contentThree: @Composable () -> Unit = {
        Text(Lorem.lenght50)
    },
    boxOneClass: List<String>? = null,
    boxTwoClass: List<String>? = null,
    boxThreeClass: List<String>? = null
) {
    ThreeColumnsToRowContainerStyles.styleSheet = ThreeColumnsToRowContainerStyleSheet(ColorMode.current.toSitePalette())
    Style(ThreeColumnsToRowContainerStyles.styleSheet)

    Div(attrs = {
        classes(ThreeColumnsToRowContainerStyles.styleSheet.threeColumnsToRowContainer)
    }) {
        fun getBoxClasses() = mutableListOf(ThreeColumnsToRowContainerStyles.styleSheet.box)
        val boxOneClasses = getBoxClasses().apply {
            boxOneClass?.let { strings ->
                this.addAll(strings)
            } ?: add(ThreeColumnsToRowContainerStyles.styleSheet.boxOne)
        }
        val boxTwoClasses = getBoxClasses().apply {
            boxTwoClass?.let { strings ->
                this.addAll(strings)
            } ?: add(ThreeColumnsToRowContainerStyles.styleSheet.boxTwo)
        }
        val boxThreeClasses = getBoxClasses().apply {
            boxThreeClass?.let { strings ->
                this.addAll(strings)
            } ?: add(ThreeColumnsToRowContainerStyles.styleSheet.boxThree)
        }
        Div(attrs = {
            classes(boxOneClasses)
        }) {
            contentOne()
        }
        Div(attrs = {
            classes(boxTwoClasses)
        }) {
            contentTwo()
        }
        Div(attrs = {
            classes(boxThreeClasses)
        }) {
            contentThree()
        }
    }
}

