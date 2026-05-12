package ru.malevichrp.bbank.features.home.presentation

import ru.malevichrp.bbank.core.Money
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
fun Money.formattedWithPlus(): String {
    val sign = if (minor > 0) "+" else ""
    return sign + formatted()
}