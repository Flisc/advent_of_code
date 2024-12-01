package day_2

import print
import readInput
import java.io.File

val cubeConfig = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14
)

fun main() {
    val gamesRaw = readInput("puzzle", "day_2")
//    val gamesRaw = readInput("example", "day_2")
    val games = gamesRaw.map { mapToGame(it) }
    val gameIds: MutableSet<Int> = mutableSetOf()
    filterGames(games, gameIds)
    println("part-1:  ${gameIds.sum()}")
    print("part-2: ")
    calculatePowerOfGames(games)
}

private fun calculatePowerOfGames(games: List<Game>) {
    //part 2
    val powerOfGames:MutableList<Int> = mutableListOf()
    games.forEach{game ->
        val max:MutableMap<String, Int> = mutableMapOf(
            "red" to 1,
            "green" to 1,
            "blue" to 1
        )
        game.rounds.forEach{round ->
            round.numberByColor.entries.forEach{
                if(max[it.key]!! < it.value) {
                    max[it.key] = it.value
                }
            }
        }
       powerOfGames.add(max.values.reduce{acc, i ->  acc * i})
    }
    powerOfGames.sum().print()
}
private fun filterGames(games: List<Game>, gameIds: MutableSet<Int>) {
    //part 1
    games.forEach { game ->
        val valid = game.rounds.all {
            it.numberByColor.entries.all { cubeConfig.get(it.key)!! >= it.value }
        }
        if (valid) {
//            println("Game ${game.id} ok")
            gameIds.add(game.id)
        } else {
//            println("Game ${game.id} NOT ok")
        }
    }
}

fun mapToGame(gameRaw: String): Game {
    val id = gameRaw.substring("Game ".length, "Game ".length + 3).split(":")[0].toInt()
    val game = Game(id, mutableListOf())
    val rounds = gameRaw.substring(gameRaw.indexOf(": ") + 2)
        .split("; ")
    rounds.forEach { str ->
        val cubes = str.split(", ")
        val numberByColor = cubes.map {
            val nrAndColor = it.split(" ")
            nrAndColor[1] to nrAndColor[0].toInt()
        }
        game.rounds.add(Round(numberByColor.toMap()))
    }
    return game
}

fun readFileLines(fileName: String): List<String> = File(fileName).useLines { it.toList() }