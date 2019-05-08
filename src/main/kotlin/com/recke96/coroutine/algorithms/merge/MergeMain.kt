package com.recke96.coroutine.algorithms.merge

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import com.xenomachina.argparser.mainBody
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder
import java.util.concurrent.TimeUnit
import kotlin.random.Random

/**
 * Additional arguments for the benchmark program for merge algorithms.
 */
class MergeArguments(parser: ArgParser) {

    /**
     * Name of the program. Only used by [com.recke96.coroutine.algorithms.main] to decide which program to run.
     */
    val prog by parser.positional(
        "PROGRAM",
        help = "Name of the program"
    )

    /**
     * Enables verbose output of the program.
     * Currently ignored!
     */
    val verbose by parser.flagging(
        "-v", "--verbose",
        help = "enable verbose logging"
    ).default { false }

    /**
     * Enables a check of the result after the algorithm.
     * Currently ignored.
     */
    val check by parser.flagging(
        "-c", "--check",
        help = "enable sequential check of the result"
    ).default { false }

    /**
     * Size of the first input list of the merge-algorithm.
     */
    val n by parser.storing(
        "-n", "--n",
        help = "Size of the first array to merge"
    ) { toInt() }.default { 100 }

    /**
     * Size of the second input list of the merge-algorithm.
     */
    val m by parser.storing(
        "-m", "--m",
        help = "Size of the second array to merge"
    ) { toInt() }.default { 100 }

    /**
     * Number of threads to use for algorithms which work on a specific number of threads.
     * Defaults to the number of processors available to this JVM instance ([Runtime.availableProcessors]).
     */
    val p by parser.storing(
        "-p", "--processes",
        help = "Number of processes (threads or coroutines) to use if applicable"
    ) { toInt() }
        .default { Runtime.getRuntime().availableProcessors() }
}

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Benchmark)
open class BenchMerge {

    companion object {

        /**
         * Entry point of the benchmark program for merge algorithms.
         * Parses the [args] into [MergeArguments] and starts the jmh-benchmark.
         */
        @JvmStatic
        fun main(args: Array<String>) = mainBody {
            ArgParser(args).parseInto(::MergeArguments).run {

                val opt = OptionsBuilder()
                    .include("com.recke96.coroutine.algorithms.merge.*")
                    .param("n", n.toString())
                    .param("m", m.toString())
                    .param("p", p.toString())
                    .build()

                Runner(opt).run()
            }
        }
    }


    @Param("100")
    private var n: Int = 100

    @Param("100")
    private var m: Int = 100

    @Param("4")
    private var p: Int = 4

    private lateinit var a: List<Int>
    private lateinit var b: List<Int>

    @Setup
    fun setup() {
        a = (1..n).map { Random.nextInt() }.sorted()
        b = (1..m).map { Random.nextInt() }.sorted()
    }

    @Benchmark
    fun benchSequentialIterative(): List<Int> {
        return mergeSeq(a, b)
    }

}