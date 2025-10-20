package org.example.me

import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.silk.init.InitSilk
import com.varabyte.kobweb.silk.init.InitSilkContext
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.palette.background
import com.varabyte.kobweb.silk.theme.colors.palette.color
import org.jetbrains.compose.web.css.CSSColorValue

/**
 * @property nearBackground A useful color to apply to a container that should differentiate itself from the background
 *   but just a little.
 */
data class SitePalette(
    val siteColor: CSSColorValue,
    val siteColorInverse: CSSColorValue,
    val nearBackground: CSSColorValue,
    val cobweb: CSSColorValue,
    val brand: Brand,
    val surfaceVariant: CSSColorValue,
    val overlayTransparent: CSSColorValue,
) {
    data class Brand(
        val primary: CSSColorValue,
        val accent: CSSColorValue
    )
}

object SitePalettes {
    val light = SitePalette(
        siteColor = Colors.Black,
        siteColorInverse = Colors.Gray,
        nearBackground = Color.rgb(0xe4e4e4),
        cobweb = Colors.LightGray,
        brand = SitePalette.Brand(
            primary = Color.rgb(0x3C83EF),
            accent = Color.rgb(0xFCBA03),
        ),
        surfaceVariant = Colors.LightBlue,
        overlayTransparent = Color.rgba(0, 0, 0, 0.1f)
    )
    val dark = SitePalette(
        siteColor = Colors.Gray,
        siteColorInverse = Colors.Black,
        nearBackground = Color.rgb(0x13171F),
        cobweb = Colors.LightGray.inverted(),
        brand = SitePalette.Brand(
            primary = Color.rgb(0x3C83EF),
            accent = Color.rgb(0xF3DB5B),
        ),
        surfaceVariant = Colors.DarkSlateBlue,
        overlayTransparent = Color.rgba(255, 255, 255, 0.1f)
    )
}

fun ColorMode.toSitePalette(): SitePalette {
    return when (this) {
        ColorMode.LIGHT -> SitePalettes.light
        ColorMode.DARK -> SitePalettes.dark
    }
}

@InitSilk
fun initTheme(ctx: InitSilkContext) {
    ctx.theme.palettes.light.background = Color.rgb(0xFAFAFA)
    ctx.theme.palettes.light.color = Colors.Black
    ctx.theme.palettes.dark.background = Color.rgb(0x06080B)
    ctx.theme.palettes.dark.color = Colors.Gray
}
