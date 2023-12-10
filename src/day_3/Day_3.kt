package day_3

import isNumber
import print
import readInput
import kotlin.text.StringBuilder

class Day_3 {
    var startIndex = -1
    var endIndex = -1
    fun part1(lines: List<String>): Long {
        val matrix: ArrayList<ArrayList<String>> = ArrayList()
        val numbers: ArrayList<Long> = ArrayList()
        fillMatrix(lines, matrix)

        for (i in 0..<matrix.size) {
            for (j in 0..<matrix[i].size) {
                if (matrix[i][j].isNumber() && startIndex == -1) {
                    startIndex = j
                    if (j == matrix[i].size - 1) {
                        endIndex = startIndex
                        checkItem(i, startIndex, endIndex, matrix, numbers)
                        resetRange()
                    }

                } else if (isEndOfDigitSeq(matrix, i, j, startIndex)) {
                    endIndex = j - 1
                }
                if (lastDigitInRow(j, matrix, i, startIndex)) {
                    endIndex = j
                }
                if (startIndex != -1 && endIndex != -1) {
                    checkItem(i, startIndex, endIndex, matrix, numbers)
                    resetRange()
                }
            }
            println()
            resetRange()
        }
        val sum = numbers.sum()
        sum.print()
        return sum
    }

    private fun resetRange() {
        startIndex = -1
        endIndex = -1
    }

    private fun lastDigitInRow(
        j: Int,
        matrix: ArrayList<ArrayList<String>>,
        i: Int,
        startIndex: Int
    ) = j == matrix[i].size - 1 && matrix[i][j].isNumber() && startIndex != -1

    private fun isEndOfDigitSeq(
        matrix: ArrayList<ArrayList<String>>,
        i: Int,
        j: Int,
        startIndex: Int
    ) = !matrix[i][j].isNumber() && startIndex != -1

    private fun checkItem(i: Int, startIndex: Int, endIndex: Int, matrix: ArrayList<ArrayList<String>>, numbers: ArrayList<Long>) {
        if (hasValidNeighbor(i, startIndex..endIndex, matrix)) {
            numbers.add(matrix[i].subList(startIndex, endIndex + 1).joinToString("").toLong())
        }
    }


    private fun hasValidNeighbor(row: Int, range: IntRange, matrix: ArrayList<ArrayList<String>>): Boolean {
        val neighbors: ArrayList<String> = ArrayList()
        val item = matrix[row].subList(range.first, range.last + 1)
        println("neighbors for: ${item}")
        if (row > 0) {
            val adjustedStart = range.first - if (range.first > 0) 1 else 0
            val adjustedEnd = range.last + if (range.last < matrix[row].size - 1) 2 else 1

            val subList = matrix[row - 1].subList(adjustedStart, adjustedEnd)
            neighbors.addAll(subList)
            println("above: $subList")
        }
        if (row < matrix.size - 1) {
            val adjustedStart = range.first - if (range.first > 0) 1 else 0
            val adjustedEnd = range.last + if (range.last < matrix[row].size - 1) 2 else 1

            val subList = matrix[row + 1].subList(adjustedStart, adjustedEnd)
            neighbors.addAll(subList)
            println("below: $subList")
        }

        checkLeftNeighbor(range, matrix, row, neighbors)
        checkRightNeighbor(range, matrix, row, neighbors)

        return neighbors.any { !it.equals(".") }
    }

    private fun checkRightNeighbor(
        range: IntRange, matrix: ArrayList<ArrayList<String>>, row: Int, neighbors: ArrayList<String>) {
        if (range.last < matrix[row].size - 1) {
            //right item
            val subList = matrix[row].subList(range.last + 1, range.last + 2)
            neighbors.addAll(subList)
            println("right: $subList")
        }
    }

    private fun checkLeftNeighbor(range: IntRange, matrix: ArrayList<ArrayList<String>>, row: Int, neighbors: ArrayList<String>) {
        if (range.first > 0) {
            //left item
            val subList = matrix[row].subList(range.first - 1, range.first)
            neighbors.addAll(subList)
            println("left: $subList")
        }
    }

    private fun fillMatrix(lines: List<String>, matrix: ArrayList<ArrayList<String>>) {
        lines.forEach { line ->
            val items: ArrayList<String> = ArrayList()
            line.forEach { char ->
                items.add(char.toString())
            }
            matrix.add(ArrayList(items.filter { it.isNotBlank() }))
        }
    }
}

fun main() {
//    val lines = readInput("example", "day_3")
    val lines = readInput("puzzle", "day_3")
    val reddit_ex = readInput("reddit_ex", "day_3")
    val reddit_ex_2 = readInput("reddit_ex_2", "day_3")
//        check(part1(reddit_ex) == 5049L)
//        check(part1(reddit_ex_2) == 468L)
    val runner = Day_3()
    check(runner.part1(lines) == 531932L)
}