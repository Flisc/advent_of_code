package template

import Util.Companion.prettyPrint
import print
import readInput
import kotlin.system.measureTimeMillis

class Day {
    val dayNumber = "day_x"
    fun part1(lines: List<String>): Long {
        var sum: Long = 0
        return sum
    }

    fun part2(lines: List<String>): Long {
        var sum: Long = 0
        return sum
    }
}


fun main() {
    val runner = Day()
    val time = measureTimeMillis {
        val example = readInput("example", runner.dayNumber, "2025")
//        val example = readInput("puzzle", runner.dayNumber, "2025")
        runner.part1(example).prettyPrint("res")
//        runner.part2(example).prettyPrint("res")
    }
    println("Execution time: ${time}ms")
}