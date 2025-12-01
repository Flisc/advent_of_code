package `2025`.day_1

import print
import readInput
import kotlin.math.abs

class Day {
    val dayNumber = "day_1"
    fun part1(lines: List<String>): Long {
        val dial = Dial(50)
        dial.rotateLeftBy(68)
        dial.rotateLeftBy(30)
        dial.rotateRightBy(48)
        dial.rotateLeftBy(5)

        dial.rotateRightBy(60)
        return lines.size.toLong()
    }

    fun part2(lines: List<String>): Long {
        return lines.size as Long
    }
}

fun main() {
    val runner = Day()
    val example = readInput("example", runner.dayNumber, "2025")
//    val puzzle = readInput("puzzle", runner.dayNumber)
//    check(runner.part1(puzzle) == 123L)
    runner.part1(example)
}

data class Dial(var curr: Int) {
    val lefLimit: Int = 0
    val rightLimit: Int = 99

    fun rotateLeftBy(newValue: Int) {
        this.curr -= newValue
        if (this.curr < lefLimit) {
            this.curr = this.rightLimit - abs(this.curr + 1)
        }
        this.print()
    }

    fun rotateRightBy(newValue: Int) {
        this.curr += newValue
        if (this.curr > this.rightLimit) {
            this.curr = this.lefLimit + (this.rightLimit - this.curr) + 1
        }
        this.print()
    }
}