package com.recke96.coroutine.algorithms.merge

/**
 * Simple strictly sequential implementation of a generic merge algorithm.
 */
fun <T : Comparable<T>> mergeSeq(a: Array<T>, fromA: Int = 0, sizeA: Int = a.size,
                                 b: Array<T>, fromB: Int = 0, sizeB: Int = b.size,
                                 out: Array<T>, fromOut: Int = 0) {
    require(fromA >= 0) { "From index of array a can't be less than 0 but was $fromA" }
    require( fromA + sizeA <= a.size) { "The sub-array from $fromA with size $sizeA would produce an index out of bounds on array a with size ${a.size}" }

    require(fromB >= 0) { "From index of array b can't be less than 0 but was $fromB" }
    require( fromB + sizeB <= b.size) { "The sub-array from $fromB with size $sizeB would produce an index out of bounds on array b with size ${b.size}" }

    require(fromOut >= 0) { "From index of array out can't be less than 0 but was $fromOut" }
    require( fromOut + sizeA + sizeB <= out.size) { "The input arrays of size $sizeA + $sizeB would produce an index out of bounds on array out from $fromOut with size ${out.size}" }

    var i = fromA
    var j = fromB
    var k = fromOut

    while (i < sizeA && j < sizeB) {
        out[k++] = if (a[i] <= b[j]) a[i++] else b[j++]
    }

    while (i < sizeA) {
        out[k++] = a[i++]
    }
    while (j < sizeB) {
        out[k++] = b[j++]
    }

}
