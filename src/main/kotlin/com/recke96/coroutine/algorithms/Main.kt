package com.recke96.coroutine.algorithms

import com.recke96.coroutine.algorithms.merge.BenchMerge
import com.xenomachina.argparser.mainBody

/**
 * A map of all algorithm categories which can be benchmarked mapping an identifier which can be given to the program
 * to the entry point of the category-benchmark.
 */
val programs = mapOf(
    "merge" to BenchMerge.Companion::main
)

/**
 * Entry point of the application delegating the work to the algorithm category benchmark selected with [args(0)][args].
 * The mapping is specified in [programs] and each benchmark program may need additional arguments.
 */
fun main(args: Array<String>): Unit = mainBody {
    if (args.isEmpty()) {
        error("Argument PROGRAM expected")
    }

    val program = args[0]
    programs[program]?.invoke(args) ?: error("PROGRAM $program not found")
}