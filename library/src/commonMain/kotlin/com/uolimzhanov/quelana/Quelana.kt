package com.uolimzhanov.quelana

import java.awt.*
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

class Quelana(
    private val width: Int,
    private val backgroundColor: Color,
    private val contentColor: Color,
    private val font: Font,
    private val padding: Padding,
    private val hints: RenderingHints
) {
    private var y: Int = 0
    private val elements = mutableListOf<QuelanaElement>()

    private fun Graphics2D.drawLine(line: QuelanaLine) = drawLine(line.x, line.y, line.endX, line.endY)

    private fun Graphics2D.drawText(text: QuelanaText) {
        font = this@Quelana.font.copy(
            fontSize = text.fontSize,
            style = text.fontStyle.style()
        )
        drawString(text.text, text.x, text.y)
        font = this@Quelana.font
    }

    private fun Graphics2D.drawImage(image: QuelanaImage) {
        val transformOperation = AffineTransformOp(AffineTransform(), AffineTransformOp.TYPE_NEAREST_NEIGHBOR)
        drawImage(image.image, transformOperation, image.x, image.y)
    }

    fun text(
        text: String,
        fontSize: Int = font.size,
        alignment: Alignment = Alignment.START,
        fontStyle: FontStyle = font.fontStyle(),
        fromNewLine: Boolean = false,
    ) {
        Canvas().getFontMetrics(font.copy(style = fontStyle.style(), fontSize = fontSize)).apply {
            val xPosition: (String) -> Int = { string ->
                when (alignment) {
                    Alignment.START -> 0
                    Alignment.CENTER -> (width - stringWidth(string)) / 2
                    Alignment.END -> width - stringWidth(string)
                }
            }

            fitTextBounds(text, width).let { stringList ->
                stringList.forEach { string ->
                    elements += QuelanaText(
                        text = string, fontStyle = fontStyle, fontSize = fontSize, alignment = alignment,
                        x = xPosition(string),
                        y = y + fontSize
                    )
                    if (stringList.size != 1 || fromNewLine) y += fontSize
                }
            }
        }
    }

    fun textLine(
        text: String,
        fontSize: Int = font.size,
        alignment: Alignment = Alignment.START,
        fontStyle: FontStyle = font.fontStyle(),
    ) = text(
        text = text,
        fontSize = fontSize,
        alignment = alignment,
        fontStyle = fontStyle,
        fromNewLine = true,
    )

    fun emptyLine(height: Int = font.size) = textLine("", height)

    fun line(height: Int = font.size, start: Int = 0, end: Int = width) {
        y += height / 2
        elements.add(QuelanaLine(start, y, end, y))
    }

    fun image(image: BufferedImage, alignment: Alignment = Alignment.CENTER) {
        val x = (width - image.width) / 2
        y += font.size / 2
        elements.add(QuelanaImage(image, alignment, x, y))
        y += image.height
    }

    fun build(): BufferedImage {
        val height = elements.maxOf { it.y }
        val parentWidth = width + padding.horizontal
        val parentHeight = height + padding.vertical

        val result = BufferedImage(parentWidth, parentHeight, BufferedImage.TYPE_INT_RGB).apply {
            createGraphics().let { graphics ->
                graphics.color = backgroundColor
                graphics.fillRect(0, 0, parentWidth, parentHeight)
            }
        }

        result.getSubimage(padding.start, padding.top, width, height).createGraphics().apply {
            setRenderingHints(hints)
            color = contentColor
            font = this@Quelana.font
            elements.forEach { element ->
                println(element)
                when (element) {
                    is QuelanaText -> drawText(element)
                    is QuelanaLine -> drawLine(element)
                    is QuelanaImage -> drawImage(element)
                }
            }
            dispose()
        }

        return result
    }
}

@OptIn(ExperimentalContracts::class)
inline fun buildBufferedImage(
    width: Int = 384,
    backgroundColor: Color = Color.WHITE,
    contentColor: Color = Color.BLACK,
    font: Font = Font(Font.SANS_SERIF, Font.PLAIN, 20),
    padding: Padding = Padding(20, 20, 10, 20),
    hints: RenderingHints = quelanaRenderingHints,
    builderAction: Quelana.() -> Unit
): BufferedImage {
    contract { callsInPlace(builderAction, InvocationKind.EXACTLY_ONCE) }
    return Quelana(width, backgroundColor, contentColor, font, padding, hints).apply(builderAction).build()
}