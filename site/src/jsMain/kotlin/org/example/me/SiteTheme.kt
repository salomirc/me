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
        siteColorInverse = Colors.White,
        nearBackground = Color.rgb(0xe4e4e4),
        cobweb = Colors.LightGray,
        brand = SitePalette.Brand(
            primary = SiteColors.darkGray,
            accent = SiteColors.blueMarin,
        ),
        surfaceVariant = Colors.LightBlue,
        overlayTransparent = Color.rgba(0, 0, 0, 0.1f)
    )
    val dark = SitePalette(
        siteColor = SiteColors.lightGray,
        siteColorInverse = Colors.Black,
        nearBackground = Color.rgb(0x13171F),
        cobweb = Colors.LightGray.inverted(),
        brand = SitePalette.Brand(
            primary = SiteColors.heavyDarkGray,
            accent = SiteColors.blueMarin,
        ),
        surfaceVariant = Colors.DarkSlateBlue,
        overlayTransparent = Color.rgba(255, 255, 255, 0.1f)
    )
}

object SiteColors {
    val binayShawGray = Color.rgb(0x696969)
    val darkGray = Color.rgb(0x363636)
    val heavyDarkGray = Color.rgb(0x181818)
    val blueMarin = Color.rgb(0x4b959f)
    val lightGray = Color.rgb(0xe8e8e8)
    val yellow = Color.rgb(0xfae88a)
    val ocru = Color.rgb(0xbab15b)
    val occruLight = Color.rgb(0xdad86c)
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
    ctx.theme.palettes.dark.color = Colors.White
}
