@file:Suppress("UnsafeCastFromDynamic")
package org.example.me.utils

import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLElement
import kotlin.js.Promise
import kotlin.js.json

private fun ensureHtml2PdfLoaded(): Promise<Unit> {
    return Promise { resolve, reject ->
        val w = js("window").unsafeCast<dynamic>()
        if (w.html2pdf != undefined) { resolve(Unit); return@Promise }
        val script = document.createElement("script")
        script.asDynamic().src =
            "https://cdnjs.cloudflare.com/ajax/libs/html2pdf.js/0.12.1/html2pdf.bundle.min.js"
        script.asDynamic().onload = { resolve(Unit) }
        script.asDynamic().onerror = { reject(Throwable("Failed to load html2pdf.js")) }
        document.head?.appendChild(script)
            ?: reject(Throwable("No <head> element to attach html2pdf.js"))
    }
}

fun exportPageToPdf(
    elementId: String = "root",
    fileName: String = "MyPortfolio_A4.pdf"
) {
    ensureHtml2PdfLoaded().then {
        val element = document.getElementById(elementId) as? HTMLElement ?: run {
            console.warn("exportPageToPdfIterative: Element '$elementId' not found.")
            return@then
        }

        val initialContentHeightPx = element.scrollHeight

        val win = js("window").unsafeCast<dynamic>()
        val html2pdf = win.html2pdf

        // Common options for both passes
        val options = json(
            "margin" to 0,
            "filename" to fileName,
            "image" to json(
                "type" to "jpeg",
                "quality" to 0.98
            ),
            "html2canvas" to json(
                "scale" to 2,
                "useCORS" to true,
                "allowTaint" to false,
                "scrollX" to 0,
                "scrollY" to 0
            ),
            "jsPDF" to json(
                "unit" to "mm",
                "format" to "a4",
                "orientation" to "portrait"
            ),
            "pagebreak" to json(
                "before" to arrayOf(".pdf-break-before"),
                "after" to arrayOf(".pdf-break-after"),
                "avoid" to arrayOf(".pdf-avoid-break")
            )
        )

        // -------- PHASE 1: Generate temporary PDF and measure pages ----------
        html2pdf().set(options).from(element).toPdf().get("pdf").then { pdf ->
            val totalPages = pdf.internal.getNumberOfPages() as Int
            console.log("Phase 1 → estimated $totalPages A4 pages")

            // Compute new height based on actual number of pages
            val newHeightMm = totalPages * 297.0 - 1
            element.style.height = "${newHeightMm}mm"
            console.log("Phase 2 → Adjusted element height to $newHeightMm mm")

            // Small delay to let browser reflow layout
            window.setTimeout({
                // -------- PHASE 2: Generate final PDF ----------
                html2pdf().set(options).from(element).toPdf().get("pdf").then { finalPdf ->
                    val finalPages = finalPdf.internal.getNumberOfPages() as Int
                    console.log("Final PDF has $finalPages pages (expected $totalPages)")
                    finalPdf.setDisplayMode("fullwidth", "continuous")
                    finalPdf.save(fileName)
                    element.style.height = "${initialContentHeightPx}px"
                    console.log(
                        "Adjusted back ${element.id}' to ${initialContentHeightPx}px"
                    )
                }.catch { err: dynamic ->
                    console.error("Phase 2 export error:", err)
                }
            }, 300)
        }.catch { err: dynamic ->
            console.error("Phase 1 export error:", err)
        }
    }.catch { err ->
        console.error("ensureHtml2PdfLoaded error:", err)
    }
}
