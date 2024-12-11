package `2024`.day_11

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import java.io.File

class Batch {

}

fun main() {
    val dayNumber = "day_11"
    var res = 0L
    var stones = mutableListOf<Long>()

    fun mapInput(input: List<String>) {
        input.forEachIndexed { index, line ->
            val row = line.split(" ")
                .filter { it.isNotEmpty() }
                .map { it.toString().toLongOrNull() ?: -1 }
                .toMutableList()
            stones.addAll(row)
        }
        println()
    }

    fun resetData() {
        res = 0L
        stones = mutableListOf()
    }

    fun Long.applyRule(): List<Long> {
        return when {
            this == 0L -> listOf(1L)
            this.toString().length % 2 == 0 -> {
                val digits = this.toString()
                val mid = digits.length / 2
                val left = digits.substring(0, mid).toLong()
                val right = digits.substring(mid).toLong()
                listOf(left, right)
            }
            else -> listOf(this * 2024)
        }
    }

    fun saveProgress(blinks: Int, stones: List<Long>, fileName: String) {
        File(fileName).bufferedWriter().use { out ->
            out.write("$blinks\n")
            stones.forEach { out.write("$it\n") }
        }
    }

    fun loadProgress(fileName: String): Pair<Int, MutableList<Long>> {
        val file = File(fileName)
        if (!file.exists()) return Pair(0, mutableListOf())
        val lines = file.readLines()
        val blinks = lines.first().toInt()
        val stones = lines.drop(1).map { it.toLong() }.toMutableList()
        return Pair(blinks, stones)
    }

    fun <T> List<T>.chunked(size: Int): List<List<T>> {
        return (0 until this.size step size).map { i -> this.subList(i, (i + size).coerceAtMost(this.size)) }
    }

    fun processChunk(chunk: List<Long>): List<Long> {
        val processed = mutableListOf<Long>()
        for (stone in chunk) {
            val rule = stone.applyRule()
            processed.add(rule.first())
            if (rule.size > 1) {
                processed.add(rule.last())
            }
        }
        return processed
    }

    fun part1(input: List<String>): Long {
//        resetData()
//        mapInput(input)
        val fileName = "E:\\projects\\advent_of_code\\src\\2024\\day_11\\progres_1.txt"
        val totalBlinks = 50
        val chunkSize = 1000
        val (startBlink, stones) = loadProgress(fileName)

        println("Resuming from blink: $startBlink with ${stones.size} stones")

        runBlocking {
            for (blink in (startBlink + 1)..totalBlinks) {
                val chunks = stones.chunked(chunkSize)
                val newStones = mutableListOf<Long>()

                val deferredResults = chunks.map { chunk ->
                    async(Dispatchers.Default) {
                        processChunk(chunk)
                    }
                }

                deferredResults.awaitAll().forEach { processedChunk ->
                    newStones.addAll(processedChunk)
                }

                stones.clear()
                stones.addAll(newStones)

                if (blink % 10 == 0 || blink == totalBlinks) {
                    saveProgress(blink, stones, fileName)
                    println("Progress saved at blink $blink with ${stones.size} stones")
                }
            }
        }

        println("Processing complete.")

        return stones.size.toLong()
    }

    part1(listOf())

}