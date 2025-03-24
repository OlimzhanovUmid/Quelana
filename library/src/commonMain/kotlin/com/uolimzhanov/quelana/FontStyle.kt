package com.uolimzhanov.quelana;

import java.awt.Font

enum class FontStyle {
    Regular,
    Italic,
    Bold,
    BoldItalic;

    fun style(): Int {
        return when (this) {
            Regular -> Font.PLAIN
            Italic -> Font.ITALIC
            Bold -> Font.BOLD
            BoldItalic -> Font.BOLD + Font.ITALIC
        }
    }
}

fun Font.fontStyle() = when (this.style) {
    Font.BOLD -> FontStyle.Bold
    Font.ITALIC -> FontStyle.Italic
    Font.BOLD + Font.ITALIC -> FontStyle.BoldItalic
    else -> FontStyle.Regular
}

internal fun Font.copy(
    fontName: String = this.fontName,
    style: Int = this.style,
    fontSize: Int = this.size
) = Font(fontName, style, fontSize)