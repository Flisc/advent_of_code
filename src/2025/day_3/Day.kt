package `2025`.day_3

import Util.Companion.prettyPrint
import print
import readInput
import kotlin.system.measureTimeMillis

class Day {
    val dayNumber = "day_3"
    fun part1(lines: List<String>): Long {
        var sum: Long = 0
        lines.forEach { line ->
            val lineNumbers: MutableList<Int> = line.map { it.digitToInt() }.toMutableList()
            val first: Int = lineNumbers.max()
//            val indexOfFirst: Int = lineNumbers.indexOf(first)
//            val second =lineNumbers.subList(indexOfFirst+1, lineNumbers.size).max()
            lineNumbers.mapIndexed{i, it -> i to it}.sortedWith(
                    compareByDescending<Pair<Int, Int>> { it.second }
                        .thenBy { it.first }
            )
//            14 - 9, 0 - 8,

//            val second = lineNumbers.max()
//            val secondIndex: Int = lineNumbers.indexOf(second)
//            val joltage = if (indexOfFirst <= secondIndex)
//                         "$first$second".toInt() else "$second$first".toInt()
//            val joltage = "$first$second".toInt()
//            line.print();print(" -> $joltage \n")
        }
        return sum
    }

    fun part2(lines: List<String>): Long {
        var sum: Long = 0
        var count: Long = 0

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

