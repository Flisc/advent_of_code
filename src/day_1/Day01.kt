package day_1

import print
import readInput
import java.util.ArrayList


val digitsByWord = mapOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

fun main() {
    fun part1(input: List<String>): Int {
        var totalCalibration: Long = 0
        input.forEach {
            totalCalibration = totalCalibration.plus(calculateLineFromDigits(it)!!)
        }
        print("calibration: ${totalCalibration}")
        return totalCalibration.toInt()
    }

    fun part2(input: List<String>) {
        input.forEach { println(searchForWords(it)) }
    }


    val testInput = readInput("example", "day_1")
    part1(testInput)

}
fun calculateLineFromDigits(line: String): Long? {
    var numbers = ArrayList<String>()
    println("\n line: " + line)
    for (i in 0..line.length - 1) {
        if (line[i].isDigit()) {
            numbers.add(line[i].toString())
        }
    }
    var totalCalibration = (numbers[0] + numbers[numbers.size - 1]).toLongOrNull()
    println("line calibration: " + totalCalibration)
    return totalCalibration
}

private fun searchForWords(line: String): Any? {
    var numbers = ArrayList<String>()
    var i: Int = 0
    println("line: ${line}")
    while (i < line.length) {
        if(line[i].isDigit()){
            numbers.add(line[i].toString())
            i++
        } else {
            var defaultKeyLength = line.substring(i, i+2)
            if (digitsByWord.containsKey(defaultKeyLength)) {
                numbers.add(digitsByWord.get(defaultKeyLength).toString())
                i += defaultKeyLength.length
            } else {
                var reset = false
                for(j in i + defaultKeyLength.length..line.length - 1 ) {
                    var key = line.substring(line.indexOf(defaultKeyLength), j + 1)
                    if (digitsByWord.containsKey(key)) {
                        numbers.add(digitsByWord.get(key).toString())
                        i += key.length
                    }
                    if(key.length == 5) {
                        i++
                        reset = true
                        break
                    }
                }
                if(!reset) i++
            }
        }
    }
    return numbers[0].toString().plus(numbers[numbers.size-1].toString())
}