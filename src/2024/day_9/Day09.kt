package `2024`.day_5

import Util.Companion.prettyPrint
import isNumber
import readInput
import searchAvailableRangeUntil

enum class BlockType() {
    empty,
    file
}

data class Block(val type: BlockType, val value: Int, val originalIndex: Int)

fun main() {
    var sum = 0L
    // day 9
    var diskMap = mutableListOf<Block>()
    fun mapInput(input: List<String>) {
        input.forEach { line ->
                var lastIndex = 0
                line.mapIndexed { index, c ->
                    if(index % 2 == 0) {
                        diskMap.add(Block(BlockType.file, c.toString().toInt(), lastIndex))
                        lastIndex++
                    } else {
                        diskMap.add(Block(BlockType.empty, c.toString().toInt(), index))
                    }
                }
        }
    }

    fun resetData() {
        sum = 0
    }

    fun part1(input: List<String>): Long {
        resetData()
        mapInput(input)
        var blocks = mutableListOf<String>()
        diskMap.forEach { item ->
            if(item.type == BlockType.file) {
                for(i in 1..item.value) {
                    blocks.add(item.originalIndex.toString())
                }
            }
            if(item.type == BlockType.empty) {
                for(i in 1..item.value) {
                    blocks.add(".")
                }
            }
        }

        while (blocks.contains(".")) {
            for(i in blocks.lastIndex downTo 0) {
                var fistPoint = blocks.indexOfFirst { it == "." }
                if(fistPoint != -1) {
                    blocks[fistPoint] = blocks[i]
                    blocks[i] = ""
//                    blocks.joinToString(" ").prettyPrint()
                }
            }
        }
//        blocks.joinToString(" ").prettyPrint()
        blocks.mapIndexed { index, s ->
            if(s.isNotEmpty()) {
                sum += index * s.toLong()
            }
        }


        return sum
    }

    fun part2(input: List<String>): Long {
        resetData()
        mapInput(input)

        var blocks = mutableListOf<String>()
        diskMap.forEach { item ->
            if(item.type == BlockType.file) {
                for(i in 1..item.value) {
                    blocks.add(item.originalIndex.toString())
                }
            }
            if(item.type == BlockType.empty) {
                for(i in 1..item.value) {
                    blocks.add(".")
                }
            }
        }

        var end = blocks.lastIndex
        var start = end
        var loopLimit = 1000
        do {
            for(i in diskMap.lastIndex downTo 0) {

                var item = diskMap[i]
                start = end - item.value + 1
//                blocks.subList(start, end+1)
                if(item.type == BlockType.file) {
                    blocks.joinToString(" ").prettyPrint()
                    var availableRange = blocks.searchAvailableRangeUntil(item.value, start)
                    if(availableRange != null) {
                        for(j in availableRange.first .. availableRange.second) {
                            //replace .. with values
                            blocks[j] = item.originalIndex.toString()
                        }
                        for(j in start..end) {
                            blocks[j] = ""
                        }
                    }
//                    end = start-1
                }
                end = start-1
//                if(item.type == BlockType.empty) {
//                    println()
//                }

            }
//            blocks.joinToString(" ").prettyPrint()
            loopLimit--
        } while (blocks.contains(".") && end > start && loopLimit > 0)

        blocks.mapIndexed { index, s ->
            if(s.isNotEmpty() && s.isNumber()) {
                sum += index * s.toLong()
            }
        }


        return sum
    }

//    val testInput = readInput("example", "day_9")
//    part1(testInput).prettyPrint("example")

//    val part1 = readInput("puzzle", "day_9")
//    part1(part1).prettyPrint("part1")
//
//    val part2 = readInput("example", "day_9")
//    part2(part2).prettyPrint("part2")
////
    val part2p = readInput("puzzle", "day_9")
    part2(part2p).prettyPrint("part2")

}

