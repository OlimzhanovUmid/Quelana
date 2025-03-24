package com.uolimzhanov.quelana

import java.awt.RenderingHints

val quelanaRenderingHints = RenderingHints(
    mapOf(
        RenderingHints.KEY_ANTIALIASING to RenderingHints.VALUE_ANTIALIAS_ON,
        RenderingHints.KEY_TEXT_ANTIALIASING to RenderingHints.VALUE_TEXT_ANTIALIAS_ON,
        RenderingHints.KEY_RENDERING to RenderingHints.VALUE_RENDER_QUALITY,
        RenderingHints.KEY_STROKE_CONTROL to RenderingHints.VALUE_STROKE_PURE
    )
)