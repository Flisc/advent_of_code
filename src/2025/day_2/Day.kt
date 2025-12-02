package `2025`.day_2

import Util.Companion.prettyPrint
import print
import readInput
import kotlin.system.measureTimeMillis

class Day {
    val dayNumber = "day_2"
    fun part1(lines: List<String>): Long {
        var sum: Long = 0
        lines.forEach { line ->
            val ranges = line.split(",").filter { it.isNotBlank() }
            ranges.forEach { range ->
                println("processing $range")
                for (i in range.split("-")[0].toLong() .. range.split("-")[1].toLong()) {
                    if (i.toString().length % 2 == 0) {
                        val size = i.toString().length
                        if (i.toString().substring(0, size / 2) == i.toString().substring(size / 2, size)) {
                            println("id-ok: ${i}")
                            sum += i
                        }
                    }
                }
            }
        }
        return sum
    }

    fun part2(lines: List<String>): Long {
        var sum: Long = 0
        var count: Long = 0
        lines.forEach { line ->
            val ranges = line.split(",").filter { it.isNotBlank() }
            ranges.forEach { range ->
                println("processing $range")
                for (i in range.split("-")[0].toLong() .. range.split("-")[1].toLong()) {
                    val number = i.toString()
                    val firstCombination = number.map { it.digitToInt() }.toSet().joinToString("")
                    val splitted = number.split(firstCombination)
                    val valid = splitted.all { it.isBlank() } && number.substringBefore(firstCombination).isBlank()
                    val occurences = splitted.size -1
                    if (valid && occurences >= 2) {
                        println("id-ok: ${i}")
                        sum += i
                        count++
                    }
                }
            }
        }
        println("\n sum = $sum, count = $count")
        return sum
    }
}

fun main() {
    val runner = Day()
    val time = measureTimeMillis {
        val example = readInput("example", runner.dayNumber, "2025")
//        val example = readInput("puzzle", runner.dayNumber, "2025")
//        runner.part1(example).print()
        runner.part2(example).prettyPrint("res")
    }
    println("Execution time: ${time}ms")
}

