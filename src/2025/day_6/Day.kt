package `2025`.day_6

import isNumber
import printMatrix
import println
import readInput
import kotlin.system.measureTimeMillis

class Day {
    // https://adventofcode.com/2025/day/6#part2
    val dayNumber = "day_6"
    val matrix = mutableListOf<MutableList<String>>()

    fun part1(lines: List<String>): Long {
        var sum: Long = 0
        lines.forEach{ line -> matrix.add(line.split(" ").filter { it.isNotBlank() }.toMutableList()) }
        for (i in 0..matrix[0].size-1){
            val operator = matrix[matrix.size-1][i]
            var colRes: Long = if (operator == "*") 1 else 0

            "col: ${i}, operator: ${operator}".println()

            for (j in 0..matrix.size-2){
                val number = matrix[j][i].toLong()
                println("colRes: $colRes $operator $number")
                if (operator == "*") {
                    colRes *= number
                }
                if (operator == "+") {
                    colRes += number
                }
            }
            sum += colRes
            println("colRes=$colRes")
        }
        return sum
    }

    fun part2(lines: List<String>): Long {
        var sum: Long = 0
        lines.forEach { line -> matrix.add(line.split("").drop(1).dropLast(1).toMutableList()) }
        // i col, j row
        matrix.printMatrix();
        val lastRow =  matrix[matrix.size-1]
        println(lastRow)

        for (i in 0..lastRow.size-1){
            if (listOf("*", "+").contains(lastRow[i])){
                val operator = lastRow[i]
                val indexOfNextSpace = findIndexOfNextOperator(i, lastRow)
                // traverse columns from i -> indexOfNextSpace-1
                sum += traverseColumns(matrix, i, indexOfNextSpace-1, operator)
            }
        }
        return sum
    }

    fun findIndexOfNextOperator(currentIndex: Int, list: MutableList<String>): Int {
        for (j in currentIndex+1..list.size-1) {
            if (listOf("*", "+").contains(list[j])) {
                println("$currentIndex -> $j")
                return j-1
            }
        }
        return list.size
    }

    fun traverseColumns(matrix: MutableList<MutableList<String>>, startCol: Int, endCol: Int, operator: String): Long {
        val sb = StringBuilder()
        var colRes: Long = if (operator == "*") 1 else 0
        for (col in startCol..endCol) {
            // each row for the current column
            for (row in 0..matrix.size - 1) {
                val item = matrix[row][col]
                println("ITEM: $item")
                if (item.isNumber()) {
                    sb.append(matrix[row][col])
                }
            }
            // TODO process number
            println("col: $col, nr: ${sb.toString()} \n")
            if (operator == "+") {
                colRes += sb.toString().toLong()
            }
            if (operator == "*") {
                colRes *= sb.toString().toLong()
            }
            sb.clear()
        }

        return colRes
    }

}


fun main() {
    val runner = Day()
    val time = measureTimeMillis {
//        val example = readInput("example", runner.dayNumber, "2025")
        val example = readInput("puzzle", runner.dayNumber, "2025")
//        runner.part1(example).prettyPrint("res")
//        runner.part2(example).prettyPrint("res")
        println( "result: ${runner.part2(example)}")
    }
    println("Execution time: ${time}ms")
}

