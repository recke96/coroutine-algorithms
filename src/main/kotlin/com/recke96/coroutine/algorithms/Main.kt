package com.recke96.coroutine.algorithms

import com.recke96.coroutine.algorithms.merge.BenchMerge
import com.xenomachina.argparser.mainBody

val programs = mapOf(
    "merge" to BenchMerge.Companion::main
)

fun main(args: Array<String>) = mainBody {
    if (args.isEmpty()) {
        error("Argument PROGRAM expected")
    }

    val program = args[0]
    programs[program]?.invoke(args) ?: error("PROGRAM $program not found")
}