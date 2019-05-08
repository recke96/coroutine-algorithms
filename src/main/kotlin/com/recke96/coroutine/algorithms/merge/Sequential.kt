package com.recke96.coroutine.algorithms.merge

fun <T: Comparable<T>> mergeSeq(a: List<T>, b: List<T>): List<T> {
    val merged = ArrayList<T>(a.size + b.size)
    var i = 0; var j = 0

    while (i < a.size && j < b.size){
        merged.add(if (a[i] <= b[j]) a[i++] else b[j++])
    }

    while (i < a.size){
        merged.add(a[i++])
    }
    while (j < b.size){
        merged.add(b[j++])
    }

    return merged
}
