package `2025`.day_1

import print
import readInput
import kotlin.math.abs

class Day {
    val dayNumber = "day_1"
    fun part1(lines: List<String>): Long {
        val dial = Dial(50)
        lines.forEach { line ->
            val direction = line[0]
            if (direction == 'L') {
                dial.rotateLeftBy(line.substring(1).toInt())
            }
            if (direction == 'R') {
                dial.rotateRightBy(line.substring(1).toInt())
            }
            println()
        }
        return lines.size.toLong()
    }

    fun part2(lines: List<String>): Long {
        return lines.size as Long
    }
}

fun main() {
    val runner = Day()
    val example = readInput("example", runner.dayNumber, "2025")
//    val example = readInput("puzzle", runner.dayNumber, "2025")
    runner.part1(example)
}

data class Dial(var curr: Int) {
    val lefLimit: Int = 0
    val rightLimit: Int = 99
    var rotationsTo0: Int = 0

    fun rotateLeftBy(newValue: Int) {
        println("curr=$curr -> rotateLeftBy: $newValue")
        if (newValue > 100) {
            val shift = newValue % 100
            this.rotationsTo0 += newValue / 100
            this.curr -= shift
            if (this.curr < lefLimit) {
                this.curr = this.rightLimit - abs(this.curr + 1)
                this.rotationsTo0++
            }
        } else if (newValue % 100 == 0) {
            println("curr=$curr -> rotateLeftBy: $newValue, same position")
            this.rotationsTo0 += newValue/100
        }
        if (newValue < 100) {
            val countZero = this.curr != 0
            this.curr -= newValue
            if (this.curr < lefLimit) {
                this.curr = this.rightLimit - abs(this.curr + 1)
                if (countZero) this.rotationsTo0++
            }
        }

        if (this.curr == 0) this.rotationsTo0++
        this.print()
    }

    fun rotateRightBy(newValue: Int) {
        println("curr=$curr -> rotateRightBy: $newValue")
        if (newValue > 100) {
            val shift = newValue % 100
            this.curr += shift
            this.rotationsTo0 += newValue / 100
            if (this.curr > rightLimit) {
                this.curr = this.lefLimit + (this.curr - this.rightLimit) - 1
            }
        } else if (newValue % 100 == 0) {
            println("curr=$curr -> rotateLeftBy: $newValue, same position")
            this.rotationsTo0 += newValue/100
        }
        if (newValue < 100) {
            val countZero = this.curr != 0
            this.curr += newValue
            if (this.curr > rightLimit) {
                this.curr = this.lefLimit + (this.curr - this.rightLimit) - 1
                if (countZero) this.rotationsTo0++
            }
        }

//        if (this.curr == 0) this.rotationsTo0++
        this.print()
    }

    override fun toString(): String = "curr=${curr}, rotations-to-zero=${rotationsTo0}"
}