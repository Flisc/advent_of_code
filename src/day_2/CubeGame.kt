package day_2

import java.io.File

val cubeConfig = mapOf(
    "red" to 12,
    "green" to 13,
    "blue" to 14
)

fun main() {
//    val gamesRaw = readFileLines("C:\\projects\\practice\\advent_of_code\\src\\main\\kotlin\\day_2\\example.txt")
    val gamesRaw = readFileLines("C:\\projects\\practice\\advent_of_code\\src\\main\\kotlin\\day_2\\puzzle.txt")
    val games = gamesRaw.map { mapToGame(it) }
    val gameIds: MutableSet<Int> = mutableSetOf()
    filterGames(games, gameIds)
    println(gameIds.sum())
}

private fun filterGames(games: List<Game>, gameIds: MutableSet<Int>) {
    games.forEach { game ->
        val valid = game.rounds.all {
            it.numberByColor.entries.all { cubeConfig.get(it.key)!! >= it.value }
        }
        if (valid) {
            println("Game ${game.id} ok")
            gameIds.add(game.id)
        } else {
            println("Game ${game.id} NOT ok")
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