package `2024`.day_3

import Util.Companion.prettyPrint
import isNumber
import readInput
import kotlin.math.abs


fun main() {
    fun part1(input: List<String>): Int {
        val operation = "mul"
        val regex = Regex("""\(\d{1,3},\d{1,3}\)""")
        return input.map { line ->
            line.split(operation)
                .filter { regex.find(it) != null }
                .filter { it.startsWith(regex.find(it)!!.value) }
                .map { it!!.substringBefore(")") }
                .map { it.substringAfter("(") }
                .map { it.split(",") }
                .map { it[0].toInt() to it[1].toInt() }
                .map { it.first * it.second }
                .sum()
        }.sum()
    }

    fun part2(input: List<String>): Long {

        return 0L
    }

//    val testInput = readInput("example", "day_3")
//    part1(testInput).prettyPrint("example")

//    part2(listOf("1 3 2 4 5")).prettyPrint("example")

    val part1 = readInput("puzzle", "day_3")
    part1(part1).prettyPrint("part1")
//
//    val part2 = readInput("example", "day_1")
//    part2(part2).prettyPrint("part2")
//
//    val part2p = readInput("puzzle", "day_2")
//    part2(part2p).prettyPrint("part2")

}

