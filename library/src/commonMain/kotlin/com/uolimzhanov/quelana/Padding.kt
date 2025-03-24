package com.uolimzhanov.quelana

class Padding(
    val start: Int,
    val top: Int,
    val end: Int,
    val bottom: Int
) {
    val horizontal = start + end
    val vertical = bottom + top
}