package `2024`.day_2

import Util.Companion.prettyPrint
import readInput
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
            val numbers = line.split(" ").map { it.toLong() }
            var valid = true
            val invalidIndices = mutableListOf<Int>()

            for (index in 0 until numbers.size - 1) {
                val diff = numbers[index + 1] - numbers[index]

                if (abs(diff) > 3 || diff == 0L) {
                    valid = false
                    invalidIndices.add(index + 1)
                } else if (index > 0) {
                    val prevDiff = numbers[index] - numbers[index - 1]
                    if ((diff > 0 && prevDiff < 0) || (diff < 0 && prevDiff > 0)) {
                        valid = false
                        invalidIndices.add(index)
                    }
                }
            }

            if (valid) {
                safe++
            } else {
                var lineSafe = false
                for (i in numbers.indices) {
                    val tempNumbers = numbers.toMutableList()
                    tempNumbers.removeAt(i)

                    var tempValid = true
                    for (j in 0 until tempNumbers.size - 1) {
                        val diff = tempNumbers[j + 1] - tempNumbers[j]
                        if (abs(diff) > 3 || diff == 0L) {
                            tempValid = false
                            break
                        }
                        if (j > 0) {
                            val prevDiff = tempNumbers[j] - tempNumbers[j - 1]
                            if ((diff > 0 && prevDiff < 0) || (diff < 0 && prevDiff > 0)) {
                                tempValid = false
                                break
                            }
                        }
                    }

                    if (tempValid) {
                        lineSafe = true
                        break
                    }
                }

                if (lineSafe) {
                    safe++
                    valid = true
                }
            }

            println("${line} : penalties: ${invalidIndices.size}, safe: $valid")
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

