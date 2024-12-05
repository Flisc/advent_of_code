package `2024`.day_5

import Util.Companion.prettyPrint
import readInput


fun main() {
    var matrix = mutableListOf<List<String>>()
    var sum = 0
    var pairRules =  mutableListOf<Pair<Int, Int>>()
    val updates = mutableListOf<List<Int>>()
    val validUpdates = mutableListOf<List<Int>>()

    fun mapInput(input: List<String>) {
        input.forEach { line ->
            if (line.contains("|")) {
                val pair = line.split("|")
                pairRules.add(Pair(pair[0].trim().toInt(), pair[1].toInt()))
            }
            if (line.contains(",")) {
                updates.add(line.split(",").map(String::trim).map(String::toInt))
            }
        }
    }

    fun List<Pair<Int, Int>>.matchFirst(firstPage: Int) =
        this.filter { it.first == firstPage }

    fun List<Pair<Int, Int>>.containsSecond(second: Int) =
        this.filter { it.second == second }.size != 0

    fun part1(input: List<String>): Int {
        mapInput(input)
        for(pages in updates) {
            for(i in 0..pages.size-1) {
                val currentRules = pairRules.matchFirst(pages[i])
                var validPage = false
                pages.subList(i+1, pages.size).forEach {
                    if (currentRules.containsSecond(it)) {
                        validPage = true
                    }
                }
                println()
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {

        return sum
    }

    val testInput = readInput("example", "day_5")
    part1(testInput).prettyPrint("example")

//    val part1 = readInput("puzzle", "day_4")
//    part1(part1).prettyPrint("part1")
//
//    val part2 = readInput("example", "day_4")
//    part2(part2).prettyPrint("part2")
//
//    val part2p = readInput("puzzle", "day_4")
//    part2(part2p).prettyPrint("part2")

}

