package `2024`.day_1

import Util.Companion.prettyPrint
import readInput
import kotlin.math.abs


fun main() {
    fun part1(input: List<String>): Long {
        // Start time: 01.12.2024 12:15
        val left = mutableListOf<String>()
        val right = mutableListOf<String>()
        input.forEach {
            val numbers = it.split("\\s+".toRegex())
            left.add(numbers[0])
            right.add(numbers[1])
        }
        left.sort()
        right.sort()
        var sum = 0L
        for ((index, value) in left.withIndex()) {
            sum += abs(value.toLong() - right[index].toLong())
        }
        // Finish time: 01.12.2024 12:50, Duration = 35 min
        return sum
    }

    fun part2(input: List<String>): Long {
        val left = mutableListOf<String>()
        val right = mutableMapOf<String, Int>()
        input.forEach {
            val numbers = it.split("\\s+".toRegex())
            left.add(numbers[0])
            var item = right.get(numbers[1])
            if (item == null) {
                right.set(numbers[1], 1)
            } else {
                right.set(numbers[1], item!! + 1)
            }
        }
        var sum = 0L
        left.forEach {
            sum += it.toLong() * right.getOrDefault(it, 0).toLong()
        }
        return sum
    }

//    val testInput = readInput("example", "day_1")
//    part1(testInput).prettyPrint("example")

//    val part1 = readInput("puzzle", "day_1")
//    part1(part1).prettyPrint("part1")

    val part2 = readInput("example", "day_1")
    part2(part2).prettyPrint("part2")

    val part2p = readInput("puzzle", "day_1")
    part2(part2p).prettyPrint("part2")

}

