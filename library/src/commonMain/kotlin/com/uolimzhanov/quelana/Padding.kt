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

fun Padding(all: Int) = Padding(all, all, all, all)

fun Padding(horizontal: Int, vertical: Int) = Padding(horizontal, vertical, horizontal, vertical)