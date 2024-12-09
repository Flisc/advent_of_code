import com.fasterxml.jackson.databind.ObjectMapper
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.io.path.Path
import kotlin.io.path.readLines

public class Util {
    companion object {
        val objectMapper = ObjectMapper()
        public fun Any.prettyPrint(header: String? = "") {
            println("\n\n *********************** $header **************************")
            println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this))
        }
    }
}
/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String, day: String, year: String? = "2024") = Path("src/$year/$day/$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5() = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray()))
    .toString(16)
    .padStart(32, '0')

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.print() = println(this)

fun List<Any>.print() = this.forEach{ println(it) }

fun String.isNumber() = this.toLongOrNull() != null

fun String.is3DigitNumber() = this.length <= 3 && this.toLongOrNull() != null

fun List<List<Any>>.printMatrix() {
    for (row in this) {
        println(row.joinToString(" "))
    }
    println()
}

fun List<String>.searchAvailableRangeUntil(size: Int, limitIndex: Int): Pair<Int, Int>  {
    var start = 0
    var end = 0
    for(i in 0 .. limitIndex) {
        if(this[i] == "." && start == 0) {
            start = i
        }
        if(this[i] == "." && this[i+1] != ".") {
            end = i
            if(end-start >= size) {
                return Pair(start, end)
            }
        }
    }
    return Pair(start, end)
}



