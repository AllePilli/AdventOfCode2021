import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

/**
 * Iterate columns of a 2D list
 */
inline fun <T> List<List<T>>.forEachCol(action: (List<T>) -> Unit): Unit = indices
    .map { idx -> map { it[idx] } }
    .forEach(action)

/**
 * Returns the adjacent (UP, DOWN, LEFT, RIGHT) coordinates of a 2D point
 */
fun adjacentPositionsOf(rowIdx: Int, colIdx: Int): List<Pair<Int, Int>> = listOf(
    rowIdx - 1 to colIdx,
    rowIdx + 1 to colIdx,
    rowIdx to colIdx - 1,
    rowIdx to colIdx + 1
)

/**
 * Returns the adjacent (UP, DOWN, LEFT, RIGHT) values of a value in the 2D List
 */
fun <T> List<List<T>>.adjacentValuesOf(rowIdx: Int, colIdx: Int): List<T> = adjacentPositionsOf(rowIdx, colIdx)
    .mapNotNull { (row, col) ->
        try { this[row][col] } catch (ignored: IndexOutOfBoundsException) { null }
    }

infix fun String.getGroups(regex: Regex): List<String>? = regex.find(this)
    ?.groupValues
    ?.drop(1)
