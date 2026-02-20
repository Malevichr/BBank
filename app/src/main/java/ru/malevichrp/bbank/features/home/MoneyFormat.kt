package ru.malevichrp.bbank.features.home

import ru.malevichrp.bbank.features.home.domain.Money
import kotlin.math.abs

fun Money.formatted(): String {
    val abs = abs(minor)
    val rub = "%,d".format(abs / 100).replace(',', ' ')
    val kop = abs % 100
    val sign = if (minor < 0) "-" else ""
    return if (kop == 0L) {
        "$sign$rub ₽"
    } else {
        val kopPart = kop.toString().padStart(2, '0')
        "$sign$rub,$kopPart ₽"
    }
}