package day_7

import print
import readInput
import java.util.Comparator

class Day {
    val dayNumber = "day_7"

    fun part1(lines: List<String>): Long {
        val strengthPriority = setOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
        val rankByValue: Map<Char, Int> = strengthPriority
            .withIndex()
            .associate { (i, ch) -> ch to i }
        var totalWin: Long = 0
        val hands = mutableListOf<Hand>()

        lines.forEachIndexed { index, line ->
            val charsByFrequency = mutableMapOf<Char, Int>()
            val hand = line.substring(0..4)
            val bid = line.split(" ").last().toLong()
            hand.chars().forEach { char ->
                charsByFrequency[char.toChar()] = (charsByFrequency[char.toChar()] ?: 0) + 1
            }

            val values = charsByFrequency.values
//            charsByFrequency.print()
            val type =
                when {
                    values.contains(5) -> {
                        println("5x")
                        99999
                    }
                    values.count { it == 4 } == 1 && values.count { it == 1 } == 1 -> {
                        println("4x, 4 1")
                        99990
                    }
                    values.containsAll(listOf(3, 2)) && values.size == 2 -> {
                        println("full house, 3 2")
                        99988
                    }
                    values.count { it == 3 } == 1 && values.count { it == 1 } == 2  -> {
                        println("Three of a kind, 3 1 1")
                        99987
                    }
                    values.count { it == 2 } == 2 && values.count { it == 1 } == 1 -> {
                        println("2 pair, 2 2 1")
                        99881
                    }
                    values.count { it == 2 } == 1 && values.count { it == 1 } == 3 -> {
                        println("1 pair, 2 1 1 1")
                        99123
                    }
                    values.size == 5 -> {
                        println("high")
                        0
                    }
                    else -> {
                        println("xxx")
                        -1
                    }
                }

            val handDetails = Hand(hand, bid, type).also { it.print()  }
            hands.add(handDetails)
        }

        println("Sorted by type: ")
        hands.print()

        val comparatorByCard = kotlin.Comparator<Hand>{ a, b ->
            val s1 = a.hand
            val s2 = b.hand
            for (i in s1.indices) {
                val c1 = s1[i]
                val c2 = s2[i]
                if (c1 != c2) {
                    val r1 = rankByValue[c1] ?: Int.MAX_VALUE
                    val r2 = rankByValue[c2] ?: Int.MAX_VALUE
                    return@Comparator r2.compareTo(r1)
                }
            }
            0
        }

        hands.sortWith(compareBy<Hand> { it.type }.then(comparatorByCard))

        println("Sorted by hands: ")
        hands.forEachIndexed { index, hand ->
            println("hand: $hand, rank: ${index+1}")
            totalWin += hand.bid * (index+1)
        }
        totalWin.print()
        return lines.size.toLong()
    }

    fun part2(lines: List<String>): Long {
        val newStrengthPriority = setOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')
        val rankByValue: Map<Char, Int> = newStrengthPriority
            .withIndex()
            .associate { (i, ch) -> ch to i }
        var totalWin: Long = 0
        val hands = mutableListOf<Hand>()

        lines.forEachIndexed { index, line ->
            val charsByFrequency = mutableMapOf<Char, Int>()
            val hand = line.substring(0..4)
            val bid = line.split(" ").last().toLong()
            hand.chars().forEach { char ->
                charsByFrequency[char.toChar()] = (charsByFrequency[char.toChar()] ?: 0) + 1
            }

            val values = charsByFrequency.values
//            charsByFrequency.print()
            val type =
                when {
                    values.contains(5) -> {
                        println("5x")
                        99999
                    }
                    values.count { it == 4 } == 1 && values.count { it == 1 } == 1 -> {
                        println("4x, 4 1")
                        99990
                    }
                    values.containsAll(listOf(3, 2)) && values.size == 2 -> {
                        println("full house, 3 2")
                        99988
                    }
                    charsByFrequency.keys.contains('J') &&
                            values.count { it == 3 } == 1 &&
                            values.count { it == 1 } == 2  -> {
                        println("Three of a kind, 3 1 1")
                        99987
                    }
                    values.count { it == 2 } == 2 && values.count { it == 1 } == 1 -> {
                        println("2 pair, 2 2 1")
                        99881
                    }
                    values.count { it == 2 } == 1 && values.count { it == 1 } == 3 -> {
                        println("1 pair, 2 1 1 1")
                        99123
                    }
                    values.size == 5 -> {
                        println("high")
                        0
                    }
                    else -> {
                        println("xxx")
                        -1
                    }
                }

            val handDetails = Hand(hand, bid, type).also { it.print()  }
            hands.add(handDetails)
        }

        println("\n Sorted by type: ")
        hands.print()

//        val comparatorByCard = kotlin.Comparator<Hand>{ a, b ->
//            val s1 = a.hand
//            val s2 = b.hand
//            for (i in s1.indices) {
//                val c1 = s1[i]
//                val c2 = s2[i]
//                if (c1 != c2) {
//                    val r1 = rankByValue[c1] ?: Int.MAX_VALUE
//                    val r2 = rankByValue[c2] ?: Int.MAX_VALUE
//                    return@Comparator r2.compareTo(r1)
//                }
//            }
//            0
//        }
//
//        hands.sortWith(compareBy<Hand> { it.type }.then(comparatorByCard))
//
//        println("Sorted by hands: ")
//        hands.forEachIndexed { index, hand ->
//            println("hand: $hand, rank: ${index+1}")
//            totalWin += hand.bid * (index+1)
//        }
        totalWin.print()
        return lines.size as Long
    }
}


fun main() {
    val runner = Day()
    val example = readInput("example", runner.dayNumber, "2023")
    val puzzle = readInput("puzzle", runner.dayNumber, "2023")
//    check(runner.part1(puzzle) == 123L)
//    runner.part1(example)
//    runner.part1(puzzle)
    runner.part2(example)
//    runner.part2(puzzle)
}

data class Hand(val hand: String, val bid: Long, val type: Int)