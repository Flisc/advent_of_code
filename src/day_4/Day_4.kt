package day_4

import print
import readInput
import java.lang.StrictMath.pow

class Day_4 {
    val dayNumber = "Day_4"
    fun part1(lines: List<String>): Long? {
        var sum: Long = 0
        lines.forEach {
            val game = it.split(": ")
            val game_id = game[0].split(" ")[1]
            val numbers = game[1].split(" | ")
            val winners = numbers[0].split(" ").filter { it.isNotBlank() }
            val values = numbers[1].split(" ").filter { it.isNotBlank() }
            var cardPoints: Long = 0
            cardPoints = calculateCardPoints(winners, values, cardPoints)
            sum = sum.plus(cardPoints)
        }
        return sum
    }

    private fun calculateCardPoints(
        winners: List<String>,
        values: List<String>,
        cardPoints: Long
    ): Long {
        var cardPoints1 = cardPoints
        winners.forEach {
            if (values.contains(it)) {
                if (cardPoints1 == 0L) {
                    cardPoints1 = 1
                } else {
                    cardPoints1 = cardPoints1 * 2
                }
            }
        }
        return cardPoints1
    }

    fun part2(lines: List<String>): Long {
        var sum: Long = 0
        var cardsById: MutableMap<String, Card> = mutableMapOf()
        var copies: MutableList<Card> = mutableListOf()
        for (i in 0..lines.size - 1) {
            val card = cardPointsToMatchers(lines[i])
            cardsById.put(card.game_id, card)
            if (card.points >= 1) {
                if (card.copies > 0) {
                    for (j in i + 1..i + 1 + card.copies - 1) {
                        copies.add(cardPointsToMatchers(lines[j]))
                    }
                }
            }
        }
        return sum
    }

    private fun cardPointsToMatchers(line: String): Card {
        val game = line.split(": ")
        val game_id = game[0].split(" ")[1]
        val numbers = game[1].split(" | ")
        val winners = numbers[0].split(" ").filter { it.isNotBlank() }
        val values = numbers[1].split(" ").filter { it.isNotBlank() }
        var cardPoints: Double = 0.00
        val matchers = winners.intersect(values)
        cardPoints = pow(2.00, matchers.size - 1.00)
        return Card(game_id, cardPoints.toLong(), matchers.size)
    }

    private fun calculatePointsOnSubList(lines: List<String>): Long {
        var sum: Long = 0
        lines.forEach {
            val game = it.split(": ")
//            val game_id = game[0].split(" ")[1]
            val numbers = game[1].split(" | ")
            val winners = numbers[0].split(" ").filter { it.isNotBlank() }
            val values = numbers[1].split(" ").filter { it.isNotBlank() }
            var cardPoints: Double = 0.00
            val matchers = winners.intersect(values)
            cardPoints = pow(2.00, matchers.size - 1.00 )
            if(cardPoints >= 1 ){
                sum = sum.plus(cardPoints.toLong())
            }
        }
        return sum
    }
}

data class Card(
    val game_id:String,
    val points: Long,
    val copies: Int
)

fun main() {
    val runner = Day_4()
    val puzzle = readInput("puzzle", runner.dayNumber)
    val example = readInput("example", runner.dayNumber)

    check(runner.part1(example) == 13L)
//    runner.part1(puzzle).print()
    runner.part2(example).print()
//    check(runner.part2(example) == 30L)

}