package `2024`.day_4

import Util.Companion.prettyPrint
import readInput


fun main() {
    var matrix = mutableListOf<List<String>>()
    var sum = 0

    fun countXmasByPosition(i: Int, j: Int) {
        var collect = ""
        if(j+3 <= matrix[i].size-1) { //go right
            for(j in j..j+3) {
                collect += matrix[i][j]
            }
            if(collect == "XMAS") {
                sum++
                println("found 1 at: $i, $j ;sum: $sum")
            }
            collect = ""
        }
        if(j-3 >= 0) { //go left
            for(x in j downTo  j-3) {
                collect += matrix[i][x]
            }
            if(collect == "XMAS") {
                sum++
                println("found 1 at: $i, $j ;sum: $sum")
            }
            collect = ""
        }

        if(i-3 >= 0) { // go up
            for(x in i downTo  i-3) {
                collect += matrix[x][j]
            }
            if(collect == "XMAS") {
                sum++
                println("found 1 at: $i, $j ;sum: $sum")
            }
            collect = ""
        }

        if(i+3 <= matrix.size-1) { // go down
            for(x in i ..  i+3) {
                collect += matrix[x][j]
            }
            if(collect == "XMAS") {
                sum++
                println("found 1 at: $i, $j ;sum: $sum")
            }
            collect = ""
        }

        if(i+3 <= matrix.size-1 && j+3 <= matrix[i].size-1) { // go down-right diag
            (i .. i+3).toList().zip((j .. j+3).toList()).forEach {
               collect += matrix[it.first][it.second]
            }
            if(collect == "XMAS") {
                sum++
                println("found 1 at: $i, $j ;sum: $sum")
            }
            collect = ""
        }
        if(i+3 <= matrix.size-1 && j-3 >= 0) { // go down-left diag
            (i .. i+3).toList().zip((j downTo j-3).toList()).forEach {
                collect += matrix[it.first][it.second]
            }
            if(collect == "XMAS") {
                sum++
                println("found 1 at: $i, $j ;sum: $sum")
            }
            collect = ""
        }
        if(i-3 >= 0 && j+3 <= matrix[i].size -1) { // go up-right diag
            (i downTo  i-3).toList().zip((j .. j+3).toList()).forEach {
                collect += matrix[it.first][it.second]
            }
            if(collect == "XMAS") {
                sum++
                println("found 1 at: $i, $j ;sum: $sum")
            }
            collect = ""
        }
        if(i-3 >= 0 && j-3 >= 0) { // go up-left diag
            (i downTo  i-3).toList().zip((j downTo j-3).toList()).forEach {
                collect += matrix[it.first][it.second]
            }
            if(collect == "XMAS") {
                sum++
                println("found 1 at: $i, $j ;sum: $sum")
            }
            collect = ""
        }
    }

    fun countXmasXByPosition(i: Int, j: Int) {
        var collect = ""
        var rightD = false
        var leftD = false
        if (j - 1 >= 0 && i - 1 >= 0 ) { // left-up
            collect += matrix[i - 1][j - 1] + "A"
        }
        if (j + 1 < matrix[i].size && i + 1 <= matrix.size-1 ) { // right down
            collect += matrix[i + 1][j + 1]
        }
        if(collect == "MAS" || collect == "SAM") {
            rightD = true
            collect = ""
        }

        if (j - 1 >= 0 && i + 1 < matrix.size ) { // left-down
            collect += matrix[i + 1][j - 1] + "A"
        }
        if (j + 1 < matrix[i].size && i - 1 >= 0 ) { // right up
            collect += matrix[i - 1][j + 1]
        }

        if(collect == "MAS" || collect == "SAM") {
            leftD = true
        }

        if( rightD and leftD ) {
            sum++
        }
    }

    fun part1(input: List<String>): Int {
        input.forEach{ matrix.add(it.split("").filter { it.isNotEmpty() }) }
        for (i in 0..matrix.size-1){
            for (j in 0..matrix[i].size-1){
                if(matrix[i][j] == "X"){
                    countXmasByPosition(i, j)
                }
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        matrix = mutableListOf<List<String>>()
        input.forEach{ matrix.add(it.split("").filter { it.isNotEmpty() }) }

        for (i in 0..matrix.size-1){
            for (j in 0..matrix[i].size-1){
                if(matrix[i][j] == "A"){
                    countXmasXByPosition(i, j)
                }
            }
        }

        return sum
    }

//    val testInput = readInput("example", "day_4")
//    part1(testInput).prettyPrint("example")

//    val part1 = readInput("puzzle", "day_4")
//    part1(part1).prettyPrint("part1")
//
//    val part2 = readInput("example", "day_4")
//    part2(part2).prettyPrint("part2")
//
    val part2p = readInput("puzzle", "day_4")
    part2(part2p).prettyPrint("part2")

}

