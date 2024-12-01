package day_2

data class Game(
    val id: Int,
    val rounds: MutableList<Round>
)
data class Round(
    val numberByColor: Map<String, Int>
)