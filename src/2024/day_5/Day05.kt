package `2024`.day_5

import Util.Companion.prettyPrint
import readInput


fun main() {
    var matrix = mutableListOf<List<String>>()
    var sum = 0
    var pairRules =  mutableListOf<Pair<Int, Int>>()
    var updates = mutableListOf<List<Int>>()
    var validUpdates = mutableListOf<List<Int>>()

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

    fun List<Pair<Int, Int>>.matchSecond(second: Int): List<Pair<Int, Int>> =
        this.filter { it.second == second }

    fun resetData() {
        sum = 0
        pairRules = mutableListOf<Pair<Int, Int>>()
        updates = mutableListOf<List<Int>>()
        validUpdates = mutableListOf<List<Int>>()
    }

    fun part1(input: List<String>): Int {
        resetData()
        mapInput(input)


        for(pages in updates) {
            var validPage = true
            for(i in 0..pages.size-1) {
                val currentRules = pairRules.matchFirst(pages[i])
                currentRules.forEach {
                    if(pages.contains(it.second) &&
                        !pages.subList(i+1, pages.size).contains(it.second)) {
                        validPage = false
                    }
                }
            }
            if(validPage) {
                validUpdates.add(pages)
            }
            validPage = false
        }

        return validUpdates.map { it[it.size/2] }.sum()
    }

    fun part2(input: List<String>): Int {
        resetData()
        mapInput(input)
        var invalidUpdates = mutableListOf<List<Int>>()

        for(pages in updates) {
            var validPage = true
            var wrongPoints = mutableMapOf<Int, Int>()
            for(i in 0..pages.size-1) {
                val currentRules = pairRules.matchFirst(pages[i])
                currentRules.forEach {
                    if(pages.contains(it.second) &&
                        !pages.subList(i+1, pages.size).contains(it.second)) {
                        validPage = false
                    }
                }
            }

            if(validPage.not()) {
                var newPages = mutableListOf<Int>()
                newPages.addAll(pages)
                for(i in 0..newPages.size-1) {
                    val currentRule = pairRules.matchFirst(newPages[i])
                    val badPoints = newPages.subList(0, i+1).intersect(currentRule.map { it.second })
                    if(badPoints.isNotEmpty()) {
                        badPoints.forEach {
                            var temp = newPages[i]
                            newPages[i] = newPages[newPages.indexOf(it)]
                            newPages[newPages.indexOf(it)] = temp
                        }
                    }
                }
                invalidUpdates.add(newPages)
            }
            validPage = true
        }

        invalidUpdates.prettyPrint()


        return invalidUpdates.map { it[it.size/2] }.sum()
    }

//    val testInput = readInput("example", "day_5")
//    part1(testInput).prettyPrint("example")
//
//    val part1 = readInput("puzzle", "day_5")
//    part1(part1).prettyPrint("part1")
//
    val part2 = readInput("example", "day_5")
    part2(part2).prettyPrint("part2")
//
    val part2p = readInput("puzzle", "day_5")
    part2(part2p).prettyPrint("part2")

}

