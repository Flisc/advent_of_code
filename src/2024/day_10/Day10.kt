package `2024`.day_10

import Util.Companion.prettyPrint
import printMatrix
import readInput


fun main() {
    val dayNumber = "day_10"
    var sum = 0L
    var matrix = mutableListOf<MutableList<Int>>()
    val allRoutes = mutableListOf<List<Pair<Int, Int>>>()

    fun mapInput(input: List<String>) {
            input.forEachIndexed { index, line ->
                val row = line.split("")
                    .filter { it.isNotEmpty() }
                    .map { it.toString().toIntOrNull() ?: -1 }
                    .toMutableList()
                matrix.add(row)
            }
        matrix.removeAt(0)
        println()
    }

    fun resetData() {
        sum = 0L
        matrix = mutableListOf(mutableListOf())
    }

    fun List<List<Pair<Int, Int>>>.printRoutes() {
        this.forEachIndexed { index, pairs ->
            val sb = StringBuilder()
            pairs.forEachIndexed { index, pair ->
                if (index == 0 || index == 9) {
                    sb.append(" [${pair.first}, ${pair.second}] ")
                }
                sb.append("${matrix[pair.first][pair.second]}")
            }
            println(sb.toString())
            sb.clear()
        }
    }

    fun Set<Pair<Pair<Int, Int>, Pair<Int, Int>>>.printRoutePoints() {
        this.forEachIndexed { index, pairs ->
            val sb = StringBuilder()
            sb.append(" [${pairs.first}, ${pairs.second}] ")
            println(sb.toString())
            sb.clear()
        }
    }


    fun dfsFindAll(row: Int, col: Int, target: Int, visited: MutableSet<Pair<Int, Int>>,
        currentSequence: MutableList<Pair<Int, Int>>) {
        //limit
        if (row < 0 || col < 0 || row >= matrix.size || col >= matrix[0].size) return

        if (visited.contains(row to col) || matrix[row][col] != target) return

        visited.add(row to col)
        currentSequence.add(row to col)

        if (target == 9) {
            allRoutes.add(currentSequence.toList())
            visited.remove(row to col)
            currentSequence.removeAt(currentSequence.size - 1)
            return
        }

        val directions = listOf(-1 to 0, 1 to 0, 0 to -1, 0 to 1)
        for ((dr, dc) in directions) {
            dfsFindAll(row + dr, col + dc, target + 1, visited, currentSequence)
        }

        visited.remove(row to col)
        currentSequence.removeAt(currentSequence.size - 1)
    }


    fun part1(input: List<String>): Long {
        resetData()
        mapInput(input)
        //day-6
        matrix.printMatrix()
        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                if (matrix[row][col] == 0) {
                    val visited = mutableSetOf<Pair<Int, Int>>()
                    val currentSequence = mutableListOf<Pair<Int, Int>>()
                    dfsFindAll(row, col, 0, visited, currentSequence)
                }
            }
        }

        allRoutes.printRoutes()

//        allRoutes.map { it.first() to it.last() }
//            .groupBy { it.first }
//            .forEach { println("matrix[${it.key.first}, ${it.key.second}]: ${matrix[it.key.first][it.key.second]}") }

        allRoutes.map { it.first() to it.last() }
            .groupBy { it.first }
            .map { it.value.size }
            .count()
            .prettyPrint("score")

        allRoutes.map { it.first() to it.last() }
            .size
            .prettyPrint("score v2")

        return sum
    }

    fun part2(input: List<String>): Long {
        resetData()
        mapInput(input)


        return sum
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

