package org.example.me.utils

import org.w3c.dom.HTMLElement
import kotlin.math.ceil

/**
 * Convert millimeters to CSS pixels using 96 DPI.
 * 1 in = 25.4 mm = 96 px.
 */
private fun mmToPx(mm: Double): Double = mm * (96.0 / 25.4)

/**
 * Resize the element so its height is an integer multiple of 297 mm (A4 height).
 *
 * Example:  if content = 1.6 A4 high, the element becomes 2 × 297 mm tall.
 */
fun adjustHeightToA4Multiples(element: HTMLElement) {
    val a4HeightPx = mmToPx(297.0)
    val contentHeightPx = element.scrollHeight.toDouble()
    val pageCount = (ceil(contentHeightPx / a4HeightPx))
    val newHeightMm = pageCount * 297.0 - 1

    element.style.height = "${newHeightMm}mm"
    console.log(
        "Adjusted '${element.id}' to ${pageCount}×A4 (${newHeightMm} mm, +margin)"
    )
}

