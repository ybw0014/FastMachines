package net.guizhanss.fastmachines.utils

import net.guizhanss.guizhanlib.common.utils.StringUtil

fun String.toId() = StringUtil.dehumanize(this)

fun <T : Comparable<T>> T.clamp(min: T, max: T): T {
    return when {
        this < min -> min
        this > max -> max
        else -> this
    }
}
