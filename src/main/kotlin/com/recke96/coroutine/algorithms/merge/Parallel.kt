package com.recke96.coroutine.algorithms.merge

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Given [elem] and a [array] not containing [elem] [rank] is the number of elements
 * in [array] which are smaller than [elem].
 *
 * Used by some parallel merge algorithms to split up the input lists.
 * Leverage binary search to obtain a runtime of O(log [array].size).
 */
fun <T : Comparable<T>> rank(elem: T, array: Array<T>): Int {
    var index = array.size / 2
    var jump = array.size / 4
    while (jump > 0) {
        if (array[index] < elem) {
            index += jump
        } else {
            index -= jump
        }
        jump /= 2
    }
    return index
}

/**
 * A naive parallel implementation assuming [a].size + [b].size processors and determining the position
 * of each input element in a separate coroutine.
 *
 * Assuming there are [a].size + [b].size processors the runtime of this algorithm is O(log (max([a].size, [b].size))
 * because it uses the [rank] algorithm and it is NOT work-optimal.
 *
 * Really heavy on memory!
 *
 * @param dispatcher Dispatcher to [launch] the coroutines.
 */
fun <T : Comparable<T>> mergeParNaive(a: Array<T>, b: Array<T>, out: Array<T>, dispatcher: CoroutineDispatcher = Dispatchers.Default): Unit {
    require(a.size + b.size == out.size) { "Output array has to have to have size ${a.size} + ${b.size} but has ${out.size}" }
    runBlocking {
        for (i in 0 until a.size + b.size) {
            launch(dispatcher) {
                if (i < a.size) {
                    out[i + rank(a[i], b)] = a[i]
                } else {
                    val j = i - a.size
                    out[j + rank(b[j], a)] = b[j]
                }
            }
        }
    }
}

