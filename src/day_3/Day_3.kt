package day_3

import isNumber
import print
import readInput
import kotlin.text.StringBuilder

fun main() {
    val lines = readInput("example", "day_3")
//    lines.print()
    part1(lines)
}

private fun part1(lines: List<String>) {
    val matrix: ArrayList<ArrayList<String>> = ArrayList()
    val numbers: ArrayList<Long> = ArrayList()
    fillMatrix(lines, matrix)
    for (i in 0..<matrix.size) {
        for (j in 0..<matrix[i].size) {
//            print("${matrix[i][j]} ${matrix[i][j].isNumber()}")
            if(matrix[i][j].isNumber()){
                if(hasNeighbor(i, j, matrix)){
                    numbers.add(matrix[i][j].toLong())
                }
            }
        }
        println()
    }
    numbers.sum().print()
}

fun hasNeighbor(row: Int, col: Int, matrix: ArrayList<ArrayList<String>>): Boolean {
    val neighbors: ArrayList<String> = ArrayList()
    if (row > 0) {
        neighbors.add(matrix[row - 1][col])
        //all from above
        if( col > 0) {
            for(i in col-1.. col + matrix[row][col].length -1) {
                neighbors.add(matrix[row-1][i].trim())
            }
        }
//        neighbors.addAll(matrix[row-1].filter { it.isNotBlank() }.subList(col - matrix[row][col].length -1, col + matrix[row][col].length))
//        neighbors.addAll(matrix[row-1].filter { it.isNotBlank() }.subList(col - matrix[row][col].length , col + matrix[row][col].length - 1))
    }
    // Check below
    if (row < matrix.size - 1) {
        neighbors.add(matrix[row + 1][col])
        // all from row below
//        neighbors.addAll(matrix[row+1].filter { it.isNotBlank() }.subList(col, col + matrix[col][row].length +1))
    }
    // Check to the left
    if (col > 0) {
        neighbors.add(matrix[row][col - 1])
    }
    // Check to the right
    if (col < matrix[0].size - 1) {
        neighbors.add(matrix[row][col + 1])
    }

    return neighbors.any { !it.equals(".") }
}

private fun fillMatrix(lines: List<String>, matrix: ArrayList<ArrayList<String>>) {
    lines.forEach { line ->
        val items: ArrayList<String> = ArrayList()
        val value = StringBuilder()
        line.forEach { char ->
            if (char.isDigit()) {
                value.append(char)
            } else {
                items.add(value.toString())
                items.add(char.toString())
                value.clear()
            }
        }
        matrix.add(items)
    }
}
