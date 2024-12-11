package `2024`.day_11

import Util.Companion.prettyPrint
import readInput


fun main() {
    val dayNumber = "day_11"
    var res = 0L
    var stones = mutableListOf<Long>()

    fun mapInput(input: List<String>) {
            input.forEachIndexed { index, line ->
                val row = line.split(" ")
                    .filter { it.isNotEmpty() }
                    .map { it.toString().toLongOrNull() ?: -1 }
                    .toMutableList()
                stones.addAll(row)
            }
        println()
    }

    fun resetData() {
        res = 0L
        stones = mutableListOf()
    }

    fun Long.applyRule(): List<Long> {
        return when {
            this == 0L -> listOf(1L)
            this.toString().length % 2 == 0 -> {
                val digits = this.toString()
                val mid = digits.length / 2
                val left = digits.substring(0, mid).toLong()
                val right = digits.substring(mid).toLong()
                listOf(left, right)
            }
            else -> listOf(this * 2024)
        }
    }


    fun part1(input: List<String>): Long {
        resetData()
        mapInput(input)
        var i = 0
        var blinks = 75
        var actualSize = stones.size
        for (blink in 1..blinks) {
            while( i < stones.size) {
                val rule = stones[i].applyRule()
                stones[i] = rule.first()
                if(rule.size > 1) {
                    stones.add(i+1, rule.last())
                    i+=2
                    actualSize++
                } else {
                    i++
                }
            }
            i=0
            stones.size.prettyPrint("after blink: $blink")
        }


        return stones.size.toLong()
    }

    fun part2(input: List<String>): Long {
        resetData()
        mapInput(input)


        return res
    }

//    val testInput = readInput("example", dayNumber)
//    part1(testInput).prettyPrint("example")

    val part1 = readInput("puzzle", dayNumber)
    part1(part1).prettyPrint("part1")


//    val part2 = readInput("example", "day_9")
//    part2(part2).prettyPrint("part2")


//    val part2p = readInput("puzzle", "day_9")
//    part2(part2p).prettyPrint("part2")

}

