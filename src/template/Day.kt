package template

import print
import readInput

class Day {
    val dayNumber = "Day_X"
    fun part1(lines: List<String>): Long {
        return lines.size as Long
    }

    fun part2(lines: List<String>): Long {
        return lines.size as Long
    }
}

fun main() {
    val runner = Day()
    val puzzle = readInput("puzzle", runner.dayNumber)
    val example = readInput("example", runner.dayNumber)
//    check(runner.part1(puzzle) == 123L)
    runner.part1(puzzle).print()
}