package com.uolimzhanov.quelana

import java.awt.FontMetrics

class QuelanaText(
    val text: String,
    val fontStyle: FontStyle,
    val fontSize: Int,
    val alignment: Alignment,
    override val x: Int,
    override val y: Int,
) : QuelanaElement

internal fun FontMetrics.fitTextBounds(
    text: String,
    maxWidth: Int
): List<String> {
    val maxCharWidth = widths.maxOrNull() ?: return emptyList()
    return if (stringWidth(text) > maxWidth) {
        text.chunked((maxWidth / maxCharWidth) - 1).let { chunks ->
            chunks.mapIndexed { index, string ->
                if (index < chunks.lastIndex) {
                    if (string.lastOrNull() != ' ') "$string${Typography.ndash}" else string.trimEnd()
                } else string
            }
        }
    } else listOf(text)
}