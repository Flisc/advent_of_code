package `2025`.day_7

import Util.Companion.prettyPrint
import printMatrix
import readInput
import kotlin.system.measureTimeMillis

class Day {
    // https://adventofcode.com/2025/day/7
    val dayNumber = "day_7"
    val matrix = mutableListOf<MutableList<String>>()

    fun part1(lines: List<String>): Long {
        var splitCounter: Long = 0
        lines.forEach { line -> matrix.add(line.split("").filter { it.isNotBlank() }.toMutableList()) }
        matrix.printMatrix();
        outer@ for (i in 0..matrix.size-1){
            for (j in 0..matrix[i].size-1){
                val item = matrix[i][j]
                if(item == "S"){
                    matrix[i+1][j] = "|"
                    continue
                }
                if (item == "|") {
                    if (i + 1 == matrix.size) continue
                    if (matrix[i + 1][j] == "^") {
                        splitCounter++
                        if (matrix[i + 1][j - 1] == ".") {
                            matrix[i + 1][j - 1] = "|"
                        }
                        if (matrix[i + 1][j + 1] == ".") {
                            matrix[i + 1][j + 1] = "|"
                        }
                    }
                    if (matrix[i + 1][j] == ".") {
                        matrix[i + 1][j] = "|"
                    }
                }
            }
        }
        matrix.printMatrix();
        return splitCounter
    }

    fun part2(lines: List<String>): Long {
        var sum: Long = 0

        return sum
    }

}


fun main() {
    val runner = Day()
    val time = measureTimeMillis {
//        val example = readInput("example", runner.dayNumber, "2025")
        val example = readInput("puzzle", runner.dayNumber, "2025")
        runner.part1(example).prettyPrint("res")
//        runner.part2(example).prettyPrint("res")
    }
    println("Execution time: ${time}ms")
}

