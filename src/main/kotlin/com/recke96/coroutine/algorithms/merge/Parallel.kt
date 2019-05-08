package com.recke96.coroutine.algorithms.merge

/**
 * Given [elem] and a [list] not containing [elem] [rank] is the number of elements
 * in [list] which are smaller than [elem].
 *
 * Used by some parallel merge algorithms to split up the input lists.
 * Leverage binary search to obtain a runtime of O(log [list].size).
 */
fun <T : Comparable<T>> rank(elem: T, list: List<T>): Int {
    var index = list.size / 2; var jump = list.size / 4
    while (jump > 0){
        if (list[index] < elem){
            index += jump
        } else {
            index -= jump
        }
        jump /= 2
    }
    return index
}