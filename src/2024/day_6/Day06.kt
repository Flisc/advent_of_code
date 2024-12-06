package `2024`.day_5

import Util.Companion.prettyPrint
import readInput


fun main() {
    var matrix = mutableListOf(mutableListOf<String>())
    var sum = 0
    var pairRules =  mutableListOf<Pair<Int, Int>>()
    var updates = mutableListOf<List<Int>>()
    var validUpdates = mutableListOf<List<Int>>()
    /* HELPERS */
    fun mapInput(input: List<String>) {
        input.forEach{ matrix.add(it.split("").filter { it.isNotEmpty() }.toMutableList()) }
    }

    fun resetData() {
        sum = 1
        pairRules = mutableListOf<Pair<Int, Int>>()
        updates = mutableListOf<List<Int>>()
        validUpdates = mutableListOf<List<Int>>()
        matrix = mutableListOf()
    }

    fun insideMatrix(nextRow: Int, rows: Int, nextCol: Int, cols: Int) =
        nextRow in 0 until rows && nextCol in 0 until cols

    fun startFrom(row: Int, col: Int, direction: String): Pair<Int, Int>? {
        var currentRow = row
        var currentCol = col
        var currentDirection = direction
        sum = 1
        val rows = matrix.size
        val cols = matrix.firstOrNull()?.size ?: 0

        while (insideMatrix(currentRow, rows, currentCol, cols)) {
            val nextRow = when (currentDirection) {
                "up" -> currentRow - 1
                "right" -> currentRow
                "down" -> currentRow + 1
                "left" -> currentRow
                else -> currentRow
            }
            val nextCol = when (currentDirection) {
                "up" -> currentCol
                "right" -> currentCol + 1
                "down" -> currentCol
                "left" -> currentCol - 1
                else -> currentCol
            }

            if (insideMatrix(nextRow, rows, nextCol, cols)
                && matrix[nextRow][nextCol] == "#") {
                if(matrix[currentRow][currentCol] != "X") {
                    matrix[currentRow][currentCol] = "X"
                    sum++
                }

                currentDirection = when (currentDirection) {
                    "up" -> "right"
                    "right" -> "down"
                    "down" -> "left"
                    "left" -> "up"
                    else -> currentDirection
                }
            } else {
                if(insideMatrix(nextRow, rows, nextCol, cols)
                    && matrix[currentRow][currentCol] != "X") {
                    sum++
                    matrix[currentRow][currentCol] = "X"
                }
            }

//            println("After step: ${sum}")
//            matrix.printMatrix()

            when (currentDirection) {
                "up" -> currentRow--
                "right" -> currentCol++
                "down" -> currentRow++
                "left" -> currentCol--
            }
        }

        return null
    }

    fun part1(input: List<String>): Int {
        resetData()
        mapInput(input)
        for (i in 0..matrix.size - 1) {
            for (j in 0..matrix[i].size - 1) {
                when (matrix[i][j]) {
                    "^" -> { startFrom(i, j, "up") }
                }
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        resetData()
        mapInput(input)

        return sum
    }

    val testInput = readInput("example", "day_6")
    part1(testInput).prettyPrint("example")
//
    val part1 = readInput("puzzle", "day_6")
    part1(part1).prettyPrint("part1")
//
//    val part2 = readInput("example", "day_5")
//    part2(part2).prettyPrint("part2")
////
//    val part2p = readInput("puzzle", "day_5")
//    part2(part2p).prettyPrint("part2")





}

