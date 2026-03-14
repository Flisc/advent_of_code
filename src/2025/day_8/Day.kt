package `2025`.day_8

import Util.Companion.prettyPrint
import readInput
import java.lang.Math.pow
import java.util.*
import kotlin.math.sqrt
import kotlin.system.measureTimeMillis

data class Point(val x: Int, val y: Int, val z: Int) {
    override fun toString() = "$x,$y,$z"
}
data class Box(
        val position: Point,
        val id: String = UUID.randomUUID().toString(),
) {
    override fun toString(): String = "($position)"
}
data class Circuit(
        val from: Box,
        val to: Box,
        val distance: Double,
)

// https://en.wikipedia.org/wiki/Euclidean_distance
fun Box.distanceTo(box: Box): Double =
    sqrt(
            pow((position.x - box.position.x).toDouble(), 2.00) +
               pow((position.y - box.position.y).toDouble(), 2.00) +
               pow((position.z - box.position.z).toDouble(), 2.00)
    )

class Day {
    // https://adventofcode.com/2025/day/8
    val dayNumber = "day_8"
    val boxes = ArrayList<Box>()
    fun part1(lines: List<String>): Long {
        var res: Long = 0
        // map input
        lines.forEach { line ->
            val coordinates = line.split(",").map { it.toInt() }
            boxes.add(Box(Point(coordinates[0], coordinates[1], coordinates[2])))
        }
        val distances: MutableList<Circuit> = mutableListOf()

        // combinations of n taken by k
        boxes.forEachIndexed { index, box ->
            for (i in index+1 until boxes.size) {
                distances.add(Circuit(box, boxes[i], box.distanceTo(boxes[i])))
            }
        }
        distances.sortBy { it.distance }

        val circuits: MutableList<MutableSet<String>> = mutableListOf()
        val mappedIds = mutableSetOf<String>()
        var i:Int = 0
        for (c in distances) {
//            if (i == 10) break
            if (i >= 1000) break
            if (circuits.isEmpty()) {
                circuits.add(mutableSetOf(c.from.id, c.to.id))
                mappedIds.add(c.from.id)
                mappedIds.add(c.to.id)
                println("first circuit: $c")
                i++
                continue
            }
            val circuitWithFrom = circuits.firstOrNull { it.contains(c.from.id) }
            val circuitWithTo = circuits.firstOrNull { it.contains(c.to.id) }

            when {
                circuitWithFrom != null && circuitWithFrom === circuitWithTo -> {
                    println("already mapped circuit: ${c.from} - ${c.to}")
                    i++
                }
                circuitWithFrom != null && circuitWithTo != null && circuitWithFrom !== circuitWithTo -> {
                    println("MERGING circuits: ${circuits.indexOf(circuitWithFrom)} and ${circuits.indexOf(circuitWithTo)}")
                    circuitWithFrom.addAll(circuitWithTo)
                    circuits.remove(circuitWithTo)
                    println("Merged circuit now has ${circuitWithFrom.size} boxes")
                    i++
                }
                circuitWithFrom != null -> {
                    circuitWithFrom.add(c.to.id)
                    mappedIds.add(c.to.id)
                    println("add ${c.to} to circuit: ${circuits.indexOf(circuitWithFrom)}, size: ${circuitWithFrom.size}")
                    i++
                }
                circuitWithTo != null -> {
                    circuitWithTo.add(c.from.id)
                    mappedIds.add(c.from.id)
                    println("add ${c.from} to circuit: ${circuits.indexOf(circuitWithTo)}, size: ${circuitWithTo.size}")
                    i++
                }
                else -> {
                    circuits.add(mutableSetOf(c.from.id, c.to.id))
                    mappedIds.add(c.from.id)
                    mappedIds.add(c.to.id)
                    i++
                    println("new circuit: $c")
                }
            }
            println()
        }

        res = circuits.map { it.size }.sortedDescending().take(3).reduce { acc, i -> acc * i }.toLong()


        return res
    }

    fun part2(lines: List<String>): Long {
        var res: Long = 0
        // map input
        lines.forEach { line ->
            val coordinates = line.split(",").map { it.toInt() }
            boxes.add(Box(Point(coordinates[0], coordinates[1], coordinates[2])))
        }
        val distances: MutableList<Circuit> = mutableListOf()

        // combinations of n taken by k
        boxes.forEachIndexed { index, box ->
            for (i in index+1 until boxes.size) {
                distances.add(Circuit(box, boxes[i], box.distanceTo(boxes[i])))
            }
        }
        distances.sortBy { it.distance }

        val circuits: MutableList<MutableSet<String>> = mutableListOf()
        val mappedIds = mutableSetOf<String>()
        var i:Int = 0
        for (c in distances) {
//            if (circuits.size == 1) break
//            if (i >= 1000) break
            if (circuits.isEmpty()) {
                circuits.add(mutableSetOf(c.from.id, c.to.id))
                mappedIds.add(c.from.id)
                mappedIds.add(c.to.id)
                println("first circuit: $c")
                i++
                continue
            }
            val circuitWithFrom = circuits.firstOrNull { it.contains(c.from.id) }
            val circuitWithTo = circuits.firstOrNull { it.contains(c.to.id) }

            when {
                circuitWithFrom != null && circuitWithFrom === circuitWithTo -> {
                    println("already mapped circuit: ${c.from} - ${c.to}")
                    i++
                }
                circuitWithFrom != null && circuitWithTo != null && circuitWithFrom !== circuitWithTo -> {
                    println("MERGING circuits: ${circuits.indexOf(circuitWithFrom)} and ${circuits.indexOf(circuitWithTo)}")
                    circuitWithFrom.addAll(circuitWithTo)
                    circuits.remove(circuitWithTo)
                    println("Merged circuit now has ${circuitWithFrom.size} boxes")
                    i++
                    println("Circuits size: ${circuits.size}")
                    if (circuits.size == 1 && circuits[0].size == boxes.size) {
                        println("Circuits merged by: ${c.from} - ${c.to}")
                        break
                    }
                }
                circuitWithFrom != null -> {
                    circuitWithFrom.add(c.to.id)
                    mappedIds.add(c.to.id)
                    println("add to ${c.to} to circuit: ${circuits.indexOf(circuitWithFrom)}, size: ${circuitWithFrom.size}")
                    i++
                }
                circuitWithTo != null -> {
                    circuitWithTo.add(c.from.id)
                    mappedIds.add(c.from.id)
                    println("add from ${c.from} to circuit: ${circuits.indexOf(circuitWithTo)}, size: ${circuitWithTo.size}")
                    i++
                }
                else -> {
                    circuits.add(mutableSetOf(c.from.id, c.to.id))
                    mappedIds.add(c.from.id)
                    mappedIds.add(c.to.id)
                    i++
                    println("new circuit: $c")
                }
            }
            println()
            if (mappedIds.size == boxes.size && circuits.size == 1) {
                println("1 Circuit by: $c")
                return (c.from.position.x * c.to.position.x).toLong()
            }

        }

        res = circuits.map { it.size }.sortedDescending().take(3).reduce { acc, i -> acc * i }.toLong()


        return res
    }

}


fun main() {
    val runner = Day()
    measureTimeMillis {
//        val example = readInput("example", runner.dayNumber, "2025")
        val example = readInput("puzzle", runner.dayNumber, "2025")
//        runner.part1(example).prettyPrint("res")
        runner.part2(example).prettyPrint("res")
    }.also { println("Execution time: ${it}ms") }

}

