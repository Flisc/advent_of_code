package `2025`.day_9

import Util.Companion.prettyPrint
import readInput
import java.awt.Point
import java.util.*
import kotlin.system.measureTimeMillis
import kotlin.time.times

//data class Point(val x: Int, val y: Int) {
//    override fun toString() = "($x,$y)"
//}

class Day {
    // https://adventofcode.com/2025/day/9
    val dayNumber = "day_9"
    val points = ArrayList<Point>()
    fun part1(lines: List<String>): Long {
        var res: Long = 0
        // map input
        lines.forEach { line ->
            val indexes = line.split(",").map { it.toInt() }
            points.add(Point(indexes[0], indexes[1]))
        }
        var maxArea = 0
        var bestDiagonal = Pair(Point(0, 0), Point(0, 0))
        var rectangleCount = 0

        val n = points.size

        for (i in 0 until n) {
            for (j in i + 1 until n) {
                val p1 = points[i]
                val p2 = points[j]

                if (p1.x != p2.x && p1.y != p2.y) {
                    rectangleCount++

                    val colCount = Math.abs(p2.x - p1.x) + 1
                    val rowCount = Math.abs(p2.y - p1.y) + 1
                    val area = rowCount * colCount

                    if (area > maxArea) {
                        maxArea = area
                        bestDiagonal = Pair(p1, p2)
                    }
                }
            }
        }
        println("Max Area: $maxArea")
        println("Rectangles: $rectangleCount")
        println("Points: (${bestDiagonal.first.x}, ${bestDiagonal.first.y}) to (${bestDiagonal.second.x}, ${bestDiagonal.second.y})")

        return maxArea.toLong()
    }

    fun part2(lines: List<String>): Long {
        var res: Long = 0
        // map input

        return res
    }

}


fun main() {
    val runner = Day()
    measureTimeMillis {
//        val example = readInput("example", runner.dayNumber, "2025")
        val example = readInput("puzzle", runner.dayNumber, "2025")
        runner.part1(example).prettyPrint("res")
//        runner.part2(example).prettyPrint("res")
    }.also { println("Execution time: ${it}ms") }

}

