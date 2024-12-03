package `2024`.day_2

import Util.Companion.prettyPrint
import readInput
import kotlin.coroutines.coroutineContext
import kotlin.math.abs


fun main() {
    fun part1(input: List<String>): Long {
        var safe = 0L
        input.forEach { line ->
            val numbers = line.split(" ")
                              .map { it.toLong() }
            var increase = true
            var decrease = true
            var valid = true
            for (index in 0..numbers.size-2) {
                val diff = numbers[index+1]- numbers[index]
                if(index == 0) {
                    if(abs(diff) <= 3 && diff != 0L) {
                        increase = if (diff > 0) true else false
                        decrease = !increase
                        continue
                    } else {
                        valid = false
                        break
                    }
                }
                if(diff > 0 && decrease) {
                    valid = false
                    break
                }
                if(diff < 0 && increase) {
                    valid = false
                    break
                }
                if(abs(diff) > 3 || diff == 0L) {
                    valid = false
                    break
                }
            }
            if(valid) {
                println("${line} SAFE")
                safe++
            }
        }

        return safe
    }

    fun part2(input: List<String>): Long {
        var safe = 0L
        input.forEach { line ->
            val numbers = line.split(" ")
                .map { it.toLong() }
            var increase = true
            var decrease = true
            var valid = true
            var penalties = 0
            val unsafePoints = mutableMapOf<Int, Long>()
            for (index in 0..numbers.size-2) {
                val diff = numbers[index+1]- numbers[index]
                if(index == 0) {
                    if(abs(diff) <= 3 && diff != 0L) {
                        increase = if (diff > 0) true else false
                        decrease = !increase
                        continue
                    } else {
                        valid = false
                        break
                    }
                }
                if(diff >= 0 && decrease) { //change direction
                    valid = false
                    penalties++
                }
                if(diff <= 0 && increase) { //change direction
                    valid = false
                    penalties++
                }
                if(abs(diff) > 3 || diff == 0L) {
                    valid = false
                }
            }

            if(valid || (!valid && penalties == 1)) {
                safe++
            }
            println("${line} : penalties: ${penalties}, safe: $valid")
            penalties = 0

        }

        return safe
    }

    val testInput = readInput("example", "day_2")
    part2(testInput).prettyPrint("example")
//    part2(listOf("1 3 2 4 5")).prettyPrint("example")

//    val part1 = readInput("puzzle", "day_2")
//    part1(part1).prettyPrint("part1")
//
//    val part2 = readInput("example", "day_1")
//    part2(part2).prettyPrint("part2")
//
    val part2p = readInput("puzzle", "day_2")
    part2(part2p).prettyPrint("part2")

}

