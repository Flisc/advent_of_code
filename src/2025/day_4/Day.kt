package `2025`.day_4

import Util.Companion.prettyPrint
import readInput
import kotlin.system.measureTimeMillis

class Day {
    val dayNumber = "day_4"
    val matrix = mutableListOf<MutableList<String>>()

    fun part1(lines: List<String>): Long {
        var count: Long = 0
        lines.forEach{ matrix.add(it.split("").filter { it.isNotEmpty() }.toMutableList()) }
        for (i in 0..matrix.size-1){
            for (j in 0..matrix[i].size-1){
                if(matrix[i][j] == "@"){
                    if(countAdiacentRolls(i, j) < 4) {
                        count++
                    }
                }
            }
        }
        return count
    }

    fun part2(lines: List<String>): Long {
        var count: Long = 0
        var removed: Long = 0
        lines.forEach { matrix.add(it.split("").filter { it.isNotEmpty() }.toMutableList()) }

        do {
            removed = 0
            for (i in 0 .. matrix.size - 1) {
                for (j in 0 .. matrix[i].size - 1) {
                    if (matrix[i][j] == "@") {
                        if (countAdiacentRolls(i, j) < 4) {
                            matrix[i][j] = "x";
                            removed = 1
                            count++
                            println("removed: $i - $j")
                        }
                    }
                }
            }
        }
        while (removed != 0L)

        return count
    }

    fun countAdiacentRolls(row: Int, col: Int): Int {
        var count = 0
        val directions = arrayOf(
                intArrayOf(-1, -1), intArrayOf(-1, 0), intArrayOf(-1, 1),
                intArrayOf(0, -1),  /* item */         intArrayOf(0, 1),
                intArrayOf(1, -1),  intArrayOf(1, 0),  intArrayOf(1, 1)
        )

        val numRows = matrix.size
        val numCols = matrix[0].size

        for ((dr, dc) in directions) {
            val newRow = row + dr
            val newCol = col + dc

            if (newRow in 0 until numRows && newCol in 0 until numCols) {
                if (matrix[newRow][newCol] == "@") {
                    count++
                }
            }
        }
        return count
    }
}




fun main() {
    val runner = Day()
    val time = measureTimeMillis {
        val example = readInput("example", runner.dayNumber, "2025")
//        val example = readInput("puzzle", runner.dayNumber, "2025")
//        runner.part1(example).prettyPrint("res")
        runner.part2(example).prettyPrint("res")
    }
    println("Execution time: ${time}ms")
}

