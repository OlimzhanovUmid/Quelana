package com.uolimzhanov.quelana

import java.awt.image.BufferedImage

class QuelanaImage(
    val image: BufferedImage,
    val alignment: Alignment,
    override val x: Int,
    override val y: Int,
) : QuelanaElement