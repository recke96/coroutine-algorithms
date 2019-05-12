package com.recke96.coroutine.algorithms.merge

/**
 * Simple strictly sequential implementation of a generic merge algorithm.
 */
fun <T : Comparable<T>> mergeSeq(a: Array<T>, b: Array<T>, out: Array<T>) {
    require(a.size + b.size == out.size) { "Output array has to have to have size ${a.size} + ${b.size} but has ${out.size}" }
    var i = 0
    var j = 0
    var k = 0

    while (i < a.size && j < b.size) {
        out[k++] = if (a[i] <= b[j]) a[i++] else b[j++]
    }

    while (i < a.size) {
        out[k++] = a[i++]
    }
    while (j < b.size) {
        out[k++] = b[j++]
    }

}
