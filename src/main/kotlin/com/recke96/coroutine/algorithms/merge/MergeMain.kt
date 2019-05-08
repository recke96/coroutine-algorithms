package com.recke96.coroutine.algorithms.merge

import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.default
import com.xenomachina.argparser.mainBody
import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.runner.Runner
import org.openjdk.jmh.runner.options.OptionsBuilder
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class MergeArguments(parser: ArgParser) {

    val prog by parser.positional(
        "PROGRAM",
        help = "Name of the program"
    )

    val verbose by parser.flagging(
        "-v", "--verbose",
        help = "enable verbose logging"
    ).default { false }

    val check by parser.flagging(
        "-c", "--check",
        help = "enable sequential check of the result"
    ).default { false }

    val n by parser.storing(
        "-n", "--n",
        help = "Size of the first array to merge"
    ) { toInt() }

    val m by parser.storing(
        "-m", "--m",
        help = "Size of the second array to merge"
    ) { toInt() }

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
        @JvmStatic
        fun main(args: Array<String>) = mainBody {
            ArgParser(args).parseInto(::MergeArguments).run {

                val opt = OptionsBuilder()
                    .include("com.recke96.coroutine.algorithms.merge.*")
                    .param("n", "100000")
                    .param("m", "100000")
                    .build()

                val result = Runner(opt).run()
            }
        }
    }


    @Param("100")
    private var n: Int = 0

    @Param("100")
    private var m: Int = 0

    private lateinit var a: List<Int>
    private lateinit var b: List<Int>

    @Setup
    fun setup(){
        a = (1..n).map { Random.nextInt() }.sorted()
        b = (1..m).map { Random.nextInt() }.sorted()
    }

    @Benchmark
    fun benchSequentialIterative(): List<Int> {
        return mergeSeq(a, b)
    }

}