package `2024`.day_3

import Util.Companion.prettyPrint
import readInput


fun main() {
    fun calculateMulForPart(input: String, regex: Regex) =
            input.split("mul")
                .filter { regex.find(it) != null }
                .filter { it.startsWith(regex.find(it)!!.value) }
                .map { it!!.substringBefore(")") }
                .map { it.substringAfter("(") }
                .map { it.split(",") }
                .map { it[0].toInt() to it[1].toInt() }
                .map { it.first * it.second }
                .sum()

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

    fun part2(input: List<String>): Int {
        val regex = Regex("""\(\d{1,3},\d{1,3}\)""")
        var sum = 0
        input.forEach { line ->
            var idx = 0
            var doEnabled = true
            var dont = false
            var tempLine = line.slice(IntRange(idx, line.length-1))
            while (idx < line.length) {
                if(doEnabled) {
                    val activeSegment = tempLine.substringBefore("don't()")
                    sum += calculateMulForPart(activeSegment, regex)
                    idx += activeSegment.length + "don't()".length
                    tempLine = line.slice(IntRange(idx, line.length-1))
                    doEnabled = false
                    dont = true
                    println("current idx: $idx , sum = $sum \n activeSegment = $activeSegment")
                } else {
                    if(dont && tempLine.contains("do()")) {
                        val inActiveSegment = tempLine.substringBefore("do()")
                        idx += inActiveSegment.length + "do()".length
                        tempLine = line.slice(IntRange(idx, line.length-1))
                        doEnabled = true
                        dont = false
                    } else {
                        println("missing do")
                        idx++
                    }
                }
            }
        }

        return sum
    }

//    val testInput = readInput("example", "day_3")
//    part1(testInput).prettyPrint("example")

//    part2(listOf("1 3 2 4 5")).prettyPrint("example")

//    val part1 = readInput("puzzle", "day_3")
//    part1(part1).prettyPrint("part1")
//
    val part2 = readInput("example", "day_3")
    part2(part2).prettyPrint("part2")

    val part2p = readInput("puzzle", "day_3")
    part2(part2p).prettyPrint("part2")

}

